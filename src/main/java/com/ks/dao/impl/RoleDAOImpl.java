package com.ks.dao.impl;

import java.util.List;

import com.ks.dao.RoleDAO;
import com.ks.mapper.RoleMapper;
import com.ks.model.Role;

public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {

	@Override
	public List<Role> getListRole() {
		StringBuilder sql = new StringBuilder("SELECT * FROM public.mst_role");
		return query(sql.toString(), new RoleMapper());
	}

}
