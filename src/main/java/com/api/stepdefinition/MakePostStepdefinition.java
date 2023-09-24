package com.api.stepdefinition;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.endpoints.PostEndpoint;
import com.api.model.API;
import com.api.model.Createpost;
import com.api.utils.ExcelUtils;
import com.api.utils.JsonReader;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class MakePostStepdefinition {
	static ExtentReports report;
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(MakePostStepdefinition.class);
	private PostEndpoint postEndpoint = new PostEndpoint();
	private API api;

	public MakePostStepdefinition(API api) {
		// this.context = context;
		this.api = api;
	}

	@When("user creates a post")
	public void userCreatesABooking(DataTable dataTable) {
		Map<String, String> postData = dataTable.asMaps().get(0);

		api.setRequest(postEndpoint.requestSetup());
		api.setResponse(postEndpoint.createPost(api.getRequest(),
				new Createpost(postData.get("title"), postData.get("body"), postData.get("userId"))));

		Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		assertNotNull("Post not created", Createpost);
		ExtentCucumberAdapter.addTestStepLog("Newly created booking ID: " + Createpost.getId());
		LOG.info("Newly created booking ID: " + Createpost.getId());

		validateBookingData(new JSONObject(postData), Createpost);
	}

	@Then("user should get the response code {int} for successfull post")
	public void user_should_get_the_response_code_for_successfull_post(Integer status) {
		postEndpoint.verifyResponseStatusValue(api.getResponse(), status.intValue());
	}

	@Then("user should get the response code {int}")
	public void userShpuldGetTheResponseCode(Integer statusCode) {
		postEndpoint.verifyResponseStatusValue(api.getResponse(), statusCode.intValue());
	}

	@Then("user validates the response with JSON schema {string}")
	public void userValidatesResponseWithJSONSchema(String schemaFileName) {

		postEndpoint.verifyResponseWithJSONSchema(api.getResponse(), schemaFileName);

	}

	private void validateBookingData(JSONObject postData, Createpost Createpost) {
		LOG.info(postData);

		assertEquals("title did not match", postData.get("title"), Createpost.getTitle());
		assertEquals("body did not match", postData.get("body"), Createpost.getBody());
		assertEquals("userId did not match", postData.get("userId"), Createpost.getUserId());

	}

	@When("user creates a post with invalid resource")
	public void user_creates_a_post_with_invalid_resource(DataTable dataTable) {
		Map<String, String> postData = dataTable.asMaps().get(0);

		api.setRequest(postEndpoint.requestSetup());
		api.setResponse(postEndpoint.createPost1(api.getRequest(),
				new Createpost(postData.get("title"), postData.get("body"), postData.get("userId"))));

		Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		assertNotNull("Post not created", Createpost);
		ExtentCucumberAdapter.addTestStepLog("Newly created booking ID: " + Createpost.getId());
		LOG.info("Newly created booking ID: " + Createpost.getId());
	}

	@When("user creates a post using data {string} from Excel")
	public void userCreatesABookingUsingDataFromExcel(String dataKey) throws Exception {
		Map<String, String> excelDataMap = ExcelUtils.getData(dataKey);

		api.setRequest(postEndpoint.requestSetup());

		api.setResponse(
				api.getRequest().body(excelDataMap.get("requestBody").toString()).when().post(postEndpoint.getPath()));

		Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		assertNotNull("Post not created", Createpost);
		LOG.info("Newly created booking ID: " + Createpost.getId());

		validateBookingData(new JSONObject(excelDataMap.get("responseBody")), Createpost);
		postEndpoint.session.put("excelDataMap", excelDataMap);
	}

	@Then("user validates the response with JSON schema from Excel")
	public void userValidatesTheResponseWithJSONSchemaFromExcel() {
		api.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
				((Map<String, String>) postEndpoint.session.get("excelDataMap")).get("responseSchema")));
		LOG.info("Successfully Validated schema from Excel");
	}

	@When("user creates a post using data {string} from JSON file {string}")
	public void userCreatesABookingUsingDataFromJSONFile(String dataKey, String JSONFile) {
		
		api.setRequest(postEndpoint.requestSetup());

		api.setResponse(
				api.getRequest().body(JsonReader.getRequestBody(JSONFile, dataKey)).when().post(postEndpoint.getPath()));

		Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		
		
		assertNotNull("Post not created", Createpost);
		LOG.info("Newly created booking ID: " + Createpost.getId());
	}
}
