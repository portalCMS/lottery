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
<script src="${contextPath }/jsp/js/user/saveLoginPwd.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if('${param.success}'!=""){
		$("#error_content").html(decodeURI("${param.success}"));
		$("#error_msg_modal").modal("show");
	}
	if('${param.errorMsg}'!=""){
		$("#error_content").html(decodeURI("${param.errorMsg}"));
		$("#error_msg_modal").modal("show");
	}
	
});
</script>
</head>
<body>
<%@ include file="../include/top.jsp"%>
	<div class="container">
	<form action="${contextPath }/user/saveLoginPwd.do" method="post" id="setLoginPwdForm"> 
		<input type="hidden" name="token" value="${token}">
		<div class="panel panel-info">
			<div class="panel-heading">基本信息</div>
			<div class="panel-body">
				<div class="form-horizontal" role="form">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >初始登录密码</label>
								<div class="col-sm-6">
									<input type="hidden" class="form-control" id="userId" name="userId" value="${userId }">
									<input type="password" class="form-control" id="customerPwd" name="customerPwd">
								</div>
							</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >新密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="newPwd" name="newPwd">
								</div>
							</div>
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label" >确认密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="confirmPwd" name="confirmPwd">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" class="btn btn-danger" onclick="submitForm()">确定修改</button>
			</div>
		</div>
	</form>
</body>
</html>
