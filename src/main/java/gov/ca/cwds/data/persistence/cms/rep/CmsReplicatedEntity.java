package gov.ca.cwds.data.persistence.cms.rep;

import java.util.Date;

import gov.ca.cwds.dao.ApiLegacyAware;

/**
 * Provides common CMS replication columns and checks for Hibernate CMS entity classes.
 * 
 * @author CWDS API Team
 */
public interface CmsReplicatedEntity extends ApiLegacyAware {

  /**
   * Determines whether record was deleted from companion transactional table.
   * 
   * @param t another replicated entity
   * @return true if deleted
   */
  default boolean isDelete(CmsReplicatedEntity t) {
    return t.getReplicationOperation() == CmsReplicationOperation.D;
  }

  /**
   * Getter for replication operation.
   * 
   * @return replication operation
   */
  CmsReplicationOperation getReplicationOperation();

  /**
   * Getter for replication date.
   * 
   * @return replication date
   */
  Date getReplicationDate();

  /**
   * Setter for replication operation (I = insert, U = update, D = delete).
   * 
   * @param replicationOperation SQL operation that triggered the replication of this record..
   */
  void setReplicationOperation(CmsReplicationOperation replicationOperation);

  /**
   * Setter for replication date.
   * 
   * @param replicationDate when this record replicated
   */
  void setReplicationDate(Date replicationDate);
}