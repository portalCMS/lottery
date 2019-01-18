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
			<li><a href="index.jsp">首页</a></li>
			<li><a href="orders.jsp">财务管理</a></li>
			<li class="active">后台充提</li>
		</ol>
		<form action="changeCustomerMoney.do" method="post">
		<input type="hidden" name="token" id="token" value="${token }">
		<div class="panel panel-default">
			<div class="panel-heading">请谨慎操作</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs">
							<li class="active" name="changeform"><a href="#profile" data-toggle="tab">资金转移</a>
							</li>
						</ul>
						<br />
						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane active" id="profile">
								<form class="form-horizontal" role="form" id="fundmove">
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-sm-2 control-label">转入用户</label>
											<div class="col-sm-5">
												<select class="form-control" id="cuId" name="cuId">
													<c:forEach items="${lowerusers }" var="user">
														<option value="${user.id }">${user.customerName }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">转移金额</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="cash" name="cash" />
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
								<label class="col-sm-2 control-label">管理密码</label>
								<div class="col-sm-4">
									<input type="password" class="form-control " name="userpwd" id="userpwd" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-danger" onclick="return orderSubmit()">确认提交</button>
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
		$(document).ready(function() {
			$(".check_user_btn").click(function() {
				checkUsetName();
			});
		});
	</script>

</body>
</html>
