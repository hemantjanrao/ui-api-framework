package org.hello.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hello.core.framework.api.connection.AbstractService;

public class BookingServer extends AbstractService {

    private static final String BOOKING_URL = "/booking/";

    /**
     * Constructor
     *
     * @param host Host Name
     * @param port Port Number
     * @param protocol Protocol
     */
    public BookingServer(String host, int port, String protocol) {
        super(host, port, protocol);
    }

    @Override
    protected void login(String username, String password) {
        log.info("Currently no login is required");
    }

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
    public Response createBooking(String booking){
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
        String url = BOOKING_URL+""+id;

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
