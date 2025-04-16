Feature: Create a new booking using URL encoded example
  # This scenario creates a booking using x-www-form-urlencoded payload.
  # It validates both the response body and headers.

  Scenario: Successfully create a booking
    # Set the API endpoint for booking creation
    Given url baseUrl + "/booking"

    # Set necessary headers for form submission and expected response
    And header Content-Type = "application/x-www-form-urlencoded"
    And header Accept = "application/json"

    # Provide form-encoded request data for a new booking
    And request "firstname=Jim&lastname=Brown&totalprice=111&depositpaid=true&bookingdates%5Bcheckin%5D=2018-01-01&bookingdates%5Bcheckout%5D=2018-01-02"

    # Send POST request
    When method POST
    Then status 200

    # Validate booking response payload
    * match response.bookingid == "#number"
    * match response.booking.firstname == "Jim"
    * match response.booking.lastname == "Brown"
    * match response.booking.totalprice == 111
    * match response.booking.depositpaid == true
    * match response.booking.bookingdates.checkin == "2018-01-01"
    * match response.booking.bookingdates.checkout == "2018-01-02"

    # Validate response headers
    * match responseHeaders['Content-Type'][0] contains "application/json"

    # Store and print the booking ID for reference
    * def bookingid = response.bookingid
    * print "Booking Created Successfully with ID:", bookingid
