<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>投注记录</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/order/lottery_order_detail.js"></script>
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
						<li class="fl mr10"><a href="${fromUrl }"
							class="color_blue underline _fromUrlName">${fromUrlName }</a></li>
						<li class="fl mr10">></li>
						<li class="fl mr10">${brvo.orderNo }</li>
					</ul>
					<div class="fr">
						<a href="${fromUrl }" class="color_yellow underline">返回</a>
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<h3>注单信息</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>注单编号：${brvo.orderNo }  <c:if test="${brvo.betStatus == 21001 and brvo.customerId == customerkey.id }"><a href="javascript:;" class="_cancel ml20 color_blue underline">撤销此单</a></c:if></li>
						<li>投注金额：<span class="color_red"><fmt:formatNumber value="${brvo.betMoney * brvo.multiple }" pattern="#,##0.00" /> </span>
						</li>
						<li>生成时间：<span class="color_8d"><fmt:formatDate value="${brvo.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
						</li>
						<c:if test="${brvo.betStatus == 21001 }"><li>当前状态：<span class="correctHint _status">投注成功</span></c:if>
						<c:if test="${brvo.betStatus == 21002 }">
							<c:set value="${ fn:split(brvo.awardLevel, ',') }" var="str1" />
							<c:set value="${ fn:split(brvo.awardCount, ',') }" var="str2" />
							<li>
								当前状态：<span class="correctHint _status">已中奖(
								<c:forEach items="${ str1 }" var="s" varStatus="i">
									${str2[i.index]}个${s}等奖 &nbsp;&nbsp;</c:forEach>)
							</span>
						</c:if>
						<c:if test="${brvo.betStatus == 21003 }"><li>当前状态：<span class="color_8d _status">未中奖</span></c:if>
						<c:if test="${brvo.betStatus == 21004 }"><li>当前状态：<span class="color_8d _status"><del>已撤单</del></span></c:if>
						</li>
						<c:if test="${brvo.betStatus == 21002 }">
						<li>中奖金额：<span class="color_red"><fmt:formatNumber value="${brvo.winMoney }" pattern="#,##0.00" /></span>
						</li>
						</c:if>
						<c:if test="${brvo.betStatus != 21002 }">
						<li>中奖金额：<span class="color_red">0.00</span>
						</li>
						</c:if>
					</ul>
					<h3>投注内容</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>彩种： ${brvo.lotteryCode }</li>
						<li>模式： ${brvo.betModelName }</li>
						<li>返点：<fmt:formatNumber value="${brvo.rebates }" pattern="0.0%" /> </li>
						<li class="clearfix">
							期号： ${brvo.issueNo }期</li>
						<li>倍数：  ${brvo.multiple }倍</li>
						<li>玩法： ${brvo.playCode }<span class="color_blue">(${brvo.selectCodeName})</span><input type="hidden" id="${brvo.id}" class="_param" /> </li>
						<li>开奖号码：<span class="color_red">${brvo.opernBetNumber }</span>
						</li>
						<li>投注内容：<textarea cols="112" rows="15" style="resize:none;border: 0;background-color: white;"><c:if test="${not empty brvo.bileNum }">胆码：${brvo.bileNum}托码:${brvo.betNum }</c:if><c:if test="${empty brvo.bileNum}">${brvo.betNum }</c:if></textarea>
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
		$("#myBetMenu").addClass("current");
	});
</script>
</html>