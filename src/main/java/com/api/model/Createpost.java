package com.api.model;

public class Createpost {

	private Integer id;
	private String title;
	private String body;
	private String userId;

	public Createpost() {

	}

	public Createpost(String title, String body, String userId) {

		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}