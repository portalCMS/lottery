<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>方案详情</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>

</head>
<body>
	<%@ include file="../include/top.jsp"%>

	<div class="container">
		<ol class="breadcrumb">
			<li><a href="index.do">首页</a></li>
			<li><a href="showRecords.do">投注记录</a></li>
			<li class="active">${vo.orderNo }</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">方案详情</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">投注用户</label>
						<div class="form-control-static col-sm-10">
							<a href="user/showUserInfo/${vo.customerId }.do" >${vo.uName }</a>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">方案编号</label>
						<div class="form-control-static col-sm-10">${vo.id }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注订单</label>
						<div class="form-control-static col-sm-10">
							<a href="javascript:void(0);">${vo.orderNo }</a>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="panel panel-default">
			<div class="panel-heading">投注详情</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">彩种名称</label>
						<div class="form-control-static col-sm-10">${vo.lotteryCode }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">玩法名称</label>
						<div class="form-control-static col-sm-10">${vo.playCode } &nbsp;&nbsp; ${vo.selectCodeName }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">彩种期号</label>
						<div class="form-control-static col-sm-10">${vo.issueNo }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">开奖号码</label>
						<div class="form-control-static col-sm-10">${vo.opernBetNumber }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注金额</label>
						<div class="form-control-static col-sm-10">
							<span class="text-danger"><fmt:formatNumber value="${vo.betMoney * vo.multiple }" groupingUsed="true"/></span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注模式</label>
						<div class="form-control-static col-sm-10">${vo.betModelName }</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注倍数</label>
						<div class="form-control-static col-sm-10">${vo.multiple }</div>
					</div>
					<!-- 
					<div class="form-group">
						<label class="control-label col-sm-1">奖金组</label>
						<div class="form-control-static col-sm-10">1950</div>
					</div>
					-->
					<div class="form-group">
						<label class="control-label col-sm-1">投注状态</label>
						<div class="form-control-static col-sm-10">
							<c:if test="${vo.betStatus == 21001 }">
								<label class="label label-danger">投注成功</label>
							</c:if>
							<c:if test="${vo.betStatus == 21002 }">
								<label class="label label-danger">已中奖 &nbsp;&nbsp;
								中奖金额：<fmt:formatNumber value="${vo.winMoney }" groupingUsed="true"/>
								 &nbsp;&nbsp;返奖率：<fmt:formatNumber value="${vo.payoutRatio }" groupingUsed="true"/></label>
							</c:if>
							<c:if test="${vo.betStatus == 21003 }">
								<label class="label label-danger">未中奖</label>
							</c:if>
							<c:if test="${vo.betStatus == 21004 }">
								<label class="label label-danger">已撤单</label>
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注时间</label>
						<div class="form-control-static col-sm-10">
							<span class="text-muted"><fmt:formatDate value="${vo.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/></span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">投注内容</label>
						<div class="form-control-static col-sm-10">
							<div style="height: 100px; overflow-y: scroll; word-wrap: break-word; word-spacing: normal;">
								<c:if test="${! vo.bileNum eq '' }">
									胆码:${vo.bileNum} 托码:${vo.betNum}
								</c:if>
								<c:if test="${ vo.bileNum eq '' }">
									${vo.betNum }
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>