package com.api.stepdefinition;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.api.endpoints.CommentEndpoint;
import com.api.endpoints.PostEndpoint;
import com.api.model.API;
import com.api.model.Comments;
import com.api.model.Createpost;
import com.api.model.Userdetails;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommentStepdefinition {

	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(CommentStepdefinition.class);

	private CommentEndpoint commentEndpoint = new CommentEndpoint();
	private API api;

	public CommentStepdefinition(API api) {
		// this.context = context;
		this.api = api;
	}
	
	
	
	@When("they request the total number of comments")
	public void they_request_the_total_number_of_comments() {
		
		api.setRequest(commentEndpoint.requestSetup());
		api.setResponse(commentEndpoint.getComments(api.getRequest()));

		int postId = api.getResponse().getBody().jsonPath().getInt("[0].postId");

		LOG.info("Post ID: " + postId);
		assertNotNull("Post ID not found!", postId);

		ExtentCucumberAdapter.addTestStepLog("Post ID -" + postId);
		
		
	}

	@Then("they should receive a response with the total comments count")
	public void they_should_receive_a_response_with_the_total_comments_count() {
		Comments[] commentiddetails = ResponseHandler.deserializedResponse(api.getResponse(), Comments[].class);
		LOG.info("Number of Comments: " + commentiddetails.length);
		assertNotNull("Number of Comments!!", commentiddetails);
	}

	@When("they request the total number of comments on post {string}")
	public void they_request_the_total_number_of_comments_on_post(String pathparam) {
	    
		api.setRequest(commentEndpoint.requestSetup());
		api.setResponse(commentEndpoint.getCommentsbypostid(api.getRequest(),pathparam));
		ExtentCucumberAdapter.addTestStepLog(" Response received: " + api.getResponse().getBody().asString());
	}

	
	@Then("should be able to view records of particular post {string}")
	public void should_be_able_to_view_records_of_particular_post(String actualpostid) {
		Comments[] commentiddetails = ResponseHandler.deserializedResponse(api.getResponse(), Comments[].class);
		LOG.info("Number of Comments: " + commentiddetails.length);

		for(int i=0; i<commentiddetails.length;i++) {
			int psotid = api.getResponse().getBody().jsonPath().getInt("["+i+"].postId");
			
			if(psotid == Integer.parseInt(actualpostid)) {
				LOG.info(api.getResponse().getBody().jsonPath().getString("["+i+"].name"));
			}
			else {
				LOG.info("Postid do not match Expected is 2 but actual" + psotid);
			}
		}
		
		
		
		assertNotNull("Comment ID not found!!", commentiddetails);
	}

	
	
		

	
	
}
