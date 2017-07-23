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

import gov.ca.cwds.data.persistence.cms.BaseEducationProviderContact;

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedEducationProviderContactR1.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.PRICNTIND, z.PH_NUMBR, z.PH_EXTNO, z.FAX_NO, "
            + "trim(z.FIRST_NME) FIRST_NME, trim(z.MIDDLE_NM) MIDDLE_NM, trim(z.LAST_NME) LAST_NME, "
            + "trim(z.NM_PREFIX) NM_PREFIX, trim(z.SUFFX_TITL) SUFFX_TITL, trim(z.TITLDESC) TITLDESC, "
            + "trim(z.EMAILADR) EMAILADR, z.DOE_IND, z.LST_UPD_ID, z.LST_UPD_TS, z.FKED_PVDRT, "
            + "z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}EDPRVCNT z WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY WITH UR ",
        resultClass = ReplicatedEducationProviderContactR1.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedEducationProviderContactR1.findPartitionedBuckets",
        query = "select z.IDENTIFIER, z.PRICNTIND, z.PH_NUMBR, z.PH_EXTNO, z.FAX_NO, "
            + "trim(z.FIRST_NME) FIRST_NME, trim(z.MIDDLE_NM) MIDDLE_NM, trim(z.LAST_NME) LAST_NME, "
            + "trim(z.NM_PREFIX) NM_PREFIX, trim(z.SUFFX_TITL) SUFFX_TITL, trim(z.TITLDESC) TITLDESC, "
            + "trim(z.EMAILADR) EMAILADR, z.DOE_IND, z.LST_UPD_ID, z.LST_UPD_TS, z.FKED_PVDRT, "
            + "z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER from {h-schema}EDPRVCNT z "
            + "WHERE (1=1 OR (z.IDENTIFIER >= :min_id AND z.IDENTIFIER < :max_id)) "
            + "AND (1=1 OR 57 = :bucket_num OR 92 = :total_buckets) FOR READ ONLY WITH UR",
        resultClass = ReplicatedEducationProviderContactR1.class)})
@Entity
@Table(name = "EDPRVCNT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedEducationProviderContactR1 extends BaseEducationProviderContact
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
