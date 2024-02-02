DROP FUNCTION IF EXISTS getlastencounter;
/0xd
CREATE FUNCTION `getlastencounter`(`patient_id` int,`form_id` int,`cuttoffdate` date) RETURNS mediumtext CHARSET utf8
BEGIN

	 DECLARE enc_id LONG;
	 
	 select encounter_id into enc_id from encounter where encounter.form_id=form_id and encounter.patient_id=patient_id and encounter.voided=0 AND encounter.encounter_datetime<=cuttoffdate order by encounter_datetime desc limit 1;
	 
	RETURN enc_id;
END /0xd