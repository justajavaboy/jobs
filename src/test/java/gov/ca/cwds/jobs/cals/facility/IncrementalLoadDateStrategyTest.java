package gov.ca.cwds.jobs.cals.facility;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.jobs.cals.BaseIncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author CWDS TPT-2
 */
public class IncrementalLoadDateStrategyTest {

  private static final String RUNNING_FILE_NAME = "test_last_load_time";

  @BeforeClass
  public static void beforeClass() throws IOException {
    Files.deleteIfExists(Paths.get(RUNNING_FILE_NAME));
    Files.deleteIfExists(Paths.get(FacilityIncrementalLoadDateStrategy.RUNNING_FILE_NAME));
    Files.deleteIfExists(Paths.get(LISFacilityIncrementalLoadDateStrategy.RUNNING_FILE_NAME));
  }

  @AfterClass
  public static void afterClass() throws IOException {
    beforeClass();
  }

  @Test
  public void testBaseIncrementalLoadDateStrategy() {
    IncrementalLoadDateStrategy incrementalLoadDateStrategy = new BaseIncrementalLoadDateStrategy() {

      @Override
      protected String getDateFileName() {
        return RUNNING_FILE_NAME;
      }
    };

    LocalDateTime calculatedTime0 = toLocalDateTime(incrementalLoadDateStrategy.calculate());
    assertBefore(calculatedTime0, LocalDateTime.now().minusYears(99));

    LocalDateTime calculatedTime1 = toLocalDateTime(incrementalLoadDateStrategy.calculate());
    LocalDateTime now = LocalDateTime.now();
    assertBetween(calculatedTime1, now.minusMinutes(1), now);

    LocalDateTime calculatedTime2 = toLocalDateTime(incrementalLoadDateStrategy.calculate());
    assertAfter(calculatedTime2, calculatedTime1);
  }

  @Test
  public void testFacilityIncrementalLoadDateStrategy() {
    IncrementalLoadDateStrategy incrementalLoadDateStrategy = new FacilityIncrementalLoadDateStrategy();

    Date calculatedDate0 = incrementalLoadDateStrategy.calculate();
    assertThat(calculatedDate0, is(nullValue()));

    LocalDateTime calculatedTime1 = toLocalDateTime(incrementalLoadDateStrategy.calculate());
    LocalDateTime now = LocalDateTime.now();
    assertBetween(calculatedTime1, now.minusMinutes(1), now);

    LocalDateTime calculatedTime2 = toLocalDateTime(incrementalLoadDateStrategy.calculate());
    assertAfter(calculatedTime2, calculatedTime1);
  }

  @Test
  public void testLISFacilityIncrementalLoadDateStrategy() {
    IncrementalLoadDateStrategy incrementalLoadDateStrategy = new LISFacilityIncrementalLoadDateStrategy();

    LocalDate now = LocalDate.now();

    LocalDate calculatedDate0 = toLocalDate(incrementalLoadDateStrategy.calculate());
    assertBefore(calculatedDate0, now.minusYears(99));

    LocalDate calculatedDate1 = toLocalDate(incrementalLoadDateStrategy.calculate());
    assertBetween(calculatedDate1, now.minusDays(2), now);

    LocalDate calculatedDate2 = toLocalDate(incrementalLoadDateStrategy.calculate());
    assertThat(calculatedDate2, is(equalTo(calculatedDate1)));
  }

  private static LocalDateTime toLocalDateTime(final Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  private static LocalDate toLocalDate(final Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * asserts if time tAfter is after time tBefore
   *
   * @param tAfter some moment in time
   * @param tBefore some moment in time
   */
  private static void assertAfter(LocalDateTime tAfter, LocalDateTime tBefore) {
    assertThat(tAfter.compareTo(tBefore), is(equalTo(1)));
  }

  /**
   * asserts if time tBefore is before time tAfter
   *
   * @param tBefore some moment in time
   * @param tAfter some moment in time
   */
  private static void assertBefore(LocalDateTime tBefore, LocalDateTime tAfter) {
    assertThat(tBefore.compareTo(tAfter), is(equalTo(-1)));
  }

  /**
   * asserts if time tBetween is between time tBefore and time tAfter
   *
   * @param tBetween some moment in time
   * @param tBefore some moment in time
   * @param tAfter some moment in time
   */
  private static void assertBetween(LocalDateTime tBetween, LocalDateTime tBefore, LocalDateTime tAfter) {
    assertBefore(tBefore, tBetween);
    assertAfter(tAfter, tBetween);
  }

  /**
   * asserts if day dAfter is after day dBefore
   *
   * @param dAfter some day
   * @param dBefore some day
   */
  private static void assertAfter(LocalDate dAfter, LocalDate dBefore) {
    assertTrue(dAfter.compareTo(dBefore) > 0);
  }

  /**
   * asserts if day dBefore is before day dAfter
   *
   * @param dBefore some day
   * @param dAfter some day
   */
  private static void assertBefore(LocalDate dBefore, LocalDate dAfter) {
    assertTrue(dBefore.compareTo(dAfter) < 0);
  }

  /**
   * asserts if day dBetween is between day dBefore and day dAfter
   *
   * @param dBetween some day
   * @param dBefore some day
   * @param dAfter some day
   */
  private static void assertBetween(LocalDate dBetween, LocalDate dBefore, LocalDate dAfter) {
    assertBefore(dBefore, dBetween);
    assertAfter(dAfter, dBetween);
  }

}
