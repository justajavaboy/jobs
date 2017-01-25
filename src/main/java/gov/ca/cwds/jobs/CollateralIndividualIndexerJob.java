package gov.ca.cwds.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.inject.CmsSessionFactory;
<<<<<<< HEAD
import gov.ca.cwds.jobs.inject.LastRunFile;
=======
import gov.ca.cwds.jobs.inject.JobsGuiceInjector;

public class CollateralIndividualIndexerJob extends JobBasedOnLastSuccessfulRunTime {
  private static final Logger LOGGER = LogManager.getLogger(CollateralIndividualIndexerJob.class);

  private static ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
  private static ObjectMapper MAPPER = new ObjectMapper();
>>>>>>> minor modification for java docs


/**
 * Job to load collateral individuals from CMS into ElasticSearch.
 * 
 * @author CWDS API Team
 */
public class CollateralIndividualIndexerJob extends BasePersonIndexerJob<CollateralIndividual> {

  private static final Logger LOGGER = LogManager.getLogger(CollateralIndividualIndexerJob.class);

  /**
   * Construct batch job instance with all required dependencies.
   * 
   * @param mainDao Attorney DAO
   * @param elasticsearchDao ElasticSearch DAO
   * @param lastJobRunTimeFilename last run date in format yyyy-MM-dd HH:mm:ss
   * @param mapper Jackson ObjectMapper
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public CollateralIndividualIndexerJob(final CollateralIndividualDao mainDao,
      final ElasticsearchDao elasticsearchDao, @LastRunFile final String lastJobRunTimeFilename,
      final ObjectMapper mapper, @CmsSessionFactory SessionFactory sessionFactory) {
    super(mainDao, elasticsearchDao, lastJobRunTimeFilename, mapper, sessionFactory);
  }

  /**
   * Batch job entry point.
   * 
   * @param args command line arguments
   */
  public static void main(String... args) {
    LOGGER.info("Run Collateral Individual indexer job");
    try {
      runJob(CollateralIndividualIndexerJob.class, args);
    } catch (JobsException e) {
      LOGGER.error("STOPPING BATCH: " + e.getMessage(), e);
      throw e;
    }
  }

}
