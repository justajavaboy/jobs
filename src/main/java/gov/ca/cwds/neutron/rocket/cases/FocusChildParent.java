package gov.ca.cwds.neutron.rocket.cases;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
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

  private int stanza;

  private short relationCode;
  private String focusClientId;

  private String parentClientId;
  private String parentFirstName;
  private String parentLastName;
  private String parentSensitivity = "N";

  public FocusChildParent(int stanza, String focusClientId, String parentClientId,
      short relationCode, String parentFirstName, String parentLastName, String parentSensitivity) {
    this.focusClientId = focusClientId;
    this.parentClientId = parentClientId;
    this.parentFirstName = parentFirstName;
    this.parentLastName = parentLastName;
    this.parentSensitivity = parentSensitivity;

    this.relationCode = relationCode;

    SystemCode sc = SystemCodeCache.global().getSystemCode(this.relationCode);
    if (sc != null && StringUtils.isNotBlank(sc.getLongDescription())) {
      sc = SystemCodeCache.global().getSystemCode(Integer.valueOf(sc.getLongDescription()));
      this.relationCode = sc.getSystemId();
    }
  }

  public static FocusChildParent extract(final ResultSet rs) throws SQLException {
    return new FocusChildParent(1, rs.getString("FOCUS_CHILD_ID"), rs.getString("R_CLIENT_ID"),
        rs.getShort("REL_CODE"), rs.getString("R_FIRST"), rs.getString("R_LAST"),
        rs.getString("R_SENSTV_IND"));
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

  public String getParentSensitivity() {
    return parentSensitivity;
  }

  public void setParentSensitivity(String parentSensitivity) {
    this.parentSensitivity = parentSensitivity;
  }

  public int getStanza() {
    return stanza;
  }

  public void setStanza(int stanza) {
    this.stanza = stanza;
  }

}
