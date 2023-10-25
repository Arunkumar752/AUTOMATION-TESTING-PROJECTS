package com.kgisl.base;

import java.util.ArrayList;
import java.util.List;

import com.kgisl.library.ReportOut;

public class Data_Set {

private String ExceptionReason = null;
	
	
	private String Browser;
	private String OS;
	private String BrowserVersion;
	private String TestcaseID;
	private String reportAppName;
	
	ArrayList<Integer> RowIndices = new ArrayList<Integer>();
	private String ExecutionStTime;
	private String sheet=null;

	
	public List<Integer> getRowIndices() {
		return RowIndices;
	}

	public void setRowIndices(ArrayList<Integer> rowlist) {
		RowIndices = rowlist;
	}
	
	public String getExecutionStartTime() {
		return ExecutionStTime;
	}

	public void setExecutionStartTime(String StartTime) {
		ExecutionStTime = StartTime;
	}
	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheetname) {
		sheet = sheetname;
	}
	
	public String getException() {
		return ExceptionReason;
	}
	public void setException(String exception) {
		ExceptionReason = exception;
	}

	public String getBrowserName() {
		return Browser;
	}

	public void setBrowserName(String browser) {
		Browser = browser;
	}

	public String getOSname() {
		return OS;
	}

	public void setOSname(String oS) {
		OS = oS;
	}

	public String getBrowserVersion() {
		return BrowserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		BrowserVersion = browserVersion;
	}
	
	public String getTestcaseID() {
		return TestcaseID;
	}

	public void setTestcaseID(String testcaseID) {
		TestcaseID = testcaseID;
	}
	
	public String getReportAppName() {
		return reportAppName;
	}

	public void setReportAppName(String reportAppName) {
		this.reportAppName = reportAppName;
	}
	
	public void ResetVariables() {
		
		RowIndices = null;
    	ExecutionStTime=null;
    	
    	ExceptionReason = null;
    	Browser = null;
    	OS = null;
    	BrowserVersion = null;
    	TestcaseID = null;
    	reportAppName = null;
	}

		
}
