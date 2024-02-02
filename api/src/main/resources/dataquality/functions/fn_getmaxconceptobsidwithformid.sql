DROP FUNCTION IF EXISTS getmaxconceptobsidwithformid;
/0xd
CREATE  FUNCTION `getmaxconceptobsidwithformid`(`patientid` int,`conceptid` int, `formid` int,`cutoffdate` DATE) RETURNS decimal(10,0)
BEGIN

    DECLARE value_num INT;

    SELECT  obs.obs_id into value_num from obs inner join encounter on(encounter.encounter_id=obs.encounter_id and encounter.voided=0) WHERE  encounter.form_id=formid and obs.person_id=patientid 
		and obs.concept_id=conceptid and obs.voided=0 and obs.obs_datetime<=cutoffdate ORDER BY obs.obs_datetime DESC LIMIT 1;

	RETURN value_num;

END /0xd

