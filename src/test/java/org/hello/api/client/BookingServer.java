package org.hello.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hello.api.entity.BookingModal;
import org.hello.core.framework.api.connection.AbstractBookingService;

public class BookingServer extends AbstractBookingService {

    private static final String BOOKING_URL = "/booking/";

    /**
     * Method to get all the bookings from server
     * @return Response
     */
    @Step("Getting bookings")
    public Response getBooking(){
        log.info("Getting all bookings");

        return getRequest(BOOKING_URL);
    }

    /**
     * Method to get all the bookings from server
     * @return Response
     */
    @Step("Create booking")
    public Response createBooking(BookingModal booking){
        log.info("Creating booking");

        return postRequest(BOOKING_URL, booking);
    }

    /**
     * Method to get the bookings from server
     * @return Response
     */
    @Step("Getting the booking using the id:[{0}]")
    public Response getBooking(int id){
        log.info("Getting the booking by Id");
        String url = BOOKING_URL+"/"+id;

        return getRequest(url);
    }

    /**
     * Method to delete the bookings from server
     * @return Response
     */
    @Step("Getting the booking using the id:[{0}]")
    public Response deleteBooking(int id){
        log.info("Getting the booking by Id");
        String url = BOOKING_URL+"/"+id;

        return getRequest(url);
    }
}
