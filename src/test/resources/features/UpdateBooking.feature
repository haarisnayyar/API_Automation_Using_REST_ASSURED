Feature: Update an Existing Booking
  # This scenario performs a full update of a previously created booking using the PUT method.

  Scenario: Update the created booking

    # Get authentication token from reusable authentication feature
    * def authResponse = call read("Authenticate.feature")
    * def authToken = authResponse.authToken

    # Create a new booking to update
    * def bookingResponse = call read("CreateBooking.feature")
    * def storedBookingId = bookingResponse.bookingid
    * assert storedBookingId != null && storedBookingId != ""

    # Prepare complete request payload to update all booking fields
    Given url baseUrl + "/booking/" + storedBookingId
    And headers { Accept: "application/json", "Content-Type": "application/json", Cookie: "#('token=' + authToken)" }
    And request
      """
      {
        "firstname": "James",
        "lastname": "Brown",
        "totalprice": 222,
        "depositpaid": false,
        "bookingdates": {
          "checkin": "2022-01-01",
          "checkout": "2023-01-01"
        },
        "additionalneeds": "Lunch"
      }
      """

    # Send PUT request
    When method PUT
    Then status 200

    # Validate all updated fields
    * match response.firstname == "James"
    * match response.lastname == "Brown"
    * match response.totalprice == 222
    * match response.depositpaid == false
    * match response.bookingdates.checkin == "2022-01-01"
    * match response.bookingdates.checkout == "2023-01-01"
    * match response.additionalneeds == "Lunch"

    # Output the full response
    * print "Booking Updated Successfully:", response
