<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">JOB System</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav _tops">
				<li id="taskPage"><a href="${contextPath }/initTaskList.do">奖期查询</a></li>
				<li id="jobPage"><a href="${contextPath }/initJobs.do">奖期生成</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">管理员 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">退出登录</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>
<div style="margin-bottom: 70px;"></div>
