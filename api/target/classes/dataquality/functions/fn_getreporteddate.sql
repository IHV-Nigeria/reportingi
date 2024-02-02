DROP FUNCTION IF EXISTS `getreporteddate`;
/0xd

CREATE FUNCTION `getreporteddate`(`encounter_id` INT,`reporteddateconcept_id` INT,`patientid` INT) RETURNS DATE
    BEGIN
        DECLARE val_date DATE;
        SELECT obs.value_datetime INTO val_date FROM obs
        WHERE concept_id=reporteddateconcept_id
        AND obs.encounter_id=encounter_id
        AND obs.person_id=patientid
        AND voided=0 LIMIT 1;
        RETURN val_date;
    END
/0xd