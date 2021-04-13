package com.ks.service;

import com.ks.model.User;

public interface UserService {
	User checkByUserIdAndPassword(String userId, String password);
}
