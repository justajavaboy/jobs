package gov.ca.cwds.jobs.cals.rfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.cals.DatabaseHelper;
import gov.ca.cwds.jobs.cals.BaseCalsIndexerJobTest;
import java.util.Map;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author CWDS TPT-2
 */
public class RFA1aFormIndexerJobTest extends BaseCalsIndexerJobTest {

  private static final String TEST_CONFIG = "src/test/resources/config/cals/rfa/CALS_RFA1aForm.yaml";

  private static final String CALSNS_SCHEMA = "calsns";
  private static final String CALSNS_JDBC_URL_TEMPLATE = "jdbc:h2:%s;INIT=create schema if not exists %s\\;set schema %s;autocommit=true";

  private static final String CALSNS_TEST_DATA_SCRIPT = "liquibase/calsns_database_master_for_tests.xml";

  private static final RFA1aFormIncrementalLoadDateStrategy RFA1A_FORM_LOAD_DATE_STRATEGY = new RFA1aFormIncrementalLoadDateStrategy();

  private static String tempDbFile;

  private static void cleanUp() throws Exception {
    RFA1A_FORM_LOAD_DATE_STRATEGY.reset(TIME_FILES_DIR);
  }

  @BeforeClass
  public static void beforeClass() throws Exception {
    cleanUp();
    tempDbFile = createTestDbFile(CALSNS_SCHEMA);
    setUpCalsns();
    System.setProperty("DB_CALSNS_JDBC_URL", getCalsnsJdbcUrl());
    System.setProperty("DB_CALSNS_USER", CALSNS_USER);
    System.setProperty("DB_CALSNS_PASSWORD", CALSNS_PASSWORD);

    // M:\ca-cwds\jobs\

    RFA1aFormIndexerJob.main(new String[]{
        "-c", TEST_CONFIG, "-l", TIME_FILES_DIR
    });
  }

  @AfterClass
  public static void afterClass() throws Exception {
    cleanUp();
  }

  @Test(expected = ResponseException.class)
  public void testUnauthorized() throws Exception {
    anonymousRestClient.performRequest("GET", "/");
  }

  @Test
  public void testTotalIndexedDocuments() throws Exception {
    Response response = restClient.performRequest("GET", "/rfa1aforms/rfa1aform/_search");
    assertEquals(200, response.getStatusLine().getStatusCode());

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> jsonMap = mapper.readValue(response.getEntity().getContent(), Map.class);
    assertFalse(jsonMap.isEmpty());
    assertNotNull(jsonMap.get("hits"));
    Map<String, Object> hits = (Map<String, Object>) jsonMap.get("hits");
    assertEquals(2, hits.get("total"));
  }

  private static String getCalsnsJdbcUrl() throws Exception {
    return String.format(CALSNS_JDBC_URL_TEMPLATE, tempDbFile, CALSNS_SCHEMA, CALSNS_SCHEMA);
  }

  private static void setUpCalsns() throws Exception {
    new DatabaseHelper(getCalsnsJdbcUrl(), CALSNS_USER, CALSNS_PASSWORD)
        .runScript(CALSNS_TEST_DATA_SCRIPT, CALSNS_SCHEMA);
  }
}
