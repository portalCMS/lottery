<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>URL验证</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/check.js"></script>
</head>
<body>
	<div class="headC" >
	<div class="k990 clearfix">
		<span class="fl">
			</span>
			<i class="fuImg"></i>
		</div>
	</div>
	<!-- content -->
	<div class="k990 mt10 clearfix" align="center">
		<div class="loginFr" >
			<div class="loginBox">
				<form action="login.html" method="post" id="loginForm"
					class="cleanbgcolor">
					<ul>
						<li class="mt5">URL：<span class="mediumInput"><span><input
									type="text" id="url" name="url" class="uname"
									style="width: 188px;"  placeholder="请输入需要验证的url"/></span></span></li>
						<li class="mt5">验证码：<span class="mediumInput"><span><input
									type="text" id="code" name="code" class="code" style="width: 95px;" /></span></span>&nbsp;&nbsp;<a
							href="javascript:;"><img
								src="${contextPath }/jsp/img/loginCode.jpg" class="loginCode" /></a></li>
						<li class="mt20"><a href="javascript:;" class="maxBtn _checkBtn"><span
								style="min-width: 282px;">提 交</span></a></li>
						<li class="mt20">
					</ul>
				</form>
			</div>
			<p class="loginBoxBot"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="include/simple-footer.jsp"%>

</body>
</html>