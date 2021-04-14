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
		if (users.isEmpty())
			return null;//　合っているユーザがない場合
		else
			return users.get(0);//合っているユーザがいる場合

	}

	@Override
	public List<User> getListUser() {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role USING (authority_id) ");//　役職テーブルと　LEFT JOIN
		sql.append("LEFT JOIN public.mst_gender  USING (gender_id)");//　性別テーブルと　　LEFT JOIN
		List<User> list = query(sql.toString(), new UserMapper());//　SQLクエリーを実行して、リストに保存する
		return list;
	}

	@Override
	public List<User> search(String familyName, String firstName, int authorityId) {
		List<User> list = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_user as u");
		sql.append(" LEFT JOIN public.mst_role as r USING (authority_id)");//　役職テーブルと　LEFT JOIN
		sql.append(" LEFT JOIN public.mst_gender as g USING (gender_id)");//　性別テーブルと　　LEFT JOIN
		if (familyName.equals("") != true) {//familyNameフィールドのインプットが入力した場合
			sql.append(" WHERE (u.family_name = ? )");
			if (firstName.equals("") != true) {//familyName, firstNameのインプットが入力した場合
				sql.append(" AND( u.first_name = ? )");
				if (authorityId != -1) {//familyName, firstName,authorityIdのインプットが入力した場合
					sql.append(" AND (u.authority_id = ? )"); //　familyName, firstName, authorityIdフィールドによる比較する
					list = query(sql.toString(), new UserMapper(), familyName, firstName, authorityId);
				} else {
					sql.append(" AND (u.authority_id = ? )");//familyName, firstNameフィールドによる比較する
					list = query(sql.toString(), new UserMapper(), familyName, firstName);
				}

			} else {
				if (authorityId != -1) {
					sql.append(" AND (u.authority_id = ? )");//familyName, authorityIdフィールドによる比較する
					list = query(sql.toString(), new UserMapper(), familyName, authorityId);
				} else {
					list = query(sql.toString(), new UserMapper(), familyName);//familyNameフィールドによる比較する
				}
			}
		}
		//familyNameフィールドのインプットが入力しない場合
		else if (firstName.equals("") != true) {
			sql.append(" WHERE ( u.first_name = ? )");//firstNameフィールドによる比較する
			if (authorityId != -1) {
				sql.append(" AND (u.authority_id = ? )");//firstName, authorityIdフィールドによる比較する
				list = query(sql.toString(), new UserMapper(), firstName, authorityId);
			} else {
				//firstNameフィールドによる比較する
				list = query(sql.toString(), new UserMapper(), firstName);
			}
		}
		//familyName、firstNameフィールドのインプットが入力しない場合
		else if (authorityId != -1) {
			sql.append(" WHERE (u.authority_id = ? )");//authorityIdフィールドによる比較する
			list = query(sql.toString(), new UserMapper(), authorityId);
		}
		//インプットがない場合
		else {
			list = query(sql.toString(), new UserMapper());//全てのデータを取る
		}
		return list;
	}

}
