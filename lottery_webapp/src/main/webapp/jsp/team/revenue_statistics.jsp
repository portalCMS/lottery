<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-营收统计</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/team/revenue.js"></script>
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
						<td width="2%" height="50" align="center" valign="middle">&nbsp;</td>
						<td height="50" align="left" valign="middle" colspan="4" class="_setTime">设定时间：
							<span class="smallInput">
								<span><input type="text" id="startTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<span style="margin-left: 10px;margin-right: 10px">至</span>
							<span class="smallInput">
								<span><input type="text" id="endTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td width="2%" height="50" align="center" valign="middle">&nbsp;</td>
						<td width="20%" height="50" align="left" valign="middle">投注用户：
							<span class="smallInput">
								 <span><input type="text" id="userName" style="width: 100px" class="_param"/></span>
							</span>
							<label style="margin-left: 10px;">
								<input id="checkLower" name="checkLower" type="checkbox" value="true" class="_param"/>包含下级
							</label>
						</td>
						<td width="10%" height="50" align="left" valign="middle">总合：
							<span class="color_red _amount" id="totalAmount">0.00</span>
						</td>
						<td width="11%" height="50" align="left" valign="middle"><a
							href="javascript:;" class="miniBtn" id="queryOrder"><span
								style="min-width: 80px;">查 询</span></a></td>
						<td width="5%" height="50" align="left" valign="middle"></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th height="39" align="center" valign="middle">时间</th>
							<th height="39" align="center" valign="middle">投注用户</th>
							<th height="39" align="center" valign="middle">用户类型</th>
							<th height="39" align="center" valign="middle">点差</th>
							<th height="39" align="center" valign="middle">收入金额</th>
						</tr>
					</thead>
					<tbody id="orderTbody">
					</tbody>
					<!-- 隐藏tr对象，方便js进行tr对象克隆。 -->
					<tr class="_hidTr" style="display:none;">
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle">
							<a href="javascript:;" class="color_blue underline _user"></a>
						</td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
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