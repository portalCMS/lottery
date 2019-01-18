<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公告详情</title>
<%@ include file="../include/js_asset.jsp"%>
<%@ include file="../include/css_asset.jsp"%>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span>公告详情</span></div>
	<a href="${contextPath }/showLoadNotices.html" class="back"><i class="icon icon-zuofuben"></i>返回</a>
</header>

<aside></aside>

<section class="container board-detail">
	<h1>
		${entity.title}
		<span>${entity.detailTypeName }<time><fmt:formatDate value="${entity.createTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></time></span>
	</h1>
	<div>
		<p>${entity.content }</p>
	</div>

</section>

</body>
</html>