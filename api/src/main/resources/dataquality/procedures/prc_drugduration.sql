DROP PROCEDURE IF EXISTS drugduration;
/0xd

CREATE PROCEDURE `drugduration`(IN `enddate` DATE)
BEGIN
    DROP TABLE IF EXISTS arvmedication;
    CREATE   TABLE  arvmedication AS
    SELECT  DISTINCT obs.person_id, obs.obs_id FROM obs WHERE concept_id=162240 AND voided=0 AND obs.obs_datetime <=enddate
    GROUP BY obs.person_id,obs.concept_id ORDER BY  obs.obs_datetime DESC;

CREATE  INDEX pid ON arvmedication(person_id);

END
/0xd