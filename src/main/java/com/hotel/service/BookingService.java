package com.hotel.service;

import com.hotel.dao.BillingDAO;
import com.hotel.dao.CheckInDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.ReservationDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Bill;
import com.hotel.model.Reservation;
import com.hotel.model.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingService {

    private RoomDAO roomDAO               = new RoomDAO();
    private GuestDAO guestDAO             = new GuestDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private BillingDAO billingDAO         = new BillingDAO();
    private CheckInDAO checkInDAO         = new CheckInDAO();

    // ─── 1. Show Available Rooms ────────────────────────────
    public void showAvailableRooms(String checkIn, String checkOut) {
        List<Room> rooms = roomDAO.getAvailableRooms(checkIn, checkOut);

        if (rooms.isEmpty()) {
            System.out.println("No rooms available for selected dates.");
        } else {
            System.out.println("\n===== Available Rooms =====");
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    // ─── 2. Book a Room ─────────────────────────────────────
    public boolean bookRoom(int guestId, int roomId,
                            String checkIn, String checkOut) {
        LocalDate inDate  = LocalDate.parse(checkIn);
        LocalDate outDate = LocalDate.parse(checkOut);

        if (!outDate.isAfter(inDate)) {
            System.out.println("Check-out date must be after check-in date!");
            return false;
        }

        Room room = roomDAO.getRoomById(roomId);
        if (room == null) {
            System.out.println("Room not found!");
            return false;
        }

        if (!room.getStatus().equals("Available")) {
            System.out.println("Room is not available!");
            return false;
        }

        Reservation reservation = new Reservation();
        reservation.setGuestId(guestId);
        reservation.setRoomId(roomId);
        reservation.setCheckInDate(inDate);
        reservation.setCheckOutDate(outDate);
        reservation.setStatus("Confirmed");

        return reservationDAO.bookRoom(reservation);
    }

    // ─── 3. Cancel Booking ──────────────────────────────────
    public boolean cancelBooking(int reservationId) {
        Reservation r = reservationDAO.getReservationById(reservationId);

        if (r == null) {
            System.out.println("Reservation not found!");
            return false;
        }

        if (r.getStatus().equals("Cancelled")) {
            System.out.println("Reservation is already cancelled!");
            return false;
        }

        return reservationDAO.cancelReservation(reservationId, r.getRoomId());
    }

    // ─── 4. Check-In Guest ──────────────────────────────────
    public boolean checkIn(int reservationId) {
        Reservation r = reservationDAO.getReservationById(reservationId);

        if (r == null) {
            System.out.println("Reservation not found!");
            return false;
        }

        if (!r.getStatus().equals("Confirmed")) {
            System.out.println("Reservation is not confirmed!");
            return false;
        }

        return checkInDAO.recordCheckIn(reservationId);
    }

    // ─── 5. Check-Out Guest + Auto Generate Bill ────────────
    public boolean checkOut(int reservationId) {
        Reservation r = reservationDAO.getReservationById(reservationId);

        if (r == null) {
            System.out.println("Reservation not found!");
            return false;
        }

        Room room    = roomDAO.getRoomById(r.getRoomId());
        long nights  = ChronoUnit.DAYS.between(r.getCheckInDate(), r.getCheckOutDate());
        double roomCharges = nights * room.getPricePerNight();

        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setRoomCharges(roomCharges);
        bill.setExtraCharges(0);
        bill.setTotalAmount(roomCharges);
        bill.setPaymentStatus("Pending");

        billingDAO.generateBill(bill);

        boolean result = checkInDAO.recordCheckOut(reservationId);

        if (result) {
            System.out.println("\n===== Bill Generated =====");
            System.out.println("Nights stayed  : " + nights);
            System.out.println("Room charges   : Rs." + roomCharges);
            System.out.println("Total amount   : Rs." + roomCharges);
            System.out.println("Payment Status : Pending");
            System.out.println("==========================");
        }

        return result;
    }

    // ─── 6. View Guest Booking History ──────────────────────
    public void viewBookingHistory(int guestId) {
        List<Reservation> list = reservationDAO.getReservationsByGuest(guestId);

        if (list.isEmpty()) {
            System.out.println("No bookings found for this guest.");
        } else {
            System.out.println("\n===== Booking History =====");
            for (Reservation r : list) {
                System.out.println(r);
            }
        }
    }
}