package com.ks.mapper;

import java.sql.ResultSet;

import com.ks.model.Role;

public class RoleMapper implements RowMapper<Role> {

	@Override
	public Role mapRow(ResultSet rs) {
		try {
			/*データベースの結果セットをRoleのフィールドにそれぞれマップする*/
			Role role = new Role();
			role.setAuthorityId(rs.getInt("authority_id"));
			role.setAuthorityName(rs.getString("authority_name"));
			role.setCreateDate(rs.getLong("create_date"));
			role.setCreateUserId(rs.getString("create_user_id"));
			role.setUpdateDate(rs.getLong("update_date"));
			role.setUpdateUserId(rs.getString("update_user_id"));

			return role;
		} catch (Exception ex) {
			return null;
		}

	}

}
