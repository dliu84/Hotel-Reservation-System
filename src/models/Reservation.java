package models;

import java.util.Date;
import java.util.List;

public class Reservation {
    private static int latestBookID = 3000;

    private int bookID;
    private Date bookDate;
    private Date checkInDate;
    private Date checkOutDate;
    private Guest guest;
    private List<Room> rooms;
    private Bill bill;

    // Constructor
    public Reservation(Date bookDate, Date checkInDate, Date checkOutDate, Guest guest, List<Room> rooms, Bill bill) {
        this.bookID = ++latestBookID;
        this.bookDate = bookDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guest = guest;
        this.rooms = rooms;
        this.bill = bill;
    }

    // Getters and setters
    public int getBookID() {
        return bookID;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
