package com.ks.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.model.Role;
import com.ks.model.User;
import com.ks.service.GenderService;
import com.ks.service.RoleService;
import com.ks.service.UserService;
import com.ks.service.impl.GenderServiceImpl;
import com.ks.service.impl.RoleServiceImpl;
import com.ks.service.impl.UserServiceImpl;

@WebServlet("/admin-user")
public class UserController extends HttpServlet {
	private static final Logger logger = Logger.getLogger(UserController.class);
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private RoleService roleService;
	private GenderService genderService;

	public UserController() {
		userService = new UserServiceImpl();
		roleService = new RoleServiceImpl();
		genderService = new GenderServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String viewLink = "";
		if (action != null) {
			if (action.equals("get")) {
				logger.info("GET LIST USER");
				viewLink = "/views/admin/home.jsp";
				List<User> listUser = userService.getListUser();
				List<Role> listRole = roleService.getListRole();
				request.setAttribute("listUser", listUser);
				request.setAttribute("listRole", listRole);
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewLink);
		rd.forward(request, response);
	}
}
