DROP TRIGGER CWSINT.trg_oth_adlt_del;

CREATE TRIGGER CWSINT.trg_oth_adlt_del
AFTER DELETE ON CWSINT.OTH_ADLT
REFERENCING OLD AS OROW
FOR EACH ROW MODE DB2SQL
BEGIN ATOMIC
	MERGE INTO CWSRS1.OTH_ADLT tgt USING ( 
		SELECT
		orow.IDENTIFIER,
		orow.BIRTH_DT,
		orow.END_DT,
		orow.GENDER_CD,
		orow.OTH_ADLTNM,
		orow.START_DT,
		orow.LST_UPD_ID,
		orow.LST_UPD_TS,
		orow.FKPLC_HM_T,
		orow.COMNT_DSC,
		orow.OTH_ADL_CD,
		orow.IDENTFD_DT,
		orow.RESOST_IND,
		orow.PASSBC_CD
		FROM sysibm.sysdummy1
	) X ON (tgt.IDENTIFIER = X.IDENTIFIER)
	WHEN MATCHED THEN UPDATE SET 
		BIRTH_DT = x.BIRTH_DT,
		END_DT = x.END_DT,
		GENDER_CD = x.GENDER_CD,
		OTH_ADLTNM = x.OTH_ADLTNM,
		START_DT = x.START_DT,
		LST_UPD_ID = x.LST_UPD_ID,
		LST_UPD_TS = x.LST_UPD_TS,
		FKPLC_HM_T = x.FKPLC_HM_T,
		COMNT_DSC = x.COMNT_DSC,
		OTH_ADL_CD = x.OTH_ADL_CD,
		IDENTFD_DT = x.IDENTFD_DT,
		RESOST_IND = x.RESOST_IND,
		PASSBC_CD = x.PASSBC_CD,
		IBMSNAP_OPERATION = 'D',
		IBMSNAP_LOGMARKER = current timestamp
	WHEN NOT MATCHED THEN INSERT (
		IDENTIFIER,
		BIRTH_DT,
		END_DT,
		GENDER_CD,
		OTH_ADLTNM,
		START_DT,
		LST_UPD_ID,
		LST_UPD_TS,
		FKPLC_HM_T,
		COMNT_DSC,
		OTH_ADL_CD,
		IDENTFD_DT,
		RESOST_IND,
		PASSBC_CD,
		IBMSNAP_OPERATION,
		IBMSNAP_LOGMARKER
		) VALUES (
		x.IDENTIFIER,
		x.BIRTH_DT,
		x.END_DT,
		x.GENDER_CD,
		x.OTH_ADLTNM,
		x.START_DT,
		x.LST_UPD_ID,
		x.LST_UPD_TS,
		x.FKPLC_HM_T,
		x.COMNT_DSC,
		x.OTH_ADL_CD,
		x.IDENTFD_DT,
		x.RESOST_IND,
		x.PASSBC_CD,
		'D',
		current timestamp
	);
END
