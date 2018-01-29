package gov.ca.cwds.jobs.exception;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.neutron.exception.NeutronCheckedException;

public class NeutronExceptionTest {

  @Test
  public void type() throws Exception {
    assertThat(NeutronCheckedException.class, notNullValue());
  }

  @Test
  public void instantiation1() throws Exception {
    String message = "test";
    NeutronCheckedException target = new NeutronCheckedException(message);
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation2() throws Exception {
    String message = null;
    NeutronCheckedException target =
        new NeutronCheckedException(message, new IllegalArgumentException("whatever"));
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation4() throws Exception {
    String message = null;
    NeutronCheckedException target =
        new NeutronCheckedException(message, new IllegalArgumentException("whatever"), false, false);
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation5() throws Exception {
    NeutronCheckedException target = new NeutronCheckedException(new IllegalArgumentException("whatever"));
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String message = null;
    NeutronCheckedException target = new NeutronCheckedException(message);
    assertThat(target, notNullValue());
  }

}
