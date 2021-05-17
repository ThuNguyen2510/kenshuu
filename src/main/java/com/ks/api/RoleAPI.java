package com.ks.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ks.service.RoleService;
import com.ks.service.impl.RoleServiceImpl;

@WebServlet("/api-authority")
public class RoleAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RoleAPI.class);
	private RoleService roleService;

	public RoleAPI() {
		roleService = new RoleServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("GET LIST");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), roleService.getListRole());
	}
}
