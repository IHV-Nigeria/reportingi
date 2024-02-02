DROP FUNCTION IF EXISTS getencounterid;
/0xd
CREATE  FUNCTION `getencounterid`(`obsid` int) RETURNS int(11)
BEGIN

    DECLARE val INT;

    SELECT  obs.encounter_id INTO val from 
		obs 
		where obs.obs_id=obsid;
		

	RETURN val;

END /0xd

