package gov.ca.cwds.jobs.cals.facility;

import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import gov.ca.cwds.rest.api.ApiException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Created by TPT-2 team on 6/13/2017.
 */
public class FacilityIncrementalLoadDateStrategy implements IncrementalLoadDateStrategy {
  static final String RUNNING_FILE_NAME = FacilityIncrementalLoadDateStrategy.class.getSimpleName() + "-running";
  private static final String DATE_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  @Override
  public Date calculate() throws ApiException {
    try {
      Path runningFile = Paths.get(RUNNING_FILE_NAME);
      LocalDateTime now = LocalDateTime.now();
      String currentDate = now.format(dateTimeFormatter);
      LocalDateTime result;
      if (Files.exists(runningFile)) {
        //not a first time for this job
        Optional<String> firstLine = Files.lines(runningFile).findFirst();
        if(!firstLine.isPresent()) {
          throw new ApiException("Wrong date file format!");
        }
        String lastDate = firstLine.get();
        Files.write(runningFile, currentDate.getBytes(), WRITE, TRUNCATE_EXISTING);
        result = LocalDateTime.parse(lastDate, dateTimeFormatter);
      } else {
        //first time for this job
        result = now.minusYears(100);
        Files.write(runningFile, currentDate.getBytes(), WRITE, CREATE_NEW);
      }

      return Date.from(result.atZone(ZoneId.systemDefault()).toInstant());
    } catch (Exception e) {
      throw new ApiException("Failed to calculate date after", e);
    }
  }
}
