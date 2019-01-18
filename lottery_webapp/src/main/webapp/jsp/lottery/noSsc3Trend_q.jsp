<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>前三基本号码走势图</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<link href="${contextPath }/jsp/css/notrend.css" rel="stylesheet"
	type="text/css" />
<script src="${contextPath }/jsp/lottery/js/notrend/noSsc3Trend.js"></script>
</head>
<body>
<%-- 	<%@ include file="../include/head.jsp"%> --%>
	<input id="noType" type="hidden" value="q3">
	<!-- content -->
	<div class="k990 mainTrend">
		<div class="clearfix mt15 ">
			<div class="clearfix zhuiQiDl">
				<div class="clearfix ">
					<ul id="trendSelect" class="zhuiQiUl fl clearfix">
						<li class="current" name="30">近30期</li>
						<li name="50">近50期</li>
						<li name="100">近100期</li>
						<li name="1">今天</li>
						<li name="2">昨天</li>
						<li name="3">前天</li>
					</ul>
					<div class="_lotInfo fr color_8d"></div>
				</div>
				<p class="line1 mt10"></p>
				<div class="mt5">
					<%@ include file="./noTrend.jsp"%>
				</div>
			</div>
			<p class="line1"></p>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="_noTrendDiv ">
				<thead>
					<tr class="_noTrendTh">
						<td align="center" valign="middle" rowspan="2">期号</td>
						<td align="center" valign="middle" rowspan="2">开奖号码</td>
						<td align="center" valign="middle" colspan="10">万位开奖号码</td>
						<td align="center" valign="middle" colspan="10">千位号码分布</td>
						<td align="center" valign="middle" colspan="10">百位号码分布</td>
						<td align="center" valign="middle" colspan="10">开奖号码分布</td>
						<td align="center" valign="middle" colspan="3">组选特征</td>
						<td align="center" valign="middle" rowspan="2">大小</td>
						<td align="center" valign="middle" rowspan="2">单双</td>
<!-- 						<td align="center" valign="middle" rowspan="2">质合</td> -->
						<td align="center" valign="middle" rowspan="2">跨度</td>
						<td align="center" valign="middle" rowspan="2" style="border: none;">和值</td>
					</tr>
					<tr class="_noTr">
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">豹子</td>
						<td align="center" valign="middle" class="_analog">组三</td>
						<td align="center" valign="middle" class="_analog">组六</td>
					</tr>
				</thead>
				<tbody id="noTb">
				</tbody>
				<tr class="_lodingTr" style="display: none; height: 30px;">
					<td align="center" valign="middle" colspan="43"><img
						src="${contextPath }/jsp/images/l_loading.gif"></img> 数据加载中，请稍候…</td>
				</tr>
				<tr class="_hidTr" style="display: none; height: 30px;">
					<td align="center" valign="middle" class="_issue"></td>
					<td align="center" valign="middle" class="_lotNum"></td>
				</tr>
				<td align="center" valign="middle" class="_num3_group"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_num4_group"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_num5_group"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_allnum_group"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_num3_group"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_dx"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_ds"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_zh"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_zxst"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_kd"
					style="display: none;">0</td>
				<td align="center" valign="middle" class="_sum"
					style="display: none;">0</td>
				<tr class="_hidCountTr" style="display: none; height: 30px;">
					<td align="center" valign="middle" class="_count_info" colspan="2"></td>
				</tr>
				<tr class="_hidLineTr" style="display: none;">
					<td colspan="49" style="height: 2px;background-color: #BDB7B7"></td>
				</tr>
				<thead>
					<tr class="_noTrendTh">
						<td align="center" valign="middle" rowspan="2">期号</td>
						<td align="center" valign="middle" rowspan="2">开奖号码</td>
						<td align="center" valign="middle" colspan="10">万位开奖号码</td>
						<td align="center" valign="middle" colspan="10">千位号码分布</td>
						<td align="center" valign="middle" colspan="10">百位号码分布</td>
						<td align="center" valign="middle" colspan="10">开奖号码分布</td>
						<td align="center" valign="middle" colspan="3">组选特征</td>
						<td align="center" valign="middle" rowspan="2">大小</td>
						<td align="center" valign="middle" rowspan="2">单双</td>
<!-- 						<td align="center" valign="middle" rowspan="2">质合</td> -->
						<td align="center" valign="middle" rowspan="2">跨度</td>
						<td align="center" valign="middle" rowspan="2" style="border: none;">和值</td>
					</tr>
					<tr class="_noTr">
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						
						<td align="center" valign="middle" class="_analog">&nbsp;0&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;1&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;2&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;3&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;4&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;5&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;6&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;7&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;8&nbsp;</td>
						<td align="center" valign="middle" class="_analog">&nbsp;9&nbsp;</td>
						

						<td align="center" valign="middle" class="_analog">豹子</td>
						<td align="center" valign="middle" class="_analog">组三</td>
						<td align="center" valign="middle" class="_analog">组六</td>
					</tr>
				</thead>
			</table>
			<div class="p20">
				<h3>参数说明：</h3>
				<p class="line1"></p>
				<ul class="mt5">
					<li class="mt5">[出现总次数] 当前查询中，单个开奖号码出现的总次数</li>
					<li class="mt5">[平均遗漏值] 某个开奖号码平均隔多少期出现一次</li>
					<li class="mt5">[最大遗漏值]
						把每个数字连续没出现的期数，称为该号码的遗漏值。当前查询中，本列遗漏值中的最大值就是“最大遗漏值”</li>
					<li class="mt5">[最大连出值]
						单个开奖号码连续出现的期数值，称为该号码的连出值。当前查询中，本列连出值中的最大值就是“最大连出值”</li>
					<li class="mt5">[号码分布] 所有开奖号码的开出情况。</li>
					<li class="mt5">[号码跨度] 所有开奖号码中最大值和最小值的差值。</li>
					<li class="mt5">[大小比]
						所有开奖号码的大号和小号个数的比值。大号：5，6，7，8，9；小号：0，1，2，3，4</li>
					<li class="mt5">[单双比]
						所有开奖号码的奇号和偶号个数的比值。奇号：1，3，5，7，9；偶号：0，2，4，6，8</li>
					<li class="mt5">[质合比]
						所有开奖号码的质号和合号个数的比值。质号：1，2，3，5，7；合号：0,4，6，8，9，10</li>
					<li class="mt5">[和值] 所有开奖号码之和。</li>
				</ul>
			</div>
			<p class="line1"></p>
			<input type="hidden" id="lotCode" value="${lotteryCode }" />
		</div>
	</div>
	<div id="mylineDiv"></div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>