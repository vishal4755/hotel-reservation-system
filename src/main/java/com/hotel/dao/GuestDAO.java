package com.hotel.dao;

import com.hotel.model.Guest;
import com.hotel.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    // ─── 1. Add New Guest ───────────────────────────────────
    public boolean addGuest(Guest guest) {
        String sql = "INSERT INTO guests (full_name, email, phone, " +
                     "address, id_proof_type, id_proof_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getPhone());
            ps.setString(4, guest.getAddress());
            ps.setString(5, guest.getIdProofType());
            ps.setString(6, guest.getIdProofNumber());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding guest: " + e.getMessage());
            return false;
        }
    }

    // ─── 2. Get All Guests ──────────────────────────────────
    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                guests.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guests: " + e.getMessage());
        }
        return guests;
    }

    // ─── 3. Get Guest by ID ─────────────────────────────────
    public Guest getGuestById(int guestId) {
        String sql = "SELECT * FROM guests WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, guestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guest: " + e.getMessage());
        }
        return null;
    }

    // ─── 4. Find Guest by Email ─────────────────────────────
    public Guest getGuestByEmail(String email) {
        String sql = "SELECT * FROM guests WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guest by email: " + e.getMessage());
        }
        return null;
    }

    // ─── 5. Search Guest by Name ────────────────────────────
    public List<Guest> searchByName(String name) {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests WHERE full_name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                guests.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error searching guest: " + e.getMessage());
        }
        return guests;
    }

    // ─── 6. Update Guest ────────────────────────────────────
    public boolean updateGuest(Guest guest) {
        String sql = "UPDATE guests SET full_name=?, email=?, phone=?, " +
                     "address=?, id_proof_type=?, id_proof_number=? " +
                     "WHERE guest_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getPhone());
            ps.setString(4, guest.getAddress());
            ps.setString(5, guest.getIdProofType());
            ps.setString(6, guest.getIdProofNumber());
            ps.setInt(7, guest.getGuestId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating guest: " + e.getMessage());
            return false;
        }
    }

    // ─── 7. Delete Guest ────────────────────────────────────
    public boolean deleteGuest(int guestId) {
        String sql = "DELETE FROM guests WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, guestId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
            return false;
        }
    }

    // ─── Helper: ResultSet to Guest object ──────────────────
    private Guest mapRow(ResultSet rs) throws SQLException {
        return new Guest(
            rs.getInt("guest_id"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getString("id_proof_type"),
            rs.getString("id_proof_number")
        );
    }
}