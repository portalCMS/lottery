<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>奖期配置</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/lottery/issues.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container">
		<ul class="follow-list">
			<li class="active">彩种配置</li>
			<li class="active">奖期配置</li>
			<li>玩法配置</li>
			<li>奖金组配置</li>
			<li>限额配置</li>
			<div class="clearfix"></div>
		</ul>
		<form action="${contextPath }/lottery/saveLotteryTask.do" method="post" name="lotteryForm">
		<div class="panel panel-default">
			<div class="panel-heading">奖期参数</div>
			<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-1 control-label">开奖周期</label>
						<div class="col-sm-10">
							<label class="checkbox-inline"> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox1"  value="MON"> 周一
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox2"  value="TUE"> 周二
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox3"  value="WED"> 周三
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox3"  value="THU"> 周四
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox3"  value="FRI"> 周五
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox3"  value="SAT"> 周六
							</label> <label class="checkbox-inline "> <input type="checkbox" name="seriesTime"
								id="inlineCheckbox3"  value="SUN"> 周日
							</label>
							<input type="hidden" id="timeConfig" name="timeConfig" value="${timeConfig}"/>
						</div>
					</div>
				<hr />
				<div id="lottery_series">
					<c:forEach items="${lotteryList }" var="lot" varStatus="status">
						<div class="panel panel-default period-panel">
						<div class="panel-heading">
							<div class="pull-left">奖期阶段<label class="series_level">1</label></div>
							<div class="pull-right">
								<c:if test="${status.index==0 }"><a href="javascript:;" class="close_btn">关闭</a></c:if>
								<c:if test="${status.index!=0 }"><a href="javascript:;" class="close_btn2">关闭</a></c:if>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<div class="form-group">
									<label class="control-label col-sm-1">首期时间</label>
									<div class="col-sm-2">
										<input type="text" class="form-control startTime checkedAttr" 
										checkType="isNotEmpty,time1" alt="首期时间不能为空！,首期时间格式必须是HH:mm:ss或者HH:mm！"
										name="lotterys[0].startTime" value="${lot.startTime }"/>
									</div>
									<label class="control-label col-sm-1">末期时间</label>
									<div class="col-sm-2">
										<input type="text" class="form-control endTime checkedAttr" 
										checkType="isNotEmpty,time1" alt="末期时间不能为空！,末期时间格式必须是HH:mm:ss或者HH:mm！"
										name="lotterys[0].endTime" value="${lot.endTime }"/>
									</div>
									<label class="control-label col-sm-1">奖期间隔</label>
									<div class="col-sm-1">
										<input type="text" class="form-control spanTime checkedAttr" 
										checkType="isNotEmpty,num1" alt="奖期间隔不能为空！,奖期间隔必须是正整数！"
										name="lotterys[0].spanTime" value="${lot.spanTime }"/>
									</div>
									<label class="control-label col-sm-1">开奖延迟</label>
									<div class="col-sm-1">
										<input type="text" class="form-control afterLotTime " 
										name="lotterys[0].afterLotTime" value="${lot.afterLotTime }"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-1">奖期期数</label>
									<div class="col-sm-2">
										<input type="text" class="form-control totalTimes checkedAttr" 
										checkType="isNotEmpty,num1" alt="奖期期数不能为空！,奖期期数必须是正整数！"
										name="lotterys[0].totalTimes" value="${lot.totalTimes }"/>
									</div>
									<label class="control-label col-sm-1">抓取次数</label>
									<div class="col-sm-2">
										<input type="text" class="form-control catchTimes checkedAttr" 
										checkType="isNotEmpty,num1" alt="抓取次数不能为空！,抓取次数必须是正整数！"
										name="lotterys[0].catchTimes" value="${lot.catchTimes }"/>
									</div>
									<label class="control-label col-sm-1">截止时差</label>
									<div class="col-sm-1">
										<input type="text" class="form-control beforeLotTime checkedAttr" 
										checkType="isNotEmpty,num1" alt="截止时差不能为空！,截止时差必须是正整数！"
										name="lotterys[0].beforeLotTime" value="${lot.beforeLotTime }"/>
									</div>
								</div>
							</div>
					</div>
					</div>
				</c:forEach>
			</div>
			<div id="series_panle"></div>
			<div id ="clonePeriod" style="display:none;">
				<div class="panel panel-default period-panel">
				<div class="panel-heading">
						<div class="pull-left">奖期阶段<label class="series_level">1</label></div>
						<div class="pull-right">
							<a href="javascript:;" class="close_btn2">关闭</a>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-1">首期时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control startTime checkedAttr" 
									checkType="isNotEmpty,time1" alt="首期时间不能为空！,首期时间格式必须是HH:mm:ss或者HH:mm！"
									name="lotterys[0].startTime" value="${lot.startTime }"/>
								</div>
								<label class="control-label col-sm-1">末期时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control endTime checkedAttr" 
									checkType="isNotEmpty,time1" alt="末期时间不能为空！,末期时间格式必须是HH:mm:ss或者HH:mm！"
									name="lotterys[0].endTime" value="${lot.endTime }"/>
								</div>
								<label class="control-label col-sm-1">奖期间隔</label>
								<div class="col-sm-1">
									<input type="text" class="form-control spanTime checkedAttr" 
									checkType="isNotEmpty,num1" alt="奖期间隔不能为空！,奖期间隔必须是正整数！"
									name="lotterys[0].spanTime" value="${lot.spanTime }"/>
								</div>
								<label class="control-label col-sm-1">开奖延迟</label>
								<div class="col-sm-1">
									<input type="text" class="form-control afterLotTime " 
									name="lotterys[0].afterLotTime" value="${lot.afterLotTime }"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-1">奖期期数</label>
								<div class="col-sm-2">
									<input type="text" class="form-control totalTimes checkedAttr" 
									checkType="isNotEmpty,num1" alt="奖期期数不能为空！,奖期期数必须是正整数！"
									name="lotterys[0].totalTimes" value="${lot.totalTimes }"/>
								</div>
								<label class="control-label col-sm-1">抓取次数</label>
								<div class="col-sm-2">
									<input type="text" class="form-control catchTimes checkedAttr" 
									checkType="isNotEmpty,num1" alt="抓取次数不能为空！,抓取次数必须是正整数！"
									name="lotterys[0].catchTimes" value="${lot.catchTimes }"/>
								</div>
								<label class="control-label col-sm-1">截止时差</label>
								<div class="col-sm-1">
									<input type="text" class="form-control beforeLotTime checkedAttr" 
									checkType="isNotEmpty,num1" alt="截止时差不能为空！,截止时差必须是正整数！"
									name="lotterys[0].beforeLotTime" value="${lot.beforeLotTime }"/>
								</div>
							</div>
						</div>
				</div>
				</div>
			</div>
				<a id="new_btn" class="btn btn-default btn-block btn-default btn-sm">添加新阶段</a>
		</div>
			
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="botton"  id="next_btn" class="btn btn-primary" value="下一步">
							<a href="${contextPath }/lottery/initCreateLottery.do" class="btn btn-sm btn-link text-muted">返回</a>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>