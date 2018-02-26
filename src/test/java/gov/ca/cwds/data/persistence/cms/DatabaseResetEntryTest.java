package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class DatabaseResetEntryTest extends Goddard {

  DatabaseResetEntry target;
  final Date now = new Date();

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new DatabaseResetEntry();

    target.setSchemaName("CWSNS4");
    target.setRefreshStatus("R");
    target.setStartTime(now);
  }

  @Test
  public void type() throws Exception {
    assertThat(DatabaseResetEntry.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getSchemaName_A$() throws Exception {
    final String actual = target.getSchemaName();
    final String expected = "CWSNS4";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSchemaName_A$String() throws Exception {
    final String schemaName = null;
    target.setSchemaName(schemaName);
  }

  @Test
  public void getStartTime_A$() throws Exception {
    final Date actual = target.getStartTime();
    final Date expected = now;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartTime_A$Date() throws Exception {
    final Date startTime = new Date();
    target.setStartTime(startTime);
  }

  @Test
  public void getEndTime_A$() throws Exception {
    final Date actual = target.getEndTime();
    final Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndTime_A$Date() throws Exception {
    final Date endTime = new Date();
    target.setEndTime(endTime);
  }

  @Test
  public void getRefreshStatus_A$() throws Exception {
    final String actual = target.getRefreshStatus();
    final String expected = "R";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRefreshStatus_A$String() throws Exception {
    final String refreshStatus = null;
    target.setRefreshStatus(refreshStatus);
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    final Serializable actual = target.getPrimaryKey();
    final Serializable expected = new VarargPrimaryKey(target.getSchemaName(),
        DomainChef.cookTimestamp(target.getStartTime()));
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void toString_A$() throws Exception {
    final String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_A$() throws Exception {
    final int actual = target.hashCode();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    final boolean actual = target.equals(obj);
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
