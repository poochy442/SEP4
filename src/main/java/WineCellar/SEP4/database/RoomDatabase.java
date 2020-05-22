package WineCellar.SEP4.database;

import WineCellar.SEP4.resource.Room;

import java.util.LinkedList;
import java.util.List;

public class RoomDatabase {

    private List<Room> rooms;

    private static RoomDatabase instance = null;
    public static RoomDatabase getInstance() {
        if(instance == null)
            instance = new RoomDatabase();
        return instance;
    }

    private RoomDatabase(){
        rooms = new LinkedList<>();
    }

    // Return all rooms
    public List<Room> fetchRooms(){
        // TODO fetch rooms
        return rooms;
    }

    // Add room to database
    public void addRoom(Room room){
        rooms.add(room);
        System.out.println("Added room "+room.getName());
        System.out.println("Added room "+room.getName());
        System.out.println("Added room "+room.getName());
        System.out.println("Added room "+room.getName());
    }

    // Update a room
    // Updates the room with the name @Param roomName with the attributes from @Param room
    public void updateRoom(Room room, String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName))
                r = room;
        }
    }

    public void removeRoom(String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName))
                rooms.remove(r);
        }
    }

    // Get room by name
    // Return room when found, null when nothing is found
    public Room getRoomByName(String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName))
                return r;
        }
        return null;
    }

    // Get weekly average temperature
    public float getWeeklyTemperature(String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName)){
                // TODO calculate average
                return 0;
            }
        }
        return -1;
    }

    // Get weekly average humidity
    public float getWeeklyHumidity(String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName)){
                // TODO calculate average
                return 0;
            }
        }
        return -1;
    }

    // Get weekly average CO2
    public float getWeeklyCO2(String roomName){
        for(Room r : rooms){
            if(r.getName().equals(roomName)){
                // TODO calculate average
                return 0;
            }
        }
        return -1;
    }

}
