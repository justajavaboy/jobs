package gov.ca.cwds.jobs.cals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.jobs.common.ChangedDTO;
import gov.ca.cwds.jobs.common.ElasticsearchIndexerDao;
import gov.ca.cwds.jobs.common.RecordChangeOperation;
import gov.ca.cwds.jobs.common.elastic.ElasticJobWriter;
import gov.ca.cwds.jobs.common.exception.JobsException;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author CWDS TPT-2
 */
public class CalsElasticJobWriter<T extends ChangedDTO<?>> extends ElasticJobWriter<T> {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CalsElasticJobWriter.class);

  /**
   * Constructor.
   *
   * @param elasticsearchDao ES DAO
   * @param objectMapper Jackson object mapper
   */
  public CalsElasticJobWriter(ElasticsearchIndexerDao elasticsearchDao,
      ObjectMapper objectMapper) {
    super(elasticsearchDao, objectMapper);
  }

  @Override
  public void write(List<T> items) {
    items.stream().forEach(item -> {
      try {
        RecordChangeOperation recordChangeOperation = item.getRecordChangeOperation();

        LOGGER.info("Preparing to delete item: ID {}", item.getId());
        bulkProcessor.add(elasticsearchDao.bulkDelete(item.getId()));

        if (RecordChangeOperation.I == recordChangeOperation
            || RecordChangeOperation.U == recordChangeOperation) {
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