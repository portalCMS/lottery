<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/my97datejs/WdatePicker.js"></script>
<link href="${contextPath }/jsp/js/my97datejs/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script src="${contextPath }/jsp/js/activity/activity_list.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				活动列表查询
			</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">起始时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _strTime _param" readonly="readonly" id="starttime"/>
						</div>
						<label class="control-label col-sm-1">截止时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _endTime _param" readonly="readonly" id="endtime"/>
						</div>
						<label class="control-label col-sm-1">活动标题</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _param" id="titile" />
						</div>
						<label class="control-label col-sm-1">参与用户</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _param" id="customerName" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">活动类型</label>
						<div class="col-sm-2">
							<select class="form-control _param" id="type">
								<option value="">全部类型</option>
								<option value="REG">注册活动</option>
								<option value="FRC">首充活动</option>
								<option value="TRC">累充奖励活动</option>
								<option value="BET">投注奖励活动</option>
								<option value="AWA">中奖奖励活动</option>
								<option value="">救援活动</option>
								<option value="">沉寂活动</option>
							</select>
						</div>
						<div class="col-sm-2">
							<button class="btn btn-primary btn-query _qbtn">立即查询</button>
						</div>
						</div>
					</div>
				</div>
			</div>
			<table class="table table-condensed table-bordered table-hover">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>活动模式</td>
					<td>活动类型</td>
					<td>活动开始时间</td>
					<td>活动结束时间</td>
					<td>活动名称</td>
					<td>活动领取人数</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody class="orderBody" id="_tbody">
				
			</tbody>
			<tr style="display: none;" id="_tr">
					<td>1</td>
					<td>手动</td>
					<td>注册活动</td>
					<td>2014-7-21 19:44:14</td>
					<td>2014-7-21 19:44:14</td>
					<td>注册就送</td>
					<td>100</td>
					<td align="center"></td>
			</tr>
		</table>
		<ul class="pager" id="pageInfo">
		</ul>
		</div>

</body>
</html>