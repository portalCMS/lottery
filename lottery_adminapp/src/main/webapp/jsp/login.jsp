<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="stylesheet" href="${contextPath }/jsp/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${contextPath }/css/global.css" />
		<script src="${contextPath }/jsp/js/jQuery.min.js"></script>
		<script src="${contextPath }/jsp/js/bootstrap.min.js"></script>
		<script src="${contextPath }/jsp/js/global.js"></script>
		<script src="${contextPath }/jsp/js/bootstrap-datetimepicker.min.js"></script>
		<script src="${contextPath }/jsp/js/utile.js"></script>
		<script src="${contextPath }/jsp/js/login/login.js"></script>
		<title>登录</title>
	</head>
	<script>
		$(document).ready(function(){
			document.onkeydown = function(e){
		        if(!e) e = window.event;
		        if((e.keyCode || e.which) == 13){
		        	loginform.submit();
		        }
		    }
			init();
			if('${errorMsg }'!=""){
				$("#error_msg_val").html("${errorMsg }");
				$("#error_msg").show();
			}
		});
	</script>
	<body>
		<div class="container">

			<div class="row" style="margin-top:100px;">
				<div class="col-sm-4"></div> 
				<div class="col-sm-4">
					<div class="panel panel-primary">
						<div class="panel-heading">
							登录
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-sm-12">
									<div class="alert alert-danger alert-dismissable" id='error_msg' >
										<button type="button" class="close alert_close_btn">
											&times;
										</button>
										<div id="error_msg_val">用户名或密码错误</div>
									</div>
									<form role="form" action="login.do" method="post" id="loginform">
										<div class="form-group">
											<label for="exampleInputEmail1">用户名</label>
											<input type="email" class="form-control" id="adminName" name="adminName">
										</div>
										<div class="form-group">
											<label for="exampleInputPassword1">密码</label>
											<input type="password" class="form-control" id="adminPwd" name="adminPwd">
										</div>
										<div class="form-group ">
											<label for="aqm" class="" >安全码</label>
											<div class="clearfix">
												<input type="text" class="form-control pull-left " id="picCode" name="picCode" style="width: 100px;margin-right: 10px;">
												<img class="pull-left" id="code" src="picode/createPic.do" width="100" height="35" />
												<div class="clearfix"></div>
											</div>
										</div>
										<button type="button" class="btn btn-warning btn-block"  onclick="return submitForm()">
											登录
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-4"></div>
			</div>
		</div>
	</body>
</html>