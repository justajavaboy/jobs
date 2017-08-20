DROP VIEW CWSRS1.VW_LST_SAFETY_ALERT;

CREATE VIEW CWSRS1.VW_LST_SAFETY_ALERT (
  ALERT_ID,
  CLIENT_ID,
  CLIENT_SENSITIVITY_IND,
  ACTIVATION_REASON_CD,
  ACTIVATION_DATE,
  ACTIVATION_COUNTY_CD,
  ACTIVATION_EXPLANATION,
  DEACTIVATION_DATE,
  DEACTIVATION_COUNTY_CD,
  DEACTIVATION_EXPLANATION,
  LAST_UPDATED_ID,
  LAST_UPDATED_TS,
  ALERT_IBMSNAP_OPERATION,
  ALERT_IBMSNAP_LOGMARKER,
  LAST_CHANGED
) AS
SELECT 
  SALR.THIRD_ID AS ALERT_ID,
  CLNT.IDENTIFIER AS CLIENT_ID,
  CLNT.SENSTV_IND AS CLIENT_SENSITIVITY_IND,
  SALR.ACTV_RNC AS ACTIVATION_REASON_CD,
  SALR.ACTV_DT AS ACTIVATION_DATE,
  SALR.ACTV_GEC AS ACTIVATION_COUNTY_CD,
  ACTV_LONG_TXT.TTEXT_DSC AS ACTIVATION_EXPLANATION,
  SALR.DACT_DT AS DEACTIVATION_DATE,
  SALR.DACT_GEC AS DEACTIVATION_COUNTY_CD,
  DACTV_LONG_TXT.TTEXT_DSC AS DEACTIVATION_EXPLANATION,
  SALR.LST_UPD_ID AS LAST_UPDATED_ID,
  SALR.LST_UPD_TS AS LAST_UPDATED_TS,
  SALR.IBMSNAP_OPERATION AS ALERT_IBMSNAP_OPERATION,
  SALR.IBMSNAP_LOGMARKER AS ALERT_IBMSNAP_LOGMARKER,
  SALR.IBMSNAP_LOGMARKER AS LAST_CHANGED
FROM CWSRS1.SAF_ALRT SALR
JOIN CWSRS1.CLIENT_T CLNT ON CLNT.IDENTIFIER = SALR.FKCLIENT_T
LEFT OUTER JOIN CWSRS1.LONG_TXT ACTV_LONG_TXT ON SALR.ACTV_TXT = ACTV_LONG_TXT.IDENTIFIER
LEFT OUTER JOIN CWSRS1.LONG_TXT DACTV_LONG_TXT ON SALR.DACT_TXT = DACTV_LONG_TXT.IDENTIFIER;