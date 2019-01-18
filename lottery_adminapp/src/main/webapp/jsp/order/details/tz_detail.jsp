<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="showOrders.do">订单管理</a></li>
			<li class="active">${ordervo.orderNumber }</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">订单信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">用户</label>
						<div class="form-control-static col-sm-10">
							<a href="user/showUserInfo/${ordervo.customerId }.do">${ordervo.customerName }</a>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">订单类型</label>
						<div class="form-control-static col-sm-10">投注订单</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">订单编号</label>
						<div class="form-control-static col-sm-10">
							${ordervo.orderNumber }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">创建时间</label>
						<div class="form-control-static col-sm-10"><fmt:formatDate value="${ordervo.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">订单金额</label>
						<div class="form-control-static col-sm-10">
							<span class="text-danger"><fmt:formatNumber value="${ordervo.orderAmount }" pattern="#,##0.00" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">状态</label>
						<div class="form-control-static col-sm-10">
							<span class="text-success">${ordervo.orderStatusName }</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">方案信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">投注彩种</label>
						<div class="form-control-static col-sm-10">${brs[0].lotteryCode }</div>
					</div>
					<!--  
						<div class="form-group">
							<label class="control-label col-sm-1">奖金组</label>
							<div class="form-control-static col-sm-10">1950</div>
						</div>
					-->
					<div class="form-group">
						<label class="control-label col-sm-1">期号</label>
						<div class="form-control-static col-sm-10">
							<c:set var="temp"  value="" />
							<c:forEach items="${brs }" var="br">
								<c:if test="${temp.indexOf(br.issueNo )==-1 }">
									<c:set var="temp"  value="${temp },${br.issueNo }" />
									<c:if test="${br.betStatus == 21001 }">
										<p>
											${br.issueNo }期 &nbsp;${br.multiple }倍 &nbsp;<span class="text-success">投注成功</span>
										</p>
									</c:if>
									<c:if test="${br.betStatus == 21002 }">
										<p>
											${br.issueNo }期 &nbsp;${br.multiple }倍 &nbsp;<span class="">已中奖</span>
										</p>
									</c:if>
									<c:if test="${br.betStatus == 21003 }">
										<p>
											${br.issueNo }期 &nbsp;${br.multiple }倍 &nbsp;<span class="text-muted">未中奖</span>
										</p>
									</c:if>
									<c:if test="${br.betStatus == 21004 }">
										<p>
											${br.issueNo }期 &nbsp;1倍 &nbsp;<span class="text-muted"><del>已撤单</del></span><a>撤单返款</a>
										</p>
									</c:if>
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注内容</label>
						<div class="form-control-static col-sm-10">
							<c:forEach items="${brs }" var="br">	
								<p>
									<span class="text-success">${br.playCode }</span>&nbsp;&nbsp;<c:if test="${not empty br.bileNum }">胆码：${br.bileNum }托码:${br.betNum }</c:if><c:if test="${empty br.bileNum }">${br.betNum }</c:if>
								</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>