-- DB2 View/MQT
-- ORDER BY clause is only valid on mainframe, remove it if running on other hosts.

DROP TABLE CWSRS1.MQT_OTHER_CLIENT_NAME ;

CREATE TABLE CWSRS1.MQT_OTHER_CLIENT_NAME AS (
	SELECT 
	ONM.THIRD_ID AS THIRD_ID, 
	ONM.FIRST_NM AS FIRST_NM, 
	ONM.LAST_NM AS LAST_NM, 
	ONM.MIDDLE_NM AS MIDDLE_NM, 
	ONM.NMPRFX_DSC AS NMPRFX_DSC,
	ONM.NAME_TPC AS NAME_TPC, 
	ONM.SUFX_TLDSC AS SUFX_TLDSC, 
	ONM.LST_UPD_ID AS LST_UPD_ID, 
	ONM.LST_UPD_TS AS LST_UPD_TS, 
	ONM.FKCLIENT_T AS FKCLIENT_T, 	
	ONM.IBMSNAP_OPERATION AS IBMSNAP_OPERATION, 
	ONM.IBMSNAP_LOGMARKER AS IBMSNAP_LOGMARKER,
	CLT.SENSTV_IND AS CLIENT_SENSITIVITY_IND,
	MAX(ONM.IBMSNAP_LOGMARKER, CLT.IBMSNAP_LOGMARKER) LAST_CHG
FROM CWSRS1.OCL_NM_T ONM
JOIN CWSRS1.CLIENT_T CLT ON CLT.IDENTIFIER = ONM.FKCLIENT_T
ORDER BY FKCLIENT_T  -- MAINFRAME ONLY!
)
DATA INITIALLY DEFERRED
REFRESH DEFERRED
DISABLE QUERY OPTIMIZATION;

-- Execute following commands on linux hosts
SET INTEGRITY FOR CWSRS1.MQT_OTHER_CLIENT_NAME MATERIALIZED QUERY IMMEDIATE UNCHECKED;
REFRESH TABLE CWSRS1.MQT_OTHER_CLIENT_NAME;
