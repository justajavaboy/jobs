package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.neutron.rocket.ClientSQLResource;

/**
 * Entity bean for view CWSTMP.DBREFSTA.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "DBREFSTA")
//@formatter:off
@NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.EsClientAddress.findAllUpdatedAfter",
    query = "SELECT " + ClientSQLResource.LAST_CHG_COLUMNS + "\n"
        + "FROM {h-schema}VW_LST_CLIENT_ADDRESS x \n"
        + "WHERE (1=1 OR x.LAST_CHG > :after) \n"
        + "ORDER BY CLT_IDENTIFIER, CLA_IDENTIFIER, ADR_IDENTIFIER \n"
        + "FOR READ ONLY WITH UR ",
    resultClass = DatabaseResetEntry.class, readOnly = true)

@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.EsClientAddress.findAllUpdatedAfterWithLimitedAccess",
        query = "SELECT " + ClientSQLResource.LAST_CHG_COLUMNS + "\n"
        + "FROM {h-schema}VW_LST_CLIENT_ADDRESS x \n"
        + "WHERE (1=1 OR x.LAST_CHG > :after) \n"
        + "AND x.CLT_SENSTV_IND != 'N' \n"
        + "ORDER BY CLT_IDENTIFIER, CLA_IDENTIFIER, ADR_IDENTIFIER \n"
        + "FOR READ ONLY WITH UR ",
    resultClass = DatabaseResetEntry.class, readOnly = true)
//@formatter:on
public class DatabaseResetEntry implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "CLT_CONF_EFIND")
  protected String cltConfidentialityInEffectIndicator;

  @Type(type = "date")
  @Column(name = "CLT_CREATN_DT")
  protected Date cltCreationDate;

  @Column(name = "CLT_CURRCA_IND")
  protected String cltCurrCaChildrenServIndicator;

  @Column(name = "CLT_COTH_DESC")
  protected String cltCurrentlyOtherDescription;

  // =====================
  // IDENTITY:
  // =====================

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
