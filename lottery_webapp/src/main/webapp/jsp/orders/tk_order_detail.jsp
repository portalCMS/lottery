<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>现金提款订单详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
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
						<li class="fl mr10"><<a href="${fromUrl }" class="color_blue underline">财务明细</a></li>
						<li class="fl mr10">></li>
						<li class="fl mr10">提款申请</li>
						<li class="fl mr10">></li>

						<li class="fl color_8d">${order.orderNumber }</li>
					</ul>
					<div class="fr">
						<a href="javascript:window.history.go(-1);" 
						class="color_yellow underline">返回</a>
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<ul class="p20">
						<li>订单类型：提款申请</li>
						<li>订单编号：${order.orderNumber }</li>
						<li>订单金额：<span class="color_red _amount">${order.orderAmount }</span>
						</li>
						<li>创建时间：<span class="color_8d">${order.createTime }</span>
						</li>
						<li>订单状态：
							<span class="correctHint">
								<c:if test="${order.orderStatus==17002 }">成功</c:if>
								<c:if test="${order.orderStatus==17003 }">失败</c:if>
								<c:if test="${order.orderStatus==17004 }">过期</c:if>
							</span>
						</li>
						<li>
							<p class="line4"></p>
						</li>
						<li>提款银行：${order.rsvst5 }</li>
						<li>提款卡号：${order.rsvst4 }</li>
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