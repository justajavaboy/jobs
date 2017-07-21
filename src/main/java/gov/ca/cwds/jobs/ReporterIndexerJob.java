package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedReporterR1Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedReporterR1;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.inject.LastRunFile;

/**
 * Job to load reporters from CMS into ElasticSearch
 * 
 * @author CWDS API Team
 */
public class ReporterIndexerJob extends BasePersonIndexerJob<ReplicatedReporterR1> {

  private static final Logger LOGGER = LogManager.getLogger(ReporterIndexerJob.class);

  /**
   * Construct batch job instance with all required dependencies.
   * 
   * @param reporterDao Client DAO
   * @param elasticsearchDao ElasticSearch DAO
   * @param lastJobRunTimeFilename last run date in format yyyy-MM-dd HH:mm:ss
   * @param mapper Jackson ObjectMapper
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public ReporterIndexerJob(final ReplicatedReporterR1Dao reporterDao,
      final ElasticsearchDao elasticsearchDao, @LastRunFile final String lastJobRunTimeFilename,
      final ObjectMapper mapper, @CmsSessionFactory SessionFactory sessionFactory) {
    super(reporterDao, elasticsearchDao, lastJobRunTimeFilename, mapper, sessionFactory);
  }

  @Override
  protected List<Pair<String, String>> getPartitionRanges() {
    List<Pair<String, String>> ret = new ArrayList<>();

    ret.add(Pair.of("AaaacJM6AD", "A6y49C3046"));
    ret.add(Pair.of("A6y5Ztl5Dl", "B26CINE92i"));
    ret.add(Pair.of("B26CO2GAR1", "CY8sCNP6d7"));
    ret.add(Pair.of("CY8sE2b63f", "DWQK5baGUw"));
    ret.add(Pair.of("DWQLkxm438", "ESH7UXSBSV"));
    ret.add(Pair.of("ESH8BxU3Wy", "FN4LJVd0AR"));
    ret.add(Pair.of("FN4M0jRAmN", "GMSoVYy8Op"));
    ret.add(Pair.of("GMSpuwSJDr", "HJtTh9f4jf"));
    ret.add(Pair.of("HJtUv0lHKH", "IEwL9Ef4kN"));
    ret.add(Pair.of("IEwMszJ30A", "JBHtWsL37S"));
    ret.add(Pair.of("JBHvi0A6DS", "KybshZU8Hb"));
    ret.add(Pair.of("Kybsjpt5xF", "LuIRpwB9k6"));
    ret.add(Pair.of("LuISxf810S", "Mq7jvEH3HH"));
    ret.add(Pair.of("Mq7jRX1MTJ", "NnZ00Af2OJ"));
    ret.add(Pair.of("NnZ066DB42", "OjJpyxxAFo"));
    ret.add(Pair.of("OjJpLfq2Ld", "Pf5BLjG4cy"));
    ret.add(Pair.of("Pf5BP86AA8", "Qd7QPaE74E"));
    ret.add(Pair.of("Qd7Q6XIChE", "RaLnoyQDyB"));
    ret.add(Pair.of("RaLnpli5Q3", "R62kZoRLJg"));
    ret.add(Pair.of("R62lkw14kN", "S2pP4Xt3Qu"));
    ret.add(Pair.of("S2pP6o55Le", "T0fhH6o10S"));
    ret.add(Pair.of("T0fikDQ5Je", "0JGINrlCGB"));
    ret.add(Pair.of("0JGIVBuEyI", "1FbRaX4B47"));
    ret.add(Pair.of("1FbRtNhJSW", "2AzY8cD30A"));
    ret.add(Pair.of("2AzZmuP30A", "3yrjB8Z93f"));
    ret.add(Pair.of("3yrjFvz9hu", "4u1e2dfFpP"));
    ret.add(Pair.of("4u1f0q86ob", "5rOwNNa5DO"));
    ret.add(Pair.of("5rOxzD06b3", "6oi199YEqp"));
    ret.add(Pair.of("6oi28x810S", "7kjn4TK0Z6"));
    ret.add(Pair.of("7kjocVA64q", "8gu1ml707S"));
    ret.add(Pair.of("8gu6lVM7x1", "9cSjB9u8Q2"));
    ret.add(Pair.of("9cSkIHx06Q", "9999999999"));

    return ret;
  }

  /**
   * Batch job entry point.
   * 
   * @param args command line arguments
   */
  public static void main(String... args) {
    LOGGER.info("Run Reporter indexer job");
    try {
      runJob(ReporterIndexerJob.class, args);
    } catch (JobsException e) {
      LOGGER.error("STOPPING BATCH: " + e.getMessage(), e);
      throw e;
    }
  }

}
