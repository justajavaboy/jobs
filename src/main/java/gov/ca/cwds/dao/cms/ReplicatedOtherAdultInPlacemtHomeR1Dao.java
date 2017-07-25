package gov.ca.cwds.dao.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHomeR1;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedOtherAdultInPlacemtHomeR1}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedOtherAdultInPlacemtHomeR1Dao
    extends BatchDaoImpl<ReplicatedOtherAdultInPlacemtHomeR1>
    implements BatchBucketDao<ReplicatedOtherAdultInPlacemtHomeR1> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedOtherAdultInPlacemtHomeR1Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
