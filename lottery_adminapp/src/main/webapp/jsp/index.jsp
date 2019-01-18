<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file='include/asset_css.jsp'%>
<%@ include file='include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/index.js"></script>

<title>index</title>
</head>
<body>
	<%@ include file="include/top.jsp"%>
	<div class="container">
		<c:if test="${isscue==true }">
			<div class="row" style="height: 240px; overflow: hidden">
				<div class="col-sm-6">
					<div id="lottery_log" style="height: 290px"></div>
				</div>
				<div class="col-sm-4">
					<div id="user_stat" style="height: 257px"></div>
				</div>
				<div class="col-sm-2">
					<label class="text-muted" style="font-size: 16px;margin-top: 10px;margin-bottom: 20px;">在线用户名</label>
					<textarea rows="9" cols="15" id="onLineUserNames"></textarea>
				</div>
			</div>
		</c:if>
		<br />
		<div class="split"></div>
		<div class="row">
				<div class="col-sm-8">
				<div class="panel panel-default">
					<div class="panel-heading">
						反馈列表 <span class="badge _count">1000</span>
					</div>
					<div class="panel-body">
						<table
							class="table table-bordered table-condensed table-container table-hover table-striped">
							<thead>
								<tr class="success text-center">
									<td width="5%">#</td>
									<td width="25%">时间</td>
									<td width="15%">类别</td>
									<td width="10%">用户</td>
									<td width="15%">页面名称</td>
									<td width="30%">内容</td>
								</tr>
							</thead>
							<tbody id="fbTbody">
								
							</tbody>
							
							<tr class="_hidTr text-center" style="display:none;">
								<td class="_index" style="vertical-align: middle;"></td>
								<td class="_time" style="vertical-align: middle;"></td>
								<td class="_typeName" style="vertical-align: middle;"></td>
								<td style="vertical-align: middle;" class="_user"></td>
								<td class="_url" style="vertical-align: middle;"></td>
								<td class="_content" style="vertical-align: middle;"></td>
							</tr>
						</table>
						<ul class="pager _feedbackPage">
							<li><a href="javascript:;" class="_prePage">上一页</a></li>
							<span class="_pn"></span>/<span class="_tp"></span>
							<li><a href="javascript:;" class="_nextPage">下一页</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="pull-left">
							<a href="${contextPath }/article/initNotice.do">网站公告</a>
						</div>
						<div class="pull-right">
							<a href="${contextPath }/article/initCreateNotice.do" class="btn  btn-xs btn-primary">添加新公告</a>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body">
						<ul class="list-unstyled _notices">
							
						</ul>
						<li class="_hidLi" style="display:none;">
							<div class="pull-left">
								<a href="javascript:;" class="_title">活动文字假字请勿阅读</a>
							</div>
							<div class="pull-right">
								<span class="text-muted _time"> 2014-7-21 17:58:24 </span>
							</div>
							<div class="clearfix"></div>
						</li>
						<ul class="pager _noticePage">
							<li><a href="javascript:;" class="_prePage">上一页</a></li>
							<span class="_pn"></span>/<span class="_tp"></span>
							<li><a href="javascript:;" class="_nextPage">下一页</a></li>
						</ul>
					</div>
				</div>
			</div>
	
		</div>
	</div>
</body>
</html>
