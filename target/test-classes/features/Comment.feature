@comment
Feature: To view comments on post

@ViewAllcomments @Smoke
Scenario: Retrieving the total number of comments
  Given a user is authenticated as an admin and has access to endpoints
  When they request the total number of comments
  And the response status code should be 200
  Then they should receive a response with the total comments count
 
@commentparticularpost @Smoke
Scenario Outline: Retrieving total number of comments on particular post
  Given a user is authenticated as an admin and has access to endpoints
  When they request the total number of comments on post "<Id>"
  And the response status code should be 200
  Then should be able to view records of particular post "<Id>"
  Examples: 
     |Id | 
     |2  | 


  