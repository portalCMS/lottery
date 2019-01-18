<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人中心-提款申请</title> <%@ include file="../include/css_asset.jsp"%>
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/user/payout_request.js"></script>
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
				<div class="clearfix f12">
					<c:forEach items="${cards}" var="card" varStatus="status"> 
						<label class="mr30 ">
							<input name="cardId" type="radio"  class="mtF3 bindCard" value="${card.id }"/> 
							&nbsp;${card.openCardName }&nbsp;${card.cardNo }&nbsp; 
							<c:if test="${card.bank.out!=1}"><font color="red">(不可提款)</font></c:if>
						</label> 
					</c:forEach>
				</div>
				<div class="clearfix f12">
					<a href="javascript:;" id="bind_panle_btn" class="fr color_blue underline">绑定银行卡</a> 
				</div>

				<!-- 如果用户未绑定银行卡 显示 p 标签中的内容，显示  id= bind_panle 和 隐藏 id = payout_panle -->
				<!--
					<p class="f14 color_red">
					绑定银行卡后可用于【银行卡充值】与【提款】服务
					</p>
					-->
				<div id="bind_panle">
					<form action="saveUserCard.html" method="post" id="saveUserCardForm" name="saveUserCardForm"> 
					<input type="hidden" name="token" value="${token}">
					<p class="line4 mt10"></p>
					<ul class="pt15 pl20">
						<li class="clearfix mt10"><span class="formListSpan lh26">银行名称：</span>
							<div class="formListDiv">
								<select class="select2" style="width: 255px;" id="selectBank">
									<c:forEach items="${banks}" var="ara"> 
										<option value="${ara.id }">
											${ara.name }
										</option>
									</c:forEach>
								</select>
								<input type="hidden" id="bankId" name="bankId" >
							</div></li>
						<li class="clearfix mt10"><span class="formListSpan lh26">开户地址：</span>
							<div class="formListDiv">
								<select class="select2" id="province" name="province"style="width: 125px;" >
								</select> 
								<select class="select2" id="city" name="city" style="width: 125px;">
								</select>
								<div id="errorDiv"></div>
								<script type="text/javascript">
									initPlace('', '');
								</script>
								<input type="hidden" id="address" name="address"> 
							</div></li>
						<li class="clearfix mt10"><span class="formListSpan lh26">支行名称：</span>
							<div class="formListDiv">
								<span class="smallInput"><span> <input type="text" class="checkedAttr"
										value="" style="width: 235px;" id="branchBankName" name="branchBankName"
										checkType="isNotEmpty" alt="支行名称不能为空！" loc="errorDiv1"/>
								</span></span><span id="errorDiv1"></span>
							</div></li>
						<li class="clearfix mt10"><span class="formListSpan lh26">开户姓名：</span>
							<div class="formListDiv">
								<span class="smallInput"><span> <input type="text" class="checkedAttr"
										value="" style="width: 235px;" id="openCardName" name="openCardName"
										checkType="isNotEmpty" alt="开户姓名不能为空！" loc="errorDiv2"/>
								</span></span><span id="errorDiv2"></span>
							</div></li>
						<li class="clearfix mt10"><span class="formListSpan lh26">银行卡号：</span>
							<div class="formListDiv">
								<span class="smallInput"><span> <input type="text" class="checkedAttr"
										value="" style="width: 235px;"  id="cardNo" name="cardNo"
										checkType="isNotEmpty,num" alt="银行卡号不能为空！,银行卡号必须是数字！" loc="errorDiv3"/>
								</span></span><span id="errorDiv3"></span>
								<p class="color_8d mt5">您的银行卡信息经过本站加密中心处理，请放心使用此功能。</p>
							</div></li>
					</ul>
					<p class="line4 mt25"></p>
					<div class="pl20 mt25">
						<span class="formListSpan lh26">资金密码：</span>
						<div class="formListDiv">
							<span class="smallInput"><span> <input type="password" class="checkedAttr"
									value="" style="width: 235px;" id="moneyPwd" name="moneyPwd"
									checkType="isNotEmpty" alt="资金密码不能为空！" loc="errorDiv4"/>
							</span></span><span id="errorDiv4"></span>
							<p class="mt10">
								<a href="javascript:submitBindForm();" class="miniBtn"><span>绑定此卡</span></a>&nbsp;&nbsp;<a
									href="javascript:;" class="miniSubBtn" id="cancelBtn"><span>取 消</span></a>
							</p>
						</div>
					</div>
					</form>
				</div>
				<div id="payout_panle">
					<form action="submitDrawingRqeuest.html" method="post" id="userDrawingForm" name="userDrawingForm"> 
					<input type="hidden" name="token" value="${token}">
					<p class="line4 mt10"></p>
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
							</div></li>
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
			<div class="pageFrBox">
				<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
					Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus
					accumsan et viverra justo commodo. Proin sodales pulvinar tempor.
					Cum sociis natoque penatibus et magnis dis parturient montes,
					nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra
					vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget
					odio.</p>
				<p>&nbsp;</p>
				<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
					Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus
					accumsan et viverra justo commodo. Proin sodales pulvinar tempor.
					Cum sociis natoque penatibus et magnis dis parturient montes,
					nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra
					vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget
					odio.</p>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
	<script>
		$(document).ready(function() {
			$("#bind_panle").hide();
			$("#bind_panle_btn").click(function() {
				$("#bind_panle").stop().toggle(500);
				$("#payout_panle").stop().toggle(500);
			});
		});
	</script>
</body>
</html>