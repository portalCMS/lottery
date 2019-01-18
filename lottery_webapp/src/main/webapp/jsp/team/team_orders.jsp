<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>圈子管理-订单查询</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/team/orders.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/team_menu.jsp"%>
		</div>
		<input type="hidden" id="token" name="token" value="${token}">
		<div class="pageFr fr">
			<div class="iTab" id="userTab">
				<a href="javascript:;" class="current" name="lotOdt"
				<c:if test="${customerkey.customerLevel == 0 }">style="display: none;"</c:if>>彩票账变</a>
				<a href="javascript:;" name="finOdt">充提账变</a> 
				<a href="javascript:;" name="userOdt">团队内部账变</a> 
				<a href="javascript:;" name="proOdt">活动账变</a> 
			</div>
			<div class="pageFrBox_1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
				<tr>
					<td width="12%" height="50" align="left" valign="middle" class="_selectTime">&nbsp;&nbsp;设定时间：
						<select id="setTime" name="select" class="select2 _param" style="width: 110px;">
							<option value="0">今日记录</option>
							<option value="1">历史记录</option>
						</select>
					</td>
					<td width="40%" height="50" align="left" valign="middle" class="_setTime" colspan="3" style="display: none;">
						<span class="_selectText" style="display:none;">&nbsp;&nbsp;设定时间：</span>
						<span class="smallInput">
							<span><input type="text" id="sdate" readonly="readonly" style="width: 140px" /></span>
						</span>
						<span style="margin-left: 10px;margin-right: 10px">至</span>
						<span class="smallInput">
							<span><input type="text" id="edate" readonly="readonly" style="width: 140px" /></span>
						</span>
					</td>
					<td width="15%" height="50" align="left" valign="middle" class="_queryOrder2" style="display:none;">
							<a href="javascript:;" class="miniBtn" id="queryOrder2"><span style="min-width: 80px;">查 询</span></a>
					</td>
				</tr>
				<tr>
					<td width="12%" height="50" align="left" valign="middle" class="_lottType">&nbsp;&nbsp;选择彩种：
						<select id="lotteryType" name="select" class="select2 _czList" style="width: 110px;">
							<option value="">全部彩种</option>
							<c:forEach items="${lotteryTypes}" var="type">
								<option value="${type.lotteryCode}">${type.lotteryName}</option>
							</c:forEach>
						</select>
					</td>
					<td width="20%" height="50" align="left" valign="middle" class="_orderSelectType" colspan="2"><span>类型：</span>
						<select id="orderType"  class="select2 _param" name="select" style="width: 100px;">
							<option value="0">全部分类</option>
							<option value="14001">收入</option>
							<option value="14002">支出</option>
						</select>
						<select id="lotOdt" name="select" class="select2 _odtList _param" style="width: 120px;"></select>
						<select id="finOdt" name="select" class="select2 _odtList _param" style="width: 140px; display: none;"></select>
					 	<select id="userOdt" name="select" class="select2 _odtList _param" style="width: 140px; display: none;"></select>
						<select id="proOdt" name="select" class="select2 _odtList _param" style="width: 140px; display: none;"></select> 
					</td>
				</tr>
				<tr>
					<td width="12%" height="50" align="left" valign="middle" class="_userName"><span style="margin-left: 25px;">&nbsp;</span>用户：
						<span class="smallInput">
							 <span><input type="text" id="userName" style="width: 100px" class="_param"/></span>
						</span>
					</td>
					<td width="5%" height="50" align="left" valign="middle" class="_checkLower">
						<label>
							<input id="checkLower" name="checkLower" type="checkbox" value="true" class="_param"/>包含下级
						</label>
					</td>
					<td width="10%" height="50" align="left" valign="middle" class="_orderStatus">状态：
						<select id="orderStatus" class="select2" style="width: 100px;">
							<option value="0">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
							<option value="17004">过期</option>
							<option value="17005">撤销</option>
						</select>
					</td>
					<td width="15%" height="50" align="left" valign="middle" id="tip">
						<span class="color_red" >&nbsp;&nbsp;只可查询成功状态订单记录</span>
					</td>
					<td width="15%" height="50" align="left" valign="middle" class="_queryOrder1">
						<a href="javascript:;" class="miniBtn" id="queryOrder1"><span style="min-width: 80px;">查 询</span></a>
					</td>
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
							<th height="39" align="center" valign="middle">用户</th>
							<th height="39" align="center" valign="middle"  class="_orderNo">帐变编号</th>
							<th height="39" align="center" valign="middle">时间</th>
							<th height="39" align="center" valign="middle" class="_orderType">分类</th>
							<th height="39" align="center" valign="middle">明细类型</th>
							<th height="39" align="center" valign="middle" class="_lotteryType">彩种</th>
							<!-- <th height="39" align="center" valign="middle" class="_playModel">玩法</th> -->
							<th height="39" align="center" valign="middle" class="_issueNO">期号</th>
							<th height="39" align="center" valign="middle">变动金额</th>
							<th height="39" align="center" valign="middle">账户余额</th>
							<th height="39" align="center" valign="middle" class="_orderStatuss">状态</th>
							<th height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody class="lotOdt _tbody"></tbody>
					<tbody class="finOdt _tbody" style="display: none;"></tbody>
					<tbody class="userOdt _tbody" style="display: none;"></tbody>
					<tbody class="proOdt _tbody" style="display: none;"></tbody>
					<!-- 隐藏tr对象，方便js进行tr对象克隆。 -->
					<tr class="hidTr" style="display:none;">
						<td height="39" align="center" valign="middle">用户</td>
						<td height="39" align="center" valign="middle" class="_orderNo">帐变编号</td>
						<td height="39" align="center" valign="middle">时间</td>
						<td height="39" align="center" valign="middle"  class="_orderType">分类</td>
						<td height="39" align="center" valign="middle">明细类型</td>
							<input class="_odt" type="hidden" value=""/>
						<td height="39" align="center" valign="middle" class="_lotteryType">彩种</td>
						<!-- <td height="39" align="center" valign="middle">玩法</td> -->
						<td height="39" align="center" valign="middle" class="_issueNO">期号</td>
						<td height="39" width="10%" align="center" valign="middle" class="color_red _amount">
							<span class="correctHint">变动金额</span>
						</td>
						<td height="39" width="10%" align="center" valign="middle" class="color_red _amount"><span class="correctHint">账户余额</span></td>
						<td height="39" width="6%" align="center" valign="middle" class="_orderStatuss">状态</td>
						<td height="39" width="8%" align="center" valign="middle">
							<a href="javascript:;" style="display:none;" class="color_blue underline _detailInfo">详情</a>
							<input type="hidden" value=""/>
						</td>
					</tr>
				</table>
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
					
					<option value="18013" name="_userOdt" alt="资金转出" class="_userOdt 14002" style="display:none;"></option>
					<option value="18014" name="_userOdt" alt="资金转入" class="_userOdt 14001" style="display:none;"></option>
					<option value="18016" name="_userOdt" alt="系统分红" class="_userOdt 14001" style="display:none;"></option>
					
					<option value="18012" name="_proOdt" alt="活动派发" class="_proOdt 14001" style="display:none;"></option> 
				</div>		
				<!-- 分页-->
				<xb:foot ajax="true"></xb:foot>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>