package com.ks.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ks.model.User;
import com.ks.service.UserService;
import com.ks.service.impl.UserServiceImpl;
import com.ks.utils.HttpUtil;
import com.ks.utils.JWTGenerate;

@WebServlet("/auth")
public class AuthAPI extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AuthAPI.class);
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public AuthAPI() {
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("LOGOUT");
		ObjectMapper mapper = new ObjectMapper();
		request.getSession().removeAttribute("currentUser");
		mapper.writeValue(response.getOutputStream(), "logout_success");//データを返す
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("LOGIN");
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		JsonNode rootNode = mapper.createObjectNode();
		try {
			user = HttpUtil.of(request.getReader()).toModel(User.class);
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if (userService.checkByUserIdAndPassword(user.getUserId(), user.getPassword()) != null) {//ユーザの情報をチェックして、成功の場合
			String token = JWTGenerate.createJwtSignedHMAC(user.getUserId());//JWTを作成する
			((ObjectNode) rootNode).put("token", "Bearer "+token);
			request.getSession().setAttribute("currentUser", user.getUserId());
			((ObjectNode) rootNode).put("familyName", userService.checkByUserIdAndPassword(user.getUserId(), user.getPassword()).getFamilyName());
			((ObjectNode) rootNode).put("status", "success");
		} else {//ログインに失敗する場合/
			((ObjectNode) rootNode).put("status", "fail");
		}
		mapper.writeValue(response.getOutputStream(), rootNode);//データを返す
	}

}
