package com.hotel.dao;

import com.hotel.model.Room;
import com.hotel.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // ─── 1. Add New Room ────────────────────────────────────
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type, price_per_night, " +
                     "floor_number, status, capacity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPricePerNight());
            ps.setInt(4, room.getFloorNumber());
            ps.setString(5, room.getStatus());
            ps.setInt(6, room.getCapacity());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    // ─── 2. Get All Rooms ───────────────────────────────────
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching rooms: " + e.getMessage());
        }
        return rooms;
    }

    // ─── 3. Get Available Rooms by Date ─────────────────────
    public List<Room> getAvailableRooms(String checkIn, String checkOut) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = 'Available' " +
                     "AND room_id NOT IN (" +
                     "  SELECT room_id FROM reservations " +
                     "  WHERE status = 'Confirmed' " +
                     "  AND check_in_date < ? AND check_out_date > ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, checkOut);
            ps.setString(2, checkIn);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
        }
        return rooms;
    }

    // ─── 4. Get Room by ID ──────────────────────────────────
    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching room: " + e.getMessage());
        }
        return null;
    }

    // ─── 5. Update Room Status ──────────────────────────────
    public boolean updateStatus(int roomId, String status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, roomId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating room status: " + e.getMessage());
            return false;
        }
    }

    // ─── 6. Delete Room ─────────────────────────────────────
    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    // ─── Helper: ResultSet to Room object ───────────────────
    private Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
            rs.getInt("room_id"),
            rs.getString("room_number"),
            rs.getString("room_type"),
            rs.getDouble("price_per_night"),
            rs.getInt("floor_number"),
            rs.getString("status"),
            rs.getInt("capacity")
        );
    }
}