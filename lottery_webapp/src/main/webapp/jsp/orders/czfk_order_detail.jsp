<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>手续费返款订单详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript">
function showOrignalOrder(){
		var attrs = new Array();
		attrs.push("id");
		var values = new Array();
		values.push($("#orderId").val());
		formPost.submitForm(ctx+"/finance/rcOrderDetail.html",attrs,values);
}
</script>
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
						<li class="fl mr10">手续费返款</li>
						<li class="fl mr10">></li>

						<li class="fl color_8d">${order.orderNumber }</li>
						<input type="hidden" value="${order.rsvdc1 }" id="orderId"/>
					</ul>
					<div class="fr">
						<a href="javascript:window.history.go(-1);" class="color_yellow underline">返回</a>
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<ul class="p20">
						<li>订单类型：手续费返款</li>
						<li>订单编号：${order.orderNumber }</li>
						<li>来源订单(充值订单)：
						<a href="javascript:showOrignalOrder();" class="color_blue underline">${order.rsvst1 }
						</a>
						</li>
						<li>充值方式：现金充值</li>
						<li>订单金额：<span class="color_red">${order.receiveAmount }</span>
						</li>
						<li>创建时间：<span class="color_8d">${order.createTime }</span>
						</li>
						<li>订单状态：<span class="correctHint">
								<c:if test="${order.orderStatus==17002 }">成功</c:if>
								<c:if test="${order.orderStatus==17003 }">失败</c:if>
								<c:if test="${order.orderStatus==17004 }">过期</c:if>
								</span>
						</li>

					</ul>
					<p class="line5"></p>
				</div>
			</div>
			<p class="boxBotBg"></p>
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