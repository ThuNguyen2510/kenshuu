package com.ks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet) {
		try {
			User user = new User();
			user.setUserId(resultSet.getString("user_id"));
			user.setPassword(resultSet.getString("password"));
			user.setFamilyName(resultSet.getString("family_name"));
			user.setFirstName(resultSet.getString("first_name"));
			user.setGenderId(resultSet.getInt("gender_id"));
			user.setAge(resultSet.getInt("age"));
			user.setAuthorityId(resultSet.getInt("authority_id"));
			user.setAdmin(resultSet.getInt("admin"));
			user.setCreateDate(resultSet.getLong("create_date"));
			user.setCreateUserId(resultSet.getString("create_user_id"));
			user.setUpdateDate(resultSet.getLong("update_date"));
			user.setUpdateUserId(resultSet.getString("update_user_id"));

			return user;
		} catch (SQLException e) {
			return null;
		}
	}

}