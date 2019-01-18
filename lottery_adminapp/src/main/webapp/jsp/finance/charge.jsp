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
<script src="${contextPath }/jsp/js/finance/charge.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active">后台充提</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">请谨慎操作</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs">
							<li class="active" name="changeform"><a href="#home" data-toggle="tab">单点充提</a></li>
							<li class="" name="changeform"><a href="#profile" data-toggle="tab">资金转移</a>
							</li>
						</ul>
						<br />
						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane active" id="home">
								<form class="form-horizontal" role="form" id="singleForm" method="post" action="addSingleOrder.do">
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">充提类型</label>
										<div class="col-sm-5">
											<select class="form-control" id="orderDetailType" name="orderDetailType">
												<option value="18011">后台充值</option>
												<option value="18008">后台扣款</option>
												<option value="18016">系统分红</option>
												<option value="18012">活动派发</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="page_type" class="col-sm-2 control-label">账户类型</label>
										<div class="col-sm-5">
											<select class="form-control" id="accountType" name="accountType">
												<option value="cash">现金账户</option>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="money" class="col-sm-2 control-label">充值金额</label>
										<div class="col-sm-5">
											<input type="number" class="form-control" id="receiveAmount" name="receiveAmount" />
										</div>
									</div>

									<div class="form-group">
										<label for="uname" class="col-sm-2 control-label">用户名</label>
										<div class="col-sm-5">
											<input type="text" class="form-control" id="customerName1" name="customerName"  onblur="checkUsetNameonblur()"/>
											<span class='help-block' id="money"></span>
										</div>
										<a href="javascript:void(0);"
											class="btn btn-warning btn-sm check_user_btn" id="">检查用户</a>
										<input type="hidden" name="userStatus1" id="userStatus1" >
									</div>

									<div class="form-group">
										<label for="remarks" class="col-sm-2 control-label">备注</label>
										<div class="col-sm-5">
											<input type="text" class="form-control" id="remark" name="remark"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">管理密码</label>
										<div class="col-sm-4">
											<input type="password" class="form-control " name="adminPwd" id="adminPwd1" />
										</div>
									</div>
								</form>
							</div>
							<div class="tab-pane" id="profile">
								<form class="form-horizontal" role="form" id="fundmove" action="moveMoneyTOLowerCustomer.do" method="post">
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-sm-2 control-label">账户类型</label>
											<div class="col-sm-5">
												<!-- 彩金账户不能转出金额  -->
												<select class="form-control">
													<option value="cash">现金账户</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">转出用户</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="customerNamefrom" name="customerName" onblur="checkUsetNameForm2onblur()"/>
												<input type="hidden" id="userMainId" name="userMainId" />
												<span class='help-block' id="outmoney"></span>
											</div>
											<a href="#" class="btn btn-sm btn-warning" onclick="checkUsetNameForm2()">检查用户</a>
											<input type="hidden" name="userStatus2" id="userStatus2" >
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">转入用户</label>
											<div class="col-sm-5">
												<input type='text' class='form-control' id="customerSunNamefrom" name="customerSunName" onblur="checkLowerCustomeronblur()"  />
												<input type="hidden" id="userSunId" name="userSunId" />
												<span class='help-block' id="inmoney"></span>
											</div>
											<a href="#" class="btn btn-sm btn-warning" onclick="checkLowerCustomer()">检查用户</a>
											<input type="hidden" name="userStatus3" id="userStatus3" >
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">转移金额</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="cash" name="cash" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">管理密码</label>
											<div class="col-sm-4">
												<input type="password" class="form-control " name="adminPwd" id="adminPwd2" />
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="split"></div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" class="btn btn-danger" onclick="return orderSubmit()">确认提交</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	
	
	<div class="modal fade" id="check_and_select_user_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">用户信息</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-horizontal" role="form">
								<div class="form-group">
									<label class="col-sm-3 control-label">用户名</label>
									<div class="col-sm-8">
										<p class="form-control-static" id="uname">
											张三&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" target="_blank"
												class="btn btn-link btn-sm">查看用户详情</a>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">用户类型</label>
									<div class="col-sm-8" >
										<p class="form-control-static" id="ulevel">一级代理</p>
									</div>
								</div>
								<div class="split "></div>
								<div class="form-group">
									<label class="col-sm-3 control-label">现金余额</label>
									<div class="col-sm-8">
										<p class="form-control-static text-danger" id="ucash">123456.0000</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">冻结金额</label>
									<div class="col-sm-8">
										<p class="form-control-static text-danger" id="frozencash">123456.0000</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				<div class="modal-footer clear_margin_top">
					<button type="button" class="btn btn-primary btn-block"
						data-dismiss="modal">确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	
	
	<script>
		$(document).ready(function() {
			$(".check_user_btn").click(function() {
				checkUsetName();
			});
			
		});
		
		
	</script>

</body>
</html>
