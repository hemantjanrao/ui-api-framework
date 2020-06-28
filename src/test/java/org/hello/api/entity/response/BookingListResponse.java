package org.hello.api.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hello.api.entity.BookingModal;

import java.util.List;

public class BookingListResponse {

    @Expose
    @SerializedName("bookings")
    public List<BookingModal> bookingModalList;

    public List<BookingModal> getBookingModalList() {
        return bookingModalList;
    }

    public void setBookingModalList(List<BookingModal> bookingModalList) {
        this.bookingModalList = bookingModalList;
    }
}
