package com.ks.service.impl;

import java.util.List;

import com.ks.dao.RoleDAO;
import com.ks.dao.impl.RoleDAOImpl;
import com.ks.model.Role;
import com.ks.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO;

	public RoleServiceImpl() {
		roleDAO = new RoleDAOImpl();
	}

	@Override
	public List<Role> getListRole() {

		return roleDAO.getListRole();
	}

}
