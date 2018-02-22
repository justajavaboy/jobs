package gov.ca.cwds.neutron.rocket.cases;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CaseSQLResourceTest {

  @Test
  public void type() throws Exception {
    assertThat(CaseSQLResource.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CaseSQLResource target = new CaseSQLResource();
    assertThat(target, notNullValue());
  }

}
