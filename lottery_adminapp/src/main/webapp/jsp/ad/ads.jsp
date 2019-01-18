<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告位管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<link rel="stylesheet" href="${contextPath }/jsp/css/ads.css" />
<script src="${contextPath }/jsp/js/ad/ads.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">广告位管理</div>
			<div class="panel-body ">
				<!-- 静态页面，无需设置，就是告知每个编号是在哪一块 -->
				<a href="adDes.jsp" class="btn btn-default">区域位置对照表</a>
			</div>
		</div>
		<c:forEach items="${regions }" var="region">
			<c:if test="${region.advers == null  || fn:length(region.advers) == 0 }">
				<div class="ad_content "
					bgPic="http://zcimg.zcool.com.cn/zcimg/e1b053e3ad0732f87520e6f25235">
			</c:if>
			<c:if test="${fn:length(region.advers) > 0}">
				<div class="ad_content "
					bgPic="${region.advers[0].url }">
			</c:if>
			
				<div class="settings">
					<span class="_showPicBtn"> 已设置 ${region.settingCont } 张</span>
				</div>
				<div class="info clearfix">
					<ul class="list-unstyled pull-left">
						<li>页面：${region.pageName }</li>
						<li>区域：${region.regionCode }</li>
						<li>数量：${region.count }</li>
						<li>尺寸：${region.size }</li>
						<li>描述：${region.dsce }</li>
					</ul>
					<div class="pull-right">
						<a href="javascript:;" id="${region.regionCode }" name="${region.id }" class="btn btn-danger btn-sm _setPicBtn">设置图片</a> 
					</div>
				</div>
			</div>
		</c:forEach>

	</div>
</body>
</html>