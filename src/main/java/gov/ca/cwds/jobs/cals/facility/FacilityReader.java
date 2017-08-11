package gov.ca.cwds.jobs.cals.facility;

import com.google.inject.Inject;
import gov.ca.cwds.cals.inject.FasSessionFactory;
import gov.ca.cwds.cals.inject.LisSessionFactory;
import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.cals.service.dto.changed.ChangedFacilityDTO;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.util.JobReader;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * @author CWDS TPT-2
 */
public class FacilityReader implements JobReader<ChangedFacilityDTO> {

  private Iterator<ChangedFacilityDTO> facilityDTOIterator;

  @Inject
  private FacilityIncrementalLoadDateStrategy incrementalLoadDateStrategy;

  @Inject
  private LISFacilityIncrementalLoadDateStrategy lisIncrementalLoadDateStrategy;

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
  public ChangedFacilityDTO read() {
    return facilityDTOIterator.hasNext() ? facilityDTOIterator.next() : null;
  }

  @Override
  public void destroy() {
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