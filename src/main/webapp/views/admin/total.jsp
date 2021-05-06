<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>役職別集計</title>
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
</head>
<body>
	<nav class="sb-topnav navbar navbar-expand "> <font size="8">役職別集計</font>
	<form
		class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
	</form>
	<a class="nav-link" href="/kenshuu/admin-user?action=get"
		style="padding-right: 5%">一覧</a> </nav>
	<c:if test="${not empty message}">
		<div>
			<h5 style="padding-left: 5%; color: blue; font-size: 25px;">${message}</h5>
		</div>
	</c:if>
	<div class="container-fluid" style="margin-top: 50px">
		<form action="<c:url value='/total'/>" method="post">
			<input type="hidden" value="get" name="action" />
			<button type="submit"
				style="margin-left: 85%; width: 120px;font-size: 20px; font-weight: 600;border-radius:10px">集計</button>
		</form>
	</div>
	<div id="layoutSidenav_content">
		<main>
		<div class="card-body">
			<div class="table-responsive" style="padding: 0 5% 0 5%">
				<table class="table table-bordered" id="dataTable" width="100%"
					style="text-align: center" cellspacing="0">
					<thead>
						<tr>
							<th>No</th>
							<th>役職</th>
							<th>男の人数</th>
							<th>女の人数</th>
							<th style="color: red">未登録<br />の人数
							</th>
							<th>年齢別人数<br />(0-19)
							</th>
							<th>年齢別人数<br />(20以上)
							</th>
							<th>年齢別人数<br>(未登録）
							</th>
						</tr>
					</thead>

					<tbody>
						<c:set var="i" value="${1}" />
						<c:forEach var="item" items="${listCount}">
							<tr>
								<td style="text-align: right">${i}</td>
								<td>${item.authorityName}</td>
								<td>${item.male}</td>
								<td>${item.female}</td>
								<td style="color: red">${item.nullGender}</td>
								<td>${item.under19}</td>
								<td>${item.over20}</td>
								<td>${item.nullAge}</td>
							</tr>
							<c:set var="i" value="${i+1}" />
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
		</main>
	</div>
</body>
</html>