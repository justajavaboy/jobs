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
import gov.ca.cwds.data.persistence.cms.rep.CmsReplicationOperation;
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
  protected boolean isDelete(ReplicatedCollateralIndividualR1 t) {
    return t.getReplicationOperation() == CmsReplicationOperation.D;
  }

  @Override
  protected List<Pair<String, String>> getPartitionRanges() {
    List<Pair<String, String>> ret = new ArrayList<>();

    ret.add(Pair.of("aaaaaaaaaa", "ACWv1PfL4m"));
    ret.add(Pair.of("ACWv4wR7XG", "A6mOyA78bU"));
    ret.add(Pair.of("A6mPxR5Ilb", "By4eJKHOx3"));
    ret.add(Pair.of("By4e5CkE1Y", "B2VUsbO8lo"));
    ret.add(Pair.of("B2VVjwvIzg", "Cvu2IaYIc1"));
    ret.add(Pair.of("Cvu2NaFJQh", "CY2ISNMAnd"));
    ret.add(Pair.of("CY2KjEP1Jz", "DsBsufZGQf"));
    ret.add(Pair.of("DsBBvmOC7k", "DWD1BLdI3U"));
    ret.add(Pair.of("DWD2Aww6vg", "EpHUadl9wZ"));
    ret.add(Pair.of("EpHUhIeJPL", "ESz8boj8rm"));
    ret.add(Pair.of("ESz8bHjH02", "Fmgka7u1H9"));
    ret.add(Pair.of("FmgkniGBg1", "FNDP4tYFuj"));
    ret.add(Pair.of("FND4ccFFJR", "GiD5TzAEAo"));
    ret.add(Pair.of("GiD6FT6BD0", "GMnU6nC4yT"));
    ret.add(Pair.of("GMnU7C06IC", "HeXchVXDKI"));
    ret.add(Pair.of("HeXctUnJ0m", "HIPoTOU2jZ"));
    ret.add(Pair.of("HIQcSR7CId", "Ibs7rEqK3H"));
    ret.add(Pair.of("Ibs7YSPCYV", "IEm2LE4EYk"));
    ret.add(Pair.of("IEm27VXGHu", "I6I3Fcq7rk"));
    ret.add(Pair.of("I6I3TkGFuB", "JBHNkCB0Wc"));
    ret.add(Pair.of("JBHNzeB8N6", "J5wigtPEOb"));
    ret.add(Pair.of("J5wi6rRCus", "KylCyA8L9w"));
    ret.add(Pair.of("KylCQWnBml", "K10fxzXCco"));
    ret.add(Pair.of("K10gdgFAfo", "LuI9JvsAAr"));
    ret.add(Pair.of("LuI9LriEWN", "LX3HLPv5cT"));
    ret.add(Pair.of("LX3HSPV8JE", "MrjMMnw7nF"));
    ret.add(Pair.of("MrjMXKLKFB", "MUVQnW6D02"));
    ret.add(Pair.of("MUVQn3J9KL", "NobPqq48bS"));
    ret.add(Pair.of("NobRdii5KJ", "NR6ilQBL7M"));
    ret.add(Pair.of("NR6il1NCRo", "Oj5e5GDJJ2"));
    ret.add(Pair.of("Oj5e5WiFLB", "ONVolXj8bU"));
    ret.add(Pair.of("ONVomg7GYe", "PgLWwgB0KH"));
    ret.add(Pair.of("PgLWLNwEOa", "PJwxpOiEIK"));
    ret.add(Pair.of("PJwx4BFJ8A", "Qerlpwt3rK"));
    ret.add(Pair.of("Qerm6rf7K3", "QG098CcAVo"));
    ret.add(Pair.of("QG1abMQFE8", "RaGhRfW42v"));
    ret.add(Pair.of("RaGhRXJ9Vq", "REA1TMKO8F"));
    ret.add(Pair.of("REA10eZ9lD", "R7hkXmY3Q9"));
    ret.add(Pair.of("R7hkX39AS2", "SzQ7uFaEby"));
    ret.add(Pair.of("SzQ7F1iHaX", "S2qE3aO3cV"));
    ret.add(Pair.of("S2qFC72DVw", "TxprGutIqm"));
    ret.add(Pair.of("Txpr3fCApz", "TZ93nQ9BYF"));
    ret.add(Pair.of("TZ93pLr2dW", "0fNIQDT7WA"));
    ret.add(Pair.of("0fNI2J9AmM", "0Ju1gmGIim"));
    ret.add(Pair.of("0Ju1upYMUZ", "1bdKe9tNJX"));
    ret.add(Pair.of("1bdKSZHDWr", "1E5UydmLZ4"));
    ret.add(Pair.of("1E5U3AS9pl", "17OD3f1MhW"));
    ret.add(Pair.of("17OD93B5ig", "2Ay7CEHAKJ"));
    ret.add(Pair.of("2AzklwBLIl", "224GZzPDRI"));
    ret.add(Pair.of("224H3pKFUi", "3x5JWBfFec"));
    ret.add(Pair.of("3x5KyQcGHu", "31yJ4q3LXy"));
    ret.add(Pair.of("31yUvTJGkW", "4uC8unkBDb"));
    ret.add(Pair.of("4uC9Az3B6P", "4YsUNr59us"));
    ret.add(Pair.of("4YsUQaQESf", "5rkUIyGD0v"));
    ret.add(Pair.of("5rkVN2JI5t", "5TOieGe3T6"));
    ret.add(Pair.of("5TOjVgLNeC", "6nCZQnQCta"));
    ret.add(Pair.of("6nC0lZKEPw", "6Rppbk16oI"));
    ret.add(Pair.of("6Rpp1e4I6A", "7jUdFIE7IS"));
    ret.add(Pair.of("7jUdJQ35Dg", "7NMj9cuAWz"));
    ret.add(Pair.of("7NMkBxkNeN", "8gnsaW4EQf"));
    ret.add(Pair.of("8gntDLB8jw", "8JeC4AvNtB"));
    ret.add(Pair.of("8JeC5VIAoa", "9cA1GT74tY"));
    ret.add(Pair.of("9cA1LvkAxm", "9GnY4XWCG7"));
    ret.add(Pair.of("9Gn0qcVL9W", "9999999999"));

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
