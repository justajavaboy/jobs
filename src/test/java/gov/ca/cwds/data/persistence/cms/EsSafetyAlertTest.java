package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchSafetyAlert;
import gov.ca.cwds.data.es.ElasticSearchSystemCode;
import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.jobs.test.SimpleTestSystemCodeCache;

public class EsSafetyAlertTest extends Goddard {

  EsSafetyAlert target;

  @BeforeClass
  public static void initClass() {
    SimpleTestSystemCodeCache.init();
  }

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new EsSafetyAlert();
  }

  @Test
  public void type() throws Exception {
    assertThat(EsSafetyAlert.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getNormalizationClass_Args__() throws Exception {
    Class<ReplicatedSafetyAlerts> actual = target.getNormalizationClass();
    Class<ReplicatedSafetyAlerts> expected = ReplicatedSafetyAlerts.class;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalize_Args__Map() throws Exception {
    Map<Object, ReplicatedSafetyAlerts> map = new HashMap<>();
    ReplicatedSafetyAlerts actual = target.normalize(map);
    ReplicatedSafetyAlerts expected = new ReplicatedSafetyAlerts();
    ElasticSearchSafetyAlert esSafetyAlert = new ElasticSearchSafetyAlert();
    ElasticSearchSafetyAlert.Activation activation = new ElasticSearchSafetyAlert.Activation();
    esSafetyAlert.setActivation(activation);
    activation.setActivationCounty(new ElasticSearchSystemCode());
    ElasticSearchSafetyAlert.Deactivation deactivation =
        new ElasticSearchSafetyAlert.Deactivation();
    esSafetyAlert.setDeactivation(deactivation);
    deactivation.setDeactivationCounty(new ElasticSearchSystemCode());
    expected.addSafetyAlert(esSafetyAlert);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNormalizationGroupKey_Args__() throws Exception {
    String actual = target.getNormalizationGroupKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastChanged_Args__() throws Exception {
    Date actual = target.getLastChanged();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastChanged_Args__Date() throws Exception {
    Date lastChanged = mock(Date.class);
    target.setLastChanged(lastChanged);
  }

  @Test
  public void getClientId_Args__() throws Exception {
    String actual = target.getClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientId_Args__String() throws Exception {
    String clientId = null;
    target.setClientId(clientId);
  }

  @Test
  public void getAlertId_Args__() throws Exception {
    String actual = target.getAlertId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAlertId_Args__String() throws Exception {
    String alertId = null;
    target.setAlertId(alertId);
  }

  @Test
  public void getActivationReasonCode_Args__() throws Exception {
    Integer actual = target.getActivationReasonCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationReasonCode_Args__Integer() throws Exception {
    Integer activationReasonCode = null;
    target.setActivationReasonCode(activationReasonCode);
  }

  @Test
  public void getActivationDate_Args__() throws Exception {
    Date actual = target.getActivationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationDate_Args__Date() throws Exception {
    Date activationDate = mock(Date.class);
    target.setActivationDate(activationDate);
  }

  @Test
  public void getActivationCountyCode_Args__() throws Exception {
    Integer actual = target.getActivationCountyCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationCountyCode_Args__Integer() throws Exception {
    Integer activationCountyCode = null;
    target.setActivationCountyCode(activationCountyCode);
  }

  @Test
  public void getActivationExplanation_Args__() throws Exception {
    String actual = target.getActivationExplanation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationExplanation_Args__String() throws Exception {
    String activationExplanation = null;
    target.setActivationExplanation(activationExplanation);
  }

  @Test
  public void getDeactivationDate_Args__() throws Exception {
    Date actual = target.getDeactivationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationDate_Args__Date() throws Exception {
    Date deactivationDate = mock(Date.class);
    target.setDeactivationDate(deactivationDate);
  }

  @Test
  public void getDeactivationCountyCode_Args__() throws Exception {
    Integer actual = target.getDeactivationCountyCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationCountyCode_Args__Integer() throws Exception {
    Integer deactivationCountyCode = null;
    target.setDeactivationCountyCode(deactivationCountyCode);
  }

  @Test
  public void getDeactivationExplanation_Args__() throws Exception {
    String actual = target.getDeactivationExplanation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationExplanation_Args__String() throws Exception {
    String deactivationExplanation = null;
    target.setDeactivationExplanation(deactivationExplanation);
  }

  @Test
  public void getLastUpdatedId_Args__() throws Exception {
    String actual = target.getLastUpdatedId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedId_Args__String() throws Exception {
    String lastUpdatedId = null;
    target.setLastUpdatedId(lastUpdatedId);
  }

  @Test
  public void getLastUpdatedTimestamp_Args__() throws Exception {
    Date actual = target.getLastUpdatedTimestamp();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedTimestamp_Args__Date() throws Exception {
    Date lastUpdatedTimestamp = mock(Date.class);
    target.setLastUpdatedTimestamp(lastUpdatedTimestamp);
  }

  @Test
  public void getLastUpdatedOperation_Args__() throws Exception {
    String actual = target.getLastUpdatedOperation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedOperation_Args__String() throws Exception {
    String lastUpdatedOperation = null;
    target.setLastUpdatedOperation(lastUpdatedOperation);
  }

  @Test
  public void getReplicationTimestamp_Args__() throws Exception {
    Date actual = target.getReplicationTimestamp();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReplicationTimestamp_Args__Date() throws Exception {
    Date replicationTimestamp = mock(Date.class);
    target.setReplicationTimestamp(replicationTimestamp);
  }

  @Test
  public void getNormalizationClass_A$() throws Exception {
    Class<ReplicatedSafetyAlerts> actual = target.getNormalizationClass();
    Class<ReplicatedSafetyAlerts> expected = ReplicatedSafetyAlerts.class;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalize_A$Map() throws Exception {
    Map<Object, ReplicatedSafetyAlerts> map = new HashMap<Object, ReplicatedSafetyAlerts>();
    ReplicatedSafetyAlerts actual = target.normalize(map);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getNormalizationGroupKey_A$() throws Exception {
    String actual = target.getNormalizationGroupKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastChanged_A$() throws Exception {
    Date actual = target.getLastChanged();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastChanged_A$Date() throws Exception {
    Date lastChanged = mock(Date.class);
    target.setLastChanged(lastChanged);
  }

  @Test
  public void getClientId_A$() throws Exception {
    String actual = target.getClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientId_A$String() throws Exception {
    String clientId = null;
    target.setClientId(clientId);
  }

  @Test
  public void getAlertId_A$() throws Exception {
    String actual = target.getAlertId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAlertId_A$String() throws Exception {
    String alertId = null;
    target.setAlertId(alertId);
  }

  @Test
  public void getActivationReasonCode_A$() throws Exception {
    Integer actual = target.getActivationReasonCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationReasonCode_A$Integer() throws Exception {
    Integer activationReasonCode = null;
    target.setActivationReasonCode(activationReasonCode);
  }

  @Test
  public void getActivationDate_A$() throws Exception {
    Date actual = target.getActivationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationDate_A$Date() throws Exception {
    Date activationDate = mock(Date.class);
    target.setActivationDate(activationDate);
  }

  @Test
  public void getActivationCountyCode_A$() throws Exception {
    Integer actual = target.getActivationCountyCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationCountyCode_A$Integer() throws Exception {
    Integer activationCountyCode = null;
    target.setActivationCountyCode(activationCountyCode);
  }

  @Test
  public void getActivationExplanation_A$() throws Exception {
    String actual = target.getActivationExplanation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActivationExplanation_A$String() throws Exception {
    String activationExplanation = null;
    target.setActivationExplanation(activationExplanation);
  }

  @Test
  public void getDeactivationDate_A$() throws Exception {
    Date actual = target.getDeactivationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationDate_A$Date() throws Exception {
    Date deactivationDate = mock(Date.class);
    target.setDeactivationDate(deactivationDate);
  }

  @Test
  public void getDeactivationCountyCode_A$() throws Exception {
    Integer actual = target.getDeactivationCountyCode();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationCountyCode_A$Integer() throws Exception {
    Integer deactivationCountyCode = null;
    target.setDeactivationCountyCode(deactivationCountyCode);
  }

  @Test
  public void getDeactivationExplanation_A$() throws Exception {
    String actual = target.getDeactivationExplanation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDeactivationExplanation_A$String() throws Exception {
    String deactivationExplanation = null;
    target.setDeactivationExplanation(deactivationExplanation);
  }

  @Test
  public void getLastUpdatedId_A$() throws Exception {
    String actual = target.getLastUpdatedId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedId_A$String() throws Exception {
    String lastUpdatedId = null;
    target.setLastUpdatedId(lastUpdatedId);
  }

  @Test
  public void getLastUpdatedTimestamp_A$() throws Exception {
    Date actual = target.getLastUpdatedTimestamp();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedTimestamp_A$Date() throws Exception {
    Date lastUpdatedTimestamp = mock(Date.class);
    target.setLastUpdatedTimestamp(lastUpdatedTimestamp);
  }

  @Test
  public void getLastUpdatedOperation_A$() throws Exception {
    String actual = target.getLastUpdatedOperation();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedOperation_A$String() throws Exception {
    String lastUpdatedOperation = null;
    target.setLastUpdatedOperation(lastUpdatedOperation);
  }

  @Test
  public void getReplicationTimestamp_A$() throws Exception {
    Date actual = target.getReplicationTimestamp();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReplicationTimestamp_A$Date() throws Exception {
    Date replicationTimestamp = mock(Date.class);
    target.setReplicationTimestamp(replicationTimestamp);
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
