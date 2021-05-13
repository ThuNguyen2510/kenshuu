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
		if (request.getSession().getAttribute("currentUser") != null) { // ユーザがログインしている
			response.sendRedirect(request.getContextPath() + "/admin-user?action=get");//一覧画面に遷移する
		} else {
			String alert = request.getParameter("alert");
			String message = request.getParameter("message");
			if (message != null && alert != null) {//メッセージがある場合
				if (message.equals("blankUserId")) {
					request.setAttribute("message", "※ユーザIDが未入力です。");
				} else if (message.equals("blankPassword")) {
					request.setAttribute("message", "※パスワードが未入力です。");
				} else if(message.equals("blankUserIdblankPassword")){
					request.setAttribute("message", "※ユーザIDとパスワードが未入力です。");
				}else {
					request.setAttribute("message", "※ログインに失敗しました。");//不正なユーザID、パスワードで、メッセージが表示される
				}
				request.setAttribute("alert", alert);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");//ログイン画面に遷移する
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		if (userId.equals("") != true && password.equals("") != true) { // インプットをチェックして、十分でした
			User user = userService.checkByUserIdAndPassword(userId, password); // ユーザＩＤとパスワードからユーザを検索する
			HttpSession session = request.getSession();
			if (user != null) {//ユーザが見つけた場合/
				logger.info("LOGIN SUCCESS");
				session.setAttribute("currentUser", user.getUserId()); // セッションに保存する
				response.sendRedirect(request.getContextPath() + "/admin-user?action=get&message=success");
			} else {//ユーザが見つからない場合
				logger.info("LOGIN FAIL");
				response.sendRedirect(request.getContextPath() + "/login?message=fail&alert=danger");
			}

		} else {//インプットが足りない場合
			String message = "";
			logger.info("LOGIN FAIL");
			if (userId.equals("")) {
				message = "blankUserId";
			}
			if(password.equals("")) {
				message += "blankPassword";
			}
			response.sendRedirect(request.getContextPath() + "/login?message=" + message + "&alert=danger");

		}

	}

}
