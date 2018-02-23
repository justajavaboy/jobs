package gov.ca.cwds.neutron.rocket.cases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;

public class CaseClientRelativeTest extends Goddard {

  private static final String DEFAULT_CASE_ID = "xyz7654321";

  CaseClientRelative target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    String caseId = DEFAULT_CASE_ID;
    String focusClientId = DEFAULT_CLIENT_ID;
    String clientId = "x051982732";
    short relationCode = (short) 203;
    target = new CaseClientRelative(caseId, focusClientId, clientId, relationCode, false);
  }

  @Test
  public void type() throws Exception {
    assertThat(CaseClientRelative.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void extract_Args__ResultSet() throws Exception {
    when(rs.getString("CASE_ID")).thenReturn(DEFAULT_CASE_ID);
    when(rs.getString("FOCUS_CHILD_ID")).thenReturn(DEFAULT_CLIENT_ID);
    when(rs.getString("THIS_CLIENT_ID")).thenReturn("x051982732");
    when(rs.getShort("RELATION")).thenReturn((short) 203);

    CaseClientRelative actual = CaseClientRelative.extract(rs);
    CaseClientRelative expected = new CaseClientRelative(DEFAULT_CASE_ID, DEFAULT_CLIENT_ID,
        "x051982732", (short) 203, false);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hasRelation_Args__() throws Exception {
    final boolean actual = target.hasRelation();
    final boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hasNoRelation_Args__() throws Exception {
    final boolean actual = target.hasNoRelation();
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRelatedClientId_Args__() throws Exception {
    final String actual = target.getRelatedClientId();
    final String expected = "x051982732";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedClientId_Args__String() throws Exception {
    final String clientId_ = null;
    target.setRelatedClientId(clientId_);
  }

  @Test
  public void getCaseId_Args__() throws Exception {
    final String actual = target.getCaseId();
    final String expected = DEFAULT_CASE_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCaseId_Args__String() throws Exception {
    final String referralId = null;
    target.setCaseId(referralId);
  }

  @Test
  public void getFocusClientId_Args__() throws Exception {
    final String actual = target.getFocusClientId();
    final String expected = DEFAULT_CLIENT_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFocusClientId_Args__String() throws Exception {
    final String focusClientId_ = null;
    target.setFocusClientId(focusClientId_);
  }

  @Test
  public void getRelationCode_Args__() throws Exception {
    final short actual = target.getRelationCode();
    final short expected = (short) 203;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationCode_Args__short() throws Exception {
    final short relationCode_ = 0;
    target.setRelationCode(relationCode_);
  }

  @Test
  public void isParentRelation_Args__() throws Exception {
    final boolean actual = target.isParentRelation();
    final boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void translateRelationship_Args__() throws Exception {
    final SystemCode actual = target.translateRelationship();
    assertThat(actual.getShortDescription(), is(equalTo("Daughter/Father (Adoptive)")));
  }

  @Test
  public void toString_Args__() throws Exception {
    final String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_Args__() throws Exception {
    final int actual = target.hashCode();
    final int expected =
        new CaseClientRelative(DEFAULT_CASE_ID, "r2d2abcdef", DEFAULT_CLIENT_ID, (short) 187, false)
            .hashCode();
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    final Object obj = null;
    final boolean actual = target.equals(obj);
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  public void test_extract_A$ResultSet() throws Exception {
    final CaseClientRelative actual = CaseClientRelative.extract(rs);
    final CaseClientRelative expected = null;
    assertEquals(expected, actual);
  }

  public void test_isParentRelation_A$() throws Exception {
    final boolean actual = target.isParentRelation();
    final boolean expected = false;
    assertEquals(expected, actual);
  }

  public void test_hasRelation_A$() throws Exception {
    final boolean actual = target.hasRelation();
    final boolean expected = false;
    assertEquals(expected, actual);
  }

  public void test_hasNoRelation_A$() throws Exception {
    final boolean actual = target.hasNoRelation();
    final boolean expected = false;
    assertEquals(expected, actual);
  }

  public void test_translateRelationship_A$() throws Exception {
    final SystemCode actual = target.translateRelationship();
    final SystemCode expected = null;
    assertEquals(expected, actual);
  }

  public void test_translateRelationshipToString_A$() throws Exception {
    final String actual = target.translateRelationshipToString();
    final String expected = null;
    assertEquals(expected, actual);
  }

  public void test_toString_A$() throws Exception {
    final String actual = target.toString();
    final String expected = null;
    assertEquals(expected, actual);
  }

  public void test_hashCode_A$() throws Exception {
    final int actual = target.hashCode();
    final int expected = 0;
    assertEquals(expected, actual);
  }

  public void test_equals_A$Object() throws Exception {
    final Object obj = null;
    final boolean actual = target.equals(obj);
    final boolean expected = false;
    assertEquals(expected, actual);
  }

}
