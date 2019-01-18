<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>普通投注订单详情</title>
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
<%-- 		<input type="hidden" id="token" name="token" value="${token}"> --%>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="clearfix p10 ">
					<ul class="fl clearfix">
						<li class="fl mr10">
							<a href="${fromUrl }" class="color_blue underline">财务明细</a>
						</li>
						<li class="fl mr10">></li>
						<li class="fl mr10">普通投注订单</li>
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
					<h3>订单信息</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>订单类型：普通投注扣款</li>
						<li>订单编号：${order.orderNumber }</li>
						<li>投注用户：<span class="color_blue">${order.createUser }</span></li>
						<li>订单金额：<span class="color_red _amount">${order.orderAmount }</span></li>
						<li>创建时间：<span class="color_8d"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
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
						<li class="color_yellow">彩种：${order.rsvst2 }</li> 
						<li>期号：${order.rsvst3 }期</li> 
						<c:forEach var="br" items="${betRecords}">
						<li>
							<span class="color_blue">${br.playName }</span>
							<span>${br.multiple}倍</span>
							<span>${br.betModelName }</span>
							<span class="color_red _amount">${br.multiple*br.betMoney}</span>
							<c:if test="${br.betStatus == 21001 }">
								<span class="color_8d">投注成功</span> 
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
							<span>${br.betContent }</span>
						</li>
						</c:forEach>
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