package gov.ca.cwds.jobs.cals.facility;

import static gov.ca.cwds.jobs.cals.facility.FacilityIncrementalLoadDateStrategy.RUNNING_FILE_NAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-2
 */
public class FacilityIncrementalLoadDateStrategyTest {

  @Before
  public void before() throws IOException {
    Files.deleteIfExists(Paths.get(RUNNING_FILE_NAME));
  }

  @Test
  public void testLoadDateStrategy() {
    IncrementalLoadDateStrategy incrementalLoadDateStrategy = new FacilityIncrementalLoadDateStrategy();

    LocalDateTime calculatedTime0 = toLocal(incrementalLoadDateStrategy.calculate());
    assertBefore(calculatedTime0, LocalDateTime.now().minusYears(99));

    LocalDateTime calculatedTime1 = toLocal(incrementalLoadDateStrategy.calculate());
    LocalDateTime now = LocalDateTime.now();
    assertBetween(calculatedTime1, now.minusMinutes(1), now);

    LocalDateTime calculatedTime2 = toLocal(incrementalLoadDateStrategy.calculate());
    assertAfter(calculatedTime2, calculatedTime1);
  }

  private static LocalDateTime toLocal(final Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  /**
   * asserts if tAfter is after tBefore
   *
   * @param tAfter some moment in time
   * @param tBefore some moment in time
   */
  private void assertAfter(LocalDateTime tAfter, LocalDateTime tBefore) {
    assertThat(tAfter.compareTo(tBefore), is(equalTo(1)));
  }

  /**
   * asserts if tBefore is before tAfter
   *
   * @param tBefore some moment in time
   * @param tAfter some moment in time
   */
  private void assertBefore(LocalDateTime tBefore, LocalDateTime tAfter) {
    assertThat(tBefore.compareTo(tAfter), is(equalTo(-1)));
  }

  /**
   * asserts if tBetween is between tBefore and tAfter
   *
   * @param tBetween some moment in time
   * @param tBefore some moment in time
   * @param tAfter some moment in time
   */
  private void assertBetween(LocalDateTime tBetween, LocalDateTime tBefore, LocalDateTime tAfter) {
    assertBefore(tBefore, tBetween);
    assertAfter(tAfter, tBetween);
  }
}
