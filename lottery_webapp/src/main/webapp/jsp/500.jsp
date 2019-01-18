<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>500</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>

</head>
<body>
	<%@ include file="include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt25 clearfix">
		<div class="k310 fl relative">
			<div class="l404Bg">
				<p class="l404BgP">
					<span>500</span>
				</p>
				<p class="l404Line"></p>
				<p class="tc color_fff f14 mt10">网站貌似出现了错误，您可以去：</p>
			</div>
			<div class="menu404">
				<a href="${contextPath }/index.html">首页</a><span class="plr15">|</span>
				<a href="${contextPath }/user/showUserRecharge.html">充值</a><span
					class="plr15">|</span>
				<a href="${contextPath }/user/showDrawing.html">提款</a><span class="plr15">|</span>
				<a href="${contextPath }/user/showUserData.html">个人中心</a>
			</div>
			<p class="boxShadow"></p>
		</div>
		<div class="k668 fr relative">
			<div class="clearfix">
				<div class="luckyN404 fl">
					<p class="luckyTitle404">很遗憾您看见此页面</p>
					<div class="text3_500">
						我们一直在努力为您提供优质的彩票投注网站，注重美学及产品使用体验。在完成每一项功能时，无法将每一个细节都关注到。<br>请与我们一起共同完善。
						将您遇见的问题反馈给我们，我们将在下一个版本迭代时修复它！
					</div>
				</div>
				<div class="luckyR404 fr">
					<p class="luckyTitle404">我们非常重视此问题</p>
					<p class="mt15 ml20">
						<a href="${contextPath }/showfeedback.html" class="btn3 big f18"><span>提交反馈</span></a>
					</p>
					<p class="mt20 ml20">帮助我们完成的更好。</p>
				</div>
			</div>
			<p class="boxShadow"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="include/simple-footer.jsp"%>

</body>
</html>