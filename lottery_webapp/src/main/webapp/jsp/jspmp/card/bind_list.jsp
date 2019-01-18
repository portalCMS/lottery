<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>银行卡绑定</title>
		<%@ include file="../include/js_asset.jsp"%>
		<%@ include file="../include/css_asset.jsp"%>
		<script src="${contextPath }/jsp/jspmp/js/card/bind_card.js"></script>
</head>
<body>
	<header class="clearfix"> <i class="icon icon-nav"></i>
	<div class="title">
		<span>银行卡绑定</span>
	</div>
	</header>

	<aside></aside>

	<section class="container">
	<div class="bind-list">
		<c:forEach items="${cards}" var="card" varStatus="status"> 
		<div class="bind-item">
			<h4>${card.openCardName }</h4>
			<p>
				${card.bank.name } <span>${card.cardNo }</span>
			</p>
		</div>
		</c:forEach>
	</div>
	<div class="wrap">
		<p class="center text-warn fs20">您还可以绑定3张银行卡</p>
		<a href="${contextPath }/bindCard.html" role="button" class="btn">绑定新卡</a>
	</div>
	</section>


</body>
</html>