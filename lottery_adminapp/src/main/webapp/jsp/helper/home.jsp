<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置帮助中心首页</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
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
			<li class="active ">设置首页</li>
		</ol>
		<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
		<br />
		<div class="panel panel-default">
			<div class="panel-body">
				<a href="#" class="btn btn-primary">确定添加</a>
			</div>
		</div>
	</div>
</body>
</html>