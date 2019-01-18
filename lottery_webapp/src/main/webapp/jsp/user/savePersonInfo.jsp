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
<script src="${contextPath }/jsp/js/user/savePersonInfo.js"></script>
</head>
<body>
<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.jsp">首页</a></li>
			<li><a href="users.jsp">邀请注册</a></li>
		</ol>
	<form action="${contextPath }/user/savePersonInfo.do" method="post" id="savePersonInfoForm"> 
		<input type="hidden" name="token" value="${token}">
		<div class="panel panel-info">
			<div class="panel-heading">基本资料</div>
			<div class="panel-body">
				<div class="form-horizontal" role="form">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >昵称：</label>
								<div class="col-sm-6">
									<input type="hidden" class="form-control" id="userId" name="userId" value="${userId }">
									<input type="text" class="form-control" id="customerAlias" name="customerAlias">
								</div>
							</div>
							<div class="panel-heading">联系信息</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >QQ：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="qq" name="qq">
								</div>
							</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >Email：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="email" name="email">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" class="btn btn-danger" onclick="submitForm()">提交</button>
			</div>
		</div>
	</form>
</body>
</html>
