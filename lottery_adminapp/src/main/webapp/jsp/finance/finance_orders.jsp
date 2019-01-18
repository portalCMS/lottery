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
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/finance/finance_orders.js"></script>
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
				<a href="${contextPath }/initFinanceConfig.do" class="btn btn-warning">配置管理</a>
			</div>
		</div>
		<ul class="nav nav-tabs" id="typeUl">
			<li class="active">
			<input type="hidden" value="drawing" class="_typeVal">
			<a href="#drawingDiv" data-toggle="tab">提款记录&nbsp;&nbsp;<label
					class="label label-danger _drawingCount" id="drawingCount" >0</label></a></li>
			<li>
			<input type="hidden" value="recharge" class="_typeVal">
			<a href="#rechargeDiv" data-toggle="tab">充值记录&nbsp;&nbsp;<label
					class="label label-danger _rechargeCount" id="rechargeCount" >0</label></a></li>
			<li>
			<input type="hidden" value="activity" class="_typeVal">
			<a href="#activityDiv" data-toggle="tab">活动记录&nbsp;&nbsp;<label
					class="label label-danger _activityCount" id="activityCount" >0</label></a></li>
		</ul>
		<br />
		<div class="tab-content">
			<div class="tab-pane " id="rechargeDiv">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control _startTime"  />
					</div>
					至
					<div class="form-group">
						<input type="text" class="form-control _endTime" />
					</div>
					<div class="form-group">
						<select class="form-control _orderStatus" >
							<option value="">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
							<option value="17004">过期</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control _userName" placeholder="用户名" />
					</div>
					<div class="form-group">
						<a href="javascript:;" class="btn btn-primary _queryOrder">查询</a>
					</div>
				</div>
				<br />
				<table
					class="table table-bordered table-condensed table-hover table-striped ">
					<thead>
						<tr class="success text-center">
							<td>序号</td>
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
					<tbody id="rechargeTb">
					</tbody>
					<tr class="_hidRcTr" style="display:none;">
						<td class="_index text-center" style="vertical-align: middle;">#</td>
						<td class="_time text-center" style="vertical-align: middle;">时间</td>
						<td class="_bankName text-center" style="vertical-align: middle;">银行名称</td>
						<td class="_openName text-center" style="vertical-align: middle;">开户名</td>
						<td class="_carNo text-center" style="vertical-align: middle;">卡号</td>
						<td class="_rechargAmount text-center" style="vertical-align: middle;">充值金额</td>
						<td class="_ps text-center" style="vertical-align: middle;">附言</td>
						<td class="_rcUser text-center" style="vertical-align: middle;">充值用户</td>
						<td class="_status text-center" style="vertical-align: middle;">状态</td>
						<td class="text-center">
							<a href="javascript:;" class='btn btn-link btn-sm _approve'>通过审核</a>
							<input type="hidden" class="_ordId">
							<a href="javascript:;" class='btn btn-link btn-sm _reject'>驳回审核</a>
							<a href="javascript:;" class='btn btn-link btn-sm _query'>查看详情</a>
						</td>
					</tr>
				</table>
				<ul class="pager"  id="rechargePage">
					<li><a href='javaScript:;' class="_prev">上一页</a></li>
					<span class="pageNum">0</span>/<span class="totalPage">0</span>
					<li><a href='javaScript:;' class="_next">下一页</a></li>
				</ul>
			</div>
			<div class="tab-pane active" id="drawingDiv">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control _startTime"  />
					</div>
					至
					<div class="form-group">
						<input type="text" class="form-control _endTime" />
					</div>
					<div class="form-group">
						<select class="form-control _orderStatus" >
							<option value="">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control _userName" placeholder="用户名"/>
					</div>
					<div class="form-group">
						<a href="javascript:;" class="btn btn-primary _queryOrder">查询</a>
					</div>
				</div>
				<br />
				<table
					class="table table-bordered table-condensed table-hover table-striped ">
					<thead>
						<tr class="success text-center">
							<td>序号</td>
							<td>申请时间</td>
							<td>提款金额</td>
							<td>提款用户</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="drawingTb">
					</tbody>
					<tr class="_hidDwTr" style="display:none;">
						<td class="_index text-center" style="vertical-align: middle;">#</td>
						<td class="_time text-center" style="vertical-align: middle;">时间</td>
						<td class="_drawingAmount text-center" style="vertical-align: middle;">充值金额</td>
						<td class="_dwUser text-center" style="vertical-align: middle;">充值用户</td>
						<td class="_status text-center" style="vertical-align: middle;">状态</td>
						<td class="text-center" style="vertical-align: middle;">
							<input type="hidden" class="_ordId">
							<a href="javascript:;" class='btn btn-link btn-sm _query'>查看详情</a>
						</td>
					</tr>
				</table>
				<ul class="pager" id="drawingPage">
					<li><a href='javaScript:;' class="_prev">上一页</a></li>
					<span class="pageNum">0</span>/<span class="totalPage">0</span>
					<li><a href='javaScript:;' class="_next">下一页</a></li>
				</ul>
			</div>
			
			<div class="tab-pane" id="activityDiv">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control _startTime"  />
					</div>
					至
					<div class="form-group">
						<input type="text" class="form-control _endTime" />
					</div>
					<div class="form-group">
						<select class="form-control _orderStatus" >
							<option value="">全部状态</option>
							<option value="17001">处理中</option>
							<option value="17002">成功</option>
							<option value="17003">失败</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control _userName" placeholder="用户名"/>
					</div>
					<div class="form-group">
						<a href="javascript:;" class="btn btn-primary _queryOrder">查询</a>
					</div>
				</div>
				<br />
				<table
					class="table table-bordered table-condensed table-hover table-striped ">
					<thead>
						<tr class="success text-center">
							<td>序号</td>
							<td>申请时间</td>
							<td>活动类型</td>
							<td>活动标题</td>
							<td>领奖金额</td>
							<td>领奖用户</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="activityTb">
					</tbody>
					<tr class="_hidAcTr" style="display:none;">
						<td class="_index text-center" style="vertical-align: middle;"></td>
						<td class="_time text-center" style="vertical-align: middle;"></td>
						<td class="_type text-center" style="vertical-align: middle;"></td>
						<td class="_title text-center" style="vertical-align: middle;"></td>
						<td class="_awardAmount text-center" style="vertical-align: middle;"></td>
						<td class="_awaUser text-center" style="vertical-align: middle;"></td>
						<td class="_status text-center" style="vertical-align: middle;"></td>
						<td class="text-center" style="vertical-align: middle;">
							<input type="hidden" class="_ordId">
							<a href="javascript:;" class='btn btn-link btn-sm _actApprove'>通过审核</a>
							<a href="javascript:;" class='btn btn-link btn-sm _actReject'>驳回申请</a>
							<a href="javascript:;" class='btn btn-link btn-sm _actQuery'>查看详情</a>
							<input type="hidden" class="_actId">
						</td>
					</tr>
				</table>
				<ul class="pager" id="activityPage">
					<li><a href='javaScript:;' class="_prev">上一页</a></li>
					<span class="pageNum">0</span>/<span class="totalPage">0</span>
					<li><a href='javaScript:;' class="_next">下一页</a></li>
				</ul>
			</div>
		</div>
	</div>
	<input type="hidden" id="content"/>
	<div class="modal fade" id="activity_detail_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">活动详情</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<div class= "col-sm-11">
								<script id="editor" type="text/javascript" style="width:100%;height:100px;"></script>
								<br />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="reject_activity_model">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">x</span>
					</button>
					<h4 class="modal-title">驳回活动奖励申请</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="col-sm-10">
								<input type="text" id="rjaReason" class="form-control" placeholder="驳回原因"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default " data-dismiss="modal" id="rejectCancel">
						取消</button>
					<button type="button" class="btn btn-primary" id="rejectConfrim">确定</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
