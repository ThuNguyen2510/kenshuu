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
				} else {
					((ObjectNode) rootNode).put("status", "fail");
				}
			} else {
				((ObjectNode) rootNode).put("message", "※ユーザが見つかりません。");
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
			if (userService.getUser(user.getUserId()) != null) {
				((ObjectNode) rootNode).put("message", "※ユーザIDが重複しています。");
			} else {
				if (userService.createUser(user)) {
					((ObjectNode) rootNode).put("status", "success");
				} else {
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
		if (userService.getUser(user.getUserId()) == null) {
			((ObjectNode) rootNode).put("message", "※ユーザが見つかりません。");
		} else {
			if (userService.deleteUser(user.getUserId())) {
				((ObjectNode) rootNode).put("status", "success");
			} else {
				((ObjectNode) rootNode).put("status", "fail");
			}
		}
		mapper.writeValue(response.getOutputStream(), rootNode);

	}

}
