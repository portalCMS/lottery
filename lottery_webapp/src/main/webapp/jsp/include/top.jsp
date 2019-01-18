<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="globaltop.jsp"%>

<nav class="navbar navbar-inverse" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">SuperMan</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${contextPath }/index.do">首页</a></li>
				<li><a href="${contextPath }/index.do">彩票大厅</a></li>
				
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">管理员 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${contextPath }/jsp/admin/admins.jsp">管理员列表</a></li>
						<li><a href="${contextPath }/jsp/admin/admins.jsp">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="#">退出登录</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>