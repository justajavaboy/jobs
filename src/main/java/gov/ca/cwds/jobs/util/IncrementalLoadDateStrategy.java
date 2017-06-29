package gov.ca.cwds.jobs.util;

import gov.ca.cwds.rest.api.ApiException;

import java.util.Date;

/**
 * Created by TPT-2 team on 6/13/2017.
 * Calculates date for incremental load
 */
public interface IncrementalLoadDateStrategy {
  /**
   *  Must be called inside batch transaction
   * @return date after
   */
  Date calculate() throws ApiException;
}