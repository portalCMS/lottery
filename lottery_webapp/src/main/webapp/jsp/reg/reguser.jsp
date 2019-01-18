<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新用户注册</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/reg/reguser.js"></script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt20">
		<div class="registTitle">
			<span class="color_fff">新用户注册</span><span>设置账户安全</span><span>完善资料</span><span>完成</span>
		</div>
		<div class="findPwBox">
			<div class="clearfix hidden">
				<div class="pl20 fl">
					<span class="f18 ">新用户注册：</span> <span class="f14" id="errorDiv2"></span>
					<p class="line4 mt5"></p>
					<ul class="mt25">
						<li class="hintText"><div id="errorDiv" class="formListDiv f14 errorHint"></div></li>
						<li class="clearfix mt10 f14 lh40"><span class="formListSpan">用   户    名：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
								<input type="text" id="customerName"  name="customerName" class="_formvalue" style="width: 230px;" /></span></span>
								<p class="color_8d f12 lh24">最小6位数字、中文、字符</p>
							</div></li>
						<li class="clearfix mt10 f14 lh40"><span class="formListSpan">新    密    码：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
									<input type="password" id="customerPwd"  name="customerPwd"  class="_formvalue" style="width: 230px;" />
								</span></span>
								<p class="color_8d f12 lh24">最小6位字符</p>
							</div></li>
					</ul>
					<ul class="mt25">
						<li class="clearfix mt5 f14 lh40"><span class="formListSpan">重复密码:</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
									<input type="password" id="confirmPwd"  name="confirmPwd"  style="width: 230px;" />
								</span></span>
							</div></li>
						<li class="clearfix mt5 f14 lh40"><span class="formListSpan">验  证  码:</span>
								<div class="formListDiv">
									<span class="maxInput"><span>
										<input
											type="text" name="code" id="code" class="_formvalue" style="width: 230px;" />
									</span></span>
									&nbsp;&nbsp;<a
									href="javascript:;"><img
										src="${contextPath }/jsp/img/loginCode.jpg" class="loginCode" /></a>
									<p class="mt25">
										<a href="javascript:;" class="maxBtn _commit"><span
											style="width: 241px;">提 交</span></a>
										<a href="../home.html" class="color_blue underline ml10">返回登陆</a>
									</p>
								</div>
						</li>
					</ul>
				</div>
				<div class="findPwText fr" style="margin-right: 10px;width: 430px;">
					<h2 class="mt20">用户名称</h2>
					<p class="line1 mt5"></p>
					<p class="mt5">设置您的用户名称，是您在本站唯一标识。网站日常操作中不会使用用户名称，当涉及到“登录”的操作时，需要用户名称验证。以确定是您本人进行操作。</p>
					<p class="color_red">请妥善保管您的用户名称</p>
					<h2 class="mt20">账户密码</h2>
					<p class="line1 mt5"></p>
					<p class="mt5">设置您的账户密码，为您账户的保驾护航。网站日常操作中不会使用账户密码，当涉及到“登录”的操作时，需要账户密码验证。以确定是您本人进行操作。</p>
					<p class="color_red">请妥善保管您的账户密码</p>
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>