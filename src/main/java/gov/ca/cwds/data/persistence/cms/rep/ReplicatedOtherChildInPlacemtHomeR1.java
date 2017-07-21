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

import gov.ca.cwds.data.persistence.cms.BaseOtherChildInPlacemtHome;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherChildInPlacemtHomeR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.BIRTH_DT, z.GENDER_CD, z.OTHCHLD_NM, z.LST_UPD_ID, z.LST_UPD_TS, "
            + "z.FKPLC_HM_T, z.YR_INC_AMT , z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER from {h-schema}OTH_KIDT z "
            + "WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR ",
        resultClass = ReplicatedOtherChildInPlacemtHomeR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherChildInPlacemtHomeR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.BIRTH_DT, z.GENDER_CD, z.OTHCHLD_NM, z.LST_UPD_ID, "
            + "z.LST_UPD_TS, z.FKPLC_HM_T, z.YR_INC_AMT, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}OTH_KIDT z "
            + "WHERE (1=1 OR (z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id)) "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR ",
        resultClass = ReplicatedOtherChildInPlacemtHomeR1.class)})
@Entity
@Table(name = "OTH_KIDT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedOtherChildInPlacemtHomeR1 extends BaseOtherChildInPlacemtHome
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
