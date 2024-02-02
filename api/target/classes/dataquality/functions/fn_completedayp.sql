DROP FUNCTION IF EXISTS completedayp;
/0xd
CREATE FUNCTION `completedayp`(patientid int) RETURNS varchar(10) CHARSET utf8
BEGIN

DECLARE total INT;

DECLARE ans VARCHAR(10);

select count(distinct concept_id) into total from obs where person_id=patientid and concept_id in(166261,166262,166263,166264,166265,166266,166268) and voided=0;

IF total < 7
  THEN SET ans = 'No';
ELSE  
  SET ans = 'Yes';
END IF;
RETURN ans;
END /0xd
