-- SET CURRENT SCHEMA = 'CWSRS1' ;

DROP TABLE GT_REFR_CLT;

CREATE GLOBAL TEMPORARY TABLE GT_REFR_CLT ( 
	FKREFERL_T  CHAR(10) NOT NULL,
	FKCLIENT_T  CHAR(10) NOT NULL,
	SENSTV_IND 	CHAR(1)  NOT NULL
);

COMMIT;
