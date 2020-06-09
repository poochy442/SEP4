package WineCellar.SEP4.api;

import WineCellar.SEP4.database.Adapter;
import WineCellar.SEP4.resource.Room;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    Adapter adapter=Adapter.getInstance();
    @GetMapping("/rooms/allrooms")
    public List<Room> get() {
            return adapter.getAllRooms();
    }

    @GetMapping("/rooms/all")
    public List<Room> getUserRooms(@RequestParam(name = "username") String username) {
        return adapter.getUserRooms(username);
    }

    @GetMapping("/rooms")
    public Room getRoomMeasurement(@RequestParam(name = "roomname") String roomname) {
        return adapter.getRoomMeasurement(roomname);
    }

    @PostMapping("/rooms")
    public String create(@RequestParam(name = "username") String userName,@RequestBody Room room) {
        return  adapter.createRoom(userName,room.getRoomName(),room.getEUI());
    }

    @GetMapping("/rooms/average")
    public Room weeklyAverage(@RequestParam(name = "roomname") String roomName){
        return adapter.getWeeklyAverage(roomName);
    }

    @GetMapping("/rooms/history")
    public List<Room> roomHistory(@RequestParam(name = "roomname") String roomName){
        return adapter.getHistory(roomName);
    }

    @DeleteMapping("/rooms")
    public String deleteRoom(@RequestParam(name = "roomname") String roomName){
        return adapter.deleteRoom(roomName);
    }
}