<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-个人盈亏</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<%-- <script src="${contextPath }/jsp/js/echarts/esl.js"></script>
<script src="${contextPath }/jsp/js/echarts/echarts.js" type="text/javascript"></script>
 --%>
<%-- <script src="${contextPath }/jsp/js/highchart/jquery-1.4.2.min.js"></script> --%>
<%-- <script src="${contextPath }/jsp/js/highchart/highcharts.js"></script> --%>
<script src="http://cdn.anychart.com/js/latest/anychart.min.js" type="text/javascript"></script> 
<script src="${contextPath }/jsp/js/profile/personalFinanceDetail.js"></script>
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<tr>
						<td width="25%" height="50" align="center" valign="middle" class="_selectTime">设定时间：
							<select id="timeList" name="select" class="ml10 select2" style="width: 100px;">
								<option value="0">今日记录</option>
								<option value="1">历史记录</option>
							</select>
						</td>
						<td width="50%" height="50" align="left" valign="middle" colspan="4">
							<span class="smallInput">
								<span><input type="text" id="sdate" readonly="readonly" style="width: 140px" /></span>
							</span>
							<span style="margin-left: 10px;margin-right: 10px">至</span>
							<span class="smallInput">
								<span><input type="text" id="edate" readonly="readonly" style="width: 140px" /></span>
							</span>
						</td>
						<td width="30%" height="50" align="left" valign="middle">
							<a href="javascript:void(0);" class="miniBtn _ykBtn"><span style="min-width: 80px;">查 询</span></a>
						</td>
					</tr>
					<p class="ml15 mt10 color_red" id="tip" style="display:none;">历史记录最多只能查询您31天的个人盈亏数据</p>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th height="39" align="center" valign="middle">用户名</th>
							<th height="39" align="center" valign="middle">时间</th>
							<th height="39" align="center" valign="middle">投注总额</th>
							<th height="39" align="center" valign="middle">返点总额 </th>
							<th height="39" align="center" valign="middle">实际销售总额</th>
							<th height="39" align="center" valign="middle">中奖总额</th>
							<th height="39" align="center" valign="middle">总盈亏</th>
						</tr>
					</thead>
					<tbody id="ykdata"></tbody>
					<tr class="_ykclone" style="display:none;">
						<td height="39" align="center" valign="middle">用户名</td>
						<td height="39" align="center" valign="middle">时间</td>
						<td height="39" align="center" valign="middle" class="_amount">投注总额</td>
						<td height="39" align="center" valign="middle" class="_amount">返点总额 </td>
						<td height="39" align="center" valign="middle" class="_amount">实际销售总额</td>
						<td height="39" align="center" valign="middle" class="_amount">中奖总额</td>
						<td height="39" align="center" valign="middle" class="_amount">总盈亏</td>
					</tr>
					<tr class="_calcSta" style="display:none;color: red;">
						<td height="39" align="center" valign="middle" colspan="2" style="font-size: 16px;">合&nbsp;&nbsp;计</td>
						<td height="39" align="center" valign="middle" class="_amount">投注总额</td>
						<td height="39" align="center" valign="middle" class="_amount">返点总额 </td>
						<td height="39" align="center" valign="middle" class="_amount">实际销售总额</td>
						<td height="39" align="center" valign="middle" class="_amount">中奖总额</td>
						<td height="39" align="center" valign="middle" class="_amount">总盈亏</td>
					</tr>
				</table>
				<div style="margin-left:15px;margin-top:15px;">
					<a href="javascript:void(0);" class="miniBtn _showDayGrapBtn" style="display:none;"><span style="min-width: 80px;">可视化图表</span></a>
					<a href="javascript:void(0);" class="miniBtn _showDaysGrapBtn" style="display:none;"><span style="min-width: 80px;">查看盈亏统计</span></a>
				</div>
				<br/>
				<div id="grapReport" style="height: 400px; width:750px;"></div>
				<xb:foot ajax="true"></xb:foot>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>