package gov.ca.cwds.jobs.util;

import gov.ca.cwds.jobs.exception.NeutronException;

/**
 * @author CWDS TPT-2
 */
public interface JobComponent {

  /**
   * Optionally initialize resources. Default is no-op.
   * 
   * @throws NeutronException checked exception
   */
  default void init() throws NeutronException {}

  /**
   * Close and de-allocate exclusive resources. Default is no-op.
   * 
   * @throws NeutronException checked exception
   */
  default void destroy() throws NeutronException {}

}
