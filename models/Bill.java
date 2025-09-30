package models;

public class Bill {
	private static int lastAssignedID = 1000; // Static variable to keep track of the last assigned ID
    private int billID; // Instance variable to store the generated ID
    private double amount;

    // Constructor
    public Bill(double amount) {
        this.billID = ++lastAssignedID; // Increment lastAssignedID and assign it to billID
        this.amount = amount;
    }

    // Getters and setters for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter for billID (no setter as it's generated automatically)
    public int getBillID() {
        return billID;
    }
}
