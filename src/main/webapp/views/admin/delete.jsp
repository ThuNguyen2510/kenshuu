<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>削除</title>
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
	padding-top: 2%;
	font-weight: bold;
}
</style>
	<font style="padding: 30px;" size="8">削除</font>
	<div>
		<c:if test="${ empty message}">
			<h3>以下のデータを削除してよろしいですか。</h3>
		</c:if>
		<c:if test="${ not empty message}">
			<h3 style="color:red">${message}</h3>
		</c:if>
	</div>

	<div id="layoutSidenav_content">
		<main>
		<div class="container" style="margin-top: 20px">
			<div class="card mb-4" style="padding-left: 10%">
				<form action="<c:url value='/admin-user'/>" method="post">
					<div class="card-body" style="padding-left: 25%; font-size: 20px;">
						<div class="row">
							<strong>ユーザID:&nbsp; &nbsp; ${model.userId}</strong>
						</div>
						<div class="row">
							<strong>氏名:&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
								&nbsp;${model.familyName} ${model.firstName} </strong>
						</div>

					</div>
					<input type="hidden" name="userId" value="${model.userId }">
					<div class="form-group row" style="padding-top: 40px">
						<div class="col-sm-5">
							<button id="back" style="margin-left: 60%" type="button"
								class="btn btn-secondary">戻る</button>
						</div>
						<div class="col-sm-5">
							<input type="hidden" name="action" value="delete">
							<button id="create" type="submit" class="btn btn-primary">削除</button>
						</div>
					</div>
			</div>
			</form>
		</div>
	</div>
	</main>
	</div>
	<script>
		$("#back").click(function() {
			window.location.href = "/kenshuu/admin-user?action=get";
		});
	</script>

</body>
<style>
.btn {
	width: 100px
}
</style>
</html>
