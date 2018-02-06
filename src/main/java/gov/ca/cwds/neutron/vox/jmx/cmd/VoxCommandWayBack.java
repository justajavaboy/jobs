package gov.ca.cwds.neutron.vox.jmx.cmd;

import org.apache.commons.lang3.exception.ExceptionUtils;

import gov.ca.cwds.neutron.jetpack.ConditionalLogger;
import gov.ca.cwds.neutron.jetpack.JetPackLogger;
import gov.ca.cwds.neutron.vox.jmx.VoxJMXCommandClient;

public class VoxCommandWayBack extends VoxJMXCommandClient {

  private static final ConditionalLogger LOGGER = new JetPackLogger(VoxCommandWayBack.class);

  public VoxCommandWayBack() {
    super();
  }

  public VoxCommandWayBack(String host, String port) {
    super(host, port);
  }

  @Override
  public String run() {
    final String input = getArgs().trim();

    try {
      final int hours = Integer.parseInt(input);
      getMbean().waybackHours(hours);
      return String.format("WAY BACK MACHINE! {} hours in past", hours);
    } catch (Exception e) {
      LOGGER.error("WAY BACK MACHINE: ERROR PARSING {}", input, e);
      return ExceptionUtils.getStackTrace(e);
    }
  }

}
