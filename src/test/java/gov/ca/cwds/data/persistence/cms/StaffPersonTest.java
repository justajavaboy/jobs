package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

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

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndDate_A$() throws Exception {
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getJobTitle_A$() throws Exception {
    String actual = target.getJobTitle();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitial_A$() throws Exception {
    String actual = target.getMiddleInitial();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefix_A$() throws Exception {
    String actual = target.getNamePrefix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumber_A$() throws Exception {
    BigDecimal actual = target.getPhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneExt_A$() throws Exception {
    Integer actual = target.getPhoneExt();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartDate_A$() throws Exception {
    Date actual = target.getStartDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_A$() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTelecommuterIndicator_A$() throws Exception {
    String actual = target.getTelecommuterIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCwsOffice_A$() throws Exception {
    String actual = target.getCwsOffice();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAvailabilityAndLocationDescription_A$() throws Exception {
    String actual = target.getAvailabilityAndLocationDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsrsLicensingWorkerId_A$() throws Exception {
    String actual = target.getSsrsLicensingWorkerId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCountyCode_A$() throws Exception {
    String actual = target.getCountyCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDutyWorkerIndicator_A$() throws Exception {
    String actual = target.getDutyWorkerIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCwsOfficeAddress_A$() throws Exception {
    String actual = target.getCwsOfficeAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_A$() throws Exception {
    String actual = target.getEmailAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
