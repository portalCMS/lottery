<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/statistic/ylreport.js"/>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_marketing.jsp"%>
			<div class="col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="pull-left form-inline">
								<span class="text-muted f12">时间范围:</span>
								<div class="form-group">
									<input type="text" class="form-control input-sm h21" id="st1" >
								</div>
								<span class="text-muted f12">至</span>
								<div class="form-group">
									<input type="text" class="form-control input-sm h21" id="et1">
								</div>
								<span class="text-muted f12" style="margin-left: 150px;">用户名:</span>
								<div class="form-group">
									<input type="text" class="form-control input-sm h21" id="uname">
								</div>
								<div class="btn-group">
									<button class="btn btn-primary btn-xs dropdown-toggle " type="button" data-toggle="dropdown">
										查询数据 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li>
											<a href="javascript:;" id="queryReport">查询数据</a>
										</li>
										<li>
											<a href="javascript:;" id="downloadExcel">下载报表</a>
										</li>
									</ul>
								</div>
						</div>
						<div class="pull-right">
							<!-- 来一个日期到日期的选择框 -->
						</div>
					</div>
					<div class="panel-body">
						<table width="100%" class="accountTab table">
							<thead>
								<tr>
									<th height="39" align="center" valign="middle">用户名</th>
									<th height="39" align="center" valign="middle">投注总额</th>
									<th height="39" align="center" valign="middle">返点总额</th>
									<th height="39" align="center" valign="middle">实际销售总额</th>
									<th height="39" align="center" valign="middle">中奖总额</th>
									<th height="39" align="center" valign="middle">游戏总盈亏</th>
								<!-- 	<th height="39" align="center" valign="middle">活动总领取</th>
									<th height="39" align="center" valign="middle">充提手续费</th>
									<th height="39" align="center" valign="middle">总结算</th> -->
									<th height="39" align="center" valign="middle">团队详情</th>
								</tr>
							</thead>
							<tbody id="orderTbody">
							</tbody>
							<!-- 隐藏tr对象，方便js进行tr对象克隆。 -->
							<tr class="_hidTr" style="display:none;">
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
							<!-- 	<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td>
								<td height="39" align="center" valign="middle"></td> -->
								<td height="39" align="center" valign="middle"></td>
							</tr>
						</table>
						<ul class="pager _pageInfo">
							<li><a href="#">上一页</a></li>
							<li><a href="#">下一页</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>