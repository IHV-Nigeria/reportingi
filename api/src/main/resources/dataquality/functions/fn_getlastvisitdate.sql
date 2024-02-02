DROP FUNCTION IF EXISTS getlastvisitdate;
/0xd
CREATE FUNCTION `getlastvisitdate`(`patient_id` int,`cuttoffdate` DATE) RETURNS int(11)
BEGIN
	DECLARE encid INT;
	select encounter.encounter_id into encid from encounter where encounter.voided=0 and form_id in(22,56,14,69,23,44,74,53,21,73,20,27,67) and encounter.encounter_datetime<=cuttoffdate and encounter.patient_id=patient_id order by encounter.encounter_datetime DESC LIMIT 1;

	RETURN encid;
END /0xd

