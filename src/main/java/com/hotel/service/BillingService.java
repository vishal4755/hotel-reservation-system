package com.hotel.service;

import com.hotel.dao.BillingDAO;
import com.hotel.model.Bill;

public class BillingService {

    private BillingDAO billingDAO = new BillingDAO();

    // ─── 1. View Bill ───────────────────────────────────────
    public void viewBill(int reservationId) {
        Bill bill = billingDAO.getBillByReservationId(reservationId);

        if (bill == null) {
            System.out.println("No bill found for Reservation ID: " + reservationId);
        } else {
            System.out.println("\n========== INVOICE ==========");
            System.out.println("Bill ID         : " + bill.getBillId());
            System.out.println("Reservation ID  : " + bill.getReservationId());
            System.out.println("Room Charges    : Rs." + bill.getRoomCharges());
            System.out.println("Extra Charges   : Rs." + bill.getExtraCharges());
            System.out.println("------------------------------");
            System.out.println("Total Amount    : Rs." + bill.getTotalAmount());
            System.out.println("Payment Status  : " + bill.getPaymentStatus());
            System.out.println("Payment Date    : " + bill.getPaymentDate());
            System.out.println("==============================");
        }
    }

    // ─── 2. Pay Bill ────────────────────────────────────────
    public boolean payBill(int reservationId) {
        Bill bill = billingDAO.getBillByReservationId(reservationId);

        if (bill == null) {
            System.out.println("No bill found for Reservation ID: " + reservationId);
            return false;
        }

        if (bill.getPaymentStatus().equals("Paid")) {
            System.out.println("Bill is already paid!");
            return false;
        }

        boolean result = billingDAO.markAsPaid(reservationId);

        if (result) {
            System.out.println("Payment successful! Bill marked as Paid.");
        }
        return result;
    }

    // ─── 3. Add Extra Charges ───────────────────────────────
    public boolean addExtraCharges(int reservationId, double amount) {
        if (amount <= 0) {
            System.out.println("Extra charges must be greater than 0!");
            return false;
        }

        boolean result = billingDAO.addExtraCharges(reservationId, amount);

        if (result) {
            System.out.println("Extra charges of Rs." + amount + " added successfully!");
            viewBill(reservationId);
        }
        return result;
    }

    // ─── 4. View All Unpaid Bills ───────────────────────────
    public void viewUnpaidBills() {
        System.out.println("\n===== All Unpaid Bills =====");
        billingDAO.printUnpaidBills();
    }
}