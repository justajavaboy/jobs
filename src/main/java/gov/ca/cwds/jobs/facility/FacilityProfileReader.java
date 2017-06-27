package gov.ca.cwds.jobs.facility;

import gov.ca.cwds.cals.service.ReplicatedFacilityService;
import gov.ca.cwds.cals.service.dto.rs.ReplicatedFacilityCompositeDTO;
import gov.ca.cwds.cals.web.rest.parameter.FacilityParameterObject;
import gov.ca.cwds.jobs.util.IncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.util.JobReader;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by TPT-2 team on 6/13/2017.
 */
public class FacilityProfileReader implements JobReader<ReplicatedFacilityCompositeDTO> {
  private Iterator<ReplicatedFacilityCompositeDTO> facilityDTOIterator;
  private SessionFactory sessionFactory;
  private ReplicatedFacilityService replicatedFacilityService;
  private IncrementalLoadDateStrategy incrementalLoadDateStrategy;

  public FacilityProfileReader(SessionFactory sessionFactory,
                               ReplicatedFacilityService replicatedFacilityService) {
    this.incrementalLoadDateStrategy = new FacilityProfileIncrementalLoadDate();
    this.sessionFactory = sessionFactory;
    this.replicatedFacilityService = replicatedFacilityService;
  }

  public void init() {
    sessionFactory.getCurrentSession().beginTransaction();
    Date dateAfter = incrementalLoadDateStrategy.calculate();
    FacilityParameterObject facilityParameterObject = new FacilityParameterObject(dateAfter);
    facilityDTOIterator = replicatedFacilityService.facilitiesStream(facilityParameterObject).iterator();
  }

  @Override
  public ReplicatedFacilityCompositeDTO read() throws Exception {
    if (facilityDTOIterator.hasNext()){
      return facilityDTOIterator.next();
    } else {
      return null;
    }
  }

  @Override
  public void destroy() throws Exception {
    sessionFactory.getCurrentSession().getTransaction().rollback();
    sessionFactory.close();
  }
}
