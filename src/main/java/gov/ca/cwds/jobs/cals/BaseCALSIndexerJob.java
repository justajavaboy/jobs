package gov.ca.cwds.jobs.cals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gov.ca.cwds.cals.inject.MappingModule;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.data.es.ElasticsearchConfiguration5x;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.JobsException;
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

/**
 * @author CWDS TPT-2
 */
public abstract class BaseCALSIndexerJob<R extends JobReader> extends AbstractModule {

  private static final Logger LOGGER = LogManager.getLogger(BaseCALSIndexerJob.class);

  private File config;

  private Class<R> readerClass;

  protected BaseCALSIndexerJob(File config, Class<R> readerClass) {
    this.config = config;
    this.readerClass = readerClass;
  }

  @Override
  protected void configure() {
    install(new MappingModule());
    bind(JobReader.class).to(readerClass);
  }

  @Provides
  @Inject
  public JobWriter provideItemWriter(Elasticsearch5xDao elasticsearchDao, ObjectMapper objectMapper) {
    return new ReplicatedElasticJobWriter(elasticsearchDao, objectMapper);
  }

  @Provides
  @Inject
  public Job provideJob(JobReader jobReader, JobWriter jobWriter) {
    return new AsyncReadWriteJob(jobReader, jobWriter);
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
    if (config.getUser() != null && config.getPassword() != null) {
      settings.put("xpack.security.user", config.getUser() + ":" + config.getPassword());
      return new PreBuiltXPackTransportClient(settings.build());
    } else {
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
}
