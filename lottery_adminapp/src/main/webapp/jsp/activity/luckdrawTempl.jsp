<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/activity/luck_activity.js"></script>
</head>
<body>
<%@ include file='../include/top.jsp'%>
	<div class="container">
		<div class="row">
			<%@ include file='../include/activity_menu.jsp'%>
			<div class="col-sm-10">
					<div class="panel panel-default">
					<div class="panel-heading">抽奖活动模板</div>
						<div class="panel-body">
							<div id="cretaActivity">
								<div class="form-horizontal" style="margin-top:5px;">
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">活动对象：</label>
										<div class="col-sm-3">
											<select class="form-control" id="userType" name="userType">
												<option value="ALL">所有用户</option>
												<option value="AGENT">代理</option>
												<option value="NUMBER">会员</option>
											</select>
										</div>
										
										<label class="control-label col-sm-2">抽奖彩金总限额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control checkedAttr _name" 
											id="maxAwardAmount" name="maxAwardAmount"  placeholder="元（不包含安慰奖金额）"
											checkType="isNotEmpty,decmal4" alt="抽奖彩金总限额不能为空！,抽奖彩金总限额必须为正浮点数！"/>
										
											<label class="checkbox-inline"> <input type="checkbox" id="noMaxLimit"/>不设限额
											</label> 
										</div>
										
									</div>
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">活动类型：</label>
										<div class="col-sm-3">
											<select class="form-control" id="actType" name="type">
												<option value="LORC">单笔充值</option>
												<option value="LTRC">累计充值</option>
												<option value="LBET">累计投注</option>
												<option value="LAWA">累计中奖</option>
											</select>
										</div>
										
										<label for="order_type" class="col-sm-5 control-label">（超出设定金额提示：恭喜，您获得了安慰奖！）</label>
									</div>
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">领奖方式：</label>
										<div class="col-sm-3">
											<select class="form-control" id="awardType" name="model">
												<option value="HAUTO">半自动领奖</option>
												<option value="HAND">纯手动领奖</option>
												<option value="AUTO">全自动领奖</option>
												<option value="PAUTO">程序派发领奖</option>
											</select>
										</div>
										
										<label for="order_type" class="col-sm-2 control-label">领奖途径：</label>
										<div class="col-sm-3">
											<select class="form-control _name" id="sourceType" name="source">
												<option value="NONE">无限制</option>
												<option value="CLIENT">客户端</option>
												<option value="WEB">网页</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">预热时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control _name" id="preTime" name="pretime"
											checkType="isNotEmpty" alt="活动预热时间不能为空！"/>
										</div>
										
										<label class="control-label col-sm-2">开始时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control _name" id="startTime" name="starttime"
											checkType="isNotEmpty" alt="活动开始时间不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">结束时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control _name" id="endTime" name="endtime"
											checkType="isNotEmpty" alt="活动截止时间不能为空！"/>
										</div>
										
										<label class="control-label col-sm-2">关闭时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control _name" id="closeTime" name="closetime"
											checkType="isNotEmpty" alt="活动关闭时间不能为空！"/>
										</div>
									</div>
<!-- 									<div class="form-group"> -->
<!-- 										<label class="control-label col-sm-2">最低游戏金额：</label> -->
<!-- 										<div class="col-sm-3"> -->
<!-- 											<input type="text" class="form-control checkedAttr" id="minGameAmount" name="minGameAmount" -->
<!-- 											checkType="isNotEmpty,decmal4" alt="最低游戏金额不能为空！,最低游戏金额必须为正浮点数！"/> -->
<!-- 										</div> -->
										
<!-- 										<label class="control-label col-sm-2">礼金封顶金额：</label> -->
<!-- 										<div class="col-sm-3"> -->
<!-- 											<input type="text" class="form-control checkedAttr" id="maxAwardAmount" name="maxAwardAmount" -->
<!-- 											checkType="isNotEmpty,decmal4" alt="礼金封顶金额不能为空！,礼金封顶金额必须为正浮点数！"/> -->
<!-- 										</div> -->
<!-- 									</div> -->
									<span class="text-primary" >
											抽奖参数设定</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									（注：如果高奖级送出的个数被限制，将自动过渡到低一级的奖级中分配，依次过渡，直到过渡到最低奖级）
									<p/>
									<div id="awardDiv">
									<div class="_awrdArea" style="margin-top: 2px;background-color: #E7E7E7;">
									<div class="form-group _amountConfig">
										<label class="col-sm-2" 
										style="color:#fff;margin-left:15px;background-color: #008000;font-size: 20px;">
										&nbsp;&nbsp;&nbsp;&nbsp;<span class="_cfIndex">1</span>等奖奖区</label>
									</div>
									<div class="form-group _amountConfig">
										<label class="control-label col-sm-2">单笔充值金额  ：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _minAmount" name="minAmount" placeholder="元（包含该金额）"/>
										</div>
										<label class="control-label col-sm-1">≤&nbsp;&nbsp;&nbsp;&nbsp; </label>
										<div class="col-sm-3">
											<input type="text" class="form-control _maxAmount" name="maxAmount" placeholder="元（包含该金额）"/>
										</div>
										<input class="_awardRank" name="awardRank" type="hidden"/>
									</div>
									<table class="table _mytb" style="width: 94%;margin-left: 28px;">
									   <thead>
									      <tr>
									         <th width="80px;">奖级</th>
									         <th width="100px;">金额</th>
									         <th width="100px;">概率</th>
									         <th width="100px;">
											 <select class="_cds" name="cycleDays">
												<option value="1">每天送出</option>
												<option value="99999999">总计送出</option>
											</select>
											</th>
<!-- 									         <th width="100px;">增加标准</th> -->
									         <th width="80px;">操作</th>
									      </tr>
									   </thead>
									   <tbody id="awardTb">
									      <tr>
									         <td><label class="control-label"><span class="_li">1</span>级</label></td>
									         <td><input type="text" class="form-control"  name="awardAmount"/></td>
									         <td><input type="text" class="form-control"  name="awardCount"/></td>
									         <td>
									         <input type="text" class="form-control"  name="awardChance"/>
									         <input class="_awardLevel" name="awardLevel" type="hidden"/>
									         </td>
<!-- 									         <td> -->
<!-- 													<select class="form-control" name="source"> -->
<!-- 														<option value="NO">不增加</option> -->
<!-- 														<option value="CHARGE">充值量</option> -->
<!-- 														<option value="BET">投注量</option> -->
<!-- 													</select> -->
<!-- 											</td> -->
									         <td>
													<a href="javascript:;" class="btn btn-default btn-sm _addLevel" 
													style="margin-left:5px;">加</a>
													<a href="javascript:;" class="btn btn-default btn-sm _delLevel" 
													style=";margin-left:5px;">减</a>
											</td>
									      </tr>
									   </tbody>
									</table>
									<div class="form-group">
										<div style="width: 47%;float:left;">
											<a href="javascript:;" class="btn btn-default btn-block _addAwardArea" 
										 	style="margin-left:30px;margin-top: 2px;">点我，加个奖区</a>
										</div>
										<div style="width: 47%;float: right;">
											<a href="javascript:;" class="btn btn-default btn-block _deleteAwardArea" 
										 	style="margin-right:25px;margin-top: 2px;float: right;">点我，删除奖区</a>
										</div>
									</div>
									</div>
								</div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">用户黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _name" id="blackUser" name="blackCustomer" placeholder="请以逗号分隔"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">IP黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _name" id="blackIP" name="blackIp" placeholder="请以逗号分隔"/>
										</div>
									</div>
									
									<span class="text-primary" >
											活动提款条件</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">投注流水倍数：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _name" id="betMultiple" name="betMultiple"/>
										</div>
									</div>
									<span class="text-primary" >
											活动信息简介</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动标题：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control checkedAttr _name" id="title" name="title"
											checkType="isNotEmpty" alt="活动标题不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动图标：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control checkedAttr _name" id="imgUrl" name="picurl" placeholder="简介图片url"
											checkType="isNotEmpty" alt="活动图标不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动内容：</label>
										<div class="col-sm-10">
											<script id="editor" name="summary" type="text/javascript" style="width:95%;height:250px;"></script>
											<br />
										</div>
									</div>
									
									<div class="form-group">
									</div>
									<div class="form-group">
										<div class="col-sm-2 col-sm-offset-1">
											<a href="javascript:;" id="createActivity" class="btn btn-primary btn-sm btn-block">创建活动</a>
										</div>
										<div class="col-sm-2">
											<a href="${contextPath }/activity/showBetTempl.do" id="resetForm" class="btn btn-primary btn-sm btn-block">表单重置</a>
										</div>
									</div>
								</div>
							</div>
						</div>
			</div>
	</div>
</body>
</html>