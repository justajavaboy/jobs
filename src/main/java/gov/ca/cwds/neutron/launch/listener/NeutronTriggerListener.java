package gov.ca.cwds.neutron.launch.listener;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.neutron.jetpack.CheeseRay;
import gov.ca.cwds.neutron.launch.LaunchDirector;
import gov.ca.cwds.neutron.launch.NeutronRocket;
import gov.ca.cwds.neutron.util.NeutronThreadUtils;

/**
 * Neutron implementation of Quartz TriggerListener.
 * 
 * @author CWDS API Team
 */
public class NeutronTriggerListener implements TriggerListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(NeutronTriggerListener.class);

  private final LaunchDirector neutronScheduler;

  @Inject
  public NeutronTriggerListener(final LaunchDirector neutronScheduler) {
    this.neutronScheduler = neutronScheduler;
    NeutronThreadUtils.nameThread("neutron_trigger_listener", this);
  }

  @Override
  public String getName() {
    return "neutron_trigger_listener";
  }

  @Override
  public void triggerFired(Trigger trigger, JobExecutionContext context) {
    final TriggerKey key = trigger.getKey();
    LOGGER.debug("trigger fired: key: {}", key);
    neutronScheduler.getRocketsInFlight().put(key, (NeutronRocket) context.getJobInstance());
  }

  /**
   * Quartz Job instance type is {@link NeutronRocket}.
   */
  @Override
  public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
    final JobDataMap map = context.getJobDetail().getJobDataMap();
    final String className = map.getString("job_class");
    boolean answer = true;

    try {
      answer = neutronScheduler.isLaunchVetoed(className);
    } catch (Exception e) {
      throw CheeseRay.runtime(LOGGER, e, "NO LAUNCH PAD! rocket class: {}", className, e);
    }

    LOGGER.debug("veto job execution: {}", answer);
    return answer;
  }

  @Override
  public void triggerMisfired(Trigger trigger) {
    final TriggerKey key = trigger.getKey();
    LOGGER.info("TRIGGER MISFIRED! key: {}", key);
    neutronScheduler.removeExecutingJob(key);
  }

  @Override
  public void triggerComplete(Trigger trigger, JobExecutionContext context,
      CompletedExecutionInstruction triggerInstructionCode) {
    final TriggerKey key = trigger.getKey();
    LOGGER.debug("TRIGGER COMPLETE: key: {}", key);
    neutronScheduler.removeExecutingJob(key);
  }

}
