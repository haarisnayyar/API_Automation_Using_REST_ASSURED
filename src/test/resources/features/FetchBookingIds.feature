Feature: Fetch Booking IDs by Date Range
  # This scenario retrieves a list of booking IDs filtered by check-in and check-out dates.

  Scenario: Fetch booking IDs using check-in/check-out query parameters

    # Set endpoint to fetch bookings with filters
    Given url baseUrl + "/booking"
    And headers { Accept: "application/json" }

    # Apply query parameters for date range
    And param checkin = "2020-03-13"
    And param checkout = "2023-05-21"

    # Execute GET request
    When method GET
    Then status 200

    # Output raw API response to console
    * print "Raw Response from /booking:", response

    # Extract booking IDs and convert each to string
    * def bookingIds = response.map(x => x.bookingid + "")

    # Assert that the list is not empty
    * assert bookingIds.length > 0

    # Output retrieved booking IDs
    * print "Booking IDs received:", bookingIds
