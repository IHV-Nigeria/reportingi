DROP FUNCTION IF EXISTS gettextvalueobsid;
/0xd
CREATE  FUNCTION `gettextvalueobsid`(`obsid` int) RETURNS text CHARSET utf8
BEGIN

    DECLARE val TEXT;

    SELECT  obs.value_text INTO val from obs WHERE  obs.obs_id=obsid;

	RETURN val;

END /0xd
