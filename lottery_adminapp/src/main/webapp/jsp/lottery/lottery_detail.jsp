<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${lottery.lotteryName }--详情</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript">
var path = "${contextPath }";
</script>
<script src="${contextPath }/jsp/js/lottery/lotterydetail.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/lottery/showLotteryEngine.do">彩种管理</a></li>
			<li><a href="${contextPath }/lottery/showLotteryEngine.do">${lottery.lotteryGroupName }</a></li>
			<li class="active">${lottery.lotteryName }</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class='pull-left' style="margin-top:5px;">彩种信息</div>
				<div class="pull-left">
					<div class="row">
						<div id="modifyBtn1" class="col-sm-2 col-sm-offset-2" style="display:block;">
							<a href="javaScript:showSaveBtn('lottery','modifyBtn1','saveBtn1');"  class="btn btn-sm btn-danger">修改彩种信息</a>
						</div>
						<div id="saveBtn1" class="col-sm-2 col-sm-offset-2" style="display:none;">
							<a href="javaScript:saveLottery('${lottery.id}');"  class="btn btn-sm btn-success">保存更改</a>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-1 control-label">彩种名称</label>
						<div class="col-sm-2">
							<input type="text" class="form-control lottery" name="lotteryName" 
								checkType="isNotEmpty" alt="彩种名称不能为空！"
								value="${lottery.lotteryName }" readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">彩种编号</label>
						<div class="col-sm-2">
							<input type="text" class="form-control " name="lotteryCode" value="${lottery.lotteryCode }" readonly="readonly" />
								<input type="hidden" value="${lottery.lotteryCode }" id="lotteryCode" />
								<input type="hidden" value="${lottery.lotteryGroup }" id="lotteryGroup" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">彩种状态</label>
						<div class="col-sm-11">
							<label class="radio-inline"> <input type="radio"
								id="inlineCheckbox1" name="lotteryStatus" value="10002">
								<input type="hidden" value="${lottery.lotteryStatus }">
								正常
							</label> <label class="radio-inline"> <input type="radio"
								id="inlineCheckbox2" name="lotteryStatus" value="10003">
								<input type="hidden" value="${lottery.lotteryStatus }">
								暂停销售
							</label> <label class="radio-inline"> <input type="radio"
								id="inlineCheckbox3" name="lotteryStatus" value="10001">
								<input type="hidden" value="${lottery.lotteryStatus }">
								关闭
							</label>
						</div>
					</div>
					<c:if test="${lottery.lotteryGroup == '3D' }">
						<div class="form-group" style="margin-bottom: 0px;">
						<label class="col-sm-1 control-label">休市天数</label>
						<div class="col-sm-2">
							<input type="text" id="restDays" class="form-control lottery" value="${restDays }" readonly="readonly"
							checkType="isNotEmpty,num1" alt="休市天数不能为空！,休市天数必须为正整数！"/> 
							<span class="help-block">确保休市后奖期正常</span>
						</div>
						</div>
					</c:if>
					
					<div class="form-group" style="margin-bottom: 0px;">
						<label class="col-sm-1 control-label">权重排序</label>
						<div class="col-sm-2">
							<input type="text" class="form-control lottery" value="100000"
								checkType="isNotEmpty,num1" alt="权重排序不能为空！,权重排序必须为正整数！"
								readonly="readonly" /> <span class="help-block">数值越高排序越靠前</span>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class='pull-left' style="margin-top: 5px;">号源列表</div>
				<div class="pull-left">
					<div class="row">
						<div id="modifyBtn2" class="col-sm-2 col-sm-offset-2" style="display:block;">
							<a href="javaScript:showSourceModify('source','modifyBtn2','saveBtn2');"  class="btn btn-sm btn-danger">修改号源配置</a>
						</div>
						<div id="saveBtn2" class="col-sm-2 col-sm-offset-2" style="display:none;">
							<a href="javaScript:saveSource();"  class="btn btn-sm btn-success">保存更改</a>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<input type="hidden" id="linkSize" value="${linkSize }">
			<div class="panel-body">
				<table class="table table-hover table-bordered table-condensed">
					<thead>
						<tr class="text-center success">
							<td>编号</td>
							<td>别名</td>
							<td>地址</td>
							<td>权重</td>
							<td width="200">状态</td>
						</tr>
					</thead>
					<tbody id="links_tbody">
						<c:forEach items="${linkList }" var="source" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${source.link.sourceName }</td>
								<td>${source.link.sourceLink }</td>
								<td>${source.link.sourceLevel }</td>
								<td>
									<label class="radio-inline"> 
										<input type="radio" name="linkStatus_${status.index }" value="1">
										<input type="hidden" name="linkId_${source.id }" value="${source.status }"> 启用
									</label>
									<label class="radio-inline">
										<input type="radio" name="linkStatus_${status.index }" value="0"> 
										<input type="hidden" name="linkId_${source.id }" value="${source.status }"> 停用
									</label>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="newSourceBtn" class="panel-body" style="display:none;">
				<div class="pull-left">
					<a href="javascript:addNewSourceLink();" id="new_btn" class="btn btn-sm btn-primary">添加新号源</a>
				</div>
			</div>
			<div id="sourceLinks" class="panel-body" >
			</div>
			<div id="sourceLink" style="display:none;" class="panel-body" >
				<div class="panel panel-default number_url_panle_class" name="newSourceLink">
					<div class="panel-heading">
						<div class="pull-right">
							<a href="javascript:;" class="number_url_close_btn">关闭</a>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body">
						<div class="form-horizontal" >
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">号源别名</label>
								<div class="col-sm-3">
									<input type="text" class="form-control link_names" name="sourceName" value=""
									checkType="isNotEmpty" alt="号源别名不能为空！"/>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">号源网址</label>
								<div class="col-sm-8">
									<input type="text" class="form-control link_urls" name="sourceLink" value=""
									checkType="isNotEmpty" alt="号源网址不能为空！"/>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">权重数值</label>
								<div class="col-sm-3">
									<input type="text" class="form-control link_levels" name="sourceLevel" 
									checkType="isNotEmpty,intege1" alt="权重数值不能为空！,权重数值必须为正整数！" value="100">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class='pull-left' style="margin-top: 5px;">奖期规则</div>
				<div class="pull-left">
					<div class="row">
						<div id="modifyBtn3" class="col-sm-2 col-sm-offset-2" style="display:block;">
							<a href="javaScript:showPeriodModify('period','modifyBtn3','saveBtn3');"  class="btn btn-sm btn-danger">修改奖期配置</a>
						</div>
						<div id="saveBtn3" class="col-sm-8 col-sm-offset-2" style="display:none;">
							<a href="javaScript:savePeriod();"  class="btn btn-sm btn-success">保存更改</a>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body" id="totalPeriods">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-1 control-label">开奖周期</label>
						<div class="col-sm-5">
							<fieldset disabled>
								<input type="hidden" value="${lottery.taskCornExpression }"
									id="seriesExpression" /> <label class="checkbox-inline">
									<input type="checkbox" name="series" value="MON"> 周一
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="TUE"> 周二
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="WED"> 周三
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="THU"> 周四
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="FRI"> 周五
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="SAT"> 周六
								</label> <label class="checkbox-inline"> <input type="checkbox"
									name="series" value="SUN"> 周日
								</label>
							</fieldset>
						</div>
						<label class="col-sm-1 control-label">奖期规则</label>
						<div class="col-sm-2">
							<input type="text" class="form-control checkedAttr period _seriesRule" 
								value="${lottery.seriesRule }"   placeholder="yyMMdd[nn]"
								checkType="isNotEmpty" alt="奖期规则不能为空！" readonly="readonly">
						</div>
					</div>
				</div>
				<div class="priodsInfo">
				<c:forEach items="${lotteryList }" var="lot" varStatus="status">
					<c:choose>
						<c:when test="${lot.lotteryStatus==10002}">
							<div class="panel panel-default lotPeriods">
								<div class="panel-heading">
									<div class="pull-left">奖期阶段</div>	
									<div class="pull-right">
										<a href="javascript:;" class="btn btn-sm btn-danger delete_btn">删除奖期</a>
									</div>
									<div class="clearfix"></div>	
								</div>
								<div class="panel-body periods">
									<input type="hidden" class="period" name="lotteryStatus" value="${lot.lotteryStatus }">
									<input type="hidden" class="period" name="seriesExpression" />
									<input type="hidden" class="period" name="seriesRule" value="${lottery.seriesRule }"/>
									<input type="hidden" class="period" name="id" value="${lot.id }">
									<div class="form-inline">
										<div class="form-group">
											<label class="col-sm-4 control-label" style="margin-top: 5px;">首期时间</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="startTime"
													checkType="isNotEmpty,time1" alt="首期时间不能为空！,首期时间格式必须是HH:mm:ss或者HH:mm！"
													value="${lot.startTime }" readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label" style="margin-top: 5px;">奖期间隔</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="spanTime"
													checkType="isNotEmpty,num1" alt="奖期间隔不能为空！,奖期间隔必须是正整数！"
													value="${lot.spanTime }" readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label " style="margin-top: 5px;">截止时差</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="beforeLotTime"
													checkType="isNotEmpty,num1" alt="截止时差不能为空！,截止时差必须是正整数！"
													value="${lot.beforeLotTime }" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="form-group"></div>
									<div class="form-inline">
										<div class="form-group">
											<label class="col-sm-4 control-label " style="margin-top: 5px;">末期时间</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="endTime"
													checkType="isNotEmpty,time1" alt="末期时间不能为空！,末期时间格式必须是HH:mm:ss或者HH:mm！"
													value="${lot.endTime }" readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label " style="margin-top: 5px;">奖期期数</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="totalTimes"
													checkType="isNotEmpty,num1" alt="奖期期数不能为空！,奖期期数必须是正整数！"
													value="${lot.totalTimes }" readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label " style="margin-top: 5px;">抓取次数</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="catchTimes"
												checkType="isNotEmpty,num1" alt="抓取次数不能为空！,抓取次数必须是正整数！"
													value="${lot.catchTimes }" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="form-group"></div>
									<div class="form-inline">
										<div class="form-group">
											<label class="col-sm-4 control-label " style="margin-top: 5px;">开奖延迟</label>
											<div class="col-sm-5">
												<input type="text" class="form-control period" name="afterLotTime"
													value="${lot.afterLotTime }" readonly="readonly">
											</div>
										</div>
									</div>
								</div>
								</div>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
					</c:forEach>
					</div>
			</div>
			
			<div id="newPeriodBtn" class="panel-body" style="display:none;">
				<div class="pull-left">
					<a href="javascript:addNewPeriod();" id="new_btn" class="btn btn-sm btn-primary">添加新奖期</a>
				</div>
			</div>
			
			<div class="panel panel-success new_periods" id="lottery_series">
			</div>
			<div id="lottery_period" style="display:none;">
				<div class="panel panel-default period-panel">
				<div class="panel-heading">
					<div class="pull-left">奖期阶段<label class="series_level"></label></div>
					<div class="pull-right">
						<a href="javascript:;" class="close_btn">关闭</a>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-1">首期时间</label>
							<div class="col-sm-2">
								<input type="hidden" class="new_period" name="lotteryCode" value="${lottery.lotteryCode }"/>
								<input type="hidden" class="new_period" name="lotteryGroup"  value="${lottery.lotteryGroup }"/>
								<input type="hidden" class="new_period" name="lotteryName" value="${lottery.lotteryName }"/>
								<input type="hidden" class="new_period" name="seriesRule" value="${lottery.seriesRule }"/>
								<input type="hidden" class="new_period" name="totalLimitBets" value="${lottery.totalLimitBets }"/>
								<input type="hidden" class="new_period" name="totalLimitAmount" value="${lottery.totalLimitAmount }"/>
								
								<input type="hidden" class="new_period" name="seriesExpression" />
								<input type="text" class="form-control new_period" name="startTime" value=""
									checkType="isNotEmpty,time1" alt="首期时间不能为空！,首期时间格式必须是HH:mm:ss或者HH:mm！"/>
							</div>
							<label class="control-label col-sm-1">末期时间</label>
							<div class="col-sm-2">
								<input type="text" class="form-control new_period" name="endTime" value=""
									checkType="isNotEmpty,time1" alt="末期时间不能为空！,末期时间格式必须是HH:mm:ss或者HH:mm！"/>
							</div>
							<label class="control-label col-sm-1">奖期间隔</label>
							<div class="col-sm-1">
								<input type="text" class="form-control new_period" name="spanTime" value=""
								checkType="isNotEmpty,num1" alt="奖期间隔不能为空！,奖期间隔必须是正整数！"/>
							</div>
							<label class="control-label col-sm-1">开奖延迟</label>
							<div class="col-sm-1">
								<input type="text" class="form-control new_period" name="afterLotTime" value=""
									/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">奖期期数</label>
							<div class="col-sm-2">
								<input type="text" class="form-control new_period" name="totalTimes" value=""
								checkType="isNotEmpty,num1" alt="奖期期数不能为空！,奖期期数必须是正整数！"/>
							</div>
							<label class="control-label col-sm-1">抓取次数</label>
							<div class="col-sm-2">
								<input type="text" class="form-control new_period" name="catchTimes" value=""
								checkType="isNotEmpty,num1" alt="抓取次数不能为空！,抓取次数必须是正整数！"/>
							</div>
							<label class="control-label col-sm-1">截止时差</label>
							<div class="col-sm-1">
								<input type="text" class="form-control new_period" name="beforeLotTime" value=""
									checkType="isNotEmpty,num1" alt="截止时差不能为空！,截止时差必须是正整数！"/>
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<div class='pull-left' style="margin-top: 5px;">玩法配置</div>
				<div class="pull-left">
					<div class="row">
						<div class="col-sm-2 col-sm-offset-2">
							<a href="javascript:;" class="btn btn-sm btn-danger modifyLpm">修改玩法相关配置</a>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<input type="hidden" id="lpmSize" value="${lpmSize }">
			<div class="panel-body">
				<table class="table table-hover table-bordered table-condensed">
					<thead>
						<tr class="text-center success">
							<td>编号</td>
							<td>玩法名称</td>
							<td>描述</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lpmList }" var="lpm" varStatus="status">
							<!-- 不显示标识为已删除的玩法记录 -->
							<c:if test="${lpm.status!=2 }">
								<tr>
									<td>${status.index+1 }</td>
									<td>${lpm.playModel.modelName }</td>
									<td>${lpm.playModel.modelDesc }</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>


				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
					<c:forEach items="${bonusGroupList }" var="bg" varStatus="status">
						<c:if test="${status.index==0 }">
							<li class="active"><a href="#${bg.id }" role="tab"
								data-toggle="tab">${bg.name }</a></li>
						</c:if>
						<c:if test="${status.index!=0 }">
							<li class=""><a href="#${bg.id }" role="tab"
								data-toggle="tab">${bg.name }</a></li>
						</c:if>
					</c:forEach>
				</ul>
				<!-- Tab panes -->
				<div class="nav_content">
					<div class="tab-content">
						<c:forEach items="${bonusGroupList }" var="bg" varStatus="status">
							<c:if test="${status.index==0 }">
								<div class="tab-pane active" id="${bg.id }">
							</c:if>
							<c:if test="${status.index!=0 }">
								<div class="tab-pane" id="${bg.id }">
							</c:if>
							<table class="table table-bordered table-hover table-condensed">
								<thead>
									<tr class="text-center success">
										<td>玩法名</td>
										<td>中奖率</td>
										<td>理论奖金</td>
										<td>返奖率</td>
										<td>返点率</td>
										<td>利润率</td>
										<td>总值</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bg.bonusList }" var="lpb" varStatus="status2">
										<!-- 不显示标识为已删除的玩法记录 -->
										<c:if test="${lpb.status!=2 }">
										<tr>
											<td>${lpb.model.modelCode }</td>
											<td>${lpb.winningRate}</td>
											<td class="amount_class">${lpb.bonusAmount}</td>
											<td>${lpb.payoutRatio}</td>
											<td>${lpb.rebates}</td>
											<td>${lpb.margin}</td>
											<td>100%</td>
										</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
					</div>
					</c:forEach>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					总限额金额：<span class="text-danger">${lottery.totalLimitAmount }</span>
					&nbsp;&nbsp; 总限额注数：<span class="text-danger">${lottery.totalLimitBets }</span>
					&nbsp;&nbsp;
				</div>
				<div class="panel-body">
					<table class="table table-bordered table-hover table-condensed">
						<thead>
							<tr class="success text-center">
								<td align="center" valign="middle">玩法</td>
								<td align="center" valign="middle">号码总数量</td>
								<td align="center" valign="middle">理论中奖金额(元)</td>
								<td align="center" valign="middle">最大投注限额(元)</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lpmList }" var="lpm" varStatus="status">
								<c:if test="${lpm.status!=2 }">
									<tr>
										<td align="center" valign="middle">${lpm.playModel.modelName }</td>
										<td align="center" valign="middle">${lpm.playModel.totalBets }</td>
										<td class="amount_class cr_red" align="center" valign="middle">
											${lpm.playModel.winAmount }</td>
										<td class="amount_class cr_red" align="center" valign="middle">
											${lpm.limitAmount }</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	</div>
</body>
</html>