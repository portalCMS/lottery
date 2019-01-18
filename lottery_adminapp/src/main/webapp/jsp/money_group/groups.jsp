<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖金组列表</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/monery_group/groups.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">奖金组管理</div>
			<div class="panel-body">
				<a href="showcreatebonus.do" class="btn btn-primary">新增奖金组</a>
			</div>
		</div>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr class="text-center success">
					<td>#</td>
					<td>别名</td>
					<td>添加时间</td>
					<td>参考理论返奖</td>
					<td>返奖率</td>
					<td>返点率</td>
					<td>利润率</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonusGroups}" var="bonus">
					<tr>
						<td>${bonus.id }</td>
						<td>${bonus.name }</td>
						<td><fmt:formatDate value="${bonus.createTime }"
								pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td><fmt:formatNumber value="2000" pattern="0,000.00" /></td>
						<td>${bonus.payoutRatio }</td>
						<td>${bonus.rebates }</td>
						<td>${bonus.margin }</td>
						<td>${bonus.remark }</td>
						<td><a href="javaScript:void(0)" id="${bonus.id }"
							class="btn btn-xs btn-link _edit_btn">修改</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>