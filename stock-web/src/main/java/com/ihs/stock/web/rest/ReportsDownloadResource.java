package com.ihs.stock.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportsDownloadResource {

	public void generatePDF(HttpServletResponse response, List items, String jrxmlReportName, String downloadReportName,
			HashMap<String, Object> additionalDetails) throws JRException, IOException {
		
		String root = getPath();
		String subReportFileName = root + "/WEB-INF/resources/" + jrxmlReportName;
		
		JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);
		Map<String, Object> parameters = additionalDetails;
		parameters.put("wMark", root + "images");//for Unfepi
		
		JRBeanCollectionDataSource jRBeanCollectionDataSource = new JRBeanCollectionDataSource(items);
		JRDataSource dataSource = jRBeanCollectionDataSource;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperSubReport, parameters, dataSource);
		response.addHeader("Content-disposition", "attachment; filename=" + downloadReportName + ".pdf");
		OutputStream outputStream = response.getOutputStream();
		
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		System.out.println("--");
		
		outputStream.write(baos.toByteArray());
		outputStream.flush();
		outputStream.close();

	}
	public String getPath() throws UnsupportedEncodingException {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		System.out.println(fullPath);
		System.out.println(pathArr[0]);
		fullPath = pathArr[0];
		String reponsePath = "";
		// to read a file from webcontent
		reponsePath = new File(fullPath).getPath() + File.separatorChar;
		System.out.println(reponsePath);
		return reponsePath;
	}



}
