package gov.ca.cwds.jobs;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.dao.cms.ReplicatedEducationProviderContactDao;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedEducationProviderContact;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class EducationProviderContactIndexerJobTest extends
    Goddard<ReplicatedEducationProviderContact, ReplicatedEducationProviderContact> {

  ReplicatedEducationProviderContactDao dao;
  EducationProviderContactIndexerJob target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    dao = new ReplicatedEducationProviderContactDao(this.sessionFactory);
    target = new EducationProviderContactIndexerJob(dao, esDao, lastJobRunTimeFilename, MAPPER,
        sessionFactory, flightRecorder, opts);
  }

  @Test
  public void testType() throws Exception {
    assertThat(EducationProviderContactIndexerJob.class, notNullValue());
  }

  @Test
  public void testInstantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void main_Args__StringArray() throws Exception {
    final String[] args = new String[] {"-c", "config/local.yaml", "-l",
        "/Users/CWS-NS3/client_indexer_time.txt", "-S"};
    EducationProviderContactIndexerJob.main(args);
  }

}
