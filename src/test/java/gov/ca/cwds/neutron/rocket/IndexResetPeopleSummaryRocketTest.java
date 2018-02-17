package gov.ca.cwds.neutron.rocket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.dao.cms.ReplicatedOtherAdultInPlacemtHomeDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHome;
import gov.ca.cwds.jobs.Goddard;

public class IndexResetPeopleSummaryRocketTest
    extends Goddard<ReplicatedOtherAdultInPlacemtHome, ReplicatedOtherAdultInPlacemtHome> {

  ReplicatedOtherAdultInPlacemtHomeDao dao;
  IndexResetPeopleSummaryRocket target;

  @Override
  public void setup() throws Exception {
    super.setup();

    dao = new ReplicatedOtherAdultInPlacemtHomeDao(sessionFactory);
    target = new IndexResetPeopleSummaryRocket(dao, esDao, mapper, flightPlan);
  }

  @Test
  public void type() throws Exception {
    assertThat(IndexResetPeopleSummaryRocket.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getIndexSettingsLocation_A$() throws Exception {
    String actual = target.getIndexSettingsLocation();
    String expected = "/elasticsearch/setting/people-summary-index-settings.json";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDocumentMappingLocation_A$() throws Exception {
    String actual = target.getDocumentMappingLocation();
    String expected = "/elasticsearch/mapping/map_person_summary.json";
    assertThat(actual, is(equalTo(expected)));
  }

}
