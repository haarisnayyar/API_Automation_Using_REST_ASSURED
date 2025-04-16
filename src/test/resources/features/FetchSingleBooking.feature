Feature: Fetch Single Booking Details
  # This scenario retrieves the complete details of a booking using a given booking ID.

  Scenario: Fetch booking details by ID

    # Set the endpoint to retrieve booking by ID (passed via scenario context)
    Given url baseUrl + "/booking/" + bookingId

    # Set the expected header for JSON response
    And headers { Accept: "application/json" }

    # Execute GET request
    When method GET
    Then status 200

    # Store the entire response object for further use
    * def response = response
