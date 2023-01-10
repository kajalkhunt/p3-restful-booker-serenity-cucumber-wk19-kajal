Feature: Testing different request on the restful booker app

  As a user I want to test booking Application

  Scenario Outline: CRUD Test
    Given RestfulBooker application is running
    When When I create a new booking by providing the information firstName "<firstName>" lastName "<lastName>" totalprice "<totalPrice>" depositpaid "<depositPaid>" checkin "<checkIn>"checkout "<checkOut>"additionalneeds "<additionalNeeds>"
    Then I verify that Booking with bookingId is created
    And I update a booking with firstName "<firstName>" lastName "<lastName>" totalPrice "<totalPrice>" depositPaid "<depositPaid>" checkIn "<checkIn>" checkOut "<checkOut>" additionalNeeds "<additionalNeeds>"
    And I update partial booking with firstName "<firstName>" lastName "<lastName>" totalPrice "<totalPrice>" depositPaid "<depositPaid>" checkIn "<checkIn>" checkOut "<checkOut>" additionalNeeds "<additionalNeeds>"
    And I delete the booking with bookingId
    Then I verify the response has "<code>" status code
    Examples:
      | firstName | lastName   | totalPrice | depositPaid | checkIn    | checkOut   | additionalNeeds |
      | Tommie    | Ringsell   | 500        | 100         | 2022-01-16 | 2022-03-13 | Breakfast       |
      | Dynah     | Studdert   | 200        | 100         | 2022-03-10 | 2022-06-19 | Breakfast       |