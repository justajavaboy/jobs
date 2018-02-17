package gov.ca.cwds.dao.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;

public class StaffPersonDaoTest extends Goddard {

  @Test
  public void type() throws Exception {
    assertThat(StaffPersonDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    StaffPersonDao target = new StaffPersonDao(sessionFactory);
    assertThat(target, notNullValue());
  }

}
