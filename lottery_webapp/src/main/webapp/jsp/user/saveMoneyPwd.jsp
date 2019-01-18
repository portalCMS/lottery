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
<script src="${contextPath }/jsp/js/user/saveMoneyPwd.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if('${success}'!=""){
		$("#error_content").html(decodeURI("${success}"));
		$("#error_msg_modal").modal("show");
	}
	if('${errorMsg}'!=""){
		$("#error_content").html(decodeURI("${errorMsg}"));
		$("#error_msg_modal").modal("show");
	}
	
});
</script>
</head>
<body>
<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.jsp">首页</a></li>
			<li><a href="users.jsp">邀请注册</a></li>
		</ol>
	</div>
	<form action="${contextPath }/user/saveMoneyPwd.do" method="post" id="saveMoneyPwdForm"> 
		<input type="hidden" name="token" value="${token}">
		<div class="panel panel-info">
			<div class="panel-heading">设置账户安全</div>
			<div class="panel-body">
				<div class="form-horizontal" role="form">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >资金密码</label>
								<div class="col-sm-6">
									<input type="hidden" class="form-control" id="userId" name="userId" value="${userId }">
									<input type="password" class="form-control" id="moneyPwd" name="moneyPwd">
									<input type="hidden" class="form-control" id="newPwd" name="newPwd">
								</div>
							</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >确认密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="confirmPwd" name="confirmPwd">
								</div>
							</div>
							<div class="panel-heading">设置安全问题</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >安全问题</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="question" name="question">
								</div>
							</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >安全答案</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="answer" name="answer">
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
