package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.std.ApiMarker;

public class CaseSQLResource implements ApiMarker {

  private static final long serialVersionUID = 1L;

  //@formatter:off
  public static final String PREP_AFFECTED_CLIENTS_FULL =
       "INSERT INTO GT_ID (IDENTIFIER) \n"
          + "WITH DRIVER AS ( \n"
          + " SELECT c.IDENTIFIER \n"
          + " FROM CLIENT_T C \n"
          + " WHERE c.IDENTIFIER BETWEEN ? AND ? \n"
          + "   AND c.IBMSNAP_OPERATION IN ('I','U') \n"
          + ") \n"
       + "SELECT DISTINCT CAS1.FKCHLD_CLT AS CLIENT_ID \n"
          + "FROM DRIVER d1 \n"
          + "JOIN CASE_T CAS1   ON CAS1.FKCHLD_CLT = d1.IDENTIFIER \n"
          + "UNION \n"
       + "SELECT DISTINCT REL2.FKCLIENT_0  AS CLIENT_ID \n"
          + "FROM DRIVER d2 \n"
          + "JOIN CLN_RELT REL2 ON REL2.FKCLIENT_T = d2.IDENTIFIER \n"
          + "JOIN CASE_T   CAS2 ON CAS2.FKCHLD_CLT = REL2.FKCLIENT_0 \n"
          + "UNION \n"
       + "SELECT DISTINCT REL3.FKCLIENT_T  AS CLIENT_ID \n"
          + "FROM DRIVER d3 \n"
          + "JOIN CLN_RELT REL3 ON REL3.FKCLIENT_0 = d3.IDENTIFIER \n"
          + "JOIN CASE_T   CAS3 ON CAS3.FKCHLD_CLT = REL3.FKCLIENT_T  \n"
          + "UNION \n"
       + "SELECT DISTINCT REL4.FKCLIENT_T  AS CLIENT_ID \n"
          + "FROM DRIVER d4 \n"
          + "JOIN CLN_RELT REL4 ON REL4.FKCLIENT_T = d4.IDENTIFIER \n"
          + "JOIN CASE_T   CAS4 ON CAS4.FKCHLD_CLT = REL4.FKCLIENT_0 \n"
          + "UNION \n"
       + "SELECT DISTINCT REL5.FKCLIENT_0  AS CLIENT_ID \n"
          + "FROM DRIVER d5 \n"
          + "JOIN CLN_RELT REL5 ON REL5.FKCLIENT_0 = d5.IDENTIFIER \n"
          + "JOIN CASE_T   CAS5 ON CAS5.FKCHLD_CLT = REL5.FKCLIENT_T ";  
  //@formatter:on

  //@formatter:off
  public static final String INSERT_CLIENT_CASE =
        "INSERT INTO GT_REFR_CLT (FKCLIENT_T, FKREFERL_T, SENSTV_IND) \n"
        + "WITH DRIVER AS ( \n"
          + " SELECT CAS1.IDENTIFIER    AS CASE_ID,  \n"
          + "        CAS1.FKCHLD_CLT    AS THIS_CLIENT_ID,  \n"
          + "        CAS1.FKCHLD_CLT    AS FOCUS_CHILD_ID \n"
          + " FROM CASE_T CAS1 \n"
          + " WHERE CAS1.FKCHLD_CLT IN (SELECT gt1.IDENTIFIER FROM GT_ID gt1) \n"
          + " UNION ALL \n"
          + " SELECT CAS2.IDENTIFIER    AS CASE_ID, \n"
          + "        REL2.FKCLIENT_T    AS THIS_CLIENT_ID, \n"
          + "        CAS2.FKCHLD_CLT    AS FOCUS_CHILD_ID \n"
          + " FROM CLN_RELT REL2, CASE_T CAS2 \n"
          + " WHERE CAS2.FKCHLD_CLT = REL2.FKCLIENT_0  \n"
          + "   AND REL2.FKCLIENT_T IN (SELECT gt2.IDENTIFIER FROM GT_ID gt2) \n"
          + " UNION \n"
          + " SELECT CAS3.IDENTIFIER    AS CASE_ID, \n"
          + "        REL3.FKCLIENT_0    AS THIS_CLIENT_ID, \n"
          + "        CAS3.FKCHLD_CLT    AS FOCUS_CHILD_ID \n"
          + " FROM CLN_RELT REL3, CASE_T CAS3 \n"
          + " WHERE CAS3.FKCHLD_CLT = REL3.FKCLIENT_T  \n"
          + "   AND REL3.FKCLIENT_0 IN (SELECT gt3.IDENTIFIER FROM GT_ID gt3) \n"
          + ") \n"
        + "SELECT DISTINCT d1.THIS_CLIENT_ID AS CLIENT_ID, d1.CASE_ID, 'X' AS SENSTV_IND \n"
        + "FROM DRIVER D1 \n"
        + "UNION \n"
        + "SELECT DISTINCT d2.FOCUS_CHILD_ID AS CLIENT_ID, d2.CASE_ID, 'X' AS SENSTV_IND \n"
        + "FROM DRIVER D2 ";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_CLIENT_CASE =
      "SELECT DISTINCT rc.FKCLIENT_T AS CLIENT_ID, rc.FKREFERL_T AS CASE_ID FROM GT_REFR_CLT rc WITH UR ";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_CASE =
          "WITH driver AS ( \n"
        + "   SELECT DISTINCT rc.FKREFERL_T AS CASE_ID FROM GT_REFR_CLT rc \n"
        + ") \n"
        + "SELECT DISTINCT    \n"
        +   " CAS.IDENTIFIER          AS CASE_ID, \n"
        +   " CAS.FKCHLD_CLT          AS FOCUS_CHILD_ID, \n"
        +   " TRIM(CAS.CASE_NM)       AS CASE_NAME, \n"
        +   " CAS.START_DT            AS START_DATE, \n"
        +   " CAS.END_DT              AS END_DATE, \n"
        +   " CAS.SRV_CMPC            AS SERVICE_COMP, \n"
        +   " CAS.CLS_RSNC            AS CLOSE_REASON_CODE, \n"
        +   " CAS.FKSTFPERST          AS WORKER_ID, \n"
        +   " CAS.LMT_ACSSCD          AS LIMITED_ACCESS_CODE, \n"
        +   " CAS.LMT_ACS_DT          AS LIMITED_ACCESS_DATE, \n"
        +   " TRIM(CAS.LMT_ACSDSC)    AS LIMITED_ACCESS_DESCRIPTION, \n"
        +   " CAS.L_GVR_ENTC          AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
        +   " CAS.LST_UPD_TS          AS CASE_LAST_UPDATED, \n"
        +   " CAS.GVR_ENTC            AS COUNTY, \n"
        +   " CAS.APV_STC  \n"
        + "FROM driver d  \n"
        + "JOIN CASE_T CAS ON CAS.IDENTIFIER = d.CASE_ID \n"
        + "WHERE CAS.IBMSNAP_OPERATION IN ('I','U')  \n"
        + "WITH UR ";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_CLIENT = 
      "\nSELECT \n"
          + "    c.IDENTIFIER        AS CLIENT_ID \n"
          + "  , TRIM(c.COM_FST_NM)  AS CLIENT_FIRST_NM \n"
          + "  , TRIM(c.COM_LST_NM)  AS CLIENT_LAST_NM \n"
          + "  , c.SENSTV_IND        AS CLIENT_SENSITIVITY_IND \n"
          + "  , c.LST_UPD_TS        AS CLIENT_LAST_UPDATED \n"
          + "  , c.IBMSNAP_LOGMARKER AS CLIENT_LOGMARKER \n"
          + "  , c.IBMSNAP_OPERATION AS CLIENT_OPERATION \n"
          + " FROM (\n"
          + "   SELECT DISTINCT rc.FKCLIENT_T AS CLIENT_ID FROM GT_REFR_CLT rc \n"
          + ") GT \n"
          + " JOIN CLIENT_T C ON C.IDENTIFIER = GT.CLIENT_ID \n"
      + " FOR READ ONLY WITH UR  ";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_FOCUS_CHILD_PARENTS = 
       "SELECT DISTINCT cas.FKCHLD_CLT AS FOCUS_CHILD_ID,  \n"
          + "ccc.IDENTIFIER AS L_CLIENT_ID, ccc.COM_FST_NM AS L_FIRST, ccc.COM_MID_NM AS L_MIDDLE, ccc.COM_LST_NM AS L_LAST, ccc.BIRTH_DT AS L_BIRTH, ccc.GENDER_CD AS L_GENDER, \n"
          + "sc.SHORT_DSC AS REL_TYPE, sc.SYS_ID AS REL_CODE,  \n"
          + "cc0.IDENTIFIER AS R_CLIENT_ID, cc0.COM_FST_NM AS R_FIRST, cc0.COM_MID_NM AS R_MIDDLE, cc0.COM_LST_NM AS R_LAST, cc0.BIRTH_DT AS R_BIRTH, cc0.GENDER_CD AS R_GENDER \n"
          + "FROM (SELECT DISTINCT gt.FKREFERL_T AS CASE_ID FROM GT_REFR_CLT gt) x \n"
          + "JOIN CASE_T cas   ON cas.IDENTIFIER  = x.CASE_ID \n"
          + "JOIN CLN_RELT rel ON rel.FKCLIENT_T  = cas.FKCHLD_CLT \n"
          + "JOIN CLIENT_T cc0 ON cc0.identifier  = rel.FKCLIENT_0 \n"
          + "JOIN CLIENT_T ccc ON ccc.identifier  = rel.FKCLIENT_T \n"
          + "JOIN SYS_CD_C SC  ON SC.SYS_ID       = rel.CLNTRELC \n"
          + "JOIN SYS_CD_C SC2 ON SC2.SYS_ID      = CAST(SC.LONG_DSC AS SMALLINT) \n"
          + "WHERE SC.SYS_ID  IN (188,189,190,191,192,193,194,195,196,197,198,199,283,284,285,286,287,288,289,290,291,292,293,242,243,301,6360) \n"
     + "UNION \n"
     + "SELECT DISTINCT cas.FKCHLD_CLT AS FOCUS_CHILD_ID,  \n"
          + "cc0.IDENTIFIER AS L_CLIENT_ID, cc0.COM_FST_NM AS L_FIRST, cc0.COM_MID_NM AS L_MIDDLE, cc0.COM_LST_NM AS L_LAST, cc0.BIRTH_DT AS L_BIRTH, cc0.GENDER_CD AS L_GENDER, \n"
          + "sc2.SHORT_DSC AS REL_TYPE, sc2.SYS_ID AS REL_CODE,  \n"
          + "ccc.IDENTIFIER AS R_CLIENT_ID, ccc.COM_FST_NM AS R_FIRST, ccc.COM_MID_NM AS R_MIDDLE, ccc.COM_LST_NM AS R_LAST, ccc.BIRTH_DT AS R_BIRTH, ccc.GENDER_CD AS R_GENDER \n"
          + "FROM (SELECT DISTINCT gt.FKREFERL_T AS CASE_ID FROM GT_REFR_CLT gt) x \n"
          + "JOIN CASE_T cas   ON cas.IDENTIFIER  = x.CASE_ID \n"
          + "JOIN CLN_RELT rel ON rel.FKCLIENT_0  = cas.FKCHLD_CLT \n"
          + "JOIN CLIENT_T cc0 ON cc0.identifier  = rel.FKCLIENT_0 \n"
          + "JOIN CLIENT_T ccc ON ccc.identifier  = rel.FKCLIENT_T \n"
          + "JOIN SYS_CD_C SC  ON SC.SYS_ID       = rel.CLNTRELC \n"
          + "JOIN SYS_CD_C SC2 ON SC2.SYS_ID      = CAST(SC.LONG_DSC AS SMALLINT) \n"
          + "WHERE SC2.SYS_ID  IN (188,189,190,191,192,193,194,195,196,197,198,199,283,284,285,286,287,288,289,290,291,292,293,242,243,301,6360) \n"
          + "WITH UR";
  //@formatter:on

  /**
   * Filter <strong>deleted</strong> Clients and Cases.
   */
  //@formatter:off
  public static final String PREP_AFFECTED_CLIENTS_LAST_CHG =  
      "INSERT INTO GT_ID (IDENTIFIER)"
          + "WITH step1 AS ( \n"
          + " SELECT CAS1.FKCHLD_CLT \n"
          + " FROM  CASE_T CAS1  \n"
          + " WHERE CAS1.IBMSNAP_LOGMARKER > ? \n"
          + "UNION   \n"
          + " SELECT CAS2.FKCHLD_CLT  \n"
          + " FROM CASE_T CAS2 \n"
          + " JOIN CHLD_CLT CCL1 ON CCL1.FKCLIENT_T = CAS2.FKCHLD_CLT   \n"
          + " JOIN CLIENT_T CLC1 ON CLC1.IDENTIFIER = CCL1.FKCLIENT_T  \n"
          + " WHERE CCL1.IBMSNAP_LOGMARKER > ?  \n"
          + "UNION     \n"
          + " SELECT CAS3.FKCHLD_CLT  \n"
          + " FROM CASE_T CAS3  \n"
          + " JOIN CLIENT_T CLC2 ON CLC2.IDENTIFIER = CAS3.FKCHLD_CLT \n"
          + " WHERE CLC2.IBMSNAP_LOGMARKER > ? \n"
          + "UNION  \n"
          + " SELECT CAS4.FKCHLD_CLT  \n"
          + " FROM CASE_T CAS4  \n"
          + " JOIN CLN_RELT CLR  ON CLR.FKCLIENT_T = CAS4.FKCHLD_CLT \n"
          + " WHERE CLR.IBMSNAP_LOGMARKER > ? \n"
          + "UNION  \n"
          + " SELECT CAS5.FKCHLD_CLT  \n"
          + " FROM CASE_T CAS5 \n"
          + " JOIN CLN_RELT CLR ON CLR.FKCLIENT_T = CAS5.FKCHLD_CLT \n"
          + " JOIN CLIENT_T CLP ON CLP.IDENTIFIER = CLR.FKCLIENT_0  \n"
          + " WHERE CLP.IBMSNAP_LOGMARKER > ? \n"
          + "), step2 AS ( \n"
          + " SELECT DISTINCT s1.FKCHLD_CLT FROM step1 s1 \n"
          + ") \n"
          + "SELECT c.FKCHLD_CLT  \n"
          + "FROM step2 c \n"
          + "WHERE   \n"
          + "   EXISTS (  \n"
          + "    SELECT CAS1.FKCHLD_CLT  \n"
          + "    FROM CASE_T CAS1   \n"
          + "    WHERE CAS1.FKCHLD_CLT = c.FKCHLD_CLT \n"
          + ") \n"
          + "OR EXISTS ( \n"
          + "     SELECT REL2.FKCLIENT_T  \n"
          + "     FROM CLN_RELT REL2  \n"
          + "     JOIN CASE_T   CAS2 ON CAS2.FKCHLD_CLT = REL2.FKCLIENT_0  \n"
          + "     WHERE REL2.FKCLIENT_T = c.FKCHLD_CLT  \n"
          + ")  \n"
          + "OR EXISTS (  \n"
          + "     SELECT REL3.FKCLIENT_0  \n"
          + "     FROM CLN_RELT REL3  \n"
          + "     JOIN CASE_T   CAS3 ON CAS3.FKCHLD_CLT = REL3.FKCLIENT_T  \n"
          + "     WHERE REL3.FKCLIENT_0 = c.FKCHLD_CLT  \n"
          + ") \n";
  //@formatter:on

  /**
   * Original, overkill approach. Brings back too much redundant data.
   */
  //@formatter:off
  public static final String SELECT_CASES_FULL_EVERYTHING = 
      "\nWITH DRIVER AS (\n"
          + " SELECT     \n"
          + "       c.IDENTIFIER        AS THIS_CLIENT_ID \n"
          + "     , TRIM(c.COM_FST_NM)  AS THIS_CLIENT_FIRST_NM \n"
          + "     , TRIM(c.COM_LST_NM)  AS THIS_CLIENT_LAST_NM \n"
          + "     , c.SENSTV_IND        AS THIS_CLIENT_SENSITIVITY_IND \n"
          + "     , c.LST_UPD_TS        AS THIS_CLIENT_LAST_UPDATED \n"
          + "     , c.IBMSNAP_LOGMARKER AS THIS_CLIENT_LOGMARKER \n"
          + "     , c.IBMSNAP_OPERATION AS THIS_CLIENT_OPERATION \n"
          + " FROM GT_ID GT \n"
          + " JOIN CLIENT_T C ON C.IDENTIFIER = GT.IDENTIFIER \n"
          + ") \n"
     + " SELECT   \n"
          + " CAS1.IDENTIFIER      AS CASE_ID, \n"
          + " CAS1.FKCHLD_CLT      AS FOCUS_CHILD_ID, \n"
          + " DRV1.THIS_CLIENT_ID  AS THIS_CLIENT_ID, \n"
          + " 1                    AS STANZA, \n"
          + " 0                    AS REL_FOCUS_TO_OTHER, \n"
          + " 0                    AS REL_OTHER_TO_FOCUS, \n"
          + " CAS1.CASE_NM         AS CASE_NAME, \n"
          + " CAS1.START_DT        AS START_DATE, \n"
          + " CAS1.END_DT          AS END_DATE, \n"
          + " CAS1.SRV_CMPC        AS SERVICE_COMP, \n"
          + " CAS1.CLS_RSNC        AS CLOSE_REASON_CODE, \n"
          + " CAS1.FKSTFPERST      AS WORKER_ID, \n"
          + " CAS1.LMT_ACSSCD      AS LIMITED_ACCESS_CODE, \n"
          + " CAS1.LMT_ACS_DT      AS LIMITED_ACCESS_DATE, \n"
          + " CAS1.LMT_ACSDSC      AS LIMITED_ACCESS_DESCRIPTION, \n"
          + " CAS1.L_GVR_ENTC      AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
          + " CAS1.LST_UPD_TS      AS CASE_LAST_UPDATED, \n"
          + " CAS1.GVR_ENTC        AS COUNTY, \n"
          + " CAS1.APV_STC \n"
          + "FROM DRIVER DRV1 \n"
          + "JOIN CASE_T CAS1 ON CAS1.FKCHLD_CLT = DRV1.THIS_CLIENT_ID \n"
          + "WHERE CAS1.IBMSNAP_OPERATION IN ('I','U') \n"
     + "UNION ALL \n"
          + "SELECT     \n"
          + " CAS2.IDENTIFIER      AS CASE_ID, \n"
          + " CAS2.FKCHLD_CLT      AS FOCUS_CHILD_ID, \n"
          + " DRV2.THIS_CLIENT_ID  AS THIS_CLIENT_ID, \n"
          + " 2                    AS STANZA, \n"
          + " REL2.CLNTRELC        AS REL_FOCUS_TO_OTHER, \n"
          + " 0                    AS REL_OTHER_TO_FOCUS, \n"
          + " CAS2.CASE_NM         AS CASE_NAME, \n"
          + " CAS2.START_DT        AS START_DATE, \n"
          + " CAS2.END_DT          AS END_DATE, \n"
          + " CAS2.SRV_CMPC        AS SERVICE_COMP, \n"
          + " CAS2.CLS_RSNC        AS CLOSE_REASON_CODE, \n"
          + " CAS2.FKSTFPERST      AS WORKER_ID, \n"
          + " CAS2.LMT_ACSSCD      AS LIMITED_ACCESS_CODE, \n"
          + " CAS2.LMT_ACS_DT      AS LIMITED_ACCESS_DATE, \n"
          + " CAS2.LMT_ACSDSC      AS LIMITED_ACCESS_DESCRIPTION, \n"
          + " CAS2.L_GVR_ENTC      AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
          + " CAS2.LST_UPD_TS      AS CASE_LAST_UPDATED, \n"
          + " CAS2.GVR_ENTC        AS COUNTY, \n"
          + " CAS2.APV_STC \n"
          + "FROM DRIVER DRV2 \n"
          + "JOIN CLN_RELT REL2 ON REL2.FKCLIENT_T = DRV2.THIS_CLIENT_ID \n"
          + "JOIN CASE_T   CAS2 ON CAS2.FKCHLD_CLT = REL2.FKCLIENT_0 \n"
          + "WHERE CAS2.IBMSNAP_OPERATION IN ('I','U') \n"
          + "  AND REL2.IBMSNAP_OPERATION IN ('I','U') \n"
    + "UNION ALL \n"
          + "SELECT  \n"
          + " CAS3.IDENTIFIER      AS CASE_ID, \n"
          + " CAS3.FKCHLD_CLT      AS FOCUS_CHILD_ID, \n"
          + " DRV3.THIS_CLIENT_ID  AS THIS_CLIENT_ID, \n"
          + " 3                    AS STANZA, \n"
          + " 0                    AS REL_FOCUS_TO_OTHER, \n"
          + " REL3.CLNTRELC        AS REL_OTHER_TO_FOCUS, \n"
          + " CAS3.CASE_NM         AS CASE_NAME, \n"
          + " CAS3.START_DT        AS START_DATE, \n"
          + " CAS3.END_DT          AS END_DATE, \n"
          + " CAS3.SRV_CMPC        AS SERVICE_COMP, \n"
          + " CAS3.CLS_RSNC        AS CLOSE_REASON_CODE, \n"
          + " CAS3.FKSTFPERST      AS WORKER_ID, \n"
          + " CAS3.LMT_ACSSCD      AS LIMITED_ACCESS_CODE, \n"
          + " CAS3.LMT_ACS_DT      AS LIMITED_ACCESS_DATE, \n"
          + " CAS3.LMT_ACSDSC      AS LIMITED_ACCESS_DESCRIPTION, \n"
          + " CAS3.L_GVR_ENTC      AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
          + " CAS3.LST_UPD_TS      AS CASE_LAST_UPDATED, \n"
          + " CAS3.GVR_ENTC        AS COUNTY, \n"
          + " CAS3.APV_STC \n"
          + "FROM DRIVER DRV3, CLN_RELT REL3, CASE_T CAS3 \n"
          + "WHERE CAS3.FKCHLD_CLT = REL3.FKCLIENT_T AND REL3.FKCLIENT_0 = DRV3.THIS_CLIENT_ID \n"
          + "  AND CAS3.IBMSNAP_OPERATION IN ('I','U') \n"
          + "  AND REL3.IBMSNAP_OPERATION IN ('I','U') \n"
     + " FOR READ ONLY WITH UR ";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_LAST_RUN_CHILD = 
        "SELECT \n"
      + " CAS.IDENTIFIER AS CASE_ID, \n"
      + " CAS.START_DT   AS START_DATE, \n"
      + " CAS.END_DT     AS END_DATE, \n"
      + " CAS.GVR_ENTC   AS COUNTY, \n"
      + " CAS.SRV_CMPC   AS SERVICE_COMP, \n"
      + " CAS.LMT_ACSSCD AS LIMITED_ACCESS_CODE, \n"
      + " CAS.LMT_ACS_DT AS LIMITED_ACCESS_DATE, \n"
      + " CAS.LMT_ACSDSC AS LIMITED_ACCESS_DESCRIPTION, \n"
      + " CAS.L_GVR_ENTC AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
      + " CAS.LST_UPD_TS AS CASE_LAST_UPDATED, \n"
      + " CLC.COM_FST_NM AS FOCUS_CHLD_FIRST_NM, \n"
      + " CLC.COM_LST_NM AS FOCUS_CHLD_LAST_NM, \n"
      + " CLC.IDENTIFIER AS FOCUS_CHILD_ID, \n"
      + " CLC.SENSTV_IND AS FOCUS_CHILD_SENSITIVITY_IND, \n"
      + " CLC.LST_UPD_TS AS FOCUS_CHILD_LAST_UPDATED, \n"
      + " CLP.COM_FST_NM AS PARENT_FIRST_NM, \n"
      + " CLP.COM_LST_NM AS PARENT_LAST_NM, \n"
      + " CLR.CLNTRELC   AS PARENT_RELATIONSHIP, \n"
      + " CLP.IDENTIFIER AS PARENT_ID, \n"
      + " CLP.SENSTV_IND AS PARENT_SENSITIVITY_IND, \n"
      + " CLP.LST_UPD_TS AS PARENT_LAST_UPDATED, \n"
      + " 'CLIENT_T'     AS PARENT_SOURCE_TABLE, \n"
      + " STF.FIRST_NM   AS WORKER_FIRST_NM, \n"
      + " STF.LAST_NM    AS WORKER_LAST_NM, \n"
      + " STF.IDENTIFIER AS WORKER_ID, \n"
      + " STF.LST_UPD_TS AS WORKER_LAST_UPDATED, \n"
      + " CAS.IBMSNAP_LOGMARKER AS CAS_IBMSNAP_LOGMARKER, \n"
      + " CAS.IBMSNAP_OPERATION AS CAS_IBMSNAP_OPERATION, \n"
      + " CLC.IBMSNAP_LOGMARKER AS CLC_IBMSNAP_LOGMARKER, \n"
      + " CLC.IBMSNAP_OPERATION AS CLC_IBMSNAP_OPERATION, \n"
      + " CLP.IBMSNAP_LOGMARKER AS CLP_IBMSNAP_LOGMARKER, \n"
      + " CLP.IBMSNAP_OPERATION AS CLP_IBMSNAP_OPERATION, \n"
      + " STF.IBMSNAP_LOGMARKER AS STF_IBMSNAP_LOGMARKER, \n"
      + " STF.IBMSNAP_OPERATION AS STF_IBMSNAP_OPERATION, \n"
      + " MAX( \n"
      + "     NVL(CAS.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')),\n"
      + "     NVL(CLC.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')),\n"
      + "     NVL(CLP.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')) \n"
      + " ) LAST_CHG \n"
      + "FROM CASE_T CAS \n"
      + "JOIN STFPERST STF ON STF.IDENTIFIER = CAS.FKSTFPERST \n"
      + "JOIN CHLD_CLT CCL ON CCL.FKCLIENT_T = CAS.FKCHLD_CLT \n"
      + "JOIN CLIENT_T CLC ON CLC.IDENTIFIER = CCL.FKCLIENT_T \n"
      + "LEFT JOIN CLN_RELT CLR ON CLR.FKCLIENT_0 = CCL.FKCLIENT_T "
   // + "AND ((CLR.CLNTRELC BETWEEN 187 and 214) OR \n"
   // + "(CLR.CLNTRELC BETWEEN 245 and 254) OR (CLR.CLNTRELC BETWEEN 282 and 294) OR (CLR.CLNTRELC IN (272, 273, 5620, 6360, 6361))) \n"
      + "LEFT JOIN CLIENT_T CLP ON CLP.IDENTIFIER = CLR.FKCLIENT_T \n"
      + "WHERE CAS.IDENTIFIER IN ( \n"
      + "   SELECT GT.IDENTIFIER FROM GT_ID GT \n"
      + ") \n";
  //@formatter:on

  //@formatter:off
  public static final String SELECT_LAST_RUN_PARENT = 
        "SELECT \n"
      + "  CAS.IDENTIFIER AS CASE_ID, \n"
      + "  CAS.START_DT   AS START_DATE, \n"
      + "  CAS.END_DT     AS END_DATE, \n"
      + "  CAS.GVR_ENTC   AS COUNTY, \n"
      + "  CAS.SRV_CMPC   AS SERVICE_COMP, \n"
      + "  CAS.LMT_ACSSCD AS LIMITED_ACCESS_CODE, \n"
      + "  CAS.LMT_ACS_DT AS LIMITED_ACCESS_DATE, \n"
      + "  CAS.LMT_ACSDSC AS LIMITED_ACCESS_DESCRIPTION, \n"
      + "  CAS.L_GVR_ENTC AS LIMITED_ACCESS_GOVERNMENT_ENT, \n"
      + "  CAS.LST_UPD_TS AS CASE_LAST_UPDATED, \n"
      + "  CLC.COM_FST_NM AS FOCUS_CHLD_FIRST_NM, \n"
      + "  CLC.COM_LST_NM AS FOCUS_CHLD_LAST_NM, \n"
      + "  CLC.IDENTIFIER AS FOCUS_CHILD_ID, \n"
      + "  CLC.SENSTV_IND AS FOCUS_CHILD_SENSITIVITY_IND, \n"
      + "  CLC.LST_UPD_TS AS FOCUS_CHILD_LAST_UPDATED, \n"
      + "  CLP.COM_FST_NM AS PARENT_FIRST_NM, \n"
      + "  CLP.COM_LST_NM AS PARENT_LAST_NM, \n"
      + "  CLR.CLNTRELC   AS PARENT_RELATIONSHIP, \n"
      + "  CLP.IDENTIFIER AS PARENT_ID, \n"
      + "  CLP.SENSTV_IND AS PARENT_SENSITIVITY_IND, \n"
      + "  CLP.LST_UPD_TS AS PARENT_LAST_UPDATED, \n"
      + "  'CLIENT_T'     AS PARENT_SOURCE_TABLE, \n"
      + "  STF.FIRST_NM   AS WORKER_FIRST_NM, \n"
      + "  STF.LAST_NM    AS WORKER_LAST_NM, \n"
      + "  STF.IDENTIFIER AS WORKER_ID, \n"
      + "  STF.LST_UPD_TS AS WORKER_LAST_UPDATED,   \n"
      + "  CAS.IBMSNAP_LOGMARKER AS CAS_IBMSNAP_LOGMARKER, \n"
      + "  CAS.IBMSNAP_OPERATION AS CAS_IBMSNAP_OPERATION, \n"
      + "  CLC.IBMSNAP_LOGMARKER AS CLC_IBMSNAP_LOGMARKER, \n"
      + "  CLC.IBMSNAP_OPERATION AS CLC_IBMSNAP_OPERATION, \n"
      + "  CLP.IBMSNAP_LOGMARKER AS CLP_IBMSNAP_LOGMARKER, \n"
      + "  CLP.IBMSNAP_OPERATION AS CLP_IBMSNAP_OPERATION, \n"
      + "  STF.IBMSNAP_LOGMARKER AS STF_IBMSNAP_LOGMARKER, \n"
      + "  STF.IBMSNAP_OPERATION AS STF_IBMSNAP_OPERATION, \n"
      + "  MAX ( \n"
      + "    NVL(CAS.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')),\n"
      + "    NVL(CLC.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')),\n"
      + "    NVL(CLP.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')) \n"
      + "  ) LAST_CHG \n"
      + "FROM CASE_T CAS \n"
      + "JOIN STFPERST STF ON STF.IDENTIFIER = CAS.FKSTFPERST \n"
      + "JOIN CHLD_CLT CCL ON CCL.FKCLIENT_T = CAS.FKCHLD_CLT \n"
      + "JOIN CLIENT_T CLC ON CLC.IDENTIFIER = CCL.FKCLIENT_T \n"
      + "LEFT JOIN CLN_RELT CLR ON CLR.FKCLIENT_T = CCL.FKCLIENT_T "
   // + "AND ((CLR.CLNTRELC BETWEEN 187 and 214) OR \n"
   // + "(CLR.CLNTRELC BETWEEN 245 and 254) OR (CLR.CLNTRELC BETWEEN 282 and 294) OR (CLR.CLNTRELC IN (272, 273, 5620, 6360, 6361))) \n"
      + "LEFT JOIN CLIENT_T CLP ON CLP.IDENTIFIER = CLR.FKCLIENT_0 \n"
      + "WHERE CAS.IDENTIFIER IN ( \n"
      + "   SELECT GT.IDENTIFIER FROM GT_ID GT \n"
      + ") \n";
  //@formatter:on

}
