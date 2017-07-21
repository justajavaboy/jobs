package gov.ca.cwds.jobs.cals;

import gov.ca.cwds.rest.ElasticsearchConfiguration;
import java.io.Closeable;
import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * A DAO for Elasticsearch with writing indexes functionality.
 * It is not intended for searching, nor it can contain any index-specific code or hardcoded mapping.
 *
 * <p>
 * Let Guice manage inject object instances. Don't manage instances in this class.
 * </p>
 *
 * @author CWDS TPT-2
 *
 */
public class CalsElasticsearchIndexerDao implements Closeable {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CalsElasticsearchIndexerDao.class);

  private static int NUMBER_OF_SHARDS = 5;

  private static int NUMBER_OF_REPLICAS = 1;

  /**
   * Client is thread safe.
   */
  private Client client;

  /**
   * Elasticsearch configuration
   */
  private ElasticsearchConfiguration config;

  /**
   * Constructor.
   *
   * @param client The ElasticSearch client
   * @param config The ElasticSearch configuration which is read from .yaml file
   */
  @Inject
  public CalsElasticsearchIndexerDao(Client client, ElasticsearchConfiguration config) {
    this.client = client;
    this.config = config;
  }

  /**
   * Check whether Elasticsearch already has the chosen index.
   *
   * @param index index name or alias
   * @return whether the index exists
   */
  private boolean doesIndexExist(final String index) {
    final IndexMetaData indexMetaData = client.admin().cluster()
        .state(Requests.clusterStateRequest()).actionGet().getState().getMetaData().index(index);
    return indexMetaData != null;
  }

  /**
   * Create an index before blasting documents into it.
   *
   * @param index index name or alias
   * @param numShards number of shards
   * @param numReplicas number of replicas
   */
  private void createIndex(final String index, int numShards, int numReplicas) {
    LOGGER.warn("CREATE ES INDEX {} with {} shards and {} replicas", index, numShards, numReplicas);
    final Settings indexSettings = Settings.builder().put("number_of_shards", numShards)
        .put("number_of_replicas", numReplicas).build();
    CreateIndexRequest indexRequest = new CreateIndexRequest(index, indexSettings);
    getClient().admin().indices().create(indexRequest).actionGet();
  }

  /**
   * Create an index, if needed, before blasting documents into it.
   *
   * <p>
   * Defaults to 5 shards and 1 replica.
   * </p>
   *
   * <p>
   * Method is intentionally synchronized to prevent race conditions and multiple attempts to create
   * the same index.
   * </p>
   *
   * @param index index name or alias
   */
  public synchronized void createIndexIfNeeded(final String index) {
    if (!doesIndexExist(index)) {
      LOGGER.warn("ES INDEX {} DOES NOT EXIST!!", index);
      createIndex(index, NUMBER_OF_SHARDS, NUMBER_OF_REPLICAS);

      try {
        // Give Elasticsearch a moment to catch its breath.
        // Thread.currentThread().wait(2000L); // thread monitor error
        Thread.sleep(2000L);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.warn("Interrupted!");
      }
    }
  }

  /**
   * Prepare an index request for bulk operations.
   *
   * @param mapper Jackson ObjectMapper
   * @param id ES document id
   * @param obj document object
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public IndexRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj)
      throws JsonProcessingException {
    return client.prepareIndex(config.getElasticsearchAlias(),
        config.getElasticsearchDocType(), id).setSource(mapper.writeValueAsBytes(obj), XContentType.JSON).request();
  }

  /**
   * Prepare an delete request for bulk operations.
   *
   * @param id ES document id
   * @return prepared DeleteRequest
   */
  public DeleteRequest bulkDelete(final String id) {
    return client.prepareDelete(config.getElasticsearchAlias(),
        config.getElasticsearchDocType(), id).request();
  }

  /**
   * Stop the ES client, if started.
   */
  private void stop() {
    if (client != null) {
      this.client.close();
    }
  }

  @Override
  public void close() throws IOException {
    try {
      stop();
    } catch (Exception e) {
      final String msg = "Error closing ElasticSearch DAO: " + e.getMessage();
      LOGGER.error(msg, e);
      throw new IOException(msg, e);
    }
  }

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }
}
