package gov.ca.cwds.jobs.util;

import gov.ca.cwds.neutron.exception.NeutronCheckedException;

/**
 * @author CWDS Elasticsearch Team
 */
public interface JobComponent {

  /**
   * Optionally initialize resources. Default is no-op.
   * 
   * @throws NeutronCheckedException checked exception
   */
  default void init() throws NeutronCheckedException {}

  /**
   * Close and de-allocate exclusive resources. Default is no-op.
   * 
   * @throws NeutronCheckedException checked exception
   */
  default void destroy() throws NeutronCheckedException {}

}
