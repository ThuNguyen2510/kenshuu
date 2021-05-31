package com.ks.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	private int checkAge;

	public UserController() {
		userService = new UserServiceImpl();
		roleService = new RoleServiceImpl();
		genderService = new GenderServiceImpl();
		checkAge = 1;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String viewLink = "";//ビューのリングを保存する
		String action = request.getParameter("action");// アクションタイプを取る
		if (action != null) {
			if (action.equals("get")) {//全てのデータを表示するアクションタイプ
				logger.info("GET LIST USER");
				viewLink = "/views/admin/home.jsp";//リングをセットする
				String message = request.getParameter("message");
				if (message != null) {
					if (message.equals("success"))
						request.setAttribute("message", "※ログインに成功しました。");
				}
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
				User user = new User();
				user.setAge(-1);
				request.setAttribute("model", user);
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
			if (action.equals("delete")) {
				logger.info("DELETE USER");
				String userId = request.getParameter("userId");
				User deleteUser = userService.getUser(userId);
				request.setAttribute("model", deleteUser);
				viewLink = "/views/admin/delete.jsp";
			}
			if (action.equals("logout")) {
				logger.info("LOG OUT");
				request.getSession().removeAttribute("currentUser");
				request.setAttribute("message", "※ログアウトしました。");
				request.setAttribute("alert", "success");
				viewLink = "/views/login.jsp";
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewLink);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");// アクションタイプを取る
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
				request.setAttribute("familyName", request.getParameter("familyName")); //入力したデータを返す
				request.setAttribute("firstName", request.getParameter("firstName"));
				request.setAttribute("authorityId", request.getParameter("authorityId"));
			}
			if (action.equals("create")) {
				logger.info("SAVE NEW USER");
				String message = "";
				User newUser = mapForm(request);
				if (checkAge == 0) {//タイプが間違った場合
					message += "※年齢は整数。";
				}
				ValidateUser validator = new ValidateUser();
				message += validator.validate(newUser);//ユーザのフィールドをバリデーションチェック、エラーをセットする
				if (message.equals("") == false) {
					request.setAttribute("message", message);
					viewLink = "/views/admin/create.jsp";
				} else {
					if (userService.getUser(newUser.getUserId()) != null) {//登録済みのユーザIDで登録した場合
						request.setAttribute("message", "※ ユーザIDが重複しています。");
						viewLink = "/views/admin/create.jsp";
					} else {
						boolean check = userService.createUser(newUser);//登録する
						if (check) {//成功場合
							request.setAttribute("message", "登録完了");
							viewLink = "/views/admin/success.jsp";
						} else {//失敗場合/
							request.setAttribute("message", "※ 登録失敗しました。");
							viewLink = "/views/admin/create.jsp";
						}
					}
				}
				request.setAttribute("model", newUser);//入力したユーザ情報を返す
				checkAge = 1;

			}
			if (action.equals("update")) {
				logger.info("SAVE UPDATE USER");
				String message = "";
				User updateUser = mapForm(request);
				if (checkAge == 0) {//タイプが間違った場合
					message += "※年齢は整数。";
				}
				ValidateUser validator = new ValidateUser();
				message += validator.validate(updateUser);//ユーザのフィールドをバリデーションチェック、エラーをセットする
				if (message.equals("") == false) {
					request.setAttribute("message", message);//更新失敗する
					viewLink = "/views/admin/update.jsp";
				} else {
					boolean check = userService.updateUser(updateUser);//更新する
					if (check) {//成功場合
						request.setAttribute("message", "更新完了");
						viewLink = "/views/admin/success.jsp";
					} else {//失敗場合/
						request.setAttribute("message", "※ 更新失敗しました。");
						viewLink = "/views/admin/update.jsp";
					}
				}
				request.setAttribute("model", updateUser);//入力したユーザ情報を返す
				checkAge = 1;
			}
			if (action.equals("delete")) {
				logger.info("DELETE USER");
				String userId = request.getParameter("userId");
				HttpSession session = request.getSession();
				if (userId.equals(session.getAttribute("currentUser"))) {//削除したいユーザはログインしている場合
					String message = "※ログインしているユーザなので、削除できません。";
					request.setAttribute("message", message);
					User deleteUser = userService.getUser(userId);
					request.setAttribute("model", deleteUser);
					viewLink = "/views/admin/delete.jsp";
				} else {
					boolean check = userService.deleteUser(userId);
					if (check) {
						viewLink = "/views/admin/success.jsp";
						request.setAttribute("message", "削除完了");//成功場合
					} else {
						String message = "※削除失敗しました。";//失敗場合
						request.setAttribute("message", message);
						User deleteUser = userService.getUser(userId);
						request.setAttribute("model", deleteUser);
						viewLink = "/views/admin/delete.jsp";
					}
				}
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
		logger.info(request.getParameter("age"));
		newUser.setFirstName(request.getParameter("firstName"));
		newUser.setGenderId(Integer.parseInt(request.getParameter("genderId")));
		newUser.setAuthorityId(Integer.parseInt(request.getParameter("authorityId")));
		if (request.getParameter("admin") == null) {
			newUser.setAdmin(0);
		} else
			newUser.setAdmin(1);
		newUser.setCreateUserId(request.getSession().getAttribute("currentUser").toString());
		newUser.setUpdateUserId(request.getSession().getAttribute("currentUser").toString());
		if (request.getParameter("age") == null || request.getParameter("age").equals("") == true) {
			newUser.setAge(-1);//年齢未入力
		} else {
			if (isInteger(request.getParameter("age")) == false) {//年齢のタイプをチェックする
				checkAge = 0;
			} else
				newUser.setAge(Integer.valueOf(request.getParameter("age")));
		}
		return newUser;
	}

	public boolean isInteger(String strNum) {
		try {
			int intValue = Integer.parseInt(strNum);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
