------------------------------------------------------------------------------------
-- SQL PROCEDURE FOR REFRESHING MQTs  
-- Created on: 10/30/2017                          
-- AUTHOR: TPT1 TEAM
------------------------------------------------------------------------------------
CREATE PROCEDURE CWSRS1.REFRESHMQT (
    IN MQT_NM VARCHAR(30),    	-- MQT NAME
    OUT RETCODE INTEGER			-- SQLCODE RETURNED FROM THE PROCEDURE
)
LANGUAGE SQL
PACKAGE OWNER CWDSDBA
QUALIFIER CWSRS1
COMMIT ON RETURN YES
P1: BEGIN
	DECLARE MQT_NAME VARCHAR(30);  
	DECLARE REFSQL VARCHAR(100);  
	DECLARE SQLCODE INTEGER DEFAULT 0;
				
--  DECLARE HANDLERS		            
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	SET RETCODE = SQLCODE;  
	SET MQT_NAME = RTRIM(MQT_NM);
	SET REFSQL = 'REFRESH TABLE MQT_CLIENT_ADDRESS';
--	REFRESH TABLE MQT_NAME; 
    EXECUTE IMMEDIATE REFSQL;
END P1
@