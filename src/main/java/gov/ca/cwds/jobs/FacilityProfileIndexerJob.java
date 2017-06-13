package gov.ca.cwds.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import gov.ca.cwds.cals.persistence.dao.cms.CountiesDao;
import gov.ca.cwds.cals.persistence.dao.cms.IPlacementHomeDao;
import gov.ca.cwds.cals.service.mapper.FacilityMapper;
import gov.ca.cwds.jobs.inject.CalsDataAccessModule;
import gov.ca.cwds.cals.inject.MappingModule;
import gov.ca.cwds.cals.service.FacilityCollectionService;
import gov.ca.cwds.cals.service.dto.CollectionDTO;
import gov.ca.cwds.cals.service.dto.FacilityDTO;
import gov.ca.cwds.cals.web.rest.parameter.FacilityParameterObject;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.data.es.ElasticsearchConfiguration5x;
import gov.ca.cwds.jobs.util.AsyncReadWriteJob;
import gov.ca.cwds.jobs.util.JobReader;
import gov.ca.cwds.jobs.util.JobWriter;
import gov.ca.cwds.jobs.util.elastic.ElasticJobWriter;
import java.io.File;
import java.net.InetAddress;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.hibernate.Session;

import static gov.ca.cwds.cals.Constants.UnitOfWork.CMS;

/**
 * <p>
 * Command line arguments:
 * </p>
 *
 * <pre>
 * {@code run script: $java -cp jobs.jar gov.ca.cwds.jobs.FacilityProfileIndexerJob path/to/config/file.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public class FacilityProfileIndexerJob extends AbstractModule {
  private static final Logger LOGGER = LogManager.getLogger(FacilityProfileIndexerJob.class);

  private static final String JOB_NAME = "facility-profile-job";

  private File config;

  private CalsDataAccessModule dataAccessModule2;

  // todo tests, run javadoc, sonar

  /**
   * Default constructor.
   *
   * @param config configuration file
   */
  public FacilityProfileIndexerJob(File config) {
    this.config = config;
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.FacilityProfileIndexerJob path/to/config/file.yaml");
    }
    try {
      File configFile = new File(args[0]);
      Injector injector = Guice.createInjector(new FacilityProfileIndexerJob(configFile));
      Job job = injector.getInstance(Key.get(Job.class, Names.named(JOB_NAME)));
      job.run();
    } catch (Exception e) {
      LOGGER.fatal("ERROR: ", e.getMessage(), e);
    }
  }

  // todo commonize
  @Override
  protected void configure() {
    dataAccessModule2 = new CalsDataAccessModule();
    install(dataAccessModule2);

    install(new MappingModule());
  }

  @Provides
  @Inject
  FacilityCollectionService provideFacilityCollectionService(IPlacementHomeDao placementHomeDao, CountiesDao countiesDao, FacilityMapper facilityMapper) {
    return new FacilityCollectionService(placementHomeDao, countiesDao, facilityMapper);
  }

  // todo commonize
  @Provides
  @Inject
  public Client elasticsearchClient(ElasticsearchConfiguration5x config) {
    TransportClient client = null;
    if (config != null) {
      LOGGER.info("Creating new ES5 client to {}:{} in cluster '{}'",
          config.getElasticsearchHost(),
          config.getElasticsearchPort(),
          config.getElasticsearchCluster()
      );
      try {
        Settings settings = Settings.builder()
            .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), config.getElasticsearchCluster()).build();
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
                Integer.parseInt(config.getElasticsearchPort())));
      } catch (Exception e) {
        LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
        throw new JobsException("Error initializing Elasticsearch client: " + e.getMessage(), e);
      }
    }
    return client;
  }

  // todo commonize
  @Provides
  @Singleton
  @Inject
  public Elasticsearch5xDao elasticsearchDao(Client client, ElasticsearchConfiguration5x configuration) {
    return new Elasticsearch5xDao(client, configuration);
  }

  @Provides
  public ElasticsearchConfiguration5x config() {
    ElasticsearchConfiguration5x configuration = null;
    if (config != null) {
      try {
        configuration =
            new ObjectMapper(new YAMLFactory()).readValue(config, ElasticsearchConfiguration5x.class);
      } catch (Exception e) {
        LOGGER.error("Error reading job configuration: {}", e.getMessage(), e);
        throw new JobsException("Error reading job configuration: " + e.getMessage(), e);
      }
    }
    return configuration;
  }

  @Provides
  @Named("facility-reader")
  @Inject
  public JobReader itemReader(FacilityCollectionService facilityCollectionService) {
    // todo commonize
    return new JobReader<FacilityDTO>() {
      private Iterator<FacilityDTO> facilityDTOIterator;

      @Override
      public FacilityDTO read() throws Exception {
        return facilityDTOIterator.hasNext() ? facilityDTOIterator.next() : null;
      }

      @Override
      public void init() throws Exception {
        FacilityParameterObject facilityParameterObject = new FacilityParameterObject(CMS);

        // todo transaction control might not be needed after removal @UnitOfWork(CMS) from the FacilityCollectionService
        Session cmsSession = dataAccessModule2.getCmsSessionFactory().getCurrentSession();
        cmsSession.beginTransaction();
        facilityDTOIterator = ((CollectionDTO<FacilityDTO>) facilityCollectionService.find(facilityParameterObject)).getCollection().iterator();
        cmsSession.getTransaction().rollback();
        cmsSession.close();
      }

      @Override
      public void destroy() throws Exception {
        dataAccessModule2.getCmsSessionFactory().close();
      }
    };
  }

  @Provides
  @Named("facility-writer")
  @Inject
  public JobWriter itemWriter(Elasticsearch5xDao elasticsearchDao, ObjectMapper objectMapper) {
    return new ElasticJobWriter<FacilityDTO>(elasticsearchDao, objectMapper);
  }

  @Provides
  @Named(JOB_NAME)
  @Inject
  public Job itemWriter(@Named("facility-reader") JobReader jobReader,
      @Named("facility-writer") JobWriter jobWriter) {
    return new AsyncReadWriteJob(jobReader, jobWriter);
  }
}
