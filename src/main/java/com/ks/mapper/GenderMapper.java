package com.ks.mapper;

import java.sql.ResultSet;

import com.ks.model.Gender;

public class GenderMapper implements RowMapper<Gender> {

	@Override
	public Gender mapRow(ResultSet rs) {
		try {
			/*データベースの結果セットをGenderのフィールドにそれぞれマップする*/
			Gender gen = new Gender();
			gen.setGenderId(rs.getInt("gender_id"));
			gen.setGenderName(rs.getString("gender_name"));
			gen.setCreateDate(rs.getLong("create_date"));
			gen.setCreateUserId(rs.getString("create_user_id"));
			gen.setUpdateDate(rs.getLong("update_date"));
			gen.setUpdateUserId(rs.getString("update_user_id"));
			return gen;

		} catch (Exception e) {
			return null;
		}

	}
}
