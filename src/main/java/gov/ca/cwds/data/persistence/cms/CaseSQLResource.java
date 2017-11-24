package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.std.ApiMarker;

public class CaseSQLResource implements ApiMarker {

  private static final long serialVersionUID = 1L;

//@formatter:off
  public static final String BASE_VIEW_SELECT = 
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
      + " CLC.LST_UPD_TS AS FOCUS_CHILD_LAST_UPDATED,   \n"
      + " CLP.COM_FST_NM AS PARENT_FIRST_NM, \n"
      + " CLP.COM_LST_NM AS PARENT_LAST_NM, \n"
      + " CLR.CLNTRELC   AS PARENT_RELATIONSHIP, \n"
      + " CLP.IDENTIFIER AS PARENT_ID, \n"
      + " CLP.SENSTV_IND AS PARENT_SENSITIVITY_IND, \n"
      + " CLP.LST_UPD_TS AS PARENT_LAST_UPDATED,   \n"
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
      + "     NVL(CAS.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')), \n"
      + "     NVL(CLC.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')), \n"
      + "     NVL(CLP.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40'))    \n"
      + " ) LAST_CHG \n"
      + "FROM CASE_T CAS \n"
      + "LEFT JOIN CWSRS1.CHLD_CLT CCL ON CCL.FKCLIENT_T = CAS.FKCHLD_CLT \n"
      + "LEFT JOIN CWSRS1.CLIENT_T CLC ON CLC.IDENTIFIER = CCL.FKCLIENT_T \n"
      + "LEFT JOIN CLIENT_T CLC ON CLC.IDENTIFIER = CCL.FKCLIENT_T \n"
      + "LEFT JOIN CLN_RELT CLR ON CLR.FKCLIENT_0 = CCL.FKCLIENT_T AND ((CLR.CLNTRELC BETWEEN 187 and 214) OR \n"
      + "(CLR.CLNTRELC BETWEEN 245 and 254) OR (CLR.CLNTRELC BETWEEN 282 and 294) OR (CLR.CLNTRELC IN (272, 273, 5620, 6360, 6361))) \n"
      + "LEFT JOIN CLIENT_T CLP ON CLP.IDENTIFIER = CLR.FKCLIENT_T \n"
      + "LEFT JOIN STFPERST STF ON STF.IDENTIFIER = CAS.FKSTFPERST \n"
      + "WHERE CAS.IDENTIFIER IN ( \n"
      + " SELECT GT.IDENTIFIER FROM GT_ID GT \n"
      + ") \n"
   + "UNION \n"
      + "SELECT \n"
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
      + "    NVL(CAS.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')), \n"
      + "    NVL(CLC.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40')), \n"
      + "    NVL(CLP.IBMSNAP_LOGMARKER, TIMESTAMP('2008-09-30 11:54:40'))  \n"
      + "  ) LAST_CHG \n"
      + "FROM CASE_T CAS \n"
      + "LEFT JOIN CHLD_CLT CCL ON CCL.FKCLIENT_T = CAS.FKCHLD_CLT \n"
      + "LEFT JOIN CLIENT_T CLC ON CLC.IDENTIFIER = CCL.FKCLIENT_T \n"
      + "LEFT JOIN CLN_RELT CLR ON CLR.FKCLIENT_T = CCL.FKCLIENT_T AND ((CLR.CLNTRELC BETWEEN 187 and 214) OR \n"
      + "(CLR.CLNTRELC BETWEEN 245 and 254) OR (CLR.CLNTRELC BETWEEN 282 and 294) OR (CLR.CLNTRELC IN (272, 273, 5620, 6360, 6361))) \n"
      + "LEFT JOIN CLIENT_T CLP ON CLP.IDENTIFIER = CLR.FKCLIENT_0 \n"
      + "LEFT JOIN STFPERST STF ON STF.IDENTIFIER = CAS.FKSTFPERST \n"
      + "WHERE CAS.IDENTIFIER IN ( \n"
      + " SELECT GT.IDENTIFIER FROM GT_ID GT \n"
      + ") \n";
//@formatter:on

}
