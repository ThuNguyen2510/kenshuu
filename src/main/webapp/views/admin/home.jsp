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
		<div>
			<h5 style="padding-left: 20px; font-weight: 600;">${message}</h5>
		</div>
	</c:if>
	<div id="layoutSidenav_content">
		<main>
		<div class="container-fluid" style="margin-top: 20px">
			<div class="card mb-4">
				<form method="post" id="searchListForm">
					<div class="card-body">
						<div class="form-group row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-2">
										<strong>姓:</strong>
									</div>
									<div class="col-md-8">
										<input type="text" name="familyName" value="${familyName}" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-2">
										<strong>名:</strong>
									</div>
									<div class="col-md-8">
										<input name="firstName" type="text" value="${firstName}" />
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
												<option value="${item.authorityId}"
													<c:if test="${item.authorityId == authorityId}">selected="selected"</c:if>>
													${item.authorityName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<input type="hidden" value="search" name="action" />
								<div class="row">
									<div class="col-md-5">
										<button id="report" type="button"
											style="margin-left: 100px; width: 150px; border-radius: 10px; font-size: 20px; font-weight: 600;">リスト</button>
									</div>
									<div class="col-md-5">
										<button id="search" type="button"
											style="width: 150px; border-radius: 10px; font-size: 20px; font-weight: 600;">検索</button>

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
								<th>
									<form action="<c:url value='/admin-user'/>" method="get">
										<input type="hidden" value="create" name="action" />
										<button style="width: 100%; border-radius: 10px">登録</button>
									</form>
								</th>
							</tr>
						</thead>

						<tbody>
							<c:set var="i" value="${1}" />
							<c:forEach var="item" items="${listUser}">
								<tr>
									<td style="text-align: right">${i}</td>
									<td><c:out value="${item.userId}"></c:out></td>
									<td><c:out value="${item.familyName}"></c:out> &nbsp;<c:out
											value="${item.firstName}"></c:out></td>
									<td>${item.gender.genderName}</td>
									<td style="text-align: right"><c:if
											test="${item.age!='-1'}">${item.age}</c:if> <c:if
											test="${item.age=='-1'}"></c:if></td>
									<td><c:if test="${item.admin==1}">
											<span class="fa fa-star"></span>
										</c:if>${item.role.authorityName}</td>
									<td>

										<button style="width: 45%; border-radius: 10px"
											onclick="window.location.href='/kenshuu/admin-user?action=update&userId=${item.userId}'">更新</button>

										<button style="width: 45%; border-radius: 10px"
											onclick="window.location.href='/kenshuu/admin-user?action=delete&userId=${item.userId}'">削除</button>

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
	<script>
		$('#search').click(function() {
			$('#searchListForm').attr('action', '/kenshuu/admin-user');
			document.getElementById('searchListForm').submit();
		});
		$('#report').click(function() {
			$('#searchListForm').attr('action', '/kenshuu/report');
			document.getElementById('searchListForm').submit();
		});
	</script>
</body>
</html>