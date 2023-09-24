package com.api.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.api.stepdefinition.CommonStepdef;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
		
	public Response response;
	public Map<String, Object> session = new HashMap<String, Object>();
	private static final String CONTENT_TYPE = PropertiesFile.getProperty("content.type");
	
	public RequestSpecification requestSetup() {	
		RestAssured.reset();
		Options options = Options.builder().logStacktrace().build();
		RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options); 
		RestAssured.baseURI = PropertiesFile.getProperty("baseURL");	
		return RestAssured.given()
				.config(config)
				.filter(new RestAssuredRequestFilter())	
			    .header("Content -Type",CONTENT_TYPE)
				.contentType(CONTENT_TYPE)
				.accept(CONTENT_TYPE)
				;
	} 
	
	
	
	   public RequestSpecification requestSetupwithauth() {
		   RestAssured.reset();
		   user_creates_a_auth_token_with_credential() ;
			Options options = Options.builder().logStacktrace().build();
			RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options); 
			RestAssured.baseURI = PropertiesFile.getProperty("baseURL");	
			return RestAssured.given()
					.config(config)
					.filter(new RestAssuredRequestFilter())	
				    .header("Content -Type",CONTENT_TYPE)
					.contentType(CONTENT_TYPE)
					.accept(CONTENT_TYPE)
					;
	    }
	   
	   private  void user_creates_a_auth_token_with_credential() {
			JSONObject credentials = new JSONObject();
			credentials.put("username", PropertiesFile.getProperty("username"));
			credentials.put("password", PropertiesFile.getProperty("password"));
			response = requestSetup().body(credentials.toString())
					.when().post(session.get("endpoint").toString());
			String token = response.path("token");

			session.put("token", "token="+token);
		}
	
	
}
