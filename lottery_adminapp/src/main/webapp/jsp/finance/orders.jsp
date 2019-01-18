<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/finance/orders.js"></script>
<script type="text/javascript">
var contextPath = '${contextPath}';
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-body">
				<a href="${contextPath }/showFinanceChare.do" class="btn btn-warning">后台充提</a> 
				<!-- <a href="${contextPath }/showStatistics.do" class="btn btn-warning">盈亏报表</a>  -->
				<a href="${contextPath }/initFinanceConfig.do" class="btn btn-warning">配置管理</a>
			</div>
		</div>
		<ul class="nav nav-tabs">
			<li><a href="#cz" data-toggle="tab">充值记录&nbsp;&nbsp;<label
					class="label label-danger _rechargeCount" id="rechargeCount" >0</label></a></li>
			<li class="active"><a href="#profile" data-toggle="tab">提款记录&nbsp;&nbsp;<label
					class="label label-danger _drawingCount" id="drawingCount" >0</label></a></li>
		</ul>
		<br />
		<div class="tab-content">
			<div class="tab-pane " id="cz">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control recharge_time" id="orderTimeBegin" name="orderTimeBegin"  />
					</div>
					至
					<div class="form-group">
						<input type="text" class="form-control recharge_time" id="orderTimeEnd" name="orderTimeEnd" />
					</div>
					<div class="form-group">
						<select class="form-control" id="orderStatus" name="orderStatus" >
							<option value="">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="用户名" id="customerName" name="customerName" />
					</div>
					<div class="form-group">
						<a href="javascript:orders.queryOrders('pageInfo','queryRechargeOrder.do',1);" class="btn btn-primary">查询</a>
					</div>
				</div>
				<br />
				<table
					class="table table-bordered table-condensed table-hover table-striped ">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>时间</td>
							<td>银行名称</td>
							<td>开户名</td>
							<td>卡号</td>
							<td>充值金额</td>
							<td>附言</td>
							<td>充值用户</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="rechargeOrdersTbody">
					</tbody>
				</table>
				<ul class="pager"  id="pageInfo">
				</ul>
			</div>
			<div class="tab-pane active" id="profile">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control drawing_time" id="orderTimeBegin2" name="orderTimeBegin2"  />
					</div>
					至
					<div class="form-group">
						<input type="text" class="form-control drawing_time" id="orderTimeEnd2" name="orderTimeEnd2" />
					</div>
					<div class="form-group">
						<select class="form-control" id="orderStatus2" name="orderStatus2" >
							<option value="">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="用户名" id="customerName2" name="customerName2"/>
					</div>
					<div class="form-group">
						<a href="javascript:orders.queryOrders('pageInfo2','queryDrawingOrder.do',1);" class="btn btn-primary">查询</a>
					</div>
				</div>
				<br />
				<table
					class="table table-bordered table-condensed table-hover table-striped ">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>申请时间</td>
							<td>提款金额</td>
							<td>提款用户</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="rechargeOrdersTbody2">
						
					</tbody>
				</table>
				<ul class="pager" id="pageInfo2">
				</ul>
			</div>
		</div>
	</div>
	
</body>
</html>
