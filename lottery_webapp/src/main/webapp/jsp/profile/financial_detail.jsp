<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人中心-财务明细</title> <%@ include file="../include/css_asset.jsp"%>
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${contextPath }/jsp/js/profile/financeDetail.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<p class="boxBotBg"></p>
			<div class="pageFrBox_1">
				<div class="iTab" id="userTab">
					<a href="javascript:;" class="current" name="lotOdt" index="1"
					<c:if test="${customerkey.customerLevel == 0 }">style="display: none;"</c:if>>彩票账变</a>
					<a href="javascript:;" name="finOdt" index="2">充提账变</a> 
					<a href="javascript:;" name="proOdt" index="3">活动账变</a> 
				</div>
				<div class="userTabBox cp">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="25%" height="50" align="center" valign="middle" class="_selectTime">设定时间：
								<select id="timeList" name="select" class="ml10 select2" style="width: 100px;">
									<option value="0">今日记录</option>
									<option value="1">历史记录</option>
								</select>
							</td>
							<td width="25%" height="50" align="left" valign="middle" colspan="4" class="_setTime" style="display: none;">
								<span class="_selectText" style="display:none;margin-left: 12px;margin-right:10px;">设定时间：</span>
								<span class="smallInput">
									<span><input type="text" id="sdate" readonly="readonly" style="width: 140px" /></span>
								</span>
								<span style="margin-left: 10px;margin-right: 10px">至</span>
								<span class="smallInput">
									<span><input type="text" id="edate" readonly="readonly" style="width: 140px" /></span>
								</span>
							</td>
						</tr>
						<tr>
							<td width="25%" height="50" align="center" valign="middle" class="_lottType">&nbsp;&nbsp;选择彩种：
								<select id="lotteryType" name="select" class="ml10 select2 _czList" style="width: 110px;">
									<option value="">全部彩种</option>
									<c:forEach items="${lotteryTypes}" var="type">
										<option value="${type.lotteryCode}">${type.lotteryName}</option>
									</c:forEach>
								</select>
							</td>
							<td width="25%" height="50" align="center" valign="middle">订单类型：
								<select id="lotdt" name="select" class="ml10 select2 _dtList" style="width: 100px;">
									<option value="0">全部类型</option>
									<option value="14001">收入</option>
									<option value="14002">支出</option>
								</select>
							</td>
							<td width="20%" height="50" align="left" valign="middle">
								<select id="lotOdt" name="select" class="select2 _odtList" style="width: 120px;"></select>
								<select id="finOdt" name="select" class="select2 _odtList" style="width: 140px; display: none;"></select>
							 	<!-- <select id="userOdt" name="select" class="select2 _odtList" style="width: 140px; display: none;"></select> -->
								<select id="proOdt" name="select" class="select2 _odtList" style="width: 140px; display: none;"></select> 
							</td>
							<td width="20%" height="50" align="left" valign="middle">状态：
								<select id="orderStatus" class="select2" style="width: 100px;">
									<option value="0">全部状态</option>
									<option value="17001">处理中</option>
									<option value="17002">成功</option>
									<option value="17003">失败</option>
									<option value="17004">过期</option>
									<option value="17005">撤销</option>
								</select>
							</td>
							<td width="15%" height="50" align="left" valign="middle">
								<a id="queryOrder" href="javascript:;" class="miniBtn">
									<span style="min-width: 50px;">查 询</span></a>
							</td>
							<div id="hidOpt" style="display:none;">
								<option value="0" class="_alldt"  style="display:none;">全部类型</option>
								<option value="18001" name="_lotOdt" alt="普通投注扣款" class="_lotOdt 14002" style="display:none;"></option>
								<option value="18017" name="_lotOdt" alt="追号投注扣款" class="_lotOdt 14002" style="display:none;"></option>
								<option value="18003" name="_lotOdt" alt="中奖返款"  class="_lotOdt 14001" style="display:none;"></option>
								<option value="18004" name="_lotOdt" alt="追号撤单返款"  class="_lotOdt 14001" style="display:none;"></option>
								<option value="18005" name="_lotOdt" alt="投注返款"  class="_lotOdt 14001" style="display:none;"></option>
								<option value="18006" name="_lotOdt" alt="系统撤单返款"  class="_lotOdt 14001" style="display:none;"></option>
								<option value="18018" name="_lotOdt" alt="投注撤单返款"  class="_lotOdt 14001" style="display:none;"></option>
								
								<option value="18007" name="_finOdt" alt="现金提款" class="_finOdt 14002" style="display:none;"></option>
								<option value="18008" name="_finOdt" alt="后台扣款" class="_finOdt 14002" style="display:none;"></option>
								<option value="18009" name="_finOdt" alt="现金充值" class="_finOdt 14001" style="display:none;"></option>
								<option value="18010" name="_finOdt" alt="手续费返款" class="_finOdt 14001" style="display:none;"></option>
								<option value="18011" name="_finOdt" alt="后台充值" class="_finOdt 14001" style="display:none;"></option>
								<option value="18019" name="_finOdt" alt="第三方充值" class="_finOdt 14001" style="display:none;"></option>
																
								<option value="18012" name="_proOdt" alt="活动派发" class="_proOdt 14001" style="display:none;"></option> 
							</div>		
						</tr>
					</table>
					<div class="mb10">
						<span class="ml20">收入：<span class="color_red mr20 _amount" id="inAmount">0.00</span>
						<span class="">&nbsp;支出：<span class="color_red mr20 _amount" id="outAmount">0.00</span>
						<span class="">&nbsp;总计：<span class="color_red mr20 _amount" id="profitAmount">0.00</span>
					</div>
					<p class="line5"></p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
						<thead>
							<tr>
								<th height="39" align="center" valign="middle">时间</th>
								<th height="39" align="center" valign="middle" class="_orderNo">订单号</th>
								<th height="39" align="center" valign="middle">明细类型</th>
								<th height="39" align="center" valign="middle" class="_lotteryType">彩种</th>
								<th height="39" align="center" valign="middle" class="_issueNO">期号</th>
								<th height="39" align="center" valign="middle">类型</th>
								<th height="39" align="center" valign="middle">变动金额</th>
								<th height="39" align="center" valign="middle">账户余额</th>
								<th height="39" align="center" valign="middle">状态</th>
								<th height="39" align="center" valign="middle">操作</th>
							</tr>
						</thead>
						<tbody class="lotOdt _tbody"></tbody>
						<tbody class="finOdt _tbody" style="display: none;"></tbody>
						<tbody class="proOdt _tbody" style="display: none;"></tbody>

						<!-- 隐藏tr对象，方便js进行tr对象克隆。 -->
						<tr class="hidTr" style="display: none;">
							<td height="39" width="18%" align="center" valign="middle">时间</td>
							<td height="39" width="18%" align="center" valign="middle" class="_orderNo">订单号</td>
							<td height="39" width="12%" align="center" valign="middle">明细类型</td>
							<input type="hidden" value="" class="_odt" />
							<td height="39" width="12%" align="center" valign="middle" class="_lotteryType">彩种</td>
							<td height="39" width="20%" align="center" valign="middle" class="_issueNO">期号</td>
							<td height="39" width="5%" align="center" valign="middle">类型</td>
							<td height="39" width="12%" align="center" valign="middle" class="color_red _amount">
								<span class="correctHint">变动余额</span>
							</td>
							<td height="39" width="10%" align="center" valign="middle" class="color_red _amount">
								<span class="correctHint">账户余额</span>
							</td>
							<td height="39" width="5%" align="center" valign="middle">状态</td>
							<td height="39" width="12%" align="center" valign="middle">
								<a href="javascript:;" style="display: none;" class="color_blue underline _detailInfo">详情</a> 
								<input type="hidden" value="" />
							</td>
						</tr>
					</table>
						<xb:foot ajax="true"></xb:foot>
			</div>
			<p class="boxBotBg"></p>
			</div>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
	<script>
		$(document).ready(function() {
			$("#iceTip").sTip({
				display : false,
				offsetTop : 0,
				offsetLeft : 15,
				tipInfo : "用于处理具有中间状态的订单金额,例如您申请一笔提款金额，该订单的金额会被冻结，直至工作人员处理后，订单成功则扣除冻结金额，失败则解冻返款."
			});
		});
	</script>
</body>
</html>