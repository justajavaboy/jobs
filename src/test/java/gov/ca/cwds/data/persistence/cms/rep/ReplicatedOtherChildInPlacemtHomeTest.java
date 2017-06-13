package gov.ca.cwds.data.persistence.cms.rep;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ReplicatedOtherChildInPlacemtHomeTest {

  @Test
  public void type() throws Exception {
    assertThat(ReplicatedOtherChildInPlacemtHome.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ReplicatedOtherChildInPlacemtHome target = new ReplicatedOtherChildInPlacemtHome();
    assertThat(target, notNullValue());
  }

  @Test
  public void getNormalizationClass_Args__() throws Exception {
    ReplicatedOtherChildInPlacemtHome target = new ReplicatedOtherChildInPlacemtHome();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    Class<ReplicatedOtherChildInPlacemtHome> actual = target.getNormalizationClass();
    // then
    // e.g. : verify(mocked).called();
    Class<ReplicatedOtherChildInPlacemtHome> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void normalize_Args__Map() throws Exception {
    ReplicatedOtherChildInPlacemtHome target = new ReplicatedOtherChildInPlacemtHome();
    // given
    Map<Object, ReplicatedOtherChildInPlacemtHome> map =
        new HashMap<Object, ReplicatedOtherChildInPlacemtHome>();
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    ReplicatedOtherChildInPlacemtHome actual = target.normalize(map);
    // then
    // e.g. : verify(mocked).called();
    ReplicatedOtherChildInPlacemtHome expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNormalizationGroupKey_Args__() throws Exception {
    ReplicatedOtherChildInPlacemtHome target = new ReplicatedOtherChildInPlacemtHome();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    Object actual = target.getNormalizationGroupKey();
    // then
    // e.g. : verify(mocked).called();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    ReplicatedOtherChildInPlacemtHome target = new ReplicatedOtherChildInPlacemtHome();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getLegacyId();
    // then
    // e.g. : verify(mocked).called();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
