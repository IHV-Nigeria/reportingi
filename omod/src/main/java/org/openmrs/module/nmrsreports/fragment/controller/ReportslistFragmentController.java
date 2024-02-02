package org.openmrs.module.nmrsreports.fragment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.Module;
import org.openmrs.module.nmrsreports.api.NmrsreportsService;
import org.openmrs.module.nmrsreports.models.PatientList;
import org.openmrs.module.nmrsreports.models.ReportResponse;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.logging.Level;

public class ReportslistFragmentController {
	
	NmrsreportsService nmrsreportingService;
	
	ObjectMapper mapper;
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
		String metadataModuleVersion = null;
		nmrsreportingService = Context.getService(NmrsreportsService.class);
		List<Module> modules = nmrsreportingService.getModules();
		Iterator var6 = modules.iterator();
		List<String> metadataVersion = Arrays.asList("nmrsmetadata", "nmrsmetadata-poc");
		while (var6.hasNext()) {
			Module m = (Module) var6.next();
			if (metadataVersion.contains(m.getModuleId())) {
				metadataModuleVersion = m.getVersion();
			}
		}
		
		model.addAttribute("users", service.getAllUsers());
		model.addAttribute("metadataModuleVersion", metadataModuleVersion);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getPatientsList(@RequestParam(value = "id") String id) {
		ObjectMapper mapper = new ObjectMapper();
		nmrsreportingService = Context.getService(NmrsreportsService.class);
		List<PatientList> patientLists = nmrsreportingService.getPatientList();

		Map<String, Object> patientListMap = new HashMap<>();
		patientListMap.put("totalPatients",patientLists.stream().count());
		patientListMap.put("totalPatientsOnART",patientLists.stream().filter(x->!x.getPepid().isEmpty()).count());
		patientListMap.put("totalHtsPatients", patientLists.stream().filter(x->!x.getHts().isEmpty()).count());
		patientListMap.put("totalRecencyPatients", patientLists.stream().filter(x->!x.getRecencyID().isEmpty()).count());
		patientListMap.put("totalPBS", patientLists.stream().filter(x->!x.getValidCapture().isEmpty()).count());
		String response = Type.EMPTYSTRING;
		mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			if (patientLists.isEmpty()) {
				response = "EMPTY";
			} else {
				response = mapper.writeValueAsString(patientListMap);
			}
		}
		catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "getPatientWithIssues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getPatientWithIssues(@RequestParam("start") int start, @RequestParam("draw") int draw,
	        @RequestParam("length") int pageLength, @RequestParam(value = "from", required = false) Date from,
	        @RequestParam(value = "to", required = false) Date to, @RequestParam("issueType") String issueType) {
		List<ReportResponse> returningData = null;
		JsonObject jsonObject = null;
		
		int recordsTotal = 10;
		int pageLengtheDefault = pageLength;
		int recordsFiltered = 10;
		nmrsreportingService = Context.getService(NmrsreportsService.class);
		List<ReportResponse> patientLists = nmrsreportingService.getReports(from, to, issueType);
		if (patientLists.size() >= 10) {
			returningData = patientLists.subList(start, pageLength + start);
		} else {
			recordsTotal = patientLists.size();
			pageLengtheDefault = pageLength;
			recordsFiltered = patientLists.size();
			returningData = patientLists;
		}
		Map<String, Object> json = new HashMap();
		json.put("draw", String.valueOf(draw++));
		json.put("recordsTotal", patientLists.size());
		json.put("recordsFiltered", patientLists.size());
		json.put("data", returningData);
		return json;
	}
	
	private List<PatientList> getData(int start, int length) {
		nmrsreportingService = Context.getService(NmrsreportsService.class);
		List<PatientList> patientLists = nmrsreportingService.getPatientList();
		return patientLists.subList(start, length + start);
	}
}
