<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="globaltop.jsp"%>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<audio src="${contextPath }/jsp/audio/alarm.wav" id="alarm"
			loop="loop"></audio>
		<audio src="${contextPath }/jsp/audio/charge.wav" id="charge"
			loop="loop"></audio>
		<span class="_drawingCount" style="display: none;"></span> <span
			class="_rechargeCount" style="display: none;"></span> <span
			class="_activityCount" style="display: none;"></span>
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a id="openAudio" class="navbar-brand" href="javascript:;" title="点击开启提示音" ><span
						class="glyphicon glyphicon-volume-off"></span></a>
			<a id="closeAudio" class="navbar-brand" href="javascript:;" title="点击关闭提示音" style="display:none;"><span
						class="glyphicon glyphicon-volume-up"></span></a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class=""><a href="${contextPath }/index.do"><span
						class="glyphicon glyphicon-home"></span>&nbsp;首页</a></li>
				<c:forEach var="adminPermissions" items="${listAdminPermissionsVO }">
					<c:if test="${adminPermissions.permissionsName=='用户管理' }">
						<li><a href="${contextPath }/user/showUserManage.do"><span
								class="glyphicon glyphicon-user"></span>&nbsp;用户管理</a></li>
					</c:if>
					<c:if
						test="${adminPermissions.permissionsName!='用户管理' && adminPermissions.permissionsName!='管理员' }">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <c:if
									test="${adminPermissions.permissionsName=='财务管理' }">
									<span class="glyphicon glyphicon-usd"></span>
								</c:if> <c:if test="${adminPermissions.permissionsName=='活动引擎' }">
									<span class="glyphicon glyphicon-star"></span>
								</c:if> <c:if test="${adminPermissions.permissionsName=='彩种管理' }">
									<span class="glyphicon glyphicon-record"></span>
								</c:if> <c:if test="${adminPermissions.permissionsName=='订单数据' }">
									<span class="glyphicon glyphicon-list-alt"></span>
								</c:if> <c:if test="${adminPermissions.permissionsName=='内容管理' }">
									<span class="glyphicon glyphicon-book"></span>
								</c:if> <c:if test="${adminPermissions.permissionsName=='统计报表' }">
									<span class="glyphicon glyphicon-stats"></span>
								</c:if> &nbsp; ${adminPermissions.permissionsName } <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<c:forEach var="adminLevelsPermissions"
									items="${adminPermissions.listLevelsPermissions }">
									<li><a
										href="${contextPath }${adminLevelsPermissions.permissions}">${adminLevelsPermissions.permissionsName }</a></li>
								</c:forEach>
							</ul></li>
					</c:if>
				</c:forEach>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">系统管理 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<c:forEach var="adminPermissions"
							items="${listAdminPermissionsVO }">
							<c:if test="${adminPermissions.permissionsName=='管理员' }">
								<li><a href="${contextPath }/admin/showadmins.do">管理员列表</a></li>
								<li class="divider"></li>
								<li><a href="${contextPath }/admin/initAdminRole.do">角色列表</a></li>
								<li class="divider"></li>
								<li><a href="${contextPath }/ment/initAdminPermissions.do">权限列表</a></li>
								<li class="divider"></li>
							</c:if>
						</c:forEach>
						<li><a href="${contextPath }/loginout.do">退出登录</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>
<div style="margin-bottom: 70px;"></div>
