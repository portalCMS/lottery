<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>邀请注册-完成</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt20">
		<div class="registTitle registTitle4">
			<span>修改登录密码</span><span>设置账户安全</span><span>完善资料</span><span
				class="color_fff">完成</span>
		</div>
		<div class="findPwBox">
			<div class="clearfix hidden">
				<div class="fl pl20">
					<div class="regist4Title">
						恭喜您已完成注册！
						<p class="f14 color_5b mt15">接下来您可以完成操作：</p>
					</div>
					<p class="tc ml30 mt55">
						<a href="${contextPath }/user/showBindCard.html" class="miniBtn"><span>绑定银行卡</span></a>&nbsp;&nbsp;<a
							href="${contextPath }/user/showUserRecharge.html" class="miniSubBtn"><span>账户充值</span></a>&nbsp;&nbsp;<a
							href="${contextPath }/index.html" class="miniSubBtn"><span>前往彩种大厅</span></a>
					</p>
				</div>
				<div class="findPwText fr">
					<img src="${contextPath }/jsp/images/luck_4step.jpg" />
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>