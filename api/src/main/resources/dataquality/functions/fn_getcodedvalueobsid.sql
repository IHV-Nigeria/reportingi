DROP FUNCTION IF EXISTS getcodedvalueobsid;
/0xd
CREATE FUNCTION `getcodedvalueobsid`(`obsid` int) RETURNS text CHARSET utf8
BEGIN

    DECLARE val TEXT;

    SELECT  cn.name INTO val from 
		obs 
		inner join concept_name cn on(obs.value_coded=cn.concept_id and cn.locale='en' and cn.locale_preferred=1) where obs.obs_id=obsid;
		

	RETURN val;

END /0xd