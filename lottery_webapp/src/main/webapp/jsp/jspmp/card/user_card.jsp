<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>提款申请</title>
	<%@ include file="../include/css_asset.jsp"%>
	<%@ include file="../include/js_asset.jsp"%> 
	<script src="${contextPath }/jsp/jspmp/js/card/user_card.js"></script>
</head>
<body>

	<header class="clearfix"> <i class="icon icon-nav"></i>
	<div class="title">
		<span>提款申请</span>
	</div>
	</header>

	<aside></aside>

	<section class="container">
	<div class="col-box">
		<div class="col-box-first">
			<span>账户金额</span> <strong>${cash.cash }</strong>
		</div>
		<div>
			<span>冻结金额</span> <strong><font color="#BEBEBE">${cash.frozenCash }</font></strong>
		</div>
	</div>

	<form class="form" role="form" id="userDrawingForm"
		name="userDrawingForm">
		<div class="form-control">
			<label for="">提款金额</label>
			<div class="form-ele form-input">
				<input type="text" id="cashAmount" name="cashAmount" />
			</div>
			<p class="text-warn">
				单次充值金额小于<span class="amount">${drawingMaxAmount }</span>大于<span
					class="amount1">${drawingMinAmount }</span>
			</p>
		</div>
		<input type="hidden" name="token" value="${token}">
		<div class="form-control">
			<label for="">提款至</label> <select class="form-ele form-select"
				style="width: 1850px;" id="referenceId" nname="referenceId">
				<c:if test="${cards.size()==0}">
					<option value="">请先绑定银行卡</option>
				</c:if>
				<c:forEach items="${cards}" var="card">
					<option value="${card.id }">&nbsp;${fn:substring(card.openCardName,0,3) }&nbsp;[&nbsp;${fn:substring(card.cardNo,0,4)}
						${fn:substring(card.cardNo,14,fn:length(card.cardNo))}&nbsp;]
						<c:if test="${card.bank.out!=1}">
							<font color="red">(不可提款)</font>
						</c:if></option>
				</c:forEach>
			</select>
		</div>
		<div class="form-control">
			<label for="">资金密码</label>
			<div class="form-ele form-input">
				<input type="password" id="adminPwd" name="adminPwd" />
			</div>
		</div>
		<div class="wrap">
			<input type="hidden" id="drawingTimes" name="drawingTimes"
				value="${drawingTimes }">
			<p class="center text-warn fs20">今日可提款次数：${drawingTimes }</p>
		</div>
		<a href="javascript:bindCard.checkCard();" role="button" class="btn">确认提现</a>
	</form>
	</section>
</body>
</html>