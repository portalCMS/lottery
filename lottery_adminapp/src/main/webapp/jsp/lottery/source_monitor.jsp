<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>号源监控</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/lottery/monitor.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="showLotteryEngine.do">彩种引擎</a></li>
			<li class="active">号源监控</li>
			<label class="label label-primary _tc pull-right">总记录数:<span></span></label>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">
				奖期监控
				<div class="pull-right ">
					<button class="btn btn-xs btn-primary _refreshPage">刷新页面</button>
				</div>
			</div>
		<div class="panel-body">
		<div class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-1">彩种</label>
				<div class="col-sm-2">
					<select class="form-control" id="lotteryType" >
						<c:forEach items="${lotterys }" var="lot">
							<option value="${lot.lotteryCode }">${lot.lotteryName }</option>
						</c:forEach>
					</select>
				</div>
				<label class="control-label col-sm-1">开奖期号</label>
				<div class="col-sm-2">
					<input type="text" class="form-control"  id="openIssueNo"/>
				</div>
				<div class="col-sm-2">
					<button class="btn btn-primary _btn-query">查询开奖结果</button>
				</div>
			</div>
		</div>
		<table class="table table-hover table-bordered table-condensed ">
			<thead>
				<tr class="text-center success">
					<td>编号</td>
					<td>时间</td>
					<td>彩种名称</td>
					<td>期数</td>
					<td>状态</td>
					<td width="350">操作</td>
				</tr>
			</thead>
			<tbody id="myTbody">
				
			</tbody>
			
			<tr class="_hidTr" style="display:none;">
				<td align="center" valign="middle" style="vertical-align: middle;"></td>
				<td align="center" valign="middle" style="vertical-align: middle;"></td>
				<td align="center" valign="middle" class="_lotteryName" style="vertical-align: middle;"></td>
				<td align="center" valign="middle" class="_issue" style="vertical-align: middle;"></td>
				<td align="center" valign="middle" style="vertical-align: middle;"><label class="label"></label></td>
				<td align="center" valign="middle" style="vertical-align: middle;">
					<div class="form-inline _failedDiv" style="display:none;">
						<div class="form-group">
							<input type="text" class="form-control input-sm" placeholder="号码之间以逗号分隔 ">
						</div>
						<a class="btn btn-primary btn-sm _handAward"> 录号开奖 </a> 
						<a class="btn btn-warning btn-sm _cancelOrder"> 撤单 </a>
					</div>
					<div class="_succDiv" style="display:none;">
						<span></span><span class="text-muted"></span>
						<a class="btn btn-primary btn-sm _reAward">重开</a>
					</div>
					<div class="form-inline _reAwardDiv" style="display:none;">
						<div class="form-group">
							<input type="text" class="form-control input-sm" placeholder="号码之间以逗号分隔 ">
						</div>
						<a class="btn btn-danger btn-sm _handAward"> 重开 </a> 
						<a class="btn btn-warning btn-sm _cancelOrder"> 撤单 </a>
						<a class="btn btn-primary btn-sm _back">取消</a>
					</div>
					<div class="_stopDiv" style="display:none;">
						<a class="btn btn-danger btn-sm"> 停止开奖 </a>
					</div>
					<input type="hidden" class="_recordId"/>
					<input type="hidden" class="_lotCode"/>
					<input type="hidden" class="_lotGroup"/>
				</td>
			</tr>
		</table>
		<ul class="pager">
			<li><a href="javascript:;" class="_prePage">上一页</a></li>
			<span class="_pn"></span>/<span class="_tp"></span>
			<li><a href="javascript:;" class="_nextPage">下一页</a></li>
		</ul>

	</div>
	</div>
	</div>
	<div class="modal fade" id="back_order_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4><label class="modal-title"></label></h4>
				</div>
				<div class="modal-body">
					<p>
						您确定要将[
						  <span class="text-danger _lotName"></span>
						]，
						第 [<span class="text-danger _lotIssue"></span>]
						期的所有订单，做<span class="_info"></span>操作？
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default _back" data-dismiss="modal">返回</button>
					<button type="button" class="btn btn-primary _confirm">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>