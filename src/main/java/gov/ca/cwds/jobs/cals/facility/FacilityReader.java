package gov.ca.cwds.jobs.cals.facility;

import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.cals.service.dto.changed.ChangedFacilityDTO;
import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.util.JobReader;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * @author TPT-2 team
 */
public class FacilityReader implements JobReader<ChangedFacilityDTO> {

  private Iterator<ChangedFacilityDTO> facilityDTOIterator;
  private SessionFactory fasSessionFactory;
  private SessionFactory lisSessionFactory;
  private SessionFactory cwsCmcSessionFactory;
  private ChangedFacilityService changedFacilityService;
  private IncrementalLoadDateStrategy incrementalLoadDateStrategy;
  private IncrementalLoadDateStrategy lisIncrementalLoadDateStrategy;

  FacilityReader(SessionFactory fasSessionFactory, SessionFactory lisSessionFactory,
      SessionFactory cwsCmcSessionFactory, ChangedFacilityService changedFacilityService) {
    this.incrementalLoadDateStrategy = new FacilityIncrementalLoadDateStrategy();
    this.lisIncrementalLoadDateStrategy = new LISFacilityIncrementalLoadDateStrategy();
    this.fasSessionFactory = fasSessionFactory;
    this.lisSessionFactory = lisSessionFactory;
    this.cwsCmcSessionFactory = cwsCmcSessionFactory;
    this.changedFacilityService = changedFacilityService;
  }

  @Override
  public void init() {
    Date dateAfter = incrementalLoadDateStrategy.calculate();
    Date lisDateAfter = lisIncrementalLoadDateStrategy.calculate();
    fasSessionFactory.getCurrentSession().beginTransaction();
    lisSessionFactory.getCurrentSession().beginTransaction();
    cwsCmcSessionFactory.getCurrentSession().beginTransaction();
    facilityDTOIterator = changedFacilityService.changedFacilitiesStream(dateAfter, lisDateAfter)
        .iterator();
  }

  @Override
  public ChangedFacilityDTO read() throws Exception {
    if (facilityDTOIterator.hasNext()) {
      return facilityDTOIterator.next();
    } else {
      return null;
    }
  }

  @Override
  public void destroy() throws Exception {
    try {
      closeSessionFactory(fasSessionFactory);
    } finally {
      try {
        closeSessionFactory(lisSessionFactory);
      } finally {
        closeSessionFactory(cwsCmcSessionFactory);
      }
    }
  }

  private void closeSessionFactory(SessionFactory sessionFactory) {
    try {
      sessionFactory.getCurrentSession().getTransaction().rollback();
    } finally {
      sessionFactory.close();
    }
  }
}
