package gov.ca.cwds.jobs.cals.facility;

import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import gov.ca.cwds.rest.api.ApiException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.*;

/**
 * Created by TPT-2 team on 6/13/2017.
 */
public class FacilityIncrementalLoadDate implements IncrementalLoadDateStrategy {
  private static final String RUNNING_FILE_NAME = FacilityIncrementalLoadDate.class.getSimpleName() + "-running";
  private static final String DATE_FORMAT = "yyyy-MM-dd";

  @Override
  public Date calculate() throws ApiException {
    try {
      Path runningFile = Paths.get(RUNNING_FILE_NAME);
      LocalDate date = LocalDate.now();
      String currentDate = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
      if (Files.exists(runningFile)) {
        Optional<String> firstLine = Files.lines(runningFile).findFirst();
        if(!firstLine.isPresent()) {
          throw new ApiException("Wrong date file format!");
        }
        String runningDate = firstLine.get();
        if (!currentDate.equals(runningDate)) {
          //first time for this day
          date = date.minusDays(2);
          Files.write(runningFile, currentDate.getBytes(), WRITE, TRUNCATE_EXISTING);
        } else {
          //not first time for this day
          date = date.minusDays(1);
        }
      } else {
        //first time for this job
        date = date.minusYears(100);
        Files.write(runningFile, currentDate.getBytes(), WRITE, CREATE_NEW);
      }
      return Date.valueOf(date);
    } catch (Exception e) {
      throw new ApiException("Failed to calculate date after", e);
    }
  }
}
