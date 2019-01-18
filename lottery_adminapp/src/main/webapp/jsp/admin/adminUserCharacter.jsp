<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告列表</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript"
	src="${contextPath }/jsp/js/admin/adminUserCharacter.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">角色权限分配</div>
			<div class="panel-body">
				<button type="submit" class="btn btn-danger _savepermissionsBtn">确定增加</button>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-inline clearfix">
					<div class="form-group ">
						<label class="control-label">角色列表：</label> 
						<select class="form-control" id="adminRoleType">
							<option value="">===请选择角色===</option>
							<c:forEach items="${listAdminRoleVO }" var="adminRole">
								<option value="${adminRole.id }" class="_${adminRole.id }">${adminRole.roleName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div id='adminGroup'>
		</div>
	</div>
</body>
</html>