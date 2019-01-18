<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath }/jsp/css/bootstrap.min.css" />
<link rel="stylesheet" href="${contextPath }/css/global.css" />
<script src="${contextPath }/jsp/js/jQuery.min.js"></script>
<script src="${contextPath }/jsp/js/bootstrap.min.js"></script>
<script src="${contextPath }/jsp/js/global.js"></script>
<script src="${contextPath }/jsp/js/bootstrap-datetimepicker.min.js"></script>
<script src="${contextPath }/jsp/js/utile.js"></script>
<script src="${contextPath }/jsp/js/login/login.js"></script>
<script>
	$(document).ready(function() {
		document.onkeydown = function(e){
	        if(!e) e = window.event;
	        if((e.keyCode || e.which) == 13){
	        	loginform.submit();
	        }
	    }
		init();
	});
</script>
</head>
<body>
<div class='container'>
	<div class="row">
	<div class="col-sm-6">
	<div class="panel panel-info">
		<div class="panel-heading">代理</div>
		<div class="panel-body">
			<div class="form-horizontal">
				<div class="">
					<form action="login.do" method="post" id="loginForm">
						<input type="hidden" name="token" id="token" value="${token }">
						用户名：<input class="form-control" type="text" id="customerName"
							name="customerName" /><br> 密 &nbsp;码：<input type="password"
							class="form-control" id="customerPwd" name="customerPwd" /><br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码：<input
							type="text" id="code" class="form-control" name="code" /><img id="pode"
							src="${contextPath }/createPic.do"><br> <input
							type="button" value="登  录" class="btn btn-danger _login"
							onclick="submitForm()"><a href="showfinduserpwd.do">找回密码</a>
					</form>
				</div>
			</div>
		</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>