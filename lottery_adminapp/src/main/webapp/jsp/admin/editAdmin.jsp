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
<script src="${contextPath }/jsp/js/admin/editAdmin.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/admin/showadmins.do">管理员</a></li>
			<li class="active">修改管理员</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6 ">
						<div class="form-horizontal" role="form">
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-6">
								<input type="hidden" id="id" value="${auvo.id }">
									<input type="text" class="form-control" id="adminName" value="${auvo.userName }">
								</div>
							</div>
							
								<div class="form-group">
									<label for="aname" class="col-sm-2 control-label">权限组</label>
									<div class="col-sm-6">
										<select class="form-control" id="userGroup">
											<option value="1" <c:if test="${auvo.userGroup == 1 }">selected</c:if> >财务组</option>
											<option value="2" <c:if test="${auvo.userGroup == 2 }">selected</c:if> >用户组</option>
										</select>
									</div>
								</div>
								<!-- 
								<div class="form-group">
									<label for="aname" class="col-sm-2 control-label">角色</label>
									<div class="col-sm-6">
										<select class="form-control">
											<option>管理员</option>
											<option>充值</option>
										</select>
									</div>
								</div>
							-->
							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">登录密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="adminPwd" value="${auvo.userPwd}"/>
								</div>
							</div>

							<div class="form-group">
								<label for="aname" class="col-sm-2 control-label">管理密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="adminRolePwd" value="${auvo.userRolePwd}"/>
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
									<button type="submit" class="btn btn-danger _editBtn">确定修改</button>
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
