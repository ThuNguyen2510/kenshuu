package com.ks.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class doFilterInternal implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//含まれるデータの文字コードを指定した値に書き換える
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();//リングを取る
		if (uri.startsWith("/kenshuu/admin") || uri.startsWith("/kenshuu/total") || uri.startsWith("/kenshuu/report")) {
			response.setContentType("UTF-8");
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");

		} else if (uri.startsWith("/kenshuu/api")) {
			// Authorizationをヘッダーから取得する
			String authorizationHeader = req.getHeader("Authorization");
			if (!isTokenBasedAuthentication(authorizationHeader)) {//ヌルと”Bearer ”の始めに含むことをチェックして、適切じゃない場合
				abortWithUnauthorized((HttpServletResponse) response);
				return;
			}
			String token = authorizationHeader.substring("Bearer".length()).trim();//”Bearer ”を Authorizationから切って、JWTを取得する
			try {
				// ヴァリデーション
				if (JWTGenerate.isTokenExpired(token)) {
					abortWithUnauthorized((HttpServletResponse) response);
					return;
				}

			} catch (Exception e) {
				abortWithUnauthorized((HttpServletResponse) response);
			}
		}
		chain.doFilter(request, response);
	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {

		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith("Bearer".toLowerCase() + " ");
	}

	private void abortWithUnauthorized(HttpServletResponse response) {

			try {
			PrintWriter out;
			out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print("wrong_jwt");//”wrong_jwt”を返す
			out.flush();
			out.close();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
