package gov.ca.cwds.neutron.rocket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;

public class FocusChildToParentRelationTest extends Goddard {

  FocusChildToParentRelation target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new FocusChildToParentRelation();
  }

  @Test
  public void type() throws Exception {
    assertThat(FocusChildToParentRelation.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void setter_getter_focusChildId() throws Exception {
    target.setFocusChildId(DEFAULT_CLIENT_ID);
    assertThat(target.getFocusChildId(), is(equalTo(DEFAULT_CLIENT_ID)));
  }

  @Test
  public void setter_getter_parentId() throws Exception {
    target.setParentId(DEFAULT_CLIENT_ID);
    assertThat(target.getParentId(), is(equalTo(DEFAULT_CLIENT_ID)));
  }

  @Test
  public void setter_getter_relationship() throws Exception {
    target.setRelationship("Mother/Daughter (birth)");
    assertThat(target.getRelationship(), is(equalTo("Mother/Daughter (birth)")));
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
