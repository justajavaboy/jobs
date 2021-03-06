DROP TRIGGER CWSINT.trg_allgtn_del;

CREATE TRIGGER CWSINT.trg_allgtn_del
AFTER DELETE ON CWSINT.ALLGTN_T
REFERENCING OLD AS OROW
FOR EACH ROW MODE DB2SQL
BEGIN ATOMIC
	MERGE INTO CWSRS1.ALLGTN_T adr USING (
		SELECT 
		orow.IDENTIFIER,
		orow.ABUSE_ENDT,
		orow.ABUSE_FREQ,
		orow.ABUSE_PDCD,
		orow.LOC_DSC,
		orow.ABUSE_STDT,
		orow.ALG_DSPC,
		orow.ALG_TPC,
		orow.DISPSN_DSC,
		orow.DISPSN_DT,
		orow.IJHM_DET_B,
		orow.NON_PRT_CD,
		orow.STFADD_IND,
		orow.LST_UPD_ID,
		orow.LST_UPD_TS,
		orow.FKCLIENT_T,
		orow.FKCLIENT_0,
		orow.FKREFERL_T,
		orow.CNTY_SPFCD,
		orow.ZIPPY_IND,
		orow.PLC_FCLC
		FROM sysibm.sysdummy1
	) X ON (adr.IDENTIFIER = X.IDENTIFIER) 
	WHEN MATCHED THEN UPDATE SET 
		ABUSE_ENDT = x.ABUSE_ENDT,
		ABUSE_FREQ = x.ABUSE_FREQ,
		ABUSE_PDCD = x.ABUSE_PDCD,
		LOC_DSC = x.LOC_DSC,
		ABUSE_STDT = x.ABUSE_STDT,
		ALG_DSPC = x.ALG_DSPC,
		ALG_TPC = x.ALG_TPC,
		DISPSN_DSC = x.DISPSN_DSC,
		DISPSN_DT = x.DISPSN_DT,
		IJHM_DET_B = x.IJHM_DET_B,
		NON_PRT_CD = x.NON_PRT_CD,
		STFADD_IND = x.STFADD_IND,
		LST_UPD_ID = x.LST_UPD_ID,
		LST_UPD_TS = x.LST_UPD_TS,
		FKCLIENT_T = x.FKCLIENT_T,
		FKCLIENT_0 = x.FKCLIENT_0,
		FKREFERL_T = x.FKREFERL_T,
		CNTY_SPFCD = x.CNTY_SPFCD,
		ZIPPY_IND = x.ZIPPY_IND,
		PLC_FCLC = x.PLC_FCLC,
		IBMSNAP_OPERATION = 'D',
		IBMSNAP_LOGMARKER = current timestamp
	WHEN NOT MATCHED THEN INSERT (
		IDENTIFIER,
		ABUSE_ENDT,
		ABUSE_FREQ,
		ABUSE_PDCD,
		LOC_DSC,
		ABUSE_STDT,
		ALG_DSPC,
		ALG_TPC,
		DISPSN_DSC,
		DISPSN_DT,
		IJHM_DET_B,
		NON_PRT_CD,
		STFADD_IND,
		LST_UPD_ID,
		LST_UPD_TS,
		FKCLIENT_T,
		FKCLIENT_0,
		FKREFERL_T,
		CNTY_SPFCD,
		ZIPPY_IND,
		PLC_FCLC,
		IBMSNAP_OPERATION,
		IBMSNAP_LOGMARKER
	) VALUES (
		x.IDENTIFIER,
		x.ABUSE_ENDT,
		x.ABUSE_FREQ,
		x.ABUSE_PDCD,
		x.LOC_DSC,
		x.ABUSE_STDT,
		x.ALG_DSPC,
		x.ALG_TPC,
		x.DISPSN_DSC,
		x.DISPSN_DT,
		x.IJHM_DET_B,
		x.NON_PRT_CD,
		x.STFADD_IND,
		x.LST_UPD_ID,
		x.LST_UPD_TS,
		x.FKCLIENT_T,
		x.FKCLIENT_0,
		x.FKREFERL_T,
		x.CNTY_SPFCD,
		x.ZIPPY_IND,
		x.PLC_FCLC,
		'D',
		current timestamp
	);
END
