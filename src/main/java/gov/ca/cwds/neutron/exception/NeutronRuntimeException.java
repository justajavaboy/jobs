package gov.ca.cwds.neutron.exception;

/**
 * Base class for <strong>runtime</strong> exceptions. Specialized runtime exceptions should extend
 * this class.
 * 
 * @author CWDS API Team
 * @see NeutronCheckedException
 */
@SuppressWarnings("serial")
public class NeutronRuntimeException extends RuntimeException {

  /**
   * Pointless constructor. Use another one. Thanks Java.
   */
  @SuppressWarnings("unused")
  private NeutronRuntimeException() {
    // Default, no-op.
  }

  /**
   * @param message error message
   */
  public NeutronRuntimeException(String message) {
    super(message);
  }

  /**
   * @param cause original Throwable
   */
  public NeutronRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   */
  public NeutronRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message error message
   * @param cause original Throwable
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public NeutronRuntimeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
