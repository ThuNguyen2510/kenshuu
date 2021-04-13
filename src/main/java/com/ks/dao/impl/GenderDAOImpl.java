package com.ks.dao.impl;

import java.util.List;

import com.ks.dao.GenderDAO;
import com.ks.mapper.GenderMapper;
import com.ks.model.Gender;

public class GenderDAOImpl  extends BaseDAOImpl<Gender> implements GenderDAO {

	@Override
	public List<Gender> getListGender() {
		StringBuilder sql= new StringBuilder("SELECT * FROM public.mst_gender");
		return query(sql.toString(),new GenderMapper());

	}

}
