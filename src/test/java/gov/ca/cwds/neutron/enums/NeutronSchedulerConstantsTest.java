package gov.ca.cwds.neutron.enums;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NeutronSchedulerConstantsTest {

  @Test
  public void type() throws Exception {
    assertThat(NeutronSchedulerConstants.class, notNullValue());
  }

}
