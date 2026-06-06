package com.hotel.dao;

import com.hotel.model.Reservation;
import com.hotel.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // ─── 1. Book Room ───────────────────────────────────────
    public boolean bookRoom(Reservation reservation) {
        String sqlReservation = "INSERT INTO reservations (guest_id, room_id, " +
                                "check_in_date, check_out_date, status) " +
                                "VALUES (?, ?, ?, ?, 'Confirmed')";

        String sqlUpdateRoom = "UPDATE rooms SET status = 'Booked' " +
                               "WHERE room_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Transaction start

            // Step 1 - Insert reservation
            PreparedStatement ps1 = conn.prepareStatement(sqlReservation);
            ps1.setInt(1, reservation.getGuestId());
            ps1.setInt(2, reservation.getRoomId());
            ps1.setString(3, reservation.getCheckInDate().toString());
            ps1.setString(4, reservation.getCheckOutDate().toString());
            ps1.executeUpdate();

            // Step 2 - Update room status to Booked
            PreparedStatement ps2 = conn.prepareStatement(sqlUpdateRoom);
            ps2.setInt(1, reservation.getRoomId());
            ps2.executeUpdate();

            conn.commit();  // Transaction commit
            System.out.println("Room booked successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Booking failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();  // Rollback on error
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

    // ─── 2. Get All Reservations ────────────────────────────
    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }
        return list;
    }

    // ─── 3. Get Reservation by ID ───────────────────────────
    public Reservation getReservationById(int reservationId) {
        String sql = "SELECT * FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservation: " + e.getMessage());
        }
        return null;
    }

    // ─── 4. Get Reservations by Guest ───────────────────────
    public List<Reservation> getReservationsByGuest(int guestId) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, guestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guest reservations: " + e.getMessage());
        }
        return list;
    }

    // ─── 5. Cancel Reservation ──────────────────────────────
    public boolean cancelReservation(int reservationId, int roomId) {
        String sqlCancel = "UPDATE reservations SET status = 'Cancelled' " +
                           "WHERE reservation_id = ?";

        String sqlRoom = "UPDATE rooms SET status = 'Available' " +
                         "WHERE room_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Transaction start

            PreparedStatement ps1 = conn.prepareStatement(sqlCancel);
            ps1.setInt(1, reservationId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(sqlRoom);
            ps2.setInt(1, roomId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Reservation cancelled successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
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

    // ─── Helper: ResultSet to Reservation object ────────────
    private Reservation mapRow(ResultSet rs) throws SQLException {
        return new Reservation(
            rs.getInt("reservation_id"),
            rs.getInt("guest_id"),
            rs.getInt("room_id"),
            rs.getDate("check_in_date").toLocalDate(),
            rs.getDate("check_out_date").toLocalDate(),
            rs.getString("status"),
            rs.getString("booked_on")
        );
    }
}