<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="pageFlBox">
	<li><a id="rechargeMenu" href="${contextPath }/user/showUserRecharge.html"><span class="ico1"></span>账户充值</a></li>
	<li><a id="payoutMenu" href="${contextPath }/user/showDrawing.html"><span class="ico6"></span>提款申请</a></li>
	<li><a id="bindCardMenu" href="${contextPath }/user/showBindCard.html"><span class="ico2"></span>银行卡绑定<c:if test="${sessioncards == 1 }"><label class="tip_icon">&nbsp;&nbsp;&nbsp;</label></c:if></a></li>
	<li><a id="financeMenu" href="${contextPath }/finance/showFinanceDetail.html"><span class="ico3"></span>财务明细</a></li>
	<li><a id="lotteryTypeInfo" href="${contextPath }/finance/showLotteryTypeDetail.html"><span class="ico3"></span>彩种信息</a></li>
	<li><a id="personalFinancialMenu" href="${contextPath }/finance/showPersonalFinancialPage.html"><span class="ico3"></span>个人盈亏</a></li>
	<c:if test="${customerkey.customerLevel != 0 }">
		<li><a id="myBetMenu" href="${contextPath }/finance/showMyBetRecord.html"><span class="ico3"></span>投注记录</a></li>
		<li><a id="myTraceBetMenu" href="${contextPath }/finance/showMyTraceBetRecord.html"><span class="ico3"></span>追号记录</a></li>
	</c:if>
	<li><a id="userDataMenu" href="${contextPath }/user/showUserData.html"><span class="ico4"></span>个人资料<c:if test="${empty customerkey.customerAlias or empty customerkey.qq or empty customerkey.email}"><label class="tip_icon">&nbsp;&nbsp;&nbsp;</label></c:if></a></li>
	<li><a id="accountSafeMenu" href="${contextPath }/user/showAccountSafe.html"><span
			class="ico5"></span>账户安全</a></li>
		<li><a id="webMsgMenu" href="${contextPath }/showWebMsg.html"><span
			class="ico20"></span>站内信</a></li>
</ul>
<p class="pageFlBoxBot"></p>