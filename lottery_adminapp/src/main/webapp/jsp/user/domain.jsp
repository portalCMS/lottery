<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/user/domain.js"></script>

<script>
	$(document).ready(function() {
		$("#error_msg").hide();
		$(".change_btn").click(function() {
			$("#change_domain_users").modal("show");
		});
		$("#sub").click(function(){
			var url = $("#url").val();
			if(url == ""){
				$("#error_msg_val").html("URL不能为空");
				$("#error_msg").show();
				return;
			}
			urlFrom.submit();
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
			<li class="active">域名管理</li>
		</ol>
		<div class="well">
			<div class="alert alert-danger alert-dismissable" id='error_msg' >
				<button type="button" class="close alert_close_btn">
					&times;
				</button>
				<div id="error_msg_val">用户名或密码错误</div>
			</div>
			<form class="form-inline" role="form" action="addCustomerUrl.do" method="post" id="urlFrom"> 
				<div class="form-group">
					<input type="text" class="form-control"
						placeholder="www.domain.com" name="url" id="url">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="备注" name="remark" id="remark">
				</div>
				<button type="button" id="sub" class="btn btn-default">添加域名</button>
			</form>
		</div>
		<table
			class="table table-hover table-bordered table-striped table-condensed">
			<thead>
				<tr class="success">
					<td>#</td>
					<td>添加时间</td>
					<td>域名</td>
					<td>绑定人数</td>
					<td>备注</td>
					<td width="200">操作</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pagelist.pagelist }" var="url" varStatus="sta">
					<tr>
						<td>${url.id }</td>
						<td><fmt:formatDate value="${url.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>${url.url }</td>
						<td>${url.bindcount }</td>
						<td>${url.remark }</td>
						<td><!-- <a href="javascript:void(0);"
							class="btn btn-link btn-sm change_btn">转移绑定</a>--><a
							href="javascript:void(0);" onclick="comdelUrl('${url.id }','${url.url }')" class="btn btn-link btn-sm del_btn">删除域名</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
		  <li><a href="showcustomerlist.do?pageNum=${(pagelist.pageNum-1)<=0?1:(pagelist.pageNum-1) }">上一页</a></li>
		  <li>第${pagelist.pageNum }页/共${pagelist.pageCount }页</li>
		  <li><a href="showcustomerlist.do?pageNum=${(pagelist.pageNum)>=pagelist.pageCount?pagelist.pageCount:(pagelist.pageNum+1) }">下一页</a></li>
		</ul>
	</div>

	<div class="modal fade" id="change_domain_users">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">转移绑定</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-6">
							将域名：<span class="text-danger" id="change_domain_info">www.xxx.com</span>&nbsp;&nbsp;的用户转移至
						</div>
						<div class="col-sm-6 col-sm-pull-1">
							<select class="form-control">
								<option>www.aaaa.com</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-primary">确定转移</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div class="modal fade" id="del_domain">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除域名</h4>
				</div>
				<div class="modal-body">
					<p>
						您确定要删除：<span class="text-danger" id="del_domain_info">www.xxx.com</span>&nbsp;&nbsp;域名么？
					</p>
					<p>删除该域名有可能导致用户无绑定域名无法登录，请在删除前先转移用户绑定其他域名。</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-primary" id="delUrl" >确定删除</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>
