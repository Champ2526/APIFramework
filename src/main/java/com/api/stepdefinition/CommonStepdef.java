package com.api.stepdefinition;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.endpoints.BaseEndpoints;
import com.api.utils.PropertiesFile;
import com.api.utils.TestContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class CommonStepdef {
	private TestContext context;
	private BaseEndpoints be = new BaseEndpoints();
	private static final Logger LOG = LogManager.getLogger(CommonStepdef.class);
	
	public CommonStepdef(TestContext context) {
		this.context = context;
	}
	
	
	
	@Given("a user is authenticated as an admin and has access to endpoints")
	public void a_user_is_authenticated_as_an_admin_and_has_access_to_endpoints() {
		String url = be.getBaseUrl() ;
		be.sendRequest(null, BaseEndpoints.GET_REQUEST, url, null).then().statusCode(200);
	}

	
	
	
	
	@When("user creates a auth token with credential")
	public  void user_creates_a_auth_token_with_credential() {
		JSONObject credentials = new JSONObject();
		credentials.put("username", PropertiesFile.getProperty("username"));
		credentials.put("password", PropertiesFile.getProperty("password"));
		context.response = context.requestSetup().body(credentials.toString())
				.when().post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		LOG.info("Auth Token: "+token);
		context.session.put("token", "token="+token);
	}

	
	/*@When("user creates a auth token with credential {string} & {string}")
	public void userCreatesAAuthTokenWithCredential(String username, String password) {
		JSONObject credentials = new JSONObject();
		credentials.put("username", username);
		credentials.put("password", password);
		context.response = context.requestSetup().body(credentials.toString()).when()
				.post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		LOG.info("Auth Token: " + token);
		context.session.put("token", "token=" + token);
	}*/
	
	public void verifyNestedResponseKeyValues(String nestTedCompnent, String key, String val, Response  r ) {
		Map<String, String> nestedJSON = r.jsonPath().getMap(nestTedCompnent);
		String actual = String.valueOf(nestedJSON.get(key));
		//assertThat(actual, is(val));
	}

	public void verifyNestedArrayValueResponseKeyValues(String nestTedCompnent, String[] val, Response  r) {

		ArrayList<Object> nestedArray = (ArrayList<Object>) r.jsonPath().getList(nestTedCompnent);

		String actual;

		for (int i = 0; i < nestedArray.size(); i++) {
			actual = (String) nestedArray.get(i);
			//assertThat(actual, is(val[i]));
		}
	}

	public void verifyNestedArrayMapResponseKeyValues(String nestTedCompnent, String key, String[] val, Response r) {
		ArrayList<Object> nestedArray = (ArrayList<Object>) r.jsonPath().getList(nestTedCompnent);

		String actual;
		for (int i = 0; i < nestedArray.size(); i++) {
			Map<String, String> map = (Map<String, String>) nestedArray.get(i);
			actual = String.valueOf(map.get(key));
			//assertThat(actual, is(val[i]));
		}
	}

	
	
	
}
