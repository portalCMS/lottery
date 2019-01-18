<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<c:if test="${userkey.userGroup==0 }">
			<div class="panel panel-default">
				<div class="panel-body">
					<a href="showaddadmins.do" class="btn btn-warning">添加新管理员</a>
					<a href="showaddcharacter.do" class="btn btn-warning">角色权限管理</a>
				</div>
			</div>
		</c:if>
		<table
			class="table table-hover table-bordered table-striped table-condensed clear_margin_bottom">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>用户名</td>
					<td>状态</td>
					<td>权限组</td>
					<td>角色</td>
					<td>登录次数</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${admins }" var="admin" varStatus="str">
					<tr>
						<td>${str.index+1 }</td>
						<td>${admin.userName }</td>
						<td><label class="label label-success">正常</label></td>
						<td>用户组</td>
						<td>用户管理员</td>
						<td>21</td>
						<td align="center"><a href="admin.html" class="btn btn-link btn-sm">查看详情</a>
										   <a href="showupdateadmins.do?id=${admin.id }" class="btn btn-link btn-sm">修改</a>
										   <a href="addcharacter.do?id=${admin.id }"  class="btn btn-link btn-sm">分配角色</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>
