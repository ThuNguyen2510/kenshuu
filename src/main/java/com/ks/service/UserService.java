package com.ks.service;

import java.util.List;

import com.ks.model.User;

public interface UserService {
	User checkByUserIdAndPassword(String userId, String password);
	List<User> getListUser();
	List<User> search(String familyName, String firstName, int authorityId);
	boolean createUser(User newUser);
	User getUser(String userId);
	boolean updateUser(User updateUser);
	boolean deleteUser(String userId);
}
