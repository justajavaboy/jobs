package gov.ca.cwds.jobs.cals.jobgeneric.dao.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.std.BatchBucketDao;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.jobs.cals.jobgeneric.data.persistence.ns.IntakeParticipant;
import org.hibernate.SessionFactory;

/**
 * Hibernate DAO for DB2 {@link IntakeParticipant}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class IntakeParticipantDao extends BaseDaoImpl<IntakeParticipant>
    implements BatchBucketDao<IntakeParticipant> {

  /**
   * Constructor
   * 
   * @param sessionFactory The PostgreSQL sessionFactory
   */
  @Inject
  public IntakeParticipantDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
