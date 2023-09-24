package com.api.endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommentEndpoint extends BaseEndpoints{

	private final String commentendpoint = "/comments" ;
	
	public CommentEndpoint() {
		super();
	}

	public String getPath() {
		return this.commentendpoint;
	}

	public Response getComments(RequestSpecification request) {
		String url = getBaseUrl() + this.getPath() ;
		return sendRequest(request, BaseEndpoints.GET_REQUEST, url, null);
	}
	
	public Response getCommentsbypostid(RequestSpecification request,String pathparam) {
		String url = getBaseUrl() + this.getPath() ;
		
		 
		
		return sendRequest(request.queryParam("postId", pathparam), BaseEndpoints.GET_REQUEST, url, null);
	}
	
	
	
}
