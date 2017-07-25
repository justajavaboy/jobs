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
    ret.add(Pair.of("aaaaaaaaaa", "AnXtfCm3hy"));
    ret.add(Pair.of("AnXtgOr6Y7", "ACXHGEw0P3"));
    ret.add(Pair.of("ACXHSMQ8kF", "ARKPAq937S"));
    ret.add(Pair.of("ARKPM5x01S", "A6nGexi9k6"));
    ret.add(Pair.of("A6nGfYt7PN", "BkehRS75om"));
    ret.add(Pair.of("BkehSz82Mh", "By4f9P9ICJ"));
    ret.add(Pair.of("By4ge6hIE6", "BNXmRRD5Dg"));
    ret.add(Pair.of("BNXm5es9mo", "B2Q96606CQ"));
    ret.add(Pair.of("B2Q98hGIE6", "ChnTjj72Pz"));
    ret.add(Pair.of("ChnTniz3J0", "CvpprL87GE"));
    ret.add(Pair.of("CvppCeI5Aj", "CJV8uHXFlU"));
    ret.add(Pair.of("CJV8NmPA6p", "CYZcUsW6xC"));
    ret.add(Pair.of("CYZcXLA8kF", "De13bRNCJ8"));
    ret.add(Pair.of("De13tYN03d", "DsAfKkN5DO"));
    ret.add(Pair.of("DsAfMoN3t4", "DHrB6kU5DQ"));
    ret.add(Pair.of("DHrCwtO199", "DWElds941S"));
    ret.add(Pair.of("DWElVDWBbF", "EaMhS7u3qR"));
    ret.add(Pair.of("EaMhTOL3TL", "EpBFy4b2vI"));
    ret.add(Pair.of("EpBFV6gBt6", "EExQXU59GX"));
    ret.add(Pair.of("EExQ2iZCG1", "ESx1zaWClV"));
    ret.add(Pair.of("ESx186b15A", "E7tkbHG10S"));
    ret.add(Pair.of("E7tkJd337S", "FmoQRKPBuJ"));
    ret.add(Pair.of("FmoQWK08o7", "FAcY5V2Bjh"));
    ret.add(Pair.of("FAcZCcr01S", "FNJLm5L6mW"));
    ret.add(Pair.of("FNJLrPc9gO", "F2EE4Dz06Q"));
    ret.add(Pair.of("F2EFiJq6eP", "GiQjAhYBXe"));
    ret.add(Pair.of("GiQjBQv5Du", "GxT9IvqICJ"));
    ret.add(Pair.of("GxT92oe2LR", "GMxhmw01nH"));
    ret.add(Pair.of("GMxhtrS87C", "G0goGrx36B"));
    ret.add(Pair.of("G0go5HeALa", "He0TJNh36B"));
    ret.add(Pair.of("He0T3T8KeQ", "Ht9WDoX40S"));
    ret.add(Pair.of("Ht93Gwz30A", "HIWv4Gy63O"));
    ret.add(Pair.of("HIWv7P28p7", "HW536v88J7"));
    ret.add(Pair.of("HW537mk2LP", "IbjHPWZ92i"));
    ret.add(Pair.of("IbjIdRiLZ2", "IqsqvBbMqI"));
    ret.add(Pair.of("Iqsrg8OD8z", "ID3JRxzFcZ"));
    ret.add(Pair.of("ID3J1vX8kK", "ISI0JOb54S"));
    ret.add(Pair.of("ISI0NOeKnx", "I6zIR00Ans"));
    ret.add(Pair.of("I6zJX7p43S", "JmzlUiqG4t"));
    ret.add(Pair.of("JmzmqIq74v", "JBrlCjJ5Le"));
    ret.add(Pair.of("JBrlDjG42v", "JQmZccj36B"));
    ret.add(Pair.of("JQmZTu1Gzt", "J481cbgIw6"));
    ret.add(Pair.of("J481tac7HX", "KiSMEAj5Fb"));
    ret.add(Pair.of("KiSNc5G6eS", "KxPVlMbBV2"));
    ret.add(Pair.of("KxPVEKa1qa", "KMI3Cb7Cwr"));
    ret.add(Pair.of("KMI3F3Z33A", "K1qJC2x6VR"));
    ret.add(Pair.of("K1qJGAvGI0", "Lfe5XpTIzg"));
    ret.add(Pair.of("Lfe6AilGLZ", "LublVK5Klf"));
    ret.add(Pair.of("Lubl5kWJeV", "LIB5uvh37S"));
    ret.add(Pair.of("LIB5C6r36B", "LXgzabJILP"));
    ret.add(Pair.of("LXgzvJB04F", "MbAkSzdDTu"));
    ret.add(Pair.of("MbAlplx6eV", "Mqo1AwO4nn"));
    ret.add(Pair.of("Mqo1AXnIg3", "MFau9LHDjK"));
    ret.add(Pair.of("MFavnu6GLi", "MT0uJ8IFns"));
    ret.add(Pair.of("MT0uYVt01S", "M8XkkZO5Df"));
    ret.add(Pair.of("M8XkuYsCQd", "NnfXRK3CR3"));
    ret.add(Pair.of("NnfXYiLDGM", "NB3nZaM5DB"));
    ret.add(Pair.of("NB3ol77EyI", "NQS86QBC1F"));
    ret.add(Pair.of("NQS9eaVD1N", "N46d9Jy5Q0"));
    ret.add(Pair.of("N46ectX9ip", "OiELUDSLY3"));
    ret.add(Pair.of("OiELW4n43S", "OxrKmcV2dT"));
    ret.add(Pair.of("OxrKokn2hs", "OMyg32f30A"));
    ret.add(Pair.of("OMymd9N30A", "O1qxih67VF"));
    ret.add(Pair.of("O1qx0TM1X9", "Pe2DPbJ9Bz"));
    ret.add(Pair.of("Pe2DPCg8by", "PtFR1rU8ZL"));
    ret.add(Pair.of("PtFR9eL54S", "PHOY4W58iJ"));
    ret.add(Pair.of("PHOZbPu5Cy", "PWPIrQI94v"));
    ret.add(Pair.of("PWPIx5C6cS", "QcKVDPT2Ne"));
    ret.add(Pair.of("QcKVFXa05E", "QqDzEkbEHJ"));
    ret.add(Pair.of("QqDzR0M5O4", "QFB3Mqw2KJ"));
    ret.add(Pair.of("QFB32PQ9rd", "QUqEGdV43S"));
    ret.add(Pair.of("QUqKkwz34A", "Q83TMkV05S"));
    ret.add(Pair.of("Q83UnBp30A", "RmRV7FzIQg"));
    ret.add(Pair.of("RmRWfYH37S", "RBrXXkVJBL"));
    ret.add(Pair.of("RBrYm0AC96", "RQihaas7BU"));
    ret.add(Pair.of("RQihAHp23F", "R4870r336B"));
    ret.add(Pair.of("R488My107S", "SjyhTHG6cS"));
    ret.add(Pair.of("SjyigOgz19", "SxCih399Yv"));
    ret.add(Pair.of("SxCipyJC3n", "SLfj2eg7eS"));
    ret.add(Pair.of("SLfkfHgC08", "S0jzk7SFlM"));
    ret.add(Pair.of("S0jzm2z36B", "Tge1VUsI9k"));
    ret.add(Pair.of("Tge2nc7GaQ", "TtRSWyf15A"));
    ret.add(Pair.of("TtRS0wlG0n", "TIEHUuGEyI"));
    ret.add(Pair.of("TIEIYEmDpj", "TXKp7liC5M"));
    ret.add(Pair.of("TXKtg0bBVn", "UCscc2U2NA"));
    ret.add(Pair.of("UCsceyD6Gt", "0djP4kEAcg"));
    ret.add(Pair.of("0djQxHt37S", "0rmDvIn5yN"));
    ret.add(Pair.of("0rmDxMx90Z", "0Ge0vfF197"));
    ret.add(Pair.of("0Ge0DJXJFy", "0VaO2Rz2k5"));
    ret.add(Pair.of("0VaPfPO3RZ", "08XoSEWNqU"));
    ret.add(Pair.of("08Xo2K53sQ", "1mVSIqu2PY"));
    ret.add(Pair.of("1mVSOBD5om", "1BYTJb73hG"));
    ret.add(Pair.of("1BYTJjN5EC", "1QLP3qr01S"));
    ret.add(Pair.of("1QLQJu99fn", "15OgOoZ6SD"));
    ret.add(Pair.of("15OhdOz6d2", "2kTP0df4l6"));
    ret.add(Pair.of("2kTP5Qx5xB", "2yFB33GA15"));
    ret.add(Pair.of("2yFCacWJMy", "2MjG0Yb8ZL"));
    ret.add(Pair.of("2MjHG8v5eq", "21rPkqf30A"));
    ret.add(Pair.of("21rPpBI1aS", "3hxHgLR43S"));
    ret.add(Pair.of("3hxHFAn30A", "3wzyYQl6WR"));
    ret.add(Pair.of("3wzzPRUKyJ", "3LneS4937S"));
    ret.add(Pair.of("3Lne0QiAzW", "3ZsiOqFBF1"));
    ret.add(Pair.of("3ZsjokV85e", "4ekgQcvB3n"));
    ret.add(Pair.of("4ekgUkw947", "4tmKnLH2ba"));
    ret.add(Pair.of("4tmKLAr2jb", "4IlvUOxBm3"));
    ret.add(Pair.of("4Ilv02ADFH", "4VT2BYd2ES"));
    ret.add(Pair.of("4VT2ODA6qq", "5aRj0t730A"));
    ret.add(Pair.of("5aRlWWf34A", "5pTCy4wH4s"));
    ret.add(Pair.of("5pTCWu210S", "5DIy3pW8r0"));
    ret.add(Pair.of("5DIy4xh996", "5SqKK2y8rm"));
    ret.add(Pair.of("5SqKYdG8SW", "56gqiY05Ch"));
    ret.add(Pair.of("56gqynZF82", "6mCxexPBgV"));
    ret.add(Pair.of("6mCxwTJAns", "6BJzY624zP"));
    ret.add(Pair.of("6BJzZd2Bs6", "6QuSRGwBV9"));
    ret.add(Pair.of("6QuSTPD8YT", "65jPX4FMnm"));
    ret.add(Pair.of("65jP6IKFE8", "7iYSYMJ6CQ"));
    ret.add(Pair.of("7iY0gHx38A", "7xTlTkMByp"));
    ret.add(Pair.of("7xTlZ0lHWq", "7MYzb2P34A"));
    ret.add(Pair.of("7MYApBc6Au", "710xQoL2kE"));
    ret.add(Pair.of("710xSsR78v", "8fIUbJnCpS"));
    ret.add(Pair.of("8fIUKfTEaC", "8uGOtx9F3Q"));
    ret.add(Pair.of("8uGOBfK4c8", "8ITlUK01B5"));
    ret.add(Pair.of("8ITl2U8EUM", "8XQbBvYKFN"));
    ret.add(Pair.of("8XQbMNg0Yt", "9cpBLqaCEA"));
    ret.add(Pair.of("9cpBZdL7GE", "9rdUeqSCrd"));
    ret.add(Pair.of("9rdUpIY6CQ", "9GeCyfZ1kS"));
    ret.add(Pair.of("9GeCz4L4fX", "9VbzMLh30A"));
    ret.add(Pair.of("9VbADFh30A", "9999999999"));

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
