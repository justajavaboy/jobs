package gov.ca.cwds.dao.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherChildInPlacemtHomeR1;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedOtherChildInPlacemtHomeR1}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedOtherChildInPlacemtHomeR1Dao
    extends BaseDaoImpl<ReplicatedOtherChildInPlacemtHomeR1>
    implements BatchBucketDao<ReplicatedOtherChildInPlacemtHomeR1> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedOtherChildInPlacemtHomeR1Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
