package org.openmrs.module.nmrsreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.UserApp;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.serializer.ReportingSerializer;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.ui.framework.resource.ResourceProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsInitializer implements Initializer {
	
	protected static final String providerName = "nmrsreports";
	
	protected static final String reportsPath = "reports/";
	
	protected static final String appsPath = "app/";
	
	protected static final Log log = LogFactory.getLog(ReportsInitializer.class);
	
	@Override
	public void started() {
		ReportDefinitionService reportDefinitionService = Context.getService(ReportDefinitionService.class);
		DataSetDefinitionService dataSetDefinitionService = Context.getService(DataSetDefinitionService.class);
		final ResourceFactory resourceFactory = ResourceFactory.getInstance();
		final ResourceProvider resourceProvider = resourceFactory.getResourceProviders().get(providerName);
		final File reportsDir = resourceProvider.getResource(reportsPath);
		System.out.print(reportsDir);

		final List<String> reportPaths = new ArrayList<String>();
		// Scanning the forms resources folder
		if (reportsDir != null && reportsDir.listFiles() != null) {
			for (File file : reportsDir.listFiles())
				reportPaths.add(reportsPath + file.getName());
		}
		try {
			ReportingSerializer reportingSerializer = new ReportingSerializer();
			if (reportPaths.size() > 0) {
				for (String reportsPath : reportPaths) {
					try {
						String xml = resourceFactory.getResourceAsString(providerName, reportsPath);
						Object object = reportingSerializer.deserialize(xml, Object.class);
						if (object instanceof SqlDataSetDefinition) {
							SqlDataSetDefinition sqlDataSetDefinition = (SqlDataSetDefinition) object;
							if (dataSetDefinitionService.getDefinitionByUuid(sqlDataSetDefinition.getUuid()) == null) {
								sqlDataSetDefinition.setCreator(null);
								sqlDataSetDefinition.setId(null);
								sqlDataSetDefinition.setDateChanged(null);
								sqlDataSetDefinition.setDateCreated(new Date());
								dataSetDefinitionService.saveDefinition(sqlDataSetDefinition);
							} else {
								SqlDataSetDefinition old = (SqlDataSetDefinition) dataSetDefinitionService
								        .getDefinitionByUuid(sqlDataSetDefinition.getUuid());
								old.setSqlQuery(sqlDataSetDefinition.getSqlQuery());
								old.setParameters(sqlDataSetDefinition.getParameters());
								old.setDateChanged(new Date());
								dataSetDefinitionService.saveDefinition(old);
							}
						} else if (object instanceof ReportDefinition) {
							ReportDefinition reportDefinition = (ReportDefinition) object;
							if (reportDefinitionService.getDefinitionByUuid(reportDefinition.getUuid()) == null) {
								reportDefinition.setCreator(null);
								reportDefinition.setId(null);
								reportDefinition.setDateChanged(null);
								reportDefinition.setDateCreated(new Date());
								reportDefinitionService.saveDefinition(reportDefinition);
							} else {
								ReportDefinition old = reportDefinitionService.getDefinitionByUuid(reportDefinition
								        .getUuid());
								old.setDataSetDefinitions(reportDefinition.getDataSetDefinitions());
								old.setParameters(reportDefinition.getParameters());
								old.setDateChanged(new Date());
								reportDefinitionService.saveDefinition(old);
							}
						}
					}
					catch (Exception e) {
						log.error("Could not generate Report from the following resource file: " + reportsPath, e);
					}
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		
		//Update Appdescriptor
		final File appJsonDir = resourceProvider.getResource(appsPath);
		final List<String> appJsonPaths = new ArrayList<>();
		// Scanning the forms resources folder
		if (appJsonDir != null && appJsonDir.listFiles() != null) {
			for (File file : appJsonDir.listFiles())
				appJsonPaths.add(appsPath + file.getName());
		}
		for (String appJsonPath : appJsonPaths) {
			try {
				File file = resourceFactory.getResource(providerName, appJsonPath);
				String json = resourceFactory.getResourceAsString(providerName, appJsonPath);
				String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
				AppFrameworkService appFrameworkService = Context.getService(AppFrameworkService.class);
				UserApp userApp = appFrameworkService.getUserApp(fileNameWithOutExt);
				if (userApp == null) {
					userApp = new UserApp();
					userApp.setAppId(fileNameWithOutExt);
				}
				userApp.setJson(json);
				appFrameworkService.saveUserApp(userApp);
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	@Override
	public void stopped() {
		
	}
}
