package com.ks.api;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/api-search")
public class SearchAPI extends HttpServlet {
	private static final Logger logger = Logger.getLogger(SearchAPI.class);
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public SearchAPI() {
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		logger.info("SEARCH ");
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		JsonNode rootNode = mapper.createObjectNode();
		try {
			user = HttpUtil.of(request.getReader()).toModel(User.class);
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if (user.getFamilyName() == null) {
			user.setFamilyName("");
		}
		if (user.getFirstName() == null) {
			user.setFirstName("");
		}
		if(user.getAuthorityId()==0) {
			user.setAuthorityId(-1);
		}
		List<User> listUser = userService.search(user.getFamilyName(), user.getFirstName(), user.getAuthorityId());
		if (listUser == null || listUser.size() == 0) {
			((ObjectNode) rootNode).put("message", "not_found");
			mapper.writeValue(response.getOutputStream(), rootNode);
		} else {
			mapper.writeValue(response.getOutputStream(), listUser);
		}

	}
}
