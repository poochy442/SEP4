package WineCellar.SEP4.api;

import WineCellar.SEP4.database.RoomDatabase;
import WineCellar.SEP4.resource.Room;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    RoomDatabase rooms = RoomDatabase.getInstance();

    @GetMapping("/rooms")
    public List<Room> get() {
        return rooms.fetchRooms();
    }

    @GetMapping("/rooms")
    public Room get(@RequestParam(name = "roomname") String roomName) {
        return rooms.getRoomByName(roomName);
    }

    @PostMapping("/rooms")
    public void create(@RequestBody Room room) {
        rooms.addRoom(room);
    }

    @PutMapping("/rooms")
    public void update(@RequestBody Room room,
                       @RequestParam(name = "roomname") String roomName) {
        rooms.updateRoom(room, roomName);
    }

    @GetMapping("/rooms/averageTemperature")
    public float weeklyTemperature(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyTemperature(roomName);
    }

    @GetMapping("/rooms/averageHumidity")
    public float weeklyHumidity(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyHumidity(roomName);
    }

    @GetMapping("/rooms/averageCO2")
    public float weeklyCO2(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyCO2(roomName);
    }

}