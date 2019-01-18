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
<script src="${contextPath }/jsp/js/finance/binding_card.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	init();
	$("#pcode").attr("title", "点击刷新");
	$("#pcode").bind("click", function() {
		$("#pcode").attr("src", "${contextPath}/picode/ycode.do?key=bindcardkey&&"+Math.random());
	});
});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行卡管理</a></li>
			<li class="active">绑定新银行卡</li>
		</ol>
		<div class="alert alert-danger alert-dismissable" id='error_msg'>
			<button type="button" class="close alert_close_btn">&times;
			</button>
			<div id="error_msg_val"></div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">新银行卡信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<form class="form-horizontal" role="form" id="bindCardForm" method="post"  action="saveBindCard.do">
							<div class="form-group">
								<label for="bank_list" class="col-sm-2 control-label">银行名称</label>
								<div class="col-sm-5">
									<select class="form-control" id="selectBank">
										<c:forEach items="${banks}" var="ara"> 
											<option value="${ara.id }">
												${ara.name }
											</option>
										</c:forEach>
									</select>
								</div>
								<input type="hidden"  id="bankId" name="bankId">
								<input type="hidden"  id="bankName" name="bankName">
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">支行名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="branceBankName" name="branceBankName">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">支行地址</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="bankcardAddress" name="bankcardAddress">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">开户名</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="opencardName" name="opencardName">
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">银行账号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="cardNo" name="cardNo">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">卡等级</label>
								<div class="col-sm-3">
									<select class="form-control" id="cardInventory">
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
									<input type="hidden" id="cardInventoryId" name="cardInventoryId"/>
								</div>
								<div class="col-sm-3">
									<select class="form-control" id="levelSelect">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
									<input type="hidden" id="cardLevel" name="cardLevel"/>
								</div>
							</div>
							
							<div class="form-group">
								<label for="bz" class="col-sm-2 control-label">备注</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="remark" name="remark">
								</div>
							</div>
							<div class="split"></div>
							<div class="form-group">
								<label for="pwd" class="col-sm-2 control-label">财务密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="rolePwd" name="rolePwd">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">验证码</label>
									<div class="col-sm-3">
										<input type="text" class="form-control pull-left " id="picCode" name="picCode" style="width: 100px;margin-right: 10px;">
									</div>
									<div class="col-sm-5 col-sm-pull-0">
										<img class="pull-left" id="pcode" src="${contextPath}/picode/ycode.do?key=bindcardkey" width="100" height="35" />
									</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
								<button type="button" class="btn btn-danger"
											onclick="return submitForm()">确定绑定</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
