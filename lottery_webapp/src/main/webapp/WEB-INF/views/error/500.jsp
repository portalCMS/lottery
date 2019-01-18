<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(500);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>500</title>
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
					<span>500</span>
				</p>
				<p class="l404Line"></p>
				<p class="tc color_fff f14 mt10">网站貌似出现了错误，您可以去：</p>
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
					<p class="luckyTitle404">错误标题</p>
					<div class="text3_500">Lorem ipsum dolor sit amet,
						consectetur adipiscing elit. Aenean euismod bibendum laoreet.
						Proin gravida dolor sit amet lacus accumsan et viverra justo
						commodo. Proin sodales pulvinar tempor. Cum sociis natoque
						penatibus et magnis dis parturient montes, nascetur ridiculus mus.
						Nam fermentum, nulla luctus pharetra vulputate, felis tellus
						mollis orci, sed rhoncus sapien nunc eget odio.</div>
				</div>
				<div class="luckyR404 fr">
					<p class="luckyTitle404">我们非常重视此问题</p>
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
