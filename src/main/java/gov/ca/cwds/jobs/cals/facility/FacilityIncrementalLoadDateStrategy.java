package gov.ca.cwds.jobs.cals.facility;

import gov.ca.cwds.jobs.cals.BaseIncrementalLoadDateStrategy;

/**
 * Created by TPT-2 team on 6/13/2017.
 */
public final class FacilityIncrementalLoadDateStrategy extends BaseIncrementalLoadDateStrategy {

  static final String RUNNING_FILE_NAME = "CALS_Facility_last_load_time";

  @Override
  protected String getDateFileName() {
    return RUNNING_FILE_NAME;
  }
}
