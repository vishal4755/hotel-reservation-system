package com.hotel.dao;

import com.hotel.model.Bill;
import com.hotel.util.DBConnection;

import java.sql.*;

public class BillingDAO {

    // ─── 1. Generate Bill ───────────────────────────────────
    public boolean generateBill(Bill bill) {
        String sql = "INSERT INTO billing (reservation_id, room_charges, " +
                     "extra_charges, total_amount, payment_status) " +
                     "VALUES (?, ?, ?, ?, 'Pending')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bill.getReservationId());
            ps.setDouble(2, bill.getRoomCharges());
            ps.setDouble(3, bill.getExtraCharges());
            ps.setDouble(4, bill.getTotalAmount());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error generating bill: " + e.getMessage());
            return false;
        }
    }

    // ─── 2. Get Bill by Reservation ID ──────────────────────
    public Bill getBillByReservationId(int reservationId) {
        String sql = "SELECT * FROM billing WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bill: " + e.getMessage());
        }
        return null;
    }

    // ─── 3. Mark Bill as Paid ───────────────────────────────
    public boolean markAsPaid(int reservationId) {
        String sql = "UPDATE billing SET payment_status = 'Paid', " +
                     "payment_date = NOW() " +
                     "WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error marking bill as paid: " + e.getMessage());
            return false;
        }
    }

    // ─── 4. Add Extra Charges ───────────────────────────────
    public boolean addExtraCharges(int reservationId, double extraAmount) {
        String sql = "UPDATE billing SET " +
                     "extra_charges = extra_charges + ?, " +
                     "total_amount  = room_charges + extra_charges + ? " +
                     "WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, extraAmount);
            ps.setDouble(2, extraAmount);
            ps.setInt(3, reservationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding extra charges: " + e.getMessage());
            return false;
        }
    }

    // ─── 5. Get All Unpaid Bills ────────────────────────────
    public void printUnpaidBills() {
        String sql = "SELECT * FROM billing WHERE payment_status = 'Pending'";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n===== Unpaid Bills =====");
            while (rs.next()) {
                System.out.println(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching unpaid bills: " + e.getMessage());
        }
    }

    // ─── Helper: ResultSet to Bill object ───────────────────
    private Bill mapRow(ResultSet rs) throws SQLException {
        return new Bill(
            rs.getInt("bill_id"),
            rs.getInt("reservation_id"),
            rs.getDouble("room_charges"),
            rs.getDouble("extra_charges"),
            rs.getDouble("total_amount"),
            rs.getString("payment_status"),
            rs.getString("payment_date")
        );
    }
}