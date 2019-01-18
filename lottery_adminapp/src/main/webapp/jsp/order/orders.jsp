<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/order/orders.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				订单总数：<label class="label label-danger">${orderCount }</label>
			</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">起始时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control recharge_time" id="orderTimeBegin"/>
						</div>
						<label class="control-label col-sm-1">截止时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control recharge_time" id="orderTimeEnd"/>
						</div>
						<label class="control-label col-sm-1">用户名</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="customerName" value="${param.customerName }"/>
						</div>
						<label class="control-label col-sm-1">订单编号</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="orderNumber"/>
						</div>

					</div>
					<div class="form-group clear_margin_bottom">
						<label class="control-label col-sm-1">订单大类</label>
						<div class="col-sm-2">
							<select class="form-control" id="orderBigTypes">
								<option value="0">全部类型</option>
								<option value="1">彩票订单</option>
								<option value="2">财务订单</option>
								<option value="3">用户订单</option>
								<option value="4">活动订单</option>
							</select>
						</div>
						<label class="control-label col-sm-1">订单类型</label>
						<div class="col-sm-2">
							<select class="form-control" multiple="multiple" id="orderDetailTypes" >
								<option value="0" selected="selected">全部类型</option>
								<c:forEach items="${orderDetailTypes }" var="status">
									<option value="${status.sid }">${status.sname }</option>
								</c:forEach>
							</select>
						</div>
						<label class="control-label col-sm-1">订单状态</label>
						<div class="col-sm-2">
							<select class="form-control" id="orderStatus">
								<option value="0">全部状态</option>
								<c:forEach items="${orderStatus }" var="status">
									<option value="${status.sid }" <c:if test="${17002==status.sid }">selected</c:if>>${status.sname }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">用户类型</label>
						<div class="col-sm-2">
							<select class="form-control" id="customerType">
								<option value="1">非测试用户</option>
								<option value="2">测试用户</option>
								<option value="0">全部用户</option>
							</select>
						</div>
						<div class="col-sm-2  col-sm-offset-1" style="margin-top: 5px">
							<button class="btn btn-primary btn-query">查询订单</button>
						</div>
					</div>
				</div>
			</div>
			<ul>
				<label class="control-label col-sm-1">收入：</label>
				<div class="col-sm-1 _revenue _fcash" style="color:green;"></div>
				<label class="control-label col-sm-1">支出：</label>
				<div class="col-sm-1 _expenses _fcash" style="color:red;"></div>
				<label class="control-label col-sm-1">总计：</label>
				<div class="col-sm-1 _sumcount _fcash"  style="color:red;">></div>
			</ul>
			<table class="table table-condensed table-bordered table-hover">
			<thead>
				<tr class="success text-center">
					<td>订单编号</td>
					<td>订单类型</td>
					<td>时间</td>
					<td>用户</td>
					<td>类别</td>
					<td>金额</td>
					<td>状态</td>
					<td>用户余额</td>
					<td>订单备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody class="orderBody">
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td><a href="#"></a></td>
					<td><span class="text-success"></span></td>
					<td><span class="text-danger"></span></td>
					<td><label class="label label-success"></label></td>
					<td><span class="text-danger"></span></td>
					<td><a href="details/cz_detail.jsp"></a></td>
				</tr>
				
			</tbody>
		</table>
		</div>
		<ul class="pager">
			<li><a href="#">上一页</a></li>
			<li><a href="#">下一页</a></li>
		</ul>
</body>
</html>