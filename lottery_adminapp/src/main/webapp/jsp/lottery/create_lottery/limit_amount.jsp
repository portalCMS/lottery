<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>限额配置</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/lottery/limit_amount.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>

	<div class="container">
		<ul class="follow-list">
			<li class="active">彩种配置</li>
			<li class="active">奖期配置</li>
			<li class="active">玩法配置</li>
			<li class="active">奖金组配置</li>
			<li class="active">限额配置</li>
			<div class="clearfix"></div>
		</ul>
		<form action="${contextPath}/lottery/saveAllLotteryInfo.do" name="lotteryForm" method="post">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-inline pull-left">
					<div class="form-group">
						<input type="text" class="form-control checkedAttr" id="totalBets" 
						checkType="isNotEmpty,intege1" alt="限号总注数不能为空！,限号总注数必须是正整数！"
						name="totalLimitBets" placeholder="限号总注数" />
					</div>
					x
					<div class="form-group">
						<input type="text" class="form-control amount_class checkedAttr" id="bonusAmount" placeholder="理论奖金"
							checkType="isNotEmpty,decmal9" alt="理论奖金不能为空！,理论奖金必须是正浮点数！"/>
					</div>
					x
					<div class="form-group">
						<input type="text" class="form-control checkedAttr" id="payoutRatio" placeholder="返奖率"
						checkType="isNotEmpty,decmal7" alt="返奖率不能为空！,返奖率必须是小于等于100，大于等于0的最多两位小数的正浮点数！"/>
					</div>
					%
					&nbsp;&nbsp; = &nbsp;&nbsp;
					<div class="checkbox">
						理论中奖限额&nbsp;&nbsp;
						<strong class="text-danger amount_class" id="totalLimitAmount" >0.00</strong>
						<input type="hidden" id="totalLimitAmount2" name="totalLimitAmount">元
						
					</div>
				</div>
				<div class="pull-right">
					<a href="javascript:limitAmount.avgLimitAmount();" class="btn btn-primary btn-sm">平均分配</a>
				</div>
				<div class="clearfix"></div>
				<div class="split"></div>
				<div class="text-muted">
					总限额数，为该彩种所有玩法限额累加数。为方便设置，平均分配至每个玩法的每个号码。</div>
			</div>
		</div>

		<ul class="nav nav-tabs" id="playModelTabs">
			<c:forEach items="${playModelList}" varStatus="status" var="pm">
				<li class="playclass"">
				<a href="javascript:;" name="play_div_${pm.id }" class="_play_a">
				${pm.modelName}&nbsp;</a>
				</li>
				
			</c:forEach>
		</ul>
		<input type="hidden" id="playSize" value="${playSize}"/>
		<c:forEach items="${playModelList}" varStatus="status" var="pm">
		<div class="nav_content" style="display:none;" id="play_div_${pm.id }">
				<div class="panel panel-default ">
				<div class="panel-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">该玩法总限百分比：</label>
							<div class="col-sm-2 ">
								<input type="text" class="form-control la_percent amount_class checkedAttr" id="la_percent_${status.index}"
								checkType="isNotEmpty,decmal7" alt="总限百分比不能为空！,总限百分比必须是小于等于100，大于等于0的最多两位小数的正浮点数！"/>
							</div>
							<span style="text-align: center;line-height: 35px;">%</span>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label ">对应总限金额：</label>
							<div class="col-sm-2">
								<p class="form-control-static">
										<label class="label label-success limitAmount amount_class" id="limitAmount_${status.index}">0.00</label>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default clear_margin_bottom">
				<div class="panel-body">
						<input type="hidden" class="_winAmount" value="${pm.winAmount }"/>
					该玩法全选后共：<span class="text-danger" id="betNums_${status.index}">${pm.totalBets}</span>个号码，每个号码平均分配中奖限额<span
						class="text-danger amount_class" id="avgAmount_${status.index}">0.00</span>
						<input type="hidden" name="lotteryPlayModels[${status.index}].limitAmount" class="amount_class _limitAmount" value="">元,
						<input type="hidden" name="lotteryPlayModels[${status.index}].modelCode"  value="${pm.modelCode}">
						如果<span class="_payoutRatio">0</span>%返奖率，每注号码的限注购买金额为<span class="amount_class _limitAmount text-danger">0.00</span>元.
						
				</div>
			</div>
		</div>
		</c:forEach>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<a href="javascript:limitAmount.submitForm();" class="btn btn-primary"> 提交保存 </a> <a
							href="${contextPath }/lottery/initBonusGroup.do" class="btn btn-link btn-sm">返回</a>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>