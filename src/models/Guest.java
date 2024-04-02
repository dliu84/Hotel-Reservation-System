package models;

public class Guest {
	private static int lastAssignedID = 2000; // Static variable to keep track of the last assigned ID
    private int guestID; // Instance variable to store the generated ID
    private String title;
    private String firstName;
    private String lastName;
    private String address;
    private int phone;
    private String email;

    // Constructor
    public Guest(String title, String firstName, String lastName, String address, int phone, String email) {
        this.guestID = ++lastAssignedID; // Increment lastAssignedID and assign it to guestID
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and setters for all attributes (excluding guestID as it's generated automatically)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for guestID (no setter as it's generated automatically)
    public int getGuestID() {
        return guestID;
    }

}
