package gov.ca.cwds.neutron.exception;

/**
 * Base class for checked exceptions in Neutron rockets. Custom checked exceptions should extend
 * this class.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class NeutronCheckedException extends Exception {

  /**
   * Pointless constructor. Use another one.
   */
  @SuppressWarnings("unused")
  private NeutronCheckedException() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public NeutronCheckedException(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public NeutronCheckedException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public NeutronCheckedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public NeutronCheckedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
