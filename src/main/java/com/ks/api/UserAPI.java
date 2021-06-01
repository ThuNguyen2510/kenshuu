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
import com.ks.utils.ValidateUser;

@WebServlet("/api-user")
public class UserAPI extends HttpServlet {
	private static final Logger logger = Logger.getLogger(UserAPI.class);
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public UserAPI() {
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("GET LIST");
		ObjectMapper mapper = new ObjectMapper();
		//全てのユーザを取る
		mapper.writeValue(response.getOutputStream(), userService.getListUser());
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("UPDATE");
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		User user = null;
		JsonNode rootNode = mapper.createObjectNode();

		try {
			user = HttpUtil.of(request.getReader()).toModel(User.class);
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ValidateUser validation = new ValidateUser();
		message += validation.validate(user);

		if (!message.equals("")) {
			((ObjectNode) rootNode).put("message", message);
		} else {
			if (userService.getUser(user.getUserId()) != null) {
				if (userService.updateUser(user)) {// 成功
					((ObjectNode) rootNode).put("status", "success");
				} else {//失敗
					((ObjectNode) rootNode).put("status", "fail");
				}
			} else {//ユーザが見つからない
				((ObjectNode) rootNode).put("status", "not_found");
			}
		}
		mapper.writeValue(response.getOutputStream(), rootNode);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("CREATE");
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		User user = null;
		JsonNode rootNode = mapper.createObjectNode();
		try {
			user = HttpUtil.of(request.getReader()).toModel(User.class);
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ValidateUser validation = new ValidateUser();
		message += validation.validate(user);
		if (!message.equals("")) {
			((ObjectNode) rootNode).put("message", message);
		} else {
			if (userService.getUser(user.getUserId()) != null) {//登録済みのユーザID
				((ObjectNode) rootNode).put("status", "duplicate_user");
			} else {
				if (userService.createUser(user)) {//成功
					((ObjectNode) rootNode).put("status", "success");
				} else {//失敗
					((ObjectNode) rootNode).put("status", "fail");
				}
			}
		}
		mapper.writeValue(response.getOutputStream(), rootNode);

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("DELETE");
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		User user = null;
		JsonNode rootNode = mapper.createObjectNode();
		try {
			user = HttpUtil.of(request.getReader()).toModel(User.class);
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if (userService.getUser(user.getUserId()) == null) {//ユーザが見つからない場合
			((ObjectNode) rootNode).put("message", "not_found");
		} else {
			if (userService.deleteUser(user.getUserId())) {//削除成功の場合
				((ObjectNode) rootNode).put("status", "success");
			} else {
				((ObjectNode) rootNode).put("status", "fail");//削除失敗の場合
			}
		}
		mapper.writeValue(response.getOutputStream(), rootNode);

	}

}
