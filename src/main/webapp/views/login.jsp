<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="<c:url value='/template/login/style.css' />"
	rel="stylesheet" type="text/css" media="all" />
<link rel="icon" type="image/png"
	href="<c:url value='/template/login/images/icons/favicon.ico' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/fonts/font-awesome-4.7.0/css/font-awesome.min.css' />">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/fonts/Linearicons-Free-v1.0.0/icon-font.min.css' />">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/vendor/animate/animate.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/vendor/css-hamburgers/hamburgers.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/vendor/animsition/css/animsition.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/vendor/select2/select2.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/vendor/daterangepicker/daterangepicker.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/css/util.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/template/login/css/main.css'/>">
</head>
<body>
	<div class="login-form">
		<div class="limiter">
			<div class="container-login100">
				<div class="wrap-login100">
					<div class="login100-form-title">
						<span class="login100-form-title-1"> ログイン </span>
					</div>
					<c:if test="${not empty message}">
						<div class="alert alert-${alert}" style="text-align: center">${message}</div>
					</c:if>
					<form action="<c:url value='/login'/>" id="formLogin" method="post"
						class="login100-form validate-form">
						<div class="wrap-input100 validate-input m-b-26">
							<span class="label-input100">ユーザID</span> <input class="input100"
								type="text" name="userId" placeholder="ユーザⅠD　入力"> <span
								class="focus-input100"></span>
						</div>

						<div class="wrap-input100 validate-input m-b-18">
							<span class="label-input100">パスワード</span> <input class="input100"
								type="password" name="password" placeholder="パスワード　入力">
							<span class="focus-input100"></span>
						</div>

						<input type="hidden" value="login" name="action" />
						<div class="container-login100-form-btn">
							<button type="submit" class="login100-form-btn">ログイン</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>