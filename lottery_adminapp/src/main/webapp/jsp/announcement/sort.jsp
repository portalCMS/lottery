<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告分类</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.html">首页</a></li>
			<li><a href="orders.html">公告管理</a></li>
			<li class="active ">分类管理</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group clear_margin_bottom">
						<div class="col-sm-3">
							<input type="text" class="form-control" />
						</div>
						<a href="#" class="btn btn-primary">添加分类</a>
					</div>
				</div>
			</div>
		</div>
		<table
			class="table table-bordered table-condensed table-container table-hover table-responsive">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>添加时间</td>
					<td>分类名称</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>2014-08-09 20:46:11</td>
					<td>分类名称假字请勿阅读</td>
					<td><a href="#" class="btn btn-link btn-sm _edit_btn">修改</a><a
						href="#" class="btn btn-link btn-sm _del_btn">删除</a></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="modal fade" id="edit_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改分类名称</h4>
				</div>
				<div class="modal-body">
					<p>
						<input type="text" class="form-control " value="Sort Name~~~" />
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						Close</button>
					<button type="button" class="btn btn-primary">确定修改</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="del_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">删除分类</h4>
				</div>
				<div class="modal-body">
					<p>&nbsp; &nbsp; &nbsp;删除该分类，并将此分类的公告移至：</p>
					<div class="col-sm-6">
						<select class="form-control">
							<option>分类名称假字请勿阅读</option>
						</select>
					</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						Close</button>
					<button type="button" class="btn btn-primary">确定删除</button>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$("._edit_btn").click(function() {
				$("#edit_modal").modal("show");
			});
			$("._del_btn").click(function() {
				$("#del_modal").modal("show");
			});
		});
	</script>
</body>
</html>