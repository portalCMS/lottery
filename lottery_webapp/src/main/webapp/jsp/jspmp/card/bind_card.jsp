<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>绑定银行卡</title>
		<%@ include file="../include/js_asset.jsp"%>
		<%@ include file="../include/css_asset.jsp"%>
		<script src="${contextPath }/jsp/jspmp/js/card/bind_card.js"></script>
		<script src="${contextPath }/jsp/jspmp/js/jQuery.selected.js"></script>
</head>
<body>
	<header class="clearfix"> <i class="icon icon-nav"></i>
	<div class="title">
		<span>绑定银行卡</span>
	</div>
	</header>

	<aside></aside>
	<section class="container">
	<form class="form" role="form"
		id="saveUserCardForm" name="saveUserCardForm">
		<div class="form-control">
			<label for="">银行名称</label>
			<div>
				<select class="form-ele form-select" style="width: 1850px;"
					id="selectBank">
					<c:forEach items="${banks}" var="ara">
						<option value="${ara.id }">${ara.name }</option>
					</c:forEach>
				</select>

			</div>
			<input type="hidden" id="bankId" name="bankId">
			<input type="hidden" name="token" value="${token}">
		</div>
		<div class="form-control">
			<label for="">银行卡号</label>
			<div class="form-ele form-input">
				<input type="text" id="cardNo" name="cardNo" />
			</div>
		</div>
		<div class="form-control">
			<label for="">开户姓名</label>
			<div class="form-ele form-input">
				<input type="text" id="openCardName" name="openCardName" />
			</div>
		</div>
		<div class="form-control">
			<label for="">开户所在地</label>
			<div class="form-multi-select clearfix">
				<select class="form-ele form-select" id="province" name="province">
				</select> <select class="form-ele form-select fr" id="city" name="city">
				</select>
				<div id="errorDiv"></div>
				<script type="text/javascript">
					initPlace('', '');
				</script>
				<input type="hidden" id="address" name="address">
			</div>
		</div>
		<div class="form-control">
			<label for="">支行信息</label>
			<div class="form-ele form-input">
				<input type="text" id="branchBankName" name="branchBankName" />
			</div>
		</div>
		<div class="form-control">
			<label for="">财务密码</label>
			<div class="form-ele form-input">
				<input type="password" id="moneyPwd" name="moneyPwd" />
			</div>
		</div>
		<input type="hidden" name="token" value="${token}"> <a
			href="javascript:submitBindForm();" role="button" class="btn">确认绑卡</a>
	</form>
	</section>


</body>
</html>