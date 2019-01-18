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
			<a href="${contextPath }/index.html">首页</a><a href="showActivityList.html">活动大厅</a>${activity.title }
		</p>
		<div class="clearfix mt15">
			<div class="w740 fl">
				<p class="i3Line"></p>
				<div class="helpBorder relative noBorderT">
					<h2 class="noticeDTitle">${activity.title }</h2>
					<p class="color_red mt10 tc">[${activity.typeName }]&nbsp;&nbsp;
					<span>${activity.starttime }</span>
					至 <span>${activity.endtime }</span></p>
					<div class="noticeDCen">
						${activity.summary }
					</div>
					
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
				</p>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>活动列表</span>
				</p>
				<div class="i3Box2 relative">
					<ul class="noticeDRList">
<%-- 						<c:forEach items="${ams }" var="am"> --%>
<%-- 							<li class="clearfix"><span class="color_8d fr"><fmt:formatDate value="${am.createTime }" pattern="MM-dd"/> </span> <!-- 程序截取标题字符 --> --%>
<%-- 							<a href="shownotice.html?id=${am.id }">【${am.detailTypeName }】${am.title }</a></li> --%>
<%-- 						</c:forEach> --%>
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