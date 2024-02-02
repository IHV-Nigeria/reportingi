DROP FUNCTION IF EXISTS getdatevalueobsid;
/0xd
CREATE FUNCTION `getdatevalueobsid`(`obsid` int) RETURNS datetime
BEGIN

    DECLARE val DATETIME;

    SELECT  obs.value_datetime into val from obs WHERE  obs.obs_id=obsid;

	RETURN val;

END /0xd

