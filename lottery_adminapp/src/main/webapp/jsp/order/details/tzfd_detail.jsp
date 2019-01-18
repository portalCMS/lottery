<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/order/tzfd_detail.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="showOrders.do">订单管理</a></li>
			<li class="active">${ordervo.orderNumber }</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">订单信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">用户</label>
						<div class="form-control-static col-sm-10">
							<a href="user/showUserInfo/${ordervo.customerId }.do">${ordervo.customerName }</a>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-1">订单类型</label>
						<div class="form-control-static col-sm-10">投注返款</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-1">订单编号</label>
						<div class="form-control-static col-sm-10">
							${ordervo.orderNumber }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">创建时间</label>
						<div class="form-control-static col-sm-10">${ordervo.orderTime }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">订单金额</label>
						<div class="form-control-static col-sm-10">
							<span class="text-danger"><fmt:formatNumber value="${ordervo.orderAmount }" groupingUsed="true" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">状态</label>
						<div class="form-control-static col-sm-10">
							<span class="text-success">${ordervo.orderStatusName }</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">投注信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					
					<div class="form-group">
						<label class="control-label col-sm-1">订单编号</label>
						<div class="form-control-static col-sm-10">
							<a href="javascript:void(0);" onclick="details.queryDetail('showQueryInfotz.do','${ordervo.rsvst1 }')">${ordervo.rsvst1 }</a>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-1">用户返点</label>
						<div class="form-control-static col-sm-10">
							<span class="text-danger"><fmt:formatNumber value="${ordervo.rsvdc4 }" pattern="0.0%" /></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>