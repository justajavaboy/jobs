package gov.ca.cwds.neutron.vox.jmx;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.neutron.exception.NeutronCheckedException;
import gov.ca.cwds.neutron.jetpack.ConditionalLogger;
import gov.ca.cwds.neutron.jetpack.JetPackLogger;
import gov.ca.cwds.neutron.jetpack.JobLogs;
import gov.ca.cwds.neutron.vox.VoxCommandInstruction;

public final class VoxCommandFactory implements ApiMarker {

  private static final long serialVersionUID = 1L;
  private static final ConditionalLogger LOGGER = new JetPackLogger(VoxCommandFactory.class);

  private VoxCommandFactory() {
    // Static class
  }

  public static VoxJMXCommandClient build(final VoxCommandType cmdType,
      final VoxCommandInstruction cmd) throws NeutronCheckedException {
    VoxJMXCommandClient ret;
    try {
      ret = (VoxJMXCommandClient) cmdType.getKlass().newInstance();
    } catch (Exception e) {
      throw JobLogs.checked(LOGGER, e, "JMX ERROR! host: {}, port: {}, rocket: {}", cmd.getHost(),
          cmd.getPort(), cmd.getRocket());
    }

    return ret;
  }

  public static void launch(String[] args) throws NeutronCheckedException {
    final VoxCommandInstruction cmd = VoxCommandInstruction.parseCommandLine(args);
    final VoxCommandType cmdType = VoxCommandType.lookup(cmd.getCommand());
    build(cmdType, cmd).launch(cmd);
  }

  public static void main(String[] args) throws Exception {
    launch(args);
  }

}
