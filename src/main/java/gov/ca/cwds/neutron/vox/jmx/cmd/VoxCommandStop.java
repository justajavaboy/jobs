package gov.ca.cwds.neutron.vox.jmx.cmd;

import org.apache.commons.lang3.exception.ExceptionUtils;

import gov.ca.cwds.neutron.exception.NeutronCheckedException;
import gov.ca.cwds.neutron.jetpack.ConditionalLogger;
import gov.ca.cwds.neutron.jetpack.JetPackLogger;
import gov.ca.cwds.neutron.vox.jmx.VoxJMXCommandClient;

public class VoxCommandStop extends VoxJMXCommandClient {

  private static final ConditionalLogger LOGGER = new JetPackLogger(VoxCommandStop.class);

  public VoxCommandStop() {
    super();
  }

  public VoxCommandStop(String host, String port) {
    super(host, port);
  }

  @Override
  public String run() {
    LOGGER.info("RESUME {}!", getRocket());
    try {
      getMbean().resume();
      return String.format("RESUME {}!", getRocket());
    } catch (NeutronCheckedException e) {
      LOGGER.error("ERROR RESUMING {}!", getRocket(), e);
      return ExceptionUtils.getStackTrace(e);
    }
  }

}
