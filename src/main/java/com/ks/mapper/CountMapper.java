package com.ks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.model.Count;

public class CountMapper implements RowMapper<Count> {

	@Override
	public Count mapRow(ResultSet rs) {
		Count count = new Count();
		try {
			count.setAuthorityName(rs.getString("authority_name"));
			count.setAuthorityName(rs.getString("authority_name"));
			count.setFemale(rs.getInt("femaleCount"));
			count.setMale(rs.getInt("maleCount"));
			count.setNullAge(rs.getInt("nullAgeCount"));
			count.setUnder19(rs.getInt("under19Count"));
			count.setOver20(rs.getInt("over20Count"));
			count.setNullGender(rs.getInt("nullGenCount"));
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
