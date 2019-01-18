<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>用户IP</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/user/ip.js"></script>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showUserManage.do">用户管理</a></li>
			<li class="active">用户IP</li>
		</ol>
		<div class="panel-default">
			<div class="panel-body">
				<form action="showFrozenUsers.do" method="post">
					<div class="form-inline">
						<div class="form-group">&nbsp;&nbsp;&nbsp;&nbsp;用户名:</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="用户名"
								id="uname" name="uname"
								value="" />
						</div>
						<div class="form-group">&nbsp;&nbsp;&nbsp;IP地址:</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="IP地址"
								id="ip" name="ip"
								value="" />
						</div>
						<div class="form-group">&nbsp;&nbsp;序列号1:</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="序列号1"
								id="cpuid" name="cpuid"
								value="" />
						</div>
						<div class="form-group">序列号2:</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="序列号2"
								id="diskid" name="diskid"
								value="" />
						</div>
						<br/><br/>
						<div class="form-inline">
							<div class="form-group">开户姓名:</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="开户姓名"
									id="openCardName" name="openCardName"
									value="" />
							</div>
							<div class="form-group">银行卡号:</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="银行卡号"
									id="cardNo" name="cardNo"
									value="" />
							</div>
							<div class="form-group" style="margin-left:10px;">
								<a href="javascript:void(0);" class="btn btn-primary _queryBtn" style="width: 100px;">查询</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<table
			class="table table-hover table-bordered table-striped table-condensed">
			<thead>
				<tr class="success" >
					<td width="5" align="center">#</td>
					<td width="50" align="center">用户名</td>
					<td width="50" align="center">IP地址</td>
					<td width="80" align="center">登录时间</td>
					<td width="100" align="center">具体地址</td>
					<td width="40" align="center">序列号1</td>
					<td width="40" align="center">序列号2</td>
					<td width="60" align="center">开户姓名</td>
					<td width="60" align="center">银行卡号</td>
					<td width="125" align="center">操作</td>
				</tr>
			</thead>
			<tbody id="showbody">
			</tbody>
			<tr class="_hidTr" style="display: none;">
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center">
						<a class="_killUser" href="javascript:;">踢出平台 <input type="hidden" class="userId"/></a>
						<a href="javascript:;" class="showFileName">查看文件名<input type="hidden" class="rsvst3"/></a>
					</td>
			</tr>
		</table>
		<ul class="pager _pageInfo">
			<li><a href="#">上一页</a></li>
			<li><a href="#">下一页</a></li>
		</ul>
	</div>
	<div class="modal fade" id="view_model">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">x</span>
					</button>
					<h4 class="modal-title">查看文件名</h4>
				</div>
				<div class="modal-body">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default " data-dismiss="modal">
						关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
