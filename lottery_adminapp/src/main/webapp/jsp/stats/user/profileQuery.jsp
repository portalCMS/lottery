<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/statistic/profilequery.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_user.jsp"%>
			<div class="col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="pull-left">
							大户概况<span class="text-danger"></span>
							<span  class="text-danger">【默认 ${limitDays }</span>
							<span class="text-danger">天累计投注量大于 ${limitAmount } 】</span>
						</div>
						
						<div class="pull-right form-inline">
							<span class="text-muted f12">时间范围:</span>
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _startTime" id="startTime1">
							</div>
							<span class="text-muted f12">至</span>
							<div class="form-group">
								<input type="datetime" class="form-control input-sm h21 _endTime" id="endTime1">
							</div>
							<a href="javascript:;" class="btn btn-xs btn-primary " id="queryPrfAmount">查询</a> 
							<a href="javascript:;" class="btn btn-xs btn-primary " id="downloadExcel">下载报表</a> 
							<a href="javascript:;" class="btn btn-xs btn-default settingModalBtn">统计规则</a>
						</div>
					</div>
					<div class="panel-body">
						<table class="table table-hover">
							<thead>
								<tr class="text-muted">
									<td>数据项</td>
									<td>值</td>
									<td>数据项</td>
									<td>值</td>
									<td>数据项</td>
									<td>值</td>
								</tr>
							</thead>
							<tbody id="orderList">
							<c:forEach items="${listVo }" var="vo" varStatus="i1">
								<tr>
									<c:forEach items="${vo.vos}" var="t" varStatus="i2">
										<td class="_orderType">${t.value }</td>
										<td class="_amount text-danger" name="${t.value }">${t.value2 }</td>
									</c:forEach>
								</tr>
							</c:forEach>
								
								
							</tbody>
						</table>
						<p class="text-danger text-right">点击下方用户列表，查看具体用户记录</p>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-8">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="form-inline">
									<div class="form-group">
										<input id="userName" type="text" class="form-control" placeholder="用户账户名" />
									</div>
									<div class="form-group">
										<input type="hidden" id="userId"/>
										<a href="javascript:;" class="btn btn-info" id="queryProfile">查询该用户</a>
										<span  class="text-muted" id="reportDays" style="margin-left:20px;"></span>
									</div>
								</div>
								<p class="split"></p>
								<div class="well well-sm text-muted clearfix">
									<div class="pull-left">
										返点：<span class="text-danger" id="rebates"> </span>&nbsp;&nbsp; 
										用户类型：<span class="text-danger" id="userType"> </span>&nbsp;&nbsp; 
										父级：<a href="javascript:;" class="btn btn-link btn-xs" id="fName"> </a>&nbsp;&nbsp; 
										投注累计：<span class="text-danger" id="totalAmount"> </span>
									</div>
									<div class="pull-right">
										<a href="javascript:;" class="btn btn-default btn-xs" id="queryUserDetail">查看详情</a>
									</div>
								</div>
								<p class="text-muted">输入用户名称进行查询，或者点击右侧列表的用户名称进行查询。</p>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-body">
								<div id="yk_analytic"></div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="roiCount"></div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="betAanlytics"></div>
							</div>
						</div>

						<table
							class="table  table-condensed table-striped table-view table-responsive text-center table-hover">
							<thead>
								<tr class="success">
									<td>#</td>
									<td>时间</td>
									<td>IP</td>
									<td>所在地</td>
									<td>事件</td>
								</tr>
							</thead>
							<tbody id="ipTb">
							</tbody>
							<tr class="_hidTr" style="display:none;">
								<td>1</td>
								<td>2014-11-3 15:08:24</td>
								<td>127.0.0.1</td>
								<td>本地</td>
								<td>登陆</td>
							</tr>
						</table>
						<nav>
						<ul class="pager" id="ipPage">
							<li><a href="javascript:;" id="ipPrev">上一页</a></li>
							<span id="ipPageNo">0</span>/<span id="ipTotalPage">0</span>
							<li><a href="javascript:;" id="ipNext">下一页</a></li>
						</ul>
						</nav>
					</div>
					<div class="col-sm-4">
						<ul class="list-group list-unstyled" id="pageUl">
						</ul>
						<li class="list-group-item _hidLi" style="display:none;">
							<label class="badge _index">1</label>
							<a href="javascript:;" class="_name">李四</a>
							<input type="hidden" class="_uid" />&nbsp;
							<span class="text-muted _rebates" >6.5%</span>&nbsp;
							<span class="text-danger _betAmount amount">10000.00</span>
						</li>
						<nav>
						<ul class="pager f12">
							<li><a href="javascript:;" id="prev">上一页</a></li>
							<span id="pageNo">0</span>/<span id="totalPage">0</span>
							<li><a href="javascript:;" id="next">下一页</a></li>
						</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="settingModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">统计规则设置</h4>
				</div>
				<div class="modal-body">
					<div class="form-inline">
						<div class="form-group">
							距今日起<input type="text" style="width: 80px"
								class="form-control text-left" id="limitDays" value="${limitDays }"/>&nbsp;天内的累积投注量，
						</div>
						<div class="form-group">
							大于或等于&nbsp;<input type="text" style="width: 100px"
								class="form-control" id="limitAmount" value="${limitAmount }"/>&nbsp;则表示为大户。
						</div>
					</div>
					<p class="split"></p>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-2">报表：</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="reportDay" value="${reportDays }"/>
							</div>
							<span class="help-block" >天之前--至今</span>
						</div>
					</div>
				</div>
				<div class="modal-footer ">
					<button type="button" class="btn btn-primary " id="saveConfig">确定</button>
					<button type="button" class="btn btn-default " data-dismiss="modal">
						返回</button>

				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>