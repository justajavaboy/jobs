package gov.ca.cwds.dao.cms;

import java.util.List;

import org.hibernate.CacheMode;
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

      final Query<StaffPerson> query =
          session.getNamedQuery(namedQueryName).setCacheable(false).setFlushMode(FlushMode.MANUAL)
              .setReadOnly(true).setCacheMode(CacheMode.IGNORE).setFetchSize(5000);
      final ImmutableList.Builder<StaffPerson> entities = new ImmutableList.Builder<>();
      entities.addAll(query.list());
      txn.commit();
      return entities.build();
    } catch (HibernateException h) {
      txn.rollback();
      final String message = h.getMessage() + ". Transaction Status: " + txn.getStatus();
      throw new DaoException(message, h);
    }
  }

}
