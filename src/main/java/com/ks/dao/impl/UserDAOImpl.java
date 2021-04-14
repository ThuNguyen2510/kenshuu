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
		List<User> users = query(sql.toString(), new UserMapper(), userId, password);//　SQLクエリーを実行して、リストに保存する
		if(users.isEmpty()) return null;//　合っているユーザがない場合
		else return users.get(0);//合っているユーザがいる場合

	}

	@Override
	public List<User> getListUser() {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role as r ON u.authority_id = r.authority_id ");//　役職テーブルと　LEFT JOIN
		sql.append("LEFT JOIN public.mst_gender as g ON u.gender_id = g.gender_id");//　性別テーブルと　　LEFT JOIN
		List<User> list = query(sql.toString(),new UserMapper());//　SQLクエリーを実行して、リストに保存する
		return list;
	}

}
