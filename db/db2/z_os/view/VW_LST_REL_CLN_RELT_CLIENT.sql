-- SET CURRENT SCHEMA = 'CWSRS1' ;

DROP VIEW VW_LST_REL_CLN_RELT_CLIENT;

CREATE VIEW VW_LST_REL_CLN_RELT_CLIENT (
  THIS_LEGACY_TABLE,
  RELATED_LEGACY_TABLE, 
  THIS_LEGACY_ID,
  THIS_SENSITIVITY_IND,
  THIS_LEGACY_LAST_UPDATED_ID,
  THIS_LEGACY_LAST_UPDATED,
  THIS_FIRST_NAME,
  THIS_LAST_NAME,
  REL_CODE,
  RELATED_LEGACY_ID,
  RELATED_SENSITIVITY_IND,
  RELATED_LEGACY_LAST_UPDATED_ID,
  RELATED_LEGACY_LAST_UPDATED,
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
    'CLN_RELT'              AS THIS_LEGACY_TABLE,
    'CLIENT_T'              AS RELATED_LEGACY_TABLE,
    CLNS.IDENTIFIER         AS THIS_LEGACY_ID,
    CLNS.SENSTV_IND         AS THIS_SENSITIVITY_IND,
    CLNS.LST_UPD_ID         AS THIS_LEGACY_LAST_UPDATED_ID,
    CLNS.LST_UPD_TS         AS THIS_LEGACY_LAST_UPDATED,
    CLNS.COM_FST_NM         AS THIS_FIRST_NAME,
    CLNS.COM_LST_NM         AS THIS_LAST_NAME,
    CLNR.CLNTRELC           AS REL_CODE,
    CLNP.IDENTIFIER         AS RELATED_LEGACY_ID,
    CLNP.SENSTV_IND         AS RELATED_SENSITIVITY_IND,
    CLNP.LST_UPD_ID         AS RELATED_LEGACY_LAST_UPDATED_ID,
    CLNP.LST_UPD_TS         AS RELATED_LEGACY_LAST_UPDATED,
    CLNP.COM_FST_NM         AS RELATED_FIRST_NAME,
    CLNP.COM_LST_NM         AS RELATED_LAST_NAME,
    CLNR.IBMSNAP_LOGMARKER  AS REL_IBMSNAP_LOGMARKER,
    CLNR.IBMSNAP_OPERATION  AS REL_IBMSNAP_OPERATION,
    CLNS.IBMSNAP_LOGMARKER  AS THIS_IBMSNAP_LOGMARKER,
    CLNS.IBMSNAP_OPERATION  AS THIS_IBMSNAP_OPERATION,
    CLNP.IBMSNAP_LOGMARKER  AS RELATED_IBMSNAP_LOGMARKER,
    CLNP.IBMSNAP_OPERATION  AS RELATED_IBMSNAP_OPERATION,
    MAX (
      CLNR.IBMSNAP_LOGMARKER,
      CLNP.IBMSNAP_LOGMARKER,
      CLNS.IBMSNAP_LOGMARKER
    ) AS LAST_CHG
FROM CLN_RELT CLNR
JOIN CLIENT_T CLNS ON CLNR.FKCLIENT_T = CLNS.IDENTIFIER
JOIN CLIENT_T CLNP ON CLNR.FKCLIENT_0 = CLNP.IDENTIFIER
WHERE CLNS.IDENTIFIER IN (
	SELECT GT.IDENTIFIER FROM GT_ID GT
)
   OR CLNP.IDENTIFIER IN (
	SELECT GT.IDENTIFIER FROM GT_ID GT
);

COMMIT;

