<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邀请注册-设置账户安全</title> <%@ include file="../include/css_asset.jsp"%>
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/register/initMoneyPwd.js"></script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt20">
		<div class="registTitle registTitle2">
			<span>修改登录密码</span><span class="color_fff">设置账户安全</span><span>完善资料</span><span>完成</span>
		</div>
		<div class="findPwBox">
			<div class="clearfix hidden">
			<input type="hidden" id="token" name="token" value="${token}">
				<div class="fl pl20">
					<span class="f18 ">设置资金密码：</span> <span class="f14" id="errorDiv2"></span>
					<p class="line4 mt5"></p>
					<ul class="mt25">
						<li class="clearfix f14 lh40"><span class="formListSpan">资金密码：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
								<input  checkType="isNotEmpty,length2" alt="资金密码不能为空！,资金密码长度6-12位字符！" loc="errorDiv2" name="newPwd"
										type="password" class="checkedAttr _newPwd" style="width: 230px;" 
										value="" style="width: 225px;" /></span></span>
								<p class="color_8d f12 lh24">长度6-12位字符</p>
							</div></li>
						<li class="clearfix mt10 f14 lh40"><span class="formListSpan">重复密码：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
								<input checkType="isNotEmpty" alt="重复密码不能为空！" loc="errorDiv2" name="confirmPwd" 
										type="password" class="checkedAttr _confirmPwd" style="width: 230px;" />
								</span></span>
							</div></li>
					</ul>
					<p class="mt30"></p>
					<span class="f18  mt25">设置安全问答：</span><span class="f14" id="errorDiv3"></span>
					<p class="line4 mt5"></p>
					<ul class="mt25">
						<li class="clearfix f14 lh40"><span class="formListSpan">安全问题：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
								<input checkType="isNotEmpty,cLength1" alt="安全问题不能为空！,安全问题需在4到50个汉字之间！" loc="errorDiv3"
										type="text"  name="question"
									 	class="checkedAttr _question" style="width: 225px;" />
								</span></span>
								<p class="color_8d f12 lh24">最少4个汉字</p>
							</div></li>
						<li class="clearfix mt10 f14 lh40"><span class="formListSpan">安全答案：</span>
							<div class="formListDiv">
								<span class="maxInput"><span>
								<input checkType="isNotEmpty" alt="问题答案不能为空！" loc="errorDiv3"
									   type="text"  name="answer" 
									   class="checkedAttr _answer" style="width: 225px;" />
								</span></span>
								<p class="mt25">
									<a href="javascript:;" class="maxBtn _commit"><span
										style="width: 241px;">提 交</span></a>
								</p>
							</div></li>
					</ul>
				</div>
				<div class="findPwText fr">
					<h2>资金密码</h2>
					<p class="line1 mt5"></p>
					<p class="mt5">设置您的资金密码，为您账户的资金保驾护航。网站日常操作中不会使用资金密码，当涉及到“充值”、“提款”、“银行卡绑定”的操作时，需要资金密码验证。以确定是您本人进行操作。</p>
					<p class="color_red">请妥善保管您的资金密码</p>
					
					<h2 class="mt20">安全问答</h2>
					<p class="line1 mt5"></p>
					<p class="mt5">您自己设置的安全问题与答案，将作为修改资金密码的重要凭证。</p>
					<p class="color_red">安全问答只允许设置一次，后期无法再进行修改。</p>
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>