<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-sm-2" style="position: relative">
	<ul class="nav nav-pills nav-stacked" role="tablist"
		style="position: fixed; width: 180px" id="statsMenu">
		<li role="presentation" id="normal"><a href="${contextPath }/statistic/userStatistic.do">常规数据</a></li>
		<li role="presentation" id="profile"><a href="${contextPath }/statistic/profilequery.do">个人查询</a></li>
		<li role="presentation" id="super"><a href="${contextPath }/statistic/superquery.do">超级查询</a></li>
	</ul>
</div>