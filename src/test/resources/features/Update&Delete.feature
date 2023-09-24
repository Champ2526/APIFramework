 @updatePost
Feature: To update\delete a post


  @updatepost
  Scenario Outline: To update a post using id
    Given a user is authenticated as an admin and has access to endpoints
    And user updates the details for post "<Id>"
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createPostSchema.json"

    Examples: 
      |Id| userId | title    | body                 | 
      | 1| 1      | API TEST | API test result      |  

  
  @partialpost
  Scenario Outline: To partially update a post
    Given a user is authenticated as an admin and has access to endpoints
    And user wants to updates the details partially for post "<Id>"
      | title   | body   | 
      | <title> | <body> |
    Then user should get the response code 200


    Examples: 
      |Id| title            | body                        | 
      | 1| API TEST Updated | API test result Updated     |  
      
      
      
      
 @deletePosts
   Scenario Outline: To delete a post
   Given a user is authenticated as an admin and has access to endpoints
   When user delete the for post "<Id>"
    | Id  |  
    |<Id> |
   Then user should get the response code 200
   
    Examples: 
      |Id| 
      | 1| 
      