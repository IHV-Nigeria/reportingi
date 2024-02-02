DROP FUNCTION IF EXISTS previousobsfromform;
/0xd
CREATE FUNCTION `previousobsfromform`(patientid int, conceptid int, formid int, cuttoffdate DATE) RETURNS int(11)
BEGIN
DECLARE value_num INT;

    SELECT  obs.obs_id into value_num from obs inner join encounter on(encounter.encounter_id=obs.encounter_id and encounter.voided=0) WHERE
    obs.person_id=patientid and obs.concept_id=conceptid and encounter.form_id=formid and obs.voided=0 and 
	obs.obs_datetime<cuttoffdate ORDER BY obs.obs_datetime DESC LIMIT 1;

	RETURN value_num;
END /0xd
