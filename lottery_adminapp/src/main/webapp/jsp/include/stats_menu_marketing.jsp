<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-sm-2" style="position: relative">
	<ul class="nav nav-pills nav-stacked " role="tablist"
		style="position: fixed; width: 180px" id="statsMenu">
		<li role="presentation" id="normal"><a href="${contextPath }/statistic/showMarketStasData.do">常规数据</a></li>
		<!-- <li role="presentation" id="profile"><a href="${contextPath }/jsp/stats/marketing/action.jsp">分红查询</a></li>-->
		<li role="presentation" id="ylreport"><a href="${contextPath }/statistic/showylreport.do">盈亏报表</a></li>
	</ul>
</div>