package com.hotel.model;

public class Bill {

    private int billId;
    private int reservationId;
    private double roomCharges;
    private double extraCharges;
    private double totalAmount;
    private String paymentStatus;   // Pending, Paid
    private String paymentDate;

    // ─── Constructors ───────────────────────────────────────

    public Bill() {}

    public Bill(int billId, int reservationId, double roomCharges,
                double extraCharges, double totalAmount,
                String paymentStatus, String paymentDate) {
        this.billId        = billId;
        this.reservationId = reservationId;
        this.roomCharges   = roomCharges;
        this.extraCharges  = extraCharges;
        this.totalAmount   = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDate   = paymentDate;
    }

    // ─── Getters & Setters ──────────────────────────────────

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public double getRoomCharges() { return roomCharges; }
    public void setRoomCharges(double roomCharges) { this.roomCharges = roomCharges; }

    public double getExtraCharges() { return extraCharges; }
    public void setExtraCharges(double extraCharges) { this.extraCharges = extraCharges; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    // ─── toString ───────────────────────────────────────────

    @Override
    public String toString() {
        return "+----------------------------------+" +
             "\n| Bill ID        : " + billId +
             "\n| Reservation ID : " + reservationId +
             "\n| Room Charges   : Rs." + roomCharges +
             "\n| Extra Charges  : Rs." + extraCharges +
             "\n| Total Amount   : Rs." + totalAmount +
             "\n| Payment Status : " + paymentStatus +
             "\n| Payment Date   : " + paymentDate +
             "\n+----------------------------------+";
    }
}