package com.ks.service.impl;

import java.util.List;

import com.ks.dao.CountDAO;
import com.ks.dao.impl.CountDAOImpl;
import com.ks.model.Count;
import com.ks.service.TotalService;

public class TotalServiceImpl implements TotalService {
	private CountDAO countDAO;

	public TotalServiceImpl() {
		countDAO = new CountDAOImpl();
	}

	@Override
	public List<Count> getSummary() {

		return countDAO.getSummary();
	}

}
