package com.hotel.model;

public class Guest {

    private int guestId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String idProofType;    // Aadhar, Passport, DrivingLicense
    private String idProofNumber;

    // ─── Constructors ───────────────────────────────────────

    public Guest() {}

    public Guest(int guestId, String fullName, String email,
                 String phone, String address,
                 String idProofType, String idProofNumber) {
        this.guestId       = guestId;
        this.fullName      = fullName;
        this.email         = email;
        this.phone         = phone;
        this.address       = address;
        this.idProofType   = idProofType;
        this.idProofNumber = idProofNumber;
    }

    // ─── Getters & Setters ──────────────────────────────────

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getIdProofType() { return idProofType; }
    public void setIdProofType(String idProofType) { this.idProofType = idProofType; }

    public String getIdProofNumber() { return idProofNumber; }
    public void setIdProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; }

    // ─── toString ───────────────────────────────────────────

    @Override
    public String toString() {
        return "+----------------------------------+" +
             "\n| Guest ID : " + guestId +
             "\n| Name     : " + fullName +
             "\n| Email    : " + email +
             "\n| Phone    : " + phone +
             "\n| Address  : " + address +
             "\n| ID Proof : " + idProofType + " - " + idProofNumber +
             "\n+----------------------------------+";
    }
}