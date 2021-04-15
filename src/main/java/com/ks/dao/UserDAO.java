package com.ks.dao;

import java.util.List;

import com.ks.model.User;

public interface UserDAO {
	User checkByUserIdAndPassword(String userId, String password);
	List<User> getListUser();
	List<User> search(String familyName, String firstName, int authorityId);
	boolean createUser(User newUser);
	User getUser(String userId);
}
