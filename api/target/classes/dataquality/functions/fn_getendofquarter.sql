DROP FUNCTION IF EXISTS getendofquarter;
/0xd

CREATE FUNCTION `getendofquarter`(`date_val` date) RETURNS date
BEGIN
	DECLARE fyear INT;
	DECLARE fquarter INT;
	DECLARE start_date DATE;
	DECLARE end_date DATE;
	
	DECLARE month_val INT;
	
	
	
	/*SET fyear=IF(QUARTER(date_val)=4,YEAR(date_val)+1,YEAR(date_val));*/
        SET fyear=YEAR(date_val);
	SET fquarter=IF(QUARTER(date_val)=4,MOD(QUARTER(date_val)+1,4),QUARTER(date_val)+1);
	
	SELECT CASE  
	WHEN fquarter=1 THEN 12
	WHEN fquarter=2 THEN 3
	WHEN fquarter=3 THEN 6
	WHEN fquarter=4 THEN 9
	END INTO month_val;
	
	
	
	
	SELECT STR_TO_DATE(CONCAT(fyear,"-",month_val,"-",1),'%Y-%c-%e') INTO start_date;
	
	SELECT LAST_DAY(start_date) INTO end_date;
	

	RETURN end_date;
END /0xd