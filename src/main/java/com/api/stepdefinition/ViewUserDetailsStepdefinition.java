package com.api.stepdefinition;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.api.endpoints.BaseEndpoints;
import com.api.endpoints.PostEndpoint;
import com.api.endpoints.UserDetailsEndpoint;
import com.api.model.API;
import com.api.model.Comments;
import com.api.model.Createpost;
import com.api.model.Userdetails;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;

public class ViewUserDetailsStepdefinition {
	private TestContext context;
	static ExtentReports report;
	private static final Logger LOG = LogManager.getLogger(ViewUserDetailsStepdefinition.class);

	private UserDetailsEndpoint userDetailsEndpoint = new UserDetailsEndpoint();
	private API api;

	public ViewUserDetailsStepdefinition(API api) {
		this.api = api;
	}

	@Given("a user is authenticated as an admin and has access to endpoint {string}")
	public void a_user_is_authenticated_as_an_admin_and_has_access_to_endpoint(String endpoint) {

		context.session.put("endpoint", endpoint);
		// context.session.put("userid", userid[2]);
	}

	@When("they request the total number of registered users")
	public void theyRequestTheTotalNumberOfRegisteredUsers() {

		api.setRequest(userDetailsEndpoint.requestSetup());
		api.setResponse(userDetailsEndpoint.getUsers(api.getRequest()));

		int userid = api.getResponse().getBody().jsonPath().getInt("[0].id");

		LOG.info("User ID: " + userid);
		assertNotNull("userid ID not found!", userid);
		// context.session.put("userid", userid);
		ExtentCucumberAdapter.addTestStepLog("User id -" + userid);
	}

	@When("user request for fetching records for {string}")
	public void user_request_for_fetching_records_for(String id) {
		api.setRequest(userDetailsEndpoint.requestSetup());

		api.setResponse(userDetailsEndpoint.getUsers(api.getRequest(), id));

		int userid = api.getResponse().getBody().jsonPath().getInt("id") ;

		LOG.info("User ID: " + userid);
		assertNotNull("userid ID not found!", userid);

		ExtentCucumberAdapter.addTestStepLog("User id -" + userid);
	}

	@Then("they should receive a response with the total user count and verify {string}")
	public void they_should_receive_a_response_with_the_total_user_count_and_verify(String actualcount) {

		Userdetails[] Userdetails = ResponseHandler.deserializedResponse(api.getResponse(), Userdetails[].class);
		assertNotNull("User ID not found!!", Userdetails);
		LOG.info("Number of Registered User ID: " + Userdetails.length);

		ExtentCucumberAdapter.addTestStepLog("Total number of User id present -" + Userdetails.length);

		assertEquals(actualcount, String.valueOf(Userdetails.length));
		// assertEquals(actualcount, Userdetails.length) ;
		// assertEquals(actualcount, "10") ;

	}

	@Then("they should receive a response with the total user count")
	public void theyShouldReceiveAResponseWithTheTotalUserCount() {
		Userdetails[] Userdetails = ResponseHandler.deserializedResponse(context.response, Userdetails[].class);
		assertNotNull("User ID not found!!", Userdetails);
		LOG.info("Number of Registered User ID: " + Userdetails.length);

		ExtentCucumberAdapter.addTestStepLog("User id -" + Userdetails.length);

	}

	@Then("the response status code should be {int}")
	public void theResponseStatusCodeShouldBe(Integer statusCode) {
		// assertEquals(Long.valueOf(statusCode),
		// Long.valueOf(context.response.getStatusCode()));

		userDetailsEndpoint.verifyResponseStatusValue(api.getResponse(), statusCode.intValue());
	}

	@When("they request user is able to fetch the records")
	public void they_request_user_is_able_to_fetch_the_records() {

		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());
		int userid = context.response.getBody().jsonPath().getInt("id");

		LOG.info("User ID: " + userid);
		assertNotNull("userid ID not found!", userid);
		context.session.put("userid", userid);
	}

	@Then("should be able to view records of particular user and verify details {string} {string} {string}")
	public void should_be_able_to_view_records_of_particular_user_and_verify_details(String name, String emailid,
			String addresscity) {

		Userdetails userDetails = ResponseHandler.deserializedResponse(api.getResponse(), Userdetails.class);
		assertNotNull("User Details not found!!", userDetails);

		ExtentCucumberAdapter.addTestStepLog("ID:  -" + userDetails.getId() + "Name-" + userDetails.getName()
				+ "Address City-" + userDetails.getAddress().getCity());

		LOG.info("ID: " + userDetails.getId());
		LOG.info("Name-" + userDetails.getName());
		LOG.info("Address City-" + userDetails.getAddress().getCity());

		assertEquals(userDetails.getName(), name);
		assertEquals(userDetails.getEmail(), emailid);
		assertEquals(userDetails.getAddress().getCity(), addresscity);

	}

	@Then("should be able to view records of particular user")
	public void should_be_able_to_view_records_of_particular_user() {
		LOG.info("Session BookingID: " + context.session.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID")).when()
				.get(context.session.get("endpoint") + "/{bookingID}");
		Userdetails userDetails = ResponseHandler.deserializedResponse(context.response, Userdetails.class);
		assertNotNull("User Details not found!!", userDetails);
		LOG.info("ID: " + userDetails.getId());
		LOG.info("Name-" + userDetails.getName());
		LOG.info("Address City-" + userDetails.getAddress().getCity());

	}

///////	

	@Given("user has access to endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {
		context.session.put("endpoint", endpoint);
	}

	/*
	 * @Then("user should get the response code {int}") public void
	 * userShpuldGetTheResponseCode(Integer statusCode) {
	 * assertEquals(Long.valueOf(statusCode),
	 * Long.valueOf(context.response.getStatusCode())); }
	 */

	@Then("user should see all the booking IDs")
	public void userShouldSeeAllTheBookingIDS() {
		Comments[] bookingIDs = ResponseHandler.deserializedResponse(context.response, Comments[].class);
		assertNotNull("Booking ID not found!!", bookingIDs);
	}

	@Then("user makes a request to view details of a booking ID")
	public void userMakesARequestToViewDetailsOfBookingID() {
		LOG.info("Session BookingID: " + context.session.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID")).when()
				.get(context.session.get("endpoint") + "/{bookingID}");
		// BookingDetailsDTO bookingDetails =
		// ResponseHandler.deserializedResponse(context.response,
		// BookingDetailsDTO.class);
		// assertNotNull("Booking Details not found!!", bookingDetails);
		// context.session.put("firstname", bookingDetails.getFirstname());
		// context.session.put("lastname", bookingDetails.getLastname());
	}



	@Then("user makes a request to view all the booking IDs of that user name")
	public void userMakesARequestToViewBookingIDByUserName() {
		LOG.info("Session firstname: " + context.session.get("firstname"));
		LOG.info("Session lastname: " + context.session.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.session.get("firstname"), "lastname", context.session.get("lastname"))
				.when().get(context.session.get("endpoint").toString());
		Comments[] bookingIDs = ResponseHandler.deserializedResponse(context.response, Comments[].class);
		assertNotNull("Booking ID not found!!", bookingIDs);
	}

	/*
	 * @Then("user validates the response with JSON schema {string}") public void
	 * userValidatesResponseWithJSONSchema(String schemaFileName) {
	 * context.response.then().assertThat()
	 * .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" +
	 * schemaFileName)); LOG.info("Successfully Validated schema from " +
	 * schemaFileName); }
	 */

	@When("user makes a request to check the health of booking service")
	public void userMakesARequestToCheckTheHealthOfBookingService() {
		String url = userDetailsEndpoint.getBaseUrl() ;
		userDetailsEndpoint.sendRequest(null, BaseEndpoints.GET_REQUEST, url, null).then().statusCode(200);
	}
}
