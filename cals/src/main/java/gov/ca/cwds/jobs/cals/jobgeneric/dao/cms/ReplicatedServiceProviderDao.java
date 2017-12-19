package gov.ca.cwds.jobs.cals.jobgeneric.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.cals.jobgeneric.data.persistence.cms.rep.ReplicatedServiceProvider;
import gov.ca.cwds.jobs.cals.jobgeneric.data.persistence.cms.rep.ReplicatedSubstituteCareProvider;
import org.hibernate.SessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedSubstituteCareProvider}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedServiceProviderDao extends BaseDaoImpl<ReplicatedServiceProvider>
    implements BatchBucketDao<ReplicatedServiceProvider> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedServiceProviderDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
