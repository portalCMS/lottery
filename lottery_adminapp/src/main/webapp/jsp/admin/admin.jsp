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
			<li><a href="index.html">首页</a></li>
			<li><a href="admins.jsp">管理员</a></li>
			<li class="active">管理员详情</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-heading">管理员信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-1">用户名</label>
								<div class="col-sm-11">
									<p class="form-control-static">王二麻子</p>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-1">所属组</label>
								<div class="col-sm-11">
									<p class="form-control-static">财务组</p>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-1">角色</label>
								<div class="col-sm-11">
									<p class="form-control-static">出款</p>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-1">登录次数</label>
								<div class="col-sm-11">
									<p class="form-control-static">
										<label class="label label-danger">21次</label>&nbsp;&nbsp;<span
											class="text-muted">最后一次登录：2014-6-20 11:58:17</span>
									</p>
								</div>
							</div>
							<div class="split"></div>
							<div class="form-group">
								<label class="control-label col-sm-1">状态</label>
								<div class="col-sm-3">
									<label class="checkbox-inline"> <input type="checkbox"
										id="inlineCheckbox1" value="option1" checked="checked">
										正常
									</label> <label class="checkbox-inline"> <input type="checkbox"
										id="inlineCheckbox2" value="option2"> 冻结
									</label>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-1">重设密码</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-1">重复密码</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" />
								</div>
							</div>
							<div class="split"></div>
							<div class="form-group">
								<div class="col-sm-3 col-sm-offset-1">
									<button class="btn btn-primary ">提交保存</button>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">操作日志</div>
			<div class="panel-body">
				<table
					class="table table-hover table-condensed table-bordered table-striped">
					<thead>
						<tr class="text-center success">
							<td>#</td>
							<td>时间</td>
							<td>IP</td>
							<td>操作内容</td>
							<td>影响订单</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>2014-6-20 18:17:13</td>
							<td>192.168.1.1</td>
							<td>充值</td>
							<td><a href="#">12345QASDQ486</a></td>
						</tr>
					</tbody>
				</table>
				<ul class="pagination pagination-sm  " style="margin: 0px;">
					<li><a href="#">&laquo;</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>
