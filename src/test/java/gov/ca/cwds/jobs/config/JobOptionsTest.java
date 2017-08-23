package gov.ca.cwds.jobs.config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.jobs.exception.JobsException;

public class JobOptionsTest {

  public static final JobOptions makeGeneric() {
    return new JobOptions("config/local.yaml", null, null, null, false, 1, 5, 10, 1, " ",
        "9999999999", true, null);
  }

  @Test
  public void type() throws Exception {
    assertThat(JobOptions.class, notNullValue());
  }

  @Test
  public void getEsConfigLoc_Args__() throws Exception {
    JobOptions target = makeGeneric();
    String actual = target.getEsConfigLoc();
    String expected = "config/local.yaml";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastRunLoc_Args__() throws Exception {
    JobOptions target = makeGeneric();
    String actual = target.getLastRunLoc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastRunTime_Args__() throws Exception {
    JobOptions target = makeGeneric();
    Date actual = target.getLastRunTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIndexName_Args__() throws Exception {
    JobOptions target = makeGeneric();
    String actual = target.getIndexName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartBucket_Args__() throws Exception {
    JobOptions target = makeGeneric();
    long actual = target.getStartBucket();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndBucket_Args__() throws Exception {
    JobOptions target = makeGeneric();
    long actual = target.getEndBucket();
    long expected = 5L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTotalBuckets_Args__() throws Exception {
    JobOptions target = makeGeneric();
    long actual = target.getTotalBuckets();
    long expected = 10L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getThreadCount_Args__() throws Exception {
    JobOptions target = makeGeneric();
    long actual = target.getThreadCount();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  // @Test
  // public void makeOpt_Args__String__String__String() throws Exception {
  //
  // // given
  // String shortOpt = null;
  // String longOpt = null;
  // String description = null;
  //
  // // when
  // Option actual = JobOptions.makeOpt(shortOpt, longOpt, description);
  // // then
  // // e.g. : verify(mocked).called();
  // Option expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  // @Test
  // public void makeOpt_Args__String__String__String__boolean__int__Class__char() throws Exception
  // {
  //
  // // given
  // String shortOpt = null;
  // String longOpt = null;
  // String description = null;
  // boolean required = false;
  // int argc = 0;
  // Class<?> type = mock(Class.class);
  // char sep = ' ';
  //
  // // when
  // Option actual = JobOptions.makeOpt(shortOpt, longOpt, description, required, argc, type, sep);
  // // then
  // // e.g. : verify(mocked).called();
  // Option expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  @Ignore
  public void buildCmdLineOptions_Args__() throws Exception {
    Options actual = JobOptions.buildCmdLineOptions();
    Options expected = null;
    assertThat(actual.getOptions().size(), is(equalTo(10)));
  }

  @Test
  public void printUsage_Args__() throws Exception {
    JobOptions.printUsage();
  }

  @Test(expected = JobsException.class)
  public void parseCommandLine_Args__T__no_args() throws Exception {
    String[] args = new String[] {};
    JobOptions actual = JobOptions.parseCommandLine(args);
    JobOptions expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void parseCommandLine_Args__StringArray_T__JobsException() throws Exception {
    String[] args = new String[] {};
    try {
      JobOptions.parseCommandLine(args);
      fail("Expected exception was not thrown!");
    } catch (JobsException e) {
      // then
    }
  }


  @Test
  public void parseCommandLine_Args__1() throws Exception {
    String[] args = new String[] {"-c", "config/local.yaml", "-l",
        "/Users/CWS-NS3/client_indexer_time.txt", "-t", "4", "-x", "99"};
    JobOptions.parseCommandLine(args);
  }

  @Test
  public void parseCommandLine_Args__2() throws Exception {
    String[] args = new String[] {"-c", "config/local.yaml", "-b", "3", "-m", "4", "-r", "20-24",
        "-t", "4", "-x", "99", "-a", "2010-01-01 00:00:00", "-i", "my-index"};
    JobOptions.parseCommandLine(args);
  }

  @Test
  public void parseCommandLine_Args__3() throws Exception {
    String[] args = new String[] {"-c", "config/local.yaml", "-b", "3", "-m", "4", "-r", "20-24",
        "-t", "4", "-x", "99", "-a", "2010-01-01 00:00:gg", "-i", "my-index"};
    try {
      JobOptions.parseCommandLine(args);
      fail("Expected exception was not thrown!");
    } catch (JobsException e) {
      // do nothing
    }
  }

  @Test
  public void parseCommandLine_Args__4() throws Exception {
    String[] args = new String[] {"-c", "config/local.yaml", "-b", "g", "-m", "4", "-r", "20-24",
        "-t", "4", "-x", "99", "-a", "2010-01-01 00:00:gg", "-i", "my-index"};
    try {
      JobOptions.parseCommandLine(args);
      fail("Expected exception was not thrown!");
    } catch (JobsException e) {
      // do nothing
    }
  }

  @Test
  public void instantiation() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    assertThat(target, notNullValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void makeOpt_Args__String__String__String__all_null_args() throws Exception {
    // given
    String shortOpt = null;
    String longOpt = null;
    String description = null;

    Option actual = JobOptions.makeOpt(shortOpt, longOpt, description);
    Option expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  // @Test
  public void makeOpt_Args__String__String__String__boolean__int__Class__char() throws Exception {
    // given
    String shortOpt = null;
    String longOpt = null;
    String description = null;
    boolean required = false;
    int argc = 0;
    Class<?> type = mock(Class.class);
    char sep = ' ';

    Option actual = JobOptions.makeOpt(shortOpt, longOpt, description, required, argc, type, sep);
    Option expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMinId_Args__() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);

    String actual = target.getMinId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaxId_Args__() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);

    String actual = target.getMaxId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartBucket_Args__long() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    long startBucket_ = 0L;
    target.setStartBucket(startBucket_);
  }

  @Test
  public void setEndBucket_Args__long() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    long endBucket_ = 0L;
    target.setEndBucket(endBucket_);
  }

  @Test
  public void setThreadCount_Args__long() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    long threadCount_ = 0L;
    target.setThreadCount(threadCount_);
  }

  @Test
  public void setMinId_Args__String() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    String minId_ = null;
    target.setMinId(minId_);
  }

  @Test
  public void setMaxId_Args__String() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    String maxId_ = null;
    target.setMaxId(maxId_);
  }

  @Test
  public void setTotalBuckets_Args__long() throws Exception {
    String esConfigLoc = null;
    String lastRunLoc = null;
    boolean lastRunMode = false;
    long startBucket = 0L;
    long endBucket = 0L;
    long totalBuckets = 0L;
    long threadCount = 0L;
    String minId = null;
    String maxId = null;
    JobOptions target = new JobOptions(esConfigLoc, null, null, lastRunLoc, lastRunMode,
        startBucket, endBucket, totalBuckets, threadCount, minId, maxId, true, null);
    long totalBuckets_ = 0L;
    target.setTotalBuckets(totalBuckets_);
  }

}
