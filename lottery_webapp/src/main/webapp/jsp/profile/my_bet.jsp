<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-投注记录</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<script src="${contextPath }/jsp/js/profile/my_bet.js"></script>
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
						<td width="1%" height="50" align="center" valign="middle">&nbsp;</td>
						<td height="50" align="left" valign="middle" colspan="4" class="_setTime">设定时间：
							<span class="smallInput">
								<span><input type="text" id="startTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<span style="margin-left: 10px;margin-right: 10px">至</span>
							<span class="smallInput">
								<span><input type="text" id="endTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<label>
								&nbsp;<input type="checkbox" class="_params" id="betType">&nbsp;追号注单（勾选将只查追号的注单）
							</label>
						</td>
					</tr>
					<tr>
						<td width="1%" height="50" align="center" valign="middle">&nbsp;</td>
						<td width="22%" height="50" align="left" valign="middle">选择彩种：
							<select name="select" class="select2 _params _lotteryCode " id="lotteryCode" style="width: 110px;">
								<option value="">全部彩种</option>
								<c:forEach items="${types }" var="type">
									<option value="${type.lotteryCode }">${type.lotteryName }</option>
								</c:forEach>
							</select>
						</td>
						<td width="20%" height="50" align="left" valign="middle">玩法：
							<select class="select2 _params _playmodel " id="playCode" style="width: 100px;">
								<option value="">全部玩法</option>
							</select>
						</td>
						<td width="20%" height="50" align="left" valign="middle">状态：
							<select class="select2 _params" style="width: 100px;" id="betStatus">
								<option value="0">全部状态</option>
								<option value="21001">投注成功</option>
								<option value="21002">已中奖</option>
								<option value="21003">未中奖</option>
								<option value="21004">已撤单</option>
							</select>
						</td>
						<td width="30%" height="50" align="left" valign="middle">
							<a href="javascript:void(0);" class="miniBtn _secBtn"><span style="min-width: 80px;">查 询</span></a>
						</td>
					</tr>
					<p class="ml15 mt10 color_red">追号订单的投注订单，只显示小于或等于当前奖期的注单!</p>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th width="17%" height="39" align="center" valign="middle">投注时间</th>
							<th width="10%" height="39" align="center" valign="middle">彩种</th>
							<th width="15%" height="39" align="center" valign="middle">玩法</th>
							<th width="15%" height="39" align="center" valign="middle">投注内容</th>
							<th width="5%" height="39" align="center" valign="middle">模式</th>
							<th width="5%" height="39" align="center" valign="middle">倍数</th>
							<th width="8%" height="39" align="center" valign="middle">投注金额</th>
							<th width="11%" height="39" align="center" valign="middle">开奖号码</th>
							<th width="7%" height="39" align="center" valign="middle">状态</th>
							<th width="8%" height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody id="brdata"></tbody>
					<tr  class="_trclone" style="display:none;">
							<td height="39" align="center" valign="middle">2014-6-5 02:21:11</td>
							<td height="39" align="center" valign="middle">重庆时时彩</td>
							<td height="39" align="center" valign="middle">一星</td>
							<td height="39" align="center" valign="middle">投注内容</td>
							<td height="39" align="center" valign="middle">模式</td>
							<td height="39" align="center" valign="middle">倍数</td>
							<td height="39" align="center" valign="middle"><spanclass="">1000.00</span></td>
							<td height="39" align="center" valign="middle" class="color_red">开奖号码</td>
							<td height="39" align="center" valign="middle"><span class="correctHint">中奖</span><p class="color_red"></p></td>
							<td height="39" align="center" valign="middle">
								<a href="javascript:void(0);" class="color_blue underline _detailInfo">查看详情</a>
								<input type="hidden" value=""/>
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