package WineCellar.SEP4.resource.repository;

import WineCellar.SEP4.resource.Room;
import org.springframework.data.repository.CrudRepository;


public interface RoomRepository /*extends CrudRepository<Room, Integer>*/ {
    Room findByName(String name);

}
