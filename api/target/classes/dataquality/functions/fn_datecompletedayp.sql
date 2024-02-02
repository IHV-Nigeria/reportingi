DROP FUNCTION IF EXISTS datecompletedayp;
/0xd
CREATE FUNCTION `datecompletedayp`(patientid int) RETURNS date
BEGIN

DECLARE completion_date DATE;

select max(obs.value_datetime) into completion_date from obs where person_id=patientid and concept_id in(166261,166262,166263,166264,166265,166266,166268) and voided=0;

RETURN completion_date;
END /0xd