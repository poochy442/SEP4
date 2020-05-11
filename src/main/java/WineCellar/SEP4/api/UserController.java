package WineCellar.SEP4.api;

import WineCellar.SEP4.database.UserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import WineCellar.SEP4.resource.User;

import java.util.List;

@RestController("users")
public class UserController {

    UserDatabase users = UserDatabase.getInstance();

    private static final String template = "You have reached the login screen, %s with the password %s";

    @GetMapping("/users")
    public List<User> get(){
        return users.fetchUsers();
    }

    @GetMapping("/users/get")
    public User get(@RequestParam(name = "username") String username){
        return users.getUserByUsername(username);
    }

    @GetMapping("users/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password){
        User u = users.getUserByUsername(username);
        if(username == null)
            return "Please input username";
        else if(password == null)
            return "Please input password";
        else if(u != null && u.getPassword().equals(password))
            // TODO login
            return String.format("Logged in, %s", username);
        else
            return String.format(template, username, password);
    }

    @PostMapping("/users/create")
    public void create(@RequestParam(name = "username") String username,
                         @RequestParam(name = "password") String password){
        users.addUser(username, password);
    }

    @PostMapping("/users/update")
    public void update(@RequestBody User user,
                       @RequestParam(name = "username") String username){
        users.updateUser(user, username);
    }
}
