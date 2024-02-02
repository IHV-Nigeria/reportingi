DROP FUNCTION IF EXISTS previous;
/0xd
CREATE FUNCTION `previous`(patientid int, conceptid int, cuttoffdate DATE) RETURNS int(11)
BEGIN
DECLARE value_num INT;

    SELECT  obs.obs_id into value_num from obs WHERE  obs.person_id=patientid and obs.concept_id=conceptid and obs.voided=0 and 
	obs.obs_datetime<cuttoffdate ORDER BY obs.obs_datetime DESC LIMIT 1;

	RETURN value_num;
END /0xd

