<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(404);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404</title>
<%@ include file="../../jsp/include/css_asset.jsp"%>
<%@ include file="../../jsp/include/js_asset.jsp"%>
</head>
<body>
	<%@ include file="../../jsp/include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt25 clearfix">
		<div class="k310 fl relative">
			<div class="l404Bg">
				<p class="l404BgP">
					<span>404</span>
				</p>
				<p class="l404Line"></p>
				<p class="tc color_fff f14 mt10">很遗憾，页面没有找到。您可以去：</p>
			</div>
			<div class="menu404">
				<a href="#">首页</a><span class="plr15">|</span><a href="#">充值</a><span
					class="plr15">|</span><a href="#">提款</a><span class="plr15">|</span><a
					href="#">个人中心</a>
			</div>
			<p class="boxShadow"></p>
		</div>
		<div class="k668 fr relative">
			<div class="clearfix">
				<div class="luckyN404 fl">
					<p class="luckyTitle404">幸运数字</p>
					<p class="luckyNStrong mt20">
						<strong>11</strong><strong>12</strong><strong>13</strong><strong>14</strong>
					</p>
					<p class="luckyBtn">
						前往投注：<a href="#">重庆时时彩</a><a href="#">广东11选5</a><a href="#">天津时时彩</a><a
							href="#">各种11选5</a>
					</p>
				</div>
				<div class="luckyR404 fr">
					<p class="luckyTitle404">不满意？</p>
					<p class="mt15 ml20">
						<a href="#" class="btn3 big f18"><span>提交反馈</span></a>
					</p>
					<p class="mt20 ml20">帮助我们完成的更好。</p>
				</div>
			</div>
			<p class="boxShadow"></p>
		</div>
	</div>
	<!-- content End -->


	<%@ include file="../../jsp/include/footer.jsp"%>
</body>
</html>