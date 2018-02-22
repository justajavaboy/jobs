package gov.ca.cwds.neutron.jetpack;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CheeseRayTest {

  @Test
  public void type() throws Exception {
    assertThat(CheeseRay.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CheeseRay target = new CheeseRay();
    assertThat(target, notNullValue());
  }

}
