package gov.ca.cwds.neutron.rocket.cases;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Convenient carrier bean for focus child / parent relationships.
 * 
 * @author CWDS API Team
 */
public class FocusChildParent implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private String focusClientId;
  private String parentClientId;
  private short relationCode;
  private String parentFirstName;
  private String parentLastName;

  public FocusChildParent(String focusClientId, String parentClientId, short relationCode,
      String parentFirstName, String parentLastName) {
    this.parentClientId = parentClientId;
    this.focusClientId = focusClientId;
    this.relationCode = relationCode;
  }

  public static FocusChildParent extract(final ResultSet rs) throws SQLException {
    return new FocusChildParent(rs.getString("FOCUS_CHILD_ID"), rs.getString("R_CLIENT_ID"),
        rs.getShort("CLNTRELC"), rs.getString("R_FIRST"), rs.getString("R_LAST"));
  }

  public String getRelatedClientId() {
    return parentClientId;
  }

  public void setRelatedClientId(String clientId) {
    this.parentClientId = clientId;
  }

  public String getFocusClientId() {
    return focusClientId;
  }

  public void setFocusClientId(String focusClientId) {
    this.focusClientId = focusClientId;
  }

  public short getRelationCode() {
    return relationCode;
  }

  public void setRelationCode(short relationCode) {
    this.relationCode = relationCode;
  }

  public SystemCode translateRelationship() {
    return SystemCodeCache.global().getSystemCode(this.relationCode);
  }

  public String translateRelationshipToString() {
    final SystemCode sc = translateRelationship();
    return sc != null ? sc.getShortDescription() : null;
  }

  public String getParentClientId() {
    return parentClientId;
  }

  public void setParentClientId(String parentClientId) {
    this.parentClientId = parentClientId;
  }

  public String getParentFirstName() {
    return parentFirstName;
  }

  public void setParentFirstName(String parentFirstName) {
    this.parentFirstName = parentFirstName;
  }

  public String getParentLastName() {
    return parentLastName;
  }

  public void setParentLastName(String parentLastName) {
    this.parentLastName = parentLastName;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
