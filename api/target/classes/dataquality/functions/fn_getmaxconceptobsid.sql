DROP FUNCTION IF EXISTS getmaxconceptobsid;
/0xd

CREATE  FUNCTION `getmaxconceptobsid`(`patientid` int,`conceptid` int, `cutoffdate` DATE) RETURNS decimal(10,0)
BEGIN

    DECLARE value_num INT;

    SELECT  obs.obs_id into value_num from obs WHERE  obs.person_id=patientid and obs.concept_id=conceptid and obs.voided=0 and 
	obs.obs_datetime<=cutoffdate ORDER BY obs.obs_datetime DESC LIMIT 1;

	RETURN value_num;

END /0xd

