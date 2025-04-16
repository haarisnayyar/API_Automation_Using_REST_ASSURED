Feature: Fetch Booking Details and Validate Deposit Paid
  # This scenario loops through all booking IDs retrieved from the system
  # and validates that each booking has depositPaid set to true.

  Scenario: Validate depositPaid for each booking ID

    # Fetch booking IDs from the system via a reusable feature
    * def bookingResponse = call read("FetchBookingIds.feature")
    * def bookingIds = bookingResponse.bookingIds
    * print "Booking IDs Retrieved:", bookingIds

    # Assert that at least one record is retrieved
    * assert bookingIds.length > 0
    * print "Number of Records Retrieved:", bookingIds.length

    # Initialize results array to hold depositPaid values
    * def results = []

    # Loop over each booking ID and fetch full booking details
    * eval
      """
      for (var i = 0; i < bookingIds.length; i++) {
        var id = bookingIds[i];
        karate.log("Fetching details for Booking ID:", id);
        var response = karate.call('FetchSingleBooking.feature', { bookingId: id });
        var booking = response.response;

      // If booking not found, push false; otherwise, push depositPaid value
        if (!booking) {
          karate.log("No valid booking data found for ID:", id);
          results.push(false);
        } else {
          karate.log("Booking details for ID:", id, booking);
          results.push(booking.depositpaid);
        }
      }
      """

    # Validate all values are booleans
    * match each results == "#boolean"
    * print "Validation Results (depositPaid):", results

    # Ensure all depositPaid values are true
    * match each results == true
