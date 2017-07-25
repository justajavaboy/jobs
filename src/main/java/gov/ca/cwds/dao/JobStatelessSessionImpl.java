package gov.ca.cwds.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.jdbc.LobCreationContext.Callback;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.SessionEventListenerManager;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.StatelessSessionImpl;
import org.hibernate.loader.custom.CustomQuery;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.resource.transaction.TransactionCoordinator;
import org.hibernate.resource.transaction.TransactionCoordinatorBuilder;
import org.hibernate.type.descriptor.WrapperOptions;

public class JobStatelessSessionImpl implements StatelessSession {

  private StatelessSessionImpl wrapped;

  public JobStatelessSessionImpl(StatelessSessionImpl wrapped) {
    this.wrapped = wrapped;
  }

  // @Override
  // public boolean equals(Object obj) {
  // return wrapped.equals(obj);
  // }

  public SessionFactoryImplementor getFactory() {
    return wrapped.getFactory();
  }

  public <T> T execute(Callback<T> callback) {
    return wrapped.execute(callback);
  }

  public TransactionCoordinator getTransactionCoordinator() {
    return wrapped.getTransactionCoordinator();
  }

  public JdbcCoordinator getJdbcCoordinator() {
    return wrapped.getJdbcCoordinator();
  }

  public boolean shouldAutoJoinTransaction() {
    return wrapped.shouldAutoJoinTransaction();
  }

  public boolean isClosed() {
    return wrapped.isClosed();
  }

  @Override
  public Serializable insert(Object entity) {
    return wrapped.insert(entity);
  }

  @Override
  public Serializable insert(String entityName, Object entity) {
    return wrapped.insert(entityName, entity);
  }

  public Query createQuery(NamedQueryDefinition namedQueryDefinition) {
    return wrapped.createQuery(namedQueryDefinition);
  }

  public SQLQuery createSQLQuery(NamedSQLQueryDefinition namedQueryDefinition) {
    return wrapped.createSQLQuery(namedQueryDefinition);
  }

  @Override
  public void delete(Object entity) {
    wrapped.delete(entity);
  }

  @Override
  public void delete(String entityName, Object entity) {
    wrapped.delete(entityName, entity);
  }

  @Override
  public Query getNamedQuery(String queryName) throws MappingException {
    return wrapped.getNamedQuery(queryName);
  }

  @Override
  public void update(Object entity) {
    wrapped.update(entity);
  }

  @Override
  public void update(String entityName, Object entity) {
    wrapped.update(entityName, entity);
  }

  public Query getNamedSQLQuery(String queryName) throws MappingException {
    return wrapped.getNamedSQLQuery(queryName);
  }

  @Override
  public Object get(Class entityClass, Serializable id) {
    return wrapped.get(entityClass, id);
  }

  @Override
  public Object get(Class entityClass, Serializable id, LockMode lockMode) {
    return wrapped.get(entityClass, id, lockMode);
  }

  @Override
  public Object get(String entityName, Serializable id) {
    return wrapped.get(entityName, id);
  }

  @Override
  public Object get(String entityName, Serializable id, LockMode lockMode) {
    return wrapped.get(entityName, id, lockMode);
  }

  @Override
  public void refresh(Object entity) {
    wrapped.refresh(entity);
  }

  @Override
  public void refresh(String entityName, Object entity) {
    wrapped.refresh(entityName, entity);
  }

  @Override
  public Query createQuery(String queryString) {
    return wrapped.createQuery(queryString);
  }

  @Override
  public void refresh(Object entity, LockMode lockMode) {
    wrapped.refresh(entity, lockMode);
  }

  @Override
  public void refresh(String entityName, Object entity, LockMode lockMode) {
    wrapped.refresh(entityName, entity, lockMode);
  }

  @Override
  public SQLQuery createSQLQuery(String sql) {
    return wrapped.createSQLQuery(sql);
  }

  @Override
  public ProcedureCall getNamedProcedureCall(String name) {
    return wrapped.getNamedProcedureCall(name);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName) {
    return wrapped.createStoredProcedureCall(procedureName);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
    return wrapped.createStoredProcedureCall(procedureName, resultClasses);
  }

  public Object immediateLoad(String entityName, Serializable id) throws HibernateException {
    return wrapped.immediateLoad(entityName, id);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName,
      String... resultSetMappings) {
    return wrapped.createStoredProcedureCall(procedureName, resultSetMappings);
  }

  public void initializeCollection(PersistentCollection collection, boolean writing)
      throws HibernateException {
    wrapped.initializeCollection(collection, writing);
  }

  public Object instantiate(String entityName, Serializable id) throws HibernateException {
    return wrapped.instantiate(entityName, id);
  }

  public Object internalLoad(String entityName, Serializable id, boolean eager, boolean nullable)
      throws HibernateException {
    return wrapped.internalLoad(entityName, id, eager, nullable);
  }

  @Override
  public Transaction getTransaction() throws HibernateException {
    return wrapped.getTransaction();
  }

  public List list(NativeSQLQuerySpecification spec, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.list(spec, queryParameters);
  }

  public ScrollableResults scroll(NativeSQLQuerySpecification spec, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.scroll(spec, queryParameters);
  }

  public Iterator iterate(String query, QueryParameters queryParameters) throws HibernateException {
    return wrapped.iterate(query, queryParameters);
  }

  public Iterator iterateFilter(Object collection, String filter, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.iterateFilter(collection, filter, queryParameters);
  }

  @Override
  public String getTenantIdentifier() {
    return wrapped.getTenantIdentifier();
  }

  public EntityKey generateEntityKey(Serializable id, EntityPersister persister) {
    return wrapped.generateEntityKey(id, persister);
  }

  public List listFilter(Object collection, String filter, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.listFilter(collection, filter, queryParameters);
  }

  public JdbcConnectionAccess getJdbcConnectionAccess() {
    return wrapped.getJdbcConnectionAccess();
  }

  public boolean isOpen() {
    return wrapped.isOpen();
  }

  @Override
  public void close() {
    wrapped.close();
  }

  public boolean isAutoCloseSessionEnabled() {
    return wrapped.isAutoCloseSessionEnabled();
  }

  public boolean shouldAutoClose() {
    return wrapped.shouldAutoClose();
  }

  public UUID getSessionIdentifier() {
    return wrapped.getSessionIdentifier();
  }

  public SessionEventListenerManager getEventListenerManager() {
    return wrapped.getEventListenerManager();
  }

  public String bestGuessEntityName(Object object) {
    return wrapped.bestGuessEntityName(object);
  }

  @Override
  public Connection connection() {
    return wrapped.connection();
  }

  public int executeUpdate(String query, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.executeUpdate(query, queryParameters);
  }

  public CacheMode getCacheMode() {
    return wrapped.getCacheMode();
  }

  public int getDontFlushFromFind() {
    return wrapped.getDontFlushFromFind();
  }

  public Serializable getContextEntityIdentifier(Object object) {
    return wrapped.getContextEntityIdentifier(object);
  }

  public EntityMode getEntityMode() {
    return wrapped.getEntityMode();
  }

  public EntityPersister getEntityPersister(String entityName, Object object)
      throws HibernateException {
    return wrapped.getEntityPersister(entityName, object);
  }

  public Object getEntityUsingInterceptor(EntityKey key) throws HibernateException {
    return wrapped.getEntityUsingInterceptor(key);
  }

  public FlushMode getFlushMode() {
    return wrapped.getFlushMode();
  }

  public Interceptor getInterceptor() {
    return wrapped.getInterceptor();
  }

  public PersistenceContext getPersistenceContext() {
    return wrapped.getPersistenceContext();
  }

  public long getTimestamp() {
    return wrapped.getTimestamp();
  }

  public String guessEntityName(Object entity) throws HibernateException {
    return wrapped.guessEntityName(entity);
  }

  public boolean isConnected() {
    return wrapped.isConnected();
  }

  public boolean isTransactionInProgress() {
    return wrapped.isTransactionInProgress();
  }

  public void setAutoClear(boolean enabled) {
    wrapped.setAutoClear(enabled);
  }

  public void disableTransactionAutoJoin() {
    wrapped.disableTransactionAutoJoin();
  }

  public void setCacheMode(CacheMode cm) {
    // wrapped.setCacheMode(cm);
  }

  public void setFlushMode(FlushMode fm) {
    wrapped.setFlushMode(fm);
  }

  @Override
  public Transaction beginTransaction() throws HibernateException {
    return wrapped.beginTransaction();
  }

  public boolean isEventSource() {
    return wrapped.isEventSource();
  }

  public boolean isDefaultReadOnly() {
    return wrapped.isDefaultReadOnly();
  }

  public void setDefaultReadOnly(boolean readOnly) throws HibernateException {
    wrapped.setDefaultReadOnly(readOnly);
  }

  public List list(String query, QueryParameters queryParameters) throws HibernateException {
    return wrapped.list(query, queryParameters);
  }

  public void afterOperation(boolean success) {
    wrapped.afterOperation(success);
  }

  @Override
  public Criteria createCriteria(Class persistentClass, String alias) {
    return wrapped.createCriteria(persistentClass, alias);
  }

  @Override
  public Criteria createCriteria(String entityName, String alias) {
    return wrapped.createCriteria(entityName, alias);
  }

  @Override
  public Criteria createCriteria(Class persistentClass) {
    return wrapped.createCriteria(persistentClass);
  }

  @Override
  public Criteria createCriteria(String entityName) {
    return wrapped.createCriteria(entityName);
  }

  public ScrollableResults scroll(Criteria criteria, ScrollMode scrollMode) {
    return wrapped.scroll(criteria, scrollMode);
  }

  public List list(Criteria criteria) throws HibernateException {
    return wrapped.list(criteria);
  }

  public TransactionCoordinatorBuilder getTransactionCoordinatorBuilder() {
    return wrapped.getTransactionCoordinatorBuilder();
  }

  public WrapperOptions getWrapperOptions() {
    return wrapped.getWrapperOptions();
  }

  public List listCustomQuery(CustomQuery customQuery, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.listCustomQuery(customQuery, queryParameters);
  }

  public ScrollableResults scrollCustomQuery(CustomQuery customQuery,
      QueryParameters queryParameters) throws HibernateException {
    return wrapped.scrollCustomQuery(customQuery, queryParameters);
  }

  public ScrollableResults scroll(String query, QueryParameters queryParameters)
      throws HibernateException {
    return wrapped.scroll(query, queryParameters);
  }

  public void afterScrollOperation() {
    wrapped.afterScrollOperation();
  }

  public void flush() {
    wrapped.flush();
  }

  public LoadQueryInfluencers getLoadQueryInfluencers() {
    return wrapped.getLoadQueryInfluencers();
  }

  public int executeNativeUpdate(NativeSQLQuerySpecification nativeSQLQuerySpecification,
      QueryParameters queryParameters) throws HibernateException {
    return wrapped.executeNativeUpdate(nativeSQLQuerySpecification, queryParameters);
  }

  public JdbcSessionContext getJdbcSessionContext() {
    return wrapped.getJdbcSessionContext();
  }

  public void afterTransactionBegin() {
    wrapped.afterTransactionBegin();
  }

  public void beforeTransactionCompletion() {
    wrapped.beforeTransactionCompletion();
  }

  public void afterTransactionCompletion(boolean successful, boolean delayed) {
    wrapped.afterTransactionCompletion(successful, delayed);
  }

  public void flushBeforeTransactionCompletion() {
    wrapped.flushBeforeTransactionCompletion();
  }

}
