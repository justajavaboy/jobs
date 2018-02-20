package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.persistence.cms.rep.EmbeddableCmsReplicatedEntity;
import gov.ca.cwds.jobs.Goddard;

public class StaffPersonTest extends Goddard {

  StaffPerson target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new StaffPerson();
  }

  @Test
  public void type() throws Exception {
    assertThat(StaffPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_A$() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_A$() throws Exception {
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    ElasticSearchLegacyDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReplicatedEntity_A$() throws Exception {
    EmbeddableCmsReplicatedEntity actual = target.getReplicatedEntity();
    EmbeddableCmsReplicatedEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
