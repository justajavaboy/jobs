package gov.ca.cwds.neutron.launch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

import gov.ca.cwds.jobs.ClientIndexerJob;
import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.neutron.atom.AtomFlightRecorder;
import gov.ca.cwds.neutron.atom.AtomLaunchDirector;
import gov.ca.cwds.neutron.enums.NeutronSchedulerConstants;
import gov.ca.cwds.neutron.exception.NeutronCheckedException;
import gov.ca.cwds.neutron.flight.FlightLog;
import gov.ca.cwds.neutron.flight.FlightPlan;

public class LaunchPadTest extends Goddard {

  StandardFlightSchedule sched;
  LaunchPad target;
  // File tempFile = tempFolder.newFile("tempFile.txt");


  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    flightPlan = new FlightPlan();
    when(launchDirector.getFlightRecorder()).thenReturn(flightRecorder);

    sched = StandardFlightSchedule.CLIENT;
    target = new LaunchPad(launchDirector, sched, flightPlan);
  }

  @Test
  public void type() throws Exception {
    assertThat(LaunchPad.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void run_Args__String() throws Exception {
    String cmdLineArgs = null;
    String actual = target.run(cmdLineArgs);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void schedule_Args__() throws Exception {
    target.schedule();
  }

  @Test(expected = NeutronCheckedException.class)
  public void schedule_Args___T__SchedulerException() throws Exception {
    when(scheduler.getJobDetail(any(JobKey.class))).thenThrow(SchedulerException.class);
    when(launchDirector.launch(any(Class.class), any(FlightPlan.class)))
        .thenThrow(SchedulerException.class);
    when(scheduler.checkExists(any(JobKey.class))).thenThrow(SchedulerException.class);
    target.schedule();
  }

  @Test
  public void unschedule_Args__() throws Exception {
    target.unschedule();
  }

  @Test(expected = NeutronCheckedException.class)
  public void unschedule_Args___T__SchedulerException() throws Exception {
    doThrow(SchedulerException.class).when(scheduler).pauseTrigger(any(TriggerKey.class));
    when(scheduler.unscheduleJob(any(TriggerKey.class))).thenThrow(SchedulerException.class);
    target.unschedule();
  }

  @Test
  public void status_Args__() throws Exception {
    JobDetail jd = mock(JobDetail.class);
    when(scheduler.getJobDetail(any(JobKey.class))).thenReturn(jd);
    JobDataMap jdm = new JobDataMap();
    jdm.put("job_class", "TestNeutronJob");
    jdm.put("cmd_line", "--invalid");

    final FlightLog track = new FlightLog();
    jdm.put("track", track);
    when(jd.getJobDataMap()).thenReturn(jdm);

    flightRecorder.logFlight(ClientIndexerJob.class, track);
    target.status();
  }

  @Test
  public void stop_Args__() throws Exception {
    target.stop();
  }

  @Test(expected = NeutronCheckedException.class)
  public void stop_Args___T__SchedulerException() throws Exception {
    doThrow(SchedulerException.class).when(scheduler).pauseTrigger(any(TriggerKey.class));
    when(scheduler.interrupt(any(JobKey.class))).thenThrow(SchedulerException.class);
    target.stop();
  }

  @Test
  public void history_Args__() throws Exception {
    launchDirector = new LaunchDirector(flightRecorder, rocketFactory, flightPlanManager);
    launchDirector.setScheduler(scheduler);
    String actual = target.history();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void isVetoExecution_Args__() throws Exception {
    boolean actual = target.isVetoExecution();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVetoExecution_Args__boolean() throws Exception {
    boolean vetoExecution = false;
    target.setVetoExecution(vetoExecution);
  }

  @Test
  public void getJd_Args__() throws Exception {
    target.schedule();
    JobDetail actual = target.getJd();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getOpts_Args__() throws Exception {
    FlightPlan actual = target.getFlightPlan();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setOpts_Args__FlightPlan() throws Exception {
    FlightPlan opts_ = mock(FlightPlan.class);
    target.setFlightPlan(opts_);
  }

  @Test
  public void getFlightPlan_Args__() throws Exception {
    FlightPlan actual = target.getFlightPlan();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setFlightPlan_Args__FlightPlan() throws Exception {
    FlightPlan opts_ = mock(FlightPlan.class);
    target.setFlightPlan(opts_);
  }

  @Test
  public void getFlightSchedule_Args__() throws Exception {
    StandardFlightSchedule actual = target.getFlightSchedule();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getFlightRecorder_Args__() throws Exception {
    AtomFlightRecorder actual = target.getFlightRecorder();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getJobName_Args__() throws Exception {
    String actual = target.getRocketName();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getTriggerName_Args__() throws Exception {
    String actual = target.getTriggerName();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getJobKey_Args__() throws Exception {
    JobKey actual = target.getJobKey();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getLaunchScheduler_Args__() throws Exception {
    AtomLaunchDirector actual = target.getLaunchDirector();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void run_A$String() throws Exception {
    String cmdLine = null;
    String actual = target.run(cmdLine);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void schedule_A$() throws Exception {
    target.schedule();
  }

  @Test
  public void unschedule_A$() throws Exception {
    target.unschedule();
  }

  @Test
  public void status_A$() throws Exception {
    String actual = target.status();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void history_A$() throws Exception {
    String actual = target.history();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void logs_A$() throws Exception {
    final File folder = tempFolder.newFolder("jobrunner", "rocketlog");
    final File logFile = new File(folder, "client.log");
    FileUtils.writeStringToFile(logFile,
        "It's alive!\nWe have a winner!\nLifetime supply of Twinkies!");

    flightPlan
        .setBaseDirectory(tempFolder.getRoot().getAbsolutePath() + File.separator + "jobrunner");

    final String actual = target.logs();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void resetTimestamp_A$boolean$int() throws Exception {
    boolean initialMode = false;
    int hoursInPast = 0;
    target.resetTimestamp(initialMode, hoursInPast);
  }

  @Test
  public void waybackHours_A$int() throws Exception {
    final int hoursInPast = 0;
    target.waybackHours(hoursInPast);
  }

  @Test
  public void stop_A$() throws Exception {
    target.stop();
  }

  @Test
  public void pause_A$() throws Exception {
    target.pause();
  }

  @Test
  public void resume_A$() throws Exception {
    target.resume();
  }

  @Test
  public void threadShutdownLaunchCommand_A$() throws Exception {
    target.threadShutdownLaunchCommand();
  }

  @Test
  public void shutdown_A$() throws Exception {
    final String actual = target.shutdown();
    final String expected = "Requested shutdown!";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getJd_A$() throws Exception {
    final JobDetail actual = target.getJd();
    final JobDetail expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFlightSchedule_A$() throws Exception {
    final StandardFlightSchedule actual = target.getFlightSchedule();
    final StandardFlightSchedule expected = StandardFlightSchedule.CLIENT;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFlightRecorder_A$() throws Exception {
    final AtomFlightRecorder actual = target.getFlightRecorder();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getRocketName_A$() throws Exception {
    final String actual = target.getRocketName();
    final String expected = StandardFlightSchedule.CLIENT.getRocketName();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTriggerName_A$() throws Exception {
    final String actual = target.getTriggerName();
    final String expected = StandardFlightSchedule.CLIENT.getRocketName();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getJobKey_A$() throws Exception {
    final JobKey actual = target.getJobKey();
    final JobKey expected = new JobKey(StandardFlightSchedule.CLIENT.getRocketName(),
        NeutronSchedulerConstants.GRP_LST_CHG);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLaunchDirector_A$() throws Exception {
    final AtomLaunchDirector actual = target.getLaunchDirector();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getTriggerKey_A$() throws Exception {
    final TriggerKey actual = target.getTriggerKey();
    final TriggerKey expected = new TriggerKey(StandardFlightSchedule.CLIENT.getRocketName(),
        NeutronSchedulerConstants.GRP_LST_CHG);
    assertThat(actual, is(equalTo(expected)));
  }

}
