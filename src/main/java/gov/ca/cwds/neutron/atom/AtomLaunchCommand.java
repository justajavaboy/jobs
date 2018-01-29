package gov.ca.cwds.neutron.atom;

import gov.ca.cwds.neutron.exception.NeutronCheckedException;

public interface AtomLaunchCommand {

  void stopScheduler(boolean waitForJobsToComplete) throws NeutronCheckedException;

  void startScheduler() throws NeutronCheckedException;

  void shutdown() throws NeutronCheckedException;

}
