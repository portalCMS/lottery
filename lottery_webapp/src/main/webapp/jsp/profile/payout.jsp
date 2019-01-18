<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提款申请</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/user/payout.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>   
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox">
				<div class="clearfix f12 _cards">
					<c:if test="${cards.size()==0}">请先绑定银行卡</c:if>
					<c:forEach items="${cards}" var="card" varStatus="status"> 
						<label class=" mt10 cardInfoBox" >
							<input name="cardId" type="radio" /  class="mtF3 bindCard" value="${card.id }"/> 
							<span class="_cardInfo">&nbsp;${fn:substring(card.openCardName,0,3) }&nbsp;[&nbsp;${fn:substring(card.cardNo,0,4)}  ${fn:substring(card.cardNo,14,fn:length(card.cardNo))}&nbsp;]</span>
							<c:if test="${card.bank.out!=1}"><font color="red">(不可提款)</font></c:if>
						</label> 
					</c:forEach>
				</div>
				<p class="line1 mt10"></p>
					<div id="payout_panle">
					<form action="submitDrawingRqeuest.html" method="post" id="userDrawingForm" name="userDrawingForm"> 
					<input type="hidden" name="token" value="${token}">
					<ul class="pt15 pl20">
						<li class="mt5">今日可提款次数：<span class="f14 color_red">${drawingTimes }</span>
						<input type="hidden" id="drawingTimes" name="drawingTimes" value="${drawingTimes }"> 
						</li>
						<li class="clearfix mt15"><span class="formListSpan lh26">提款金额：</span>
							<div class="formListDiv">
								<span class="smallInput"><span> <input type="text" id="cashAmount" name="cashAmount"
										checkType="isNotEmpty,decmal9" alt="提款金额不能为空！,提款金额必须为数字！" loc="errorDiv5" 
										class="checkedAttr" value="" style="width: 100px;" />
								</span></span>&nbsp;&nbsp;RMB<span id="errorDiv5"></span>
							</div>
							<p class="color_red lh20 mt5 f12" style="margin-left:70px;">
									最低：<span class="amount">${drawingMinAmount }</span>元&nbsp;&nbsp;&nbsp;最高：<span class="amount">${drawingMaxAmount }</span>元
							</p>
						</li>
						<li class="clearfix mt10"><span class="formListSpan lh26">资金密码：</span>
							<div class="formListDiv">
								<span class="smallInput"><span> <input type="password" id="adminPwd" name="adminPwd"
										checkType="isNotEmpty" alt="资金密码不能为空！" loc="errorDiv6" 
										class="checkedAttr" value="" style="width: 200px;" />
								</span></span><span id="errorDiv6"></span>
								<input type="hidden" id="referenceId" name="referenceId" />
								
								<p class="mt10">
									<a href="javascript:submitDrawingForm();" class="miniBtn" ><span>提交申请</span></a>
								</p>
							</div></li>
					</ul>
					</form>
				</div>
				</div>
				<p class="boxBotBg"></p>
				<div class="pageFrBox lh24  mt10">
					<h3>提款申请</h3>
					<p class="line1"></p>
					<ul class="pointUL mt10">
						<li>
							为了您的账户安全考虑，本站仅支持提款到您已绑定的银行卡。
						</li>
						<li>
							提交提款申请后，该此申请将会生成一笔提款订单，可以在【<a href="${contextPath }/finance/showFinanceDetail.html" class="underline color_blue">财务明细</a>】下的【财务】分类查看此订单详情。
						</li>
						<li>
							系统不会直接将您的提款金额从账户内扣除，会先冻结您的这笔提款金额。等待该笔订单操作完成后，根据处理结果做相应处理，"成功"则代表已完成提款，扣除该笔冻结金额。"失败"则会将该笔金额解冻返款至您的账户中。
						</li>
						<li>
							财务团队 7 x 17 小时 为您服务。服务时间：每天  <span class="color_yellow">早上
							<span id="drStartTime">${drawingStartTime }</span></span>  
							至  <span class="color_yellow">次日凌晨
							<span id="drEndTime">${drawingEndTime }</span>
							</span>。
						</li>
					</ul>
				</div>
				<p class="boxBotBg"></p>
			</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>