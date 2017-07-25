package gov.ca.cwds.dao.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.rep.ReplicatedServiceProviderR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProvider;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedSubstituteCareProvider}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedServiceProviderR1Dao extends BatchDaoImpl<ReplicatedServiceProviderR1>
    implements BatchBucketDao<ReplicatedServiceProviderR1> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedServiceProviderR1Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
