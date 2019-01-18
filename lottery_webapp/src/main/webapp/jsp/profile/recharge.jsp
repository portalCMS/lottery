<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<title>个人中心-账户充值</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/user/recharge.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="iTab" id="userTab">
<%-- 					<c:if test="${defaultRecharge ==0 || defaultRecharge == 2 }"> --%>
<!-- 						<a href="javascript:;" class="current">&nbsp;&nbsp;在线充值&nbsp;&nbsp;</a> -->
<%-- 					</c:if> --%>
					<a href="javascript:;" class="current">快速充值</a>
					<c:if test="${defaultRecharge ==0 || (empty nosafe && defaultRecharge == 1)}">
						<a class="" href="javascript:;" class="current">银行卡充值</a>
					</c:if>
				</div>
<%-- 				<c:if test="${defaultRecharge ==0 || defaultRecharge == 2 }"> --%>
<!-- 				<div class="userTabBox"> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${defaultRecharge == 1 }"> --%>
<!-- 				<div class="userTabBox" c> -->
<%-- 				</c:if> --%>
<!-- 					<ul class="clearfix userBank"> -->
<!-- 						<li class="current _checkOnLine" alt="yb"><img -->
<%-- 							src="${contextPath }/jsp/img/img_20.jpg" width="152" height="65" /><span --%>
<!-- 							class="pageBg"></span></li> -->
<!-- 					</ul> -->
<!-- 					<p class="line4 mt25"></p> -->
<!-- 					<div class="clearfix mt25 f14 lh40"> -->
<!-- 						<span class="formListSpan">剩余充值次数：<span class="color_red">${rechargeTimes }</span></span><br /> -->
<!-- 						<span class="formListSpan">充值金额：</span> -->
<!-- 						<div class="formListDiv"> -->
<!-- 							<span class=smallInput><span> <input id="onlineAmount" type="text" -->
<!-- 									value="" style="width: 100px;" /> -->
<!-- 							</span></span>&nbsp;&nbsp;RMB -->
<!-- 							<p class="color_red lh20 ml5 f12"> -->
<%-- 											最低：<span class="amount">${thirdPartyMinAmount }</span>元&nbsp;&nbsp;&nbsp;最高：<span --%>
<%-- 												class="amount">${thirdPartyMaxAmount }</span>元 --%>
<!-- 							</p> -->
<!-- 							<p class="mt25"> -->
<!-- 								<a href="javascript:;" class="miniBtn _onlineBtn"><span>确 -->
<!-- 										定</span></a> -->
<!-- 							</p> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<!-- 快速充值 -->
				<div class="userTabBox">
						<table width="740px;" class="table-field">
		            	<tbody><tr>
		                <td width="90" align="left" valign="top">
		                <span class="field-name">选择充值银行：</span></td>
		                <td>
		                    <div class="bank-more-content">
		                        <div class="bank-list" id="J-bank-list">
		                                                        <label class="img-bank" for="J-bank-name-ICBC">
		                                <input data-id="25" name="bank" value="25" id="J-bank-name-ICBC" type="radio">
		                                <span class="ico-bank ICBC">中国工商银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CCB">
		                                <input data-id="26" name="bank" value="26" id="J-bank-name-CCB" type="radio">
		                                <span class="ico-bank CCB">中国建设银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-ABC">
		                                <input data-id="27" name="bank" value="27" id="J-bank-name-ABC" type="radio">
		                                <span class="ico-bank ABC">中国农业银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-BOC">
		                                <input data-id="28" name="bank" value="28" id="J-bank-name-BOC" type="radio">
		                                <span class="ico-bank BOC">中国银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CMB">
		                                <input data-id="29" name="bank" value="29" id="J-bank-name-CMB" type="radio">
		                                <span class="ico-bank CMB">招商银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-BOCOM">
		                                <input data-id="30" name="bank" value="30" id="J-bank-name-BOCOM" type="radio">
		                                <span class="ico-bank BOCOM">中国交通银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CMBC">
		                                <input data-id="31" name="bank" value="31" id="J-bank-name-CMBC" type="radio">
		                                <span class="ico-bank CMBC">中国民生银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CITIC">
		                                <input data-id="32" name="bank" value="32" id="J-bank-name-CITIC" type="radio">
		                                <span class="ico-bank CITIC">中信银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-SPDB">
		                                <input data-id="33" name="bank" value="33" id="J-bank-name-SPDB" type="radio">
		                                <span class="ico-bank SPDB">上海浦东发展银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-GDB">
		                                <input data-id="34" name="bank" value="34" id="J-bank-name-GDB" type="radio">
		                                <span class="ico-bank GDB">广东发展银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-PAB">
		                                <input data-id="35" name="bank" value="35" id="J-bank-name-PAB" type="radio">
		                                <span class="ico-bank PAB">平安银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CIB">
		                                <input data-id="37" name="bank" value="37" id="J-bank-name-CIB" type="radio">
		                                <span class="ico-bank CIB">兴业银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-HXB">
		                                <input data-id="38" name="bank" value="38" id="J-bank-name-HXB" type="radio">
		                                <span class="ico-bank HXB">华夏银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-CEB">
		                                <input data-id="39" name="bank" value="39" id="J-bank-name-CEB" type="radio">
		                                <span class="ico-bank CEB">中国光大银行</span>
		                            </label>
		                                                        <label class="img-bank" for="J-bank-name-PSBC">
		                                <input data-id="40" name="bank" value="40" id="J-bank-name-PSBC" type="radio">
		                                <span class="ico-bank PSBC">中国邮政储蓄银行</span>
		                            </label>
		                                                    </div>
		                    </div>
		                </td>
		            </tr>
		        	</tbody></table>
        			<p class="line4 mt25"></p>
					<div class="clearfix mt25 f14 lh40">
						<span class="formListSpan">充值通道：<span class="color_red">
							<select name="group" class="select2" id="group" style="width: 120px;margin-left: -4px">
								<option value="group1">充值通道一</option>
								<option value="group2">充值通道二</option>
							</select>
						</span></span><br />
						<span class="formListSpan">充值金额：</span>
						<div class="formListDiv">
							<span class=smallInput><span> <input id="newOnlineAmount" type="text"
									value="" style="width: 100px;" />
							</span></span>&nbsp;&nbsp;RMB
							<p class="color_red lh20 ml5 f12">
											最低：<span class="amount">${thirdPartyMinAmount }</span>元&nbsp;&nbsp;&nbsp;最高：<span
												class="amount">${thirdPartyMaxAmount }</span>元
							</p>
							<p class="mt25">
								<a href="javascript:;" class="miniBtn _newOtherPayBtn"><span>确
										定</span></a>
							</p>
						</div>
					</div>
				</div>
				<!-- 快速充值 End -->

				<!-- 银行卡充值 -->
				<c:if test="${ defaultRecharge == 2 }">
				<div class="userTabBox" style="display: none;">
				</c:if>
				<c:if test="${ defaultRecharge == 0 }">
				<div class="userTabBox" style="display: none;">
				</c:if>
				<c:if test="${defaultRecharge == 1 }">
				<div class="userTabBox" style="display: none;">
				</c:if>
					<form action="${contextPath }/user/userRechargeConfirm.html"
						method="post" id="saveUserRechargeForm">
						<input type="hidden" name="token" value="${token}"> <c:if
								test="${userBindCards.size()==0 }">
								<p class="f14 mt10">暂未绑定银行卡：</p>
							</c:if> <c:if test="${userBindCards.size()>0 }">
								<p class="f14 mt10">已绑定银行卡：</p>
							</c:if>
							<ul class="clearfix userBank">
								<c:forEach items="${userBindCards}" var="card"
									varStatus="status">
									<li class="card">
										<p class="mt5">${card.bank.name }</p>
										<p class="f12">${card.cardNo}</p> <input type="hidden"
										class="cardId" value="${card.id }" /> <input type="hidden"
										class="bankName" value="${card.bank.name }" /> <input
										type="hidden" class="cardNo" value="${card.cardNo }" /> <span
										class="pageBg"></span>
									</li>
								</c:forEach>
								<input type="hidden" id="id" name="id"> <input
									type="hidden" id="cardNo" name="cardNo"> <input
										type="hidden" id="branchBankName" name="branchBankName">
											<li class="btn"><a
												href="${contextPath }/user/showBindCard.html"
												class="f14 color_blue underline">添加绑定</a></li>
							</ul>
							<p class="line4 mt25"></p>
							<div class="clearfix mt25 f14 lh40">
								<span class="formListSpan">剩余充值次数：<span class="color_red">${rechargeTimes }</span></span><br />
								<input type="hidden" class="form-control" id="rechargeTimes"
									name="rechargeTimes" value="${rechargeTimes }"> <span
									class="formListSpan">充值金额：</span>
									<div class="formListDiv">
										<span class="smallInput"> <span> <input
												type="text" value="" name="cashAmount" id="cashAmount" style="width: 100px;"
												class="checkedAttr" loc="errorMsgDiv"
												checkType="isNotEmpty,decmal1" alt="充值金额不能为空！,充值金额必须是数字！" />
										</span>
										</span>&nbsp;&nbsp;RMB<span id="errorMsgDiv"></span>
										<p class="color_red lh20 ml5 f12">
											最低：<span class="amount">${rechargeMinAmount }</span>元&nbsp;&nbsp;&nbsp;最高：<span
												class="amount">${rechargeMaxAmount }</span>元
										</p>
										<p class="mt25">
											<a href="javascript:submitForm();" class="miniBtn"><span>确定</span></a>
										</p>
									</div>
							</div>
					</form>
				</div>
				<!-- 银行卡充值 End -->
				
				
			</div>
			<p class="boxBotBg"></p>
			<div class="pageFrBox lh24 mt10">
				<h3>账户充值</h3>
				<p class="line1"></p>
				<ul class="pointUL mt10">
					<li>
						财务团队 7 x 17 小时 为您服务。服务时间：每天  <span class="color_yellow">早上
						<span id="rcStartTime">${rechargeStartTime }</span></span>  
						至  <span class="color_yellow">次日凌晨
						<span id="rcEndTime">${rechargeEndTime }</span>
						</span>。
					</li>
					<li>
						银行卡充值时所产生的转账手续费，由网站承担。以“手续费反馈”订单类型在您充值成功后补到您的账户中。充值完成后请留意【<a href="${contextPath }/finance/showFinanceDetail.html" class="color_blue underline">财务明细</a>】下的财务分类
					</li>
				</ul>
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