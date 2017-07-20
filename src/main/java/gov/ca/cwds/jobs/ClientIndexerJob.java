package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedClientR1Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.CmsReplicationOperation;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedClientR1;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.inject.LastRunFile;

/**
 * Job to load Clients from CMS into ElasticSearch.
 * 
 * @author CWDS API Team
 */
public class ClientIndexerJob extends BasePersonIndexerJob<ReplicatedClientR1> {

  private static final Logger LOGGER = LogManager.getLogger(ClientIndexerJob.class);

  /**
   * Construct batch job instance with all required dependencies.
   * 
   * @param clientDao Client DAO
   * @param elasticsearchDao ElasticSearch DAO
   * @param lastJobRunTimeFilename last run date in format yyyy-MM-dd HH:mm:ss
   * @param mapper Jackson ObjectMapper
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public ClientIndexerJob(final ReplicatedClientR1Dao clientDao,
      final ElasticsearchDao elasticsearchDao, @LastRunFile final String lastJobRunTimeFilename,
      final ObjectMapper mapper, @CmsSessionFactory SessionFactory sessionFactory) {
    super(clientDao, elasticsearchDao, lastJobRunTimeFilename, mapper, sessionFactory);
  }

  @Override
  protected boolean isSensitive(ReplicatedClientR1 t) {
    return !(t.getSensitivityIndicator() != null && "N".equals(t.getSensitivityIndicator()));
  }

  @Override
  protected boolean isDelete(ReplicatedClientR1 t) {
    return t.getReplicationOperation() == CmsReplicationOperation.D;
  }

  @Override
  protected List<Pair<String, String>> getPartitionRanges() {
    List<Pair<String, String>> ret = new ArrayList<>();

    // LINUX:
    // ret.add(Pair.of(" ", "B3bMRWu8NV"));
    // ret.add(Pair.of("B3bMRWu8NV", "DW5GzxJ30A"));
    // ret.add(Pair.of("DW5GzxJ30A", "FNOBbaG6qq"));
    // ret.add(Pair.of("FNOBbaG6qq", "HJf1EJe25X"));
    // ret.add(Pair.of("HJf1EJe25X", "JCoyq0Iz36"));
    // ret.add(Pair.of("JCoyq0Iz36", "LvijYcj01S"));
    // ret.add(Pair.of("LvijYcj01S", "Npf4LcB3Lr"));
    // ret.add(Pair.of("Npf4LcB3Lr", "PiJ6a0H49S"));
    // ret.add(Pair.of("PiJ6a0H49S", "RbL4aAL34A"));
    // ret.add(Pair.of("RbL4aAL34A", "S3qiIdg0BN"));
    // ret.add(Pair.of("S3qiIdg0BN", "0Ltok9y5Co"));
    // ret.add(Pair.of("0Ltok9y5Co", "2CFeyJd49S"));
    // ret.add(Pair.of("2CFeyJd49S", "4w3QDw136B"));
    // ret.add(Pair.of("4w3QDw136B", "6p9XaHC10S"));
    // ret.add(Pair.of("6p9XaHC10S", "8jw5J580MQ"));
    // ret.add(Pair.of("8jw5J580MQ", "9999999999"));

    // Z/OS:
    ret.add(Pair.of("aaaaaaaaaa", "ACXHGEw0P3"));
    ret.add(Pair.of("ACXHSMQ8kF", "A6nGexi9k6"));
    ret.add(Pair.of("A6nGfYt7PN", "By4f9P9ICJ"));
    ret.add(Pair.of("By4ge6hIE6", "B2Q96606CQ"));
    ret.add(Pair.of("B2Q98hGIE6", "CvpprL87GE"));
    ret.add(Pair.of("CvppCeI5Aj", "CYZcUsW6xC"));
    ret.add(Pair.of("CYZcXLA8kF", "DsAfKkN5DO"));
    ret.add(Pair.of("DsAfMoN3t4", "DWElds941S"));
    ret.add(Pair.of("DWElVDWBbF", "EpBFy4b2vI"));
    ret.add(Pair.of("EpBFV6gBt6", "ESx1zaWClV"));
    ret.add(Pair.of("ESx186b15A", "FmoQRKPBuJ"));
    ret.add(Pair.of("FmoQWK08o7", "FNJLm5L6mW"));
    ret.add(Pair.of("FNJLrPc9gO", "GiQjAhYBXe"));
    ret.add(Pair.of("GiQjBQv5Du", "GMxhmw01nH"));
    ret.add(Pair.of("GMxhtrS87C", "He0TJNh36B"));
    ret.add(Pair.of("He0T3T8KeQ", "HIWv4Gy63O"));
    ret.add(Pair.of("HIWv7P28p7", "IbjHPWZ92i"));
    ret.add(Pair.of("IbjIdRiLZ2", "ID3JRxzFcZ"));
    ret.add(Pair.of("ID3J1vX8kK", "I6zIR00Ans"));
    ret.add(Pair.of("I6zJX7p43S", "JBrlCjJ5Le"));
    ret.add(Pair.of("JBrlDjG42v", "J481cbgIw6"));
    ret.add(Pair.of("J481tac7HX", "KxPVlMbBV2"));
    ret.add(Pair.of("KxPVEKa1qa", "K1qJC2x6VR"));
    ret.add(Pair.of("K1qJGAvGI0", "LublVK5Klf"));
    ret.add(Pair.of("Lubl5kWJeV", "LXgzabJILP"));
    ret.add(Pair.of("LXgzvJB04F", "Mqo1AwO4nn"));
    ret.add(Pair.of("Mqo1AXnIg3", "MT0uJ8IFns"));
    ret.add(Pair.of("MT0uYVt01S", "NnfXRK3CR3"));
    ret.add(Pair.of("NnfXYiLDGM", "NQS86QBC1F"));
    ret.add(Pair.of("NQS9eaVD1N", "OiELUDSLY3"));
    ret.add(Pair.of("OiELW4n43S", "OMyg32f30A"));
    ret.add(Pair.of("OMymd9N30A", "Pe2DPbJ9Bz"));
    ret.add(Pair.of("Pe2DPCg8by", "PHOY4W58iJ"));
    ret.add(Pair.of("PHOZbPu5Cy", "QcKVDPT2Ne"));
    ret.add(Pair.of("QcKVFXa05E", "QFB3Mqw2KJ"));
    ret.add(Pair.of("QFB32PQ9rd", "Q83TMkV05S"));
    ret.add(Pair.of("Q83UnBp30A", "RBrXXkVJBL"));
    ret.add(Pair.of("RBrYm0AC96", "R4870r336B"));
    ret.add(Pair.of("R488My107S", "SxCih399Yv"));
    ret.add(Pair.of("SxCipyJC3n", "S0jzk7SFlM"));
    ret.add(Pair.of("S0jzm2z36B", "TtRSWyf15A"));
    ret.add(Pair.of("TtRS0wlG0n", "TXKp7liC5M"));
    ret.add(Pair.of("TXKtg0bBVn", "0djP4kEAcg"));
    ret.add(Pair.of("0djQxHt37S", "0Ge0vfF197"));
    ret.add(Pair.of("0Ge0DJXJFy", "08XoSEWNqU"));
    ret.add(Pair.of("08Xo2K53sQ", "1BYTJb73hG"));
    ret.add(Pair.of("1BYTJjN5EC", "15OgOoZ6SD"));
    ret.add(Pair.of("15OhdOz6d2", "2yFB33GA15"));
    ret.add(Pair.of("2yFCacWJMy", "21rPkqf30A"));
    ret.add(Pair.of("21rPpBI1aS", "3wzyYQl6WR"));
    ret.add(Pair.of("3wzzPRUKyJ", "3ZsiOqFBF1"));
    ret.add(Pair.of("3ZsjokV85e", "4tmKnLH2ba"));
    ret.add(Pair.of("4tmKLAr2jb", "4VT2BYd2ES"));
    ret.add(Pair.of("4VT2ODA6qq", "5pTCy4wH4s"));
    ret.add(Pair.of("5pTCWu210S", "5SqKK2y8rm"));
    ret.add(Pair.of("5SqKYdG8SW", "6mCxexPBgV"));
    ret.add(Pair.of("6mCxwTJAns", "6QuSRGwBV9"));
    ret.add(Pair.of("6QuSTPD8YT", "7iYSYMJ6CQ"));
    ret.add(Pair.of("7iY0gHx38A", "7MYzb2P34A"));
    ret.add(Pair.of("7MYApBc6Au", "8fIUbJnCpS"));
    ret.add(Pair.of("8fIUKfTEaC", "8ITlUK01B5"));
    ret.add(Pair.of("8ITl2U8EUM", "9cpBLqaCEA"));
    ret.add(Pair.of("9cpBZdL7GE", "9GeCyfZ1kS"));
    ret.add(Pair.of("9GeCz4L4fX", "9999999999"));

    return ret;
  }

  /**
   * Batch job entry point.
   * 
   * @param args command line arguments
   */
  public static void main(String... args) {
    LOGGER.info("Run Client indexer job");
    try {
      runJob(ClientIndexerJob.class, args);
    } catch (JobsException e) {
      LOGGER.fatal("STOPPING BATCH: " + e.getMessage(), e);
      throw e;
    }
  }

}
