package edu.iss.caps.service;



import java.util.ArrayList;

import edu.iss.caps.model.User;

public interface UserService {

	User findUser(String userId);
	
	ArrayList<User> findAllUsers();
	
	User authenticate(String name, String password);

	User createUser(User user);

	User changeUser(User user);

	void removeUser(User user);

}
