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
<script src="${contextPath }/jsp/js/user/config.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container _config">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showUserManage.do">用户管理</a></li>
			<li class="active">基础配置</li>
		</ol>
			<div class="panel panel-info">
				<div class="panel-heading">配额相关</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-horizontal">
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">配额范围下限</label>
									<div class="col-sm-3">
										<input type="text" class="form-control checkedAttr" id="minPoint" value="${minPoint}"
										 checkType="isNotEmpty,decmal1" alt="配额下限不能为空！,配额下限必须为正浮点数！"/>
									</div>
									<span class="help-block">超过下限范围的返点无需配额即可开户</span>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">起始点差</label>
									<div class="col-sm-3">
										<input type="text" class="form-control checkedAttr" id="stepPoint" value="${stepPoint}"
										 checkType="isNotEmpty,decmal1" alt="起始点差不能为空！,起始点差必须为正浮点数！">
									</div>
									<span class="help-block">总代开户时点差计算起始值，步进0.1计算</span>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
			
		<div class="panel panel-default " >
				<div class="panel-heading clearfix">
					<span class="pull-left"> 用户等级配置 </span>
					<span class="pull-right"> 用户以<span class="text-danger">【历史累计投注量】</span>维度进行划分 </span>
				</div>
				<div class="panel-body">
					<div class="form-horizontal _userLeaveSetting">
						<div class="form-group _levelDiv">
							<label class="control-label col-sm-1"><span class="_ul">1</span>级用户</label>
							<label class="control-label col-sm-1"> <span class="_mina">0</span> - </label>
							<div class="col-sm-2">
								<input type="text" class="form-control checkedAttr _maxa" placeholder="累计投注量" 
								checkType="isNotEmpty,decmal1" alt="累计最大投注量不能为空！,累计最大投注量必须为正浮点数！"/>
							</div>
							<span class="help-block col-sm-1">之间</span>
							<div class="col-sm-2">
								<select class="form-control _in" >
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
							</div>
							<div class="col-sm-2">
								<select class="form-control _ls">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
							</div>
						</div>
						<div class="form-group _levelDiv">
							<label class="control-label col-sm-1"><span class="_ul">2</span>级用户</label>
							<label class="control-label col-sm-1"> <span class="_mina">0</span> - </label>
							<div class="col-sm-2">
								<input type="text" class="form-control checkedAttr _maxa" placeholder="累计投注量" 
								checkType="isNotEmpty,decmal1" alt="累计最大投注量不能为空！,累计最大投注量必须为正浮点数！"/>
							</div>
							<span class="help-block col-sm-1">之间</span>
							<div class="col-sm-2">
								<select class="form-control _in" >
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
							</div>
							<div class="col-sm-2">
								<select class="form-control _ls">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
							</div>
						</div>
						<div class="form-group _levelDiv">
							<label class="control-label col-sm-1"><span class="_ul">3</span>级用户</label>
							<label class="control-label col-sm-1"> <span class="_mina">0</span> - </label>
							<div class="col-sm-2">
								<input type="text" class="form-control checkedAttr _maxa" placeholder="累计投注量" 
								checkType="isNotEmpty,decmal1" alt="累计最大投注量不能为空！,累计最大投注量必须为正浮点数！"/>
							</div>
							<span class="help-block col-sm-1">之间</span>
							<div class="col-sm-2">
								<select class="form-control _in" >
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
							</div>
							<div class="col-sm-2">
								<select class="form-control _ls">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
							</div>
						</div>
						<div class="form-group _levelDiv">
							<label class="control-label col-sm-1"><span class="_ul">4</span>级用户</label>
							<label class="control-label col-sm-1"> <span class="_mina">0</span> - </label>
							<div class="col-sm-2">
								<input type="text" class="form-control checkedAttr _maxa" placeholder="累计投注量" 
								checkType="isNotEmpty,decmal1" alt="累计最大投注量不能为空！,累计最大投注量必须为正浮点数！"/>
							</div>
							<span class="help-block col-sm-1">之间</span>
							<div class="col-sm-2">
								<select class="form-control _in" >
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
							</div>
							<div class="col-sm-2">
								<select class="form-control _ls">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
							</div>
						</div>
						<div class="form-group _levelDiv">
							<label class="control-label col-sm-1"><span class="_ul">5</span>级用户</label>
							<label class="control-label col-sm-1"> <span class="_mina">0</span> - </label>
							<div class="col-sm-2 help-block">
								及以上
								<input type="hidden" class="_maxa" value="999999999999"/>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<select class="form-control _in" >
									<c:forEach items="${inventorys }" var="inventory">
										<option value="${inventory.id }">${inventory.inventoryName }</option>
									</c:forEach>
									</select>
							</div>
							<div class="col-sm-2">
								<select class="form-control _ls">
										<option value="1">1级卡</option>
										<option value="2">2级卡</option>
										<option value="3">3级卡</option>
										<option value="4">4级卡</option>
										<option value="5">5级卡</option>
									</select>
							</div>
						</div>
					</div>
					<p style="color:red;">
						请确保每一个总代都继承绑定一套等级完整的卡，以保证用户在充值时均可分配到相应的银行卡。
					</p>
				</div>
			</div>
			
			<div class="panel panel-info">
				<div class="panel-heading">ws地址</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-horizontal">
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">前台WS地址</label>
									<div class="col-sm-6">
										<input type="text" class="form-control" id="webws" value="${webws }"/>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">JOBWS地址</label>
									<div class="col-sm-6">
										<input type="text" class="form-control" id="jobws" value="${jobws }">
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6 ">

							<div class="form-horizontal" role="form">
								<div class="form-group">
									<label for="adminpwd" class="col-sm-2 control-label">管理密码</label>
									<div class="col-sm-6">
										<input type="password" class="form-control checkedAttr" id="rolePwd"
										checkType="isNotEmpty" alt="管理密码不能为空！" name="rolePwd" placeholder="Password">
									</div>
								</div>
								<div class="form-group">
									<label for="code" class="col-sm-2 control-label">验证码</label>
									<div class="col-sm-3">
										<input type="text" class="form-control pull-left checkedAttr" 
										checkType="isNotEmpty" alt="验证码不能为空！"
										id="picCode" name="picCode" style="width: 100px;margin-right: 10px;">
									</div>
									<div class="col-sm-5 col-sm-pull-0">
										<img class="pull-left" id="pcode" src="${contextPath}/picode/ycode.do?key=configkey" width="100" height="35" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="button" class="btn btn-danger"
											onclick="userConfig.submitForm()">确定提交</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
</body>
</html>
