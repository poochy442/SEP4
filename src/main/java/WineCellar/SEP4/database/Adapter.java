package WineCellar.SEP4.database;

import WineCellar.SEP4.resource.Response;
import WineCellar.SEP4.resource.Room;

import java.sql.SQLException;
import java.util.List;

public class Adapter implements IAdapter {
    Database db;
    private static Adapter instance;

    public Adapter() {
        db = Database.getInstance();
    }

    public synchronized static Adapter getInstance() {
        if (instance == null) {
            instance = new Adapter();
        }
        return instance;
    }

    @Override
    public synchronized void  processResponse(Response response) {
        int value;
        System.out.println("adapter is ready to process response");
        switch (response.getPort()) {
            case 1:
                String humidity=response.getData().substring(0,4);
                String temperature=response.getData().substring(4,8);
                String co2=response.getData().substring(8,12);
                value = Integer.parseInt(humidity,16);
                try {
                    db.addMeasurement(value, response.getEUI(), response.getTs(), "Humidity");
                    System.out.println("Added humidity measurement successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                value = Integer.parseInt(temperature,16);
                try {
                    db.addMeasurement(value, response.getEUI(), response.getTs(), "Temperature");
                    System.out.println("Added temperature measurement successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                value = Integer.parseInt(co2,16);
                try {
                    db.addMeasurement(value, response.getEUI(), response.getTs(), "Co2");
                    System.out.println("Added co2 measurement successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                value = Integer.parseInt(response.getData(),16);
                try {
                    db.addServoState(value, response.getEUI(), response.getTs());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Adapter found bad port");
                break;
        }
    }

    @Override
    public synchronized Room getRoomMeasurement(String roomname) {
        try {
            return db.getRoomMeasurement(roomname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized List<Room> getUserRooms(String username) {
        try {
            return db.getAllUserRooms(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void createUser(String username) {
        try {
            db.createUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(String old,String newuser) {
        try {
            db.changeUsername(old,newuser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized List<Room> getHistory(String roomName) {
        try {
            return db.getRoomHistory(roomName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized Room getWeeklyAverage(String roomName) {
        try {
            return db.getWeeklyAverages(roomName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized String createRoom(String username, String roomName, String roomEUI) {
        try {
            db.createRoom(username,roomName,roomEUI);
            return "Success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Room name already taken.";
        }
    }

    @Override
    public List<Room> getAllRooms()  {
        try {
            return db.getAllRooms();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteRoom(String roomName) {
        try {
            db.deleteRoom(roomName);
            return "Success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error occured";
        }
    }
}
