package com.ks.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.ks.service.RoleService;
import com.ks.service.UserService;
import com.ks.service.impl.RoleServiceImpl;
import com.ks.service.impl.UserServiceImpl;
import com.ks.utils.MakeReport;

@WebServlet("/report")
public class ReportController extends HttpServlet {
	private static final Logger logger = Logger.getLogger(ReportController.class);
	private static final long serialVersionUID = 1L;
	private MakeReport report;
	private UserService userService;
	private RoleService roleService;

	public ReportController() {
		report = new MakeReport();
		userService = new UserServiceImpl();
		roleService = new RoleServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("SAVE FILE PDF");

		List<User> userList = userService.search(request.getParameter("familyName"),
				request.getParameter("firstName"), Integer.valueOf(request.getParameter("authorityId")));//パラメータを取って、見つける
		if (userList != null && userList.size() != 0) {
			response.setContentType("application/pdf");
			report.exec(userList);//帳票を作成する
			String fullPath = "C:/Users/nguye/eclipse-workspace/kenshuu/report.pdf";//作成したファイルのリング
			Path path = Paths.get(fullPath);
			byte[] data = Files.readAllBytes(path);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=report.pdf");
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));//新しいファイルを作成する
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);//データを記入する
			}
			inputStream.close();
			outStream.close();
		} else {//userList配列は空
			request.setAttribute("message", "※リスト要素が０です。");
			List<User> listUser = userService.getListUser();//全てのユーザを取る
			List<Role> listRole = roleService.getListRole();//全ての役職を取る
			request.setAttribute("listUser", listUser);//ユーザリストを保存する
			request.setAttribute("listRole", listRole);//役職リストを保存する
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/home.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}