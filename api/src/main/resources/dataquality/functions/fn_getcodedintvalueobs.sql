DROP FUNCTION IF EXISTS getcodedintvalueobs;
/0xd
CREATE FUNCTION `getcodedintvalueobs`(`obsid` int) RETURNS int(11)
BEGIN 

    DECLARE val INT;

    SELECT  obs.value_coded INTO val from 
		obs 
		where obs.obs_id=obsid;
		
	RETURN val;

END /0xd
