package gov.ca.cwds.neutron.rocket.cases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Obsolete carrier bean for client/case/relative combinations. All-in-one approach is too complex.
 * 
 * @author CWDS API Team
 * @see FocusChildParent
 */
public class CaseClientRelative implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final Set<Short> setParentCodes = ConcurrentHashMap.newKeySet();

  private String focusClientId;
  private String relatedClientId;
  private String caseId;
  private short relationCode;
  private boolean leftSideFocusChild;

  /**
   * SonarQube complains about code ranges in conditional checks, so we stuff all the parent code
   * into a set.
   */
  static {
    addCodes(187, 214);
    addCodes(245, 254);
    addCodes(282, 294);
    addCodes(272, 273, 5620, 6360, 6361);
  }

  public CaseClientRelative(String caseId, String focusClientId, String thisClientId,
      short relationCode, boolean leftSideFocusChild) {
    this.relatedClientId = thisClientId;
    this.caseId = caseId;
    this.focusClientId = focusClientId;
    this.relationCode = relationCode;
    this.leftSideFocusChild = leftSideFocusChild;
  }

  private static void addCodes(int begin, int end) {
    IntStream.rangeClosed(begin, end).boxed().map(i -> (short) i.intValue())
        .forEach(setParentCodes::add);
  }

  private static void addCodes(int... x) {
    IntStream.of(x).boxed().map(i -> (short) i.intValue()).forEach(setParentCodes::add);
  }

  public static CaseClientRelative extract(final ResultSet rs) throws SQLException {
    return new CaseClientRelative(rs.getString("CASE_ID"), rs.getString("FOCUS_CHILD_ID"),
        rs.getString("THIS_CLIENT_ID"), rs.getShort("RELATION"),
        "Y".equalsIgnoreCase(rs.getString("LEFT_IS_FOCUS")));
  }

  public boolean isParentRelation() {
    return hasRelation() && setParentCodes.contains(this.relationCode);
  }

  public boolean hasRelation() {
    return this.relationCode != (short) 0 && StringUtils.isNotBlank(relatedClientId)
        && !"0".equals(relatedClientId) && !relatedClientId.equals(focusClientId);
  }

  public boolean hasNoRelation() {
    return !hasRelation();
  }

  public String getRelatedClientId() {
    return relatedClientId;
  }

  public void setRelatedClientId(String clientId) {
    this.relatedClientId = clientId;
  }

  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String referralId) {
    this.caseId = referralId;
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

  public boolean isLeftSideFocusChild() {
    return leftSideFocusChild;
  }

  public void setLeftSideFocusChild(boolean leftSideFocusChild) {
    this.leftSideFocusChild = leftSideFocusChild;
  }

  public SystemCode translateRelationship() {
    SystemCode sc = SystemCodeCache.global().getSystemCode(this.relationCode);
    if (sc != null && !isLeftSideFocusChild() && StringUtils.isNotBlank(sc.getLongDescription())) {
      sc = SystemCodeCache.global().getSystemCode(Integer.valueOf(sc.getLongDescription()));
    }
    return sc;
  }

  public String translateRelationshipToString() {
    final SystemCode sc = translateRelationship();
    return sc != null ? sc.getShortDescription() : null;
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
