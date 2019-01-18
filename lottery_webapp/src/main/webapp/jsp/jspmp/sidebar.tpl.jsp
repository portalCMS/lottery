<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/js_asset.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="aside-inner">
	<div class="account">
		<span>账户余额<br /><b>${cash.cash }</b></span>
	</div>
	<ul class="nav">
		<li><div class="nav-item clearfix"><a href="#">彩种<i class="icon icon-jiantouyou"></i></a></div>
			<ul class="subnav">
			<c:forEach items="${listLotteryWebApp }" var="lotterWebApp">
				<li class="on"><a href="${contextPath }/indexmp.html?lotteryGroup=${lotterWebApp.lotteryGroup}"></i>${lotterWebApp.lotteryGroupName }</a></li>
			</c:forEach>
			</ul>
		
		</li>
		<li class="on">
			<div class="nav-item clearfix"><a href="#">个人<i class="icon icon-jiantouyou"></i></a></div>
			<ul class="subnav">
				<li><a href="${contextPath }/showPhoneOrders.html" id="bofangjilu"><i class="icon icon-bofangjilu"></i>投注记录</a></li>
				<li><a href="${contextPath }/showFinanceDetail.html"><i class="icon icon-zhanghu"></i>账户明细</a></li>
				<li><a href="#"><i class="icon icon-iconziti08"></i>账户充值</a></li>
				<li><a href="${contextPath }/showUserDrawing.html"><i class="icon icon-series22"></i>提款申请</a></li>
				<li><a href="${contextPath }/showBindCards.html"><i class="icon icon-huiyuanqia"></i>银行卡绑定</a></li>
				<li><a href="${contextPath }/showAccountSafes.html"><i class="icon icon-suo1"></i>账户安全</a></li>
			</ul>
		</li>
			<li><div class="nav-item clearfix">
					<a href="#">网站<i class="icon icon-jiantouyou"></i></a>
				</div>
				<ul class="subnav">
					<li class="on"><a href="${contextPath }/showLoadNotices.html"></i>网站公告</a></li>
					<li class="on"><a href="${contextPath }/loginDropOut.html"></i>退出登录</a></li>
				</ul></li>
		</ul>
	<i class="account-shadow"></i>
</div>
</body>
</html>