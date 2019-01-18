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
<script src="${contextPath }/jsp/js/admin/addAdminPermissions.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/admin/showadmins.do">管理员</a></li>
			<li class="active">新增权限</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6 ">
						<div class="form-horizontal" role="form">
							<div class="form-group">
								<input type="hidden" id="id" value="${vo.id }" />
								<label for="aname" class="col-sm-2 control-label">权限名称</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="permissionsName" value="${vo.permissionsName }">
								</div>
							</div>
							
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">权限URL</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="permissions" value="${vo.permissions }">
								</div>
							</div>
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">父级菜单</label>
								<div class="col-sm-6">
									<select class="form-control" id="levels">
											<option value="">==选择==</option>
											<c:forEach items="${listAdminPermissions }" var="adminPermissions">
												<option value="${adminPermissions.id }" 
													<c:if test="${adminPermissions.levels==0}">
													<c:if test="${adminPermissions.id==vo.id}">
														selected
													</c:if>
													</c:if>
													<c:if test="${vo.levels!=0}">
													<c:if test="${adminPermissions.id==vo.levels}">
														selected
													</c:if>
													</c:if>
												>${adminPermissions.permissionsName } </option>
											</c:forEach>								
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">排列序号</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="sequence" value="${vo.sequence }">
								</div>
							</div>

							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">权限类型</label>
								<div class="col-sm-6">
									<select class="form-control" id="status">
											<option value="">==选择==</option>
											<option value="10002" <c:if test="${vo.status==10002 }">selected</c:if> >菜单栏展示</option>
											<option value="1" <c:if test="${vo.status==1 }">selected</c:if> >功能权限管理</option>
									</select>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-danger _addBtn">确定保存</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
