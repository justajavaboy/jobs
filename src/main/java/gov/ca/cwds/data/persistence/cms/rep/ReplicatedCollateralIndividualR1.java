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

import gov.ca.cwds.data.persistence.cms.BaseCollateralIndividual;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedCollateralIndividualR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.BADGE_NO, z.CITY_NM, z.EMPLYR_NM, z.FAX_NO, z.FIRST_NM, z.FRG_ADRT_B, "
            + "z.LAST_NM, z.MID_INI_NM, z.NMPRFX_DSC, z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, z.STREET_NM, "
            + "z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, z.COMNT_DSC, "
            + "z.GENDER_CD, z.BIRTH_DT, z.MRTL_STC, z.EMAIL_ADDR, z.ESTBLSH_CD, z.ESTBLSH_ID, z.RESOST_IND, "
            + "IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER FROM {h-schema}COLTRL_T z "
            + "WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR",
        resultClass = ReplicatedCollateralIndividualR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedCollateralIndividualR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.BADGE_NO, z.CITY_NM, z.EMPLYR_NM, z.FAX_NO, z.FIRST_NM, z.FRG_ADRT_B, "
            + "z.LAST_NM, z.MID_INI_NM, z.NMPRFX_DSC, z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, z.STREET_NM, "
            + "z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, z.COMNT_DSC, "
            + "z.GENDER_CD, z.BIRTH_DT, z.MRTL_STC, z.EMAIL_ADDR, z.ESTBLSH_CD, z.ESTBLSH_ID, z.RESOST_IND, "
            + "IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER FROM {h-schema}COLTRL_T z "
            + "WHERE z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedCollateralIndividualR1.class)})
@Entity
@Table(name = "REPTR_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedCollateralIndividualR1 extends BaseCollateralIndividual
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
