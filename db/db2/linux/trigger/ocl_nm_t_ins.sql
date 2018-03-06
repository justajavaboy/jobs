DROP TRIGGER CWSINT.trg_ocl_nm_t_ins;

CREATE TRIGGER CWSINT.trg_ocl_nm_t_ins
AFTER INSERT ON CWSINT.OCL_NM_T
REFERENCING NEW AS NROW
FOR EACH ROW MODE DB2SQL
BEGIN ATOMIC
MERGE INTO CWSRS1.OCL_NM_T tgt USING ( 
SELECT
	nrow.THIRD_ID,
	nrow.FIRST_NM,
	nrow.LAST_NM,
	nrow.MIDDLE_NM,
	nrow.NMPRFX_DSC,
	nrow.NAME_TPC,
	nrow.SUFX_TLDSC,
	nrow.LST_UPD_ID,
	nrow.LST_UPD_TS,
	nrow.FKCLIENT_T
FROM sysibm.sysdummy1
) X ON (tgt.FKCLIENT_T = X.FKCLIENT_T AND tgt.THIRD_ID = X.THIRD_ID)
WHEN MATCHED THEN UPDATE SET 
	FIRST_NM = x.FIRST_NM,
	LAST_NM = x.LAST_NM,
	MIDDLE_NM = x.MIDDLE_NM,
	NMPRFX_DSC = x.NMPRFX_DSC,
	NAME_TPC = x.NAME_TPC,
	SUFX_TLDSC = x.SUFX_TLDSC,
	LST_UPD_ID = x.LST_UPD_ID,
	LST_UPD_TS = x.LST_UPD_TS,
	IBMSNAP_OPERATION = 'I',
	IBMSNAP_LOGMARKER = current timestamp
WHEN NOT MATCHED THEN INSERT (
	THIRD_ID,
	FIRST_NM,
	LAST_NM,
	MIDDLE_NM,
	NMPRFX_DSC,
	NAME_TPC,
	SUFX_TLDSC,
	LST_UPD_ID,
	LST_UPD_TS,
	FKCLIENT_T,
	IBMSNAP_OPERATION,
	IBMSNAP_LOGMARKER
) VALUES (
	x.THIRD_ID,
	x.FIRST_NM,
	x.LAST_NM,
	x.MIDDLE_NM,
	x.NMPRFX_DSC,
	x.NAME_TPC,
	x.SUFX_TLDSC,
	x.LST_UPD_ID,
	x.LST_UPD_TS,
	x.FKCLIENT_T,
	'I',
	current timestamp
);
END