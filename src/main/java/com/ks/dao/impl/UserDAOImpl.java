package com.ks.dao.impl;

import java.util.List;

import com.ks.dao.UserDAO;
import com.ks.mapper.UserMapper;
import com.ks.model.User;

public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

	@Override
	public User checkByUserIdAndPassword(String userId, String password) {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user");
		sql.append(" WHERE user_id = ? AND password = ?");
		List<User> users = query(sql.toString(), new UserMapper(), userId, password);
		if(users.isEmpty()) return null;
		else return users.get(0);

	}

	@Override
	public List<User> getListUser() {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role as r ON u.authority_id = r.authority_id ");
		sql.append("LEFT JOIN public.mst_gender as g ON u.gender_id = g.gender_id");
		System.out.println(sql);
		List<User> list = query(sql.toString(),new UserMapper());
		return list;
	}

}
