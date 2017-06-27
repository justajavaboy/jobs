package gov.ca.cwds.jobs.util.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.cals.ReplicatedCompositeDTO;
import gov.ca.cwds.cals.ReplicationOperation;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.jobs.JobsException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CWDS TPT-2
 */
public class ReplicatedElasticJobWriter extends ElasticJobWriter<ReplicatedCompositeDTO> {

  private static final Logger LOGGER = LogManager.getLogger(ReplicatedElasticJobWriter.class);

  /**
   * Constructor.
   *
   * @param elasticsearchDao ES DAO
   * @param objectMapper Jackson object mapper
   */
  public ReplicatedElasticJobWriter(Elasticsearch5xDao elasticsearchDao,
      ObjectMapper objectMapper) {
    super(elasticsearchDao, objectMapper);
  }

  @Override
  public void write(List<ReplicatedCompositeDTO> items) throws Exception {
    items.stream().forEach(item -> {
      try {
        ReplicationOperation replicationOperation = item.getReplicationOperation();

        LOGGER.info("Preparing to delete item: ID {}", item.getId());
        bulkProcessor.add(elasticsearchDao.bulkDelete(item.getId()));

        if (ReplicationOperation.I == replicationOperation
            || ReplicationOperation.U == replicationOperation) {
          LOGGER.info("Preparing to insert item: ID {}", item.getId());
          bulkProcessor.add(elasticsearchDao.bulkAdd(objectMapper, item.getId(), item.getDTO()));
        }
      } catch (JsonProcessingException e) {
        throw new JobsException(e);
      }
    });
    bulkProcessor.flush();
  }
}
