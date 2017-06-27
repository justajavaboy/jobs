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
import gov.ca.cwds.cals.inject.ReplicatedCwsCmsDataAccessModule;
import gov.ca.cwds.cals.persistence.dao.cms.CountiesDao;
import gov.ca.cwds.cals.persistence.dao.cms.ClientDao;
import gov.ca.cwds.cals.persistence.dao.cms.PlacementHomeDao;
import gov.ca.cwds.cals.persistence.dao.cms.rs.ReplicatedPersistentEntityDao;
import gov.ca.cwds.cals.service.ReplicatedFacilityService;
import gov.ca.cwds.cals.service.mapper.FacilityChildMapper;
import gov.ca.cwds.cals.service.mapper.FacilityMapper;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.facility.FacilityProfileReader;
import gov.ca.cwds.cals.inject.MappingModule;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.data.es.ElasticsearchConfiguration5x;
import gov.ca.cwds.jobs.util.AsyncReadWriteJob;
import gov.ca.cwds.jobs.util.JobReader;
import gov.ca.cwds.jobs.util.JobWriter;
import gov.ca.cwds.jobs.util.elastic.ReplicatedElasticJobWriter;
import java.io.File;
import java.net.InetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.hibernate.SessionFactory;

/**
 * <p> Command line arguments: </p>
 *
 * <pre>
 * {@code run script: $java -cp jobs.jar gov.ca.cwds.jobs.FacilityProfileIndexerJob
 * path/to/config/file.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public class FacilityProfileIndexerJob extends AbstractModule {

  private static final Logger LOGGER = LogManager.getLogger(FacilityProfileIndexerJob.class);

  private static final String JOB_NAME = "facility-profile-job";

  private File config;

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

  @Override
  protected void configure() {
    install(new ReplicatedCwsCmsDataAccessModule("jobs-cms-hibernate.cfg.xml"));
    install(new MappingModule());
  }

  @Provides
  @Inject
  ReplicatedFacilityService provideReplicatedFacilityService(
      PlacementHomeDao placementHomeDao, CountiesDao countiesDao,
      FacilityMapper facilityMapper, ReplicatedPersistentEntityDao replicatedPersistentEntityDao,
      ClientDao clientDao, FacilityChildMapper facilityChildMapper) {
    return new ReplicatedFacilityService(null, null, placementHomeDao,
        null, countiesDao, facilityMapper, null,
        replicatedPersistentEntityDao, clientDao, facilityChildMapper);
  }

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
            .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), config.getElasticsearchCluster())
            .build();
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

  @Provides
  @Singleton
  @Inject
  public Elasticsearch5xDao elasticsearchDao(Client client,
      ElasticsearchConfiguration5x configuration) {
    return new Elasticsearch5xDao(client, configuration);
  }

  @Provides
  public ElasticsearchConfiguration5x config() {
    ElasticsearchConfiguration5x configuration = null;
    if (config != null) {
      try {
        configuration =
            new ObjectMapper(new YAMLFactory())
                .readValue(config, ElasticsearchConfiguration5x.class);
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
  public JobReader itemReader(ReplicatedFacilityService replicatedFacilityService,
      @CmsSessionFactory SessionFactory sessionFactory) {
    return new FacilityProfileReader(sessionFactory, replicatedFacilityService);
  }

  @Provides
  @Named("facility-writer")
  @Inject
  public JobWriter itemWriter(Elasticsearch5xDao elasticsearchDao, ObjectMapper objectMapper) {
    return new ReplicatedElasticJobWriter(elasticsearchDao, objectMapper);
  }

  @Provides
  @Named(JOB_NAME)
  @Inject
  public Job itemWriter(@Named("facility-reader") JobReader jobReader,
      @Named("facility-writer") JobWriter jobWriter) {
    return new AsyncReadWriteJob(jobReader, jobWriter);
  }
}
