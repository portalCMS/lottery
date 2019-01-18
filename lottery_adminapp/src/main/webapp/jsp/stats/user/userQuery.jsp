<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/statistic/userquery.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_user.jsp"%>
			<div class="col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading">网站用户概况</div>
					<div class="panel-body">
						<table
							class="table table-view table-condensed table-striped text-center table-hover">
							<thead class="text-success ">
								<tr>
									<td>项</td>
									<td>人数</td>
									<td>项</td>
									<td>人数</td>
									<td>项</td>
									<td>人数</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>未激活用户数</td>
									<td id="inactive">${inactive }</td>
									<td>用户总数</td>
									<td id="allUser">${allUser }</td>
									<td>昨日新增用户</td>
									<td id="yesterday">${yesterday }</td>
								</tr>
								<tr>
									<td>未绑卡用户数</td>
									<td id="unbindCard">${unbindCard }</td>
									<td>代理总数</td>
									<td id="agentUser">${agentUser }</td>
									<td>昨日活跃用户</td>
									<td id="ydActive">${ydActive }</td>
								</tr>
								<tr>
									<td>未充值用户数</td>
									<td id="unRecharge">${unRecharge }</td>
									<td>会员总数</td>
									<td id="memberUser">${memberUser }</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>未投注用户数</td>
									<td id="unbet">${unbet }</td>
									<td>剩余配额</td>
									<td id="remainQuota">${remainQuota }</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="pull-left">
							当月总计： <span class="text-muted">活跃点累积<span
								class="text-danger" id="totalHotCount">0</span>点
							</span>&nbsp;&nbsp;
						</div>
						<div class="pull-right form-inline">
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _startTime" id="startTime1">
							</div>
							<span class="text-muted f12">至</span>
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _endTime" id="endTime1">
							</div>
							<a href="javascript:;" class="btn btn-primary btn-xs " id="userExcelDownload">下载报表</a>
						</div>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-8">
								<div id="hotUserCount"></div>
							</div>
							<div class="col-sm-4">
								<div id="userCount"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="pull-left">
							当月总计： <span class="text-muted">自主开户<span
								class="text-danger" id="proxyAdd">&nbsp;1&nbsp;</span>人
							</span>&nbsp;&nbsp; <span class="text-muted">链接开户<span
								class="text-danger" id="linkAdd">&nbsp;0&nbsp;</span>人
							</span>&nbsp;&nbsp; <span class="text-muted">自由注册<span
								class="text-danger" id="selfAdd">&nbsp;1&nbsp;</span>人
							</span>
						</div>
						<div class="pull-right form-inline">
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _startTime" id="startTime2">
							</div>
							<span class="text-muted f12">至</span>
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _endTime" id="endTime2">
							</div>
							<a href="javascript:;" class="btn btn-primary btn-xs " id="userAddExcelDownload">下载报表</a>
						</div>
					</div>
					<div class="panel-body">
						<div id="regMap"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>