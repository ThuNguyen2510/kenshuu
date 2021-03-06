<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登録</title>
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
.errorTxt {
	min-height: 20px;
	margin-top: 2%
}

h3 {
	padding-left: 15%;
	color: red
}
</style>
	<font style="padding: 30px;" size="8">登録</font>

	<div class="errorTxt">
		<c:if test="${not empty message}">
			<h3>${message}</h3>
		</c:if>
	</div>

	<div id="layoutSidenav_content">
		<main>
		<div class="container-fluid" style="margin-top: 40px">
			<div class="card mb-4" style="padding-left: 10%">
				<form action="<c:url value='/admin-user'/>" name="registration"
					method="post">
					<div class="card-body">
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>ユーザID:</strong>
									</div>
									<div class="col-md-8">
										<input style="color: red" id="userId" type="text"
											value="${model.userId}" name="userId" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>パスワード:</strong>
									</div>
									<div class="col-md-8">
										<input id="password" name="password" value="${model.password}"
											type="password">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>姓:</strong>
									</div>
									<div class="col-md-8">
										<input type="text" id="familyname" name="familyName"
											value="${model.familyName}">
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>名:</strong>
									</div>
									<div class="col-md-8">
										<input name="firstName" id="firstname" type="text"
											value="${model.firstName}" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>性別:</strong>
									</div>
									<div class="col-md-8">
										<select name="genderId" style="width: 182px; height: 30px">
											<option value="-1"></option>
											<c:if test="${empty model.genderId}">
												<c:forEach var="item" items="${listGender}">
													<option value="${item.genderId}">${item.genderName}</option>
												</c:forEach>
											</c:if>
											<c:if test="${not empty model.genderId}">
												<c:forEach var="item" items="${listGender}">
													<option value="${item.genderId}"
														<c:if test="${item.genderId == model.genderId}">selected="selected"</c:if>>${item.genderName}
													</option>
												</c:forEach>
											</c:if>
										</select>

									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>年齢:</strong>
									</div>
									<div class="col-md-8">
										<input id="age" name="age" type="text"
											<c:if test="${model.age!='-1'}">value="${model.age}"</c:if>
											style="text-align: right" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>役職:</strong>
									</div>
									<div class="col-md-8">
										<select name="authorityId" style="width: 182px; height: 30px">
											<option value="-1"></option>
											<c:if test="${empty model.authorityId}">
												<c:forEach var="item" items="${listRole}">
													<option value="${item.authorityId}">${item.authorityName}</option>
												</c:forEach>
											</c:if>
											<c:if test="${not empty model.authorityId}">
												<c:forEach var="item" items="${listRole}">
													<option value="${item.authorityId}"
														<c:if test="${item.authorityId == model.authorityId}">selected="selected"</c:if>>${item.authorityName}
													</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>管理者:</strong>
									</div>
									<div class="col-md-8">
										<input type="checkbox" id="admin"
											style="width: 20px; height: 20px;" value="${model.admin}"
											name="admin">
									</div>
								</div>

							</div>
						</div>
						<div class="form-group row" style="padding-top: 40px">
							<div class="col-sm-5">
								<button id="back"
									style="margin-left: 60%; border-radius: 10px; width: 30%; height: 40px; font-size: 20px; font-weight: 600;"
									type="button">戻る</button>
							</div>
							<div class="col-sm-5">
								<input type="hidden" name="action" value="create">
								<button id="create" type="submit"
									style="border-radius: 10px; width: 30%; height: 40px; font-size: 20px; font-weight: 600;">登録</button>
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
		$("input[type='checkbox']").on('change', function() {
			$(this).val(this.checked ? "1" : "0");
		})
		$("#create").click(function() {
			if (confirm("登録してよろしいですか？") == false)
				return false;

		});
	</script>

</body>
<style>
.btn {
	width: 100px
}
</style>
</html>
