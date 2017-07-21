/**
 * <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017
 * Chen Chao.
 **/
package gov.ca.cwds.data.persistence.cms.rep;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.BaseServiceProvider;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedServiceProviderR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.AGENCY_NM, z.CITY_NM, z.FAX_NO, z.FIRST_NM, z.LAST_NM, trim(z.NMPRFX_DSC) NMPRFX_DSC, z.PHONE_NO, z.TEL_EXT_NO, trim(z.PSTITL_DSC) PSTITL_DSC, z.SVCPVDRC, z.STATE_C, z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NM, z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, z.ARCASS_IND, z.EMAIL_ADDR, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}SVC_PVRT z WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR ",
        resultClass = ReplicatedServiceProviderR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedServiceProviderR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.AGENCY_NM, z.CITY_NM, z.FAX_NO, z.FIRST_NM, z.LAST_NM, "
            + "trim(z.NMPRFX_DSC) NMPRFX_DSC, z.PHONE_NO, z.TEL_EXT_NO, trim(z.PSTITL_DSC) PSTITL_DSC, "
            + "z.SVCPVDRC, z.STATE_C, z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NM, "
            + "z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, z.ARCASS_IND, z.EMAIL_ADDR, "
            + "z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER FROM {h-schema}SVC_PVRT z "
            + "WHERE z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedServiceProviderR1.class)})
@Entity
@Table(name = "SVC_PVRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedServiceProviderR1 extends BaseServiceProvider
    implements CmsReplicatedEntity {

  private static final long serialVersionUID = 1L;

  @Enumerated(EnumType.STRING)
  @Column(name = "IBMSNAP_OPERATION", updatable = false)
  private CmsReplicationOperation replicationOperation;

  @Type(type = "timestamp")
  @Column(name = "IBMSNAP_LOGMARKER", updatable = false)
  private Date replicationDate;

  @Override
  public CmsReplicationOperation getReplicationOperation() {
    return this.replicationOperation;
  }

  @Override
  public void setReplicationOperation(CmsReplicationOperation replicationOperation) {
    this.replicationOperation = replicationOperation;
  }

  @Override
  public Date getReplicationDate() {
    return this.replicationDate;
  }

  @Override
  public void setReplicationDate(Date replicationDate) {
    this.replicationDate = replicationDate;
  }
}
