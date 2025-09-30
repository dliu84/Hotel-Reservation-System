package models;

public class Room {
    private int roomID;
    private double price;
    private RoomTypeName roomType;

    // Constructor
    public Room(int roomID, double price, RoomTypeName roomType) {
        this.roomID = roomID;
        this.price = price;
        this.roomType = roomType;
    }

    // Getters and Setters
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomTypeName getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeName roomType) {
        this.roomType = roomType;
    }
}
