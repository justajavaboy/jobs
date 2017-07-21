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

import gov.ca.cwds.data.persistence.cms.BaseOtherAdultInPlacemtHome;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHomeR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.BIRTH_DT, z.END_DT, z.GENDER_CD, z.OTH_ADLTNM, z.START_DT, z.LST_UPD_ID, z.LST_UPD_TS, z.FKPLC_HM_T, z.COMNT_DSC, z.OTH_ADL_CD, z.IDENTFD_DT, z.RESOST_IND, z.PASSBC_CD , z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER from {h-schema}OTH_ADLT z WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR ",
        resultClass = ReplicatedOtherAdultInPlacemtHomeR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHomeR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.BIRTH_DT, z.END_DT, z.GENDER_CD, z.OTH_ADLTNM, z.START_DT, "
            + "z.LST_UPD_ID, z.LST_UPD_TS, z.FKPLC_HM_T, z.COMNT_DSC, z.OTH_ADL_CD, "
            + "z.IDENTFD_DT, z.RESOST_IND, z.PASSBC_CD, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}OTH_ADLT z "
            + "WHERE z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedOtherAdultInPlacemtHomeR1.class)})
@Entity
@Table(name = "OTH_ADLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedOtherAdultInPlacemtHomeR1 extends BaseOtherAdultInPlacemtHome
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
