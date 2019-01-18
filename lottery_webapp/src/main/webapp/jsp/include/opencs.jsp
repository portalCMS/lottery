<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="css_asset.jsp"%>
<%@ include file="js_asset.jsp"%>

<script>
$(document).ready(function(){
	var url =$("#live800iconlink").attr("onclick");
	url = url.substring(url.indexOf("(")+2,url.indexOf(",")-1);
	location.href = url;
});
</script>
</head>
<body>
<script language="javascript" src="http://szzero.livechatvalue.com/chat/chatClient/staticButton.js?jid=4364479967&companyID=459465&configID=49000&codeType=custom"></script>
</body>
</html>