<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript"
	src="${contextPath }/jsp/js/admin/manageMent.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">权限管理</div>
			<div class="panel-body clearfix">
				<div class="pull-left">
					<a href="${contextPath }/ment/showaddPermissions.do?id=0"
						class="btn btn-warning">新增权限</a>
				</div>
			</div>
		</div>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-1">权限名称</label>
				<div class="col-sm-2">
					<input type="text" class="form-control _param" id="permissionsName" value="${vo.permissionsName }"/>
				</div>
				<div class="col-sm-2">
					<button class="btn btn-primary btn-query _querybtn">立即查询</button>
				</div>
			</div>
		</div>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>权限名称</td>
					<td>权限URL</td>
					<td>排列序号</td>
					<td>创建人</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.entitylist}" var="adminPermissions">
					<tr>
						<td>${adminPermissions.id }</td>
						<td>${adminPermissions.permissionsName }</td>
						<td>${adminPermissions.permissions }</td>
						<td>${adminPermissions.sequence }</td>
						<td>${adminPermissions.createUser }</td>
						<td><fmt:formatDate value="${adminPermissions.createTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><a href="showaddPermissions.do?id=${adminPermissions.id }" class="btn btn-link btn-sm">修改权限</a>
										   <a href="delAdminPer.do?id=${adminPermissions.id }"  class="btn btn-link btn-sm">删除权限</a>
						</td>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
			<li><a href="javaScript:void(0);"
				onclick="permissionsItems.queryData(${page.pageNum-1 })" class="_prePage">上一页</a></li>
			第${page.pageNum}页/共${page.pageCount }页
			<li><a href="javaScript:void(0);"
				onclick="permissionsItems.queryData(${page.pageNum+1 })" class="_nextPage">下一页</a></li>
		</ul>
	</div>
	<!-- /.modal -->
</body>
</html>