package com.ks.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ks.service.TotalService;
import com.ks.service.impl.TotalServiceImpl;
@WebServlet("/api-count")
public class CountAPI extends HttpServlet {
	private static final Logger logger = Logger.getLogger(CountAPI.class);
	private static final long serialVersionUID = 1L;
	private TotalService totalService;

	public CountAPI() {
		totalService = new TotalServiceImpl();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("COUNT");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(),totalService.getSummary());

	}

}
