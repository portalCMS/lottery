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
	src="${contextPath }/jsp/js/admin/adminUserRole.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">用户角色分配</div>
			<div class="panel-body">
				<button type="submit" class="btn btn-danger _saveUserRoleBtn">确定增加</button>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-inline clearfix">
					<div class="form-group ">
					<label class="control-label">用户明细：</label>
					<label class="checkbox-inline">
						<input type="hidden" id="userId" value=" ${adminuser.id }"/>
						<div>用户名称： ${adminuser.userName }</div>
						<div>用户昵称： ${adminuser.userAlias }</div>
						<c:if test="${adminuser.userGroup==0}">
							<div>用户分组：所有</div>
						</c:if>
						<c:if test="${adminuser.userGroup==1}">
							<div>用户分组：财务组</div>
						</c:if>
						<c:if test="${adminuser.userGroup==2}">
							<div>用户分组：用户组</div>
						</c:if>
				    </label>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-inline clearfix">
					<div class="form-group ">
						<label class="control-label">角色列表：</label> <label
							class="checkbox-inline"> <c:forEach var="adminRole"
								items="${listAdminRoleVO }">
								<div>
							<label
							class="checkbox-inline">
								<input type="checkbox" id="${adminRole.id }"  name="adminuserrolecheck" value="${adminRole.id }"
								<c:forEach var="adminUserRole" items="${listadminUserRole }" >
								<c:if test="${adminRole.id==adminUserRole.roleId}">checked</c:if> 
								</c:forEach>
								/> ${adminRole.roleName }<br>
							</label>
							</div>
							</c:forEach>
						</label>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>