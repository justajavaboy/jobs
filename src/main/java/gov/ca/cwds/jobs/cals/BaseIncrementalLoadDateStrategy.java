package gov.ca.cwds.jobs.cals;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import gov.ca.cwds.rest.api.ApiException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * @author CWDS TPT-2
 */
public abstract class BaseIncrementalLoadDateStrategy implements IncrementalLoadDateStrategy {

  private static final String DATE_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern(DATE_FORMAT);

  protected abstract String getDateFileName();

  protected DateTimeFormatter getDateTimeFormatter() {
    return DATE_TIME_FORMATTER;
  }

  protected LocalDateTime getDateTimeForInitialLoad() {
    return LocalDateTime.now().minusYears(100);
  }

  @Override
  public Date calculate() {
    try {
      LocalDateTime now = LocalDateTime.now();
      Path runningFile = Paths.get(getDateFileName());

      LocalDateTime result = runningFile.toFile().exists() ? readLastRunDateTime(runningFile)
          : getDateTimeForInitialLoad();

      writeRunDateTime(runningFile, now);

      return result == null ? null : Date.from(result.atZone(ZoneId.systemDefault()).toInstant());

    } catch (Exception e) {
      throw new ApiException("Failed to calculate date after", e);
    }
  }

  private String readLastRunDateTimeString(Path runningFile) throws IOException {
    Optional<String> firstLine = Files.lines(runningFile).findFirst();
    if (!firstLine.isPresent()) {
      throw new ApiException("Wrong date file format!");
    }
    return firstLine.get();
  }

  protected LocalDateTime readLastRunDateTime(Path runningFile) throws IOException {
    return LocalDateTime.parse(readLastRunDateTimeString(runningFile), getDateTimeFormatter());
  }

  protected LocalDate readLastRunDate(Path runningFile) throws IOException {
    return LocalDate.parse(readLastRunDateTimeString(runningFile), getDateTimeFormatter());
  }

  protected void writeRunDateTime(Path runningFile, LocalDateTime dateTime) throws IOException {
    writeRunDateTime(runningFile, dateTime.format(getDateTimeFormatter()));
  }

  protected void writeRunDateTime(Path runningFile, String dateTime) throws IOException {
    Files.write(runningFile, dateTime.getBytes(), WRITE,
        runningFile.toFile().exists() ? TRUNCATE_EXISTING : CREATE_NEW);
  }
}
