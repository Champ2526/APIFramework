package com.api.endpoints;

import org.json.JSONObject;

import com.api.utils.PropertiesFile;
import com.api.utils.RestAssuredRequestFilter;
import com.api.utils.TestContext;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

public class BaseEndpoints {

	private TestContext context;
	protected final String base_url = PropertiesFile.getProperty("baseURL");
	private static final String CONTENT_TYPE = PropertiesFile.getProperty("content.type");
	
	public Response response;
	public Map<String, Object> session = new HashMap<String, Object>();

	public static final int SUCCESS_STATUS_CODE = 200;

	public static final int GET_REQUEST = 0;
	public static final int POST_REQUEST = 1;
	public static final int DELETE_REQUEST = 2;
	public static final int PUT_REQUEST = 3;
	public static final int PATCH_REQUEST = 4 ;
	

	public BaseEndpoints() {

	}

	public RequestSpecification requestSetup() {
		RestAssured.reset();
		Options options = Options.builder().logStacktrace().build();
		RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
		RestAssured.baseURI = PropertiesFile.getProperty("baseURL");
		return RestAssured.given().config(config).filter(new RestAssuredRequestFilter())
				.header("Content -Type", CONTENT_TYPE).contentType(CONTENT_TYPE).accept(CONTENT_TYPE);
	}

	public String getBaseUrl() {
		return this.base_url;
	}

	public void verifyResponseStatusValue(Response response, int expectedCode) {
		assertThat(response.getStatusCode(), is(expectedCode));
	}

	

	public void verifyResponseWithJSONSchema(Response response, String schemaFileName) {
		response.then().assertThat()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
         //LOG.info("Successfully Validated schema from " + schemaFileName);
	}
	
	
	
	protected JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}

	public Response sendRequest(RequestSpecification request, int requestType, String url, Object pojo) {
		Response response = null;

		// Add the Json to the body of the request
		if (null != pojo) {
			String payload = createJSONPayload(pojo).toString();
			request.body(payload);
		}

		// need to add a switch based on request type
		switch (requestType) {
		case BaseEndpoints.GET_REQUEST:
			if (null == request) {
				response = RestAssured.when().get(url);
			} else {
				response = request.get(url);
			}
			break;
		case BaseEndpoints.POST_REQUEST:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			break;
		case BaseEndpoints.DELETE_REQUEST:
			if (null == request) {
				response = RestAssured.when().delete(url);
			} else {
				response = request.delete(url);
			}
			break;
		case BaseEndpoints.PUT_REQUEST:
			if (null == request) {
				response = RestAssured.when().put(url);
			} else {
				response = request.put(url);
			}
			break;
		case BaseEndpoints.PATCH_REQUEST:
			if (null == request) {
				response = RestAssured.when().patch(url);
			} else {
				response = request.put(url);
			}
			break;
		default:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			response = request.post(url);
			break;
		}
		return response;
	}

}
