<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-账户充值</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/user/recharge_check.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox">
				<ul class="userUl">
					<li>
						<h2 class="color_5b ">绑定银行卡</h2>
						<p class="line4 mt5 "></p>
					</li>
					<li><strong>&nbsp;&nbsp;&nbsp;银行名称：</strong>${bankName }</li>
					<input type="hidden" id="bankName" name="bankName" value="${bankName }">
					<li><strong>&nbsp;&nbsp;&nbsp;银行卡号：</strong>${cardNo }</li>
					<input type="hidden" id="cardNo" name="cardNo" value="${cardNo }">
					<input type="hidden" id="sourceId" name="sourceId" value="${cardId }">
					<li class="mt20">
						<h2 class="color_5b">转入银行卡</h2>
						<p class="line4 mt5"></p>
					</li>
					<li><strong>收款人姓名：</strong>${bindCard.bankCard.opencardName}</li>
					<li><strong>收款人账号：</strong>${bindCard.bankCard.cardNo}</li>
					<li><strong>&nbsp;&nbsp;&nbsp;充值金额：</strong>${cashAmount } RMB</li>
					<input type="hidden" id="cashAmount" name="cashAmount" value="${cashAmount }">
					<li><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;附言：</strong>${bankPs }</li>
					<li><strong>
						<a id="openUrl" href="${bankUrl }" target="_Blank" >
						<span class="color_blue underline">银行链接: ${bankUrl }</span></a>
					</strong></li>
					<input type="hidden" id="remark" name="remark" value="${bankPs }">
					<input type="hidden" id="bankUrl" name="bankUrl"  value="${bankUrl }">
					<input type="hidden" id="referenceId" name="referenceId" value="${bindCard.bankcardId }">
					<input type="hidden" id="token" name="token" value="${token}">
				</ul>
				<p class="line4 mt15"></p>
				<p class="mt25">
				转账申请提交后，请在<font color="red"> ${cancelTime }分钟</font>内完成转账。
				</p>
				<p class="mt25">
					<a href="javascript:submitForm();" class="smallBtn confirmBtn"><span
						style="min-width: 110px;">确认转账</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="javascript:previous();" class="smallSubBtn"><span
						style="min-width: 60px;">返 回</span></a>
				</p>
				<p class="f14 mt55">帮助</p>
				<p class="line4 mt10"></p>
				<div class="lh24 mt10">
					${describe }
				</div>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
	<script>
		$(function() {
			$('#userTab > a').click(
					function() {
						$(this).addClass('current').siblings().removeClass(
								'current');
						index = $(this).index('#userTab > a');
						$('.userTabBox').eq(index).show().siblings(
								'.userTabBox').hide();
					});
		});
	</script>
</body>
</html>