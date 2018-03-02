package gov.ca.cwds.dao.cms;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.neutron.enums.NeutronIntegerDefaults;

/**
 * Hibernate DAO for DB2 {@link StaffPerson}.
 * 
 * @author CWDS API Team
 */
public class StaffPersonDao extends BaseDaoImpl<StaffPerson> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public StaffPersonDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.BaseDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<StaffPerson> findAll() {
    final String namedQueryName = getEntityClass().getName() + ".findAll";
    final Session session = getSessionFactory().getCurrentSession();
    Transaction txn = session.getTransaction();

    try {
      txn = txn != null ? txn : session.beginTransaction();
      if (TransactionStatus.NOT_ACTIVE == txn.getStatus() || !txn.isActive()) {
        txn.begin();
      }

      session.clear();
      final Query<StaffPerson> query = session.getNamedQuery(namedQueryName).setCacheable(false)
          .setFlushMode(FlushMode.MANUAL).setReadOnly(true)
          // .setCacheMode(CacheMode.IGNORE)
          .setFetchSize(NeutronIntegerDefaults.FETCH_SIZE.getValue());
      final ImmutableList.Builder<StaffPerson> entities = new ImmutableList.Builder<>();
      entities.addAll(query.list());
      session.flush();
      txn.commit();
      return entities.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      final String message = h.getMessage() + ". Transaction Status: "
          + (txn != null ? txn.getStatus().toString() : "unknown");
      throw new DaoException(message, h);
    }
  }

}
