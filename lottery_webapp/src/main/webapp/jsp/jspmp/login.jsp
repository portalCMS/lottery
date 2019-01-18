<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登录</title>
	<%@ include file="include/js_asset.jsp"%>
	<%@ include file="include/css_asset.jsp"%>
	<script src="${contextPath }/jsp/jspmp/js/login/login.js"></script>
	<script>
	$(document).ready(function(){
		if('${param.errorMsg}' != ''){
			var errorMsg="${param.errorMsg}"
			msg.alertMessage(errorMsg,"alert");
		}
		if('${errorMsg}' != ''){
			var errorMsg="${errorMsg}"
			msg.alertMessage(errorMsg,"alert");
		}
		if('${param.tokenError}' == 'true'){
				msg.alertMessage("表单重复提交或过期","alert");
		}
		if('${tokenError}' == 'true'){
				msg.alertMessage("表单重复提交或过期","alert");
		}
	});
</script>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span class="maxBtn _loginSend">夺金</span></div>
	
</header>

<aside></aside>

<section class="container">
	<div class="wrap form">
	<form id="loginForm" action="loginmp.html" method="post" class="cleanbgcolor">
		<div class="input-group">
			<div class="input-group-first">
				<label for=""><i class="icon icon-zhanghu"></i>用户名</label>
				<input type="text" id="customerName" name="customerName" />
			</div>
			<div class="input-group-last">
				<label for=""><i class="icon icon-suo"></i>&nbsp;密&nbsp;&nbsp;码</label>
				<input type="password" id="customerPwd" name="customerPwd" />
			</div>
		</div>
	</form>
		<a href="#" role="button" class="btn">登录</a>
	</div>
</section>
</body>
</html>