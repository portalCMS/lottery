<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>投注返款订单详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/order/lottery_trace_order_detail.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<c:if test="${menu=='team' }">
				<%@ include file="../include/team_menu.jsp"%>
			</c:if>
			<c:if test="${menu=='profile' }">
				<%@ include file="../include/profile_menu.jsp"%>
			</c:if>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="clearfix p10 ">
					<ul class="fl clearfix">
						<li class="fl mr10"><a href="${fromUrl }" class="color_blue underline">财务明细</a></li>
						<li class="fl mr10">></li>
						<li class="fl mr10">投注返款</li>
						<li class="fl mr10">></li>
						<li class="fl color_8d">${order.orderNumber }</li>
						<input type="hidden" value="${lotCode }" id="lotCode"/>
						<input type="hidden" value="${order.id }" id="orderId"/>
						<input type="hidden" value="${fromType}" id="fromType"/>
					</ul>
					<div class="fr">
						<a href="javascript:window.history.go(-1);" 
							class="color_yellow underline">返回</a>
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<h3>订单信息</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>订单类型：投注返款</li>
						<li>订单编号：${order.orderNumber }</li>
						<li>订单金额：<span class="color_red">${order.orderAmount }</span>
						</li>
						<li>自身返点：<span class="color_red">${order.rsvdc4 }</span>
						</li>
						<li>创建时间：<span class="color_8d"><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
						</li>
						<li>订单状态：
							<span class="correctHint">
								<c:if test="${order.orderStatus==17002 }">成功</c:if>
								<c:if test="${order.orderStatus!=17002 }">失败</c:if>
							</span>
						</li>
					</ul>
				<h3>投注内容</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li class="clearfix">
							<span class="f12 fl color_yellow">彩种:${lotteryName }</span> 
							<i class="underline fr _show10Detail" id="panel_tip">展开</i>
							<input type="hidden" id="showIndex"/>
						</li>
						<li class="clearfix">
								<div class="panel_items">
									<table cellspacing="0" width="100%" id="dataTableDetail" align="center">
									<c:forEach var="br" items="${brs }" varStatus="status"> 
										<div class="panel_item _showDetail">
											<div class="ward_info clearfix">
												<div class="fl _myInfo">
														<input class="_index" type="hidden" value="${status.index }"/>
														<input class="_orderNo" type="hidden" value="${br.orderNo }"/>
														<input class="_issueNo" type="hidden" value="${br.issueNo }"/>
														<span>${br.issueNo}期</span>
														<span>${br.multiple}倍</span> 
														<c:if test="${br.betStatus == 21001 }">
															<span class="color_8d">投注成功</span> 
															<a href="javascript:;" class="underline color_blue _cancel">撤销此期追号</a>
														</c:if>
														<c:if test="${br.betStatus == 21002 }">
															<span class="color_red">已中奖</span> 
														</c:if>
														<c:if test="${br.betStatus == 21003 }">
															<span class="color_8d">未中奖</span>
														</c:if>
														<c:if test="${br.betStatus == 21004 }">
															<span class="color_8d"><del>已撤单</del></span>
														</c:if>
												</div>
												<div class="fr">
												  <span class="_issueInfo correctHint"></span> 
														 &Xi;
												</div>
											</div>
											<div class="panel_info ">
												<div class="panel_display _betInfo">
												</div>
												<ul class="_bets">
												</ul>
											</div>
										</div>
									</c:forEach>
									</table>
								</div>
						</li>
					</ul>
				</div>
			</div>
			<p class="boxBotBg"></p>
			<li class="_hidInfo" style="display:none;">
				<span class="color_blue"><span href="javascript:;" class="_name">[前一直选]</span></span>
				<span class="color_red _status"></span>
				<span class="color_red _award">0.00</span>
				<span class="_bilenum"></span>
				<span class="_betnum"></span>
				<span class="correctHint _betModel"></span>
				<span class="correctHint _betMoney">0.00</span>
			</li>
			<span class="color_yellow _awardInfo" style="display:none;"> [开奖号码] </span> 
			<span class="_openNum" style="display:none;">1,2,3,4,5</span>
			<span class="_twAmount color_red" style="display:none;">0.00</span>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
<script>
	$(document).ready(function(){
		$("#financeMenu").addClass("current");
		$("#ordersMenu").addClass("current");
	});
</script>
</html>