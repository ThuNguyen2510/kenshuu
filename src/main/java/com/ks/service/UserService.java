package com.ks.service;

import java.util.List;

import com.ks.model.User;

public interface UserService {
	User checkByUserIdAndPassword(String userId, String password);
	List<User> getListUser();
}
