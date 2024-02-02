DROP FUNCTION IF EXISTS getconceptvalV2;
/0xd

CREATE FUNCTION `getconceptvalV2`(`encounter_id` INT,`cid` INT, pid INT) RETURNS DECIMAL(10,0)
BEGIN
    DECLARE value_num INT;
       SELECT obs1.value_numeric INTO value_num FROM obs obs1
	INNER JOIN obs obs2 ON(obs1.`obs_group_id` = obs2.obs_id AND obs2.concept_id = 162240 AND obs1.encounter_id = encounter_id)
	WHERE obs1.value_numeric IS NOT NULL  AND obs1.concept_id = cid AND obs1.person_id =pid
	ORDER BY obs1.obs_datetime DESC LIMIT 1;
	RETURN value_num;
END /0xd