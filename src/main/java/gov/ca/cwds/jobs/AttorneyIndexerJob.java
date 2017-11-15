package gov.ca.cwds.jobs;

import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedAttorneyDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedAttorney;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.config.FlightPlan;
import gov.ca.cwds.jobs.schedule.LaunchCommand;
import gov.ca.cwds.neutron.inject.annotation.LastRunFile;
import gov.ca.cwds.neutron.launch.FlightRecorder;
import gov.ca.cwds.neutron.rocket.BasePersonRocket;

/**
 * Rocket to load attorneys from CMS into ElasticSearch.
 * 
 * <p>
 * NOTE: <strong>NOT IN PRODUCTION USE!</strong> The attorneys asked us to not index their personal
 * information in Elasticsearch ...
 * </p>
 * 
 * @author CWDS API Team
 */
public class AttorneyIndexerJob extends BasePersonRocket<ReplicatedAttorney, ReplicatedAttorney> {

  private static final long serialVersionUID = 1L;

  /**
   * Construct batch job instance with all required dependencies.
   * 
   * @param dao ReplicatedAttorney DAO
   * @param esDao ElasticSearch DAO
   * @param lastJobRunTimeFilename last run date in format yyyy-MM-dd HH:mm:ss
   * @param mapper Jackson ObjectMapper
   * @param sessionFactory Hibernate session factory
   * @param jobHistory job history
   * @param opts command line options
   */
  @Inject
  public AttorneyIndexerJob(final ReplicatedAttorneyDao dao, final ElasticsearchDao esDao,
      @LastRunFile final String lastJobRunTimeFilename, final ObjectMapper mapper,
      @CmsSessionFactory SessionFactory sessionFactory, FlightRecorder jobHistory,
      FlightPlan opts) {
    super(dao, esDao, lastJobRunTimeFilename, mapper, sessionFactory, opts);
  }

  /**
   * Batch job entry point.
   * 
   * @param args command line arguments
   * @throws Exception unhandled launch error
   */
  public static void main(String... args) throws Exception {
    LaunchCommand.launchOneWayTrip(AttorneyIndexerJob.class, args);
  }

}
