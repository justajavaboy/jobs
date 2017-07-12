package gov.ca.cwds.jobs.cals.facility;

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
import gov.ca.cwds.cals.inject.CwsCmsDataAccessModule;
import gov.ca.cwds.cals.inject.FasDataAccessModule;
import gov.ca.cwds.cals.inject.FasSessionFactory;
import gov.ca.cwds.cals.inject.LisDataAccessModule;
import gov.ca.cwds.cals.inject.LisSessionFactory;
import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.JobsException;
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
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.hibernate.SessionFactory;

/**
 * <p> Command line arguments: </p>
 *
 * <pre>
 * {@code run script: $ java -DDB_FAS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=fas" \
-DDB_FAS_USER="postgres_data" -DDB_FAS_PASSWORD="CHANGEME" \
-DDB_LIS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=lis" \
-DDB_LIS_USER="postgres_data" -DDB_LIS_PASSWORD="CHANGEME" \
-DDB_CMS_JDBC_URL="jdbc:db2://192.168.99.100:50000/DB0TDEV" -DDB_CMS_SCHEMA="CWSCMSRS" \
-DDB_CMS_USER="db2inst1" -DDB_CMS_PASSWORD="CHANGEME" \
-cp build/libs/DocumentIndexerJob-0.24.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob \
config/facility.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public class FacilityIndexerJob extends AbstractModule {

  private static final Logger LOGGER = LogManager.getLogger(FacilityIndexerJob.class);

  private static final String JOB_NAME = "facility-profile-job";

  private File config;

  // todo tests, run javadoc, sonar

  /**
   * Default constructor.
   *
   * @param config configuration file
   */
  public FacilityIndexerJob(File config) {
    this.config = config;
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob path/to/config/file.yaml");
    }
    try {
      File configFile = new File(args[0]);
      Injector injector = Guice.createInjector(new FacilityIndexerJob(configFile));
      Job job = injector.getInstance(Key.get(Job.class, Names.named(JOB_NAME)));
      job.run();
    } catch (Exception e) {
      LOGGER.fatal("ERROR: ", e.getMessage(), e);
    }
  }

  @Override
  protected void configure() {
    install(new CwsCmsDataAccessModule("jobs-cms-hibernate.cfg.xml"));
    install(new LisDataAccessModule("lis-hibernate.cfg.xml"));
    install(new FasDataAccessModule("fas-hibernate.cfg.xml"));
    install(new MappingModule());
    bind(ChangedFacilityService.class);
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
        client = createTransportClient(config);
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

  private TransportClient createTransportClient(ElasticsearchConfiguration5x config) {
    Settings.Builder settings = Settings.builder()
            .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), config.getElasticsearchCluster());
    if(config.getUser() != null && config.getPassword() != null) {
      settings.put("xpack.security.user", config.getUser() + ":" + config.getPassword());
      return new PreBuiltXPackTransportClient(settings.build());
    }
    else {
      return new PreBuiltTransportClient(settings.build());
    }
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
  public JobReader itemReader(ChangedFacilityService changedFacilityService,
      @FasSessionFactory SessionFactory fasSessionFactory,
      @LisSessionFactory SessionFactory lisSessionFactory,
      @CmsSessionFactory SessionFactory cwsCmcSessionFactory) {
    return new FacilityReader(fasSessionFactory, lisSessionFactory, cwsCmcSessionFactory,
        changedFacilityService);
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
