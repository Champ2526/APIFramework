package com.api.endpoints;

import com.api.model.Createpost;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostEndpoint extends BaseEndpoints{

	private final String Postendpoint = "/posts" ;
	
	
	public PostEndpoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPath() {
		return this.Postendpoint;
	}
	
	
	public Response createPost(RequestSpecification request, Createpost createpost) {
		String url = getBaseUrl() + this.getPath();
		return sendRequest(request, BaseEndpoints.POST_REQUEST, url, createpost);
	}
	
	
	public Response createPost1(RequestSpecification request, Createpost createpost) {
		String url = getBaseUrl() + "/post" ;
		return sendRequest(request, BaseEndpoints.POST_REQUEST, url, createpost);
	}

}
