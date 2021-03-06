-- SET CURRENT SCHEMA = 'CWSRS1' ;

DROP VIEW VW_MQT_BI_DIR_RELATION;

CREATE VIEW VW_MQT_BI_DIR_RELATION (
	REVERSE_RELATIONSHIP,
	THIS_LEGACY_ID,
	THIS_SENSITIVITY_IND,
	THIS_LEGACY_LAST_UPDATED,
	THIS_LEGACY_LAST_UPDATED_ID,
	THIS_FIRST_NAME,
	THIS_LAST_NAME,
	REL_CODE,
	RELATED_LEGACY_ID,
	RELATED_SENSITIVITY_IND,
	RELATED_LEGACY_LAST_UPDATED,
	RELATED_LEGACY_LAST_UPDATED_ID,
	RELATED_FIRST_NAME,
	RELATED_LAST_NAME,
	REL_IBMSNAP_LOGMARKER,
	REL_IBMSNAP_OPERATION,
	THIS_IBMSNAP_LOGMARKER,
	THIS_IBMSNAP_OPERATION,
	RELATED_IBMSNAP_LOGMARKER,
	RELATED_IBMSNAP_OPERATION,
	LAST_CHG
) AS
SELECT
	0 AS REVERSE_RELATIONSHIP,
	v.THIS_LEGACY_ID,
	v.THIS_SENSITIVITY_IND,
	v.THIS_LEGACY_LAST_UPDATED,
	v.THIS_LEGACY_LAST_UPDATED_ID,
	v.THIS_FIRST_NAME,
	v.THIS_LAST_NAME,
	v.REL_CODE,
	v.RELATED_LEGACY_ID,
	v.RELATED_SENSITIVITY_IND,
	v.RELATED_LEGACY_LAST_UPDATED,
	v.RELATED_LEGACY_LAST_UPDATED_ID,
	v.RELATED_FIRST_NAME,
	v.RELATED_LAST_NAME,
	v.REL_IBMSNAP_LOGMARKER,
	v.REL_IBMSNAP_OPERATION,
	v.THIS_IBMSNAP_LOGMARKER,
	v.THIS_IBMSNAP_OPERATION,
	v.RELATED_IBMSNAP_LOGMARKER,
	v.RELATED_IBMSNAP_OPERATION,
	v.LAST_CHG
FROM MQT_REL_CLN_RELT_CLIENT v
UNION ALL
SELECT
	1 AS REVERSE_RELATIONSHIP,
	v.RELATED_LEGACY_ID,
	v.RELATED_SENSITIVITY_IND,
	v.RELATED_LEGACY_LAST_UPDATED,
	v.RELATED_LEGACY_LAST_UPDATED_ID,
	v.RELATED_FIRST_NAME,
	v.RELATED_LAST_NAME,
	v.REL_CODE,
	v.THIS_LEGACY_ID,
	v.THIS_SENSITIVITY_IND,
	v.THIS_LEGACY_LAST_UPDATED,
	v.THIS_LEGACY_LAST_UPDATED_ID,
	v.THIS_FIRST_NAME,
	v.THIS_LAST_NAME,
	v.REL_IBMSNAP_LOGMARKER,
	v.REL_IBMSNAP_OPERATION,
	v.THIS_IBMSNAP_LOGMARKER,
	v.THIS_IBMSNAP_OPERATION,
	v.RELATED_IBMSNAP_LOGMARKER,
	v.RELATED_IBMSNAP_OPERATION,
	v.LAST_CHG
FROM MQT_REL_CLN_RELT_CLIENT v;


COMMIT;
