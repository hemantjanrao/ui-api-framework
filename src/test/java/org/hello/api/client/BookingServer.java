package org.hello.api.client;

import io.qameta.allure.Step;
import org.hello.core.framework.api.connection.AbstractServer;

import javax.ws.rs.core.Response;

public class BookingServer extends AbstractServer {

    private static final String BOOKING_URL = "/booking/";


    /**
     * Constructor
     *
     * @param host
     * @param port
     * @param protocol
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
        Response res = getRequest(BOOKING_URL);
        res.bufferEntity();
        return res;
    }

    /**
     * Method to get all the bookings from server
     * @return Response
     */
    @Step("Create booking")
    public Response createBooking(String bookingModal){
        log.info("Creating booking");
        Response res = postRequest(BOOKING_URL,bookingModal);
        res.bufferEntity();
        return res;
    }

    /**
     * Method to get the bookings from server
     * @return Response
     */
    @Step("Getting the booking using the id:[{0}]")
    public Response getBooking(int id){
        log.info("Getting the booking by Id");
        String url = BOOKING_URL+"/"+id;
        Response res = getRequest(url);
        res.bufferEntity();
        return res;
    }

    /**
     * Method to delete the bookings from server
     * @return Response
     */
    @Step("Getting the booking using the id:[{0}]")
    public Response deleteBooking(int id){
        log.info("Getting the booking by Id");
        String url = BOOKING_URL+"/"+id;
        Response res = getRequest(url);
        res.bufferEntity();
        return res;
    }
}
