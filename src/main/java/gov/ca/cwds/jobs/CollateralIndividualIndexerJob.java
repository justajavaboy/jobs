package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedCollateralIndividualR1Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedCollateralIndividualR1;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.inject.LastRunFile;

/**
 * Job to load collateral individuals from CMS into ElasticSearch.
 * 
 * @author CWDS API Team
 */
public class CollateralIndividualIndexerJob
    extends BasePersonIndexerJob<ReplicatedCollateralIndividualR1> {

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
  public CollateralIndividualIndexerJob(final ReplicatedCollateralIndividualR1Dao mainDao,
      final ElasticsearchDao elasticsearchDao, @LastRunFile final String lastJobRunTimeFilename,
      final ObjectMapper mapper, @CmsSessionFactory SessionFactory sessionFactory) {
    super(mainDao, elasticsearchDao, lastJobRunTimeFilename, mapper, sessionFactory);
  }

  @Override
  protected List<Pair<String, String>> getPartitionRanges() {
    List<Pair<String, String>> ret = new ArrayList<>();

    ret.add(Pair.of("aaaaaaaaaa", "A6mPxR5Ilb"));
    ret.add(Pair.of("A6mPMPID5N", "B2VV2f4LdS"));
    ret.add(Pair.of("B2VV6qw2P9", "CY2KGOVHOz"));
    ret.add(Pair.of("CY2KRBQ6W5", "DWD3hmjAJy"));
    ret.add(Pair.of("DWD3W409ej", "ESz9J6V7Mp"));
    ret.add(Pair.of("ESAaq8gEJ8", "FND8btS2LO"));
    ret.add(Pair.of("FND8SVHMO0", "GMnW5nY80N"));
    ret.add(Pair.of("GMnXjxZABY", "HIQfZ6QLNC"));
    ret.add(Pair.of("HIQigAP4t3", "IEnjssS8Ki"));
    ret.add(Pair.of("IEnkMmG4qe", "JBHRK6ABnA"));
    ret.add(Pair.of("JBHSkHCKdJ", "KylUmTS1MU"));
    ret.add(Pair.of("KylUHRNAX5", "LuJDdmeCVH"));
    ret.add(Pair.of("LuJEhiI7Dd", "Mrj4tCLBCn"));
    ret.add(Pair.of("Mrj4ANRAco", "NocqXqKGwl"));
    ret.add(Pair.of("Nocq4CoDsD", "Oj5w4IWFtu"));
    ret.add(Pair.of("Oj5w5pL76u", "PgMtwr05yk"));
    ret.add(Pair.of("PgMuawuOMr", "QesfJxy6fW"));
    ret.add(Pair.of("QesiVlJJQu", "RaGBF9M8iA"));
    ret.add(Pair.of("RaGB3d67jV", "R7hDMShKjT"));
    ret.add(Pair.of("R7hD2HY1XH", "S2q0LXN8qD"));
    ret.add(Pair.of("S2q0WGC8Q2", "T0asLsu2TE"));
    ret.add(Pair.of("T0auD24L6J", "0JvRy4eBwO"));
    ret.add(Pair.of("0JvRNa68Ek", "1E6wMKpMBf"));
    ret.add(Pair.of("1E6FGqrNhM", "2AzCYwU4ob"));
    ret.add(Pair.of("2AzDmjS7qQ", "3x6AsH7FSr"));
    ret.add(Pair.of("3x6Az8WIS3", "4uDXYwNLW9"));
    ret.add(Pair.of("4uD0XbN3Sh", "5rlKPs4G2m"));
    ret.add(Pair.of("5rlKZauFIj", "6nDOe5T4rt"));
    ret.add(Pair.of("6nDOh5KAiJ", "7jUZPH2Cm2"));
    ret.add(Pair.of("7jUZSfb4w7", "8gn4KlAGCT"));
    ret.add(Pair.of("8gohqnk4jx", "9cBPwfsMU1"));
    ret.add(Pair.of("9cBP1vmE5g", "9999999999"));

    return ret;
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
