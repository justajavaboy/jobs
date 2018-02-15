package gov.ca.cwds.jobs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.dao.cms.ReplicatedClientDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticSearchPerson.ESOptionalCollection;
import gov.ca.cwds.data.persistence.cms.EsClientPerson;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient;
import gov.ca.cwds.neutron.exception.NeutronCheckedException;

public class ClientPersonIndexerJobTest extends Goddard<ReplicatedClient, EsClientPerson> {

  ReplicatedClientDao dao;
  ClientPersonIndexerJob target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    when(rs.next()).thenReturn(true, true, false);
    dao = new ReplicatedClientDao(sessionFactory);
    target = new ClientPersonIndexerJob(dao, esDao, lastRunFile, mapper, flightPlan);
  }

  @Test
  public void type() throws Exception {
    assertThat(ClientPersonIndexerJob.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void useTransformThread_A$() throws Exception {
    boolean actual = target.useTransformThread();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrepLastChangeSQL_A$() throws Exception {
    target.getFlightPlan().setOverrideLastRunTime(lastRunTime);
    String actual = target.getPrepLastChangeSQL();
    String expected =
        "\"INSERT INTO GT_ID (IDENTIFIER)\\nSELECT DISTINCT CLT.IDENTIFIER \\nFROM CLIENT_T clt \\nWHERE CLT.IBMSNAP_LOGMARKER > '2018-12-31 03:21:12.000' \\nUNION SELECT DISTINCT cla.FKCLIENT_T AS IDENTIFIER \\nFROM CL_ADDRT cla \\nWHERE CLA.IBMSNAP_LOGMARKER > '2018-12-31 03:21:12.000' \\nUNION SELECT DISTINCT cla.FKCLIENT_T AS IDENTIFIER \\nFROM CL_ADDRT cla \\nJOIN ADDRS_T  adr ON cla.FKADDRS_T  = adr.IDENTIFIER \\nWHERE ADR.IBMSNAP_LOGMARKER > '2018-12-31 03:21:12.000' \\nUNION SELECT DISTINCT eth.ESTBLSH_ID AS IDENTIFIER \\nFROM CLSCP_ET eth \\nWHERE ETH.ESTBLSH_CD = 'C' \\nAND ETH.IBMSNAP_LOGMARKER > '2018-12-31 03:21:12.000' \"";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void extract_A$ResultSet() throws Exception {
    EsClientPerson actual = target.extract(rs);
    // EsClientPerson expected = null;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = SQLException.class)
  public void extract_A$ResultSet_T$SQLException() throws Exception {
    target.extract(rs);
  }

  @Test
  public void getDenormalizedClass_A$() throws Exception {
    Object actual = target.getDenormalizedClass();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getInitialLoadViewName_A$() throws Exception {
    String actual = target.getInitialLoadViewName();
    String expected = "MQT_CLIENT_ADDRESS";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMQTName_A$() throws Exception {
    String actual = target.getMQTName();
    String expected = "MQT_CLIENT_ADDRESS";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getJdbcOrderBy_A$() throws Exception {
    String actual = target.getJdbcOrderBy();
    String expected = " ORDER BY X.CLT_IDENTIFIER ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalizeAndQueueIndex_A$List() throws Exception {
    List<EsClientPerson> grpRecs = new ArrayList<EsClientPerson>();
    target.normalizeAndQueueIndex(grpRecs);
  }

  @Test
  public void getInitialLoadQuery_A$String() throws Exception {
    String dbSchemaName = null;
    String actual = target.getInitialLoadQuery(dbSchemaName);
    String expected =
        "SELECT x.* FROM null.MQT_CLIENT_ADDRESS x WHERE X.CLT_IDENTIFIER BETWEEN ':fromId' AND ':toId'  AND x.CLT_SENSTV_IND = 'N'  ORDER BY X.CLT_IDENTIFIER  FOR READ ONLY WITH UR ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void initialLoadProcessRangeResults_A$ResultSet() throws Exception {
    target.initialLoadProcessRangeResults(rs);
  }

  @Test
  public void initialLoadProcessRangeResults_A$ResultSet_T$SQLException() throws Exception {
    try {
      target.initialLoadProcessRangeResults(rs);
      fail("Expected exception was not thrown!");
    } catch (SQLException e) {
    }
  }

  @Test
  public void validateAddresses_A$ReplicatedClient$ElasticSearchPerson() throws Exception {
    ReplicatedClient client = new ReplicatedClient();
    ElasticSearchPerson person = new ElasticSearchPerson();
    boolean actual = target.validateAddresses(client, person);
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void validateDocument_A$ElasticSearchPerson() throws Exception {
    ElasticSearchPerson person = new ElasticSearchPerson();
    boolean actual = target.validateDocument(person);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NeutronCheckedException.class)
  public void validateDocument_A$ElasticSearchPerson_T$NeutronCheckedException() throws Exception {
    ElasticSearchPerson person = new ElasticSearchPerson();
    target.validateDocument(person);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void threadRetrieveByJdbc_A$() throws Exception {
    target.threadRetrieveByJdbc();
  }

  @Test
  public void isInitialLoadJdbc_A$() throws Exception {
    boolean actual = target.isInitialLoadJdbc();
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPartitionRanges_A$() throws Exception {
    List actual = target.getPartitionRanges();
    List expected = new ArrayList<>();
    expected.add(pair);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NeutronCheckedException.class)
  public void getPartitionRanges_A$_T$NeutronCheckedException() throws Exception {
    target.getPartitionRanges();
  }

  @Test
  public void mustDeleteLimitedAccessRecords_A$() throws Exception {
    boolean actual = target.mustDeleteLimitedAccessRecords();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalize_A$List() throws Exception {
    List<EsClientPerson> recs = new ArrayList<EsClientPerson>();
    List<ReplicatedClient> actual = target.normalize(recs);
    List<ReplicatedClient> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void nextThreadNumber_A$() throws Exception {
    int actual = target.nextThreadNumber();
    int expected = 1;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void keepCollections_A$() throws Exception {
    ESOptionalCollection[] actual = target.keepCollections();
    ESOptionalCollection[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NeutronCheckedException.class)
  public void main_A$StringArray_T$Exception() throws Exception {
    String[] args = new String[] {};
    ClientPersonIndexerJob.main(args);
  }

}
