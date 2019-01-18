<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助中心</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/helpcenter/items.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">帮助中心</div>
			<div class="panel-body clearfix">
				<div class="pull-left">
					<a href="initCreateHelpCenter.do" class="btn btn-primary">新增条目</a> <a
						href="showHelpCenterSort.do" class="btn btn-primary">分类管理</a>
				</div>
				<div class="pull-right">
					<a href="javaScript:void(0);" class="btn btn-default">帮助首页</a>
				</div>
			</div>
		</div>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-1">分类：</label>
				<div class="col-sm-2">
					<select class="form-control" id="detailType">
						<c:forEach items="${cslist }" var="cs">
							<option value="${cs.detailType }" <c:if test="${vo.detailType == cs.detailType }">selected</c:if> >${cs.datailName}</option>
						</c:forEach>
					</select>
				</div>
				<!-- 
				<label class="control-label col-sm-1">筛选：</label>
					<div class="col-sm-2">
						<select class="form-control">
							<option>无</option>
							<option>有用数最高</option>
							<option>无用数最高</option>
						</select>
					</div>
				-->
				<label class="control-label col-sm-1"></label>
				<div class="col-sm-2"></div>
				<div class="col-sm-6 text-right">
					<a href="javaScript:void(0);" class="btn btn-primary _queryBtn">查询条目</a>
				</div>
			</div>

		</div>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>更新时间</td>
					<td>分类</td>
					<td>问题</td>
					<!-- <td>前台链接</td>-->
					<td>有用</td>
					<td>无用</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${helpcenters.pagelist}" var="help">
				<tr>
					<td>${help.id }</td>
					<td><fmt:formatDate value="${help.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${help.detailTypeName }</td>
					<td>${help.title }</td>
					<!--<td>${help.url }</td>-->
					<td><label class="label label-info">${help.applaud }</label></td>
					<td><label class="label label-default">${help.oppose }</label></td>
					<td>
						<span class="text-success">
							<c:if test="${help.status == 10002 }">显示</c:if>
							<c:if test="${help.status == 10001 }">隐藏</c:if> 
						</span>
					</td>
					<td class="text-center">
						<a href="javascript:void(0);" id="${help.id }" class="btn btn-xs btn-link _showUpdate">编辑</a>
						<a href="javascript:void(0);" id="${help.id }" name="${help.status == 10002 }" class="btn btn-xs btn-link _hidArt">
							<c:if test="${help.status == 10001 }">显示</c:if>
							<c:if test="${help.status == 10002 }">隐藏</c:if> 
						</a>
						<a href="javascript:void(0);" id="${help.id }" class="btn btn-xs btn-link _delArt">删除</a></td>
					</tr>
				</c:forEach>
				<tr style="display: none;">
					<td>1</td>
					<td>2014-08-07 14:59:38</td>
					<td>财务</td>
					<td><a href="#">问题假字请勿阅读</a></td>
					<td>http://127.0.0.1:8020/admin/cms/index.html</td>
					<td><label class="label label-info">100</label></td>
					<td><label class="label label-default">1</label></td>
					<td><span class="text-success">显示</span></td>
					<td class="text-center"><a href="#"
						class="btn btn-xs btn-link">编辑</a><a href="#"
						class="btn btn-xs btn-link _hide_btn">隐藏</a><a href="#"
						class="btn btn-xs btn-link delete_btn">删除</a></td>
				</tr>

			</tbody>
		</table>
		<ul class="pager">
			<li><a href="javaScript:void(0);" onclick="noticeItems.queryData(${helpcenters.pageNum-1 })" class="_prePage">上一页</a></li>
			第${helpcenters.pageNum}页/共${helpcenters.pageCount }页
			<li><a href="javaScript:void(0);" onclick="noticeItems.queryData(${helpcenters.pageNum+1 })" class="_nextPage">下一页</a></li>
		</ul>
	</div>
	
	<div class="modal fade " id="delete_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">删除条目</h4>
				</div>
				<div class="modal-body">
					<p>
						是否删除此条目：&nbsp;&nbsp;<span class="text-danger _title">文章标题假字请勿阅读</span>
						&nbsp;?
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						返回</button>
					<button type="button" class="btn btn-primary _didBtn">确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>