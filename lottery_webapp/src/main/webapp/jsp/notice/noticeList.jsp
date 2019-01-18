<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告列表</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/notice/noticelist.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>

	<!-- content -->
	<div class="k990">
		<p class="pagePo mt25">
			<a href="index.html">首页</a>网站公告
		</p>
		<div class="clearfix mt15">
			<div class="w740 fl">
				<p class="i3BoxTitle">
					<span>网站公告</span>
				</p>
				<div class="helpBorder relative noBorderT">
					<ul class="noticeLList _ul" >
						
						
					</ul>
					<xb:foot ajax="true" />
					<br>
					<li style="display: none;" class="_li">
							<div class="clearfix">
								<span class="fr f12">
									<span class="color_red">[彩票]</span>&nbsp;&nbsp;
									<span class="color_8d">2014-08-01 14:48:47</span>
								</span> 
								<a href="noticeDetail.html" class="f14">周六北京单场和胜负过关日职联延期公告</a>
							</div>
							<div class="lh22 color_5b mt10">您好！原定北京时间2014年8月9日18:00进行的J联赛（日职联）广岛三箭与鸟栖沙岩的比赛因故延期，开奖后网站将进行退票工作投注单场的退还本金，购买过关投注(即串)包含......</div>
					</li>
					<p class="boxShadow"></p>
				</div>
				
			</div>
			<div class="w240 fr">
				<p class="tc">
					<c:forEach items="${adverts }" var="advert">
						<c:if test="${advert.regionCode=='N_A'}">
							<img src="${advert.url}" width="240" />
						</c:if>
					</c:forEach>
					<!-- 
					<img src="${contextPath }/jsp/images/guanGao.jpg" width="240" />
					-->
				</p>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>新手必读</span>
				</p>
				<div class="i3Box2 relative">
					<ul class="noticeUl clearfix">
						<li><a href="${contextPath }/showHelps.html?id=37">如何绑定银行卡</a></li>
						<li><a href="${contextPath }/showHelps.html?id=35">账户充值</a></li>
						<li><a href="${contextPath }/showHelps.html?id=69">如何提款</a></li>
						<li><a href="${contextPath }/showHelps.html?id=58">如何注册</a></li>
						<li><a href="${contextPath }/showHelps.html?id=79">什么是追号投注</a></li>
						<li><a href="${contextPath }/showHelps.html?id=78">什么是恶意投注</a></li>
						<li><a href="${contextPath }/showHelps.html?id=75">什么叫彩票代购</a></li>
						<li><a href="${contextPath }/showHelps.html?id=38">如何找回密码</a></li>
					</ul>
					<p class="boxShadow"></p>
				</div>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>最新中奖</span>
				</p>
				<div class="i3Box2 relative">
					<div id="noticeGd">
						<div id="noticeGdBox">
							<ul class="i3Box4List" id="noticeGd_1">
								<c:forEach items="${ ordervos}" var="order">
									<li class="clearfix">
										<span class="span1">${order.customerName }</span>
										<span class="span2 color_blue underline">
											<a href="javascript:;" class="_betPage">${order.lotteryTypeName }</a>
										</span>
										<span class="color_b30 span3"><fmt:formatNumber value="${order.orderAmount }" pattern="#,##0.00" />元</span></li>
								</c:forEach>
							</ul>
							<ul class="i3Box4List" id="noticeGd_2"></ul>
						</div>
					</div>
					<p class="boxShadow"></p>
				</div>
			</div>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
	<script>
		//无缝滚动
		var speed = 60;
		var tab = document.getElementById("noticeGd");
		var tab1 = document.getElementById("noticeGd_1");
		var tab2 = document.getElementById("noticeGd_2");
		tab2.innerHTML = tab1.innerHTML;
		function Marquee() {
			if (tab2.offsetHeight - tab.scrollTop <= 0)
				tab.scrollTop -= tab1.offsetHeight;
			else {
				tab.scrollTop++;
			}
		}
		var MyMar = setInterval(Marquee, speed);
		tab.onmouseover = function() {
			clearInterval(MyMar);
		};
		tab.onmouseout = function() {
			MyMar = setInterval(Marquee, speed);
		};
	</script>
</body>
</html>