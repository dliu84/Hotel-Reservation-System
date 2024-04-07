package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoomManager {
    private static RoomManager instance;
    private List<Room> rooms;

    private RoomManager() {
        rooms = generateRooms(); // Initialize the list of rooms
    }

    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }
        return instance;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    // Method to add a new room
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Method to delete a room
    public void deleteRoom(Room room) {
        rooms.remove(room);
    }
    
    // Method to delete a room with a specific type
//    public void deleteRoomByType(RoomTypeName type) {
//        Iterator<Room> iterator = rooms.iterator();
//        while (iterator.hasNext()) {
//            Room room = iterator.next();
//            if (room.getRoomType() == type) {
//                iterator.remove();
//            }
//        }
//    }
    
 // Method to delete the first occurrence of a room with a specific type
    public void deleteRoomByType(RoomTypeName type) {
        for (Room room : rooms) {
            if (room.getRoomType() == type) {
                rooms.remove(room);
                return; // Exit the method after removing the first occurrence
            }
        }
    }
    
    // Method to generate fake rooms
    private List<Room> generateRooms() {
        List<Room> rooms = new ArrayList<>();

        // Creating some fake rooms
	    rooms.add(new Room(1, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(2, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(3, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(4, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(5, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(6, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(7, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(8, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(9, 100.00, RoomTypeName.SINGLE));
	    rooms.add(new Room(10, 100.00, RoomTypeName.SINGLE));
	    
	    rooms.add(new Room(11, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(12, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(13, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(14, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(15, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(16, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(17, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(18, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(19, 150.00, RoomTypeName.DOUBLE));
	    rooms.add(new Room(20, 150.00, RoomTypeName.DOUBLE));
	    
	    rooms.add(new Room(21, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(22, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(23, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(24, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(25, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(26, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(27, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(28, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(29, 200.00, RoomTypeName.DELUX));
	    rooms.add(new Room(30, 200.00, RoomTypeName.DELUX));
	    
	    rooms.add(new Room(31, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(32, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(33, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(34, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(35, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(36, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(37, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(38, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(39, 300.00, RoomTypeName.PENTHOUSE));
	    rooms.add(new Room(40, 300.00, RoomTypeName.PENTHOUSE));

        return rooms;
    }
}

