<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${lotteryName }</title>
<%@ include file="../include/js_asset.jsp"%>
<%@ include file="../include/css_asset.jsp"%>
<script type="text/javascript"
	src="${contextPath }/jsp/jspmp/js/orders/initssc.js"></script>
<script type="text/javascript"
	src="${contextPath }/jsp/jspmp/js/betUtil.js"></script>


</head>
<body>

	<header class="clearfix"> <i class="icon icon-nav"></i>
	<div class="title">
		<span>${lotteryName }</span>
	</div>
	<a href="${contextPath }/showopenssc.shtml?lotteryCode=${lotteryCode }"
		class="back">开奖号码</a> </header>

	<aside></aside>

	<section class="container"> <input id="lotGroup"
		type="hidden" value="${lotteryGroup }" /> <input id="lotCode"
		type="hidden" value="${lotteryCode }" /> <input id="endBetTime"
		type="hidden" value="${task.endBetTime }" />
	<div class="ssc-box" id="ssc">
		<!-- Tab -->
		<div class="ssc-num"></div>
		<!-- SubTab -->
		<div class="ssc-type" id="sscType"></div>
		<!-- Boll Pool -->
		<div class="ssc-main">
			<!-- Choose boll items -->
			<div class="ssc-main-choose"></div>
			<!-- Control Center -->
			<div class="ssc-selector-res">
				<p class="CSR_get hidden">
					如中奖，奖金 <span>0.00</span> 元，盈利 <span>0.00</span> 元.
				</p>
				<p class="hidden">
					<select class="CSR_select">
						<option selected="selected" value="0">机选</option>
						<option value="1">机选1注</option>
					</select> <a href="#" class="CSR_btn hidden" onclick="return false;">↓
						确认选号并添加到投注区</a> <a href="#" class="CSR_clear hidden">清空全部</a>
				</p>
				<div class="wrap clearfix" style="padding-top: 0">
					<a href="#" class="CSR_select_one btn btn-gradient fr">机选一注</a>
				</div>
			</div>
		</div>
		<!-- Result Box -->
		<div class="ssc-resluts hidden">
			<div class="ssc-resluts-list"></div>
			<!-- Doublint Controller -->
			<p class="ssc-resluts-doubling">
				<a href="#">清空列表</a> 单倍下注：<strong>0</strong> ，金额：<strong>0.00</strong>
				元，您选择了 <span><i>-</i><input type="text" id="doubling"
					value="1" /><i>+</i></span> 倍。 <br /> <em>注：倍率不大于<b>1000</b></em>
			</p>
		</div>
	</div>

	<div class="touzhu-box ssc-touzhu-box  fixed-b">
		<div class="touzhu-btn">
			<p>
				当前第<span class="series text-warn">${task.lotterySeries }</span>期<span
					class='CSR_info'>您共选择了:<span>0</span>注，共<span
					class="text-warn">0.00</span>元
				</span> 离投注截止还有 <span class="text-warn" id="end_time_hour"
					style="display: none;">${betHour }</span> <i style="display: none;">时</i>
				<span class="text-warn" id="end_time_minute">${betMinutes }</span><i>分</i>
				<span class="text-warn" id="end_time_second">${betSeconds }</span><i>秒</i>
				<input id="tempCode" value="${lotteryCode }" type="hidden" />
				<!-- 火狐input域缓存清理 autocomplete="off" -->
				<input type="hidden" name="token" id="token" value="${token }"
					autocomplete="off">
			</p>
			<div class="clearfix">
				<a href="#" role="button" class="btn btn-sm btn-disable CSR_btn_tz">立即投注</a>
				<a href="#" role="button" class="btn btn-sm fr">加入号码栏</a>
			</div>
		</div>
	</div>
	</section>
	<script type="text/javascript"
		src="${contextPath }/jsp/jspmp/js/jxssc_config.js"></script>
	<script type="text/javascript">
		// 配置需要关闭的玩法，这个需要放在 main.js 之前
		// window.ConfigClose = [[4,1], [4,2], [4,3]];
		window.ConfigClose = [];
	</script>
	<script type="text/javascript"
		src="${contextPath }/jsp/jspmp/js/jxssc.js"></script>
	<script type="text/javascript">
		// 初始化
		var ssc = new SSC($("#ssc"));
	</script>

</body>
</html>