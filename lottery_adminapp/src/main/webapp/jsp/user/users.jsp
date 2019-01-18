<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>users</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/user/users.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			if('${errorMsg }'!=""){
				$("#error_msg_val").html("${errorMsg }");
				$("#error_msg").show();
			}
		});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				用户总数：<label class="label label-danger">${usercount }</label>&nbsp;&nbsp;&nbsp;&nbsp;
				代理总数：<label class="label label-danger">${proxycount }</label>&nbsp;&nbsp;&nbsp;&nbsp;
				会员总数：<label class="label label-danger">${membercount }</label>
			</div>
			<div class="panel-body">
				<a href="${contextPath }/user/showAddMainUser.do"
					class="btn btn-warning">添加总代</a>
			    <a href="${contextPath }/user/initUserBaseConfig.do"
					class="btn btn-warning">用户配置</a> 
				<a href="${contextPath }/user/showcustomerlist.do"
					class="btn btn-warning">域名管理</a> 
				<a href="${contextPath }/user/showFrozenUsers.do"
					class="btn btn-warning">冻结列表</a>
				<a href="${contextPath }/user/showuserip.do"
					class="btn btn-warning">IP查询</a>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				用户信息
			</div>
			<div class="panel-body">
				<div class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control" id="userName"
							name="customerName" placeholder="用户名">
					</div>
					<button type="button" class="btn btn-default" id="userQuery">查询</button>
	<!-- 				<button type="submit" class="btn btn-primary">查询详细信息</button> -->
					<button type="button" class="btn btn-danger pull-right" id="restPwd">登录密码重置</button>
				</div>
			<br/>
			<table class="table table-hover table-bordered table-striped table-condensed">
				<thead class="text-center">
					<tr class="active">
						<td>id</td>
						<td>用户名</td>
						<td>昵称</td>
						<td>返点</td>
						<td>状态</td>
						<td>邮箱</td>
						<td>QQ</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="myTb">
				</tbody>
				<tr class="_hidTr" style="display:none;">
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"></td>
					<td align="center" valign="middle"><a href="javascript:;">用户详情</a></td>
				</tr>
			</table>
			
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				总代用户信息
			</div>
			<div class="panel-body">
			<form class="form-inline" role="form" action="showUserManage.do"
				method="post">
				<div class="form-group">
					<input type="text" class="form-control" id="customerName"
						name="customerName" placeholder="总代用户名"
						value="${voparams.customerName}">
				</div>
				<button type="submit" class="btn btn-default">查询</button>
			</form>
			<br />
			<table
			class="table table-hover table-bordered table-striped table-condensed">
			<thead class="text-center">
				<tr class="active">
					<td>#</td>
					<td>用户名</td>
					<td>已分配银行卡</td>
					<td>总代返点</td>
					<td>下线人数</td>
					<td>状态</td>
<!-- 					<td>本月投注金额</td> -->
					<td>奖金组名称</td>
					<td>奖金组返点</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainusers }" var="user">
					<tr>
						<td align="center" valign="middle">${user[0] }</td>
						<td align="center" valign="middle">${user[1] }</td>
						<td align="center" valign="middle">${user[2] }</td>
						<td align="center" valign="middle"><fmt:formatNumber value="${user[3] }" pattern="0.0%" />
						</td>
						<td align="center" valign="middle">${user[4] }</td>
						<c:if test="${user[5] == 10002}">
							<td align="center" valign="middle"><label class="label label-success">正常</label></td>
						</c:if>
						<c:if test="${user[5] == 10001}">
							<td align="center" valign="middle"><label class="label label-success">冻结</label></td>
						</c:if>
<!-- 						<td align="center" valign="middle"><span class="text-danger"> -->
<%-- 							<fmt:formatNumber value="${user[6] }" groupingUsed="true"/> --%>
<!-- 						</span></td> -->
						<td align="center" valign="middle">${user[6] }</td>
						<td align="center" valign="middle">
						<fmt:formatNumber value="${user[7] }" pattern="0.0%" /></td>
						<td align="center" valign="middle"><a href="showUserInfo/${user[0] }.do">查看详情</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
	</div>
	</div>
</body>
</html>
