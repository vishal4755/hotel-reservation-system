package com.hotel.dao;

import com.hotel.util.DBConnection;

import java.sql.*;

public class CheckInDAO {

    // ─── 1. Record Check-In ─────────────────────────────────
    public boolean recordCheckIn(int reservationId) {
        String sql = "INSERT INTO check_ins (reservation_id, actual_checkin) " +
                     "VALUES (?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            boolean result = ps.executeUpdate() > 0;

            if (result) {
                System.out.println("Check-in recorded successfully!");
            }
            return result;

        } catch (SQLException e) {
            System.out.println("Error recording check-in: " + e.getMessage());
            return false;
        }
    }

    // ─── 2. Record Check-Out ────────────────────────────────
    public boolean recordCheckOut(int reservationId) {
        String sqlCheckOut = "UPDATE check_ins SET actual_checkout = NOW() " +
                             "WHERE reservation_id = ?";

        String sqlRoom = "UPDATE rooms SET status = 'Available' " +
                         "WHERE room_id = (" +
                         "  SELECT room_id FROM reservations " +
                         "  WHERE reservation_id = ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Transaction start

            // Step 1 - Update checkout time
            PreparedStatement ps1 = conn.prepareStatement(sqlCheckOut);
            ps1.setInt(1, reservationId);
            ps1.executeUpdate();

            // Step 2 - Free the room
            PreparedStatement ps2 = conn.prepareStatement(sqlRoom);
            ps2.setInt(1, reservationId);
            ps2.executeUpdate();

            conn.commit();  // Transaction commit
            System.out.println("Check-out recorded successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Check-out failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
                System.out.println("Transaction rolled back.");
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            return false;

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Connection close error: " + e.getMessage());
            }
        }
    }

    // ─── 3. Get Check-In Details ────────────────────────────
    public void printCheckInDetails(int reservationId) {
        String sql = "SELECT * FROM check_ins WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n===== Check-In Details =====");
                System.out.println("CheckIn ID      : " + rs.getInt("checkin_id"));
                System.out.println("Reservation ID  : " + rs.getInt("reservation_id"));
                System.out.println("Actual Check-In : " + rs.getTimestamp("actual_checkin"));
                System.out.println("Actual Checkout : " + rs.getTimestamp("actual_checkout"));
                System.out.println("============================");
            } else {
                System.out.println("No check-in record found.");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching check-in details: " + e.getMessage());
        }
    }
}