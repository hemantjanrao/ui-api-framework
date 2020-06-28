package org.hello.api.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hello.api.entity.BookingModal;

public class BookingResponse {

    @Expose
    @SerializedName("bookingid")
    private String bookingId;

    @Expose
    @SerializedName("booking")
    private BookingModal booking;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public BookingModal getBooking() {
        return booking;
    }

    public void setBooking(BookingModal booking) {
        this.booking = booking;
    }
}
