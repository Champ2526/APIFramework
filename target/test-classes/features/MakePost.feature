@createPost
Feature: To create a new Post

  #Background: create an auth token
   # Given user has access to endpoint "/posts"
    #When user creates a auth token with credential 
    #Then user should get the response code 200


  @createPost @Smoke @Regression
  Scenario Outline: User is able to create new post successfully
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 201 for successfull post
    And user validates the response with JSON schema "createPostSchema.json"

    Examples: 
      | userId | title    | body                 | 
      | 2      | API TEST | API test result      |       
       
 @createPostInvalidresponse @Smoke
  Scenario Outline: User is sending valid data for post but server response send incorrect response datatype
     Given a user is authenticated as an admin and has access to endpoints
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 201
    And user validates the response with JSON schema "createPostSchema1.json"

    Examples: 
      | userId | title    | body                 | 
      | 2      | API TEST | API test result      |  


  @createPostMissingdata @Smoke @Regression
  Scenario Outline: User is trying create new post with missing data
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 400
    Examples: 
       | title    | body                 | 
       | API TEST | API test result      |  



  @createPostInvaliddata @Regression
  Scenario Outline: User is trying to create new post with invalid data
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 400
    Examples: 
     | userId    | title    | body                 | 
     | 2@^^      | API TEST | API test result      |  


  @createPostInvalidresource
  Scenario Outline: User try to access invalid resources from server
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post with invalid resource
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 404
    Examples: 
     | userId    | title    | body                 | 
     | 2         | API TEST | API test result      |  


@createPostUnauthorizedUser
 Scenario Outline: Unauthorized user trying to post with valid data
    
    Given user creates a auth token with credential "fakeuser" & "password123"
    And user has access to endpoint "/posts"
    Then user should get the response code 401
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |

    Examples: 
      | userId | title    | body                 | 
      | 2      | API TEST | API test result      |  


#########################Other Possible Scenarios ##############################
# Valid only if there is constraint of not allowing duplicate post \resource
  @otherPost
  Scenario Outline: User is trying to create resource that already exists in the system.
    Given user has access to endpoint "/posts"
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 409
    Examples: 
     | userId    | title    | body                 | 
     | 2         | API TEST | API test result      |  

# Valid only if there is constraint of user posts exceeding the allowed post\rate limit per user\day
  @otherPost
  Scenario Outline: User is trying to multiple posts exceeding the allowed rate\post limit per user\day s  .
    Given user has access to endpoint "/posts"
    When user creates a post
      | userId   | title   | body   | 
      | <userId> | <title> | <body> |
    Then user should get the response code 429
    Examples: 
     | userId    | title    | body                 | 
     | 2         | API TEST | API test result      |  




  @createPostFromExcel1
  Scenario Outline: To create new booking using Excel data
    Given a user is authenticated as an admin and has access to endpoints
    When user creates a post using data "<dataKey>" from Excel
    Then user should get the response code 201
    And user validates the response with JSON schema from Excel

    Examples: 
      | dataKey |
      | addPost |


  @createPostFromJSON1
  Scenario Outline: To create new booking using JSON data
    Given user has access to endpoint "/posts"
    When user creates a post using data "<dataKey>" from JSON file "<JSONFile>"
    Then user should get the response code 201
    And user validates the response with JSON schema "createPostSchema.json"

    Examples: 
      | dataKey        | JSONFile         |
      | addPost        | psotBody.json    |

