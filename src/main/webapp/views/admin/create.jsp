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
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<font style="padding: 30px;" size="8">登録</font>

	<div>
		<c:if test="${not empty message}">
			<h3 style="padding-left: 15%; color: red">${message}</h3>
		</c:if>
	</div>

	<div id="layoutSidenav_content">
		<main>
		<div class="container-fluid" style="margin-top: 100px">
			<div class="card mb-4" style="padding-left: 10%">
				<form action="<c:url value='/admin-user'/>" method="post">
					<div class="card-body">
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>ユーザID:</strong>
									</div>
									<div class="col-md-8">
										<input type="text" name="userId" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>パスワード:</strong>
									</div>
									<div class="col-md-8">
										<input name="password" type="text" />
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
										<input type="text" name="familyName" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-3">
										<strong>名:</strong>
									</div>
									<div class="col-md-8">
										<input name="firstName" type="text" />
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

											<c:forEach var="item" items="${listGender}">
												<option value="${item.genderId}">
													${item.genderName}</option>
											</c:forEach>
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
										<input name="age" type="text" />
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
											<c:forEach var="item" items="${listRole}">
												<option value="${item.authorityId}">
													${item.authorityName}</option>
											</c:forEach>
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
										<input type="checkbox" id="admin" style="width: 20px; height: 20px;" value="0"
											name="admin">
									</div>
								</div>

							</div>
						</div>
						<div class="form-group row" style="padding-top: 40px">
							<div class="col-sm-5">
								<button id="back" style="margin-left: 60%"
									class="btn btn-secondary">戻る</button>
							</div>
							<div class="col-sm-5">
								<button id ="create" name="action" type="submit"
									class="btn btn-primary">登録</button>
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
			window.location.href="/kenshuu/admin-user?action=get";
		});
		$("input[type='checkbox']").on('change', function(){
			  $(this).val(this.checked ? "1" : "0");
			})
		$("#create").click(function() {
			if(confirm("登録してよろしいですか？")==false)
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