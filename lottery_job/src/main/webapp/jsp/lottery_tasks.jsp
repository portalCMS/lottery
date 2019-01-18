<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>奖期任务</title>
<%@ include file='include/asset_css.jsp'%>
<%@ include file='include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/tasks.js"></script>
</head>
<body>
	<%@ include file='include/top.jsp'%>
	<div class="container">
		<div class="row">
			<div class="col-sm-2">
				<div class="list-group _names">
					<c:forEach items="${groupList}" var="group">
						<a href="javascript:;" class="list-group-item"> 
							${group.lotteryGroupName}<input type="hidden" value="${group.lotteryGroup }"/> 
						</a>
					</c:forEach>
				</div>
			</div>
			<div class="col-sm-10">
			<div class="panel panel-default">
			<div class="panel-heading"><span id="groupName"></span>奖期任务</div>
			<div class="panel-body">
				<ul class="nav nav-tabs _lots">
					<c:forEach items="${lotteryList}" var="lot">
						<li>
							<a href="javascript:;">
								<span class="_lotName">${lot.lotteryName }</span>
								<input type="hidden" class="_lotCode" value="${lot.lotteryCode }"/>
								<input type="hidden" class="_lotGroup" value="${lot.lotteryGroup }">
								<input type="hidden" class="_seriesRule" value="${lot.seriesRule }">
								<input type="hidden" class="_totalTimes" value="${lot.totalTimes }">
							</a>
						</li>
					</c:forEach>
				</ul>
				<div class="nav_content">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="pull-left">
								<a href="javascript:;" class="btn btn-sm btn-danger"
									id="_create_issue_btn">批量生成奖期</a>
								<a href="javascript:;" class="btn btn-sm btn-danger" 
									id="all_delete">删除所选奖期</a>	
							</div>
							<div class="pull-right">
								<a href="javascript:;" class="btn btn-sm btn-danger _removeTasks">停止所选奖期</a>
								<a href="javascript:;" class="btn btn-sm btn-danger _refreshTasks">刷新奖期任务</a>
							</div>
							<div class="clearfix"></div>
							<div class="split"></div>
							<span class="text-muted" id="createIssueDesc">
								每天最后一期结束后，系统会自动生成隔天的全部奖期，无需人工操作，如生成奖期有误，请修改调整。 </span>
							<div id="createIssuePanle">
								<span class="text-muted text-primary" id="createIssueDesc">
									填写首期奖期任务的开始投注时间，截止投注时间，以及开奖时间。</span>
								<div class="form-horizontal" style="margin-top:5px;">
									<div class="form-group">
										<label class="control-label col-sm-2">开始时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _startTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">截止时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _endTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">开奖时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _lotTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">间隔时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _spanTime" value="10:00"/>
										</div>
									</div>
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">奖期跨度：</label>
										<div class="col-sm-3">
											<select class="form-control" id="periodType" name="periodType">
												<option value="1">当天</option>
												<option value="2">跨天</option>
												<option value="3">明天</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">生成期数：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _totalTimes" value="1"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">抓取次数：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _catchTimes" value="40"/>
										</div>
									</div>
								</div>
								<a href="javascript:;" class="btn btn-primary col-sm-offset-2 _commit">提交</a>
								<a href="javascript:;" class="btn btn-primary _cancel" >取消</a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default _updatePanel">
						<div class="panel-body">
							<div class="pull-left">
								<h4 class="text-info">修改奖期任务</h4>
							</div>
							<div class="pull-right">
								<h4 class="text-info _cancel_update"><a href="javascript:;" class="label label-danger">x</a></h4>
							</div>
							<div class="clearfix"></div>
							<div class="split"></div>
							<div id="updateIssuePanle">
								<div class="form-horizontal">
									<div class="form-group">
										<label class="control-label col-sm-2">彩种期号：</label>
										<div class="col-sm-3">
											<input type="hidden" class="_upd_id" />
											<input type="text" class="form-control _upd_issueNo" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">开始时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _upd_startTime" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">截止时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _upd_endTime" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">开奖时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _upd_lotTime" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">抓取次数：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _upd_catchTimes" />
										</div>
									</div>
								</div>
								<a href="javascript:;" class="btn btn-primary col-sm-offset-2 _upd_commit">提交</a>
							</div>
						</div>
					</div>
					
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
					
					<table class="table table-hover table-condensed table-bordered _taskTable">
						<thead>
							<tr class="success text-center">
								<td class="text-left">
									<label class="checkbox-inline"> 
										<input type="checkbox" id="checkAll" />
										&nbsp;期号
									</label>
								</td>
								<td>彩种名称</td>
								<td>开始时间</td>
								<td>截止时间</td>
								<td>开奖时间</td>
								<td>抓取次数</td>
<!-- 								<td>状态</td> -->
								<td>运行状态</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="tasksTbody">
							
						</tbody>
						<tr class="_hidTr text-center" style="display:none;">
							<td class="text-left" style="vertical-align: middle;">
								<label class="checkbox-inline"> 
									<input type="checkbox" class="_cb" />
									<input type="hidden" class="_id" />
									<span class="_issueNo"></span>	
								</label>
							</td>
							<td style="vertical-align: middle;"><span class="_lotName"></span></td>
							<td style="vertical-align: middle;"><span class="_startTime"></span></td>
							<td style="vertical-align: middle;"><span class="_endTime"></span></td>
							<td style="vertical-align: middle;"><span class="_lotTime"></span></td>
							<td style="vertical-align: middle;"><span class="_catchTimes"></span></td>
<!-- 							<td style="vertical-align: middle;"><label class="label label-success _status">正常</label></td> -->
							<td style="vertical-align: middle;"><label class="label label-primary _running"></label></td>
							<td class="text-center" style="vertical-align: middle;">
								<a href="javascript:;" class="btn btn-link btn-sm _upd_btn">修改</a>
								<a href="javascript:;" class="btn btn-link btn-sm _del_btn">删除</a>
								<a href="javascript:;" class="btn btn-link btn-sm _log_btn">查看日志</a>
							</td>
						</tr>
						<li class="_hidLi" style="display:none;">
							<a href="javascript:;">
								<span class="_lotName"></span>
								<input type="hidden" class="_lotCode"/>
								<input type="hidden" class="_lotGroup"/>
								<input type="hidden" class="_seriesRule" />
								<input type="hidden" class="_totalTimes" />
							</a>
						</li>
					</table>
				</div>
			</div>
		</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="del_all_model">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">x</span>
					</button>
					<h4 class="modal-title">批量删除</h4>
				</div>
				<div class="modal-body">
					<p>确定删除已勾选的定时任务？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default " data-dismiss="modal">
						取消</button>
					<button type="button" class="btn btn-primary _del_all_btn">确定删除</button>
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
					<h4 class="modal-title">奖期任务删除</h4>
				</div>
				<div class="modal-body">
					<p>确定删除该定时任务？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default " data-dismiss="modal">
						取消</button>
					<button type="button" class="btn btn-primary _del_btn">确定删除
					<input type="hidden" class="_curId"/></button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>