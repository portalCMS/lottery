<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
	<meta name="apple-mobile-web-app-title" content="彩票" />
	<meta content="telephone=no" name="format-detection" /> 
	<meta content="email=no" name="format-detection" />
	<meta name="renderer" content="webkit">
	<title>投注记录</title>
	<%@ include file="../include/js_asset.jsp"%>
	<%@ include file="../include/css_asset.jsp"%>
	<script src="${contextPath }/jsp/jspmp/js/orders/orders.js"></script>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span>投注记录</span></div>
	<a href="${contextPath }/indexmp.html" class="back"><i class="icon icon-zuofuben"></i>返回</a>
</header>

<aside></aside>

<section class="container">
	<table class="table history">
		<tr>
			<th width="3000">投注时间</th>
			<th width="3000">彩种</th>
			<th width="2000">投注金额</th>
			<th width="2000">状态</th>
		</tr>
			<tbody class="_tbody">
			</tbody>
		</table>
	</table>

	<div class="pager _pageInfo>
		<a href="#" role="button" class="btn btn-disable">下一页</a>
		<a href="#" role="button" class="btn btn-disable">上一页</a>
	</div>
</section>


</body>
</html>