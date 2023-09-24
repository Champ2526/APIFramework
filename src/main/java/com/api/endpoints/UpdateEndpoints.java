package com.api.endpoints;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;

import com.api.model.Createpost;
import com.api.model.Createpostbuilder;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateEndpoints extends BaseEndpoints{
	
private final String Updateendpoint = "/posts" ;
	
	public UpdateEndpoints() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPath() {
		return this.Updateendpoint;
	}

	
	public Response updatePostbyId (RequestSpecification request, Createpost createpost,String id) {
		String url = getBaseUrl() + this.getPath() + "/"+id;
		return sendRequest(request, BaseEndpoints.PUT_REQUEST, url, createpost);
	}
	
	public void validateBookingData(JSONObject postData, Createpost Createpost) {
		
		assertEquals("title did not match", postData.get("title"), Createpost.getTitle());
		assertEquals("body did not match", postData.get("body"), Createpost.getBody());
		assertEquals("userId did not match", postData.get("userId"), Createpost.getUserId());

	}
	
	
	public Response updatePostbyId (RequestSpecification request, Createpostbuilder createpostbuilder,String id) {
		String url = getBaseUrl() + this.getPath() + "/"+id;
		return sendRequest(request, BaseEndpoints.PATCH_REQUEST, url, createpostbuilder);
	}
	
	public Response deletePostbyId (RequestSpecification request, String id) {
		String url = getBaseUrl() + this.getPath() + "/"+id;
		return sendRequest(request, BaseEndpoints.DELETE_REQUEST, url, null);
	}
	
}
