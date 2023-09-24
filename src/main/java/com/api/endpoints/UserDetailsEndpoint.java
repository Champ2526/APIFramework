package com.api.endpoints;

import com.api.model.Createpost;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserDetailsEndpoint extends BaseEndpoints{
	
	
private final String Userendpoint = "/users" ;
	
	public UserDetailsEndpoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPath() {
		return this.Userendpoint;
	}
	
	public Response getUsers(RequestSpecification request) {
		String url = getBaseUrl() + this.getPath() ;
		return sendRequest(request, BaseEndpoints.GET_REQUEST, url, null);
	}
	
	public Response getUsers(RequestSpecification request, String id) {
		String url = getBaseUrl() + this.getPath() + "/"+id;
		return sendRequest(request, BaseEndpoints.GET_REQUEST, url, null);
	}
	
	

}
