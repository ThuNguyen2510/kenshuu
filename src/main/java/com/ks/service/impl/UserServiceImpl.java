package com.ks.service.impl;
import com.ks.dao.UserDAO;
import com.ks.dao.impl.UserDAOImpl;
import com.ks.model.User;
import com.ks.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	public UserServiceImpl() {
		userDAO = new UserDAOImpl();
	}
	@Override
	public User checkByUserIdAndPassword(String userId, String password) {
		return userDAO.checkByUserIdAndPassword(userId, password);
	}

}
