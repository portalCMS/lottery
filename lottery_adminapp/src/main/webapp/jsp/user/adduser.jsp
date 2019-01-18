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
<script src="${contextPath }/jsp/js/user/adduser.js"></script>
<script>
	$(document).ready(function() {
		$("#pcode").attr("src", "${contextPath }/picode/ycode.do?key=addbankkey" );
		$("#pcode").attr("title", "点击刷新");
		$("#pcode").bind("click", function() {
			$("#pcode").attr("src", "${contextPath }/picode/ycode.do?key=addbankkey&&random="+Math.random());
		});

	});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showUserManage.do">用户管理</a></li>
			<li class="active">添加总代</li>
		</ol>
	<form  action="addmainuser.do" method="post" id="addUserForm"> 
		<div class="panel panel-info">
			<div class="panel-heading">基本信息</div>
			<div class="panel-body">
				<div class="form-horizontal" role="form">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="uname" class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="customerName" name="customerName"
										placeholder="用户名">
								</div>
							</div>
							<div class="form-group">
								<label for="pwd" class="col-sm-2 control-label">随机密码</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="pwd"
										value="${randomPwd }" disabled> <span
										class="help-block">密码自动生成，第一次登陆时会进入修改密码引导流程。</span>
										<input type="hidden" name="customerPwd" value="${randomPwd }" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-heading">配额信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="point" class="col-sm-2 control-label">奖金组</label>
								<div class="col-sm-6">
									<select class="form-control" id="point" name="point">
										<c:forEach items="${bglist }" var="bg">
											<option value="${bg.id },${bg.rebates}">${bg.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="dc" class="col-sm-2 control-label">最小点差</label>
								<div class="col-sm-6">
									<input type="text" value="${stepPoint }" class="form-control"
										id="sPoint"> <input type="hidden" name="ysPoint"
										id="ysPoint" value="${stepPoint }"> <input
										type="hidden" name="stepPoint" id="stepPoint"
										value="${stepPoint }"> <input type="hidden"
										name="minPoint" id="minPoint" value="${minPoint }"> <span
										class="help-block">配额范围最小值${minPoint }</span>
								</div>
							</div>
							<div class="form-group">
								<label for="dc" class="col-sm-2 control-label">起始返点</label>
								<div class="col-sm-3">
									<input type="text" value="" class="form-control" id="startQuota" name="startQuota">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-2 control-label">配额分配</label>
								<div class="col-sm-10 form-group">
									<div class="form-inline " id="quota"></div>
								</div>
								<input type="hidden" name="quotas" id="quotas" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class='pull-left'>银行卡信息&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick="showBankCard()" class='btn btn-danger btn-xs'>查看已选择银行卡(<span id="cbs">0</span>张)</a></div>
				<div class='pull-right text-danger'>查询条件均为大于等于</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-horizontal form-inline">
							<div class="form-group ">
								<label for="bank" class="col-sm-4 control-label">银行</label>
								<div class="col-sm-8">
									<select class="form-control" id="bank">
										<option value="0">全部</option>
										<c:forEach items="${banks }" var="bank">
											<option value="${bank.id }">${bank.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="fprs" class="col-sm-5 control-label">已分配人数</label>
								<div class="col-sm-7">
									<input type="number" class="form-control" id="fprs" value="">
								</div>
							</div>
							<div class="form-group ">
								<label for="czcs" class="col-sm-5 control-label">充值次数</label>
								<div class="col-sm-7">
									<input type="number" class="form-control" id="czcs" value="">
								</div>
							</div>
							<div class="form-group ">
								<label for="ljje" class="col-sm-5 control-label">累计金额</label>
								<div class="col-sm-7">
									<input type="number" class="form-control" id="ljje" value="">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-1 col-sm-10">
									<button type="button" class="btn btn-info" onclick="findBanks()">查询银行卡</button>
								</div>
							</div>
						</div>
						<br />
						<table
							class="table table-hover table-bordered table-striped table-condensed">
							<thead>
								<tr class="success text-center">
									<td class=''>#</td>
									<td>更新时间</td>
									<td>银行</td>
									<td>姓名</td>
									<td>卡号</td>
									<td>充值次数</td>
									<td>累计金额</td>
									<td>卡等级</td>
									<td>卡库名称</td>
									<td>已分配人数</td>
									<td class='text-left'><label class='checkbox-inline'><input
											type='checkBox' id="banksAll" onclick="checkStatus(this)" >银行卡分配</label></td>
									<td class="tip" 
										>操作</td>
								</tr>
							</thead>
							<tbody id="banksBody">

							</tbody>
						</table>
						<input type="hidden" name="cards" id="cards" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-info">
					<div class="panel-heading">分配域名</div>
					<div class="panel-body">
						<c:forEach items="${urlList }" var="curl">
							<label class="checkbox-inline"> <input type="checkbox"
								id="curl" name="curl" value="${curl.id }"> ${curl.url }
							</label>
						</c:forEach>
						<input type="hidden" name="urls" id="urls" />
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
									<input type="password" class="form-control" id="adminPwd" name="adminPwd"
										placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-2">
									<input type="text" class="form-control " id="code" name="code"/>
									<input type="hidden" name="pickey" value="addbankkey" />
								</div>
								<div class="col-sm-2">
									<img
										src="${contextPath }/picode/ycode.do?key=addbankkey"
										width="100" height="35" id="pcode" name="pcode"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" id="openUser" class="btn btn-danger">确定开户</button>
									<c:if test="${proxyuserstatus == false }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" id="openRegUser" class="btn btn-danger">开设注册总代</button>
									</c:if>
									<button type="button" id="openTestUser" class="btn btn-danger">开设测试总代</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>

</body>
</html>
