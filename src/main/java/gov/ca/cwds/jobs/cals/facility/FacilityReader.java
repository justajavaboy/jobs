package gov.ca.cwds.jobs.cals.facility;

import com.google.inject.Inject;
import gov.ca.cwds.cals.inject.FasSessionFactory;
import gov.ca.cwds.cals.inject.LisSessionFactory;
import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.cals.service.dto.changed.ChangedFacilityDTO;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.util.JobReader;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * @author TPT-2 team
 */
public class FacilityReader implements JobReader<ChangedFacilityDTO> {

  private IncrementalLoadDateStrategy incrementalLoadDateStrategy;
  private IncrementalLoadDateStrategy lisIncrementalLoadDateStrategy;
  private Iterator<ChangedFacilityDTO> facilityDTOIterator;

  @Inject
  private ChangedFacilityService changedFacilityService;

  @Inject
  @FasSessionFactory
  private SessionFactory fasSessionFactory;

  @Inject
  @LisSessionFactory
  private SessionFactory lisSessionFactory;

  @Inject
  @CmsSessionFactory
  private SessionFactory cwsCmcSessionFactory;

  FacilityReader() {
    this.incrementalLoadDateStrategy = new FacilityIncrementalLoadDateStrategy();
    this.lisIncrementalLoadDateStrategy = new LISFacilityIncrementalLoadDateStrategy();
  }

  @Override
  public void init() {
    Date dateAfter = incrementalLoadDateStrategy.calculateDate();
    Date lisDateAfter = lisIncrementalLoadDateStrategy.calculateDate();
    fasSessionFactory.getCurrentSession().beginTransaction();
    lisSessionFactory.getCurrentSession().beginTransaction();
    cwsCmcSessionFactory.getCurrentSession().beginTransaction();
    facilityDTOIterator = changedFacilityService.changedFacilitiesStream(dateAfter, lisDateAfter)
        .iterator();
  }

  @Override
  public ChangedFacilityDTO read() throws Exception {
    return facilityDTOIterator.hasNext() ? facilityDTOIterator.next() : null;
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
