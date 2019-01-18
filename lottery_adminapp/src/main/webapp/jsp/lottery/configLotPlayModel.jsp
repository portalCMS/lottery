<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>玩法配置</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/lottery/playModel.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ul class="follow-list">
			<li class="active">玩法配置</li>
			<li>奖金组配置</li>
			<li>限额配置</li>
			<div class="clearfix"></div>
		</ul>
		<div class="clearfix _hidLevel" style="margin-top:10px;"> 
			<label class="pull-left help-block"><span>1</span><span>等奖：</span></label> 
			<input placeholder="中奖率" type="text" style="width:
				100px;" class="form-control pull-left" /> 
			<input placeholder="理论奖金" type="text" style="width:
				100px;margin-left: 18px;" class="form-control pull-left" />
			<a href="javascript:;" class="btn btn-sm btn-link 
				pull-left _del_level_btn">删除</a> 
 		</div>	
		<div class="panel panel-default">
			<div class="panel-heading">玩法信息</div>
			<div class="panel-body">
				<table class="table table-hover table-bordered table-striped">
					<thead>
						<tr class="success">
							<td width="10%">玩法编号</td>
							<td width="15%">玩法名称</td>
							<input id="lotteryGroup" type="hidden" value="${playList[0].lotteryGroup}"/>
							<c:choose>
								<c:when test="${playList[0].lotteryGroup=='SYXW'}">
									<td width="10%">理论中奖几率</td>
								</c:when>
								<c:otherwise>
									<td width="30%">中奖等级设置</td>
								</c:otherwise>
							</c:choose>
							<td width="30%">描述</td>
							<td width="15%">
								<label class="checkbox-inline"> 
									<input id="allSelect" type="checkbox"> 
									操作
								</label>
							</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${playList }" var="play">
							<tr class="_mtr">
								<td class="_mc">${play.modelCode}</td>
								<td class="_mn">${play.modelName}</td>
								<c:if test="${playList[0].lotteryGroup=='SYXW' }">
									<td class="_wr">${play.winingRate}</td>
								</c:if>
								<c:if test="${playList[0].lotteryGroup!='SYXW' }">
									<td class="_mlg">
										<div class="_levelGroup">
											<c:if test="${play.levelList!=null }">
												<c:forEach  items="${play.levelList }" var="level">
													<div class="clearfix _hidLevel" style="margin-top:10px;"> 
														<label class="pull-left help-block"><span>${level.awardLevel }</span><span>等奖：</span></label> 
														<input value="${level.winingRate }" type="text" style="width:
															100px;" class="form-control pull-left" /> 
														<input value="${level.winAmount }" type="text" style="width:
															100px;margin-left: 18px;" class="form-control pull-left" />
														<a href="javascript:;" class="btn btn-sm btn-link 
															pull-left _del_level_btn">删除</a> 
											 		</div>	
												</c:forEach>
											</c:if>
											<c:if test="${play.levelList==null }">
													<div class="clearfix _hidLevel" style="margin-top:10px;"> 
														<label class="pull-left help-block"><span>1</span><span>等奖：</span></label> 
														<input value="${play.winingRate }" type="text" style="width:
															100px;" class="form-control pull-left" /> 
														<input value="${play.winAmount }" type="text" style="width:
															100px;margin-left: 18px;" class="form-control pull-left" />
														<a href="javascript:;" class="btn btn-sm btn-link 
															pull-left _del_level_btn">删除</a> 
											 		</div>	
											</c:if>
										</div>
				  						<div class="row" style="margin-top:10px;">
										  <div class="col-xs-1"></div>
										  <div class="col-xs-8" style="margin-left:25px;">
											  <a href="javascript:;" class="btn btn-info btn-block 
					  							btn-sm _addPoutLeave">增加新奖级</a>
					  					  </div>
										</div>
				  						
	  								</td>
  								</c:if>
								<td class="_md">${play.modelDesc}</td>
								<td class="_ms">
									<label class="checkbox-inline"> 
										<c:if test="${play.selectedModel==true }">
											<input type="checkbox" class="_mid" checked='checked' value="${play.id}"> 启用此玩法
										</c:if>
										<c:if test="${play.selectedModel==false }">
											<input type="checkbox" class="_mid" value="${play.id}"> 启用此玩法
										</c:if>
									</label>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="hidden" id="playModelIdList" name="playModelIdList" >
						<input type="button" class="btn btn-primary" onclick="playForm.submitForm2()" value="下一步" > 
						<a href="${contextPath }/lottery/lotteryDetail.do" class="btn btn-link btn-sm" >返回</a>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>