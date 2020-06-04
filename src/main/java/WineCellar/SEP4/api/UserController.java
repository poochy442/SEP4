package WineCellar.SEP4.api;

import WineCellar.SEP4.database.Adapter;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    Adapter adapter=Adapter.getInstance();

    @PostMapping("/users")
    public void create(@RequestParam(name = "username") String username){
        adapter.createUser(username);
    }

    @PostMapping("/users/update")
    public void update(@RequestParam(name="old") String user,
                       @RequestParam(name = "new") String username){
        adapter.updateUser(user, username);
    }
}
