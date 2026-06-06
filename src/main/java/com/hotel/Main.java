package com.hotel;

import com.hotel.ui.MainMenu;
import com.hotel.util.DBConnection;

public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   HOTEL RESERVATION SYSTEM       ║");
        System.out.println("║   Starting application...        ║");
        System.out.println("╚══════════════════════════════════╝");

        try {
            // Test DB connection on startup
            DBConnection.getConnection();
            System.out.println("Database connected successfully!\n");

            // Launch main menu
            MainMenu menu = new MainMenu();
            menu.show();

        } catch (Exception e) {
            System.out.println("Failed to connect to database!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please check db.properties file.");
        } finally {
            // Shutdown connection pool on exit
            DBConnection.shutdown();
            System.out.println("Application closed.");
        }
    }
}