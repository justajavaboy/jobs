-- DROP TABLE CWSRSQ.GT_REFR_CLT;

CREATE GLOBAL TEMPORARY TABLE CWSRSQ.GT_REFR_CLT ( 
	FKREFERL_T  CHAR(10) NOT NULL,
	FKCLIENT_T  CHAR(10) NOT NULL,
	SENSTV_IND 	CHAR(1)  NOT NULL
);

