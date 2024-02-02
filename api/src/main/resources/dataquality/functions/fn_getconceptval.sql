DROP FUNCTION IF EXISTS getconceptval;
/0xd

CREATE FUNCTION `getconceptval`(`obsid` INT,`cid` INT, pid INT) RETURNS DECIMAL(10,0)
BEGIN
    DECLARE value_num INT;
    SELECT DISTINCT obs.value_numeric INTO value_num FROM obs
        WHERE  obs.obs_group_id IS NOT NULL
        AND obs.obs_group_id=obsid
        AND obs.concept_id=cid
        AND obs.person_id=pid
        AND obs.voided=0
        AND obs.value_numeric  IS NOT NULL
        ORDER BY obs_id ASC
        LIMIT 1;
	RETURN value_num;
END /0xd