<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>

</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990">
		<p class="pagePo mt25">
			<a href="index.html">首页</a><a href="showNotices.html">网站公告</a>${entity.title }
		</p>
		<div class="clearfix mt15">
			<div class="w740 fl">
				<p class="i3Line"></p>
				<div class="helpBorder relative noBorderT">
					<h2 class="noticeDTitle">${entity.title }</h2>
					<p class="color_8d mt10 tc">[${entity.detailTypeName }]&nbsp;&nbsp;<fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
					<input type="hidden" value='${entity.content }'/>
					<div class="noticeDCen">
						${entity.content }
					</div>
					<p class="boxShadow"></p>
				</div>
				<div class="clearfix mt15">
					<div class="w365 fl">
						<p class="i3Line"></p>
						<p class="i3BoxTitle">
							<span>新手必读</span>
						</p>
						<div class="i3Box2 relative">
							<ul class="noticeLBD hidden clearfix">
								<li><a href="${contextPath }/showHelps.html?id=37">如何绑定银行卡</a></li>
								<li><a href="${contextPath }/showHelps.html?id=35">账户充值</a></li>
								<li><a href="${contextPath }/showHelps.html?id=69">如何提款</a></li>
								<li><a href="${contextPath }/showHelps.html?id=58">如何注册</a></li>
								<li><a href="${contextPath }/showHelps.html?id=79">什么是追号投注</a></li>
								<li><a href="${contextPath }/showHelps.html?id=78">什么是恶意投注</a></li>
								<li><a href="${contextPath }/showHelps.html?id=76">彩票投注后可以撤单吗</a></a></li>
								<li><a href="${contextPath }/showHelps.html?id=75">什么叫彩票代购</a></li>
							</ul>
							<p class="boxShadow"></p>
						</div>
					</div>
					<div class="w365 fr">
						<p class="i3Line"></p>
						<p class="i3BoxTitle">
							<span>最新中奖</span>
						</p>
						<div class="i3Box2 relative">
							<div id="noticeDListGd">
								<div id="noticeGdBox">
									<ul class="i3Box4List noticeDList clearfix" id="noticeGd_1">
										<c:forEach items="${ ordervos}" var="order">
											<li class="clearfix">
												<span class="span1">${order.customerName }</span>
												<span class="span2 color_blue underline">
													<a href="javascript:;" class="_betPage">${order.lotteryTypeName }</a>
												</span>
												<span class="color_b30 span3"><fmt:formatNumber value="${order.orderAmount }" pattern="#,##0.00" />元</span></li>
										</c:forEach>
									</ul>
									<ul class="i3Box4List noticeDList clearfix" id="noticeGd_2"></ul>
								</div>
							</div>
							<p class="boxShadow"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="w240 fr">
				<p class="tc">
					<img src="${contextPath }/jsp/images/activity/sactivity.jpg" width="240" />
				</p>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>公告列表</span>
				</p>
				<div class="i3Box2 relative">
					<ul class="noticeDRList">
						<c:forEach items="${ams }" var="am">
							<li class="clearfix"><span class="color_8d fr"><fmt:formatDate value="${am.createTime }" pattern="MM-dd"/> </span> <!-- 程序截取标题字符 -->
							<a href="shownotice.html?id=${am.id }">【${am.detailTypeName }】${am.title }</a></li>
						</c:forEach>
					</ul>
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
		var tab = document.getElementById("noticeDListGd");
		var tab1 = document.getElementById("noticeGd_1");
		var tab2 = document.getElementById("noticeGd_2");
		tab2.innerHTML = tab1.innerHTML;
		function Marquee() {
			if (tab2.offsetHeight - tab.scrollTop <= 0)
				tab.scrollTop -= tab1.offsetHeight
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