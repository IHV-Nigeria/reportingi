/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nmrsreports.api.dao;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.nmrsreports.Item;
import org.openmrs.module.nmrsreports.models.EncounterListResponse;
import org.openmrs.module.nmrsreports.models.PatientList;
import org.openmrs.module.nmrsreports.models.ReportResponse;
import org.openmrs.module.nmrsreports.models.ReportResponseCustom;
import org.openmrs.module.nmrsreports.scripts.ReportScripts;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository("nmrsreports.NmrsreportsDao")
public class NmrsreportsDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	public List<Module> getModules() throws APIException {
		ArrayList<Module> m = ModuleFactory.getLoadedModules().stream().collect(Collectors.toCollection(ArrayList::new));
		return m;
	}
	
	public List<PatientList> getPatientList() {
		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(ReportScripts.getPatientList());
		List<PatientList> patientLists = sql.setResultTransformer(Transformers.aliasToBean(PatientList.class)).list();
		return patientLists;
	}
	
	public List<ReportResponse> getReports(Date from, Date to, String issueType) {
		List<ReportResponse> patientLists = null;
		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(ReportScripts.loadScript(from, to, issueType));
		
		if (from != null) {
			sql.setTimestamp("fromDate", from);
		}
		if (to != null) {
			sql.setTimestamp("toDate", to);
		}
		
		List<String> scriptWithCustomEncounterIds = Arrays.asList("getPatientNoState", "getPatientNoLGA");
		if (scriptWithCustomEncounterIds.contains(issueType)) {
			patientLists = sql.setResultTransformer(Transformers.aliasToBean(ReportResponseCustom.class)).list();
		} else {
			patientLists = sql.setResultTransformer(Transformers.aliasToBean(ReportResponse.class)).list();
		}
		return patientLists;
	}
	
	public List<EncounterListResponse> getEncounterTypeReports(Date from, Date to, int encounterTypeId, String identifier,
	        String ndrVisitId) {
		List<EncounterListResponse> encounterList = null;
		SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(
		    ReportScripts.getEncounterList(from, to, encounterTypeId, identifier, ndrVisitId));
		if (encounterTypeId > 0)
			sql.setInteger("encounter_type_id", encounterTypeId);
		if (from != null) {
			sql.setTimestamp("fromDate", from);
		}
		if (to != null) {
			sql.setTimestamp("toDate", to);
		}
		if (identifier != null) {
			sql.setParameter("identifier", "%" + identifier.trim() + "%");
		}
		if (ndrVisitId != null) {
			sql.setParameter("ndrVisitId", "%" + ndrVisitId.trim() + "%");
		}
		encounterList = sql.setResultTransformer(Transformers.aliasToBean(EncounterListResponse.class)).list();
		return encounterList;
	}
	
	public String getSqlQuery(String uuId) throws APIException {
		
		ReportDefinition reportDefinition = (ReportDefinition) Context.getService(DataSetDefinitionService.class)
		        .getDefinitionByUuid(uuId);
		DataSetDefinition def = reportDefinition.getDataSetDefinitions().values().stream().findFirst().get()
		        .getParameterizable();
		SqlDataSetDefinition definition = (SqlDataSetDefinition) def;
		return definition.getSqlQuery();
	}
	
}
