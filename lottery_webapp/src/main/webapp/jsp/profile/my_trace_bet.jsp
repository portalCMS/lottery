<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-追号记录</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<script src="${contextPath }/jsp/js/profile/my_trace_bet.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="50" align="left" valign="middle" colspan="4" class="_setTime"><span style="margin-left: 16px;">&nbsp;</span>设定时间：
							<span class="smallInput">
								<span><input type="text" id="sdate" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<span style="margin-left: 10px;margin-right: 10px">至</span>
							<span class="smallInput">
								<span><input type="text" id="edate" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td width="15%" height="50" align="left" valign="middle"><span style="margin-left: 16px;">&nbsp;</span>选择彩种：
							<select id="rsvst2" name="select" class="select2 _lotteryCode _params" style="width: 110px;">
								<option value="">全部彩种</option>
								<c:forEach items="${types }" var="type">
									<option value="${type.lotteryCode }" totalTimes="${type.totalTimes }">${type.lotteryName }</option>
								</c:forEach>
							</select>
						</td>
						<td width="15%" height="50" align="left" valign="middle">彩种奖期：
							<select class="select2  _issueNo _params" id="rsvst3" style="width: 110px;">
								<option value="">全部奖期</option>
							</select>
						</td>
						<td width="12%" height="50" align="left" valign="middle">状态：
							<select id="orderStatus" class="select2 _params" style="width: 100px;">
								<option value="0">全部状态</option>
								<option value="1">追号中</option>
								<option value="2">已完成</option>
						</select>
						</td>
						<td width="11%" height="50" align="left" valign="middle">
							<a id="queryOrder" href="javascript:;" class="miniBtn">
								<span style="min-width: 80px;">查 询</span>
							</a>
						</td>
					</tr>
				</table>
				<div class="mb10">
					<span class="ml20">投注金额：<span class="color_red mr20 _amount" id="inAmount">0.00</span>
					<span class="">&nbsp;中奖金额：<span class="color_red mr20 _amount" id="outAmount">0.00</span>
				</div>
				<p class="line5"></p>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th width="17%" height="39" align="center" valign="middle">发起时间</th>
							<th height="39" align="center" valign="middle">彩种</th>
							<th width="10%" height="39" align="center" valign="middle">起始期数</th>
							<th height="39" align="center" valign="middle">总金额</th>
							<th height="39" align="center" valign="middle">完成金额</th>
							<th height="39" align="center" valign="middle">完成状态</th>
							<th height="39" align="center" valign="middle">取消金额</th>
							<th height="39" align="center" valign="middle">中奖状态</th>
							<th height="39" align="center" valign="middle">追中即停</th>
							<th height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody id="traceOrders"></tbody>
					<!-- 隐藏tr，方便clone -->
					<tr class="_hidtr" style="display:none;">
						<td height="39" align="center" valign="middle">2014-6-5 02:21:11</td>
						<td height="39" align="center" valign="middle">重庆时时彩</td>
						<td height="39" align="center" valign="middle">123121</td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<td height="39" align="center" valign="middle" class="_amount">完成金额</td>
						<td height="39" align="center" valign="middle"><span class="color_8d">22/22期</span></td>
						<td height="39" align="center" valign="middle" class="_amount">取消金额</td>
						<td height="39" align="center" valign="middle"><span></span><p class="color_red _amount">中奖状态</p></td>
						<td height="39" align="center" valign="middle"><span>追中即停</span></td>
						<td height="39" align="center" valign="middle">
							<a href="javascript:;" class="color_blue underline _detailInfo">追号单详情</a>
							<input type="hidden"  />
						</td>
					</tr>
				</table>
				<xb:foot ajax="true"></xb:foot>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>