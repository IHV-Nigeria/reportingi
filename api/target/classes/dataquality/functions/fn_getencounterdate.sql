DROP FUNCTION IF EXISTS getencounterdate;
/0xd
CREATE FUNCTION `getencounterdate`(`eid` LONG) RETURNS datetime
BEGIN
	
	DECLARE enc_date DATETIME;
	
	select encounter.encounter_datetime into enc_date from encounter where encounter.encounter_id=eid;

	RETURN enc_date;
END /0xd

