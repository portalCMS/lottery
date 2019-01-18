<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>奖期自动生成任务</title>
<%@ include file='include/asset_css.jsp'%>
<%@ include file='include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/jobs.js"></script>
</head>
<body>
	<%@ include file='include/top.jsp'%>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<ul class="nav nav-tabs _groups">
					<c:forEach items="${groupList}" var="group">
						<li>
							<a href="javascript:;">
								<span class="_lotName">${group.lotteryGroupName}</span>
								<input type="hidden" value="${group.lotteryGroup }"/> 
							</a>
						</li>
					</c:forEach>
				</ul>
				<div class="nav_content">
					<div class="panel panel-default">
					<div class="panel-body">
					<div class="pull-left">
						<a href="javascript:;" class="btn btn-sm btn-primary" id="sta_sel_btn">启动所选任务</a>
						<a href="javascript:;" class="btn btn-sm btn-primary" id="stop_sel_btn">停止所选任务</a>	
						<a href="javascript:;" class="btn btn-sm btn-primary" id="upd_time_sel_btn">修改所选任务执行时间</a>
<!-- 						<a href="javascript:;" class="btn btn-sm btn-primary _refreshTasks">刷新任务列表</a> -->
					</div>
					<div class="pull-right ">
						<label class="checkbox-inline" style="margin-top:5px;">
					      <input type="checkbox" id="createToday">
					      <span class="text-primary">重新生成当天奖期</span>
					    </label>
					</div>
					<div class="clearfix"></div>
					<br>
					<span class="text-warning" style="margin-top: 5px;">
						根据配置的任务执行时间，系统会自动生成隔天的全部奖期，如果要重新生成当天的奖期任务，请勾选"重新生成当天奖期"。
					</span>
					<div class="split"></div>
					<table class="table table-hover table-condensed table-bordered">
						<thead>
							<tr class="success text-center">
								<td><label class="checkbox-inline"> 
									<input type="checkbox" class="_all_cb" />
								</label></td>
								<td>彩种名称</td>
								<td>开始时间</td>
								<td>截止时间</td>
								<td>执行时间表达式</td>
								<td>间隔时间(秒)</td>
								<td>总期数</td>
								<td>抓取次数</td>
								<td>截止投注(秒)</td>
								<td>开奖延迟(秒)</td>
								<td>任务状态</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="jobsTbody">
							
						</tbody>
						<tr class="_hidTr text-center" style="display:none;">
							<td class="text-left " style="vertical-align: middle;">
								<label class="checkbox-inline"> 
									<input type="checkbox" class="_cb" />
									<input type="hidden" class="_id" />
									<input type="hidden" class="_code" />
								</label>
							</td>
							<td style="vertical-align: middle;"><span class="_lotName"></span></td>
							<td style="vertical-align: middle;"><span class="_startTime"></span></td>
							<td style="vertical-align: middle;"><span class="_endTime"></span></td>
							<td style="vertical-align: middle;">
								<input class="form-control _lotTimeCorn" type="text"/>
							</td>
							<td style="vertical-align: middle;"><span class="_spanTime"></span></td>
							<td style="vertical-align: middle;"><span class="_totalTimes"></span></td>
							<td style="vertical-align: middle;"><span class="_catchTimes"></span></td>
							<td style="vertical-align: middle;"><span class="_befLot"></span></td>
							<td style="vertical-align: middle;"><span class="_aftLot"></span></td>
							<td style="vertical-align: middle;"><label class="label label-warning _status">停止</label></td>
							<td class="text-center" style="vertical-align: middle;">
								<a href="javascript:;" class="btn  btn-xs _log_btn">日志</a>
							</td>
						</tr>
					</table>
					
					<div class="panel panel-default _logPanel">
						<div class="panel-body">
							<div class="pull-left">
								<h4 class="text-info">奖期任务日志</h4>
							</div>
							<div class="pull-right">
								<h4 class="text-info _cancel_log"><a href="javascript:;" class="label label-danger">x</a></h4>
							</div>
							<div class="clearfix"></div>
							<div class="split"></div>
							<span class="text-warning" style="margin-top: 5px;">
								只显示最近的10条日志记录。
							</span>
							<table class="table table-hover table-condensed table-bordered">
								<thead>
									<tr class="success text-center">
										<td>任务名称</td>
										<td>开始时间</td>
										<td>结束时间</td>
										<td>运行结果</td>
										<td>异常日志</td>
									</tr>
								</thead>
								<tbody id="logListTbody">
								</tbody>
							</table>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="del_model">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">x</span>
					</button>
					<h4 class="modal-title _title">停止奖期生成任务</h4>
				</div>
				<div class="modal-body">
					<p class="_confirmInfo">确定停止该奖期生成任务？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default " data-dismiss="modal">
						取消</button>
					<button type="button" class="btn btn-primary _confirm_btn">确定
					<input type="hidden" class="_curId"/></button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>