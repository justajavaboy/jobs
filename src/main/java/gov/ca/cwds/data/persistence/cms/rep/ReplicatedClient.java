package gov.ca.cwds.data.persistence.cms.rep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.dao.ApiClientCaseAware;
import gov.ca.cwds.dao.ApiClientCountyAware;
import gov.ca.cwds.dao.ApiClientRaceAndEthnicityAware;
import gov.ca.cwds.dao.ApiClientSafetyAlertsAware;
import gov.ca.cwds.dao.ApiMultipleClientAddressAware;
import gov.ca.cwds.dao.ApiOtherClientNamesAware;
import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.es.ElasticSearchPersonAddress;
import gov.ca.cwds.data.es.ElasticSearchPersonAka;
import gov.ca.cwds.data.es.ElasticSearchRaceAndEthnicity;
import gov.ca.cwds.data.es.ElasticSearchSafetyAlert;
import gov.ca.cwds.data.es.ElasticSearchSystemCode;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.EsClientAddress;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.neutron.util.transform.ElasticTransformer;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * {@link PersistentObject} representing a Client a {@link CmsReplicatedEntity} in the replicated
 * schema.
 * 
 * <p>
 * Entity class {@link EsClientAddress} for Materialized Query Table ES_CLIENT_ADDRESS now holds the
 * named queries below. These are left here for tracking purposes and will be removed in the near
 * future.
 * </p>
 * 
 * @author CWDS API Team
 */
// @formatter:off
@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient.findAllUpdatedAfter",
    query = "select z.IDENTIFIER, z.ADPTN_STCD, TRIM(z.ALN_REG_NO) ALN_REG_NO, z.BIRTH_DT, "
        + "TRIM(z.BR_FAC_NM) BR_FAC_NM, z.B_STATE_C, z.B_CNTRY_C, z.CHLD_CLT_B, "
        + "TRIM(z.COM_FST_NM) COM_FST_NM, TRIM(z.COM_LST_NM) COM_LST_NM, "
        + "TRIM(z.COM_MID_NM) COM_MID_NM, z.CONF_EFIND, z.CONF_ACTDT, z.CREATN_DT, "
        + "z.DEATH_DT, TRIM(z.DTH_RN_TXT) DTH_RN_TXT, TRIM(z.DRV_LIC_NO) DRV_LIC_NO, "
        + "z.D_STATE_C, z.GENDER_CD, z.I_CNTRY_C, z.IMGT_STC, z.INCAPC_CD, "
        + "z.LITRATE_CD, z.MAR_HIST_B, z.MRTL_STC, z.MILT_STACD, TRIM(z.NMPRFX_DSC) NMPRFX_DSC, "
        + "z.NAME_TPC, z.OUTWRT_IND, z.P_ETHNCTYC, z.P_LANG_TPC, z.RLGN_TPC, "
        + "z.S_LANG_TC, z.SENSTV_IND, z.SNTV_HLIND, TRIM(z.SS_NO) SS_NO, z.SSN_CHG_CD, "
        + "TRIM(z.SUFX_TLDSC) SUFX_TLDSC, z.UNEMPLY_CD, z.LST_UPD_ID, z.LST_UPD_TS, "
        + "TRIM(z.COMMNT_DSC) COMMNT_DSC, z.EST_DOB_CD, z.BP_VER_IND, z.HISP_CD, "
        + "z.CURRCA_IND, z.CURREG_IND, TRIM(z.COTH_DESC), z.PREVCA_IND, z.PREREG_IND, "
        + "TRIM(z.POTH_DESC) POTH_DESC, z.HCARE_IND, z.LIMIT_IND, "
        + "TRIM(z.BIRTH_CITY) BIRTH_CITY, TRIM(z.HEALTH_TXT) HEALTH_TXT, "
        + "z.MTERM_DT, z.FTERM_DT, z.ZIPPY_IND, TRIM(z.DEATH_PLC) DEATH_PLC, "
        + "z.TR_MBVRT_B, z.TRBA_CLT_B, z.SOC158_IND, z.DTH_DT_IND, "
        + "TRIM(z.EMAIL_ADDR) EMAIL_ADDR, z.ADJDEL_IND, z.ETH_UD_CD, "
        + "z.HISP_UD_CD, z.SOCPLC_CD, z.CL_INDX_NO, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
        + "from {h-schema}CLIENT_T z \n" + "WHERE z.IBMSNAP_LOGMARKER >= :after \n"
        + "FOR READ ONLY WITH UR",
    resultClass = ReplicatedClient.class)
@NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient.findByTemp",
    query = "SELECT \n" 
        + "    c.IDENTIFIER \n" 
        + "  , TRIM(c.COM_FST_NM) AS COM_FST_NM \n"
        + "  , TRIM(c.COM_LST_NM) AS COM_LST_NM \n" 
        + "  , c.SENSTV_IND \n" 
        + "  , c.LST_UPD_TS \n"
        + "  , c.IBMSNAP_LOGMARKER \n" 
        + "  , c.IBMSNAP_OPERATION \n"
        + " FROM {h-schema}GT_ID GT \n"
        + " JOIN {h-schema}CLIENT_T C ON C.IDENTIFIER = GT.IDENTIFIER \n"
        + " FOR READ ONLY WITH UR ",
    resultClass = ReplicatedClient.class)
// @formatter:on
@Entity
@Table(name = "CLIENT_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedClient extends BaseClient implements ApiPersonAware,
    ApiMultipleLanguagesAware, ApiMultipleClientAddressAware, ApiMultiplePhonesAware,
    CmsReplicatedEntity, ApiClientCountyAware, ApiClientRaceAndEthnicityAware,
    ApiClientSafetyAlertsAware, ApiOtherClientNamesAware, ApiClientCaseAware {

  private static final long serialVersionUID = 1L;

  private static final String HISPANIC_CODE_OTHER_ID = "02";

  /**
   * A client can have multiple active addresses, typically one active address per address type.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "fkClient")
  private Set<ReplicatedClientAddress> clientAddresses = new LinkedHashSet<>();

  @Transient
  private List<Short> clientRaces = new ArrayList<>();

  @Transient
  private Map<String, ElasticSearchPersonAka> akas = new HashMap<>();

  @Transient
  private Map<String, ElasticSearchSafetyAlert> safetyAlerts = new HashMap<>();

  @Transient
  private Set<Short> clientCounties = new HashSet<>();

  @Transient
  private String openCaseId;

  private EmbeddableCmsReplicatedEntity replicatedEntity = new EmbeddableCmsReplicatedEntity();

  /**
   * Default, no-op constructor
   */
  public ReplicatedClient() {
    // Default, no-op.
  }

  /**
   * Get client address linkages.
   *
   * @return client addresses
   */
  public Set<ReplicatedClientAddress> getClientAddresses() {
    return clientAddresses;
  }

  /**
   * Set the client address linkages.
   *
   * @param clientAddresses Set of client address linkages
   */
  public void setClientAddresses(Set<ReplicatedClientAddress> clientAddresses) {
    if (clientAddresses != null) {
      this.clientAddresses = clientAddresses;
    } else {
      this.clientAddresses = new LinkedHashSet<>();
    }
  }

  /**
   * Add a client address linkage.
   *
   * @param clientAddress client address
   */
  public void addClientAddress(ReplicatedClientAddress clientAddress) {
    if (clientAddress != null) {
      this.clientAddresses.add(clientAddress);
    }
  }

  public void addClientCounty(Short clinetCountyId) {
    if (clinetCountyId != null) {
      this.clientCounties.add(clinetCountyId);
    }
  }

  public List<Short> getClientRaces() {
    return clientRaces;
  }

  public void setClientRaces(List<Short> clientRaces) {
    this.clientRaces = clientRaces;
  }

  public void addClientRace(Short clientRace) {
    if (clientRace != null && !this.clientRaces.contains(clientRace)) {
      this.clientRaces.add(clientRace);
    }
  }

  public Map<String, ElasticSearchPersonAka> getAkas() {
    return akas;
  }

  public void setAkas(Map<String, ElasticSearchPersonAka> akas) {
    this.akas = akas;
  }

  public void addAka(ElasticSearchPersonAka aka) {
    if (aka != null) {
      akas.put(aka.getId(), aka);
    }
  }

  public Map<String, ElasticSearchSafetyAlert> getSafetyAlerts() {
    return safetyAlerts;
  }

  public void setSafetyAlerts(Map<String, ElasticSearchSafetyAlert> safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
  }

  public void addSafetyAlert(ElasticSearchSafetyAlert safetyAlert) {
    if (safetyAlert != null) {
      safetyAlerts.put(safetyAlert.getId(), safetyAlert);
    }
  }

  /**
   * Get id of open case for this client
   * 
   * @return Open case id if any
   */
  @Override
  public String getOpenCaseId() {
    return openCaseId;
  }

  /**
   * Set id of open case for this client
   * 
   * @param openCaseId Open case id if any
   */
  public void setOpenCaseId(String openCaseId) {
    this.openCaseId = openCaseId;
  }

  // =================================
  // ApiMultipleClientAddressAware:
  // =================================

  @JsonIgnore
  @Override
  public List<ElasticSearchPersonAddress> getElasticSearchPersonAddresses() {
    Map<String, ElasticSearchPersonAddress> esClientAddresses = new HashMap<>();

    for (ReplicatedClientAddress repClientAddress : this.clientAddresses) {
      final String effectiveEndDate = DomainChef.cookDate(repClientAddress.getEffEndDt());
      final boolean addressActive = StringUtils.isBlank(effectiveEndDate);

      /*
       * We index only active addresses.
       */
      if (addressActive) {
        String effectiveStartDate = DomainChef.cookDate(repClientAddress.getEffStartDt());
        final ElasticSearchSystemCode addressType = new ElasticSearchSystemCode();
        SystemCode addressTypeSystemCode =
            SystemCodeCache.global().getSystemCode(repClientAddress.getAddressType());
        if (addressTypeSystemCode != null) {
          addressType.setDescription(addressTypeSystemCode.getShortDescription());
          addressType.setId(addressTypeSystemCode.getSystemId().toString());
        }

        for (ReplicatedAddress repAddress : repClientAddress.getAddresses()) {
          final ElasticSearchPersonAddress esAddress = new ElasticSearchPersonAddress();
          esClientAddresses.put(esAddress.getAddressId(), esAddress);

          esAddress.setLegacyDescriptor(repAddress.getLegacyDescriptor());
          esAddress.setId(repAddress.getAddressId());
          esAddress.setCity(repAddress.getCity());
          esAddress.setCounty(repAddress.getCounty());
          esAddress.setState(repAddress.getState());
          esAddress.setZip(repAddress.getZip());
          esAddress.setZip4(repAddress.getApiAdrZip4());
          esAddress.setStreetName(repAddress.getStreetName());
          esAddress.setStreetNumber(repAddress.getStreetNumber());
          esAddress.setUnitNumber(repAddress.getApiAdrUnitNumber());
          esAddress.setEffectiveStartDate(effectiveStartDate);
          esAddress.setEffectiveEndDate(effectiveEndDate);
          esAddress.setType(addressType);
          esAddress.setActive("true");

          final ElasticSearchSystemCode stateCode = new ElasticSearchSystemCode();
          esAddress.setStateSystemCode(stateCode);
          SystemCode stateSysCode = SystemCodeCache.global().getSystemCode(repAddress.getStateCd());
          if (stateSysCode != null) {
            stateCode.setDescription(stateSysCode.getShortDescription());
            stateCode.setId(stateSysCode.getSystemId().toString());
            esAddress.setStateName(stateSysCode.getShortDescription());
            esAddress.setStateCode(stateSysCode.getLogicalId());
          }

          final ElasticSearchSystemCode countyCode = new ElasticSearchSystemCode();
          esAddress.setCountySystemCode(countyCode);
          SystemCode countySysCode =
              SystemCodeCache.global().getSystemCode(repAddress.getGovernmentEntityCd());
          if (countySysCode != null) {
            countyCode.setDescription(countySysCode.getShortDescription());
            countyCode.setId(countySysCode.getSystemId().toString());
          }

          if (repAddress.getApiAdrUnitType() != null
              && repAddress.getApiAdrUnitType().intValue() != 0) {
            esAddress.setUnitType(SystemCodeCache.global()
                .getSystemCodeShortDescription(repAddress.getApiAdrUnitType()));
          }
        }
      }
    }

    return new ArrayList<>(esClientAddresses.values());
  }

  // ============================
  // ApiMultiplePhonesAware:
  // ============================

  @JsonIgnore
  @Override
  public ApiPhoneAware[] getPhones() {
    return clientAddresses.stream().flatMap(ca -> ca.getAddresses().stream())
        .flatMap(adr -> Arrays.stream(adr.getPhones())).collect(Collectors.toList())
        .toArray(new ApiPhoneAware[0]);
  }

  // =======================
  // CmsReplicatedEntity:
  // =======================

  @Override
  public EmbeddableCmsReplicatedEntity getReplicatedEntity() {
    return replicatedEntity;
  }

  // =======================
  // ApiLegacyAware:
  // =======================

  @Override
  public String getLegacyId() {
    return getId();
  }

  @Override
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return ElasticTransformer.createLegacyDescriptor(getId(), getLastUpdatedTime(),
        LegacyTable.CLIENT);
  }

  // ==================================
  // ApiOtherClientNamesAware:
  // ==================================

  @Override
  public List<ElasticSearchPersonAka> getOtherClientNames() {
    return new ArrayList<>(this.akas.values());
  }

  // ==================================
  // ApiClientSafetyAlertsAware:
  // ==================================

  @Override
  public List<ElasticSearchSafetyAlert> getClientSafetyAlerts() {
    return new ArrayList<>(this.safetyAlerts.values());
  }

  // ==================================
  // ApiClientRaceAndEthnicityAware:
  // ==================================

  @Override
  public ElasticSearchRaceAndEthnicity getRaceAndEthnicity() {
    final ElasticSearchRaceAndEthnicity racesEthnicity = new ElasticSearchRaceAndEthnicity();
    racesEthnicity.setHispanicOriginCode(getHispanicOriginCode());
    racesEthnicity.setHispanicUnableToDetermineCode(getHispUnableToDetReasonCode());
    racesEthnicity.setUnableToDetermineCode(getEthUnableToDetReasonCode());

    final List<ElasticSearchSystemCode> raceCodes = new ArrayList<>();
    racesEthnicity.setRaceCodes(raceCodes);

    final List<ElasticSearchSystemCode> hispanicCodes = new ArrayList<>();
    racesEthnicity.setHispanicCodes(hispanicCodes);

    // Add primary race
    addRaceAndEthnicity(this.primaryEthnicityType, raceCodes, hispanicCodes);

    // Add other races
    for (Short raceCode : this.clientRaces) {
      addRaceAndEthnicity(raceCode, raceCodes, hispanicCodes);
    }

    return racesEthnicity;
  }

  private static void addRaceAndEthnicity(Short codeId,
      final List<ElasticSearchSystemCode> raceCodes,
      final List<ElasticSearchSystemCode> hispanicCodes) {
    if (codeId != null && codeId != 0) {
      String description = null;
      boolean isHispanicCode = false;

      final SystemCode systemCode = SystemCodeCache.global().getSystemCode(codeId);
      if (systemCode != null) {
        description = systemCode.getShortDescription();
        isHispanicCode = HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd());
      }

      final ElasticSearchSystemCode esCode = new ElasticSearchSystemCode();
      esCode.setId(codeId.toString());
      esCode.setDescription(description);

      if (isHispanicCode) {
        hispanicCodes.add(esCode);
      } else {
        raceCodes.add(esCode);
      }
    }
  }

  // ==================================
  // ApiClientCountyAware
  // ==================================

  @Override
  public List<ElasticSearchSystemCode> getClientCounties() {
    if (this.clientCounties == null || this.clientCounties.isEmpty()) {
      return new ArrayList<ElasticSearchSystemCode>();
    }

    final List<ElasticSearchSystemCode> clientCounties =
        new ArrayList<>(this.clientCounties.size());
    for (Short county : this.clientCounties) {
      final ElasticSearchSystemCode countySysCode = new ElasticSearchSystemCode();
      countySysCode.setId(county.toString());
      countySysCode.setDescription(SystemCodeCache.global().getSystemCodeShortDescription(county));
      clientCounties.add(countySysCode);
    }

    return clientCounties;
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
