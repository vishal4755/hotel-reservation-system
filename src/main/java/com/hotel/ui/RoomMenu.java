package com.hotel.ui;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

import java.util.List;
import java.util.Scanner;

public class RoomMenu {

    private RoomDAO roomDAO = new RoomDAO();
    private Scanner sc      = new Scanner(System.in);

    // ─── Main Room Menu ─────────────────────────────────────
    public void show() {
        int choice;
        do {
            System.out.println("\n====== Room Management ======");
            System.out.println("1. Add New Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Update Room Status");
            System.out.println("4. Delete Room");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> viewAllRooms();
                case 3 -> updateRoomStatus();
                case 4 -> deleteRoom();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ─── 1. Add New Room ────────────────────────────────────
    private void addRoom() {
        System.out.println("\n--- Add New Room ---");

        System.out.print("Room Number   : ");
        String roomNumber = sc.next();

        System.out.println("Room Type     : ");
        System.out.println("  1. Single");
        System.out.println("  2. Double");
        System.out.println("  3. Suite");
        System.out.print("Enter choice  : ");
        int typeChoice = sc.nextInt();
        String roomType = switch (typeChoice) {
            case 1 -> "Single";
            case 2 -> "Double";
            case 3 -> "Suite";
            default -> "Single";
        };

        System.out.print("Price/Night   : Rs.");
        double price = sc.nextDouble();

        System.out.print("Floor Number  : ");
        int floor = sc.nextInt();

        System.out.print("Capacity      : ");
        int capacity = sc.nextInt();

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setPricePerNight(price);
        room.setFloorNumber(floor);
        room.setStatus("Available");
        room.setCapacity(capacity);

        if (roomDAO.addRoom(room)) {
            System.out.println("Room added successfully!");
        } else {
            System.out.println("Failed to add room.");
        }
    }

    // ─── 2. View All Rooms ──────────────────────────────────
    private void viewAllRooms() {
        List<Room> rooms = roomDAO.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("\n===== All Rooms =====");
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    // ─── 3. Update Room Status ──────────────────────────────
    private void updateRoomStatus() {
        System.out.println("\n--- Update Room Status ---");
        System.out.print("Enter Room ID : ");
        int roomId = sc.nextInt();

        System.out.println("New Status    :");
        System.out.println("  1. Available");
        System.out.println("  2. Booked");
        System.out.println("  3. Maintenance");
        System.out.print("Enter choice  : ");
        int statusChoice = sc.nextInt();
        String status = switch (statusChoice) {
            case 1 -> "Available";
            case 2 -> "Booked";
            case 3 -> "Maintenance";
            default -> "Available";
        };

        if (roomDAO.updateStatus(roomId, status)) {
            System.out.println("Room status updated to: " + status);
        } else {
            System.out.println("Failed to update room status.");
        }
    }

    // ─── 4. Delete Room ─────────────────────────────────────
    private void deleteRoom() {
        System.out.println("\n--- Delete Room ---");
        System.out.print("Enter Room ID : ");
        int roomId = sc.nextInt();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("yes")) {
            if (roomDAO.deleteRoom(roomId)) {
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Failed to delete room.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}