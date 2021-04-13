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
<link href="<c:url value='/template/login/css/style.css' />"
	rel="stylesheet" type="text/css" media="all" />
</head>
<body style="background: aliceblue;">
	<div class="container">


		<div class="row" style="padding-top: 10px;">
			<font size="8">ログイン</font>
		</div>


		<div class="center-div">
			<div id="message">
				<c:if test="${not empty message}">
					<div class="alert alert-${alert}"
						style="font-size: 25px; padding-left: 20%">${message}</div>
				</c:if>
			</div>
			<form action="<c:url value='/login'/>" method="post">
				<div class="form-group row">
					<div class="col-md-5" id="userLabel">
						<span>ユーザID:</span>
					</div>
					<div class="col-md-7">
						<input type="text" name="userId">
					</div>

				</div>

				<div class="form-group row">
					<div class="col-md-5" id="passLabel">
						<span>パスワード:</span>
					</div>
					<div class="col-md-7">
						<input type="password" name="password">
					</div>
				</div>

				<input type="hidden" value="login" name="action" />
				<div class="row" id="butSubmit">
					<button class="btn btn-primary" type="submit"
						style="border-radius: 10px">ログイン</button>
				</div>

			</form>
		</div>


	</div>

</body>
</html>