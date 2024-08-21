/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.api.dao;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.nmrsreports.ARTParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ihvn
 */
@Repository("nmrsreports.ARTParamsDao")
public class ARTParamsDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<ARTParams> getARTParams() {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		List<ARTParams> allPatients = new ArrayList();
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			StringBuilder queryString = new StringBuilder(
			        "SELECT flat_auto_inc, pid, State, LGA, DatimCode, FacilityName, PatientUniqueID, PatientHospitalNo, ANCNoIdentifier, ANCNoConceptID, HTSNo, Sex, AgeAtStartOfARTYears, AgeAtStartOfARTMonths, CareEntryPoint, HIVConfirmedDate, KPType, MonthsOnART, DateTransferredIn, TransferInStatus, ARTStartDate, LastPickupDate, LastVisitDate, DaysOfARVRefil, PillBalance, InitialRegimenLine, InitialRegimen, InitialCD4Count, InitialCD4CountDate, CurrentCD4Count, CurrentCD4CountDate, LastEACDate, CurrentRegimenLine, CurrentRegimen, PregnancyStatus, PregnancyStatusDate, EDD, LastDeliveryDate, LMP, GestationAgeWeeks, CurrentViralLoad_c_ml, ViralLoadEncounterDate, ViralLoadSampleCollectionDate, ViralLoadReportedDate, ResultDate, AssayDate, ApprovalDate, ViralLoadIndication, PatientOutcome, PatientOutcomeDate, CurrentARTStatus, DispensingModality, FacilityDispensingModality, DDDDispensingModality, MMDType, DateReturnedToCare, DateOfTermination, PharmacyNextAppointment, ClinicalNextAppointment, CurrentAgeYears, CurrentAgeMonths, DateOfBirth, MarkAsDeseased, MarkAsDeseasedDeathDate, RegistrationPhoneNo, NextofKinPhoneNo, TreatmentSupporterPhoneNo, BiometricCaptured, BiometricCaptureDate, ValidCapture, CurrentWeight_Kg, CurrentWeightDate, TBStatus, TBStatusDate, BaselineINHStartDate, BaselineINHStopDate, CurrentINHStartDate, CurrentINHOutcome, CurrentINHOutcomeDate, LastINHDispensedDate, BaselineTBTreatmentStartDate, BaselineTBTreatmentStopDate, LastViralLoadSampleCollectionFormDate, LastSampleTakenDate, OTZEnrollmentDate, OTZOutcomeDate, EnrollmentDate, InitialFirstLineRegimen, InitialFirstLineRegimenDate, InitialSecondLineRegimen, InitialSecondLineRegimenDate, LastPickupDatePreviousQuarter, DrugDurationPreviousQuarter, PatientOutcomePreviousQuarter, PatientOutcomeDatePreviousQuarter, ARTStatusPreviousQuarter, QuantityOfARVDispensedLastVisit, FrequencyOfARVDispensedLastVisit, CurrentARTStatusWithPillBalance, RecaptureDate, RecaptureCount from artlinelist");
			int i = 1;
			
			stmt = con.prepareStatement(queryString.toString());
			//stmt.setString(i++, startDate);
			//stmt.setString(i++, endDate);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ARTParams tempPatient = new ARTParams();
				tempPatient.setFlat_auto_inc(rs.getInt("flat_auto_inc"));
				tempPatient.setPid(rs.getInt("pid"));
				
				tempPatient.setState(rs.getString("State"));
				tempPatient.setLGA(rs.getString("LGA"));
				tempPatient.setDatimCode(rs.getString("DatimCode"));
				tempPatient.setFacilityName(rs.getString("FacilityName"));
				tempPatient.setPatientUniqueID(rs.getString("PatientUniqueID"));
				tempPatient.setPatientHospitalNo(rs.getString("PatientHospitalNo"));
				tempPatient.setANCNoIdentifier(rs.getString("ANCNoIdentifier"));
				tempPatient.setANCNoConceptID(rs.getString("ANCNoConceptID"));
				tempPatient.setHTSNo(rs.getString("HTSNo"));
				tempPatient.setSex(rs.getString("Sex"));
				tempPatient.setAgeAtStartOfARTYears(rs.getString("AgeAtStartOfARTYears"));
				tempPatient.setAgeAtStartOfARTMonths(rs.getString("AgeAtStartOfARTMonths"));
				tempPatient.setCareEntryPoint(rs.getString("CareEntryPoint"));
				tempPatient.setHIVConfirmedDate(rs.getString("HIVConfirmedDate"));
				tempPatient.setKPType(rs.getString("KPType"));
				tempPatient.setMonthsOnART(rs.getString("MonthsOnART"));
				tempPatient.setDateTransferredIn(rs.getString("DateTransferredIn"));
				tempPatient.setTransferInStatus(rs.getString("TransferInStatus"));
				tempPatient.setARTStartDate(rs.getString("ARTStartDate"));
				tempPatient.setLastPickupDate(rs.getString("LastPickupDate"));
				tempPatient.setLastVisitDate(rs.getString("LastVisitDate"));
				
				tempPatient.setDaysOfARVRefil(rs.getString("DaysOfARVRefil"));
				tempPatient.setPillBalance(rs.getString("PillBalance"));
				tempPatient.setInitialRegimenLine(rs.getString("InitialRegimenLine"));
				tempPatient.setInitialRegimen(rs.getString("InitialRegimen"));
				tempPatient.setInitialCD4Count(rs.getString("InitialCD4Count"));
				tempPatient.setInitialCD4CountDate(rs.getString("InitialCD4CountDate"));
				tempPatient.setCurrentCD4Count(rs.getString("CurrentCD4Count"));
				tempPatient.setCurrentCD4CountDate(rs.getString("CurrentCD4CountDate"));
				tempPatient.setLastEACDate(rs.getString("LastEACDate"));
				tempPatient.setCurrentRegimenLine(rs.getString("CurrentRegimenLine"));
				tempPatient.setCurrentRegimen(rs.getString("CurrentRegimen"));
				tempPatient.setPregnancyStatus(rs.getString("PregnancyStatus"));
				tempPatient.setPregnancyStatusDate(rs.getString("PregnancyStatusDate"));
				
				tempPatient.setEDD(rs.getString("EDD"));
				tempPatient.setLastDeliveryDate(rs.getString("LastDeliveryDate"));
				tempPatient.setLMP(rs.getString("LMP"));
				tempPatient.setGestationAgeWeeks(rs.getString("GestationAgeWeeks"));
				tempPatient.setCurrentViralLoad_c_ml(rs.getString("CurrentViralLoad_c_ml"));
				tempPatient.setViralLoadEncounterDate(rs.getString("ViralLoadEncounterDate"));
				tempPatient.setViralLoadSampleCollectionDate(rs.getString("ViralLoadSampleCollectionDate"));
				tempPatient.setViralLoadReportedDate(rs.getString("ViralLoadReportedDate"));
				tempPatient.setResultDate(rs.getString("ResultDate"));
				tempPatient.setAssayDate(rs.getString("AssayDate"));
				tempPatient.setApprovalDate(rs.getString("ApprovalDate"));
				tempPatient.setViralLoadIndication(rs.getString("ViralLoadIndication"));
				tempPatient.setPatientOutcome(rs.getString("PatientOutcome"));
				tempPatient.setPatientOutcomeDate(rs.getString("PatientOutcomeDate"));
				tempPatient.setCurrentARTStatus(rs.getString("CurrentARTStatus"));
				
				tempPatient.setDispensingModality(rs.getString("DispensingModality"));
				tempPatient.setFacilityDispensingModality(rs.getString("FacilityDispensingModality"));
				tempPatient.setDDDDispensingModality(rs.getString("DDDDispensingModality"));
				tempPatient.setMMDType(rs.getString("MMDType"));
				tempPatient.setDateReturnedToCare(rs.getString("DateReturnedToCare"));
				tempPatient.setDateOfTermination(rs.getString("DateOfTermination"));
				tempPatient.setPharmacyNextAppointment(rs.getString("PharmacyNextAppointment"));
				tempPatient.setClinicalNextAppointment(rs.getString("ClinicalNextAppointment"));
				tempPatient.setCurrentAgeYears(rs.getString("CurrentAgeYears"));
				tempPatient.setCurrentAgeMonths(rs.getString("CurrentAgeMonths"));
				tempPatient.setDateOfBirth(rs.getString("DateOfBirth"));
				tempPatient.setMarkAsDeseased(rs.getString("MarkAsDeseased"));
				tempPatient.setMarkAsDeseasedDeathDate(rs.getString("MarkAsDeseasedDeathDate"));
				tempPatient.setRegistrationPhoneNo(rs.getString("RegistrationPhoneNo"));
				tempPatient.setNextofKinPhoneNo(rs.getString("NextofKinPhoneNo"));
				tempPatient.setTreatmentSupporterPhoneNo(rs.getString("TreatmentSupporterPhoneNo"));
				
				tempPatient.setBiometricCaptured(rs.getString("BiometricCaptured"));
				tempPatient.setBiometricCaptureDate(rs.getString("BiometricCaptureDate"));
				tempPatient.setValidCapture(rs.getString("ValidCapture"));
				tempPatient.setCurrentWeight_Kg(rs.getString("CurrentWeight_Kg"));
				tempPatient.setCurrentWeightDate(rs.getString("CurrentWeightDate"));
				tempPatient.setTBStatus(rs.getString("TBStatus"));
				tempPatient.setTBStatusDate(rs.getString("TBStatusDate"));
				tempPatient.setBaselineINHStartDate(rs.getString("BaselineINHStartDate"));
				tempPatient.setBaselineINHStopDate(rs.getString("BaselineINHStopDate"));
				tempPatient.setCurrentINHStartDate(rs.getString("CurrentINHStartDate"));
				tempPatient.setCurrentINHOutcome(rs.getString("CurrentINHOutcome"));
				tempPatient.setCurrentINHOutcomeDate(rs.getString("CurrentINHOutcomeDate"));
				tempPatient.setLastINHDispensedDate(rs.getString("LastINHDispensedDate"));
				tempPatient.setBaselineTBTreatmentStartDate(rs.getString("BaselineTBTreatmentStartDate"));
				tempPatient.setBaselineTBTreatmentStopDate(rs.getString("BaselineTBTreatmentStopDate"));
				tempPatient.setLastViralLoadSampleCollectionFormDate(rs.getString("LastViralLoadSampleCollectionFormDate"));
				tempPatient.setLastSampleTakenDate(rs.getString("LastSampleTakenDate"));
				
				tempPatient.setOTZEnrollmentDate(rs.getString("OTZEnrollmentDate"));
				tempPatient.setOTZOutcomeDate(rs.getString("OTZOutcomeDate"));
				tempPatient.setEnrollmentDate(rs.getString("EnrollmentDate"));
				tempPatient.setInitialFirstLineRegimen(rs.getString("InitialFirstLineRegimen"));
				tempPatient.setInitialFirstLineRegimenDate(rs.getString("InitialFirstLineRegimenDate"));
				tempPatient.setInitialSecondLineRegimen(rs.getString("InitialSecondLineRegimen"));
				tempPatient.setInitialSecondLineRegimenDate(rs.getString("InitialSecondLineRegimenDate"));
				tempPatient.setLastPickupDatePreviousQuarter(rs.getString("LastPickupDatePreviousQuarter"));
				tempPatient.setDrugDurationPreviousQuarter(rs.getString("DrugDurationPreviousQuarter"));
				tempPatient.setPatientOutcomePreviousQuarter(rs.getString("PatientOutcomePreviousQuarter"));
				tempPatient.setPatientOutcomeDatePreviousQuarter(rs.getString("PatientOutcomeDatePreviousQuarter"));
				tempPatient.setARTStatusPreviousQuarter(rs.getString("ARTStatusPreviousQuarter"));
				
				tempPatient.setQuantityOfARVDispensedLastVisit(rs.getString("QuantityOfARVDispensedLastVisit"));
				tempPatient.setFrequencyOfARVDispensedLastVisit(rs.getString("FrequencyOfARVDispensedLastVisit"));
				tempPatient.setCurrentARTStatusWithPillBalance(rs.getString("CurrentARTStatusWithPillBalance"));
				tempPatient.setRecaptureDate(rs.getString("RecaptureDate"));
				tempPatient.setRecaptureCount(rs.getString("RecaptureCount"));
				
				allPatients.add(tempPatient);
				
			}
			return allPatients;
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return new ArrayList();
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	public byte[] generateARTParamsExcel() throws Exception {
		List<ARTParams> artLinelistData = getARTParams();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("ART Patients");
		
		// Create header row
		Row headerRow = sheet.createRow(0);
		String[] columns = { "flat_auto_inc", "pid", "State", "LGA", "DatimCode", "FacilityName", "PatientUniqueID",
		        "PatientHospitalNo", "ANCNoIdentifier", "ANCNoConceptID", "HTSNo", "Sex", "AgeAtStartOfARTYears",
		        "AgeAtStartOfARTMonths", "CareEntryPoint", "HIVConfirmedDate", "KPType", "MonthsOnART", "DateTransferredIn",
		        "TransferInStatus", "ARTStartDate", "LastPickupDate", "LastVisitDate", "DaysOfARVRefil", "PillBalance",
		        "InitialRegimenLine", "InitialRegimen", "InitialCD4Count", "InitialCD4CountDate", "CurrentCD4Count",
		        "CurrentCD4CountDate", "LastEACDate", "CurrentRegimenLine", "CurrentRegimen", "PregnancyStatus",
		        "PregnancyStatusDate", "EDD", "LastDeliveryDate", "LMP", "GestationAgeWeeks", "CurrentViralLoad_c_ml",
		        "ViralLoadEncounterDate", "ViralLoadSampleCollectionDate", "ViralLoadReportedDate", "ResultDate",
		        "AssayDate", "ApprovalDate", "ViralLoadIndication", "PatientOutcome", "PatientOutcomeDate",
		        "CurrentARTStatus", "DispensingModality", "FacilityDispensingModality", "DDDDispensingModality", "MMDType",
		        "DateReturnedToCare", "DateOfTermination", "PharmacyNextAppointment", "ClinicalNextAppointment",
		        "CurrentAgeYears", "CurrentAgeMonths", "DateOfBirth", "MarkAsDeseased", "MarkAsDeseasedDeathDate",
		        "RegistrationPhoneNo", "NextofKinPhoneNo", "TreatmentSupporterPhoneNo", "BiometricCaptured",
		        "BiometricCaptureDate", "ValidCapture", "CurrentWeight_Kg", "CurrentWeightDate", "TBStatus", "TBStatusDate",
		        "BaselineINHStartDate", "BaselineINHStopDate", "CurrentINHStartDate", "CurrentINHOutcome",
		        "CurrentINHOutcomeDate", "LastINHDispensedDate", "BaselineTBTreatmentStartDate",
		        "BaselineTBTreatmentStopDate", "LastViralLoadSampleCollectionFormDate", "LastSampleTakenDate",
		        "OTZEnrollmentDate", "OTZOutcomeDate", "EnrollmentDate", "InitialFirstLineRegimen",
		        "InitialFirstLineRegimenDate", "InitialSecondLineRegimen", "InitialSecondLineRegimenDate",
		        "LastPickupDatePreviousQuarter", "DrugDurationPreviousQuarter", "PatientOutcomePreviousQuarter",
		        "PatientOutcomeDatePreviousQuarter", "ARTStatusPreviousQuarter", "QuantityOfARVDispensedLastVisit",
		        "FrequencyOfARVDispensedLastVisit", "CurrentARTStatusWithPillBalance", "RecaptureDate", "RecaptureCount" };
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
		}
		
		// Create data rows
		int rowNum = 1;
		for (ARTParams param : artLinelistData) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(param.getFlat_auto_inc());
			row.createCell(1).setCellValue(param.getPid());
			row.createCell(2).setCellValue(param.getState());
			row.createCell(3).setCellValue(param.getLGA());
			row.createCell(4).setCellValue(param.getDatimCode());
			row.createCell(5).setCellValue(param.getFacilityName());
			row.createCell(6).setCellValue(param.getPatientUniqueID());
			row.createCell(7).setCellValue(param.getPatientHospitalNo());
			row.createCell(8).setCellValue(param.getANCNoIdentifier());
			row.createCell(9).setCellValue(param.getANCNoConceptID());
			row.createCell(10).setCellValue(param.getHTSNo());
			row.createCell(11).setCellValue(param.getSex());
			row.createCell(12).setCellValue(param.getAgeAtStartOfARTYears());
			row.createCell(13).setCellValue(param.getAgeAtStartOfARTMonths());
			row.createCell(14).setCellValue(param.getCareEntryPoint());
			row.createCell(15).setCellValue(param.getHIVConfirmedDate());
			row.createCell(16).setCellValue(param.getKPType());
			row.createCell(17).setCellValue(param.getMonthsOnART());
			row.createCell(18).setCellValue(param.getDateTransferredIn());
			row.createCell(19).setCellValue(param.getTransferInStatus());
			row.createCell(20).setCellValue(param.getARTStartDate());
			row.createCell(21).setCellValue(param.getLastPickupDate());
			row.createCell(22).setCellValue(param.getLastVisitDate());
			row.createCell(23).setCellValue(param.getDaysOfARVRefil());
			row.createCell(24).setCellValue(param.getPillBalance());
			row.createCell(25).setCellValue(param.getInitialRegimenLine());
			row.createCell(26).setCellValue(param.getInitialRegimen());
			row.createCell(27).setCellValue(param.getInitialCD4Count());
			row.createCell(28).setCellValue(param.getInitialCD4CountDate());
			row.createCell(29).setCellValue(param.getCurrentCD4Count());
			row.createCell(30).setCellValue(param.getCurrentCD4CountDate());
			row.createCell(31).setCellValue(param.getLastEACDate());
			row.createCell(32).setCellValue(param.getCurrentRegimenLine());
			row.createCell(33).setCellValue(param.getCurrentRegimen());
			row.createCell(34).setCellValue(param.getPregnancyStatus());
			row.createCell(35).setCellValue(param.getPregnancyStatusDate());
			row.createCell(36).setCellValue(param.getEDD());
			row.createCell(37).setCellValue(param.getLastDeliveryDate());
			row.createCell(38).setCellValue(param.getLMP());
			row.createCell(39).setCellValue(param.getGestationAgeWeeks());
			row.createCell(40).setCellValue(param.getCurrentViralLoad_c_ml());
			row.createCell(41).setCellValue(param.getViralLoadEncounterDate());
			row.createCell(42).setCellValue(param.getViralLoadSampleCollectionDate());
			row.createCell(43).setCellValue(param.getViralLoadReportedDate());
			row.createCell(44).setCellValue(param.getResultDate());
			row.createCell(45).setCellValue(param.getAssayDate());
			row.createCell(46).setCellValue(param.getApprovalDate());
			row.createCell(47).setCellValue(param.getViralLoadIndication());
			row.createCell(48).setCellValue(param.getPatientOutcome());
			row.createCell(49).setCellValue(param.getPatientOutcomeDate());
			row.createCell(50).setCellValue(param.getCurrentARTStatus());
			row.createCell(51).setCellValue(param.getDispensingModality());
			row.createCell(52).setCellValue(param.getFacilityDispensingModality());
			row.createCell(53).setCellValue(param.getDDDDispensingModality());
			row.createCell(54).setCellValue(param.getMMDType());
			row.createCell(55).setCellValue(param.getDateReturnedToCare());
			row.createCell(56).setCellValue(param.getDateOfTermination());
			row.createCell(57).setCellValue(param.getPharmacyNextAppointment());
			row.createCell(58).setCellValue(param.getClinicalNextAppointment());
			row.createCell(59).setCellValue(param.getCurrentAgeYears());
			row.createCell(60).setCellValue(param.getCurrentAgeMonths());
			row.createCell(61).setCellValue(param.getDateOfBirth());
			row.createCell(62).setCellValue(param.getMarkAsDeseased());
			row.createCell(63).setCellValue(param.getMarkAsDeseasedDeathDate());
			row.createCell(64).setCellValue(param.getRegistrationPhoneNo());
			row.createCell(65).setCellValue(param.getNextofKinPhoneNo());
			row.createCell(66).setCellValue(param.getTreatmentSupporterPhoneNo());
			row.createCell(67).setCellValue(param.getBiometricCaptured());
			row.createCell(68).setCellValue(param.getBiometricCaptureDate());
			row.createCell(69).setCellValue(param.getValidCapture());
			row.createCell(70).setCellValue(param.getCurrentWeight_Kg());
			row.createCell(71).setCellValue(param.getCurrentWeightDate());
			row.createCell(72).setCellValue(param.getTBStatus());
			row.createCell(73).setCellValue(param.getTBStatusDate());
			row.createCell(74).setCellValue(param.getBaselineINHStartDate());
			row.createCell(75).setCellValue(param.getBaselineINHStopDate());
			row.createCell(76).setCellValue(param.getCurrentINHStartDate());
			row.createCell(77).setCellValue(param.getCurrentINHOutcome());
			row.createCell(78).setCellValue(param.getCurrentINHOutcomeDate());
			row.createCell(79).setCellValue(param.getLastINHDispensedDate());
			row.createCell(80).setCellValue(param.getBaselineTBTreatmentStartDate());
			row.createCell(81).setCellValue(param.getBaselineTBTreatmentStopDate());
			row.createCell(82).setCellValue(param.getLastViralLoadSampleCollectionFormDate());
			row.createCell(83).setCellValue(param.getLastSampleTakenDate());
			row.createCell(84).setCellValue(param.getOTZEnrollmentDate());
			row.createCell(85).setCellValue(param.getOTZOutcomeDate());
			row.createCell(86).setCellValue(param.getEnrollmentDate());
			row.createCell(87).setCellValue(param.getInitialFirstLineRegimen());
			row.createCell(88).setCellValue(param.getInitialFirstLineRegimenDate());
			row.createCell(89).setCellValue(param.getInitialSecondLineRegimen());
			row.createCell(90).setCellValue(param.getInitialSecondLineRegimenDate());
			row.createCell(91).setCellValue(param.getLastPickupDatePreviousQuarter());
			row.createCell(92).setCellValue(param.getDrugDurationPreviousQuarter());
			row.createCell(93).setCellValue(param.getPatientOutcomePreviousQuarter());
			row.createCell(94).setCellValue(param.getPatientOutcomeDatePreviousQuarter());
			row.createCell(95).setCellValue(param.getARTStatusPreviousQuarter());
			row.createCell(96).setCellValue(param.getQuantityOfARVDispensedLastVisit());
			row.createCell(97).setCellValue(param.getFrequencyOfARVDispensedLastVisit());
			row.createCell(98).setCellValue(param.getCurrentARTStatusWithPillBalance());
			row.createCell(99).setCellValue(param.getRecaptureDate());
			row.createCell(100).setCellValue(param.getRecaptureCount());
			
			// ... add all other fields ...
		}
		
		// Auto-size columns
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		return outputStream.toByteArray();
	}
	
}
