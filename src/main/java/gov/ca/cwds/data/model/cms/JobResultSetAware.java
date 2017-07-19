package gov.ca.cwds.data.model.cms;

import java.sql.ResultSet;
import java.sql.SQLException;

import gov.ca.cwds.data.std.ApiGroupNormalizer;

/**
 * Reads a JDBC ResultSet from a view or materialized query table into an entity bean.
 *
 * @author CWDS API Team
 * @param <D> de-normalized entity class instance
 */
@FunctionalInterface
public interface JobResultSetAware<D extends ApiGroupNormalizer<?>> {

  /**
   * Read from a JDBC ResultSet into an entity bean.
   * 
   * @param rs the ResultSet
   * @return populated view or MQT entity bean
   * @throws SQLException on disconnect or type conversion error
   */
  D extract(ResultSet rs) throws SQLException;

}