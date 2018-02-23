package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;

public class EsCaseRelatedPersonTest extends Goddard {

  EsCaseRelatedPerson target;

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new EsCaseRelatedPerson();
  }

  @Test
  public void type() throws Exception {
    assertThat(EsCaseRelatedPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void hasRelatedPerson_A$() throws Exception {
    boolean actual = target.hasRelatedPerson();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRelatedPersonId_A$() throws Exception {
    String actual = target.getRelatedPersonId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonId_A$String() throws Exception {
    String relatedPersonId = null;
    target.setRelatedPersonId(relatedPersonId);
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    // String expected =
    // "gov.ca.cwds.data.persistence.cms.EsCaseRelatedPerson@35ff072c[\n relatedPersonId=<null>\n
    // parentPersonId=<null>\n caseId=<null>\n focusChildId=<null>\n parentId=<null>\n
    // startDate=<null>\n endDate=<null>\n county=<null>\n serviceComponent=<null>\n
    // caseLastUpdated=<null>\n focusChildFirstName=<null>\n focusChildLastName=<null>\n
    // focusChildLastUpdated=<null>\n focusChildSensitivityIndicator=<null>\n
    // worker=gov.ca.cwds.data.persistence.cms.rep.EmbeddableStaffWorker@50b38342[\n
    // workerId=<null>\n workerFirstName=<null>\n workerLastName=<null>\n
    // workerLastUpdated=<null>\n]\n parentFirstName=<null>\n parentLastName=<null>\n
    // parentRelationship=<null>\n parentLastUpdated=<null>\n parentSourceTable=<null>\n
    // parentSensitivityIndicator=<null>\n
    // accessLimitation=gov.ca.cwds.data.persistence.cms.rep.EmbeddableAccessLimitation@183ef89a\n]";
    // assertThat(actual, is(equalTo(expected)));
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
