package com.hotel.model;

import java.time.LocalDate;

public class Reservation {

    private int reservationId;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;         // Pending, Confirmed, Cancelled
    private String bookedOn;

    // ─── Constructors ───────────────────────────────────────

    public Reservation() {}

    public Reservation(int reservationId, int guestId, int roomId,
                       LocalDate checkInDate, LocalDate checkOutDate,
                       String status, String bookedOn) {
        this.reservationId = reservationId;
        this.guestId       = guestId;
        this.roomId        = roomId;
        this.checkInDate   = checkInDate;
        this.checkOutDate  = checkOutDate;
        this.status        = status;
        this.bookedOn      = bookedOn;
    }

    // ─── Getters & Setters ──────────────────────────────────

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBookedOn() { return bookedOn; }
    public void setBookedOn(String bookedOn) { this.bookedOn = bookedOn; }

    // ─── toString ───────────────────────────────────────────

    @Override
    public String toString() {
        return "+----------------------------------+" +
             "\n| Reservation ID : " + reservationId +
             "\n| Guest ID       : " + guestId +
             "\n| Room ID        : " + roomId +
             "\n| Check In       : " + checkInDate +
             "\n| Check Out      : " + checkOutDate +
             "\n| Status         : " + status +
             "\n| Booked On      : " + bookedOn +
             "\n+----------------------------------+";
    }
}