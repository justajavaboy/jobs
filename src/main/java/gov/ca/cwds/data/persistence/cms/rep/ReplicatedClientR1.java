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

import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiPersonAware;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClientR1.findAllUpdatedAfter",
        query = "select c.IDENTIFIER, c.ADPTN_STCD, c.ALN_REG_NO, c.BIRTH_DT, trim(c.BR_FAC_NM) BR_FAC_NM, c.B_STATE_C, c.B_CNTRY_C, c.CHLD_CLT_B, trim(c.COM_FST_NM) COM_FST_NM, trim(c.COM_LST_NM) COM_LST_NM, trim(c.COM_MID_NM) COM_MID_NM, c.CONF_EFIND, c.CONF_ACTDT, c.CREATN_DT, c.DEATH_DT, trim(c.DTH_RN_TXT) DTH_RN_TXT, trim(c.DRV_LIC_NO) DRV_LIC_NO, c.D_STATE_C, c.GENDER_CD, c.I_CNTRY_C, c.IMGT_STC, c.INCAPC_CD, c.LITRATE_CD, c.MAR_HIST_B, c.MRTL_STC, c.MILT_STACD, c.NMPRFX_DSC, c.NAME_TPC, c.OUTWRT_IND, c.P_ETHNCTYC, c.P_LANG_TPC, c.RLGN_TPC, c.S_LANG_TC, c.SENSTV_IND, c.SNTV_HLIND, c.SS_NO, c.SSN_CHG_CD, trim(c.SUFX_TLDSC) SUFX_TLDSC, c.UNEMPLY_CD, c.LST_UPD_ID, c.LST_UPD_TS, trim(c.COMMNT_DSC) COMMNT_DSC, c.EST_DOB_CD, c.BP_VER_IND, c.HISP_CD, c.CURRCA_IND, c.CURREG_IND, c.COTH_DESC, c.PREVCA_IND, c.PREREG_IND, trim(c.POTH_DESC) POTH_DESC, c.HCARE_IND, c.LIMIT_IND, trim(c.BIRTH_CITY) BIRTH_CITY, trim(c.HEALTH_TXT) HEALTH_TXT, c.MTERM_DT, c.FTERM_DT, c.ZIPPY_IND, trim(c.DEATH_PLC) DEATH_PLC, c.TR_MBVRT_B, c.TRBA_CLT_B, c.SOC158_IND, c.DTH_DT_IND, trim(c.EMAIL_ADDR) EMAIL_ADDR, c.ADJDEL_IND, c.ETH_UD_CD, c.HISP_UD_CD, c.SOCPLC_CD, c.CL_INDX_NO, c.IBMSNAP_OPERATION, c.IBMSNAP_LOGMARKER "
            + "from {h-schema}CLIENT_T c WHERE c.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR",
        resultClass = ReplicatedClientR1.class),
    // @NamedNativeQuery(
    // name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClientR1.findPartitionedBuckets",
    // query = "select c.IDENTIFIER, c.ADPTN_STCD, c.ALN_REG_NO, c.BIRTH_DT, trim(c.BR_FAC_NM) as
    // BR_FAC_NM, "
    // + "c.B_STATE_C, c.B_CNTRY_C, c.CHLD_CLT_B, trim(c.COM_FST_NM) COM_FST_NM,
    // trim(c.COM_LST_NM) COM_LST_NM, trim(c.COM_MID_NM) COM_MID_NM, c.CONF_EFIND, "
    // + "c.CONF_ACTDT, c.CREATN_DT, c.DEATH_DT, trim(c.DTH_RN_TXT) DTH_RN_TXT,
    // trim(c.DRV_LIC_NO) DRV_LIC_NO, "
    // + "c.D_STATE_C, c.GENDER_CD, c.I_CNTRY_C, c.IMGT_STC, c.INCAPC_CD, c.LITRATE_CD,
    // c.MAR_HIST_B, "
    // + "c.MRTL_STC, c.MILT_STACD, c.NMPRFX_DSC, c.NAME_TPC, c.OUTWRT_IND, c.P_ETHNCTYC,
    // c.P_LANG_TPC, c.RLGN_TPC, c.S_LANG_TC, "
    // + "c.SENSTV_IND, c.SNTV_HLIND, c.SS_NO, c.SSN_CHG_CD, trim(c.SUFX_TLDSC) SUFX_TLDSC,
    // c.UNEMPLY_CD, c.LST_UPD_ID, "
    // + "c.LST_UPD_TS, trim(c.COMMNT_DSC) COMMNT_DSC, c.EST_DOB_CD, c.BP_VER_IND, c.HISP_CD,
    // c.CURRCA_IND, c.CURREG_IND, c.COTH_DESC, c.PREVCA_IND, "
    // + "c.PREREG_IND, trim(c.POTH_DESC) POTH_DESC, c.HCARE_IND, c.LIMIT_IND, trim(c.BIRTH_CITY)
    // BIRTH_CITY, "
    // + "trim(c.HEALTH_TXT) HEALTH_TXT, c.MTERM_DT, c.FTERM_DT, c.ZIPPY_IND, trim(c.DEATH_PLC)
    // DEATH_PLC, "
    // + "c.TR_MBVRT_B, c.TRBA_CLT_B, c.SOC158_IND, c.DTH_DT_IND, trim(c.EMAIL_ADDR) EMAIL_ADDR,
    // "
    // + "c.ADJDEL_IND, c.ETH_UD_CD, c.HISP_UD_CD, c.SOCPLC_CD, c.CL_INDX_NO , c.IBMSNAP_OPERATION,
    // c.IBMSNAP_LOGMARKER "
    // + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 bucket, y.* "
    // + "from ( select row_number() over (order by 1) rn, x.* "
    // + "from {h-schema}CLIENT_T x "
    // + "where x.IDENTIFIER >= :min_id and x.IDENTIFIER < :max_id ) y ) c "
    // + "where c.bucket = :bucket_num FOR READ ONLY",
    // resultClass = ReplicatedClientR1.class)})
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClientR1.findPartitionedBuckets",
        query = "select c.IDENTIFIER, c.ADPTN_STCD, c.ALN_REG_NO, c.BIRTH_DT, trim(c.BR_FAC_NM) BR_FAC_NM, "
            + "c.B_STATE_C, c.B_CNTRY_C, c.CHLD_CLT_B, trim(c.COM_FST_NM) COM_FST_NM, trim(c.COM_LST_NM) COM_LST_NM, "
            + "trim(c.COM_MID_NM) COM_MID_NM, c.CONF_EFIND, c.CONF_ACTDT, c.CREATN_DT, c.DEATH_DT, "
            + "trim(c.DTH_RN_TXT) DTH_RN_TXT, trim(c.DRV_LIC_NO) DRV_LIC_NO, c.D_STATE_C, c.GENDER_CD, "
            + "c.I_CNTRY_C, c.IMGT_STC, c.INCAPC_CD, c.LITRATE_CD, c.MAR_HIST_B, c.MRTL_STC, c.MILT_STACD, "
            + "c.NMPRFX_DSC, c.NAME_TPC, c.OUTWRT_IND, c.P_ETHNCTYC, c.P_LANG_TPC, c.RLGN_TPC, c.S_LANG_TC, "
            + "c.SENSTV_IND, c.SNTV_HLIND, c.SS_NO, c.SSN_CHG_CD, trim(c.SUFX_TLDSC) SUFX_TLDSC, c.UNEMPLY_CD, "
            + "c.LST_UPD_ID, c.LST_UPD_TS, trim(c.COMMNT_DSC) COMMNT_DSC, c.EST_DOB_CD, c.BP_VER_IND, c.HISP_CD, "
            + "c.CURRCA_IND, c.CURREG_IND, c.COTH_DESC, c.PREVCA_IND, c.PREREG_IND, trim(c.POTH_DESC) POTH_DESC, "
            + "c.HCARE_IND, c.LIMIT_IND, trim(c.BIRTH_CITY) BIRTH_CITY, trim(c.HEALTH_TXT) HEALTH_TXT, c.MTERM_DT, "
            + "c.FTERM_DT, c.ZIPPY_IND, trim(c.DEATH_PLC) DEATH_PLC, c.TR_MBVRT_B, c.TRBA_CLT_B, c.SOC158_IND, "
            + "c.DTH_DT_IND, trim(c.EMAIL_ADDR) EMAIL_ADDR, c.ADJDEL_IND, c.ETH_UD_CD, c.HISP_UD_CD, c.SOCPLC_CD, "
            + "c.CL_INDX_NO , c.IBMSNAP_OPERATION, c.IBMSNAP_LOGMARKER "
            + "FROM {h-schema}CLIENT_T c "
            + "WHERE c.IDENTIFIER >= :min_id AND c.IDENTIFIER < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedClientR1.class)})
@Entity
@Table(name = "CLIENT_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedClientR1 extends BaseClient
    implements ApiPersonAware, ApiMultipleLanguagesAware, CmsReplicatedEntity {

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
