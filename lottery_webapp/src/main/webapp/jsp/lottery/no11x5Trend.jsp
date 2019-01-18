<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>11选5基本号码走势图</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<link href="${contextPath }/jsp/css/notrend.css" rel="stylesheet"
	type="text/css" />
<script src="${contextPath }/jsp/lottery/js/notrend/noTrend.js"></script>
</head>
<body>
<%-- 	<%@ include file="../include/head.jsp"%> --%>
	<!-- content -->
	<div class="k990 mainTrend">
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
					<%@ include file="./11x5noTrend.jsp"%>
				</div>
		</div>
		<p class="line1"></p>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="_noTrendDiv ">
			<thead>
				<tr>
					<td align="center" valign="middle" rowspan="2" width="20px">期号</td>
					<td align="center" valign="middle" rowspan="2" width="20px">开奖号码</td>
					<td align="center" valign="middle" colspan="11">第一位开奖号码</td>
					<td align="center" valign="middle" colspan="11">第二位开奖号码</td>
					<td align="center" valign="middle" colspan="11">第三位开奖号码</td>
					<td align="center" valign="middle" colspan="11"
						style="border: none;">开奖号码分布</td>
				</tr>
				<tr class="_noTr">
					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>


					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>

					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>

					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog "
						style="border: none;">11</td>
				</tr>
			</thead>
			<tbody id="noTb">
			</tbody>
			<tr class="_lodingTr" style="display: none; height: 30px;">
				<td align="center" valign="middle" colspan="43"><img
					src="${contextPath }/jsp/images/l_loading.gif"></img></td>
			</tr>
			<tr class="_hidTr" style="display: none; height: 30px;">
				<td align="center" valign="middle" class="_issue"></td>
				<td align="center" valign="middle" class="_lotNum"></td>
			</tr>
			<td align="center" valign="middle" class="_num1_group"
				style="display: none;">0</td>
			<td align="center" valign="middle" class="_num2_group"
				style="display: none;">0</td>
			<td align="center" valign="middle" class="_num3_group"
				style="display: none;">0</td>
			<td align="center" valign="middle" class="_allnum_group"
				style="display: none;">0</td>
			<td align="center" valign="middle" class="_num6_group"
				style="display: none;">0</td>
			<tr class="_hidCountTr" style="display: none; height: 30px;">
				<td align="center" valign="middle" class="_count_info" colspan="2"></td>
			</tr>
			<tr class="_hidLineTr" style="display: none;">
					<td colspan="46" style="height: 2px;background-color: #BDB7B7"></td>
				</tr>
			<thead>
				<tr class="_noTrendTh">
					<td align="center" valign="middle" rowspan="2">期号</td>
					<td align="center" valign="middle" rowspan="2">开奖号码</td>
					<td align="center" valign="middle" colspan="11">第一位开奖号码</td>
					<td align="center" valign="middle" colspan="11">第二位开奖号码</td>
					<td align="center" valign="middle" colspan="11">第三位开奖号码</td>
					<td align="center" valign="middle" colspan="11">开奖号码分布</td>
				</tr>
				<tr class="_noTr">
					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>


					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>

					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>

					<td align="center" valign="middle" class="_analog">1</td>
					<td align="center" valign="middle" class="_analog">2</td>
					<td align="center" valign="middle" class="_analog">3</td>
					<td align="center" valign="middle" class="_analog">4</td>
					<td align="center" valign="middle" class="_analog">5</td>
					<td align="center" valign="middle" class="_analog">6</td>
					<td align="center" valign="middle" class="_analog">7</td>
					<td align="center" valign="middle" class="_analog">8</td>
					<td align="center" valign="middle" class="_analog">9</td>
					<td align="center" valign="middle" class="_analog">10</td>
					<td align="center" valign="middle" class="_analog">11</td>
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
			</ul>
		</div>
		<p class="line1"></p>
		<input type="hidden" id="lotCode" value="${lotteryCode }" />
	</div>
	<div id="mylineDiv"></div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>