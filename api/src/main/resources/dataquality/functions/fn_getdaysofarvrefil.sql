DROP FUNCTION IF EXISTS getdaysofarvrefil;
/0xd


CREATE FUNCTION `getdaysofarvrefil`(`obsid` NUMERIC,`obsgroupid` NUMERIC,`valuenumeric` NUMERIC) RETURNS DECIMAL(10,0)
BEGIN
DECLARE ans NUMERIC;
    IF obsid=obsgroupid THEN
        SET ans = valuenumeric;
    ELSE
         SET ans = NULL;
    END IF;
RETURN ans;
END /0xd