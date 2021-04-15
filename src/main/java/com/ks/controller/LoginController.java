package com.ks.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ks.model.User;
import com.ks.service.UserService;
import com.ks.service.impl.UserServiceImpl;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	private static final long serialVersionUID = 1L;

	private UserService userService;

	public LoginController() {
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("currentUser") != null) { // check user is logged in
			response.sendRedirect(request.getContextPath() + "/admin-user?action=get");
		} else {
			String alert = request.getParameter("alert");
			String message = request.getParameter("message");
			if (message != null && alert != null) {
				request.setAttribute("message", "※ログインに失敗しました。");
				request.setAttribute("alert", alert);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		if (userId.equals("") != true && password.equals("") != true) { // check input not empty
			User user = userService.checkByUserIdAndPassword(userId, password); // find a user by userId and password
			HttpSession session = request.getSession();
			if (user != null) {
				logger.info("LOGIN SUCCESS");
				session.setAttribute("currentUser", user.getUserId()); // save user in session
				response.sendRedirect(request.getContextPath() + "/admin-user?action=get");
			} else {
				logger.info("LOGIN FAIL");
				response.sendRedirect(request.getContextPath() + "/login?message=fail&alert=danger");
			}

		} else {
			logger.info("LOGIN FAIL");
			response.sendRedirect(request.getContextPath() + "/login?message=fail&alert=danger");
		}

	}

}
