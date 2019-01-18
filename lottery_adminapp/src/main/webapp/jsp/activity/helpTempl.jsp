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
<script type="text/javascript" src="${contextPath }/jsp/js/activity/help_activity.js"></script>
</head>
<body>
<%@ include file='../include/top.jsp'%>
	<div class="container">
		<div class="row">
			<%@ include file='../include/activity_menu.jsp'%>
			<div class="col-sm-10">
					<div class="panel panel-default">
					<div class="panel-heading">亏损用户救援活动模板</div>
						<div class="panel-body">
							<span class="text-primary" >
								对象为亏损额到达一定限额的用户。</span>
							<div class="clearfix"></div>
							<div class="split"></div>
							<div id="createIssuePanle">
								<div class="form-horizontal" style="margin-top:5px;">
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">活动领奖方式：</label>
										<div class="col-sm-3">
											<select class="form-control" id="actType" name="actType">
												<option value="1">纯手动领奖</option>
												<option value="2">全自动领奖</option>
												<option value="3">半自动领奖</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动开始时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _startTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动截止时间：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _endTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">最低充值金额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _lotTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">最低亏损金额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _lotTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">最高账户余额：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _lotTime" value="00:00:00"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">充值累计周期：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _spanTime" placeholder="以天为单位"/>
										</div>
									</div>
									<div id="configDiv">
									<div class="form-group _amountConfig">
										<label class="control-label col-sm-2">金额配置区域<span class="_cfIndex">1</span>：</label>
										<div class="col-sm-2">
											<input type="text" class="form-control _spanTime" placeholder="最小金额"/>
										</div>
										<div class="col-sm-2">
											<input type="text" class="form-control _spanTime" placeholder="最大金额"/>
										</div>
										<div class="col-sm-2">
											<input type="text" class="form-control _spanTime" placeholder="赠送金额"/>
										</div>
										<div class="col-sm-2">
											<a href="javascript:;" class="btn btn-default btn-sm _addConfig"
											style="margin-top: 2px;">添加</a>
											<a href="javascript:;" class="btn btn-default btn-sm _deleteConfig" 
											style="margin-left:10px;margin-top: 2px;">删除</a>
										</div>
									</div>
									</div>
									<div id="configDiv1">
									<div class="form-group _amountConfig">
										<label class="control-label col-sm-2">金额配送比例：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _spanTime" placeholder="配置比例,百分比%"/>
										</div>
										<div class="col-sm-2">
											<a href="javascript:;" class="btn btn-default btn-sm _deleteConfig" 
											style="margin-left:10px;margin-top: 2px;">删除</a>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">用户黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _title" placeholder="请以逗号分隔"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">IP黑名单：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _title" placeholder="请以逗号分隔"/>
										</div>
									</div>
									<span class="text-primary" >
											活动信息简介</span>
									<div class="clearfix"></div>
									<div class="split"></div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动标题：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _title" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">活动图标：</label>
										<div class="col-sm-9">
											<input type="text" class="form-control _title" placeholder="简介图片url"/>
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
											<a href="#" class="btn btn-primary btn-sm btn-block">创建活动</a>
										</div>
										<div class="col-sm-2">
											<a href="#" class="btn btn-primary btn-sm btn-block">取消</a>
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