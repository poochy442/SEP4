package WineCellar.SEP4.database;

import WineCellar.SEP4.resource.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private ArrayList<User> users;

    private static UserDatabase instance = null;
    public static UserDatabase getInstance(){
        if(instance == null){
            instance = new UserDatabase();
        }
        return instance;
    }

    private UserDatabase(){
        users = new ArrayList<>();
    }

    // Return all users
    public List<User> fetchUsers(){
        // TODO fetch users
        return users;
    }

    // Add user to the database
    public void addUser(String username, String password){
        User user = new User(username, password);
        users.add(user);
    }

    // Delete a user
    public void deleteUser(String username, String password){
        for(User u : users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password))
                users.remove(u);
        }
    }

    public void updateUser(User user, String username){
        for(User u : users){
            if(u.getUsername().equals(username))
                users.remove(u);
        }
    }

    // Get user by username
    // Returns user when found, null when nothing is found
    public User getUserByUsername(String username){
        for(User u : users){
            if(u.getUsername().equals(username))
                return u;
        }
        return null;
    }

}
