<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>冻结列表</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showUserManage.do">用户管理</a></li>
			<li class="active">冻结列表</li>
		</ol>
		<div class="panel-default">
			<div class="panel-body">
				<form action="showFrozenUsers.do" method="post">
					<div class="form-inline">
						<div class="form-group">冻结时间:</div>
						<div class="form-group">
							<input type="text" class="form-control recharge_time"
								id="frozenBeginTime" name="frozenBeginTime"
								value="${vo.frozenBeginTime }" />
						</div>
						至
						<div class="form-group">
							<input type="text" class="form-control recharge_time"
								id="frozenEndTime" name="frozenEndTime"
								value="${vo.frozenEndTime }" />
						</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="用户名"
								id="customerName" name="customerName"
								value="${vo.customerName }" />
						</div>
						<div class="form-group">
							<button class="btn btn-primary" type="submit">查询</button>
							<!-- 
					<a href="javascript:void(0)"
						class="btn btn-primary">查询</a>-->
						</div>
					</div>
				</form>
			</div>
		</div>
		<table
			class="table table-hover table-bordered table-striped table-condensed">
			<thead>
				<tr class="success">
					<td>#</td>
					<td>用户名</td>
					<td>用户类型</td>
					<td>下线人数</td>
					<td>注册时间</td>
					<td>冻结时间</td>
					<td>账户余额</td>
					<td>冻结余额</td>
					<td width="100">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.entitylist }" var="frozen">
					<tr>
						<td>${frozen.userId }</td>
						<td>${frozen.customerName }</td>
						<td><c:if test="${frozen.customerLevel==0 }">总代</c:if> <c:if
								test="${frozen.customerLevel>0 }">${frozen.customerLevel}级代理</c:if>
						</td>
						<td class="text-danger">${frozen.lowercount }</td>
						<td class="text-muted"><fmt:formatDate
								value="${frozen.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td class="text-muted"><fmt:formatDate
								value="${frozen.updateTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td class="text-danger">${frozen.cash }</td>
						<td class="text-danger">${frozen.frozenCash }</td>
						<td><a href="showUserInfo/${frozen.userId }.do"
							class="btn btn-link btn-sm">查看详情</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
			<li><a
				href="showFrozenUsers.do?pageNum=${(page.pageNum-1)<=0?1:(page.pageNum-1) }">上一页</a></li>
			<li>第${page.pageNum }页/共${page.pageCount }页</li>
			<li><a
				href="showFrozenUsers.do?pageNum=${(page.pageNum)>=page.pageCount?page.pageCount:(page.pageNum+1) }">下一页</a></li>
		</ul>
	</div>

</body>
<script>
	$(document).ready(function() {
		if ($(".recharge_time").eq(0).val() == "") {
			$(".recharge_time").eq(0).val(getNowTime(0, -7));
			$(".recharge_time").eq(1).val(getNowTime(0, 0));
		}
		$(".recharge_time").bind("click", function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd HH:mm:ss'
			});
		});
	});
</script>
</html>
