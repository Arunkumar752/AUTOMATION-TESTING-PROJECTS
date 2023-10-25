package com.kgisl.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;

public class ReportUtility {
    
    public static void generateJVMReport(){



          File reportOutputDirectory = new File(System.getProperty("user.dir")+"/CCreports");
           List<String> jsonFiles = new ArrayList<>();
           jsonFiles.add("target/cucumber.json");




           String buildNumber = "1";
           String projectName = "cucumberProject";



          Configuration configuration = new Configuration(reportOutputDirectory, projectName);
   // optional configuration - check javadoc for details
           configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
   // do not make scenario failed when step has status SKIPPED
           configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
           configuration.setBuildNumber(buildNumber);
           configuration.addClassifications("Platform", "Windows");
           configuration.addClassifications("Browser", "Firefox");
           configuration.addClassifications("Branch", "release/1.0");



          ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
           Reportable result = reportBuilder.generateReports();


       }



}