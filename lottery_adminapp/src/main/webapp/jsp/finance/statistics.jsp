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
			<li><a href="index.do">首页</a></li>
			<li><a href="${contextPath }/showFinanceOrders.do">财务管理</a></li>
			<li class="active">盈亏报表</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">总代数据</div>
			<div class="panel-body">
				<label class="label label-danger">投注总额：123456.0000</label> <label
					class="label label-danger">返点总额：123456.0000</label> <label
					class="label label-danger">中奖总额：123456.0000</label> <label
					class="label label-success">总盈亏：123456.0000</label>
			</div>
		</div>

		<form class="form-inline" role="form">
			<div class="form-group">
				<input size="16" type="text" class="form_datetime form-control">
			</div>
			<div class="form-group">至</div>
			<div class="form-group">
				<input size="16" type="text" value=""
					class="form_datetime form-control">
			</div>

			<div class="form-group">
				<input type="text" placeholder="用户名" class="form-control" />
			</div>
			<button type="submit" class="btn btn-default">查询</button>
		</form>
		<br />

		<table
			class="table table-hover table-bordered table-striped table-condensed">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>用户名</td>
					<td>投注金额</td>
					<td>返点金额</td>
					<td>中奖金额</td>
					<td>结算</td>
					<td width="100">操作</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td><a href="#">张三</a></td>
					<td class="text-success">50000.0000</td>
					<td>50000.0000</td>
					<td class="text-danger">50000.0000</td>
					<td class="text-danger"><label class="label label-danger">亏</label>&nbsp;50000.0000</td>
					<td width="100"><a href="#" class="btn btn-link btn-sm">充值分红</a></td>
				</tr>
				<tr>
					<td>1</td>
					<td><a href="#">张三</a></td>
					<td class="text-success">50000.0000</td>
					<td>50000.0000</td>
					<td class="text-danger">50000.0000</td>
					<td class="text-success"><label class="label label-success">赢</label>&nbsp;50000.0000</td>
					<td width="100"><a href="#" class="btn btn-link btn-sm">充值分红</a></td>
				</tr>
				<tr>
					<td>1</td>
					<td><a href="#">张三</a></td>
					<td class="text-success">50000.0000</td>
					<td>50000.0000</td>
					<td class="text-danger">50000.0000</td>
					<td class="text-danger"><label class="label label-danger">亏</label>&nbsp;50000.0000</td>
					<td width="100"><a href="#" class="btn btn-link btn-sm">充值分红</a></td>
				</tr>
				<tr>
					<td>1</td>
					<td><a href="#">张三</a></td>
					<td class="text-success">50000.0000</td>
					<td>50000.0000</td>
					<td class="text-danger">50000.0000</td>
					<td class="text-success"><label class="label label-success">赢</label>&nbsp;50000.0000</td>
					<td width="100"><a href="#" class="btn btn-link btn-sm">充值分红</a></td>
				</tr>
			</tbody>
		</table>
		<ul class="pagination clear_margin_bottom pagination-sm">
			<li class="disabled"><a href="#">&laquo;</a></li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">&raquo;</a></li>
		</ul>
	</div>
	<script type="text/javascript">
		$(".form_datetime").datetimepicker({
			format : 'yyyy-m-d h:i',
			showMeridian : true,
			autoclose : true,
			todayBtn : true
		}).on("hide", function(ev) {
			var t1 = $(".form_datetime").eq(0).val();
			var t2 = $(".form_datetime").eq(1).val();
			if (contrastTime(t1, t2)) {
				$(".form_datetime").eq(1).val(getNowTime(0, 0));
			}
		});
		$(document).ready(function() {
			$(".form_datetime").eq(0).val(getNowTime(0, -7));
			$(".form_datetime").eq(1).val(getNowTime(0, 0));
		});
	</script>
</body>
</html>
