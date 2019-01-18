<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/statistic/marketquery.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_marketing.jsp"%>
			<div class="col-sm-10">
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<span class="text-muted">网站运营常规数据</span>
							<div class="pull-right">
								<div class="pull-right form-inline">
										<span class="text-muted f12">时间范围:</span>
								<div class="form-group">
									<input type="datetime" class="form-control input-sm h21" id="st1">
								</div>
								<span class="text-muted f12">至</span>
								<div class="form-group">
									<input type="datetime" class="form-control input-sm h21" id="et1">
								</div>
									<div class="btn-group">
										<button class="btn btn-primary btn-xs dropdown-toggle " type="button" data-toggle="dropdown">
											查询数据 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li>
												<a href="javascript:;" id="queryMarket">查询数据</a>
											</li>
											<li>
												<a href="javascript:;" id="downloadExcel">下载报表</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body">
							<table class="table table-hover table-striped">
								<thead>
									<tr class="text-muted ">
										<td>数据项</td>
										<td>值</td>
										<td>数据项</td>
										<td>值</td>
										<td>数据项</td>
										<td>值</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>注册人数</td>
										<td><span id="register"></span>人</td>
										<td>充值人数</td>
										<td>共<span id="recharge"></span>人[<span id="firstRc"></span>人首充]</td>
										<td>提款人次</td>
										<td><span id="drawingCount"></span>人/共<span id="drawingTimes"></span>次</td>
									</tr>
									<tr>
										<td>活跃人数</td>
										<td><span id="hot"></span>人</td>
										<td>充值金额</td>
										<td><span id="rcAmount" class="_amount"></span></td>
										<td>提款金额</td>
										<td><span id="dwAmount" class="_amount" ></span></td>
									</tr>
									<tr>
										<td>投注记录数</td>
										<td><span id="betCount"></span></td>
										<td>中奖记录数</td>
										<td><span id="awardCount"></span></td>
										<td>投注返点总额</td>
										<td><span id="rebatesAmount" class="_amount"></span></td>
									</tr>
									<tr>
										<td>投注总额(含追期)</td>
										<td><span id="betAmount" class="_amount"></span></td>
										<td>中奖返款总额</td>
										<td><span id="awardAmount" class="_amount"></span></td>
										<td>投注盈收总额</td>
										<td><span id="revenusAmount" class="_amount"></span></td>
									</tr>
									<tr>
										<td>网站余额</td>
										<td><span id="cashAmount" class="_amount"></span></td>
										<td></td>
										<td></td>
										<td class="text-danger">网站盈利总额</td>
										<td><span id="webProfitAmount" class="text-danger"></span></td>
									</tr>
									<tr>
										<td colspan="6">
											<span class="text-muted">
												网站余额，是以结束日期的最后结束时间的，用户余额的总和。 
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							用户充值信息
						</div>
						<div class="panel-body">
							<table class="table table-condensed table-hover">
								<thead>
									<tr class="text-muted">
										<td>充值时间</td>
										<td>充值次数</td>
										<td>用户</td>
										<td>上级</td>
										<td>用户类型</td>
										<td>充值方式</td>
										<td>充值金额</td>
										<td>账户余额</td>
										<td>订单详情</td>
									</tr>
								</thead>
								<tbody id="rechargeTb">
									
								</tbody>
									<tr class="_hidTr" style="display:none;">
										<td class="_time">2014-11-5 18:40:25</td>
										<td class="_times">新用户首次充值</td>
										<td><a href="javascript:;" class="_cn">张三</a>[<span class="_rebeats"></span>%]</td>
										<td><a href="javascript:;" class="_fn">李四</a></td>
										<td class="_ct">一级代理</td>
										<td class="_rct">银行卡转账</td>
										<td class="_oa _amount">20000.0000</td>
										<td class="_cash _amount">123456.00</td>
										<td><a href="javascript:;" class="_queryDetail">查看详情</a>
											<input type="hidden" class="_oid"/>
										</td>
									</tr>
							</table>
							<nav>
								<ul class="pager">
									<li>
										<a href="javascript:;" id="prev">上一页</a>
									</li>
									<span id="pageNo">0</span>/<span id="totalPage">0</span>
									<li>
										<a href="javascript:;" id="next">下一页</a>
									</li>
								</ul>
							</nav>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-body">
							<div id="renjunAnalytics"></div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-body">
							<div id="marketingAnalytics"></div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<span class="text-muted pull-left"> 网站余额以用户账户余额金额为准 </span>
							<div class="pull-right">
								<div class="pull-right form-inline">
									<div class="form-group">
										<input type="datetime" class="form-control input-sm h21 _startTime" id="st2">
									</div>
									<span class="text-muted">至</span>
									<div class="form-group">
										<input type="datetime" class="form-control input-sm h21 _endTime" id="et2">
									</div>
									<div class="btn-group">
										<button class="btn btn-primary btn-xs dropdown-toggle" type="button" data-toggle="dropdown">
											查询数据 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li>
												<a href="javascript:;" id="queryAmount">查询数据</a>
											</li>
											<li>
												<a href="javascript:;" id="downloadAmountExcel">下载报表</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body">
							<div id="moneyAnalytics"></div>
						</div>
					</div>
				</div>
		</div>
	</div>
</body>
</html>