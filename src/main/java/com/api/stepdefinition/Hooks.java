package com.api.stepdefinition;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	static ExtentReports report ;
	static ExtentReports test ;
	private static final Logger LOG = LogManager.getLogger(Hooks.class);
	
	@Before
	public void testStart(Scenario scenario) {
		LOG.info("*****************************************************************************************");
		LOG.info("	Scenario: "+scenario.getName());
		LOG.info("*****************************************************************************************");

	}
}
