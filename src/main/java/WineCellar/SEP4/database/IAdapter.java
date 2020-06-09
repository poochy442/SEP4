package WineCellar.SEP4.database;

import WineCellar.SEP4.resource.Response;
import WineCellar.SEP4.resource.Room;

import java.sql.SQLException;
import java.util.List;

public interface IAdapter {
    List<Room> getUserRooms(String username);
    Room getRoomMeasurement(String roomname);
    List<Room> getHistory(String roomName);
    Room getWeeklyAverage(String roomName);

    void createUser(String username);
    void updateUser(String old,String newuser);

    String createRoom(String username,String roomName,String roomEUI);
    String deleteRoom(String roomName);


    void processResponse(Response response);
    List<Room> getAllRooms();
}
