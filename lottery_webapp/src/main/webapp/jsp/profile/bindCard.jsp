<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定银行卡</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/user/bind_card.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1 cp">
			    <c:forEach items="${cards}" var="card" varStatus="status"> 
					<div class="cardItem _cards">
						<div class="clearfix  cardItem_info">
							<div class="fl">
								<h3 class="">${card.openCardName }</h3>
							</div>
							<div class="fr">
								<span class="color_blue">已加密</span>
							</div>
						</div>
						<div class="clearfix cardItem_number">
							<div class="fl">
								<span class="color_yellow">${card.bank.name }：</span> 
								<span>${card.cardNo }</span>
							</div>
							<div class="fr">
								<span class="color_8d">${card.createTime }</span>
							</div>
						</div>
					</div>
				</c:forEach>
				<a href="javascript:;" class="bindingNewCardBtn">绑定新卡</a>
			</div>
			<p class="boxBotBg"></p>
			<div class="pageFrBox mt10" id="bind_panle">
			<form action="saveUserCard.html" method="post" id="saveUserCardForm" name="saveUserCardForm"> 
				<ul class=" pl20">
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
				<input type="hidden" name="token" value="${token}">
				</form>
			</div>
			<div class="pageFrBox lh24  mt10">
				<h3>银行卡绑定：</h3>
				<p class="line1"></p>
				<ul class="pointUL mt10">
					<li>
						您可以绑定<span class="color_yellow">4张银行卡</span>以支持您的<span class="color_yellow">充值</span>与<span class="color_yellow">提款</span>业务。
					</li>
					<li>
						如果您希望解绑银行卡，请联系客服人员，我们将为您服务。
					</li>
					<li>
						请填写真实的银行卡信息，以免后续服务时产生不必要的麻烦。
					</li>
					<li>
						我们重视您的隐私，您绑定的银行卡数据经过本站数据中心加密处理，不会遭到信息泄露，请放心使用。
					</li>
					<li>
						如果您有什么疑问，请联系客服人员，或者提交<a href="${contextPath }/showfeedback.html" class="underline color_blue ">问题反馈</a>
					</li>
				</ul>
			</div>
		</div>	
		
		<div id="checkBindDiv">
			<p class="poPupTop"></p>
			<div class="poPupBg">
				<p class="poPupTitle">安全信息验证</p>
				<ul class="poPupBgUl">
					<li class="clearfix lh28">
						<div id="errorDiv" class="poPupBgDiv f14 errorHint"></div>
					</li>
					<li class="clearfix mt10 lh28"><span class="poPupBgSpan">开卡人姓名：</span>
						<div class="poPupBgDiv">
							<span class="smallInput"><span>
								<input type="text"  id="openCardName" class="_cparam" alt="开卡人姓名"  style="width: 230px;" /></span></span>
						</div></li>
					<li class="clearfix mt10 lh28"><span class="poPupBgSpan">已绑定卡号：</span>
						<div class="poPupBgDiv">
							<span class="smallInput"><span>
								<input type="text" id="cardNo" class="_cparam" alt="已绑定卡号" style="width: 230px;" /></span></span>
							</div></li>
					<li class="clearfix mt5 lh28"><span class="poPupBgSpan">支行名称：</span>
						<div class="poPupBgDiv">
							<span class="smallInput"><span>
								<input  type="text" id="branchBankName" class="_cparam" alt="支行名称"   style="width: 230px;" /></span></span>
							</div></li>
						</ul>
						<p class="poPupBtn"></p>
				</div>
				<p class="poPupBot"></p>
		</div>	
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>