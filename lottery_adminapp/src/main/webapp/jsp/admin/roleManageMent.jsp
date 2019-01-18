<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript"
	src="${contextPath }/jsp/js/admin/roleManageMent.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">角色管理</div>
			<div class="panel-body clearfix">
				<div class="pull-left">
					<a href="${contextPath }/admin/showAdminRole.do?roleId=0"
						class="btn btn-warning">新增角色</a>
				</div>
			</div>
		</div>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-1">角色名称</label>
				<div class="col-sm-2">
					<input type="text" class="form-control _param" id="roleName" value="${adminRoleVo.roleName }"/>
				</div>
				<label class="control-label col-sm-1">英文名称</label>
				<div class="col-sm-2">
					<input type="text" class="form-control _param" id="roleEnName" value="${adminRoleVo.roleEnName }"/>
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
					<td>角色名称</td>
					<td>角色英文名称</td>
					<td>创建人</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.entitylist}" var="adminRole">
					<tr>
						<td>${adminRole.id }</td>
						<td>${adminRole.roleName }</td>
						<td>${adminRole.roleEnName }</td>
						<td>${adminRole.createUser }</td>
						<td><fmt:formatDate value="${adminRole.createTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><a href="showAdminRole.do?roleId=${adminRole.id }" class="btn btn-link btn-sm">修改角色</a>
										   <a href="delAdminRole.do?id=${adminRole.id }"  class="btn btn-link btn-sm">删除角色</a>
						</td>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
			<li><a href="javaScript:void(0);"
				onclick="roleItems.queryData(${page.pageNum-1 })" class="_prePage">上一页</a></li>
			第${page.pageNum}页/共${page.pageCount }页
			<li><a href="javaScript:void(0);"
				onclick="roleItems.queryData(${page.pageNum+1 })" class="_nextPage">下一页</a></li>
		</ul>
	</div>
	<!-- /.modal -->
</body>
</html>