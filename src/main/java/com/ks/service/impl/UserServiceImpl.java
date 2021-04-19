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

	@Override
	public boolean createUser(User newUser) {
		return userDAO.createUser(newUser);
	}

	@Override
	public User getUser(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return userDAO.getUser(userId);
	}

	@Override
	public boolean updateUser(User updateUser) {
		// TODO 自動生成されたメソッド・スタブ
		return userDAO.updateUser(updateUser);
	}

}
