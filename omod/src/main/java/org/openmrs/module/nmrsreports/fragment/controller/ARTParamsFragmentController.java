/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.fragment.controller;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.module.nmrsreports.ARTParams;
import org.openmrs.module.nmrsreports.api.dao.ARTParamsDao;

/**
 * @author ihvn
 */
public class ARTParamsFragmentController {
	
	ARTParamsDao ARTParamsDao = new ARTParamsDao();
	
	public String getAllARTParamsData() {
		
		List<ARTParams> allPatients = ARTParamsDao.getARTParams();
		
		String json = new Gson().toJson(allPatients);
		return json;
	}
	
	public void getARTParamsAsExcel(HttpServletResponse response) throws Exception {
		ARTParamsDao artParamsDao = new ARTParamsDao();
		byte[] excelBytes = artParamsDao.generateARTParamsExcel();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=artlinelist.xls");
		response.getOutputStream().write(excelBytes);
	}
	
}
