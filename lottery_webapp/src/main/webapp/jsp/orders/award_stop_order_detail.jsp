<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>追号订单</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript">
function showOrignalOrder(){
		var attrs = new Array();
		attrs.push("id");
		var values = new Array();
		values.push($("#orderId").val());
		formPost.submitForm(ctx+"/order/betOrderDetail.html",attrs,values);
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
						<li class="fl mr10"><a href="${fromUrl }" class="color_blue underline">财务明细</a>
						</li>
						<li class="fl mr10">></li>
						<li class="fl color_8d">${order.orderNumber }</li>
					</ul>
					<div class="fr">
						<a href="${fromUrl }" class="color_yellow underline">返回</a>
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<h3>追号撤单订单信息</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>订单编号：${order.orderNumber }</li>
						<li>投注订单编号：
						<a href="javascript:showOrignalOrder();" class="color_blue underline">${order.rsvst1 }
						</a>
						<input type="hidden" value="${order.rsvdc1 }" id="orderId"/>
						</li>
						<li>订单金额：<span class="color_red"><fmt:formatNumber value="${order.orderAmount }"  pattern="#,#00.00"/> </span>
						</li>
						<li>创建时间：<span class="color_8d"><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /> </span>
						</li>
					</ul>

					<h3>追号撤单明细</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>彩种： ${lotteryName }</li>
						<!-- <li>&nbsp;&nbsp;&nbsp;&nbsp;奖金组： 1950</li>-->
						<li class="clearfix">
							<div class="fl">
								期号：</div>
							<div class="fl">
								<ul>
									<c:forEach var="br" items="${brs }"> 
											<li>
												<span class="correctHint">${br.issueNo}期</span> 
												<span>倍数：${br.multiple}倍</span> 
											</li>
									</c:forEach>
								</ul>
							</div>

						</li>
						<li class="clearfix">
							<div class="fl">投注内容：</div>
							<div class="fl">
								<ul>
									<c:forEach var="map" items="${tempvos }">
										<li><span class="color_yellow">[${map.key }]</span> 
										    <span>${map.value }</span>
										    <span class="color_blue">[${map.value2 }]</span>
										</li>
									</c:forEach>
								</ul>
							</div>
						</li>
					</ul>
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