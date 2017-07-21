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

import gov.ca.cwds.data.persistence.cms.BaseOtherClientName;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherClientNameR1.findAllUpdatedAfter",
        query = "select z.THIRD_ID, z.FIRST_NM, z.LAST_NM, z.MIDDLE_NM, z.NMPRFX_DSC, z.NAME_TPC, "
            + "z.SUFX_TLDSC, z.LST_UPD_ID, z.LST_UPD_TS, z.FKCLIENT_T, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}OCL_NM_T z WHERE z.IBMSNAP_LOGMARKER >= :after for read only ",
        resultClass = ReplicatedOtherClientNameR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherClientNameR1.findPartitionedBuckets",
        query = "select z.THIRD_ID, z.FIRST_NM, z.LAST_NM, z.MIDDLE_NM, z.NMPRFX_DSC, z.NAME_TPC, "
            + "z.SUFX_TLDSC, z.LST_UPD_ID, z.LST_UPD_TS, z.FKCLIENT_T, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}OCL_NM_T z "
            + "WHERE z.FKCLIENT_T >= :min_id AND z.FKCLIENT_T < :max_id "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedOtherClientNameR1.class)})
@Entity
@Table(name = "OCL_NM_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedOtherClientNameR1 extends BaseOtherClientName
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
