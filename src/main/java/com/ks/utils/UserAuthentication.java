package com.ks.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class UserAuthentication implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		boolean loggedIn = session != null && session.getAttribute("currentUser") != null;
		String uri = request.getRequestURI();//リングを取る
		if (uri.startsWith("/kenshuu/admin") || uri.startsWith("/kenshuu/total") || uri.startsWith("/kenshuu/report")) {
			if (loggedIn ) {//ログインしている場合、次に行ける
				chain.doFilter(request, response);
			} else {//ログインしていない場合、ログイン画面に遷移して、メッセージ表示される
				request.setAttribute("message", "※ログインしてください。");
				request.setAttribute("alert", "danger");
				RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
				rd.forward(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
