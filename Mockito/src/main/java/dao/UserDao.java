package dao;

import java.util.List;
import model.User;

public interface UserDao {
	
    public User getUserByUsername(String username) throws Exception;
    
    public List<User> findAllUsers();
}