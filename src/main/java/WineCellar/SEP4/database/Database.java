package WineCellar.SEP4.database;


import WineCellar.SEP4.resource.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    private static Database instance;

    public Database() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            String url = "jdbc:sqlserver://localhost;database=WineSep4;";
            this.connection = DriverManager.getConnection(url, "server", "password");
            System.out.println("Connection established ");
        } catch (Exception exception) {
            System.out.println("Connection failed");
            exception.printStackTrace();
        }
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<Room> getAllRooms() throws SQLException {
        ArrayList<Room> items = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "select * from Room";
        resultSet = statement.executeQuery(sql);
        String roomName;
        while (resultSet.next()) {
            Room item = new Room(resultSet.getString(2), resultSet.getString(3));
            items.add(item);
        }
        return items;
    }

    public Room getRoomMeasurement(String roomname) throws SQLException {
        Room room = new Room();
        statement = connection.createStatement();
        String sql = "SELECT DISTINCT sensorName,value,timestamp from Room r\n" +
                "INNER JOIN RoomHasMeasurement rhm ON r.room_ID=rhm.room_ID\n" +
                "INNER JOIN Measurement m ON m.measurement_ID=rhm.measurement_ID\n" +
                "INNER JOIN MeasurementHasSensor mhs ON m.measurement_ID=mhs.measurement_ID\n" +
                "INNER JOIN Sensor s ON s.sensor_ID=mhs.sensor_ID\n" +
                "Where r.roomName='" + roomname + "' and timestamp = (select max(timestamp) from Measurement) AND ValidTo>GETDATE()";

        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println("reading values DB");
            switch (resultSet.getString(1)) {
                case "Temperature":
                    room.setTemperature(resultSet.getInt(2));
                    break;
                case "Humidity":
                    room.setHumidity(resultSet.getInt(2));
                    break;
                case "Co2":
                    room.setCO2(resultSet.getInt(2));
                    break;
                default:
                    room.setEUI("bug");
                    break;
            }
        }
        sql = "Select TOP 1 ast.state,ast.timestamp,a.acuatorName from Room r\n" +
                "JOIN RoomHasAcuatorState rhas on rhas.room_ID=r.room_ID\n" +
                "JOIN AcuatorState ast on ast.state_ID=rhas.state_ID\n" +
                "JOIN Acuator a on a.acuator_ID=ast.acuator_ID\n" +
                "WHERE roomName='" + roomname + "'\n" +
                "order by ast.state_ID desc";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            room.setAcuator(resultSet.getInt(1));
        }

        room.setRoomName(roomname);
        return room;
    }

    public void addMeasurement(int value, String eui, long ts, String sensorName) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Measurement(timestamp,value) Values (" + ts + "," + value + ");";
        statement.execute(sql);
        sql = "SELECT SCOPE_IDENTITY();";
        resultSet = statement.executeQuery(sql);
        int Measurement_id = 0;
        while (resultSet.next()) {
            Measurement_id = resultSet.getInt(1);
        }

        sql = "SELECT Sensor.sensor_ID FROM Room r\n" +
                "JOIN Sensor ON r.room_ID=Sensor.room_ID\n" +
                "WHERE r.roomEUI='" + eui + "' AND Sensor.sensorName='" + sensorName + "';";
        resultSet = statement.executeQuery(sql);
        int Sensor_ID = 0;
        while (resultSet.next()) {
            Sensor_ID = resultSet.getInt(1);
        }

        sql = "SELECT * FROM Room\n" +
                "WHERE Room.roomEUI='" + eui + "';";
        resultSet = statement.executeQuery(sql);
        int Room_ID = 0;
        while (resultSet.next()) {
            Room_ID = resultSet.getInt(1);
        }

        sql = "INSERT INTO RoomHasMeasurement(room_ID,measurement_ID) Values (" + Room_ID + "," + Measurement_id + ");";
        statement.execute(sql);

        sql = "INSERT INTO MeasurementHasSensor(measurement_ID,sensor_ID) Values (" + Measurement_id + "," + Sensor_ID + ");";
        statement.execute(sql);
    }

    public ArrayList<Room> getAllUserRooms(String username) throws SQLException {
        ArrayList<Room> items = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT DISTINCT roomName FROM Users u\n" +
                "INNER JOIN UserHasRoom uhr ON u.user_ID=uhr.user_ID\n" +
                "INNER JOIN Room r ON r.room_ID=uhr.room_ID\n" +
                "WHERE username='" + username + "' and ValidTo>GETDATE()";

        resultSet = statement.executeQuery(sql);
        String roomName;
        while (resultSet.next()) {
            Room item = new Room(resultSet.getString(1));
            items.add(item);
        }
        return items;
    }

    public void addServoState(int value, String eui, long ts) throws SQLException {
        statement = connection.createStatement();
        String sql = "select acuator_ID,a.room_ID from Room r\n" +
                "                JOIN Acuator a ON r.room_ID=a.room_ID\n" +
                "                Where r.roomEUI='" + eui + "';";
        resultSet = statement.executeQuery(sql);
        int acuator_id = 0;
        int room_id = 0;
        int state_ID = 0;
        while (resultSet.next()) {
            acuator_id = resultSet.getInt(1);
            room_id = resultSet.getInt(2);
        }
        sql = "SELECT SCOPE_IDENTITY(); ";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            state_ID = resultSet.getInt(1);
        }
        sql = "INSERT INTO AcuatorState(acuator_ID,state,timestamp) Values (" + acuator_id + "," + value + "," + ts + ");";
        statement.execute(sql);
        sql = "INSERT INTO RoomHasAcuatorState(room_ID,state_ID) Values (" + room_id + "," + state_ID + ");";
        statement.execute(sql);
    }

    public void createUser(String username) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Users(username)\n" +
                "VALUES ('" + username + "');";
        statement.execute(sql);
    }

    public void changeUsername(String user, String usernamenew) throws SQLException {
        statement = connection.createStatement();
        String sql = "UPDATE Users\n" +
                "SET username = '" + usernamenew + "' WHERE username='" + user + "';";
        statement.execute(sql);
    }

    public void deleteRoom(String roomName) throws SQLException {
        statement = connection.createStatement();
        String sql = "UPDATE Room Set ValidTo=GETDATE() WHERE roomName='" + roomName + "';";
        statement.execute(sql);
    }

    public Room getWeeklyAverages(String roomName) throws SQLException {
        Room room = new Room();
        statement = connection.createStatement();
        String sql = "SELECT sensorName,AVG(value) FROM Measurement m\n" +
                "INNER JOIN MeasurementHasSensor mhs ON m.measurement_ID=mhs.measurement_ID\n" +
                "INNER JOIN Sensor s on s.sensor_ID=mhs.sensor_ID\n" +
                "WHERE DATEADD(ms, timestamp / 86400000, (timestamp / 86400000) + 25567) > DATEADD(Day,-7,GETDATE())\n" +
                "GROUP BY sensorName";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println("reading values DB");
            switch (resultSet.getString(1)) {
                case "Temperature":
                    room.setTemperature(resultSet.getInt(2));
                    break;
                case "Humidity":
                    room.setHumidity(resultSet.getInt(2));
                    break;
                case "Co2":
                    room.setCO2(resultSet.getInt(2));
                    break;
                default:
                    room.setEUI("bug");
                    break;
            }
        }
        room.setRoomName(roomName);
        return room;
    }

    public List<Room> getRoomHistory(String roomName) throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT sensorName,AVG(value) FROM Measurement m\n" +
                "INNER JOIN MeasurementHasSensor mhs ON m.measurement_ID=mhs.measurement_ID\n" +
                "INNER JOIN Sensor s on s.sensor_ID=mhs.sensor_ID\n" +
                "WHERE DATEADD(ms, timestamp / 86400000, (timestamp / 86400000) + 25567) > DATEADD(Day,-7,GETDATE())\n" +
                "GROUP BY sensorName";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {

        }
        //TODO:select query
        return null;
    }

    public void createRoom(String username, String roomName, String EUI) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Room(roomName,roomEUI,ValidTo,ValidFrom)\n" +
                "VALUES ('" + roomName + "','" + EUI + "','2030-04-28',GETDATE());";
        statement.execute(sql);
        int roomID = 0;
        int user_ID = 0;
        sql = "SELECT SCOPE_IDENTITY(); ";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            roomID = resultSet.getInt(1);
        }

        sql = "SELECT user_ID FROM Users WHERE username='" + username + "'";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            user_ID = resultSet.getInt(1);
        }
        sql = "INSERT INTO UserHasRoom(user_ID,room_ID)\n" +
                "VALUES (" + user_ID + "," + roomID + ");";
        statement.execute(sql);

        sql = "INSERT INTO Sensor(room_ID,sensorName,sensorUnit)\n" +
                "VALUES (" + roomID + ",'Temperature', 'Celsius');";
        statement.execute(sql);

        sql = "INSERT INTO Sensor(room_ID,sensorName,sensorUnit)\n" +
                "VALUES (" + roomID + ",'Humidity', '%');";
        statement.execute(sql);

        sql = "INSERT INTO Sensor(room_ID,sensorName,sensorUnit)\n" +
                "VALUES (" + roomID + ",'Co2', 'Parts-per-million');";
        statement.execute(sql);

        sql = "INSERT INTO Acuator(room_ID,acuatorName)\n" +
                "VALUES (" + roomID + ",'WindowControl');";
        statement.execute(sql);
    }
}
