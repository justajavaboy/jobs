package gov.ca.cwds.jobs.facility;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.ca.cwds.jobs.util.JobLogUtils;
import gov.ca.cwds.jobs.util.jdbc.RowMapper;

/**
 * @author CWDS Elasticsearch Team
 */
public class FacilityRowMapper implements RowMapper<FacilityRow> {

  private static final Logger LOGGER = LogManager.getLogger(FacilityRowMapper.class);

  @Override
  public FacilityRow mapRow(ResultSet resultSet) throws SQLException {
    FacilityRow facility = new FacilityRow();
    mapFields(facility, resultSet);
    return facility;
  }

  private void mapFields(Object object, ResultSet resultSet) { // NOSONAR
    for (Field field : object.getClass().getDeclaredFields()) {
      mapField(object, field, resultSet);
    }
  }

  private void mapField(Object object, Field field, ResultSet resultSet) {
    try {
      PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), object.getClass());
      if (field.getType().equals(String.class)) {
        String value = resultSet.getString(field.getName());
        descriptor.getWriteMethod().invoke(object, value == null ? "" : value.trim());
      } else if (field.getType().equals(Date.class)) {
        Date value = resultSet.getTimestamp(field.getName());
        descriptor.getWriteMethod().invoke(object, value);
      }
    } catch (Exception e) {
      JobLogUtils.raiseError(LOGGER, e, e.getMessage());
    }
  }

}