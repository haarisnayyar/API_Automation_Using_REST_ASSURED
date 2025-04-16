Feature: Authenticate User
  # This feature tests the authentication endpoint of the Restful Booker API.
  # It generates a token by sending valid credentials in the request body.

  Scenario: Generate Authentication Token
    # Load username and password from karate-config.js
    * def username = auth.username
    * def password = auth.password

    # Set endpoint and request payload
    Given url baseUrl + "/auth"
    And request { username: '#(username)', password: '#(password)' }

    # Send POST request to get authentication token
    When method POST
    Then status 200

    # Extract and validate token from response
    * def authToken = response.token
    * match authToken == "#string"
    * print "Token Received:", authToken
