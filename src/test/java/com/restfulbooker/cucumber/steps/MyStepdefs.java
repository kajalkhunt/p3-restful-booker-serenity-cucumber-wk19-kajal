package com.restfulbooker.cucumber.steps;

import com.restfulbooker.bookininfo.AuthSteps;
import com.restfulbooker.bookininfo.BookingSteps;
import com.restfulbooker.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Jay
 */
public class MyStepdefs {
    @Steps
    AuthSteps authSteps;
    @Steps
    BookingSteps bookingSteps;

    public static ValidatableResponse response;

    static String firstName = null;
    static String lastName = null;
    static int totalPrice;
    static boolean depositPaid = true;
    static String checkIn = null;
    static String checkOut = null;
    static String additionalNeeds = null;
    static String username = null;
    static String password = null;
    static int bookingId;
    static String token;
    @Given("^RestfulBooker application is running$")
    public void restfulbookerApplicationIsRunning() {
    }

    @When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iEnterUsernameAndPassword(String username, String password)  {

        token = authSteps.getAuthToken(username, password);

    }

    @Then("^I get authorization token$")
    public void iGetAuthorizationToken() {
    }

    @When("^When I create a new booking by providing the information firstName \"([^\"]*)\" lastName \"([^\"]*)\" totalprice \"([^\"]*)\" depositpaid \"([^\"]*)\" checkin \"([^\"]*)\"checkout \"([^\"]*)\"additionalneeds \"([^\"]*)\"$")
    public void whenICreateANewBookingByProvidingTheInformationFirstNameLastNameTotalpriceDepositpaidCheckinCheckoutAdditionalneeds(String firstName, String lastName, int totalPrice, boolean depositPaid,
                                                                                                                                    String checkIn, String checkOut, String additionalNeeds)  {

        ValidatableResponse response = bookingSteps.createBooking(firstName, lastName, totalPrice, depositPaid, checkIn,
                checkOut, additionalNeeds);
        response.statusCode(200).log().all();
        bookingId = response.extract().path("bookingid");
    }


    @Then("^I verify that Booking with bookingId is created$")
    public void iVerifyThatBookingWithBookingIdIsCreated() {
    }


    @Then("^I verify the response has \"([^\"]*)\" status code$")
    public void iVerifyTheResponseHasStatusCode(String code)  {
//      response.statusCode(200);
    }





    @When("^I update a booking with firstName \"([^\"]*)\" lastName \"([^\"]*)\" totalPrice \"([^\"]*)\" depositPaid \"([^\"]*)\" checkIn \"([^\"]*)\" checkOut \"([^\"]*)\" additionalNeeds \"([^\"]*)\"$")
    public void iUpdateABookingWithFirstNameLastNameTotalPriceDepositPaidCheckInCheckOutAdditionalNeeds(String _firstname, String _lastname, int totalPrice, boolean depositPaid,
                                                                                                        String checkIn, String checkOut, String additionalNeeds)  {
        firstName = _firstname + "_updated";
      lastName = lastName + "_updated";
//        additionalNeeds = "Bed and Breakfast";
//        token = authSteps.getAuthToken(username, password);
        ValidatableResponse response = bookingSteps.updateBooking(bookingId, _firstname, lastName, totalPrice,
                depositPaid, checkIn, checkOut, additionalNeeds, token);
        response.statusCode(200).log().all();
        response.body("firstname", equalTo(_firstname), "lastname", equalTo(lastName),
                "additionalneeds", equalTo(additionalNeeds));


    }

    @When("^I update partial booking with firstName \"([^\"]*)\" lastName \"([^\"]*)\" totalPrice \"([^\"]*)\" depositPaid \"([^\"]*)\" checkIn \"([^\"]*)\" checkOut \"([^\"]*)\" additionalNeeds \"([^\"]*)\"$")
    public void iUpdatePartialBookingWithFirstNameLastNameTotalPriceDepositPaidCheckInCheckOutAdditionalNeeds(String _firstname, String _lastname, int totalPrice, boolean depositPaid,
                                                                                                              String checkIn, String checkOut, String additionalNeeds)  {
     firstName = firstName + " _partial";
        lastName = _lastname + " _partial";
//        token = authSteps.getAuthToken(username, password);
        ValidatableResponse response = bookingSteps.updatePartialBooking(bookingId, firstName, _lastname, totalPrice,
                depositPaid, checkIn, checkOut, additionalNeeds, token);
        response.statusCode(200).log().all();
        response.body("firstname", equalTo(firstName), "lastname", equalTo(_lastname),
                "additionalneeds", equalTo(additionalNeeds));
    }

    @When("^I delete the booking with bookingId$")
    public void iDeleteTheBookingWithBookingId() {
        ValidatableResponse response = bookingSteps.deleteBookingWithBookingId(bookingId, token);
        response.statusCode(201).log().all();
        bookingSteps.getBookingWithBookingId(bookingId).log().all().statusCode(404);
    }
}
