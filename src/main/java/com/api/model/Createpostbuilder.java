package com.api.model;

public class Createpostbuilder {

	private int id;
	private String title;
	private String body;
	private String userId;

	public Createpostbuilder() {

	}

	private Createpostbuilder(UserBuilder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.body = builder.body;
		this.userId = builder.userId;
	}

	public Createpostbuilder(String title, String body, String userId) {

		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getUserId() {
		return userId;
	}

	public static class UserBuilder {
		private int id;
		private String title;
		private String body;
		private String userId;

		public UserBuilder id(int id) {
			this.id = id;
			return this;
		}

		public UserBuilder title(String title) {
			this.title = title;
			return this;
		}

		public UserBuilder body(String body) {
			this.body = body;
			return this;
		}

		public UserBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		// Return the finally consrcuted User object
		public Createpostbuilder build() {
			Createpostbuilder user = new Createpostbuilder(this);
			validateUserObject(user);
			return user;
		}

		private void validateUserObject(Createpostbuilder user) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}
	}

}
