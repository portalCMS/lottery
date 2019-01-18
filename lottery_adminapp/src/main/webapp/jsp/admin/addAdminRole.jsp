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
<script src="${contextPath }/jsp/js/admin/addAdminRole.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/admin/showadmins.do">管理员</a></li>
			<li class="active">新增角色</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6 ">
						<div class="form-horizontal" role="form">
							<div class="form-group">
								<input type="hidden" id="id" value="${rolevo.id }" />
								<label for="aname" class="col-sm-2 control-label">角色名称</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="roleName" value="${rolevo.roleName }">
								</div>
							</div>
							
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">英文名称</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="roleEnName" value="${rolevo.roleEnName }">
								</div>
							</div>

							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">角色级别</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="roleLevel" value="${rolevo.roleLevel }">
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
								<label for="adminpwd" class="col-sm-2 control-label">后台密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="amPwd"
										placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="picCode">
								</div>
								<div class="col-sm-5 col-sm-pull-0">
									<img
										src="#"
										width="100" height="35" id="code"/>
								</div>
							</div>
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
