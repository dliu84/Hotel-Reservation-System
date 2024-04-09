package models;

public class CurrentBooking {
	private int bookID;
	private String guestName;
	private double amount;
	private int days;
	
	public CurrentBooking(int bookID, String guestName, double amount, int days) {
		this.bookID = bookID;
		this.guestName = guestName;
		this.amount = amount;
		this.days = days;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	
}
