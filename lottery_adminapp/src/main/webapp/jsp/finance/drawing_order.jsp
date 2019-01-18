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
	$(document).ready(function() {
		//验证码
// 		$("#pcode").attr("title", "点击刷新");
// 		$("#pcode").bind("click", function() {
// 			$("#pcode").attr("src", "${contextPath}/picode/ycode.do?key=drawingOrderkey&&"+Math.random());
// 		});
		
		$(".form_datetime").eq(0).val(getNowTime(0, -7));
		$(".form_datetime").eq(1).val(getNowTime(0, 0));
		$(".form_datetime").datetimepicker({
			format : 'yyyy-m-d h:i',
			showMeridian : true,
			autoclose : true,
			todayBtn : true
		}).on("hide", function(ev) {
			var t1 = $(".form_datetime").eq(0).val();
			var t2 = $(".form_datetime").eq(1).val();
			if (contrastTime(t1, t2)) {
				$(".form_datetime").eq(1).val(getNowTime(0, 0));
			}
		});
	});
	
	function approveDrawingOrder(){
// 		if(!checkCodePwd()){
// 			return false;
// 		}
		var attrs = new Array();
		attrs.push("id");
		attrs.push("rsvst4");
		attrs.push("adminPwd");
		attrs.push("picCode");
		var vals = new Array();
		vals.push($("#id").val());
		vals.push($("#remarkInfo").val());
		vals.push($("#adminPwd").val());
		vals.push($("#picCode").val());
		
		formPost.submitForm("approveDrawingOrder.do",attrs,vals);
	}
	function rejectDrawingOrder(){
// 		if(!checkCodePwd()){
// 			return false;
// 		}

		if($("#remarkInfo").val().trim()==""){
			formPost.showErrorMessage("驳回申请，备注信息必须填写!");
			$("#remarkInfo").focus();
			return;
		}
		var attrs = new Array();
		attrs.push("id");
		attrs.push("rsvst4");
		attrs.push("adminPwd");
		attrs.push("picCode");
		var vals = new Array();
		vals.push($("#id").val());
		vals.push($("#remarkInfo").val());
		vals.push($("#adminPwd").val());
		vals.push($("#picCode").val());
		
		formPost.submitForm("rejectDrawingOrder.do",attrs,vals);
	}
	
	function checkCodePwd(){
		var code = $("#picCode").val();
		var pwd = $("#adminPwd").val();
		if(null==pwd||pwd==""){
			Messenger().post({
				message : "提款密码不能为空！",
				showCloseButton : true
			});
			return false;
		}
		if(null==code||code==""){
			Messenger().post({
				message : "验证码不能为空！",
				showCloseButton : true
			});
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active">提款</li>
			<li class="active">订单编号：${order.orderNumber }</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">申请人基本信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label">申请用户</label>
								<div class="col-sm-9">
									<p class="form-control-static">${userCard.openCardName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">账户余额</label>
								<div class="col-sm-9">
									<p class="form-control-static text-danger">${cash.cash }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">冻结金额</label>
								<div class="col-sm-9">
									<p class="form-control-static text-muted">${cash.frozenCash }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">申请金额</label>
								<div class="col-sm-9">
									<p class="form-control-static text-success">${order.orderAmount }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">当日申请提款</label>
								<div class="col-sm-9">
									<p class="form-control-static ">
										<label class="label label-danger">${drawingTimes }/${totalTimes }次</label> <label
											class="label label-warning">￥${totalAmount }</label>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">当日成功提款</label>
								<div class="col-sm-9">
									<p class="form-control-static ">
										<label class="label label-danger">${successTimes }次</label> <label
											class="label label-warning">￥${successAmount }</label>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">订单信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">订单编号</label>
								<div class="col-sm-10">
									<p class="form-control-static">${order.orderNumber }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">申请时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">${order.orderTime }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">订单状态</label>
								<div class="col-sm-10">
									<c:choose>
										<c:when test="${order.orderStatus==17001}"><font color="deepskyblue">处理中</font></c:when>
										<c:when test="${order.orderStatus==17002}"><font color="green">成功</font></c:when>
										<c:when test="${order.orderStatus==17003}"><font color="red">失败</font></c:when>
										<c:when test="${order.orderStatus==17004}"><font color="gray">过期</font></c:when>
										<c:otherwise><font color="red">未定义状态</font></c:otherwise>
									</c:choose>
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
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--  
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="">订单记录(暂时不做)</div>
			</div>
			<div class="panel-body">
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-12">
						<div class="form-inline">
							<div class="form-group">
								<input type="text" class="form-control form_datetime " />
							</div>
							至
							<div class="form-group">
								<input type="text" class="form-control form_datetime" />
							</div>
							<div class="form-group">
								<select class="form-control">
									<option>全部财务类型</option>
									<option>收入</option>
									<option>支出</option>
								</select>
							</div>
							<div class="form-group">
								<select class="form-control">
									<option>全部订单类型</option>
									<option>投注扣款</option>
									<option>中奖返款</option>
								</select>
							</div>
							<div class="form-group">
								<a href="#" class="btn btn-primary">查询订单</a>
							</div>
						</div>
					</div>
				</div>
				<table
					class="table table-hover table-bordered table-striped table-condensed ">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>时间</td>
							<td>金额</td>
							<td>金额类型</td>
							<td>财务类型</td>
							<td>订单类型</td>
							<td>现金余额</td>
							<td>彩金余额</td>
							<td width="100">操作</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>2014-6-19 16:25:29</td>
							<td class="text-danger">100.00000</td>
							<td>现金</td>
							<td class="text-danger">支出</td>
							<td>投注扣款</td>
							<td>10000.0000</td>
							<td>10.0000</td>
							<td width="100"><a href="#" class="btn btn-link btn-sm">查看详情</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>2014-6-19 16:25:29</td>
							<td class="text-success">100.00000</td>
							<td>现金</td>
							<td class="text-success">收入</td>
							<td>中奖返款</td>
							<td>10000.0000</td>
							<td>10.0000</td>
							<td width="100"><a href="#" class="btn btn-link btn-sm">查看详情</a></td>
						</tr>
					</tbody>
				</table>
				<ul
					class="pagination pagination-sm clear_margin_bottom clear_margin_top">
					<li class="disabled"><a href="#">&laquo;</a></li>
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
		-->
		<div class="panel panel-default">
			<div class="panel-heading">收款账号信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">银行名称</label>
								<div class="col-sm-10">
									<p class="form-control-static">${order.bank.name }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">银行卡号</label>
								<div class="col-sm-10">
									<p class="form-control-static">${userCard.cardNo }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">支行名称</label>
								<div class="col-sm-10">
									<p class="form-control-static">${userCard.branchBankName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">开户姓名</label>
								<div class="col-sm-10">
									<p class="form-control-static">${userCard.openCardName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">绑卡时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">${userCard.createTime }</p>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${order.orderStatus==17001 }">
		<form action="approveDrawingOrder.do" id="reviewOrderForm" name="reviewOrderForm">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
<!-- 							<div class="form-group"> -->
<!-- 								<label for="pwd" class="col-sm-2 control-label">提款密码</label> -->
<!-- 								<div class="col-sm-5"> -->
<!-- 									<input type="password" class="form-control" id="adminPwd" name="adminPwd"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-sm-2 control-label">验证码</label> -->
<!-- 								<div class="col-sm-2"> -->
<!-- 									<input type="text" class="form-control pull-left " id="picCode" name="picCode" style="width: 100px;margin-right: 10px;"> -->
<!-- 								</div> -->
<!-- 								<div class="col-sm-2"> -->
<%-- 									<img class="pull-left" id="pcode" src="${contextPath}/picode/ycode.do?key=drawingOrderkey" width="100" height="35" /> --%>
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label for="pwd" class="col-sm-2 control-label">备注</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="remarkInfo" name="rsvst4"/> 
									<span class="help-block">如果驳回充值申请，请务必填写备注信息。</span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<input type="hidden" id="id" name="id" value="${order.id }"/>
									<button type="button" class="btn btn-danger" onclick="approveDrawingOrder()">已完成申请</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-default" onclick="rejectDrawingOrder()">驳回该申请</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
		</c:if>
	</div>
</body>
</html>
