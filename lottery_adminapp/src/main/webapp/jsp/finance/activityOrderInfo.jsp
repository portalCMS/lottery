<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改条目</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/finance/activity_orderInfo.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active ">活动详情</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">活动详情</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">活动标题</label>
								<div class="col-sm-10">
									<p class="form-control-static">${activity.title }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">开始时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">${activity.starttime }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">结束时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">${activity.endtime }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">驳回原因</label>
								<div class="col-sm-10">
									<p class="form-control-static">${customerOrder.rsvst5}</p>
								</div>
							</div>
							
						</div>
					</div>	
				</div>
			</div>			
			<input id="content" type="hidden" value='${activity.summary }' />		
			<script id="editor" type="text/javascript" style="width:100%;height:500px;"></script>
			<br />	
		</div>
	</div>
</body>
</html>