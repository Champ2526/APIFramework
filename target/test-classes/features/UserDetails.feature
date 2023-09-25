 @viewUser
Feature: To view the user details
  #Background: create an auth token
    #Given user has access to endpoint "/auth"
    #When user creates a auth token with credential 
    #Then user should get the response code 200


@viewAllUsers @Smoke
Scenario Outline: Retrieving the total number of registered users
  Given a user is authenticated as an admin and has access to endpoints
  When they request the total number of registered users
  And the response status code should be 200
  Then they should receive a response with the total user count and verify "<Result>"
   Examples: 
     |Result | 
     |10     |
 

 @viewAllUserschema @Smoke 
  Scenario:Validating Server response received as expected schema validation
   Given a user is authenticated as an admin and has access to endpoints
   When they request the total number of registered users
   And the response status code should be 200
    And user validates the response with JSON schema "Users.json"


@viewparticularUserdetails @Smoke
Scenario Outline: Users are able to search for user by its id
  Given a user is authenticated as an admin and has access to endpoints
  When user request for fetching records for "<Id>"
  #When they request user is able to fetch the records
  And the response status code should be 200
  Then should be able to view records of particular user and verify details "<Name>" "<Emailid>" "<AddressCity>"
  Examples: 
     |Id | Name          | Emailid           |AddressCity |
     |1  | Leanne Graham | Sincere@april.biz |Gwenborough |



  @healthCheck
  Scenario: To confirm whether the API is up and running
    Given a user is authenticated as an admin and has access to endpoints
    When user makes a request to check the health of booking service
    And the response status code should be 200


 
