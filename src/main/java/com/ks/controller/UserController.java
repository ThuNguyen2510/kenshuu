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

import com.ks.model.Gender;
import com.ks.model.Role;
import com.ks.model.User;
import com.ks.service.GenderService;
import com.ks.service.RoleService;
import com.ks.service.UserService;
import com.ks.service.impl.GenderServiceImpl;
import com.ks.service.impl.RoleServiceImpl;
import com.ks.service.impl.UserServiceImpl;
import com.ks.utils.ValidateUser;

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
		String action = request.getParameter("action");// アクションタイプを取る
		String viewLink = "";//ビューのリングを保存する
		if (action != null) {
			if (action.equals("get")) {//全てのデータを表示するアクションタイプ
				logger.info("GET LIST USER");
				viewLink = "/views/admin/home.jsp";//リングをセットする
				List<User> listUser = userService.getListUser();//全てのユーザを取る
				List<Role> listRole = roleService.getListRole();//全ての役職を取る
				request.setAttribute("listUser", listUser);//ユーザリストを保存する
				request.setAttribute("listRole", listRole);//役職リストを保存する
			}
			if (action.equals("create")) {
				logger.info("CREATE USER");
				viewLink = "/views/admin/create.jsp";
				List<Gender> listGender = genderService.getListGender();
				List<Role> listRole = roleService.getListRole();
				request.setAttribute("listGender", listGender);
				request.setAttribute("listRole", listRole);
				request.setAttribute("model", new User());
			}
			if (action.equals("update")) {
				logger.info("UPDATE USER");
				viewLink = "/views/admin/update.jsp";
				List<Gender> listGender = genderService.getListGender();
				List<Role> listRole = roleService.getListRole();
				String userId = request.getParameter("userId");
				User user = userService.getUser(userId);
				request.setAttribute("listGender", listGender);
				request.setAttribute("listRole", listRole);
				request.setAttribute("model", user);
			}

		}

		RequestDispatcher rd = request.getRequestDispatcher(viewLink);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");// アクションタイプを取る
		logger.info(action);
		String viewLink = "";//ビューのリングを保存する
		if (action != null) {
			if (action.equals("search")) {
				viewLink = "/views/admin/home.jsp";//リングをセットする
				List<Role> listRole = roleService.getListRole();//全ての役職を取る
				logger.info("SEARCH");
				List<User> listUser = userService.search(request.getParameter("familyName"),
						request.getParameter("firstName"), Integer.valueOf(request.getParameter("authorityId")));//パラメータを取って、見つける
				request.setAttribute("listUser", listUser);//ユーザリストを保存する
				request.setAttribute("listRole", listRole);//役職リストを保存する
				if (listUser == null || listUser.size() == 0) {
					request.setAttribute("message", "※ユーザが見つかりません。");//見つからない場合、メッセージをセットする
				}

			}
			if (action.equals("create")) {
				User newUser = mapForm(request);
				logger.info("SAVE NEW USER");
				ValidateUser validator = new ValidateUser();
				String message = validator.validate(newUser);//ユーザのフィールドをバリデーションチェック、エラーをセットする
				if (message.equals("") == false) {
					request.setAttribute("message", message);
					viewLink = "/views/admin/create.jsp";
				} else {
					boolean rs = userService.createUser(newUser);//登録する
					if (rs) {//成功場合
						request.setAttribute("message", "登録完了");
						viewLink = "/views/admin/success.jsp";
					} else {//失敗場合/
						request.setAttribute("message", "※ ユーザIDが重複しています。");
						viewLink = "/views/admin/create.jsp";
					}
				}
				request.setAttribute("model", newUser);//入力したユーザ情報を返す

			}
			if (action.equals("update")) {
				logger.info("SAVE UPDATE USER");
				User updateUser = mapForm(request);
				ValidateUser validator = new ValidateUser();
				String message = validator.validate(updateUser);//ユーザのフィールドをバリデーションチェック、エラーをセットする
				if (message.equals("") == false) {
					request.setAttribute("message", message);//更新失敗する
					viewLink = "/views/admin/update.jsp";
				} else {
					boolean rs = userService.updateUser(updateUser);//更新する
					if (rs) {//成功場合
						request.setAttribute("message", "更新完了");
						viewLink = "/views/admin/success.jsp";
					} else {//失敗場合/
						request.setAttribute("message", "※ 更新失敗しました。");
						viewLink = "/views/admin/update.jsp";
					}
				}
				request.setAttribute("model", updateUser);//入力したユーザ情報を返す
			}

		}
		RequestDispatcher rd = request.getRequestDispatcher(viewLink);
		rd.forward(request, response);
	}

	private User mapForm(HttpServletRequest request) {
		User newUser = new User();
		newUser.setUserId(request.getParameter("userId"));
		newUser.setPassword(request.getParameter("password"));
		newUser.setFamilyName(request.getParameter("familyName"));
		newUser.setFirstName(request.getParameter("firstName"));
		newUser.setGenderId(Integer.parseInt(request.getParameter("genderId")));
		newUser.setAuthorityId(Integer.parseInt(request.getParameter("authorityId")));
		if (request.getParameter("admin") == null) {
			newUser.setAdmin(0);
		} else
			newUser.setAdmin(1);
		newUser.setCreateUserId(request.getSession().getAttribute("currentUser").toString());
		newUser.setUpdateUserId(request.getSession().getAttribute("currentUser").toString());
		if (request.getParameter("age")==null || request.getParameter("age").equals("")==true) {
			newUser.setAge(-1);//年齢未入力
		}else {
			newUser.setAge(Integer.valueOf(request.getParameter("age")));
		}
		return newUser;
	}
}
