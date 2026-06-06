package com.hotel.model;

public class Room {

    private int roomId;
    private String roomNumber;
    private String roomType;      // Single, Double, Suite
    private double pricePerNight;
    private int floorNumber;
    private String status;        // Available, Booked, Maintenance
    private int capacity;

    // ─── Constructors ───────────────────────────────────────

    public Room() {}

    public Room(int roomId, String roomNumber, String roomType,
                double pricePerNight, int floorNumber,
                String status, int capacity) {
        this.roomId       = roomId;
        this.roomNumber   = roomNumber;
        this.roomType     = roomType;
        this.pricePerNight = pricePerNight;
        this.floorNumber  = floorNumber;
        this.status       = status;
        this.capacity     = capacity;
    }

    // ─── Getters & Setters ──────────────────────────────────

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // ─── toString ───────────────────────────────────────────

    @Override
    public String toString() {
        return "+----------------------------------+" +
             "\n| Room No  : " + roomNumber +
             "\n| Type     : " + roomType +
             "\n| Price    : Rs." + pricePerNight + " / night" +
             "\n| Floor    : " + floorNumber +
             "\n| Capacity : " + capacity + " person(s)" +
             "\n| Status   : " + status +
             "\n+----------------------------------+";
    }
}