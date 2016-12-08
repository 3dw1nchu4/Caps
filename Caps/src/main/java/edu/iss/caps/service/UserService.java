package edu.iss.caps.service;



import java.util.ArrayList;

import edu.iss.caps.exception.FailedAuthentication;
import edu.iss.caps.model.User;

public interface UserService {

	User findUser(String userId);
	
	ArrayList<User> findAllUsers();
	
	User authenticate(String userId, String password) throws FailedAuthentication;

	User createUser(User user);

	User changeUser(User user);

	void removeUser(User user);

}
