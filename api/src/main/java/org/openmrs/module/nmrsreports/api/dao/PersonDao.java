/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;

/**
 * @author ihvn
 */
public class PersonDao {
	
	public int getTotalPersons() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "SELECT COUNT(person.person_id) AS count  FROM person WHERE person.voided=0 ";
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			rs.next();
			int totalCount = rs.getInt("count");
			return totalCount;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public List<Map<String, String>> getAllPatients(int limit, int offset, String lastUpdateDate) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		List<Map<String, String>> allPatients = new ArrayList();
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = " SELECT patient_id FROM patient  WHERE patient.voided=0 AND (patient.date_created >=? OR patient.date_changed >=? ";
			query += " OR " + " " + "   patient_id  in (SELECT person_id FROM obs WHERE date_created >= ? ) OR "
			        + "   patient_id IN (SELECT patient_id FROM encounter WHERE date_created >=? OR date_changed >=?) "
			        + ")";
			query += " ORDER BY patient_id LIMIT " + offset + ", " + limit;
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//stmt.setFetchSize(200);
			stmt.setString(i++, lastUpdateDate);
			stmt.setString(i++, lastUpdateDate);
			stmt.setString(i++, lastUpdateDate);
			stmt.setString(i++, lastUpdateDate);
			stmt.setString(i++, lastUpdateDate);
			
			rs = stmt.executeQuery();
			rs.setFetchSize(limit);
			while (rs.next()) {
				Map<String, String> tempMap = new HashMap();
				tempMap.put("patient_id", rs.getString("patient_id"));
				allPatients.add(tempMap);
			}
			return allPatients;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public List<Map<String, String>> getARTLL(int patientId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		List<Map<String, String>> allPatients = new ArrayList();
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = " select  "
			        + " nigeria_datimcode_mapping.state_name as `State`, "
			        + " nigeria_datimcode_mapping.lga_name as `LGA`, "
			        + " gp1.property_value as DatimCode, "
			        + " gp2.property_value as FacilityName, "
			        + " pid1.identifier as `PatientUniqueID`, "
			        + " pid2.identifier as  `PatientHospitalNo`, "
			        + " pid3.identifier as  `ANCNoIdentifier`, "
			        + " gettextvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165567,16,CURRENT_DATE())) as `ANCNoConceptID`, "
			        + " pid4.identifier as  `HTSNo`, "
			        + " person.gender as `Sex`, "
			        + " MAX(IF(obs.concept_id=159599,IF(TIMESTAMPDIFF(YEAR,person.birthdate,obs.value_datetime)>=5,TIMESTAMPDIFF(YEAR,person.birthdate,obs.value_datetime),@ageAtStart:=0),null)) as  `AgeAtStartOfARTYears`, "
			        + " MAX(IF(obs.concept_id=159599,IF(TIMESTAMPDIFF(YEAR,person.birthdate,obs.value_datetime)<5,TIMESTAMPDIFF(MONTH,person.birthdate,obs.value_datetime),null),null)) as `AgeAtStartOfARTMonths`, "
			        + " MAX(IF(obs.concept_id=160540,cn1.name,null)) as CareEntryPoint, "
			        + " MAX(IF(obs.concept_id=160554,obs.value_datetime, NULL)) as  `HIVConfirmedDate`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166369,23,CURRENT_DATE())) as `KPType`, "
			        + " TIMESTAMPDIFF(MONTH,getdatevalueobsid(getmaxconceptobsid(patient.patient_id,159599,CURRENT_DATE())),getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()))) as MonthsOnART, "
			        + " MAX(IF(obs.concept_id=160534,DATE_FORMAT(obs.value_datetime,'%d-%b-%Y'),null)) as DateTransferredIn, "
			        + " MAX(IF(obs.concept_id=165242,cn1.name,null)) as TransferInStatus, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsid(patient.patient_id,159599,CURRENT_DATE())),'%d-%b-%Y') as `ARTStartDate`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())),'%d-%b-%Y') as `LastPickupDate`, "
			        + " DATE_FORMAT(getencounterdate(getlastvisitdate(patient.patient_id,CURRENT_DATE())),'%d-%b-%Y') as LastVisitDate, "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),159368,patient.patient_id) as `DaysOfARVRefil`, "
			        + " gettextvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166406,27,CURRENT_DATE())) "
			        + " as `PillBalance`, "
			        + " MAX(IF(obs2.concept_id=165708,cn2.name,null)) as `InitialRegimenLine`, "
			        + " MAX(IF(obs2.concept_id=165708,getcurrentregimen(obs2.value_coded,obs2.encounter_id),null)) as `InitialRegimen`, "
			        + " getnumericvalueobsid(getminconceptobswithformid(patient.patient_id,5497,21,CURRENT_DATE())) as `InitialCD4Count`, "
			        + " DATE_FORMAT(getobsdatetime(getminconceptobswithformid(patient.patient_id,5497,21,CURRENT_DATE())),'%d-%b-%Y') as `InitialCD4CountDate`, "
			        + " getnumericvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,5497,21,CURRENT_DATE())) as `CurrentCD4Count`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,5497,21,CURRENT_DATE())),'%d-%b-%Y') as `CurrentCD4CountDate`, "
			        + " DATE_FORMAT(getencounterdate(getlastencounter(patient.patient_id,69,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `LastEACDate`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165708,27,CURRENT_DATE())) as `CurrentRegimenLine`, "
			        + " getcurrentregimen(getcodedintvalueobs(getmaxconceptobsidwithformid(patient.patient_id,165708,27,CURRENT_DATE())),getencounterid(getmaxconceptobsidwithformid(patient.patient_id,165708,27,CURRENT_DATE()))) as `CurrentRegimen`, "
			        + " IF(person.gender='F',getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),null) as `PregnancyStatus`, "
			        + " IF(person.gender='F',DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),'%d-%b-%Y'),null) as `PregnancyStatusDate`, "
			        + " IF(person.gender='F', DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),5596)),'%d-%b-%Y'), null) as `EDD`, "
			        + " IF(person.gender='F',DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),'%d-%b-%Y'),null) as `LastDeliveryDate`, "
			        + " IF(person.gender='F', DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),1427)),'%d-%b-%Y'), null) as `LMP`, "
			        + " IF(person.gender='F', getnumericvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,165050,14,CURRENT_DATE())),1438)), null) as `GestationAgeWeeks`, "
			        + " getnumericvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())) as `CurrentViralLoad(c/ml)`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),'%d-%b-%Y') as `ViralLoadEncounterDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),159951)),'%d-%b-%Y') as `ViralLoadSampleCollectionDate`, "
			        + " DATE_FORMAT(getreporteddate(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),165414,patient.patient_id),'%d-%b-%Y')  "
			        + " as `ViralLoadReportedDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),166423)),'%d-%b-%Y') as `ResultDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),166424)),'%d-%b-%Y') as `AssayDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),166425)),'%d-%b-%Y') as `ApprovalDate`, "
			        + " getcodedvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,856,21,CURRENT_DATE())),164980)) as `ViralLoadIndication`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165470,13,CURRENT_DATE())) as PatientOutcome, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,165470,13,CURRENT_DATE())),'%d-%b-%Y') as PatientOutcomeDate, "
			        + " IFNULL (getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165470,13,CURRENT_DATE())),getoutcome( "
			        + " getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())), "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),159368,patient.patient_id) , "
			        + " 28, "
			        + " IF(CURRENT_DATE() IS NULL or CURRENT_DATE() = '', CURDATE(),CURRENT_DATE()) "
			        + " ))  as `CurrentARTStatus`, "
			        + " getcodedvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())),166148)) as `DispensingModality`, "
			        + " getcodedvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())),166276)) as `FacilityDispensingModality`, "
			        + " getcodedvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())),166363)) as `DDDDispensingModality`, "
			        + " getcodedvalueobsid(getobswithencounterid(getencounterid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())),166278)) as `MMDType`, "
			        + " MAX(IF(obs.concept_id=165775,DATE_FORMAT(obs.value_datetime,'%d-%b-%Y'),null)) as `DateReturnedToCare`, "
			        + " MAX(IF(obs.concept_id=165469,DATE_FORMAT(obs.value_datetime,'%d-%b-%Y'),null)) as `DateOfTermination`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,5096,27,CURRENT_DATE())),'%d-%b-%Y') as `PharmacyNextAppointment`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,5096,14,CURRENT_DATE())),'%d-%b-%Y') as `ClinicalNextAppointment`, "
			        + " IF(TIMESTAMPDIFF(YEAR,person.birthdate,curdate())>=5,TIMESTAMPDIFF(YEAR,person.birthdate,curdate()),null) as `CurrentAgeYears`, "
			        + " IF(TIMESTAMPDIFF(YEAR,person.birthdate,curdate())<5,TIMESTAMPDIFF(MONTH,person.birthdate,curdate()),null) as `CurrentAgeMonths`, "
			        + " DATE_FORMAT(person.birthdate,'%d-%b-%Y') as `DateOfBirth`, "
			        + " IF(person.dead=1,\"Dead\",\"\") as MarkAsDeseased, "
			        + " IF(person.dead=1,person.death_date,\"\") as MarkAsDeseasedDeathDate, "
			        + " CONCAT(\"+234-\",TRIM(LEADING '0' FROM psn_atr.value)) AS RegistrationPhoneNo, "
			        + " MAX(IF(obs.concept_id=159635,CONCAT(\"+234-\",TRIM(LEADING '0' FROM obs.value_text)),null)) as `NextofKinPhoneNo`, "
			        + " MAX(IF(obs.concept_id=160642,CONCAT(\"+234-\",TRIM(LEADING '0' FROM obs.value_text)),null)) as  "
			        + " `TreatmentSupporterPhoneNo`, "
			        + " IF(biometrictable.patient_Id IS NOT NULL,'Yes','No') as BiometricCaptured, "
			        + " IF(biometrictable.patient_Id IS NOT NULL,DATE_FORMAT(biometrictable.date_created,'%d-%b-%Y'),\"\") as BiometricCaptureDate, "
			        + " IF(biometrictable.patient_Id IS NOT NULL,IF(invalidprint.patient_Id IS NOT NULL,'No','Yes'),\"\") as ValidCapture, "
			        + " getnumericvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,5089,14,CURRENT_DATE())) as `CurrentWeight(Kg)`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,5089,14,CURRENT_DATE())),'%d-%b-%Y') as `CurrentWeightDate`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,1659,14,CURRENT_DATE())) as `TBStatus`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,1659,14,CURRENT_DATE())),'%d-%b-%Y') as `TBStatusDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,164852,56,CURRENT_DATE())),'%d-%b-%Y') "
			        + " as `BaselineINHStartDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166096,56,CURRENT_DATE())),'%d-%b-%Y') "
			        + " as `BaselineINHStopDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165994,53,CURRENT_DATE())),'%d-%b-%Y') "
			        + " as `CurrentINHStartDate`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166007,53,CURRENT_DATE())) as `CurrentINHOutcome`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166008,53,CURRENT_DATE())),'%d-%b-%Y') "
			        + " as `CurrentINHOutcomeDate`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformidvaluecoded(patient.patient_id,165727,27,1679,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `LastINHDispensedDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsid(patient.patient_id,1113,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `BaselineTBTreatmentStartDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsid(patient.patient_id,159431,CURRENT_DATE())),'%d-%b-%Y') "
			        + " as `BaselineTBTreatmentStopDate`, "
			        + " DATE_FORMAT(getencounterdate(getlastencounter(patient.patient_id,67,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `LastViralLoadSampleCollectionFormDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,159951,21,CURRENT_DATE())),'%d-%b-%Y') as `LastSampleTakenDate`,  "
			        + " DATE_FORMAT(getencounterdate(getlastencounter(patient.patient_id,73,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `OTZEnrollmentDate`, "
			        + " DATE_FORMAT(getdatevalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166008,73,CURRENT_DATE())),'%d-%b-%Y')  "
			        + " as `OTZOutcomeDate`, "
			        + " DATE_FORMAT(pprg.date_enrolled,'%d-%b-%Y') as EnrollmentDate, "
			        + " MAX(IF(obs2.concept_id in(164506,164507), cn2.name,null)) as `InitialFirstLineRegimen`, "
			        + " MAX(IF(obs2.concept_id in(164506,164507), DATE_FORMAT(obs2.obs_datetime,'%d-%b-%Y'),null)) as `InitialFirstLineRegimenDate`, "
			        + " MAX(IF(obs2.concept_id in(164513,164514), cn2.name,null)) as `InitialSecondLineRegimen`, "
			        + " MAX(IF(obs2.concept_id in(164513,164514), DATE_FORMAT(obs2.obs_datetime,'%d-%b-%Y'),null)) as `InitialSecondLineRegimenDate`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH)))),'%d-%b-%Y') as  "
			        + " `LastPickupDatePreviousQuarter`, "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH))),159368,patient.patient_id) as  "
			        + " `DrugDurationPreviousQuarter`, "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165470,13,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH)))) as  "
			        + " `PatientOutcomePreviousQuarter`, "
			        + " DATE_FORMAT(getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,165470,13,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH)))),'%d-%b-%Y') as  "
			        + " `PatientOutcomeDatePreviousQuarter`, "
			        + " IFNULL( "
			        + " getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165470,13,DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH))), "
			        + " getoutcome( "
			        + " getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH)))), "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH))),159368,patient.patient_id) , "
			        + " 28, "
			        + " IF(CURRENT_DATE() IS NULL or CURRENT_DATE() = '', CURDATE(),getendofquarter(DATE_SUB(CURRENT_DATE(),INTERVAL 3 MONTH))) "
			        + " ) "
			        + " )  as `ARTStatusPreviousQuarter`, "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),160856,patient.patient_id) as `QuantityOfARVDispensedLastVisit`, "
			        + " getcodedvalueobsid(getconceptvalobsid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),165723,patient.patient_id)) as `FrequencyOfARVDispensedLastVisit`, "
			        + " IFNULL (getcodedvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,165470,13,CURRENT_DATE())),getoutcomewithpillbalanceandfrequency( "
			        + " getobsdatetime(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE())), "
			        + " getconceptval(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),159368,patient.patient_id) , "
			        + " 28, "
			        + " IFNULL(gettextvalueobsid(getmaxconceptobsidwithformid(patient.patient_id,166406,27,CURRENT_DATE())),0), "
			        + " getconceptvalobsid(getmaxconceptobsidwithformid(patient.patient_id,162240,27,CURRENT_DATE()),165723,patient.patient_id), "
			        + " IF(CURRENT_DATE() IS NULL or CURRENT_DATE() = '', CURDATE(),CURRENT_DATE()) "
			        + " ))  as `CurrentARTStatusWithPillBalance`, "
			        + " DATE_FORMAT(bvinfo.RecaptureDate,'%d-%b-%Y') as RecaptureDate, "
			        + " bvinfo.recapture_count as RecaptureCount "
			        + "  "
			        + " from patient "
			        + " INNER JOIN person on(person.person_id=patient.patient_id and patient.voided=0) "
			        + " LEFT JOIN patient_identifier pid1 on(pid1.patient_id=patient.patient_id and patient.voided=0 and pid1.identifier_type=4 and pid1.voided=0) "
			        + " LEFT JOIN patient_identifier pid3 on(pid3.patient_id=patient.patient_id and patient.voided=0 and pid3.identifier_type=6 and pid3.voided=0) "
			        + " LEFT JOIN patient_identifier pid4 on(pid4.patient_id=patient.patient_id and patient.voided=0 and pid4.identifier_type=8 and pid4.voided=0) "
			        + " LEFT JOIN person_attribute psn_atr ON (person.person_id=psn_atr.person_id and psn_atr.person_attribute_type_id=8)  "
			        + " LEFT JOIN patient_program pprg on(pprg.patient_id=patient.patient_id and pprg.voided=0 and patient.voided=0 and pprg.program_id=1) "
			        + " LEFT JOIN patient_identifier pid2 on(pid2.patient_id=patient.patient_id and patient.voided=0 and pid2.identifier_type=5 and pid2.voided=0) "
			        + " LEFT JOIN "
			        + " (select  "
			        + " obs.person_id, "
			        + " obs.concept_id, "
			        + " MAX(obs.obs_datetime) as last_date,  "
			        + " MIN(obs.obs_datetime) as first_date "
			        + " from obs INNER JOIN encounter on(encounter.encounter_id=obs.encounter_id) where encounter.form_id!=48 and obs.voided=0 and obs.obs_datetime<=CURRENT_DATE() and concept_id in(159599,165708,159368,164506,164513,164507,164514,165702,165703,165050, "
			        + " 856,164980,165470,159635,5089,165988,1659,164852,166096,1113,159431,162240,165242,165724,166156,166158,165727,164982,165414,5596,165775,160540,165469,5096,1427,160642,160534,160554) GROUP BY obs.person_id, obs.concept_id) as sinner on (sinner.person_id=patient.patient_id and patient.voided=0) "
			        + " INNER JOIN obs on(obs.person_id=patient.patient_id and obs.concept_id=sinner.concept_id and obs.obs_datetime=sinner.last_date and obs.voided=0 and obs.obs_datetime<=CURRENT_DATE()) "
			        + " INNER JOIN obs obs2 on(obs2.person_id=patient.patient_id and obs2.concept_id=sinner.concept_id and obs2.obs_datetime=sinner.first_date and obs2.voided=0 and obs2.obs_datetime<=CURRENT_DATE()) "
			        + " LEFT join encounter enc on(enc.encounter_id=obs.encounter_id and enc.voided=0 and obs.voided=0) "
			        + " left join concept_name cn1 on(obs.value_coded=cn1.concept_id and cn1.locale='en' and cn1.locale_preferred=1) "
			        + " left join concept_name cn2 on(obs2.value_coded=cn2.concept_id and cn2.locale='en' and cn2.locale_preferred=1) "
			        + " LEFT JOIN ( "
			        + " select  "
			        + " DISTINCT biometricinfo.patient_Id,biometricinfo.date_created "
			        + " from  "
			        + " biometricinfo GROUP BY biometricinfo.patient_Id "
			        + " ) as biometrictable  "
			        + " on(patient.patient_id=biometrictable.patient_Id and patient.voided=0) "
			        + " LEFT JOIN ( "
			        + " select  "
			        + " DISTINCT biometricinfo.patient_Id "
			        + " from  "
			        + " biometricinfo where template not like 'Rk1S%' or CONVERT(new_template USING utf8) NOT LIKE 'Rk1S%' "
			        + " ) as invalidprint  "
			        + " on(patient.patient_id=invalidprint.patient_Id and patient.voided=0) "
			        + " LEFT JOIN global_property gp1 on(gp1.property='facility_datim_code') "
			        + " LEFT JOIN global_property gp2 on(gp2.property='Facility_Name') "
			        + " LEFT JOIN nigeria_datimcode_mapping on(gp1.property_value=nigeria_datimcode_mapping.datim_code) "
			        + " LEFT JOIN ( "
			        + " select  "
			        + " encounter.patient_id, "
			        + " MAX(encounter.encounter_datetime) as last_visit_date "
			        + " from encounter "
			        + " where encounter.voided=0 and encounter.form_id!=13 and encounter.encounter_datetime<=CURRENT_DATE() GROUP BY  encounter.patient_id "
			        + " ) as enc_last on(enc_last.patient_id=patient.patient_id and patient.voided=0) "
			        + " LEFT JOIN (SELECT patient_Id, MAX(date_created) AS RecaptureDate, recapture_count, count(fingerPosition) as NumberOfFingers, MIN(imageQuality) as LowestFPQuality, CEILING(AVG(imageQuality)) as AverageFPQuality, COUNT(IF(imageQuality<80, 1, NULL)) as FPLowQuality, COUNT(IF(imageQuality<80, NULL, 1)) as FPHighQuality "
			        + " FROM biometricverificationinfo  "
			        + " GROUP BY patient_Id) as bvinfo on(patient.patient_id=bvinfo.patient_Id) "
			        + " WHERE patient.voided=0 and pid1.identifier IS NOT NULL " + " GROUP BY patient.patient_id ; ";
			
			////////////////////////////////////here we go
			/*
			try {
				// Create a PreparedStatement with the query string
				//PreparedStatement ps = con.prepareStatement(query);
				//PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
				//    ResultSet.CONCUR_READ_ONLY);
				PreparedStatement ps = con.prepareStatement(query);
				// If the creation of the PreparedStatement is successful, the query string has valid syntax
				System.out.println("Query string is valid: " + query);
			}
			catch (SQLException e) {
				// If an exception is thrown, there is a syntax error in the query string
				System.out.println("Syntax error in query string: " + query);
				e.printStackTrace();
			}
			            */
			////////////////////////////////////here we go
			
			int i = 1;
			//DateTime now = new DateTime(new Date());
			//String nowString = now.toString("yyyy'-'MM'-'dd' 'HH':'mm");
			stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//stmt.setInt(i++, patientId);
			//stmt.setFetchSize(200);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, String> tempMap = new HashMap();
				tempMap.put("State", rs.getString("State"));
				tempMap.put("LGA", rs.getString("LGA"));
				tempMap.put("DatimCode", rs.getString("DatimCode"));
				tempMap.put("FacilityName", rs.getString("FacilityName"));
				tempMap.put("PatientUniqueID", rs.getString("PatientUniqueID"));
				tempMap.put("PatientHospitalNo", rs.getString("PatientHospitalNo"));
				tempMap.put("ANCNoIdentifier", rs.getString("ANCNoIdentifier"));
				tempMap.put("ANCNoConceptID", rs.getString("ANCNoConceptID"));
				tempMap.put("HTSNo", rs.getString("HTSNo"));
				tempMap.put("Sex", rs.getString("Sex"));
				tempMap.put("AgeAtStartOfARTYears", rs.getString("AgeAtStartOfARTYears"));
				tempMap.put("AgeAtStartOfARTMonths", rs.getString("AgeAtStartOfARTMonths"));
				tempMap.put("CareEntryPoint", rs.getString("CareEntryPoint"));
				tempMap.put("HIVConfirmedDate", rs.getString("HIVConfirmedDate"));
				tempMap.put("KPType", rs.getString("KPType"));
				tempMap.put("MonthsOnART", rs.getString("MonthsOnART"));
				tempMap.put("DateTransferredIn", rs.getString("DateTransferredIn"));
				tempMap.put("TransferInStatus", rs.getString("TransferInStatus"));
				tempMap.put("ARTStartDate", rs.getString("ARTStartDate"));
				tempMap.put("LastPickupDate", rs.getString("LastPickupDate"));
				tempMap.put("LastVisitDate", rs.getString("LastVisitDate"));
				
				tempMap.put("DaysOfARVRefil", rs.getString("DaysOfARVRefil"));
				tempMap.put("PillBalance", rs.getString("PillBalance"));
				tempMap.put("InitialRegimenLine", rs.getString("InitialRegimenLine"));
				tempMap.put("InitialRegimen", rs.getString("InitialRegimen"));
				tempMap.put("InitialCD4Count", rs.getString("InitialCD4Count"));
				tempMap.put("InitialCD4CountDate", rs.getString("InitialCD4CountDate"));
				tempMap.put("CurrentCD4Count", rs.getString("CurrentCD4Count"));
				tempMap.put("CurrentCD4CountDate", rs.getString("CurrentCD4CountDate"));
				tempMap.put("LastEACDate", rs.getString("LastEACDate"));
				tempMap.put("CurrentRegimenLine", rs.getString("CurrentRegimenLine"));
				tempMap.put("CurrentRegimen", rs.getString("CurrentRegimen"));
				tempMap.put("PregnancyStatus", rs.getString("PregnancyStatus"));
				tempMap.put("PregnancyStatusDate", rs.getString("PregnancyStatusDate"));
				
				tempMap.put("EDD", rs.getString("EDD"));
				tempMap.put("LastDeliveryDate", rs.getString("LastDeliveryDate"));
				tempMap.put("LMP", rs.getString("LMP"));
				tempMap.put("GestationAgeWeeks", rs.getString("GestationAgeWeeks"));
				tempMap.put("CurrentViralLoad(c/ml)", rs.getString("CurrentViralLoad(c/ml)"));
				tempMap.put("ViralLoadEncounterDate", rs.getString("ViralLoadEncounterDate"));
				tempMap.put("ViralLoadSampleCollectionDate", rs.getString("ViralLoadSampleCollectionDate"));
				tempMap.put("ViralLoadReportedDate", rs.getString("ViralLoadReportedDate"));
				tempMap.put("ResultDate", rs.getString("ResultDate"));
				tempMap.put("AssayDate", rs.getString("AssayDate"));
				tempMap.put("ApprovalDate", rs.getString("ApprovalDate"));
				tempMap.put("ViralLoadIndication", rs.getString("ViralLoadIndication"));
				tempMap.put("PatientOutcome", rs.getString("PatientOutcome"));
				tempMap.put("PatientOutcomeDate", rs.getString("PatientOutcomeDate"));
				tempMap.put("CurrentARTStatus", rs.getString("CurrentARTStatus"));
				
				tempMap.put("DispensingModality", rs.getString("DispensingModality"));
				tempMap.put("FacilityDispensingModality", rs.getString("FacilityDispensingModality"));
				tempMap.put("DDDDispensingModality", rs.getString("DDDDispensingModality"));
				tempMap.put("MMDType", rs.getString("MMDType"));
				tempMap.put("DateReturnedToCare", rs.getString("DateReturnedToCare"));
				tempMap.put("DateOfTermination", rs.getString("DateOfTermination"));
				tempMap.put("PharmacyNextAppointment", rs.getString("PharmacyNextAppointment"));
				tempMap.put("ClinicalNextAppointment", rs.getString("ClinicalNextAppointment"));
				tempMap.put("CurrentAgeYears", rs.getString("CurrentAgeYears"));
				tempMap.put("CurrentAgeMonths", rs.getString("CurrentAgeMonths"));
				tempMap.put("DateOfBirth", rs.getString("DateOfBirth"));
				tempMap.put("MarkAsDeseased", rs.getString("MarkAsDeseased"));
				tempMap.put("MarkAsDeseasedDeathDate", rs.getString("MarkAsDeseasedDeathDate"));
				tempMap.put("RegistrationPhoneNo", rs.getString("RegistrationPhoneNo"));
				tempMap.put("NextofKinPhoneNo", rs.getString("NextofKinPhoneNo"));
				tempMap.put("TreatmentSupporterPhoneNo", rs.getString("TreatmentSupporterPhoneNo"));
				
				tempMap.put("BiometricCaptured", rs.getString("BiometricCaptured"));
				tempMap.put("BiometricCaptureDate", rs.getString("BiometricCaptureDate"));
				tempMap.put("ValidCapture", rs.getString("ValidCapture"));
				tempMap.put("CurrentWeight(Kg)", rs.getString("CurrentWeight(Kg)"));
				tempMap.put("CurrentWeightDate", rs.getString("CurrentWeightDate"));
				tempMap.put("TBStatus", rs.getString("TBStatus"));
				tempMap.put("TBStatusDate", rs.getString("TBStatusDate"));
				tempMap.put("BaselineINHStartDate", rs.getString("BaselineINHStartDate"));
				tempMap.put("BaselineINHStopDate", rs.getString("BaselineINHStopDate"));
				tempMap.put("CurrentINHStartDate", rs.getString("CurrentINHStartDate"));
				tempMap.put("CurrentINHOutcome", rs.getString("CurrentINHOutcome"));
				tempMap.put("CurrentINHOutcomeDate", rs.getString("CurrentINHOutcomeDate"));
				tempMap.put("LastINHDispensedDate", rs.getString("LastINHDispensedDate"));
				tempMap.put("BaselineTBTreatmentStartDate", rs.getString("BaselineTBTreatmentStartDate"));
				tempMap.put("BaselineTBTreatmentStopDate", rs.getString("BaselineTBTreatmentStopDate"));
				tempMap.put("LastViralLoadSampleCollectionFormDate", rs.getString("LastViralLoadSampleCollectionFormDate"));
				tempMap.put("LastSampleTakenDate", rs.getString("LastSampleTakenDate"));
				
				tempMap.put("OTZEnrollmentDate", rs.getString("OTZEnrollmentDate"));
				tempMap.put("OTZOutcomeDate", rs.getString("OTZOutcomeDate"));
				tempMap.put("EnrollmentDate", rs.getString("EnrollmentDate"));
				tempMap.put("InitialFirstLineRegimen", rs.getString("InitialFirstLineRegimen"));
				tempMap.put("InitialFirstLineRegimenDate", rs.getString("InitialFirstLineRegimenDate"));
				tempMap.put("InitialSecondLineRegimen", rs.getString("InitialSecondLineRegimen"));
				tempMap.put("InitialSecondLineRegimenDate", rs.getString("InitialSecondLineRegimenDate"));
				tempMap.put("LastPickupDatePreviousQuarter", rs.getString("LastPickupDatePreviousQuarter"));
				tempMap.put("DrugDurationPreviousQuarter", rs.getString("DrugDurationPreviousQuarter"));
				tempMap.put("PatientOutcomePreviousQuarter", rs.getString("PatientOutcomePreviousQuarter"));
				tempMap.put("PatientOutcomeDatePreviousQuarter", rs.getString("PatientOutcomeDatePreviousQuarter"));
				tempMap.put("ARTStatusPreviousQuarter", rs.getString("ARTStatusPreviousQuarter"));
				
				tempMap.put("QuantityOfARVDispensedLastVisit", rs.getString("QuantityOfARVDispensedLastVisit"));
				tempMap.put("FrequencyOfARVDispensedLastVisit", rs.getString("FrequencyOfARVDispensedLastVisit"));
				tempMap.put("CurrentARTStatusWithPillBalance", rs.getString("CurrentARTStatusWithPillBalance"));
				tempMap.put("RecaptureDate", rs.getString("RecaptureDate"));
				tempMap.put("RecaptureCount", rs.getString("RecaptureCount"));
				
				allPatients.add(tempMap);
			}
			//////////////////////////////////here we come again
			
			allPatients.forEach(tempMap -> {
			        System.out.println(tempMap.get("PatientUniqueID"));
			});
			
			//////////////////////////////////here we come again
			return allPatients;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return null;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public int saveARTLL(List<Map<String, String>> allPatientMetas) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO artlinelist (State, LGA, DatimCode, FacilityName, PatientUniqueID, PatientHospitalNo, ANCNoIdentifier, ANCNoConceptID, HTSNo, Sex, AgeAtStartOfARTYears, AgeAtStartOfARTMonths, CareEntryPoint, HIVConfirmedDate, KPType, MonthsOnART, DateTransferredIn, TransferInStatus, ARTStartDate, LastPickupDate, LastVisitDate, DaysOfARVRefil, PillBalance, InitialRegimenLine, InitialRegimen, InitialCD4Count, InitialCD4CountDate, CurrentCD4Count, CurrentCD4CountDate, LastEACDate, CurrentRegimenLine, CurrentRegimen, PregnancyStatus, PregnancyStatusDate, EDD, LastDeliveryDate, LMP, GestationAgeWeeks, CurrentViralLoad_c_ml, ViralLoadEncounterDate, ViralLoadSampleCollectionDate, ViralLoadReportedDate, ResultDate, AssayDate, ApprovalDate, ViralLoadIndication, PatientOutcome, PatientOutcomeDate, CurrentARTStatus, DispensingModality, FacilityDispensingModality, DDDDispensingModality, MMDType, DateReturnedToCare, DateOfTermination, PharmacyNextAppointment, ClinicalNextAppointment, CurrentAgeYears, CurrentAgeMonths, DateOfBirth, MarkAsDeseased, MarkAsDeseasedDeathDate, RegistrationPhoneNo, NextofKinPhoneNo, TreatmentSupporterPhoneNo, BiometricCaptured, BiometricCaptureDate, ValidCapture, CurrentWeight_Kg, CurrentWeightDate, TBStatus, TBStatusDate, BaselineINHStartDate, BaselineINHStopDate, CurrentINHStartDate, CurrentINHOutcome, CurrentINHOutcomeDate, LastINHDispensedDate, BaselineTBTreatmentStartDate, BaselineTBTreatmentStopDate, LastViralLoadSampleCollectionFormDate, LastSampleTakenDate, OTZEnrollmentDate, OTZOutcomeDate, EnrollmentDate, InitialFirstLineRegimen, InitialFirstLineRegimenDate, InitialSecondLineRegimen, InitialSecondLineRegimenDate, LastPickupDatePreviousQuarter, DrugDurationPreviousQuarter, PatientOutcomePreviousQuarter, PatientOutcomeDatePreviousQuarter, ARTStatusPreviousQuarter, QuantityOfARVDispensedLastVisit, FrequencyOfARVDispensedLastVisit, CurrentARTStatusWithPillBalance, RecaptureDate, RecaptureCount)VALUES";
			for (int i = 0; i < allPatientMetas.size(); i++) {
				query += "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),";
			}
			
			query = query.substring(0, query.length() - 1);
			query += " ON DUPLICATE KEY UPDATE State=VALUES(State), LGA=VALUES(LGA), DatimCode=VALUES(DatimCode), FacilityName=VALUES(FacilityName), PatientHospitalNo=VALUES(PatientHospitalNo), ANCNoIdentifier=VALUES(ANCNoIdentifier), ANCNoConceptID=VALUES(ANCNoConceptID), HTSNo=VALUES(HTSNo), Sex=VALUES(Sex), AgeAtStartOfARTYears=VALUES(AgeAtStartOfARTYears), AgeAtStartOfARTMonths=VALUES(AgeAtStartOfARTMonths), CareEntryPoint=VALUES(CareEntryPoint), HIVConfirmedDate=VALUES(HIVConfirmedDate), KPType=VALUES(KPType), MonthsOnART=VALUES(MonthsOnART), DateTransferredIn=VALUES(DateTransferredIn), TransferInStatus=VALUES(TransferInStatus), ARTStartDate=VALUES(ARTStartDate), LastPickupDate=VALUES(LastPickupDate), LastVisitDate=VALUES(LastVisitDate), ";
			
			query += "DaysOfARVRefil=VALUES(DaysOfARVRefil), PillBalance=VALUES(PillBalance), InitialRegimenLine=VALUES(InitialRegimenLine), InitialRegimen=VALUES(InitialRegimen), InitialCD4Count=VALUES(InitialCD4Count), InitialCD4CountDate=VALUES(InitialCD4CountDate), CurrentCD4Count=VALUES(CurrentCD4Count), CurrentCD4CountDate=VALUES(CurrentCD4CountDate), LastEACDate=VALUES(LastEACDate), CurrentRegimenLine=VALUES(CurrentRegimenLine), CurrentRegimen=VALUES(CurrentRegimen), PregnancyStatus=VALUES(PregnancyStatus), PregnancyStatusDate=VALUES(PregnancyStatusDate), ";
			
			query += "EDD=VALUES(EDD), LastDeliveryDate=VALUES(LastDeliveryDate), LMP=VALUES(LMP), GestationAgeWeeks=VALUES(GestationAgeWeeks), CurrentViralLoad_c_ml=VALUES(CurrentViralLoad_c_ml), ViralLoadEncounterDate=VALUES(ViralLoadEncounterDate), ViralLoadSampleCollectionDate=VALUES(ViralLoadSampleCollectionDate), ViralLoadReportedDate=VALUES(ViralLoadReportedDate), ResultDate=VALUES(ResultDate), AssayDate=VALUES(AssayDate), ApprovalDate=VALUES(ApprovalDate), ViralLoadIndication=VALUES(ViralLoadIndication), PatientOutcome=VALUES(PatientOutcome), PatientOutcomeDate=VALUES(PatientOutcomeDate), CurrentARTStatus=VALUES(CurrentARTStatus), ";
			
			query += "DispensingModality=VALUES(DispensingModality), FacilityDispensingModality=VALUES(FacilityDispensingModality), DDDDispensingModality=VALUES(DDDDispensingModality), MMDType=VALUES(MMDType), DateReturnedToCare=VALUES(DateReturnedToCare), DateOfTermination=VALUES(DateOfTermination), PharmacyNextAppointment=VALUES(PharmacyNextAppointment), ClinicalNextAppointment=VALUES(ClinicalNextAppointment), CurrentAgeYears=VALUES(CurrentAgeYears), CurrentAgeMonths=VALUES(CurrentAgeMonths), DateOfBirth=VALUES(DateOfBirth), MarkAsDeseased=VALUES(MarkAsDeseased), MarkAsDeseasedDeathDate=VALUES(MarkAsDeseasedDeathDate), RegistrationPhoneNo=VALUES(RegistrationPhoneNo), NextofKinPhoneNo=VALUES(NextofKinPhoneNo), TreatmentSupporterPhoneNo=VALUES(TreatmentSupporterPhoneNo), ";
			
			query += "BiometricCaptured=VALUES(BiometricCaptured), BiometricCaptureDate=VALUES(BiometricCaptureDate), ValidCapture=VALUES(ValidCapture), CurrentWeight_Kg=VALUES(CurrentWeight_Kg), CurrentWeightDate=VALUES(CurrentWeightDate), TBStatus=VALUES(TBStatus), TBStatusDate=VALUES(TBStatusDate), BaselineINHStartDate=VALUES(BaselineINHStartDate), BaselineINHStopDate=VALUES(BaselineINHStopDate), CurrentINHStartDate=VALUES(CurrentINHStartDate), CurrentINHOutcome=VALUES(CurrentINHOutcome), CurrentINHOutcomeDate=VALUES(CurrentINHOutcomeDate), LastINHDispensedDate=VALUES(LastINHDispensedDate), BaselineTBTreatmentStartDate=VALUES(BaselineTBTreatmentStartDate), BaselineTBTreatmentStopDate=VALUES(BaselineTBTreatmentStopDate), LastViralLoadSampleCollectionFormDate=VALUES(LastViralLoadSampleCollectionFormDate), LastSampleTakenDate=VALUES(LastSampleTakenDate), ";
			
			query += "OTZEnrollmentDate=VALUES(OTZEnrollmentDate), OTZOutcomeDate=VALUES(OTZOutcomeDate), EnrollmentDate=VALUES(EnrollmentDate), InitialFirstLineRegimen=VALUES(InitialFirstLineRegimen), InitialFirstLineRegimenDate=VALUES(InitialFirstLineRegimenDate), InitialSecondLineRegimen=VALUES(InitialSecondLineRegimen), InitialSecondLineRegimenDate=VALUES(InitialSecondLineRegimenDate), LastPickupDatePreviousQuarter=VALUES(LastPickupDatePreviousQuarter), DrugDurationPreviousQuarter=VALUES(DrugDurationPreviousQuarter), PatientOutcomePreviousQuarter=VALUES(PatientOutcomePreviousQuarter), PatientOutcomeDatePreviousQuarter=VALUES(PatientOutcomeDatePreviousQuarter), ARTStatusPreviousQuarter=VALUES(ARTStatusPreviousQuarter), ";
			
			query += "QuantityOfARVDispensedLastVisit=VALUES(QuantityOfARVDispensedLastVisit), FrequencyOfARVDispensedLastVisit=VALUES(FrequencyOfARVDispensedLastVisit), CurrentARTStatusWithPillBalance=VALUES(CurrentARTStatusWithPillBalance), RecaptureDate=VALUES(RecaptureDate), RecaptureCount=VALUES(RecaptureCount) ";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			for (int j = 0; j < allPatientMetas.size(); j++) {
				stmt.setString(i++, allPatientMetas.get(j).get("State"));
				stmt.setString(i++, allPatientMetas.get(j).get("LGA"));
				stmt.setString(i++, allPatientMetas.get(j).get("DatimCode"));
				stmt.setString(i++, allPatientMetas.get(j).get("FacilityName"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientUniqueID"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientHospitalNo"));
				stmt.setString(i++, allPatientMetas.get(j).get("ANCNoIdentifier"));
				stmt.setString(i++, allPatientMetas.get(j).get("ANCNoConceptID"));
				stmt.setString(i++, allPatientMetas.get(j).get("HTSNo"));
				stmt.setString(i++, allPatientMetas.get(j).get("Sex"));
				stmt.setString(i++, allPatientMetas.get(j).get("AgeAtStartOfARTYears"));
				stmt.setString(i++, allPatientMetas.get(j).get("AgeAtStartOfARTMonths"));
				stmt.setString(i++, allPatientMetas.get(j).get("CareEntryPoint"));
				stmt.setString(i++, allPatientMetas.get(j).get("HIVConfirmedDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("KPType"));
				stmt.setString(i++, allPatientMetas.get(j).get("MonthsOnART"));
				stmt.setString(i++, allPatientMetas.get(j).get("DateTransferredIn"));
				stmt.setString(i++, allPatientMetas.get(j).get("TransferInStatus"));
				stmt.setString(i++, allPatientMetas.get(j).get("ARTStartDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastPickupDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastVisitDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("DaysOfARVRefil"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("PillBalance"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialRegimenLine"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialRegimen"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialCD4Count"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialCD4CountDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentCD4Count"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentCD4CountDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastEACDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentRegimenLine"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentRegimen"));
				stmt.setString(i++, allPatientMetas.get(j).get("PregnancyStatus"));
				stmt.setString(i++, allPatientMetas.get(j).get("PregnancyStatusDate"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("EDD"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastDeliveryDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LMP"));
				stmt.setString(i++, allPatientMetas.get(j).get("GestationAgeWeeks"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentViralLoad_c_ml"));
				stmt.setString(i++, allPatientMetas.get(j).get("ViralLoadEncounterDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ViralLoadSampleCollectionDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ViralLoadReportedDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ResultDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("AssayDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ApprovalDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ViralLoadIndication"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientOutcome"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientOutcomeDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentARTStatus"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("DispensingModality"));
				stmt.setString(i++, allPatientMetas.get(j).get("FacilityDispensingModality"));
				stmt.setString(i++, allPatientMetas.get(j).get("DDDDispensingModality"));
				stmt.setString(i++, allPatientMetas.get(j).get("MMDType"));
				stmt.setString(i++, allPatientMetas.get(j).get("DateReturnedToCare"));
				stmt.setString(i++, allPatientMetas.get(j).get("DateOfTermination"));
				stmt.setString(i++, allPatientMetas.get(j).get("PharmacyNextAppointment"));
				stmt.setString(i++, allPatientMetas.get(j).get("ClinicalNextAppointment"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentAgeYears"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentAgeMonths"));
				stmt.setString(i++, allPatientMetas.get(j).get("DateOfBirth"));
				stmt.setString(i++, allPatientMetas.get(j).get("MarkAsDeseased"));
				stmt.setString(i++, allPatientMetas.get(j).get("MarkAsDeseasedDeathDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("RegistrationPhoneNo"));
				stmt.setString(i++, allPatientMetas.get(j).get("NextofKinPhoneNo"));
				stmt.setString(i++, allPatientMetas.get(j).get("TreatmentSupporterPhoneNo"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("BiometricCaptured"));
				stmt.setString(i++, allPatientMetas.get(j).get("BiometricCaptureDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("ValidCapture"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentWeight_Kg"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentWeightDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("TBStatus"));
				stmt.setString(i++, allPatientMetas.get(j).get("TBStatusDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("BaselineINHStartDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("BaselineINHStopDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentINHStartDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentINHOutcome"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentINHOutcomeDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastINHDispensedDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("BaselineTBTreatmentStartDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("BaselineTBTreatmentStopDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastViralLoadSampleCollectionFormDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastSampleTakenDate"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("OTZEnrollmentDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("OTZOutcomeDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("EnrollmentDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialFirstLineRegimen"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialFirstLineRegimenDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialSecondLineRegimen"));
				stmt.setString(i++, allPatientMetas.get(j).get("InitialSecondLineRegimenDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("LastPickupDatePreviousQuarter"));
				stmt.setString(i++, allPatientMetas.get(j).get("DrugDurationPreviousQuarter"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientOutcomePreviousQuarter"));
				stmt.setString(i++, allPatientMetas.get(j).get("PatientOutcomeDatePreviousQuarter"));
				stmt.setString(i++, allPatientMetas.get(j).get("ARTStatusPreviousQuarter"));
				
				stmt.setString(i++, allPatientMetas.get(j).get("QuantityOfARVDispensedLastVisit"));
				stmt.setString(i++, allPatientMetas.get(j).get("FrequencyOfARVDispensedLastVisit"));
				stmt.setString(i++, allPatientMetas.get(j).get("CurrentARTStatusWithPillBalance"));
				stmt.setString(i++, allPatientMetas.get(j).get("RecaptureDate"));
				stmt.setString(i++, allPatientMetas.get(j).get("RecaptureCount"));
				
			}
			//stmt.setFetchSize(200);
			//System.out.println("allPatientMetas hereeeeeeeeeeeeeeeeeeeeeeee: " + allPatientMetas);
			if (allPatientMetas.size() > 0)
				stmt.executeUpdate();
			
			return 1;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public String getGlobalProperty(String property) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "SELECT global_property.property_value FROM global_property WHERE property=?";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			stmt.setString(i++, property);
			rs = stmt.executeQuery();
			rs.next();
			
			return rs.getString("property_value");
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return "";
			
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public void saveGlobalProperty(String property, String propertyValue, String description, String uuid) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			String query = "INSERT INTO global_property (property, property_value, description, uuid)VALUES(?, ?, ?, ?)";
			
			int i = 1;
			stmt = con.prepareStatement(query);
			stmt.setString(i++, property);
			stmt.setString(i++, propertyValue);
			stmt.setString(i++, description);
			stmt.setString(i++, uuid);
			
			stmt.executeUpdate();
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
}
