package com.ks.dao.impl;

import java.util.ArrayList;
import java.util.Date;
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
		if (users.isEmpty())
			return null;//　合っているユーザがない場合
		else
			return users.get(0);//合っているユーザがいる場合

	}

	@Override
	public List<User> getListUser() {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role USING (authority_id) ");//　役職テーブルと　LEFT JOIN
		sql.append("LEFT JOIN public.mst_gender  USING (gender_id) ORDER BY u.create_date DESC" );//　性別テーブルと　　LEFT JOIN
		List<User> list = query(sql.toString(), new UserMapper());//　SQLクエリーを実行して、リストに保存する
		return list;
	}

	@Override
	public List<User> search(String familyName, String firstName, int authorityId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role as r USING (authority_id)");//　役職テーブルと　LEFT JOIN
		sql.append(" LEFT JOIN public.mst_gender as g USING (gender_id) WHERE true");//　性別テーブルと　　LEFT JOIN
		List<Object> params = new ArrayList<>();
		if (familyName.equals("") != true) {//familyNameフィールドのインプットが入力した場合
			sql.append(" AND (u.family_name = ? )");
			params.add(familyName);
		}
		if (firstName.equals("") != true) {//firstNameフィールドのインプットが入力した場合
			sql.append(" AND (u.first_name = ? )");
			params.add(firstName);
		}
		if (authorityId != -1) {//authorityIdフィールドのインプットが入力した場合
			sql.append(" AND (u.authority_id = ? )");
			params.add(authorityId);
		}
		List<User> list = query2(sql.toString(), new UserMapper(), params);
		return list;

	}

	@Override
	public boolean createUser(User newUser) {
		StringBuilder sql = new StringBuilder(
				"INSERT INTO public.mst_user (user_id, password, family_name, first_name,");
		sql.append(" admin, create_user_id, update_user_id, create_date, update_date, age, authority_id, gender_id)");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		if (getUser(newUser.getUserId()) != null) {//登録済みのユーザIDで登録した場合
			return false;
		}
		newUser.setUpdateUserId(newUser.getCreateUserId());
		newUser.setCreateDate(new Date().getTime() / 1000);
		newUser.setUpdateDate(new Date().getTime() / 1000);
		return saveOrUpdate(sql.toString(), newUser.getUserId(), newUser.getPassword(), newUser.getFamilyName(),
				newUser.getFirstName(), newUser.getAdmin(), newUser.getCreateUserId(),
				newUser.getUpdateUserId(), newUser.getCreateDate(), newUser.getUpdateDate(), newUser.getAge(),
				newUser.getAuthorityId(), newUser.getGenderId());
	}

	@Override
	public User getUser(String userId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user WHERE user_id = ? ");
		List<User> users = query(sql.toString(), new UserMapper(), userId);//　SQLクエリーを実行して、リストに保存する
		return users.size() == 0 ? null : users.get(0);
	}

	@Override
	public boolean updateUser(User updateUser) {
		StringBuilder sql = new StringBuilder(
				"UPDATE public.mst_user SET password = ? ,family_name=?,first_name=?,admin=?,"
						+ "age=?,authority_id=?,gender_id=?,update_user_id=?,update_date=?"
						+ "	WHERE user_id = ?");
		updateUser.setUpdateDate(new Date().getTime() / 1000);
		return saveOrUpdate(sql.toString(), updateUser.getPassword(), updateUser.getFamilyName(), updateUser.getFirstName(),
				updateUser.getAdmin(),
				updateUser.getAge(), updateUser.getAuthorityId(), updateUser.getGenderId(),
				updateUser.getUpdateUserId(), updateUser.getUpdateDate(), updateUser.getUserId());

	}

	@Override
	public boolean deleteUser(String userId) {
		if(getUser(userId)==null) return false;
		StringBuilder sql= new StringBuilder("DELETE FROM public.mst_user WHERE user_id = ?");
		return saveOrUpdate(sql.toString(), userId) ;
	}

}
