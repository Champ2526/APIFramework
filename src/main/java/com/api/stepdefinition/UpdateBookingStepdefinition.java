package com.api.stepdefinition;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.endpoints.PostEndpoint;
import com.api.endpoints.UpdateEndpoints;
import com.api.model.API;
import com.api.model.Createpost;
import com.api.model.Createpostbuilder;
import com.api.utils.ExcelUtils;
import com.api.utils.JsonReader;
import com.api.utils.PropertiesFile;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class UpdateBookingStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(UpdateBookingStepdefinition.class);
	private UpdateEndpoints updateEndpoints = new UpdateEndpoints();
	private API api;

	public UpdateBookingStepdefinition(API api) {
		this.api = api;
	}



	
	
	@And("user updates the details for post {string}")
	public void user_updates_the_details_for_post(String id, DataTable dataTable) {
		Map<String, String> postData = dataTable.asMaps().get(0);
		api.setRequest(updateEndpoints.requestSetup());
		api.setResponse(updateEndpoints.updatePostbyId(api.getRequest(),
			new Createpost(postData.get("title"), postData.get("body"), postData.get("userId")),id));
		
			Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		assertNotNull("Post not created", Createpost);
		ExtentCucumberAdapter.addTestStepLog("Newly created booking ID: " + Createpost.getId());
		LOG.info("Newly created booking ID: " + Createpost.getId());

		updateEndpoints.validateBookingData(new JSONObject(postData), Createpost);
	}

	
	@And("user wants to updates the details partially for post {string}")
	public void user_wants_to_updates_the_details_partially_for_post(String id, DataTable dataTable) {
		Map<String, String> postData = dataTable.asMaps().get(0);
		api.setRequest(updateEndpoints.requestSetup());
		
		api.setResponse(updateEndpoints.updatePostbyId(api.getRequest(),
				new  Createpostbuilder.UserBuilder().userId(postData.get("title")).body(postData.get("body")).build(),id)) ;
		
		Createpost Createpost = ResponseHandler.deserializedResponse(api.getResponse(), Createpost.class);
		assertNotNull("Post not UPDATED", Createpost);
		ExtentCucumberAdapter.addTestStepLog("Newly created booking ID: " + Createpost.getId());
		LOG.info("Newly created booking ID: " + Createpost.getId());


	}
	
	@When("user delete the for post {string}")
	public void user_delete_the_for_post(String id, DataTable dataTable) {
	
		api.setRequest(updateEndpoints.requestSetup());
		api.setResponse(updateEndpoints.deletePostbyId(api.getRequest(),id));

	}

	
	
	@When("user makes a request to update first name {string} & Last name {string}")
	public void userMakesARequestToUpdateFirstNameLastName(String firstName, String lastName) {
		JSONObject body = new JSONObject();
		body.put("firstname", firstName);
		body.put("lastname", lastName);
		
		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(body.toString())
				.when().patch(context.session.get("endpoint")+"/{bookingID}");
		
		//BookingDetailsDTO bookingDetailsDTO = ResponseHandler.deserializedResponse(context.response, BookingDetailsDTO.class);
	//	assertNotNull("Booking not created", bookingDetailsDTO);
		//assertEquals("First Name did not match", firstName, bookingDetailsDTO.getFirstname());
		//assertEquals("Last Name did not match", lastName, bookingDetailsDTO.getLastname());
	}
	
	
	
	///
	@When("user creates a auth token with credential {string} & {string}")
	public void userCreatesAAuthTokenWithCredential(String username, String password) {
		JSONObject credentials = new JSONObject();
		credentials.put("username", PropertiesFile.getProperty("username"));
		credentials.put("password", PropertiesFile.getProperty("password"));
		context.response = context.requestSetup().body(credentials.toString())
				.when().post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		LOG.info("Auth Token: "+token);
		context.session.put("token", "token="+token);	
	}

	
}
