package WineCellar.SEP4.database;

import WineCellar.SEP4.resource.Response;
import WineCellar.SEP4.resource.Room;

import java.sql.SQLException;
import java.util.List;

public interface IAdapter {

    void processResponse(Response response);
    Room getRoomMeasurement(String roomname);
    List<Room> getAllRooms(String username);
    void createUser(String username);
    Room getHistory(String roomName);
    Room getWeeklyAverage(String roomName);


    void createRoom(String username,String roomName,String roomEUI);
    List<Room> getAllRooms() throws SQLException;
}
