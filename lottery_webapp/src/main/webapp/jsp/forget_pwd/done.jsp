<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>找回密码-完成密码找回</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt20">
		<div class="findPwTitle findPwTitle3">
			<span>验证账号</span><span>填写安全问答</span><span class="color_fff">完成密码找回</span>
		</div>
		<div class="findPwBox">
			<p class="f16 findPw3Ico ml20">恭喜您已经成功找回密码</p>
			<p class="line5"></p>
			<div class="clearfix mt35 hidden">
				<div class="fl f14 ml20">
					<p>
						临时密码：<span class="color_red">${randomPwd}</span>
					</p>
					<p class="line4 mt10"></p>
					<p class="mt10 ml70">登录后请立即修改密码</p>
					<p class="mt15 ml70">
						<a href="../home.html" class="maxBtn"><span
							style="width: 150px;">前往登录</span></a>
					</p>
				</div>
				<div class="findPwText fr">
					请保管好您的临时密码。					
					<br>
					登陆后在个人【<span class="color_yellow">账户安全</span>】中修改您的个人密码。
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>