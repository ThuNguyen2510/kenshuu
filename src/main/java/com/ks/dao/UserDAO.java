package com.ks.dao;

import com.ks.model.User;

public interface UserDAO {
	User checkByUserIdAndPassword(String userId, String password);
}
