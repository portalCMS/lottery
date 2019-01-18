<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-营收统计</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript" src="${contextPath }/jsp/js/team/ykreport.js"></script>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/team_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<tr>
						<td width="2%" height="50" align="left" valign="middle"></td>
						<td width="98%" height="50" align="left" valign="middle" colspan="3">&nbsp;&nbsp;用户名：
							<span class="smallInput">
								 <span><input type="text" id="userName" style="width: 150px" class="_param"/></span>
							</span>
							<label style="margin-left: 10px;color:red;">
								用户名为空，则统计您整个团队的数据（包含您自己）。否则，请输入指定直属用户。
							</label>
						</td>
					</tr>
					<tr>
						<td width="2%" height="50" align="left" valign="middle"></td>
						<td width="98%" height="50" align="left" valign="middle" colspan="3">设定条件：
							<span><input type="radio" name="staType" id="staType1" value="1" checked="checked"/><label for="staType1">团队盈亏记录</label></span>
							&nbsp;&nbsp;
							<span><input type="radio" name="staType" id="staType2" value="2"/><label for="staType2">团队今日盈亏</label></span>
							&nbsp;&nbsp;
							<span><input type="radio" name="staType" id="staType3" value="3"/><label for="staType3">团队历史盈亏</label></span>
						</td>
					</tr>
					<tr>
						<td width="2%" height="50" align="left" valign="middle"></td>
						<td width="30%" height="50" align="left" valign="middle">设定时间：
							<span class="smallInput"> <span> 
								<input type="text" id="sdate" readonly="readonly" style="width: 100px" />
								</span>
							</span>&nbsp;&nbsp;至&nbsp;&nbsp;
							<span class="smallInput"> <span> 
								<input type="text" id="edate" readonly="readonly" style="width: 100px" />
								</span>
							</span>
						</td>
						<td width="25%" height="50" align="left" valign="middle"><a
							href="javascript:;" class="miniBtn" id="queryOrder"><span
								style="min-width: 80px;">查 询</span></a></td>
						<td width="5%" height="50" align="left" valign="middle"></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th height="39" align="center" valign="middle">用户名</th>
							<th height="39" align="center" valign="middle" class="_createTime">时间</th>
							<th height="39" align="center" valign="middle" class="_type" style="display:none;">类型</th>
							<th height="39" align="center" valign="middle">投注总额</th>
							<th height="39" align="center" valign="middle">返点总额</th>
							<th height="39" align="center" valign="middle" class="_saleAmount">实际销售总额</th>
							<th height="39" align="center" valign="middle">中奖总额</th>
							<th height="39" align="center" valign="middle" class="_ykAmount">游戏总盈亏</th>
							<!-- <th height="39" align="center" valign="middle">活动总领取</th>
							<th height="39" align="center" valign="middle">充提手续费</th>
							<th height="39" align="center" valign="middle">总结算</th>
							<th height="39" align="center" valign="middle">团队详情</th> -->
						</tr>
					</thead>
					<tbody id="orderTbody">
					</tbody>
					<!-- 隐藏tr对象，方便js进行tr对象克隆。 -->
					<tr class="_hidTr" style="display:none;">
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle" class="_createTime"></td>
						<td height="39" align="center" valign="middle" class="_type" style="display:none;"></td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<td height="39" align="center" valign="middle" class="_amount"></td>
						<!-- <td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td> -->
					</tr>
					
				</table>
				<!-- 分页-->
				<xb:foot ajax="true"></xb:foot>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>