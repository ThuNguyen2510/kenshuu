<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<nav class="sb-topnav navbar navbar-expand "> <font size="8">一覧</font>
<form
	class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">

</form>
<!-- Navbar-->
<ul class="navbar-nav ml-auto ml-md-0">
	<li class="nav-item "><a class="nav-link" href="<c:url value='/total'/>">役職別集計</a></li>
	<li class="nav-item "><a class="nav-link" id="logout" href="<c:url value='/admin-user?action=logout'/>">ログアウト</a></li>
</ul>
<script>
$("#logout").click(function() {
	if (confirm("ログアウトしてよろしいですか？") == false)
		return false;

});
</script>
</nav>