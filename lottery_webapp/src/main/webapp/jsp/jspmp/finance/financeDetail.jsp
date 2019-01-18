<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>账户明细</title>
		<%@ include file="../include/js_asset.jsp"%>
		<%@ include file="../include/css_asset.jsp"%>
		<script type="text/javascript"
			src="${contextPath }/jsp/jspmp/js/finance/financeDetail.js"></script>
</head>
<body>

	<header class="clearfix"> <i class="icon icon-nav"></i>
	<div class="title">
		<span>账户明细</span>
	</div>
	</header>

	<aside></aside>

	<section class="container">
	<div class="col-box">
		<div class="col-box-first">
			<span>账户金额</span> <strong>${cash.cash }</strong>
		</div>
		<div>
			<span>冻结金额</span> <strong><font color="#BEBEBE">${cash.frozenCash }</font></strong>
		</div>
	</div>

	<table class="table">
		<tr>
			<th width="2500">时间</th>
			<th width="5000">订单类型</th>
			<th width="2500">金额</th>
		</tr>
		<tbody class="_tbody">
		</tbody>
	</table>

	<div class="pager _pageInfo" id="pagers"></div>

	<select id="lotOdt" style="display: none;">
		<option value="0" class="_alldt" style="display: none;">全部类型</option>
		<option value="18001" name="_lotOdt" alt="普通投注扣款"
			class="_lotOdt 14002" style="display: none;"></option>
		<option value="18017" name="_lotOdt" alt="追号投注扣款"
			class="_lotOdt 14002" style="display: none;"></option>
		<option value="18003" name="_lotOdt" alt="中奖返款" class="_lotOdt 14001"
			style="display: none;"></option>
		<option value="18004" name="_lotOdt" alt="追号撤单返款"
			class="_lotOdt 14001" style="display: none;"></option>
		<option value="18005" name="_lotOdt" alt="投注返款" class="_lotOdt 14001"
			style="display: none;"></option>
		<option value="18006" name="_lotOdt" alt="系统撤单返款"
			class="_lotOdt 14001" style="display: none;"></option>
		<option value="18018" name="_lotOdt" alt="投注撤单返款"
			class="_lotOdt 14001" style="display: none;"></option>

		<option value="18007" name="_finOdt" alt="现金提款" class="_finOdt 14002"
			style="display: none;"></option>
		<option value="18008" name="_finOdt" alt="后台扣款" class="_finOdt 14002"
			style="display: none;"></option>
		<option value="18009" name="_finOdt" alt="现金充值" class="_finOdt 14001"
			style="display: none;"></option>
		<option value="18010" name="_finOdt" alt="手续费返款" class="_finOdt 14001"
			style="display: none;"></option>
		<option value="18011" name="_finOdt" alt="后台充值" class="_finOdt 14001"
			style="display: none;"></option>
		<option value="18019" name="_finOdt" alt="第三方充值" class="_finOdt 14001"
			style="display: none;"></option>

		<option value="18013" name="_userOdt" alt="资金转出"
			class="_userOdt 14002" style="display: none;"></option>
		<option value="18014" name="_userOdt" alt="资金转入"
			class="_userOdt 14001" style="display: none;"></option>
		<option value="18015" name="_userOdt" alt="投注盈收"
			class="_userOdt 14001" style="display: none;"></option>
		<option value="18016" name="_userOdt" alt="系统分红"
			class="_userOdt 14001" style="display: none;"></option>

		<option value="18012" name="_proOdt" alt="活动派发" class="_proOdt 14001"
			style="display: none;"></option>
	</select> </section>



</body>
</html>