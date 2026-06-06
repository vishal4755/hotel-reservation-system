package com.hotel.ui;

import com.hotel.service.BookingService;

import java.util.Scanner;

public class BookingMenu {

    private BookingService bookingService = new BookingService();
    private Scanner sc = new Scanner(System.in);

    // ─── Main Booking Menu ──────────────────────────────────
    public void show() {
        int choice;
        do {
            System.out.println("\n====== Booking Management ======");
            System.out.println("1. Check Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Check-In Guest");
            System.out.println("5. Check-Out Guest");
            System.out.println("6. View Guest Booking History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> checkAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> checkIn();
                case 5 -> checkOut();
                case 6 -> viewHistory();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ─── 1. Check Available Rooms ───────────────────────────
    private void checkAvailableRooms() {
        System.out.println("\n--- Check Available Rooms ---");
        System.out.print("Check-In  Date (YYYY-MM-DD) : ");
        String checkIn = sc.next();
        System.out.print("Check-Out Date (YYYY-MM-DD) : ");
        String checkOut = sc.next();

        bookingService.showAvailableRooms(checkIn, checkOut);
    }

    // ─── 2. Book a Room ─────────────────────────────────────
    private void bookRoom() {
        System.out.println("\n--- Book a Room ---");

        System.out.print("Guest ID                    : ");
        int guestId = sc.nextInt();

        System.out.print("Room ID                     : ");
        int roomId = sc.nextInt();

        System.out.print("Check-In  Date (YYYY-MM-DD) : ");
        String checkIn = sc.next();

        System.out.print("Check-Out Date (YYYY-MM-DD) : ");
        String checkOut = sc.next();

        if (bookingService.bookRoom(guestId, roomId, checkIn, checkOut)) {
            System.out.println("Room booked successfully!");
        } else {
            System.out.println("Booking failed. Please try again.");
        }
    }

    // ─── 3. Cancel Booking ──────────────────────────────────
    private void cancelBooking() {
        System.out.println("\n--- Cancel Booking ---");
        System.out.print("Enter Reservation ID : ");
        int reservationId = sc.nextInt();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("yes")) {
            if (bookingService.cancelBooking(reservationId)) {
                System.out.println("Booking cancelled successfully!");
            } else {
                System.out.println("Cancellation failed.");
            }
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    // ─── 4. Check-In ────────────────────────────────────────
    private void checkIn() {
        System.out.println("\n--- Guest Check-In ---");
        System.out.print("Enter Reservation ID : ");
        int reservationId = sc.nextInt();

        if (bookingService.checkIn(reservationId)) {
            System.out.println("Guest checked in successfully!");
        } else {
            System.out.println("Check-in failed.");
        }
    }

    // ─── 5. Check-Out ───────────────────────────────────────
    private void checkOut() {
        System.out.println("\n--- Guest Check-Out ---");
        System.out.print("Enter Reservation ID : ");
        int reservationId = sc.nextInt();

        if (bookingService.checkOut(reservationId)) {
            System.out.println("Guest checked out successfully!");
        } else {
            System.out.println("Check-out failed.");
        }
    }

    // ─── 6. View Booking History ────────────────────────────
    private void viewHistory() {
        System.out.println("\n--- Guest Booking History ---");
        System.out.print("Enter Guest ID : ");
        int guestId = sc.nextInt();

        bookingService.viewBookingHistory(guestId);
    }
}