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
<script src="${contextPath }/jsp/js/finance/config.js"></script>
<script>
	$(document).ready(function(){
		$("#pcode").attr("title", "点击刷新");
		$("#pcode").bind("click", function() {
			$("#pcode").attr("src", "${contextPath}/picode/ycode.do?key=financeConfigkey&&"+Math.random());
		});
		
		
	});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active">财务配置</li>
		</ol>
		<form action="saveFinanceConfig.do" method="post" id="financeConfigForm" name="financeConfigForm">
		<div class="panel panel-default">
			<div class="panel-heading">充值配置</div>
			<div class="panel-body">
				<h5 class="text-success">充值通道1</h5>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">环讯充值账号</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="payAccount" name="payAccount" value="${payAccount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">环讯跳转地址</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="payUrl" name="payUrl" value="${payUrl }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值方式</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="isOpenHx" name="isOpenHx" value="${isOpenHx }">
									<span class="help-block">使用易宝请设置0，否则设置1</span>
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">易宝充值账号</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="ybAccount" name="ybAccount" value="${ybAccount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">易宝跳转地址</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="ybUrl" name="ybUrl" value="${ybUrl }">
								</div>
							</div>
						</div>
					</div>
				</div>
				<h5 class="text-success">充值通道2</h5>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">环讯充值账号</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="payAccountGroupTwo" name="payAccountGroupTwo" value="${payAccountGroupTwo }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">环讯跳转地址</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="payUrlGroupTwo" name="payUrlGroupTwo" value="${payUrlGroupTwo }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值方式</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="isOpenHxGroupTwo" name="isOpenHxGroupTwo" value="${isOpenHxGroupTwo }">
									<span class="help-block">使用易宝请设置0，否则设置1</span>
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">易宝充值账号</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="ybAccountGroupTwo" name="ybAccountGroupTwo" value="${ybAccountGroupTwo }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">易宝跳转地址</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="ybUrlGroupTwo" name="ybUrlGroupTwo" value="${ybUrlGroupTwo }">
								</div>
							</div>
						</div>
					</div>
				</div>
				<h5 class="text-success">充值方式开放条件</h5>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">默认充值方式</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="defaultRecharge" name="defaultRecharge" value="${defaultRecharge }">
									<span class="help-block">0全部开放,1银行卡,2第三方</span>
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值次数</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="defaultRecharge" name="rechargeCount" value="${rechargeCount }">
									<span class="help-block">充值次数与金额达到条件以后才全部开放全部充值方式</span>
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="defaultRecharge" name="rechargeMoney" value="${rechargeMoney }">
									<span class="help-block">充值次数与金额达到条件以后才全部开放全部充值方式</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<h5 class="text-success">第三方支付</h5>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最小充值金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="thirdPartyMinAmount" name="thirdPartyMinAmount" value="${thirdPartyMinAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最大充值金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="thirdPartyMaxAmount" name="thirdPartyMaxAmount" value="${thirdPartyMaxAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值次数</label>
								<div class="col-sm-3">
									<input type="number" class="form-control" id="thirdPartyTimes" name="thirdPartyTimes" value="${thirdPartyTimes }">
									<span class="help-block">0表示关闭</span>
								</div>
								<span class="help-block">次/每人每天</span>
							</div>
						</div>
					</div>
				</div>
				<h5 class="text-success">银行卡充值</h5>
				<div class="split"></div>
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="start_time" class="col-sm-3 control-label">起始时间</label>
								<div class="col-sm-3">
									<input type="text" class="form_datetime form-control"
										id="rechargeStartTime" name="rechargeStartTime" value="${rechargeStartTime }">
								</div>
								<span class="help-block">每天</span>
							</div>
							<div class="form-group">
								<label for="end_time" class="col-sm-3 control-label">截止时间</label>
								<div class="col-sm-3">
									<input type="text" class="form_datetime form-control"
										id="rechargeEndTime" name="rechargeEndTime" value="${rechargeEndTime }">
								</div>
								<span class="help-block">每天</span>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最小充值金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="rechargeMinAmount" name="rechargeMinAmount" value="${rechargeMinAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最大充值金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="rechargeMaxAmount" name="rechargeMaxAmount" value="${rechargeMaxAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">充值次数</label>
								<div class="col-sm-3">
									<input type="number" class="form-control" id="rechargeTimes" name="rechargeTimes" value="${rechargeTimes }">
									<span class="help-block">0表示关闭</span>
								</div>
								<span class="help-block">次/每人每天</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">提款配置</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class=" form-horizontal">
							<div class="form-group">
								<label for="start_time" class="col-sm-3 control-label">起始时间</label>
								<div class="col-sm-3">
									<input type="text" class="form_datetime form-control"
										id="drawingStartTime" name="drawingStartTime"  value="${drawingStartTime }">
								</div>
								<span class="help-block">每天</span>
							</div>
							<div class="form-group">
								<label for="end_time" class="col-sm-3 control-label">截止时间</label>
								<div class="col-sm-3">
									<input type="text" class="form_datetime form-control"
										id="drawingEndTime" name="drawingEndTime"  value="${drawingEndTime }">
								</div>
								<span class="help-block">每天</span>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最小提款金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="drawingMinAmount" name="drawingMinAmount" value="${drawingMinAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">最大提款金额</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" id="drawingMaxAmount" name="drawingMaxAmount" value="${drawingMaxAmount }">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">提款次数</label>
								<div class="col-sm-3">
									<input type="number" class="form-control" id="drawingTimes" name="drawingTimes" value="${drawingTimes }">
									<span class="help-block">0表示关闭</span>
								</div>
								<span class="help-block">次/每人每天</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label for="pwd" class="col-sm-2 control-label">财务密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="rolePwd" name="rolePwd">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-2">
									<input type="text" class="form-control pull-left " id="picCode" name="picCode" style="width: 100px;margin-right: 10px;">
								</div>
								<div class="col-sm-2">
									<img class="pull-left" id="pcode" src="${contextPath}/picode/ycode.do?key=financeConfigkey" width="100" height="35" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" class="btn btn-danger"
											onclick="return submitForm()">确定提交</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
	<script>
		$(".form_datetime").datetimepicker({
			format : 'hh:ii',
			showMeridian : true,
			autoclose : true,
			todayBtn : true,
			startView : 1,
			keyboardNavigation : false
		}).on("hide", function(ev) {

		});
	</script>
</body>
</html>
