package com.handson.aws.swf;

public class FlightBookingActivities {

    public String gotoWebsite() {
        return "Visited flight booking website";
    }

    public String searchFlight() {
        return "Searched for flights";
    }

    public String selectFlight() {
        return "Selected flight";
    }

    public String makePayment() {
        return "Paid for flight";
    }

    public String getConfirmation() {
        return "Received flight booking confirmation";
    }

}
