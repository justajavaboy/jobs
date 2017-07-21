package gov.ca.cwds.jobs.cals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gov.ca.cwds.cals.inject.MappingModule;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.exception.JobsException;
import gov.ca.cwds.jobs.util.elastic.XPackUtils;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.ApiException;
import java.io.File;
import java.net.InetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author CWDS TPT-2
 */
public abstract class BaseCalsIndexerJob extends AbstractModule {

  private static final Logger LOGGER = LogManager.getLogger(BaseCalsIndexerJob.class);

  private File config;

  protected BaseCalsIndexerJob(String configFileName) {
    this.config = new File(configFileName);
  }

  @Override
  protected void configure() {
    install(new MappingModule());
  }

  protected final void run() {
    try {
      final Injector injector = Guice.createInjector(this);
      Job job = injector.getInstance(Job.class);
      job.run();
    } catch (RuntimeException e) {
      LOGGER.fatal("ERROR: ", e.getMessage(), e);
    }
  }

  @Provides
  @Inject
  public Client elasticsearchClient(ElasticsearchConfiguration config) {
    TransportClient client = null;
    LOGGER.warn("Create NEW ES client");
    try {
      Settings.Builder settings =
          Settings.builder().put("cluster.name", config.getElasticsearchCluster());
      client = XPackUtils.secureClient(config.getUser(), config.getPassword(), settings);
      client.addTransportAddress(
          new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
              Integer.parseInt(config.getElasticsearchPort())));
    } catch (Exception e) {
      LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
      if (client != null) {
        client.close();
      }
      throw new ApiException("Error initializing Elasticsearch client: " + e.getMessage(), e);
    }
    return client;
  }

  @Provides
  @Singleton
  @Inject
  public CalsElasticsearchIndexerDao elasticsearchDao(Client client,
      ElasticsearchConfiguration configuration) {
    return new CalsElasticsearchIndexerDao(client, configuration);
  }

  @Provides
  public ElasticsearchConfiguration config() {
    ElasticsearchConfiguration configuration = null;
    if (config != null) {
      try {
        configuration =
            new ObjectMapper(new YAMLFactory())
                .readValue(config, ElasticsearchConfiguration.class);
      } catch (Exception e) {
        LOGGER.error("Error reading job configuration: {}", e.getMessage(), e);
        throw new JobsException("Error reading job configuration: " + e.getMessage(), e);
      }
    }
    return configuration;
  }
}
