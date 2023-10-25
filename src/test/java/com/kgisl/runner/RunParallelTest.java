package com.kgisl.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src/test/resources/features",
		// features = ".",
		glue = "com.kgisl.stepDefinition",
		tags = "@PolicyCancellation002",
		monochrome = true, 
		plugin = {"pretty", "rerun:rerun.txt", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "html:target/html", "json:target/cucumber.json"}
)

public class RunParallelTest extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}	
}

