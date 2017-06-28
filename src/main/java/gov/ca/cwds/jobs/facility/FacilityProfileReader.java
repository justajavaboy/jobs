package gov.ca.cwds.jobs.facility;

import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.cals.service.dto.rs.ReplicatedFacilityCompositeDTO;
import gov.ca.cwds.jobs.util.IncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.util.JobReader;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * @author TPT-2 team
 */
public class FacilityProfileReader implements JobReader<ReplicatedFacilityCompositeDTO> {

  private Iterator<ReplicatedFacilityCompositeDTO> facilityDTOIterator;
  private SessionFactory sessionFactory;
  private ChangedFacilityService changedFacilityService;
  private IncrementalLoadDateStrategy incrementalLoadDateStrategy;

  public FacilityProfileReader(SessionFactory sessionFactory,
      ChangedFacilityService changedFacilityService) {
    this.incrementalLoadDateStrategy = new FacilityProfileIncrementalLoadDate();
    this.sessionFactory = sessionFactory;
    this.changedFacilityService = changedFacilityService;
  }

  public void init() {
    sessionFactory.getCurrentSession().beginTransaction();
    Date dateAfter = incrementalLoadDateStrategy.calculate();
    facilityDTOIterator = changedFacilityService.changedFacilitiesStream(dateAfter).iterator();
  }

  @Override
  public ReplicatedFacilityCompositeDTO read() throws Exception {
    if (facilityDTOIterator.hasNext()) {
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
