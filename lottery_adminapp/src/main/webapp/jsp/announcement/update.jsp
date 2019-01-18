<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增公告</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/js/article/update.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="initNotice.do">公告管理</a></li>
			<li class="active ">添加公告</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">公告信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">标题</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="title" value="${entity.title}"/>
							<input type="hidden" value="${entity.id }" id="id">
							<input type="hidden" value="${entity.content }" id="content">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">分类</label>
						<div class="col-sm-3">
							<select class="form-control" id="detailType">
								<c:forEach items="${sorts }" var="sort">
									<option value="${sort.detailType }" <c:if test="${sort.detailType == entity.detailType }">selected='true'</c:if> >${sort.datailName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">属性：</label>
						<div class="col-sm-3">
							<label class="checkbox-inline"> <input id="topMark" type="checkbox" <c:if test="${entity.topMark==10002 }">checked="checked"</c:if> />
								置顶
							</label> <label class="checkbox-inline"> <input id="keyMark" type="checkbox" <c:if test="${entity.keyMark==10002 }">checked="checked"</c:if> />
								加精
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">状态：</label>
						<div class="col-sm-3">
							<label class="radio-inline"> 
							<input name="status" type="radio" value="10002" <c:if test="${entity.status==10002 }">checked="true"</c:if> /> 显示
							</label> <label class="radio-inline"> 
							<input name="status" type="radio" value="10001" <c:if test="${entity.status==10001 }">checked="true"</c:if>/> 隐藏
							</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
		<br />
		<div class="panel panel-default">
			<div class="panel-body">
				<a href="javascript:;" class="btn btn-primary _saveBtn">确定添加</a>
			</div>
		</div>
	</div>
</body>
</html>