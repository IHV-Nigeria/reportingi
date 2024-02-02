DROP FUNCTION IF EXISTS getminconceptobswithformidvaluecoded;
/0xd
CREATE FUNCTION `getminconceptobswithformidvaluecoded`(`patientid` int,`conceptid` int, `formid` int, `val_coded` int, `cutoffdate` DATE) RETURNS decimal(10,0)
BEGIN

    DECLARE value_num INT;

    SELECT  obs.obs_id into value_num from obs inner join encounter on(encounter.encounter_id=obs.encounter_id and encounter.voided=0) WHERE  obs.person_id=patientid and obs.concept_id=conceptid and obs.voided=0 and 
	obs.obs_datetime<=cutoffdate and encounter.form_id=formid and obs.value_coded=val_coded  ORDER BY obs.obs_datetime ASC LIMIT 1;

	RETURN value_num;

END /0xd

