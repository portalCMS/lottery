<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建新条目</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/helpcenter/createNotice.js"></script>
<script>
	$(document).ready(function() {
		var ue = UE.getEditor('editor');
	});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${ctx }/index.do">首页</a></li>
			<li><a href="${ctx }/article/showHelp.do">帮助中心</a></li>
			<li class="active ">新增条目</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">帮助条目详情</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">分类：</label>
						<div class="col-sm-2">
							<select class="form-control" id="detailType">
								<c:forEach items="${sorts }" var="sort">
									<option value="${sort.detailType }">${sort.datailName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">问题：</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="title"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">状态：</label>
						<div class="col-sm-5">
							<label class="radio-inline"> 
							<input name="status" type="radio" value="10002" /> 显示
							</label> <label class="radio-inline"> 
							<input name="status" type="radio" value="10001"/> 隐藏
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