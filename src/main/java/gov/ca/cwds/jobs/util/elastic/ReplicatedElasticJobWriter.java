package gov.ca.cwds.jobs.util.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.cals.service.dto.rs.ReplicatedDTO;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.jobs.JobsException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @param <T> persistence class type which implements ReplicatedDTO
 * @author CWDS TPT-2
 */
public class ReplicatedElasticJobWriter<T extends ReplicatedDTO> extends ElasticJobWriter<T> {

  private static final Logger LOGGER = LogManager.getLogger(ReplicatedElasticJobWriter.class);

  private static final String REPLICATION_OPERATION_INSERT = "I";
  private static final String REPLICATION_OPERATION_UPDATE = "U";
  private static final String REPLICATION_OPERATION_DELETE = "D";

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
  public void write(List<T> items) throws Exception {
    items.stream().forEach(item -> {
      try {
        String replicationOperation = item.getReplicationOperation();
        if (REPLICATION_OPERATION_INSERT.equals(replicationOperation)) {
          LOGGER.info("Preparing to insert item: ID {}", item.getId());
          bulkProcessor.add(elasticsearchDao.bulkAdd(objectMapper, item.getId(), item));
        } else if (REPLICATION_OPERATION_UPDATE.equals(replicationOperation)) {
          LOGGER.info("Preparing to upsert item: ID {}", item.getId());
          //bulkProcessor.add(elasticsearchDao.bulkUpsert(objectMapper, item.getId(), item));
          bulkProcessor.add(elasticsearchDao.bulkAdd(objectMapper, item.getId(), item));
        } else if (REPLICATION_OPERATION_DELETE.equals(replicationOperation)) {
          LOGGER.info("Preparing to delete item: ID {}", item.getId());
          bulkProcessor.add(elasticsearchDao.bulkDelete(item.getId()));
        } else {
          throw new JobsException("Unsupported replication operation: "+ replicationOperation);
        }
      } catch (JsonProcessingException e) {
        throw new JobsException(e);
      }
    });
    bulkProcessor.flush();
  }
}
