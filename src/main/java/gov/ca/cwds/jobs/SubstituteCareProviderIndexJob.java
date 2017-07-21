package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedSubstituteCareProviderR1Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.CmsReplicationOperation;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProviderR1;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.inject.LastRunFile;

/**
 * Job to load Substitute Care Providers from CMS into ElasticSearch.
 * 
 * @author CWDS API Team
 */
public class SubstituteCareProviderIndexJob
    extends BasePersonIndexerJob<ReplicatedSubstituteCareProviderR1> {

  private static final Logger LOGGER = LogManager.getLogger(SubstituteCareProviderIndexJob.class);

  /**
   * Construct batch job instance with all required dependencies.
   * 
   * @param substituteCareProviderDao Client DAO
   * @param elasticsearchDao ElasticSearch DAO
   * @param lastJobRunTimeFilename last run date in format yyyy-MM-dd HH:mm:ss
   * @param mapper Jackson ObjectMapper
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public SubstituteCareProviderIndexJob(
      final ReplicatedSubstituteCareProviderR1Dao substituteCareProviderDao,
      final ElasticsearchDao elasticsearchDao, @LastRunFile final String lastJobRunTimeFilename,
      final ObjectMapper mapper, @CmsSessionFactory SessionFactory sessionFactory) {
    super(substituteCareProviderDao, elasticsearchDao, lastJobRunTimeFilename, mapper,
        sessionFactory);
  }

  @Override
  protected boolean isDelete(ReplicatedSubstituteCareProviderR1 t) {
    return t.getReplicationOperation() == CmsReplicationOperation.D;
  }

  @Override
  protected List<Pair<String, String>> getPartitionRanges() {
    List<Pair<String, String>> ret = new ArrayList<>();

    ret.add(Pair.of("aaaaaaaaaa", "B1ZHOmR4kl"));
    ret.add(Pair.of("B1ZK0kM9bB", "DT1Uln810S"));
    ret.add(Pair.of("DT18kLg2dI", "FKdJBTUBlB"));
    ret.add(Pair.of("FKdYk0PJnD", "HC7ouvJ33A"));
    ret.add(Pair.of("HC7BmS8Cye", "JvLGIqJ3Ku"));
    ret.add(Pair.of("JvLHjYC3Ku", "LpWX8cr197"));
    ret.add(Pair.of("LpW5mzqHEL", "NiVfgSA2LC"));
    ret.add(Pair.of("NiVfoFH3V3", "Pbljvkn30A"));
    ret.add(Pair.of("Pblkjtzq38", "Q5QnUX11sG"));
    ret.add(Pair.of("Q5QDTr58fU", "SWELBjN41S"));
    ret.add(Pair.of("SWE3NHfFNj", "0CRee3d1sG"));
    ret.add(Pair.of("0CRtfDf5W3", "2vKHMKP197"));
    ret.add(Pair.of("2vKPbCN2qZ", "4rg2m6z37S"));
    ret.add(Pair.of("4rhjjT65vr", "6lmpC97FuD"));
    ret.add(Pair.of("6lmpVGEKl8", "8fyoWSIDAP"));
    ret.add(Pair.of("8fyCjtkOOI", "9999999999"));

    return ret;
  }

  /**
   * Batch job entry point.
   * 
   * @param args command line arguments
   */
  public static void main(String... args) {
    LOGGER.info("Run Substitute Care Provider indexer job");
    try {
      runJob(SubstituteCareProviderIndexJob.class, args);
    } catch (JobsException e) {
      LOGGER.error("STOPPING BATCH: " + e.getMessage(), e);
      throw e;
    }
  }

}
