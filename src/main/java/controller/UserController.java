package controller;

import data.DataUser;
import entity.*;
import util.ApplicationException;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
	private data.DataUser dataUser;

	public UserController() {
		dataUser = new DataUser();
	}

	public User validateUser(String username, String password) throws Exception, ApplicationException {
		User user = dataUser.getByUsername(username);
		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

	public User registerUser(User user) throws Exception, ApplicationException {
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		return dataUser.add(user);
	}
}