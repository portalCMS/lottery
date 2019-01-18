<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码-验证账号</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$(".loginCode").attr("title", "点击刷新");
		$(".loginCode").attr("src", "../createPic.html?" + Math.random());
		$(".loginCode").bind("click", function() {
			$(".loginCode").attr("src", "../createPic.html?" + Math.random());
		});
		$(".maxBtn").click(function(){
			submitForm();
		});
		
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
	
	function submitForm(){
		if($("#customerName").val()==""){
			$(".errorHint").html("用户名不能为空");
			return;
		}
		if($("#code").val()==""){
			$(".errorHint").html("验证码不能为空");
			return;
		}
		form.submit();
	}
</script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>

	<!-- content -->
	<div class="k990 mt20">
		<div class="findPwTitle">
			<span class="color_fff">验证账号</span><span>填写安全问答</span><span>完成密码找回</span>
		</div>
		<form action="findpwd.html" method="post" id="form">
		<div class="findPwBox">
			<p class="f16 pl20">请输入您要找回的账号</p>
			<p class="line5 mt10"></p>
			<div class="clearfix mt10 hidden">
				<ul class="pl20 fl">
					<li class="hintText"><div class="formListDiv errorHint"></div></li>
					<li class="clearfix mt10 f14 lh40"><span class="formListSpan">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</span>
						<div class="formListDiv">
							<span class="maxInput"><span><input type="text" name="customerName" id="customerName"
									value="" style="width: 225px;" /></span></span>
						</div></li>
					<li class="clearfix mt15 f14 lh40"><span class="formListSpan">验
							证 码：</span>
						<div class="formListDiv">
							<span class="maxInput"><span><input type="text" id="code" name="code"
									value="" style="width: 114px;" /></span></span>&nbsp;&nbsp;<a
								href="javascript:;"><img
								src="${contextPath }/jsp/img/loginCode.jpg" class="loginCode"
								width="100" height="39" /></a>
							<p class="mt25">
								<a href="javascript:;" class="maxBtn"><span
									style="width: 114px;">提 交</span></a>
								<a href="../home.html" class="color_blue underline ml10">返回登陆</a>
							</p>
						</div></li>
				</ul>
				<div class="findPwText fr">
					如果您遗忘了账号，请发送Email给我们，我们的客服人员会与您取得联系为您服务。
					<br>
					发送邮件账号：<a href="mailto:email@domain.com?subject=账户遗失" class="underline color_blue ">djyl.member@gmail.com</a>
					<br>
					邮件内容
					<ul class="pointUL">
						<li>标题：账户遗失</li>
						<li>联系方式，建议QQ号码</li>
						<li>账号其他信息，如密码、昵称、上级用户等</li>
					</ul>
				</div>
			</div>
		</div>
		</form>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>