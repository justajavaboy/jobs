package gov.ca.cwds.jobs.exception;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.neutron.exception.NeutronRuntimeException;

public class JobsExceptionTest {

  @Test
  public void type() throws Exception {
    assertThat(NeutronRuntimeException.class, notNullValue());
  }

  @Test
  public void instantiation1() throws Exception {
    String message = null;
    NeutronRuntimeException target = new NeutronRuntimeException(message);
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation2() throws Exception {
    String message = null;
    NeutronRuntimeException target = new NeutronRuntimeException(message, new IllegalArgumentException("whatever"));
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation4() throws Exception {
    String message = null;
    NeutronRuntimeException target =
        new NeutronRuntimeException(message, new IllegalArgumentException("whatever"), false, false);
    assertThat(target, notNullValue());
  }

}
