DROP FUNCTION IF EXISTS getobswithencounterid;
/0xd
CREATE FUNCTION `getobswithencounterid`(`encounter_id` int,`concept_id` int) RETURNS int(11)
BEGIN
	DECLARE obsid INT;
	
        SELECT obs.obs_id into obsid from obs where obs.voided=0 and obs.concept_id=concept_id and obs.encounter_id=encounter_id LIMIT 1;

	RETURN obsid;
END /0xd

