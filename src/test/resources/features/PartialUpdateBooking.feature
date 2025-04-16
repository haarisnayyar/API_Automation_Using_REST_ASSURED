Feature: Partially Update an Existing Booking
  # This scenario performs a PATCH request to update only specific fields (firstname and lastname) in an existing booking.

  Scenario: Partially update the booking

    # Get authentication token from reusable feature
    * def authResponse = call read("Authenticate.feature")
    * def authToken = authResponse.authToken

    # Create a new booking to be updated
    * def bookingResponse = call read("CreateBooking.feature")
    * def storedBookingId = bookingResponse.bookingid
    * assert storedBookingId != null && storedBookingId != ""

    # Prepare PATCH request with new firstname and lastname
    Given url baseUrl + "/booking/" + storedBookingId
    And headers { Accept: "application/json", "Content-Type": "application/json", Cookie: "#('token=' + authToken)" }
    And request
      """
      {
        "firstname": "Michael",
        "lastname": "Jordan"
      }
      """

    # Send PATCH request
    When method PATCH
    Then status 200

    # Validate that the updates were applied successfully
    * match response.firstname == "Michael"
    * match response.lastname == "Jordan"

    # Output the final response
    * print "Booking Partially Updated Successfully:", response
