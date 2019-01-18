<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-账户充值</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox">
				<font color="red">
					<c:if test="${not empty success }">
						${success }
					</c:if>
					<c:if test="${empty success }">
						${errorMsg }
					</c:if>
				</font>
				<p class="f14 mt55">帮助</p>
				<p class="line4 mt10"></p>
				<div class="lh24 mt10 color_red">
					充值提示信息：
					每天的充值处理时间为：早上 9:00 至 次日凌晨2:00<br>
					充值后,请手动刷新您的余额查看相关帐变信息,若超过3分钟未到账,请与平台在线客服联系。<br>
				</div>
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