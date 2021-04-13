package com.ks.service.impl;

import java.util.List;

import com.ks.dao.GenderDAO;
import com.ks.dao.impl.GenderDAOImpl;
import com.ks.model.Gender;
import com.ks.service.GenderService;

public class GenderServiceImpl implements GenderService {

	private GenderDAO genderDAO;

	public GenderServiceImpl() {
		genderDAO = new GenderDAOImpl();
	}

	@Override
	public List<Gender> getListGender() {

		return genderDAO.getListGender();
	}

}
