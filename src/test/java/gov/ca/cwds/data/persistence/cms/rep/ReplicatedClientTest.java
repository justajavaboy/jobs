package gov.ca.cwds.data.persistence.cms.rep;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.es.ElasticSearchPersonAddress;
import gov.ca.cwds.data.es.ElasticSearchPersonAka;
import gov.ca.cwds.data.es.ElasticSearchRaceAndEthnicity;
import gov.ca.cwds.data.es.ElasticSearchSafetyAlert;
import gov.ca.cwds.data.es.ElasticSearchSystemCode;
import gov.ca.cwds.data.persistence.cms.EsClientAddress;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.jobs.test.SimpleTestSystemCodeCache;
import gov.ca.cwds.neutron.util.transform.ElasticTransformer;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

public class ReplicatedClientTest extends Goddard<ReplicatedClient, EsClientAddress> {

  ReplicatedClient target;

  @BeforeClass
  public static void setupClass() {
    SimpleTestSystemCodeCache.init();
  }

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new ReplicatedClient();
    target.setId(DEFAULT_CLIENT_ID);
  }

  @Test
  public void testReplicationOperation() throws Exception {
    target.setReplicationOperation(CmsReplicationOperation.I);
    final CmsReplicationOperation actual = target.getReplicationOperation();
    final CmsReplicationOperation expected = CmsReplicationOperation.I;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testReplicationDate() throws Exception {
    final DateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
    final Date date = fmt.parse("2012-10-31");
    target.setReplicationDate(date);

    final Date actual = target.getReplicationDate();
    final Date expected = fmt.parse("2012-10-31");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void type() throws Exception {
    assertThat(ReplicatedClient.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getClientAddresses_Args__() throws Exception {
    final Set<ReplicatedClientAddress> actual = target.getClientAddresses();
    final Set<ReplicatedClientAddress> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientAddresses_Args__Set() throws Exception {
    final Set<ReplicatedClientAddress> clientAddresses = new HashSet<>();
    target.setClientAddresses(clientAddresses);
  }

  @Test
  public void addClientAddress_Args__ReplicatedClientAddress() throws Exception {
    final ReplicatedClientAddress clientAddress = mock(ReplicatedClientAddress.class);
    target.addClientAddress(clientAddress);
  }

  @Test
  public void getAddresses_Args__() throws Exception {
    final ReplicatedClientAddress clientAddress = new ReplicatedClientAddress();
    clientAddress.setId(DEFAULT_CLIENT_ID);
    clientAddress.setAddressType((short) 27);
    target.addClientAddress(clientAddress);

    final ReplicatedAddress address = new ReplicatedAddress();
    clientAddress.addAddress(address);
    address.setState((short) 1873);
    address.setGovernmentEntityCd((short) 1104);

    final List<ElasticSearchPersonAddress> actual = target.getElasticSearchPersonAddresses();
    // List<ElasticSearchPersonAddress> expected = new ArrayList<>();
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getPhones_Args__() throws Exception {
    final ApiPhoneAware[] actual = target.getPhones();
    final ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    final String actual = target.getLegacyId();
    final String expected = DEFAULT_CLIENT_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void toString_Args__() throws Exception {
    final String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_Args__() throws Exception {
    final int actual = target.hashCode();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    final Object obj = null;
    final boolean actual = target.equals(obj);
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    final Date lastUpdatedTime = new Date();
    target.setReplicationOperation(CmsReplicationOperation.U);
    target.setLastUpdatedId("0x5");
    target.setLastUpdatedTime(lastUpdatedTime);
    target.setReplicationDate(lastUpdatedTime);

    final ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getClientCounty_Args__() throws Exception {
    final List<ElasticSearchSystemCode> actual = target.getClientCounties();
    final List<ElasticSearchSystemCode> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void addClientCounty_Args__Short() throws Exception {
    final Short clientCountyId = null;
    target.addClientCounty(clientCountyId);
  }

  @Test
  public void getClientRaces_Args__() throws Exception {
    final List<Short> actual = target.getClientRaces();
    final List<Short> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientRaces_Args__List() throws Exception {
    final List<Short> clientRaces = new ArrayList<>();
    target.setClientRaces(clientRaces);
  }

  @Test
  public void addClientRace_Args__Short() throws Exception {
    final Short clientRace = null;
    target.addClientRace(clientRace);
  }

  @Test
  public void getReplicatedEntity_Args__() throws Exception {
    final EmbeddableCmsReplicatedEntity actual = target.getReplicatedEntity();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getRaceAndEthnicity_Args__() throws Exception {
    final List<Short> clientRaces = new ArrayList<>();
    clientRaces.add((short) 825);
    clientRaces.add((short) 824);
    clientRaces.add((short) 3164);
    target.setClientRaces(clientRaces);

    final ElasticSearchRaceAndEthnicity actual = target.getRaceAndEthnicity();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getAkas_Args__() throws Exception {
    final Map<String, ElasticSearchPersonAka> actual = target.getAkas();
    final Map<String, ElasticSearchPersonAka> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAkas_Args__Map() throws Exception {
    final Map<String, ElasticSearchPersonAka> akas = new HashMap<>();
    target.setAkas(akas);
  }

  @Test
  public void addAka_Args__ElasticSearchPersonAka() throws Exception {
    final ElasticSearchPersonAka aka = new ElasticSearchPersonAka();
    target.addAka(aka);
  }

  @Test
  public void getSafetyAlerts_Args__() throws Exception {
    final Map<String, ElasticSearchSafetyAlert> actual = target.getSafetyAlerts();
    final Map<String, ElasticSearchSafetyAlert> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSafetyAlerts_Args__Map() throws Exception {
    final Map<String, ElasticSearchSafetyAlert> safetyAlerts = new HashMap<>();
    target.setSafetyAlerts(safetyAlerts);
  }

  @Test
  public void addSafetyAlert_Args__ElasticSearchSafetyAlert() throws Exception {
    final ElasticSearchSafetyAlert safetyAlert = mock(ElasticSearchSafetyAlert.class);
    target.addSafetyAlert(safetyAlert);
  }

  @Test
  public void getOpenCaseId_Args__() throws Exception {
    final String actual = target.getOpenCaseId();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setOpenCaseId_Args__String() throws Exception {
    final String openCaseId = null;
    target.setOpenCaseId(openCaseId);
  }

  @Test
  public void getOtherClientNames_Args__() throws Exception {
    final List<ElasticSearchPersonAka> actual = target.getOtherClientNames();
    final List<ElasticSearchPersonAka> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getClientSafetyAlerts_Args__() throws Exception {
    final List<ElasticSearchSafetyAlert> actual = target.getClientSafetyAlerts();
    final List<ElasticSearchSafetyAlert> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void addClientAddress_A$ReplicatedClientAddress() throws Exception {
    final ReplicatedClientAddress clientAddress = mock(ReplicatedClientAddress.class);
    target.addClientAddress(clientAddress);
  }

  @Test
  public void addClientRace_A$Short() throws Exception {
    final Short clientRace = null;
    target.addClientRace(clientRace);
  }

  @Test
  public void addAka_A$ElasticSearchPersonAka() throws Exception {
    final ElasticSearchPersonAka aka = new ElasticSearchPersonAka();
    target.addAka(aka);
  }

  @Test
  public void addSafetyAlert_A$ElasticSearchSafetyAlert() throws Exception {
    final ElasticSearchSafetyAlert safetyAlert = new ElasticSearchSafetyAlert();
    target.addSafetyAlert(safetyAlert);
  }

  @Test
  public void getElasticSearchPersonAddresses_A$() throws Exception {
    List<ElasticSearchPersonAddress> actual = target.getElasticSearchPersonAddresses();
    List<ElasticSearchPersonAddress> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_A$() throws Exception {
    final ApiPhoneAware[] actual = target.getPhones();
    final ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_A$() throws Exception {
    final String actual = target.getLegacyId();
    final String expected = DEFAULT_CLIENT_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_A$() throws Exception {
    final ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    final ElasticSearchLegacyDescriptor expected = ElasticTransformer
        .createLegacyDescriptor(target.getId(), target.getLastUpdatedTime(), LegacyTable.CLIENT);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOtherClientNames_A$() throws Exception {
    final List<ElasticSearchPersonAka> actual = target.getOtherClientNames();
    final List<ElasticSearchPersonAka> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getClientSafetyAlerts_A$() throws Exception {
    final List<ElasticSearchSafetyAlert> actual = target.getClientSafetyAlerts();
    final List<ElasticSearchSafetyAlert> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRaceAndEthnicity_A$() throws Exception {
    final ElasticSearchRaceAndEthnicity actual = target.getRaceAndEthnicity();

    final ElasticSearchRaceAndEthnicity expected = new ElasticSearchRaceAndEthnicity();
    expected.setRaceCodes(new ArrayList<>());
    expected.setHispanicCodes(new ArrayList<>());
    expected.setHispanicOriginCode("");
    expected.setHispanicUnableToDetermineCode("");
    expected.setUnableToDetermineCode("");

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_A$() throws Exception {
    final int actual = target.hashCode();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    final Object obj = null;
    final boolean actual = target.equals(obj);
    final boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
