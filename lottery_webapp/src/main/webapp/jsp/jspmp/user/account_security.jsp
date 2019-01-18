<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>账户安全</title>
	<%@ include file="../include/js_asset.jsp"%>
	<%@ include file="../include/css_asset.jsp"%>
	<script src="${contextPath }/jsp/jspmp/js/user/account_security.js"></script>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span>账户安全</span></div>
</header>

<aside></aside>

<section class="container security">
	<div class="panel panel-warn">修改登陆密码及财务密码请在网页端完成</div>
	<h2>操作日志</h2>
	<table class="table">
		<tr>
			<th width="2500">id</th>
			<th width="2000">时间</th>
			<th width="3000">操作</th>
			<th width="2500">IP</th>
		</tr>
			<tbody class="_tbody">
			</tbody>
		</table>
	</table>

	<div class="pager _pageInfo">
	</div>
</section>


</body>
</html>