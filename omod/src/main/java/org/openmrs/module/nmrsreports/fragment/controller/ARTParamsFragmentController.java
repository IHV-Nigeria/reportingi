/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.fragment.controller;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.openmrs.module.nmrsreports.ARTParams;
import org.openmrs.module.nmrsreports.api.dao.ARTParamsDao;

/**
 * @author ihvn
 */
public class ARTParamsFragmentController {
	
	ARTParamsDao ARTParamsDao = new ARTParamsDao();
	
	public String getAllARTParamsData() {
		//DateTime startDateTime = new DateTime(request.getParameter("startDate"));
		//DateTime endDateTime = new DateTime(request.getParameter("endDate"));
		//System.out.println(startDateTime + "in ARTParamsFragmentController");
		
		//String startDate = startDateTime.toString("yyyy'-'MM'-'dd");
		//String endDate = endDateTime.toString("yyyy'-'MM'-'dd");
		
		List<ARTParams> allPatients = ARTParamsDao.getARTParams();
		/*
		for (int i = 0; i < allPatients.size(); i++) {
		
		if (java.util.Objects.equals(allPatients.get(i).getCase_status(), 162743)) {
		
		if (allPatients.get(i).getGender().equalsIgnoreCase("M")
		        || allPatients.get(i).getGender().equalsIgnoreCase("Male")) {
			male1Total++;
		} else if (allPatients.get(i).getGender().equalsIgnoreCase("F")
		        || allPatients.get(i).getGender().equalsIgnoreCase("Female")) {
			female1Total++;
		}
		
		}
		}
		
		Map<String, String> dataMap = new HashMap();
		indic1Total = male1Total + female1Total;
		dataMap.put("male1Total", male1Total + "");
		dataMap.put("female1Total", female1Total + "");
		dataMap.put("indic1Total", indic1Total + "");
		male1Total = 0;
		female1Total = 0;
		indic1Total = 0;
		
		//dataMap.put("totalAdultsTestedPositive",  adultsTestedPositive+"");
		return new JSONObject(dataMap).toString();
		*/
		String json = new Gson().toJson(allPatients);
		return json;
	}
}
