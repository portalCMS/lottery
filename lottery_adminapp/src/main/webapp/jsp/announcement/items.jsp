<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告列表</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/article/items.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">公告管理</div>
			<div class="panel-body">
				<a href="${contextPath }/article/initCreateNotice.do" class="btn btn-primary">添加公告</a> 
				<a href="showNoticeSort.do" class="btn btn-primary">分类管理</a>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-inline clearfix">
					<div class="form-group ">
						<label class="control-label">筛选：</label> <label
							class="checkbox-inline"> <input type="checkbox" id="topMark"/> 加精
						</label> <label class="checkbox-inline"> <input type="checkbox" id="keyMark"/>
							置顶
						</label>
					</div>
					<div class="form-group ">
						<label class="control-label">分类：</label> 
						<select class="form-control" id="detailType">
							<option value="">全部分类</option>
							<c:forEach items="${sorts }" var="sort">
								<option value="${sort.detailType }" class="_${sort.detailType }">${sort.datailName }</option>
							</c:forEach>
						</select>
					</div>
					<button type="button" class="btn btn-primary pull-right _queryBtn">
						立即查询</button>
				</div>
			</div>
		</div>
		<table
			class="table table-bordered table-condensed table-container table-hover table-striped">
			<thead>
				<tr class="text-center success">
					<td>序号</td>
					<td>发布时间</td>
					<td>标题</td>
					<td>分类</td>
					<td>属性</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="myTbody">
			</tbody>
			<tr class="_hidTr" style="display:none;">
				<td  align="center" valign="middle" class="_index"></td>
				<td  align="center" valign="middle" class="_createTime"></td>
				<td  align="center" valign="middle" class="_title"></td>
				<td  align="center" valign="middle" class="_detailName"></td>
				<td  align="center" valign="middle" class="_attr">
					<span class="label label-danger _topMark" style="display:none">置顶&nbsp;</span>
					<span class="label label-danger _keyMark" style="display:none">加精</span>
				</td>
				<td align="center" valign="middle" class="_status">
						<span class="label label-success" style="display:none">显示</span>
						<span class="label label-success" style="display:none">隐藏</span>
				</td>
				<td align="center" valign="middle" class="_opera">
					<input type="hidden" value=""/>
					<a href="javascript:;" class="btn btn-link btn-sm _updArt">修改</a>
					<a href="javascript:;" class="btn btn-link btn-sm _hidArt">隐藏</a>
					<a href="javascript:;" class="btn btn-link btn-sm _delArt">删除</a>
				</td>
			</tr>
		</table>
		<ul class="pager _pageInfo">
			<li><a href="javascript:;" class="_prePage">上一页</a></li>
			<li><a href="javascript:;" class="_nextPage">下一页</a></li>
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
						是否删除此条目：&nbsp;&nbsp;<span class="text-danger _dtitle">文章标题假字请勿阅读</span>
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