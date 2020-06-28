package org.hello.api.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class BookingModal {

    @Expose
    @SerializedName("bookingid")
    private int bookingId;

    @Expose
    @SerializedName("roomid")
    private int roomId;

    @Expose
    @SerializedName("firstname")
    private String firstName;

    @Expose
    @SerializedName("lastname")
    private String lastName;

    @Expose
    @SerializedName("depositpaid")
    private boolean depositPaid;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("bookingdates")
    private HashMap<String,String> bookinDates;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public Map<String,String> getBookinDates() {
        return bookinDates;
    }

    public void setBookinDates(HashMap<String,String> bookinDates) {
        this.bookinDates = bookinDates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
