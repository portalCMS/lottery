<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>投注记录</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/records/records.js" />
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<!-- 
		<div class="panel panel-default">
			<div class="panel-heading">
				方案总数：<label class="label label-danger">1000</label>
			</div>
			<div class="panel-body"></div>
		</div>
		 -->
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">起始时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _startTime _ajaxParam" id="startTime"/>
						</div>
						<label class="control-label col-sm-1">截止时间</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _endTime _ajaxParam" id="endTime"/>
						</div>
						<label class="control-label col-sm-1">用户</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _ajaxParam" id="uName" value="${param.uName }"/>
						</div>
						<div class="col-sm-2 col-sm-offset-1">
							<a href="javascript:void(0);" class="btn btn-primary _secBtn">立即查询</a>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 0px;">
						<label class="control-label col-sm-1">彩种组</label>
						<div class="col-sm-2">
							<select class="form-control _groups _ajaxParam" id="groupCode">
								<option value="">所有彩种组</option>
								<c:forEach var="group" items="${groups }">
									<option value="${group.lotteryGroupCode }">${group.lotteryGroupName }</option>
								</c:forEach>
							</select>
						</div>
						<label class="control-label col-sm-1">彩种</label>
						<div class="col-sm-2">
							<select class="form-control _typs _ajaxParam" id="lotteryCode">
								<option value="0">所有彩种</option>
								<c:forEach var="type" items="${types }">
									<option value="${type.lotteryCode }">${type.lotteryName }</option>
								</c:forEach>
							</select>
						</div>
						<label class="control-label col-sm-1">玩法</label>
						<div class="col-sm-2">
							<select class="form-control _playModels _ajaxParam" id="playCode">
								<option value="0">所有玩法</option>
								<c:forEach var="playModel" items="${playModels }">
									<option value="${playModel.modelCode }">${playModel.modelName }</option>
								</c:forEach>
							</select>
						</div>
						<label class="control-label col-sm-1">方案状态</label>
						<div class="col-sm-2">
							<select class="form-control _ajaxParam" id="betStatus">
							    <option value="0">所有状态</option>
								<option value="21001">投注成功</option>
								<option value="21002">已中奖</option>
								<option value="21003">未中奖</option>
								<option value="21004">已撤单</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<label class="control-label col-sm-1">用户类型</label>
						<div class="col-sm-2">
							<select class="form-control _ajaxParam" id="custometTypes">
								<option value="1">非测试用户</option>
								<option value="2">测试用户</option>
								<option value="0">全部用户</option>
							</select>
						</div>
						
						<label class="control-label col-sm-1">期号</label>
						<div class="col-sm-2">
							<input type="text" class="form-control _ajaxParam" id="issueNo" />
						</div>
						<div class="col-sm-2 col-sm-offset-4">
							<a href="javascript:void(0);" class="btn btn-danger" id="_cancelBtn">奖期系统撤单</a>
						</div>
					</div>
					<div class="pull-right">
					<span class="text-muted">
						奖期系统撤单功能，是针对某一彩种的某一期，所有的投注订单进行系统撤单还款操作的功能(非撤销中奖订单) </span>
						</div>
						<div class="clearfix"></div>
				</div>
				
			</div>
		</div>
		<ul>
			 <label class="control-label col-sm-1">投注：</label>
			 <div class="col-sm-1 _bet _fcash" style="color:green;"></div>
			 <label class="control-label col-sm-1">撤单：</label>
			 <div class="col-sm-1 _cancle _fcash" style="color:red;"></div>
			 <label class="control-label col-sm-1">中奖：</label>
			 <div class="col-sm-1 _win _fcash"  style="color:red;"></div>
			 <label class="control-label col-sm-1">返点：</label>
			 <div class="col-sm-2 _rebates _fcash"  style="color:red;"></div>
			 <label class="control-label col-sm-1">总计：</label>
			 <div class="col-sm-1 _sumcount _fcash"  style="color:red;"></div>
		</ul>
		<table class="table table-hover table-condensed table-bordered">
			<thead>
				<tr class="text-center success">
					<td>编号</td>
					<td>投注时间</td>
					<td>彩种</td>
					<td>期号</td>
					<td>玩法</td>
					<td>投注金额</td>
					<td>状态</td>
					<td>用户</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody class="_tbody">
			</tbody>
		</table>
		<ul class="pager _pageInfo">
			<li><a href="#">上一页</a></li>
			<li><a href="#">下一页</a></li>
		</ul>
	</div>

	<div class="modal fade" id="records_detail_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">方案详情</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-2">投注用户</label>
							<div class="form-control-static col-sm-10">
								<a href="javascript:void(0);" class="_uName">张三</a>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">彩种玩法</label>
							<div class="form-control-static col-sm-10 _lotteryTypeAndPlayCode">
								重庆时时彩 <span class="text-success">前一直选</span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">彩种期号</label>
							<div class="form-control-static col-sm-10 _issueNo">101541</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">开奖号码</label>
							<div class="form-control-static col-sm-10 _openNum"></div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">投注金额</label>
							<div class="form-control-static col-sm-10 _betMoney">
								<span class="text-danger">10000.00</span>&nbsp;&nbsp;&nbsp;&nbsp;<label
									class="label label-primary">元模式</label>
								&nbsp;&nbsp;&nbsp;&nbsp;<label class="label label-info">1950</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">投注状态</label>
							<div class="form-control-static col-sm-10 _betStatus">
								<label class="label label-danger">已中奖</label>&nbsp;&nbsp;<span
									class="text-danger">1000000.0000</span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">投注时间</label>
							<div class="form-control-static col-sm-10">
								<span class="text-muted _time">2014-7-21 17:14:37</span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">投注内容</label>
							<div class="form-control-static col-sm-10">
								<div
									style="height: 100px; overflow-y: scroll; word-wrap: break-word; word-spacing: normal;" class="_number">
									123456 123456 789154 91567489 1348613 486156 486196486123456
									123456 789154 91567489 1348613 486156 486196486123456 123456
									789154 91567489 1348613 486156 486196486123456 123456 789154
									91567489 1348613 486156 486196486123456 123456 789154 91567489
									1348613 486156 486196486123456 123456 789154 91567489 1348613
									486156 486196486123456 123456 789154 91567489 1348613 486156
									486196486123456 123456 789154 91567489 1348613 486156
									486196486123456 123456 789154 91567489 1348613 486156
									486196486123456 123456 789154 91567489 1348613 486156
									486196486123456 123456 789154 91567489 1348613 486156
									486196486123456 123456 789154 91567489 1348613 486156 486196486
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-block btn-success"
						data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>