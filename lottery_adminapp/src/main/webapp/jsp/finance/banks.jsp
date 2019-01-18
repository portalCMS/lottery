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
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行卡管理</a></li>
			<li class="active">银行管理</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">
				银行管理系统为财务银行卡基础系统，请勿随意更改，以免大范围影响银行卡分配情况</div>
			<div class="panel-body">
				<a href="showAddBank.do" class="btn btn-warning">添加新银行</a>
			</div>
		</div>

		<table class="table table-hover table-bordered table-striped table-condensed clear_margin_bottom">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>绑定时间</td>
					<td>银行名称</td>
					<td>已绑定银行卡</td>
					<td>状态</td>
					<td>权限控制</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pagelist.entitylist }" var="bank" >
					<tr>
						<td>${bank.id }</td>
						<td>${bank.updateTime }</td>
						<td>${bank.name }</td>
						<td><label class="label label-info">50张</label></td>
						<c:if test="${bank.status == 10002 }">
							<td class="text-success">正常</td>
						</c:if>
						<c:if test="${bank.status == 10001 }">
							<td class="text-success">冻结</td>
						</c:if>
						<td>
						<c:if test="${bank.add == 1 }">
							<label class="label label-danger">充值</label> 
						</c:if>
						<c:if test="${bank.out == 1 }">
							<label class="label label-danger">提款</label> 
						</c:if>
						<c:if test="${bank.bind == 1 }">
							<label class="label label-danger">绑卡</label></td>
						</c:if>
						<td>${bank.describe }</td>
						<td><a href="showUpdateBank.do?id=${bank.id }" class="btn btn-link btn-sm">修改银行卡信息</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
		  <li><a href="showBanks.do?pageNum=${(pagelist.pageNum-1)<=0?1:(pagelist.pageNum-1) }">上一页</a></li>
		  <li>第${pagelist.pageNum }页/共${pagelist.pageCount }页</li>
		  <li><a href="showBanks.do?pageNum=${(pagelist.pageNum)>=pagelist.pageCount?pagelist.pageCount:(pagelist.pageNum+1) }">下一页</a></li>
		</ul>
	</div>

</body>
</html>
