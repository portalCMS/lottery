<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首充活动模板</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/asset/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/jsp/js/activity/frec_activity.js"></script>
</head>
<body>
<%@ include file='../include/top.jsp'%>
	<div class="container">
		<div class="row">
			<%@ include file='../include/activity_menu.jsp'%>
			<div class="col-sm-10">
					<div class="panel panel-default">
					<div class="panel-heading">首充活动模板</div>
						<div class="panel-body">
						<span class="text-primary" >
								对象为新注册会员 且未充值，每个会员只有一次，必须绑定了银行卡。</span>
						<div class="clearfix"></div>
							<div class="split"></div>
							<div id="cretaActivity">
								<div class="form-horizontal" style="margin-top:5px;">
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">活动领奖方式：</label>
										<div class="col-sm-3">
											<select class="form-control" id="awardType" name="model">
												<option value="HAND">纯手动领奖</option>
												<option value="AUTO">全自动领奖</option>
												<option value="HAUTO">半自动领奖</option>\
												<option value="PAUTO">程序派发领奖</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">活动领奖途径：</label>
										<div class="col-sm-3">
											<select class="form-control" id="sourceType" name="source">
												<option value="CLIENT">客户端</option>
												<option value="WEB">网页</option>
												<option value="NONE">无限制</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动开始时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control " id="startTime" name="starttime"
											checkType="isNotEmpty" alt="活动开始时间不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动截止时间：</label>
										<div class="col-sm-3">
											<input type="text" readonly="readonly" class="form-control " id="endTime" name="endtime"
											checkType="isNotEmpty" alt="活动截止时间不能为空！"/>
										</div>
									</div>
									<div class="form-group _amountConfig">
										<label class="control-label col-sm-2">金额配送比例：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="rateAmount" name="rateAmount" placeholder="配置比例,百分比%"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">最低充值金额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control checkedAttr" id="minGameAmount" name="minGameAmount"
											checkType="isNotEmpty,decmal4" alt="最低游戏金额不能为空！,最低游戏金额必须为正浮点数！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">礼金封顶金额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control checkedAttr" id="maxAwardAmount" name="maxAwardAmount"
											checkType="isNotEmpty,decmal4" alt="礼金封顶金额不能为空！,礼金封顶金额必须为正浮点数！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">用户黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="blackUser" name="blackCustomer" placeholder="请以逗号分隔"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">IP黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="blackIP" name="blackIp" placeholder="请以逗号分隔"/>
										</div>
									</div>
									<span class="text-primary" >
											活动提款条件</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">投注流水倍数：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="betMultiple" name="betMultiple"/>
										</div>
									</div>
									<span class="text-primary" >
											活动信息简介</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动标题：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control checkedAttr" id="title" name="title"
											checkType="isNotEmpty" alt="活动标题不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动图标：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control checkedAttr" id="imgUrl" name="picurl" placeholder="简介图片url"
											checkType="isNotEmpty" alt="活动图标不能为空！"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动内容：</label>
										<div class="col-sm-10">
											<script id="editor" type="text/javascript" style="width:95%;height:250px;"></script>
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
											<a href="${contextPath }/activity/frecActivity.do" id="resetForm" class="btn btn-primary btn-sm btn-block">表单重置</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
</body>
</html>