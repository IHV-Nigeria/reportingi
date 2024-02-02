DROP FUNCTION IF EXISTS getobsdatetime;
/0xd

CREATE  FUNCTION `getobsdatetime`(`obsid` int) RETURNS date
BEGIN

    DECLARE val DATE;

    SELECT  obs.obs_datetime INTO val from obs WHERE  obs.obs_id=obsid;

	RETURN val;

END /0xd

