package gov.ca.cwds.jobs.inject;

import com.google.inject.AbstractModule;
import gov.ca.cwds.cals.persistence.dao.cms.IClientDao;
import gov.ca.cwds.cals.persistence.dao.cms.IPlacementHomeDao;
import gov.ca.cwds.cals.persistence.dao.cms.rs.ReplicatedClientDAO;
import gov.ca.cwds.cals.persistence.dao.cms.rs.ReplicatedPlacementHomeDao;
import gov.ca.cwds.cals.persistence.model.cms.LicenseStatus;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedClient;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedCountyLicenseCase;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedLicensingVisit;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedOutOfHomePlacement;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedPlacementEpisode;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedPlacementHome;
import gov.ca.cwds.cals.persistence.model.cms.State;
import gov.ca.cwds.cals.persistence.model.cms.VisitType;
import gov.ca.cwds.cals.persistence.model.cms.rs.ReplicatedStaffPerson;
import gov.ca.cwds.cals.persistence.model.cms.County;
import gov.ca.cwds.cals.persistence.model.cms.FacilityType;
import gov.ca.cwds.cals.persistence.dao.cms.CountiesDao;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author CWDS TPT-2
 */
public class CalsDataAccessModule extends AbstractModule {
  private SessionFactory cmsSessionFactory;

  public CalsDataAccessModule() {
    cmsSessionFactory = new Configuration().configure("jobs-cms-hibernate.cfg.xml")
        .addAnnotatedClass(ReplicatedClient.class)
        .addAnnotatedClass(ReplicatedOutOfHomePlacement.class)
        .addAnnotatedClass(ReplicatedPlacementEpisode.class)
        .addAnnotatedClass(ReplicatedPlacementHome.class)
        .addAnnotatedClass(ReplicatedCountyLicenseCase.class)
        .addAnnotatedClass(ReplicatedLicensingVisit.class)
        .addAnnotatedClass(ReplicatedStaffPerson.class)
        .addAnnotatedClass(FacilityType.class)
        .addAnnotatedClass(County.class)
        .addAnnotatedClass(VisitType.class)
        .addAnnotatedClass(State.class)
        .addAnnotatedClass(LicenseStatus.class)
        .buildSessionFactory();
  }

  @Override
  protected void configure() {
    bind(SessionFactory.class).annotatedWith(CmsSessionFactory.class).toInstance(cmsSessionFactory);

    // schema: cwscms
    bind(CountiesDao.class);
    bind(IClientDao.class).to(ReplicatedClientDAO.class);
    bind(IPlacementHomeDao.class).to(ReplicatedPlacementHomeDao.class);
  }

  public SessionFactory getCmsSessionFactory() {
    return cmsSessionFactory;
  }
}
