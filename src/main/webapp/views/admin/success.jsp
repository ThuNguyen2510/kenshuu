<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登録完了</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/template/admin/js/form-validation.js"/>"></script>
</head>

<body>
	<style>
h3 {
	padding-left: 15%;
	color: blue
}
</style>
	<font style="padding: 30px;" size="8">登録完了</font>

	<div class="errorTxt">
		<c:if test="${not empty message}">
			<h3>${message}</h3>
		</c:if>
	</div>
	<div id="layoutSidenav_content">
		<main>
		<div class="container-fluid" style="margin-top: 50px">
			<div  style="padding-left: 10%">
				<form  action="<c:url value='/admin-user'/>" method="get">
				<input type="hidden" name="action" value="get">
					<button class="btn btn-secondary" style="margin-left: 20%">一覧へ戻る</button>
				</form>

			</div>
		</div>
		</main>
	</div>
</body>
</html>