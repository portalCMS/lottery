<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>玩法相关配置</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/lottery/playbonus.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<form action="${contextPath }/lottery/saveModifylpb.do" method="post" name="playBonusForm">
	<div class="container">
		<ul class="follow-list">
			<li class="active">玩法配置</li>
			<li class="active">奖金组配置</li>
			<li>限额配置</li>
			<div class="clearfix"></div>
		</ul>
		<!-- Nav tabs -->
		<ul class="nav nav-tabs">
			<c:forEach items="${bonusGroupList }" var="bonus" varStatus="status">
				<li class="bonusclass">
					<a onclick="bonusForm.showBonusGroup(this);" name="${bonus.id }">${bonus.name }</a>
				</li>
			</c:forEach>
		</ul>
		
		<c:forEach items="${bonusGroupList }" var="bonus" varStatus="status">
			<div class="nav_content" id="${bonus.id}" style="display:none;">
				<table class="table table-hover table-bordered table-condensed">
					<thead>
						<tr class="success text-center">
							<td>玩法</td>
							<td width="150">理论中奖率</td>
							<td width="150">理论奖金</td>
							<td width="150">返奖率</td>
							<td width="150">返点率</td>
							<td width="150">利润率</td>
							<td>总值</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bonus.playList }" var="play" varStatus="playStatus">
							<tr class="_lpbRow" >
								<td >${play.modelName }
									<input type="hidden" class="_mn"  value="${play.modelName }">
									<input type="hidden" class="_mc"  value="${play.modelCode }">
									<input type="hidden" class="_bid"  value="${bonus.id}">
								</td>
								<td>
									<c:if test="${play.levelList!=null }">
										<c:forEach items="${play.levelList }" var="level" varStatus="levelStatus">
											<span>${level.levelName }</span>
											<span class="_wr">${level.winingRate }</span><br>
										</c:forEach>
									</c:if>
									<c:if test="${play.levelList==null }">
										<span class="_wr">${play.winingRate }</span>
									</c:if>
								</td>
								<td>
									<c:if test="${play.levelList!=null }">
										<c:forEach items="${play.levelList }" var="level" varStatus="levelStatus">
											<span>${level.levelName }</span>
											<span class="_wa _amount">${level.winAmount }</span><br>
										</c:forEach>
									</c:if>
									<c:if test="${play.levelList==null }">
										<span class="_wa _amount">${play.winAmount }"></span>
									</c:if>
								</td>
								<td><input type="text" class="form-control _pr checkedAttr" 
										checkType="isNotEmpty,decmal8" alt="返奖率不能为空！,返奖率必须是0到1间的正浮点数！"
										value="${bonus.payoutRatio}" />
								</td>
								<td><input type="text" class="form-control _rb checkedAttr" 
										checkType="isNotEmpty,decmal8" alt="返点率不能为空！,返点率必须是0到1间的正浮点数！！"
									 	value="${bonus.rebates}" />
								</td>
								<td><input type="text" class="form-control _mg checkedAttr" 
									checkType="isNotEmpty,decmal8" alt="利润率不能为空！,利润率必须是0到1间的正浮点数！！"
								 	value="${bonus.margin}" />
								</td>
								<td>100%</td>
							</tr>
						</c:forEach>
				</tbody>
				</table>
			</div>
		</c:forEach>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="button" class="btn btn-primary" value="下一步" onclick="bonusForm.submitForm2();"> 
						<input type="hidden" id="lotteryCode" value="${lotteryVo.lotteryCode }">
						<input type="hidden" id="lotteryGroup" value="${lotteryVo.lotteryGroup }">
						<a href="javascript:;" class="btn btn-link btn-sm backBtn">返回</a>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>