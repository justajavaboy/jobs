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
    LocalDateTime time99yearsAgo = LocalDateTime.now().minusYears(99);
    assertThat(calculatedTime0.compareTo(time99yearsAgo), is(equalTo(-1)));

    LocalDateTime calculatedTime1 = toLocal(incrementalLoadDateStrategy.calculate());
    LocalDateTime time1minuteAgo = LocalDateTime.now().minusMinutes(1);
    assertThat(calculatedTime1.compareTo(time1minuteAgo), is(equalTo(1)));
    assertThat(calculatedTime1.compareTo(LocalDateTime.now()), is(equalTo(-1)));

    LocalDateTime calculatedTime2 = toLocal(incrementalLoadDateStrategy.calculate());
    assertThat(calculatedTime2.compareTo(calculatedTime1), is(equalTo(1)));
  }

  private static LocalDateTime toLocal(final Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
