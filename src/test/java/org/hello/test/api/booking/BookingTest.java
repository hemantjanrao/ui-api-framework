package org.hello.test.api.booking;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hello.core.framework.annotation.Groups;
import org.hello.core.framework.annotation.TestID;
import org.hello.core.framework.base.BaseApiTest;
import org.hello.core.framework.helper.TestHelper;
import org.hello.api.client.BookingServer;
import org.hello.api.entity.BookingModal;
import org.hello.api.entity.response.BookingListResponse;
import org.hello.api.entity.response.BookingResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Epic("Api Automation Task")
public class BookingTest extends BaseApiTest {

    private BookingServer bookingServer;

    @BeforeClass
    public void beforeBookingClass(){
        bookingServer = new BookingServer();
    }

    @Story("Bookings API")
    @Test(groups={Groups.CATEGORY_SANITY},description = "Verify getting all bookings")
    @Description("Verify getting all bookings")
    @TestID("36048093-9120-4e7d-87ec-9fd628736aa2")
    public void verifyGettingBookings(){
        Response res = bookingServer.getBooking();
        Assert.assertEquals(res.statusCode(),200, "Response status code is not as expected");
        BookingListResponse bookings = TestHelper.deserializeJson(res, BookingListResponse.class);
        Assert.assertTrue(bookings.getBookingModalList().size()>2,"Atleast 2 bookings should be present");
    }

    @Story("Bookings API")
    @Test(groups={Groups.CATEGORY_SANITY},description = "Verify creating booking")
    @Description("Verify creating booking")
    @TestID("4414f830-6172-47e3-8344-266fd64c6107")
    public void verifyCreatingBooking(){

        int roomid = RandomUtils.nextInt(1,200);
        String firstName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String lastName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String email = "auto_"+RandomStringUtils.randomAlphabetic(5)+"@test.com";

        // Createing check-in and checkout date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(2);

        //Creating Booking model
        BookingModal booking = new BookingModal();
        booking.setRoomId(roomid);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setDepositPaid(true);
        HashMap<String,String> dates = new HashMap<>();
        dates.put("checkin",today.toString());
        dates.put("checkout",tomorrow.toString());
        booking.setBookinDates(dates);

        //Response res = bookingServer.createBooking(booking);
        Response res = bookingServer.createBooking(TestHelper.serializeToJson(booking));
        Assert.assertEquals(res.statusCode(),201, "Response status code is not as expected");

        //Verify the correct booking by checking the roomID
        BookingResponse bookingResponse = TestHelper.deserializeJson(res,BookingResponse.class);
        Assert.assertEquals(bookingResponse.getBooking().getRoomId(),booking.getRoomId(),"Roomid is not as expected");
    }


    @Story("Bookings API")
    @Test(groups={Groups.CATEGORY_SANITY},description = "Verify getting the booking by id")
    @Description("Verify getting the booking by id")
    @TestID("8bfd7eab-64e2-4539-b05d-c1f6758e3617")
    public void verifyGettingBookingById() {

        //Variables used
        int roomid = RandomUtils.nextInt(1,200);
        String firstName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String lastName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String email = "auto_"+RandomStringUtils.randomAlphabetic(5)+"@test.com";

        // Creating check-in and checkout date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(2);

        // Pre-requisite - Creating Booking model
        BookingModal booking = new BookingModal();
        booking.setRoomId(roomid);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setDepositPaid(true);
        HashMap<String,String> dates = new HashMap<>();
        dates.put("checkin",today.toString());
        dates.put("checkout",tomorrow.toString());
        booking.setBookinDates(dates);

        Response res = bookingServer.createBooking(TestHelper.serializeToJson(booking));
        Assert.assertEquals(res.statusCode(),201, "Response status code is not as expected");

        //Verify the correct booking by checking the roomID
        BookingResponse bookingResponse = TestHelper.deserializeJson(res,BookingResponse.class);
        Assert.assertEquals(bookingResponse.getBooking().getRoomId(),booking.getRoomId(),"Roomid is not as expected");

        //Now verify getting the booking by id
        int bookingID = bookingResponse.getBooking().getBookingId();
        res = bookingServer.getBooking(bookingID);
        Assert.assertEquals(res.statusCode(),200, "Response status code is not as expected");
        BookingModal bookingResult = TestHelper.deserializeJson(res,BookingModal.class);

        //Verify the correct booking
        Assert.assertEquals(bookingResult.getRoomId(),booking.getRoomId(),"RoomId is not as expected");
        Assert.assertEquals(bookingResult.getBookingId(),bookingID,"BookingId is not as expected");
        Assert.assertEquals(bookingResult.getLastName(),booking.getLastName(),"LastName is not as expected");
        Assert.assertEquals(bookingResult.getFirstName(),booking.getFirstName(),"FistName is not as expected");
    }


    @Story("Bookings API")
    @Test(groups={Groups.CATEGORY_CONFIDENCE},description = "Verify creating booking using invalid booking date")
    @Description("Verify creating booking using invalid booking date")
    @TestID("3e8d6f4c-2450-46ed-be5a-b22e3857b9db")
    public void verifyCreatingBookingInvalidBookingDate() {

        //Variables used
        int roomid = RandomUtils.nextInt(1,200);
        String firstName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String lastName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String email = "auto_"+RandomStringUtils.randomAlphabetic(5)+"@test.com";

        // Creating check-in and checkout date with checkout date less than check-in date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime past = today.minusDays(2);

        //Creating Booking model
        BookingModal booking = new BookingModal();
        booking.setRoomId(roomid);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setDepositPaid(true);
        HashMap<String,String> dates = new HashMap<>();
        dates.put("checkin",today.toString());
        dates.put("checkout",past.toString());
        booking.setBookinDates(dates);

        Response res = bookingServer.createBooking(TestHelper.serializeToJson(booking));
        Assert.assertEquals(res.statusCode(),409, "Response status code is not as expected");

    }

    @Story("Bookings API")
    @Test(groups={Groups.CATEGORY_CONFIDENCE},description = "Verify creating booking using same room twice")
    @Description("Verify creating booking using same room twice")
    @TestID("3e8d6f4c-2450-46ed-be5a-b22e3857b9db")
    public void verifyBookingSameRoomTwice() {

        //Variables used
        int roomid = RandomUtils.nextInt(1,200);
        String firstName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String lastName = "auto_"+RandomStringUtils.randomAlphabetic(5);
        String email = "auto_"+RandomStringUtils.randomAlphabetic(5)+"@test.com";

        // Creating check-in and checkout date with checkout date less than check-in date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(2);

        //Creating Booking model
        BookingModal booking = new BookingModal();
        booking.setRoomId(roomid);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setEmail(email);
        booking.setDepositPaid(true);
        HashMap<String,String> dates = new HashMap<>();
        dates.put("checkin",today.toString());
        dates.put("checkout",tomorrow.toString());
        booking.setBookinDates(dates);

        Response res = bookingServer.createBooking(TestHelper.serializeToJson(booking));
        Assert.assertEquals(res.statusCode(),201, "Response status code is not as expected");

        //Again create the booking for same room
        res = bookingServer.createBooking(TestHelper.serializeToJson(booking));
        Assert.assertEquals(res.statusCode(),409, "Response status code is not as expected");
    }
}
