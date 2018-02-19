package gov.ca.cwds.neutron.vox.jmx;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import gov.ca.cwds.jobs.Goddard;
import gov.ca.cwds.neutron.exception.NeutronCheckedException;
import gov.ca.cwds.neutron.vox.VoxCommandInstruction;

public class VoxCommandFactoryTest extends Goddard {

  @Test
  public void type() throws Exception {

    assertThat(VoxCommandFactory.class, notNullValue());
  }

  @Test
  public void build_A$VoxCommandType$VoxCommandInstruction() throws Exception {


    VoxCommandType cmdType = mock(VoxCommandType.class);
    VoxCommandInstruction cmd = mock(VoxCommandInstruction.class);


    VoxJMXCommandClient actual = VoxCommandFactory.build(cmdType, cmd);


    VoxJMXCommandClient expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void build_A$VoxCommandType$VoxCommandInstruction_T$NeutronCheckedException()
      throws Exception {


    VoxCommandType cmdType = mock(VoxCommandType.class);
    VoxCommandInstruction cmd = mock(VoxCommandInstruction.class);

    try {

      VoxCommandFactory.build(cmdType, cmd);
      fail("Expected exception was not thrown!");
    } catch (NeutronCheckedException e) {

    }
  }

  @Test
  public void launch_A$StringArray() throws Exception {


    String[] args = new String[] {};


    VoxCommandFactory.launch(args);


  }

  @Test
  public void launch_A$StringArray_T$NeutronCheckedException() throws Exception {


    String[] args = new String[] {};

    try {

      VoxCommandFactory.launch(args);
      fail("Expected exception was not thrown!");
    } catch (NeutronCheckedException e) {

    }
  }

  @Test
  public void main_A$StringArray() throws Exception {


    String[] args = new String[] {};


    VoxCommandFactory.main(args);


  }

  @Test
  public void main_A$StringArray_T$Exception() throws Exception {


    String[] args = new String[] {};

    try {

      VoxCommandFactory.main(args);
      fail("Expected exception was not thrown!");
    } catch (Exception e) {

    }
  }

}
