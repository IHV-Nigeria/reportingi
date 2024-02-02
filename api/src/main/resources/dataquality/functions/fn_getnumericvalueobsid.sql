DROP FUNCTION IF EXISTS getnumericvalueobsid;
/0xd
CREATE FUNCTION `getnumericvalueobsid`(`obsid` int) RETURNS decimal(10,2)
BEGIN

    DECLARE val DECIMAL(10,2);

    SELECT  obs.value_numeric INTO val from obs WHERE  obs.obs_id=obsid;
	RETURN val;
END /0xd

