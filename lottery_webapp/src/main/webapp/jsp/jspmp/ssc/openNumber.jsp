<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>开奖号码</title>
	<%@ include file="../include/js_asset.jsp"%>
	<%@ include file="../include/css_asset.jsp"%>
	<script type="text/javascript" 
		src="${contextPath }/jsp/jspmp/js/ssc/openNumber.js"></script>
</head>
<body>

<header class="clearfix">
	<i class="icon icon-nav"></i>
	<div class="title"><span>开奖号码</span></div>
	<a href="${contextPath }/showshishicai.html?lotteryCode=${lotteryCode }" class="back"><i class="icon icon-zuofuben"></i>返回</a>
</header>

<aside></aside>

<section class="container">
	<table class="table">
	<input type="hidden" id="lotteryCode" name="lotteryCode" value="${lotteryCode }"/>
		<tr>
			<th width="4000">开奖时间</th>
			<th width="3000">奖期</th>
			<th width="4000">号码</th>
		</tr>
			<tbody class="_tbody">
			</tbody>
	</table>

	<div class="pager _pageInfo">
	</div>
</section>


</body>
</html>