DROP FUNCTION IF EXISTS `getcurrentregimen`;
/0xd


CREATE FUNCTION `getcurrentregimen`(`cid` INT,`eid` INT) RETURNS TEXT CHARSET UTF8
BEGIN
 DECLARE regimen TEXT;
   SELECT cn1.name INTO regimen
   FROM obs
   INNER JOIN concept_name cn1
   ON (obs.value_coded=cn1.concept_id
        AND cn1.locale='en'
        AND cn1.locale_preferred=1)
    WHERE obs.concept_id=cid
    AND obs.encounter_id=eid
    AND obs.voided=0 LIMIT 1;

	RETURN regimen;
END
/0xd