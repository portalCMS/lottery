<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>找回密码-填写安全问答</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript">
	function submitForm(){
		if($("#answer")==""){
			alert("答案不能为空");
			return;
		}
		questionFrom.submit();
	}
	$(document).ready(function(){
		$(".errorHint").html("");
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
</script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>

	<!-- content -->
	<div class="k990 mt20">
		<div class="findPwTitle findPwTitle2">
			<span>验证账号</span><span class="color_fff">填写安全问答</span><span>完成密码找回</span>
		</div>
		<div class="findPwBox">
			<p class="f16 pl20">填写您设置的安全问答</p>
			<p class="line5 mt10"></p>
			<div class="clearfix mt10 hidden">
				<form action="showpwd.html" method="post" id="questionFrom">
				<ul class="pl20 fl">
					<li class="hintText">
						<div class="formListDiv errorHint">答案错误</div>
					</li>
					<li class="clearfix mt10 f14 lh40"><span class="formListSpan">问&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</span>
						<div class="formListDiv">
							<select class="select1" name="id" id="qid"  style="width: 243px;">
								<c:forEach items="${sqlist }" var="sq">
									<option value="${sq.id }">${sq.question }</option>
								</c:forEach>
							</select>
						</div></li>
					<li class="clearfix mt15 f14 lh40"><span class="formListSpan">答&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案：</span>
						<div class="formListDiv">
							<span class="maxInput"><span> <input type="text"
									value="" style="width: 225px;" id="answer" name="answer" />
							</span></span></a>
							<p class="mt25">
								<a href="javascript:;" class="maxBtn"><span
									style="width: 115px;">提 交</span></a>&nbsp;&nbsp;<a
									href="changepwd.html" class="maxSubBtn"><span
									style="width: 115px;">返 回</span></a>
							</p>
						</div></li>
				</ul>
				</form>
				<div class="findPwText fr">
					输入您在注册时设置的安全问答，以找回您的密码。
					<br>
					如果您忘记了当时设置的安全问答，请发送邮件给我们，我们的客服人员会与您取得联系为您服务。
					<br>
					发送邮件账号：<a href="mailto:email@domain.com?subject=安全问答遗失" class="underline color_blue ">email@domain.com</a>
					<br>
					邮件内容
					<ul class="pointUL">
						<li>标题：安全问答遗失</li>
						<li>联系方式，建议QQ号码</li>
						<li>账号其他信息，如密码、昵称、上级用户等</li>
					</ul>
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>