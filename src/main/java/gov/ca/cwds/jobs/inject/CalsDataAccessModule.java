package gov.ca.cwds.jobs.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import gov.ca.cwds.cals.inject.CalsnsSessionFactory;
import gov.ca.cwds.cals.inject.FasSessionFactory;
import gov.ca.cwds.cals.inject.LisSessionFactory;
import gov.ca.cwds.cals.persistence.dao.calsns.AgeGroupTypeDao;
import gov.ca.cwds.cals.persistence.dao.cms.IClientDao;
import gov.ca.cwds.cals.persistence.dao.cms.IPlacementHomeDao;
import gov.ca.cwds.cals.persistence.dao.cms.rs.ReplicatedClientDAO;
import gov.ca.cwds.cals.persistence.dao.cms.rs.ReplicatedPlacementHomeDao;
import gov.ca.cwds.cals.persistence.model.calsns.AgeGroupType;
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
import gov.ca.cwds.cals.persistence.model.fas.ComplaintReportLic802;
import gov.ca.cwds.cals.persistence.model.fas.Rr809Dn;
import gov.ca.cwds.cals.persistence.model.fas.Rrcpoc;
import gov.ca.cwds.cals.persistence.model.fas.LpaInformation;
import gov.ca.cwds.cals.persistence.model.cms.County;
import gov.ca.cwds.cals.persistence.model.lisfas.FacilityStatusType;
import gov.ca.cwds.cals.persistence.model.cms.FacilityType;
import gov.ca.cwds.cals.persistence.model.lisfas.LisDoFile;
import gov.ca.cwds.cals.persistence.model.lisfas.LisFacFile;
import gov.ca.cwds.cals.persistence.model.lisfas.LisTableFile;
import gov.ca.cwds.cals.persistence.model.lisfas.VisitReasonType;
import gov.ca.cwds.cals.persistence.dao.cms.CountiesDao;
import gov.ca.cwds.cals.persistence.dao.fas.ComplaintReportLic802Dao;
import gov.ca.cwds.cals.persistence.dao.lis.FacilityTypeDao;
import gov.ca.cwds.cals.persistence.dao.fas.LpaInformationDao;
import gov.ca.cwds.cals.persistence.dao.fas.InspectionDao;
import gov.ca.cwds.inject.CmsSessionFactory;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static gov.ca.cwds.cals.Constants.UnitOfWork.CMS;

/**
 * @author CWDS TPT-2
 */
public class CalsDataAccessModule extends AbstractModule {
  private SessionFactory cmsSessionFactory;

  private SessionFactory fasSessionFactory;

  private SessionFactory lisSessionFactory;

  private SessionFactory calsnsSessionFactory;

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

    fasSessionFactory = new Configuration().configure("fas-hibernate.cfg.xml")
        .addAnnotatedClass(ComplaintReportLic802.class)
        .addAnnotatedClass(LpaInformation.class)
        .addAnnotatedClass(Rrcpoc.class)
        .addAnnotatedClass(Rr809Dn.class)
        .buildSessionFactory();

    lisSessionFactory = new Configuration().configure("lis-hibernate.cfg.xml")
        .addAnnotatedClass(LisFacFile.class)
        .addAnnotatedClass(LisTableFile.class)
        .addAnnotatedClass(gov.ca.cwds.cals.persistence.model.lisfas.FacilityType.class)
        .addAnnotatedClass(LisDoFile.class)
        .addAnnotatedClass(FacilityStatusType.class)
        .addAnnotatedClass(VisitReasonType.class)
        .addAnnotatedClass(gov.ca.cwds.cals.persistence.model.lisfas.County.class)
        .buildSessionFactory();

    // todo jobs may never need this at all, CALS API should be able to provide services even if this session factory is not bound
    calsnsSessionFactory = new Configuration().configure("jobs-ns-hibernate.cfg.xml")
        .addAnnotatedClass(AgeGroupType.class)
        .buildSessionFactory();
  }

  @Override
  protected void configure() {
    // todo one job may not need them all, CALS API has to provide more granular Data Access
    bind(SessionFactory.class).annotatedWith(CmsSessionFactory.class).toInstance(cmsSessionFactory);
    bind(SessionFactory.class).annotatedWith(FasSessionFactory.class).toInstance(fasSessionFactory);
    bind(SessionFactory.class).annotatedWith(LisSessionFactory.class).toInstance(lisSessionFactory);
    bind(SessionFactory.class).annotatedWith(CalsnsSessionFactory.class).toInstance(calsnsSessionFactory);

    // schema: cwscms
    bind(CountiesDao.class);
    bind(IClientDao.class).to(ReplicatedClientDAO.class);
    bind(IPlacementHomeDao.class).to(ReplicatedPlacementHomeDao.class);

    bind(ComplaintReportLic802Dao.class);
    bind(FacilityTypeDao.class);
    bind(LpaInformationDao.class);
    bind(InspectionDao.class);

    // schema: calsnc
    bind(AgeGroupTypeDao.class);
  }

  public SessionFactory getCmsSessionFactory() {
    return cmsSessionFactory;
  }

  @Provides
  UnitOfWorkAwareProxyFactory cmsUnitOfWorkAwareProxyFactory() {
    return new UnitOfWorkAwareProxyFactory(CMS, cmsSessionFactory);
  }
}
