package gov.ca.cwds.neutron.util.shrinkray;

import java.util.Calendar;
import java.util.Date;

import gov.ca.cwds.neutron.enums.NeutronIntegerDefaults;

public class NeutronDateUtils {

  private NeutronDateUtils() {
    // no-op
  }

  public static Date freshDate(Date incoming) {
    return incoming != null ? new Date(incoming.getTime()) : null;
  }

  public static Date lookBack(final Date lastRunTime) {
    final Calendar cal = Calendar.getInstance();
    cal.setTime(lastRunTime);
    cal.add(Calendar.MINUTE, NeutronIntegerDefaults.LOOKBACK_MINUTES.getValue());
    return cal.getTime();
  }

}
