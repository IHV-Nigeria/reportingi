/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
	
}
