<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>首页</title>
	<%@ include file="include/js_asset.jsp"%>
	<%@ include file="include/css_asset.jsp"%>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span class="hide">首页</span><span><img src="jsp/jspmp/imgs/caiwang.png" alt=""></span></div>
</header>

<aside></aside>

<section class="container">
	<div class="board">
		<i class="icon icon-gonggao"></i>
		<strong>公告：</strong>中奖秘诀:玩赚11选5 懂这些就够！
	</div>

	<div class="home-list">
	<c:forEach items="${lotteryList }" var="lottery">
		<div class="home-item clearfix">
			<c:if test="${lottery.lotteryGroup=='SSC' }">
				<a href="${contextPath }/showshishicai.html?lotteryCode=${lottery.lotteryCode}&lotteryGroup=${lottery.lotteryGroup}">
				<img src="jsp/jspmp/imgs/img_SSC.jpg" alt="">
			</c:if>
			<c:if test="${lottery.lotteryGroup=='3D' }">
				<a href="#">
				<img src="jsp/jspmp/imgs/img_3D.jpg" alt="">
			</c:if>
			<c:if test="${lottery.lotteryGroup=='SYXW' }">
				<a href="#">
				<img src="jsp/jspmp/imgs/cp-pic.png" alt="">
			</c:if>

			<h3>${lottery.lotteryName }</h3>
			<p>猜对一个号码就中奖，简单好玩！<br />全天78期，10分钟就开奖！</p>
			<i class="icon icon-jiantouyou"></i>
			</a>
		</div>
	</c:forEach>
	</div>
</section>

<div class="footer">
	<span>© 2014 xxxxx.com 版权所有</span>
	<span>客服热线：400-6100-123</span>
</div>
</body>
</html>