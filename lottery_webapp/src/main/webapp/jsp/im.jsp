<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
<script>
	$(document).ready(function(){
		$("#imframe").attr("height",$(window).height());
		if(window.name!='test'){
			window.name = 'test';
			window.location.reload();
		}
		 
	});
</script>
</head>
<body>
<iframe src="http://chat.joyousphper.com:3000/" id="imframe" width="100%" marginheight="0px">
</iframe>
</body>
</html>