package com.hotel.ui;

import com.hotel.service.BillingService;

import java.util.Scanner;

public class BillingMenu {

    private BillingService billingService = new BillingService();
    private Scanner sc = new Scanner(System.in);

    // ─── Main Billing Menu ──────────────────────────────────
    public void show() {
        int choice;
        do {
            System.out.println("\n====== Billing Management ======");
            System.out.println("1. View Bill");
            System.out.println("2. Pay Bill");
            System.out.println("3. Add Extra Charges");
            System.out.println("4. View All Unpaid Bills");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewBill();
                case 2 -> payBill();
                case 3 -> addExtraCharges();
                case 4 -> viewUnpaidBills();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ─── 1. View Bill ───────────────────────────────────────
    private void viewBill() {
        System.out.println("\n--- View Bill ---");
        System.out.print("Enter Reservation ID : ");
        int reservationId = sc.nextInt();

        billingService.viewBill(reservationId);
    }

    // ─── 2. Pay Bill ────────────────────────────────────────
    private void payBill() {
        System.out.println("\n--- Pay Bill ---");
        System.out.print("Enter Reservation ID : ");
        int reservationId = sc.nextInt();

        if (billingService.payBill(reservationId)) {
            System.out.println("Payment done successfully!");
        } else {
            System.out.println("Payment failed.");
        }
    }

    // ─── 3. Add Extra Charges ───────────────────────────────
    private void addExtraCharges() {
        System.out.println("\n--- Add Extra Charges ---");
        System.out.print("Enter Reservation ID   : ");
        int reservationId = sc.nextInt();

        System.out.print("Enter Extra Amount Rs. : ");
        double amount = sc.nextDouble();

        billingService.addExtraCharges(reservationId, amount);
    }

    // ─── 4. View Unpaid Bills ───────────────────────────────
    private void viewUnpaidBills() {
        billingService.viewUnpaidBills();
    }
}