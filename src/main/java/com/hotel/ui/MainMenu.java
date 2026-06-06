package com.hotel.ui;

import com.hotel.dao.GuestDAO;
import com.hotel.model.Guest;
import com.hotel.ui.RoomMenu;
import com.hotel.ui.BookingMenu;
import com.hotel.ui.BillingMenu;
import java.util.List;
import java.util.Scanner;


public class MainMenu {

    private Scanner sc          = new Scanner(System.in);
    private RoomMenu roomMenu   = new RoomMenu();
    private BookingMenu bookingMenu = new BookingMenu();
    private BillingMenu billingMenu = new BillingMenu();
    private GuestDAO guestDAO   = new GuestDAO();

    // ─── Main Menu ──────────────────────────────────────────
    public void show() {
        int choice;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║   HOTEL RESERVATION SYSTEM       ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Room Management              ║");
            System.out.println("║  2. Guest Management             ║");
            System.out.println("║  3. Booking Management           ║");
            System.out.println("║  4. Billing Management           ║");
            System.out.println("║  0. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> roomMenu.show();
                case 2 -> guestMenu();
                case 3 -> bookingMenu.show();
                case 4 -> billingMenu.show();
                case 0 -> System.out.println("Thank you! Goodbye.");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ─── Guest Menu ─────────────────────────────────────────
    private void guestMenu() {
        int choice;
        do {
            System.out.println("\n====== Guest Management ======");
            System.out.println("1. Register New Guest");
            System.out.println("2. View All Guests");
            System.out.println("3. Search Guest by Name");
            System.out.println("4. Delete Guest");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> registerGuest();
                case 2 -> viewAllGuests();
                case 3 -> searchGuest();
                case 4 -> deleteGuest();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ─── 1. Register Guest ──────────────────────────────────
    private void registerGuest() {
        System.out.println("\n--- Register New Guest ---");
        sc.nextLine(); // clear buffer

        System.out.print("Full Name       : ");
        String name = sc.nextLine();

        System.out.print("Email           : ");
        String email = sc.nextLine();

        System.out.print("Phone           : ");
        String phone = sc.nextLine();

        System.out.print("Address         : ");
        String address = sc.nextLine();

        System.out.println("ID Proof Type   :");
        System.out.println("  1. Aadhar");
        System.out.println("  2. Passport");
        System.out.println("  3. Driving License");
        System.out.print("Enter choice    : ");
        int idChoice = sc.nextInt();
        String idType = switch (idChoice) {
            case 1 -> "Aadhar";
            case 2 -> "Passport";
            case 3 -> "Driving License";
            default -> "Aadhar";
        };
        sc.nextLine();

        System.out.print("ID Proof Number : ");
        String idNumber = sc.nextLine();

        Guest guest = new Guest();
        guest.setFullName(name);
        guest.setEmail(email);
        guest.setPhone(phone);
        guest.setAddress(address);
        guest.setIdProofType(idType);
        guest.setIdProofNumber(idNumber);

        if (guestDAO.addGuest(guest)) {
            System.out.println("Guest registered successfully!");
        } else {
            System.out.println("Failed to register guest.");
        }
    }

    // ─── 2. View All Guests ─────────────────────────────────
    private void viewAllGuests() {
        List<Guest> guests = guestDAO.getAllGuests();

        if (guests.isEmpty()) {
            System.out.println("No guests found.");
        } else {
            System.out.println("\n===== All Guests =====");
            for (Guest g : guests) {
                System.out.println(g);
            }
        }
    }

    // ─── 3. Search Guest by Name ────────────────────────────
    private void searchGuest() {
        System.out.println("\n--- Search Guest ---");
        System.out.print("Enter Name : ");
        String name = sc.next();

        List<Guest> guests = guestDAO.searchByName(name);
        if (guests.isEmpty()) {
            System.out.println("No guest found with name: " + name);
        } else {
            for (Guest g : guests) {
                System.out.println(g);
            }
        }
    }

    // ─── 4. Delete Guest ────────────────────────────────────
    private void deleteGuest() {
        System.out.println("\n--- Delete Guest ---");
        System.out.print("Enter Guest ID : ");
        int guestId = sc.nextInt();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("yes")) {
            if (guestDAO.deleteGuest(guestId)) {
                System.out.println("Guest deleted successfully!");
            } else {
                System.out.println("Failed to delete guest.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}