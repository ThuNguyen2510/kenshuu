<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
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
	<%@ include file="/views/layout/header.jsp"%>
	<c:if test="${not empty message}">
	<div >
	<h5 style="padding-left: 20px;color:red">${message}</h5>
	</div>
	</c:if>
	<div id="layoutSidenav_content">
		<main>
		<div class="container-fluid" style="margin-top:20px">
			<div class="card mb-4">
				<form action="<c:url value='/admin-user'/>" method="post">
					<div class="card-body">
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-2">
										<strong>姓:</strong>
									</div>
									<div class="col-md-8">
										<input type="text" name="familyName" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-2">
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
									<div class="col-md-2">
										<strong>役職:</strong>
									</div>
									<div class="col-md-8">
										<select name="authorityId" style="width: 182px; height: 30px">
											<option value="-1"></option>

											<c:forEach var="item" items="${listRole}">
												<option value="${item.authorityId}">
													${item.authorityName}</option>
											</c:forEach>
										</select> </select>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<input type="hidden" value="search" name="action" />

								<div class="row">
									<div class="col-md-5">
										<button id="report" type="button" class="btn btn-primary"
											style="margin-left: 100px; width: 150px; border-radius: 10px;">リスト</button>
									</div>

									<div class="col-md-5">

										<button id="search" type="submit" class="btn btn-primary"
											style="width: 150px; border-radius: 10px;">検索</button>

									</div>
								</div>

							</div>
						</div>
					</div>
				</form>
			</div>


		</div>
		<div class="">
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered" id="dataTable" width="100%"
						cellspacing="0">
						<thead>
							<tr>
								<th>No</th>
								<th>ユーザID</th>
								<th>氏名</th>
								<th>性別</th>
								<th>年齢</th>
								<th>役職</th>
								<th><button style="width: 100%" class="btn btn-primary">登録</button></th>
							</tr>
						</thead>

						<tbody>
							<c:set var="i" value="${1}" />
							<c:forEach var="item" items="${listUser}">
								<tr>
									<td style="text-align: right">${i}</td>
									<td>${item.userId}</td>
									<td>${item.familyName}${item.firstName}</td>
									<td>${item.gender.genderName}</td>
									<td style="text-align: right">${item.age}</td>
									<td><c:if test="${item.admin==1}">
											<span class="fa fa-star"></span>
										</c:if>${item.role.authorityName}</td>
									<td>
										<button style="width: 45%" class="btn btn-warning">変更</button>
										<button style="width: 45%" class="btn btn-danger">削除</button>
									</td>
								</tr>
								<c:set var="i" value="${i+1}" />
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	</main>
	</div>
</body>
</html>