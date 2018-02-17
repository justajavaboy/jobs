package gov.ca.cwds.data.model.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;

import gov.ca.cwds.neutron.util.shrinkray.NeutronDateUtils;

/**
 * CMS document entity basics without blobs.
 * 
 * @author CWDS API Team
 */
//@formatter:off
@NamedNativeQuery(name = "findByLastJobRunTimeMinusOneMinute",
    query = "SELECT CNT.DOC_HANDLE AS DOC_HANDLE, "
        + "DECODE(CNT.CMPRS_PRG, 'DELETED', 'DELETED', 'DELETE02', 'DELETED', 'ACTIVE') AS DOC_STATUS,"
        + "CNT.LST_UPD_TS AS LST_UPD_TS \n"
        + "FROM {h-schema}TSCNTRLT CNT \n"
        + "WHERE CNT.DOC_HANDLE <> 'DUMMY' AND CMPRS_PRG = 'CWSCMP01' \n"
        + "AND CNT.LST_UPD_TS > TIMESTAMP_FORMAT(:lastJobRunTime, 'YYYY-MM-DD HH24:MI:SS') - 1 MINUTE \n"
        + "WITH UR",
    resultClass = DocumentMetadata.class)
//@formatter:on
@Entity
@Table(name = "TSCNTRLT")
public class DocumentMetadata {

  @Id
  @Column(name = "DOC_HANDLE")
  private String handle;

  @Column(name = "DOC_STATUS")
  private String status;

  @Column(name = "LST_UPD_TS")
  private Date lastUpdatedTimestamp;

  /**
   * Constructor
   */
  public DocumentMetadata() {
    super();
  }

  /**
   * @return the handle
   */
  public String getHandle() {
    return handle;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the lastUpdatedTimestamp
   */
  public Date getLastUpdatedTimestamp() {
    return NeutronDateUtils.freshDate(lastUpdatedTimestamp);
  }

}
