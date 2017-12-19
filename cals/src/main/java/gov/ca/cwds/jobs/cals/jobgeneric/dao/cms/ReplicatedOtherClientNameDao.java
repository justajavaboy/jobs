package gov.ca.cwds.jobs.cals.jobgeneric.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.jobs.cals.jobgeneric.data.persistence.cms.rep.ReplicatedOtherClientName;
import org.hibernate.SessionFactory;

/**
 * Hibernate DAO for DB2 {@link ReplicatedOtherClientName}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ReplicatedOtherClientNameDao extends BaseDaoImpl<ReplicatedOtherClientName> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReplicatedOtherClientNameDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
