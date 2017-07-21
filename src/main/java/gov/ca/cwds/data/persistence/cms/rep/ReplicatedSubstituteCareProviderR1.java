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

import gov.ca.cwds.data.persistence.cms.BaseSubstituteCareProvider;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProviderR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.ADD_TEL_NO, z.ADD_EXT_NO, z.YR_INC_AMT, z.BIRTH_DT, z.CA_DLIC_NO, "
            + "z.CITY_NM, z.EDUCATION, z.EMAIL_ADDR, z.EMPLYR_NM, z.EMPL_STAT, z.ETH_UD_CD, "
            + "z.FIRST_NM, z.FRG_ADRT_B, z.GENDER_IND, z.HISP_UD_CD, z.HISP_CD, z.IND_TRBC, "
            + "z.LAST_NM, z.LISOWNIND, z.LIS_PER_ID, z.MRTL_STC, z.MID_INI_NM, z.NMPRFX_DSC, "
            + "z.PASSBC_CD, z.PRIM_INC, z.RESOST_IND, z.SEC_INC, z.SS_NO, z.STATE_C, z.STREET_NM, "
            + "z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.ZIP_SFX_NO, z.LST_UPD_ID, z.LST_UPD_TS, "
            + "z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "FROM {h-schema}SB_PVDRT z WHERE z.IBMSNAP_LOGMARKER >= :after for read only ",
        resultClass = ReplicatedReporter.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProviderR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.ADD_TEL_NO, z.ADD_EXT_NO, z.YR_INC_AMT, z.BIRTH_DT, z.CA_DLIC_NO, "
            + "z.CITY_NM, z.EDUCATION, z.EMAIL_ADDR, z.EMPLYR_NM, z.EMPL_STAT, z.ETH_UD_CD, "
            + "z.FIRST_NM, z.FRG_ADRT_B, z.GENDER_IND, z.HISP_UD_CD, z.HISP_CD, z.IND_TRBC, "
            + "z.LAST_NM, z.LISOWNIND, z.LIS_PER_ID, z.MRTL_STC, z.MID_INI_NM, z.NMPRFX_DSC, "
            + "z.PASSBC_CD, z.PRIM_INC, z.RESOST_IND, z.SEC_INC, z.SS_NO, z.STATE_C, z.STREET_NM, "
            + "z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.ZIP_SFX_NO, z.LST_UPD_ID, z.LST_UPD_TS, "
            + "z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "FROM {h-schema}SB_PVDRT z WHERE z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedSubstituteCareProviderR1.class)})
@Entity
@Table(name = "SB_PVDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedSubstituteCareProviderR1 extends BaseSubstituteCareProvider
    implements CmsReplicatedEntity {

  private static final long serialVersionUID = 6160989831851057517L;

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
