<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript">
	var path = '${contextPath}';
</script>
<script type="text/javascript" src="${contextPath }/jsp/js/finance/rechargeOrderInfo.js"></script>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active">充值</li>
			<li class="active">订单号：${order.orderNumber }</li>
		</ol>
		<form action="approveRechargeOrder2.do" id="orderInfoForm" name="orderInfoForm">
		<div class="panel panel-default">
			<div class="panel-heading">订单信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-10">
									<p class="form-control-static">${order.user.customerName}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">申请时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">${order.orderTime}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">订单状态</label>
								<div class="col-sm-10">
									<p class="form-control-static">
										<label class="label label-success">
											<c:choose>
												<c:when test="${order.orderStatus==17001}"><font color="yellow">处理中</font></c:when>
												<c:when test="${order.orderStatus==17002}"><font color="green">成功</font></c:when>
												<c:when test="${order.orderStatus==17003}"><font color="red">失败</font></c:when>
												<c:when test="${order.orderStatus==17004}"><font color="gray">过期</font></c:when>
												<c:otherwise><font color="gray">过期</font></c:otherwise>
											</c:choose>
										</label>
										<c:if test="${order.orderStatus==17001}">
											<label class="label label-warning" style="margin-left:10px;">订单将于${order.cancelTime}过期</label>
										</c:if>
										 <c:if test="${order.orderStatus==17004}">
											<label class="label label-warning" style="margin-left:10px;">订单于${order.cancelTime}已过期</label>
										</c:if>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">充值信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal" id="rechargeDiv">

							<div class="form-group">
								<label class="col-sm-2 control-label">银行名称</label>
								<div class="col-sm-10">
									<p class="form-control-static ">${order.card.bankName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">银行卡号</label>
								<div class="col-sm-10">
									<p class="form-control-static ">${order.card.cardNo }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">开户姓名</label>
								<div class="col-sm-10">
									<p class="form-control-static ">${order.card.opencardName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">附言</label>
								<div class="col-sm-10">
									<p class="form-control-static ">${order.remark}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">充值金额</label>
								<div class="col-sm-10">
									<p class="form-control-static text-danger _amount" id="orderAmount">${order.orderAmount}</p>
								</div>
							</div>
							<c:if test="${order.orderStatus != 17001 && order.orderStatus != 17004}">
							<div class="form-group">
								<label class="col-sm-2 control-label">备注</label>
								<div class="col-sm-10">
									<p class="form-control-static text-danger" id="rsvst4">${order.rsvst4}</p>
								</div>
							</div>
							</c:if>
							<c:if test="${order.orderStatus ==17002}">
								<div class="form-group">
									<label for="pwd" class="col-sm-2 control-label">手续费</label>
									<div class="col-sm-10">
										<p class="form-control-static text-danger _amount">${order.transferAmount}</p>
									</div>
								</div>
							</c:if>
							<c:if test="${order.orderStatus ==17001}">
								<div class="form-group">
									<label for="pwd" class="col-sm-2 control-label">实际到账</label>
									<div class="col-sm-4">
									<input type="hidden" value="${order.id }" id="id" name="id"/>
									<input type="hidden" value="${order.orderNumber }" id="orderNumber" name="orderNumber"/>
									<input type="text" class="form-control checkedAttr" id="receiveAmount" name="receiveAmount"
									checkType="isNotEmpty,decmal9" alt="实际到账不能为空！,实际到账必须为正浮点数！" />
									</div>
								</div>
								<div class="form-group">
									<label for="pwd" class="col-sm-2 control-label">手续费</label>
									<div class="col-sm-4">
										<input type="text" class="form-control checkedAttr" id="transferAmount" name="transferAmount"
										placeholder="实际金额,非百分比 " checkType="isNotEmpty,decmal9" alt="手续费不能为空！,手续费必须为正浮点数！"/> 
										<span class="help-block">默认充值金额的0.1%计算</span>
									</div>
								</div>
								<div class="form-group">
									<label for="pwd" class="col-sm-2 control-label">备注</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="remarkInfo" name="rsvst4"/> 
										<span class="help-block">如果驳回充值申请，请务必填写备注信息。</span>
									</div>
								</div>
								<div class="split"></div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="button"  onclick="approveOrder()" class="btn btn-danger">审核无误完成充值
										</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button"   onclick="rejectOrder()" class="btn btn-default">驳回该申请</button>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
		<c:if test="${order.orderStatus ==17004}">
		<form action="restartOrder.do" id="restartOrderForm" name="restartOrderForm">
		<div class="panel panel-default">
			<div class="panel-body">
				<p class="text-danger">订单已于${order.cancelTime}超时关闭</p>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">财务密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="adminPwd" name="adminPwd"
										placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<input type="hidden" value="${order.id }" id="id" name="id"/>
									<input type="hidden" value="${order.orderNumber }" id="orderNumber" name="orderNumber"/>
									<button type="button" onclick="restartOrder()" class="btn btn-primary">重启订单</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		</form>
		</c:if>
	</div>
</body>
</html>
