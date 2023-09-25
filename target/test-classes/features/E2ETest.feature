 @e2eTest
Feature: End To End test scneario



 @e2eTest @Smoke @Regression
  Scenario Outline: User is able to Post, Get, Update, Delete his posts
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 201 for successfull post
    And user validates the response with JSON schema "createPostSchema.json"
    When user request for fetching records for "<Id>"
    And the response status code should be 200
     And user updates the details for post "<Id>"
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createPostSchema.json"
    When user delete the for post "<Id>"
    | Id  |  
    |<Id> |
   Then user should get the response code 200
    
    
    Examples: 
      | userId | title    | body                 | Id  |
      | 2      | API TEST | API test result      | 2   |






  