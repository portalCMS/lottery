<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>彩种管理</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/lottery/lotteryengine.js"></script>
<%
    Date date = new Date();
%>
<script language="javascript">   
//得到时间后用js函数进行累加
 var hours, minutes, seconds, xfile;
 var intHours, intMinutes, intSeconds;
 var today= new Date(); 
 today.setTime(<%=date.getTime()%>);    //把服务器时间赋给JS函数
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<input type="hidden" id="hour" value="${hour }"/>
				<input type="hidden" id="minutes" value="${minutes }"/>
				<input type="hidden" id="seconds" value="${seconds }"/>
				
				<div class="pull-left" style="font-weight:bold;font-size: 14px;color: blue;">彩种引擎</div>
				<div class="pull-right">服务器时间：<label id="time_view">${hour } : ${minutes } : ${seconds }</label></div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<div class="pull-left"></div>
				<div class="pull-right">
					<!-- 如果有号源发生异常，将此按钮的class btn-default 改为  btn-danger 并将未处理的故障数标识出来 -->
					<a href="${contextPath }/lottery/sourceMonitor.do" class="btn btn-primary btn-sm">号源监控</a>
				</div>
				<div class="clearfix"></div>
				
				<ul class="nav nav-tabs" role="tablist">
			<c:forEach items="${groupList }" var="group" varStatus="status">
				<li class="groupclass _lgInfo">
					<a onclick="lotteryEngine.showLotteryGroup(this);" name="${group.lotteryGroup }" 
						style="font-weight:bold;font-size: 14px;color:#33cccc;">${group.lotteryGroupName }</a>
				</li>
			</c:forEach>
		</ul>
		<c:forEach items="${groupList }" var="group" varStatus="status">
		<div class="nav_content _lotInfo" id="${group.lotteryGroup}" style="display:none;">
			<c:forEach items="${group.lotteryList }" var="lottery" varStatus="lotteryStatus">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="pull-left">
							<a href="javascript:lotteryEngine.showLotteryDetail('${contextPath }/lottery/lotteryDetail.do',
							'${lottery.lotteryCode }','${lottery.lotteryGroup }')" 
							class="btn btn-xs btn-link" style="font-weight:bold;font-size: 14px;">${lottery.lotteryName }
							<c:if test="${lottery.lotteryStatus==10002 }"><font color="green">(正常)</font></c:if>
							<c:if test="${lottery.lotteryStatus==10003 }"><font color="red">(暂停销售)</font></c:if>
							<c:if test="${lottery.lotteryStatus==10001 }"><font color="red">(关闭)</font></c:if>
							</a>
						</div>
<!-- 						<div class="pull-right"> -->
<!-- 							<span class="text-success">运行中</span> -->
<!-- 						</div> -->
						<div class="clearfix"></div>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-2">
								<strong>奖期总数</strong> 
								<span class="text-warning">${lottery.totalTimes } 期 </span>
							</div>
							<div class="col-sm-2">
								<strong>成功执行期数</strong> 
								<span class="text-success">${lottery.countSuccessSeries } 期 </span>
							</div>
							<div class="col-sm-2">
								<strong>当前期号</strong> <span class="text-warning">${lottery.currentSeries } </span>
							</div>
							<div class="col-sm-2">
								<strong>失败执行期数</strong> 
								<span class="text-danger">${lottery.countFailedSeries } 期 </span>
							</div>
<!-- 							<div class="col-sm-2"> -->
<%-- 								<strong>失败期号</strong> <span class="text-danger">${lottery.failedSeriesStr }</span> --%>
<!-- 							</div> -->
							
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:forEach>
		<a href="javascript:lotteryEngine.initCreateLottery('${contextPath }/lottery/initCreateLottery.do',
			'${group.lotteryGroup}');" class="btn btn-primary btn-sm btn-block" style="margin-bottom: 30px;">添加新彩种</a>
	</div>
			</div>
		</div>
		
</body>
</html>