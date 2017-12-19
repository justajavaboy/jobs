package gov.ca.cwds.jobs.cals.jobgeneric.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.cals.jobgeneric.data.persistence.cms.EsClientAddress;
import org.hibernate.SessionFactory;

/**
 * Hibernate DAO for DB2 {@link EsClientAddress}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedClientAddressDao extends BaseDaoImpl<EsClientAddress>
    implements BatchBucketDao<EsClientAddress> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedClientAddressDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
