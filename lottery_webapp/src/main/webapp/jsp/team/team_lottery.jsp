<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>圈子管理-投注记录</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<script src="${contextPath }/jsp/js/team/team_lottery.js"></script>
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
						<td height="50" align="left" valign="middle" colspan="4" class="_setTime">&nbsp;设定时间：
							<span class="smallInput">
								<span><input type="text" id="startTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<span style="margin-left: 10px;margin-right: 10px">至</span>
							<span class="smallInput">
								<span><input type="text" id="endTime" class="_params" readonly="readonly" style="width: 140px" /></span>
							</span>
							<label>
								&nbsp;<input type="checkbox" value="true" class="_params" id="betType">&nbsp;追号注单（勾选将只查追号的注单）
							</label>
						</td>
					</tr>
					<tr>
						<td width="10%" height="50" align="left" valign="middle"><span style="margin-left: 25px;">&nbsp;</span>彩种：
							<select name="select" class="select2 _initPlayMode _params" style="width: 110px;" id="lotteryCode">
								<option value="">全部彩种</option>
								<c:forEach items="${types }" var="type">
									<option value="${type.lotteryCode }">${type.lotteryName}</option>
								</c:forEach>
						</select>
						</td>
						<td width="8%" height="50" align="left" valign="middle">玩法：
							<select name="select" class="select2 _playmodel _params" style="width: 100px;" id="playCode">
								<option value="">全部玩法</option>
							</select>
						</td>
						<td width="4%" height="50" align="left" valign="middle">彩种奖期：
							<select class="select2  _issueNo _params" id="issueNo" style="width: 110px;">
								<option value="">全部奖期</option>
							</select>
						</td>
						<td width="8%" height="50" align="left" valign="middle">状态：
							<select class="select2 _params" style="width: 100px;" id="betStatus">
								<option value="0">全部状态</option>
								<option value="21001">投注成功</option>
								<option value="21002">已中奖</option>
								<option value="21003">未中奖</option>
								<option value="21004">已撤单</option>
							</select>
						</td>
						</td>
					<%-- 	<td width="12%" height="50" align="left" valign="middle">投注总额：<span
							class="color_red">
								<fmt:formatNumber value="${amount }"  pattern="#,#00.00" />
							</span>元
						</td> --%>
					</tr>
					<tr>
						<td width="8%" height="50" align="left" valign="middle"><span style="margin-left: 25px;">&nbsp;</span>用户：
							<span class="smallInput"> <span> <input type="text"
									style="width: 80px" class="_params" id="uName"/>
							</span>
						</span>
						</td>
						<td width="2%" height="50" align="left" valign="middle">
							<label>
								<input type="checkbox" class="_params" value="true" id="checkLower"/> 包含下级
							</label>
						</td>
						<td width="12%" height="50" align="left" valign="middle">
							<a href="javascript:;" class="miniBtn _secBtn"><span style="min-width: 80px;">查 询</span></a>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th width="17%" height="39" align="center" valign="middle">投注时间</th>
							<th width="12%" height="39" align="center" valign="middle">彩种</th>
							<th width="15%" height="39" align="center" valign="middle">玩法</th>
							<th width="12%" height="39" align="center" valign="middle">投注内容</th>
							<th width="5%" height="39" align="center" valign="middle">模式</th>
							<th width="6%" height="39" align="center" valign="middle">倍数</th>
							<th width="8%" height="39" align="center" valign="middle">投注金额</th>
							<th width="11%" height="39" align="center" valign="middle">开奖号码</th>
							<th width="7%" height="39" align="center" valign="middle">状态</th>
							<th width="8%" height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody id="brdata">
						
					</tbody>
					<tr class="_trclone" style="display:none;" >
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