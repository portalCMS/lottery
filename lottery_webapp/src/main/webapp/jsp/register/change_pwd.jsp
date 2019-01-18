<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邀请注册-修改登录密码</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/register/initLoginPwd.js"></script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt20">
		<div class="registTitle">
			<span class="color_fff">修改登录密码</span><span>设置账户安全</span><span>完善资料</span><span>完成</span>
		</div>
		<div class="findPwBox">
			<div class="clearfix hidden">
				<ul class="pl20 fl">
					<li class="hintText"><div id="errorDiv" class="formListDiv f14 errorHint"></div></li>
					<li class="clearfix mt10 f14 lh40"><span class="formListSpan">登录密码：</span>
						<div class="formListDiv">
							<span class="maxInput"><span>
							<input type="hidden" id="token" name="token" value="${token}">
							<input checkType="isNotEmpty" alt="登录密码不能为空！" loc="errorDiv"
									type="password"  name="customerPwd"
									class="checkedAttr customerPwd" style="width: 230px;" /></span></span>
						</div></li>
					<li class="clearfix mt10 f14 lh40"><span class="formListSpan">新    密    码：</span>
						<div class="formListDiv">
							<span class="maxInput"><span>
								<input checkType="isNotEmpty,length1" alt="新密码不能为空！,密码长度必须在6到26位之间！"
									type="password"  name="newPwd"  loc="errorDiv"
									 class="checkedAttr newPwd" style="width: 230px;" />
							</span></span>
							<p class="color_8d f12 lh24">最小6位字符</p>
						</div></li>
					<li class="clearfix mt5 f14 lh40"><span class="formListSpan">重复密码：</span>
						<div class="formListDiv">
							<span class="maxInput"><span>
								<input checkType="isNotEmpty,length1" alt="重复密码不能为空！,密码长度必须在6到26位之间！"
										type="password"  name="confirmPwd" loc="errorDiv"
										class="checkedAttr confirmPwd" style="width: 230px;" />
							</span></span>
							<p class="mt25">
								<a href="javascript:;" class="maxBtn _commit"><span
									style="width: 241px;">提 交</span></a>
							</p>
						</div></li>
				</ul>
				<div class="findPwText fr">
					<h2>：) 欢迎您的第一次登陆</h2>
					<p class="line1 mt5"></p>
					<p class="mt5 color_yellow">每一个账户只会初始化注册一次，完成此流程开启您的幸运之旅。</p>
					<p>为了您账户的安全考虑，在您第一次登陆账户时，更改生成账号时的随机密码。保证账户的密码只有您一个人知道，免于后面由账号泄露所导致各类安全性的问题。</p>
					
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>