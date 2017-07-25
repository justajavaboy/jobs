package gov.ca.cwds.dao.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherClientNameR1;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedOtherClientNameR1}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedOtherClientNameR1Dao extends BatchDaoImpl<ReplicatedOtherClientNameR1>
    implements BatchBucketDao<ReplicatedOtherClientNameR1> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedOtherClientNameR1Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
