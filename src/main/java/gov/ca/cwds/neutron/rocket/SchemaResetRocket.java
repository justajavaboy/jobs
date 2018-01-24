package gov.ca.cwds.neutron.rocket;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.dao.cms.ReplicatedOtherAdultInPlacemtHomeDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHome;
import gov.ca.cwds.neutron.flight.FlightPlan;
import gov.ca.cwds.neutron.jetpack.ConditionalLogger;
import gov.ca.cwds.neutron.jetpack.JetPackLogger;
import gov.ca.cwds.neutron.jetpack.JobLogs;

/**
 * Refreshes a <strong>TEST</strong> transactional schema and its companion replicated schema.
 * 
 * @author CWDS API Team
 */
public class SchemaResetRocket
    extends BasePersonRocket<ReplicatedOtherAdultInPlacemtHome, ReplicatedOtherAdultInPlacemtHome> {

  private static final long serialVersionUID = 1L;

  private static final ConditionalLogger LOGGER = new JetPackLogger(SchemaResetRocket.class);

  /**
   * Construct rocket with all required dependencies.
   * 
   * @param dao arbitrary DAO to fulfill interface
   * @param esDao ElasticSearch DAO for the target index
   * @param mapper Jackson ObjectMapper
   * @param flightPlan command line options
   */
  @Inject
  public SchemaResetRocket(final ReplicatedOtherAdultInPlacemtHomeDao dao,
      final ElasticsearchDao esDao, final ObjectMapper mapper, FlightPlan flightPlan) {
    super(dao, esDao, flightPlan.getLastRunLoc(), mapper, flightPlan);
  }

  @Override
  public Date launch(Date lastRunDate) {
    LOGGER.warn("REFRESH TEST SCHEMA!!!");

    try {

    } catch (Exception e) {
      JobLogs.checked(LOGGER, e, "SCHEMA REFRESH ERROR!! {}", e.getMessage());
    }

    return lastRunDate;
  }

}
