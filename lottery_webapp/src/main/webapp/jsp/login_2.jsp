<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/login/login.js"></script>
<script>
	$(document).ready(function(){
		
		var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
		if (window.MessageEvent && !document.getBoxObjectFor){
            Sys.chrome = "huohu";
		}
  
        if(Sys.chrome){ 
        	$(".pwd").attr("type","text");
        	$(".pwd").attr("onfocus","this.type='password'");
        	$(".pwd").attr("autocomplete","off");
        }
 
		if('${param.errorMsg}' != ''){
			$(".errorHint").html("${param.errorMsg}");
		}
		if('${errorMsg}' != ''){
			$(".errorHint").html("${errorMsg}");
		}
		if('${param.tokenError}' == 'true'){
			$(".errorHint").html("表单重复提交或过期");
		}
		if('${tokenError}' == 'true'){
			$(".errorHint").html("表单重复提交或过期");
		}
	});
</script>
</head>
<body>
	<%@ include file="include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 clearfix _login_bg">
		<div class="loginFr fr">
			<div class="loginBox">
				<p>
					登录&nbsp;&nbsp;<span class="f14 errorHint">用户名或密码错误</span>
				</p>
				<form action="login.html" method="post" id="loginForm"
					class="cleanbgcolor">
					<ul>
						<li><input type="hidden" name="token" id="token" value="${token }"></li>
						<li class="f14">用户名：</li>
						<li class="mt5"><span class="mediumInput"><span><input
									type="text" name="customerName" class="uname"
									style="width: 265px;" /></span></span></li>
						<li class="f14 mt10">密码：</li>
						<li class="mt5"><span class="mediumInput"><span><input
									type="password" name="customerPwd" class="pwd" style="width: 265px;"/></span></span></li>
						<c:if test="${pwdError >= 3 }">
						<li class="f14 mt10">验证码：</li>
						<li class="mt5"><span class="mediumInput"><span><input
									type="text" name="code" class="code" style="width: 155px;" /></span></span>&nbsp;&nbsp;<a
							href="javascript:;"><img
								src="${contextPath }/jsp/img/loginCode.jpg" class="loginCode" /></a></li>
						</c:if>
						<li class="mt20"><a href="javascript:;" class="maxBtn _loginSend"><span
								style="min-width: 282px;">提 交</span></a></li>
						<li class="mt20 pwdcd">
						<c:if test="${pwdError >= 3 }">
							<a href="security/changepwd.html" class="underline">忘记密码?</a>
						</c:if>
						<a href="http://www.dj365.info:909/dj.zip" class="clientDown color_blue">
						<span class="ml30">&nbsp;&nbsp;客户端下载</span></a>    
						<a href="check/showcheck.html"  class="clientDown color_blue"  target="_blank">
						<span class="ml30">&nbsp;&nbsp;域名验证</span></a>
						</li>
					</ul>
				</form>
			</div>
<!-- 			<p class="loginBoxBot"></p> -->
		</div>
<%-- 		<img src="${contextPath }/jsp/img/login-00.jpg" width="616" /> --%>
	</div>
	<!-- content End -->
	<%@ include file="include/simple-footer.jsp"%>

</body>
</html>