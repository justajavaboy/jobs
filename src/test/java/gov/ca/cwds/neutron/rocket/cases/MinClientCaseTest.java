package gov.ca.cwds.neutron.rocket.cases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;

public class MinClientCaseTest extends Goddard {

  @Test
  public void type() throws Exception {
    assertThat(MinClientCase.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final String clientId = null;
    final String caseId = null;
    final MinClientCase target = new MinClientCase(clientId, caseId);
    assertThat(target, notNullValue());
  }

  @Test
  public void extract_A$ResultSet() throws Exception {
    final MinClientCase actual = MinClientCase.extract(rs);
    final MinClientCase expected = new MinClientCase(DEFAULT_CLIENT_ID, DEFAULT_CLIENT_ID);

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void extract_A$ResultSet_T$SQLException() throws Exception {
    try {
      when(rs.getString(any(String.class))).thenThrow(SQLException.class);
      MinClientCase.extract(rs);
      fail("Expected exception was not thrown!");
    } catch (SQLException e) {
      // then
    }
  }

}
