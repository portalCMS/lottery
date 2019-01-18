<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='./include/asset_css.jsp'%>
<%@ include file='./include/asset_js.jsp'%>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${param.errorMsg}' != "") {
			$("#error_content").html(decodeURI("${param.errorMsg}"));
			$("#error_msg_modal").modal("show");
		}

		if ('${param.success}' != "") {
			$("#error_content").html(decodeURI("${param.success}"));
			$("#error_msg_modal").modal("show");
		}
	});
</script>
</head>
<body>
	<%@ include file="./include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showSaveLoginPwd.do">邀请注册</a></li>
			<li><a href="${contextPath }/user/showUserRecharge.do">用户充值</a></li>
			<li><a href="${contextPath }/user/showUserBindCardDrawing.do">提款申请</a></li>
			<li><a href="${contextPath }/user/showOpenLower.do">开设下级</a></li>
			<li><a href="${contextPath }/user/showQuotaManage.do">配额管理</a></li>
			<li><a href="${contextPath }/user/showMoveMoney.do">资金转移</a></li>
			<li><a href="${contextPath }/user/showUserData.do">个人资料</a></li>
			<li><a href="${contextPath }/user/showAccountSafe.do">账户安全</a></li>
		</ol>
	</div>
	<form>
		<div class="panel panel-info">
			<div class="panel-heading">基本信息</div>
		</div>
	</form>
</body>
</html>