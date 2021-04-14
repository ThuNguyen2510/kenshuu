package com.ks.service.impl;

import java.util.List;

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

	@Override
	public List<User> getListUser() {

		return userDAO.getListUser();
	}

	@Override
	public List<User> search(String familyName, String firstName, int authorityId) {
		return userDAO.search(familyName,firstName,authorityId);
	}

}
