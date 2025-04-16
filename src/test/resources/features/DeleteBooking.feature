Feature: Delete an Existing Booking
  # This scenario deletes a booking created dynamically and verifies that it no longer exists.

  Scenario: Delete a booking and assert status code

    # Reuse authentication token by calling Authenticate.feature
    * def authResponse = call read("Authenticate.feature")
    * def authToken = authResponse.authToken
    * print "Auth Token Retrieved:", authToken

    # Reuse booking creation logic to get a new booking ID to delete
    * def bookingResponse = call read("CreateBooking.feature")
    * def storedBookingId = bookingResponse.bookingid
    * print "Booking ID Retrieved for Deletion:", storedBookingId
    * assert storedBookingId != null && storedBookingId != ""

    # DELETE the newly created booking
    Given url baseUrl + "/booking/" + storedBookingId
    And headers { Accept: "application/json", "Content-Type": "application/json", Cookie: "#('token=' + authToken)" }
    When method DELETE
    Then status 201

    * print "Booking Deleted Successfully:", storedBookingId

    # Verify the deleted booking no longer exists
    Given url baseUrl + "/booking/" + storedBookingId
    And headers { Accept: "application/json" }
    When method GET
    Then status 404

    * print "Verified: Booking ID", storedBookingId, "no longer exists."
