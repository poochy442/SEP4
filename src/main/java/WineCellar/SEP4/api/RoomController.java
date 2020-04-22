package WineCellar.SEP4.api;

import WineCellar.SEP4.database.RoomDatabase;
import WineCellar.SEP4.resource.Room;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    RoomDatabase rooms = RoomDatabase.getInstance();

    @GetMapping("/rooms/get")
    public List<Room> get() {
        return rooms.fetchRooms();
    }

    @GetMapping("/rooms/get")
    public Room get(@RequestParam(name = "roomname") String roomName) {
        return rooms.getRoomByName(roomName);
    }

    @PostMapping("/rooms/create")
    public void create(@RequestBody Room room) {
        rooms.addRoom(room);
    }

    @PostMapping("/rooms/update")
    public void update(@RequestBody Room room,
                       @RequestParam(name = "roomname") String roomName) {
        rooms.updateRoom(room, roomName);
    }

    @GetMapping("/rooms/averagetemp")
    public float weeklyTemperature(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyTemperature(roomName);
    }

    @GetMapping("/rooms/averagetemp")
    public float weeklyHumidity(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyHumidity(roomName);
    }

    @GetMapping("/rooms/averagetemp")
    public float weeklyCO2(@RequestParam(name = "roomname") String roomName){
        return rooms.getWeeklyCO2(roomName);
    }

}