package gov.ca.cwds.data.persistence.cms.rep;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;

public class ReplicatedAttorneyTest {

  @Test
  public void testReplicationOperation() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    target.setReplicationOperation(CmsReplicationOperation.I);

    final CmsReplicationOperation actual = target.getReplicationOperation();
    final CmsReplicationOperation expected = CmsReplicationOperation.I;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testReplicationDate() throws Exception {
    ReplicatedAttorney target = new ReplicatedAttorney();
    final DateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
    final Date date = fmt.parse("2012-10-31");

    target.setReplicationDate(date);

    final Date actual = target.getReplicationDate();
    final Date expected = fmt.parse("2012-10-31");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void type() throws Exception {
    assertThat(ReplicatedAttorney.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    assertThat(target, notNullValue());
  }

  @Test
  public void getNormalizationClass_Args__() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    final Class<ReplicatedAttorney> actual = target.getNormalizationClass();
    final Class<ReplicatedAttorney> expected = ReplicatedAttorney.class;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalize_Args__Map() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    final Map<Object, ReplicatedAttorney> map = new HashMap<Object, ReplicatedAttorney>();
    final ReplicatedAttorney actual = target.normalize(map);
    final ReplicatedAttorney expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNormalizationGroupKey_Args__() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    final Object actual = target.getNormalizationGroupKey();
    final Object expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    final String actual = target.getLegacyId();
    final String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    final ReplicatedAttorney target = new ReplicatedAttorney();
    final Date lastUpdatedTime = new Date();

    target.setReplicationOperation(CmsReplicationOperation.U);
    target.setLastUpdatedId("0x5");
    target.setLastUpdatedTime(lastUpdatedTime);
    target.setReplicationDate(lastUpdatedTime);

    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

}
