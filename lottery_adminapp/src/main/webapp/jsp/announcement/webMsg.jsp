<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>站内信</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" charset="utf-8"
	src="${contextPath }/jsp/js/article/webMsg.js"></script>
</head>
<body>
<%@ include file='../include/top.jsp'%>
	<div class="container">
		<div class="row">
<!-- 		<div class="col-sm-2"> -->
<!-- 			<div class="list-group _names"> -->
<%-- 				<a href="${contextPath }/article/showWebMsg.do" class="list-group-item active" id="web_msg_menu">  --%>
<!-- 					站内信管理 -->
<!-- 				</a> -->
<!-- 			</div> -->
<!-- 			</div> -->
			<div class="col-sm-12">
					<div class="panel panel-default">
					<div class="panel-heading">站内信管理</div>
						<div class="panel-body">
							<div id="cretaActivity">
								<div class="form-horizontal" style="margin-top:5px;">
									<div class="form-group">
										<label for="order_type" class="col-sm-2 control-label">下级用户：</label>
										<div class="col-sm-2">
											<select class="form-control _param" id="msgType" >
												<option value="0">不含下级</option>
												<option value="1">包含下级</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">对象用户：</label>
										<div class="col-sm-3">
											<input type="text" class="form-control _param" id="toUserName" />
											<span class="text-danger _userNameError" style="display: none;"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">信息标题：</label>
										<div class="col-sm-8">
											<input type="text" class="form-control _param" id="title"/>
											<span class="text-danger _titleError" style="display: none;"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2">信息内容：</label>
										<div class="col-sm-3">
											<textarea rows="5" cols="100" class="_param" id="message"></textarea>
											<span class="text-danger _messageError" style="display: none;"></span>
										</div>
									</div>
									
									<div class="form-group">
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2"></label>
										<div class="col-sm-2">
											<a href="javascript:;" id="sendBtn" class="btn btn-primary btn-sm btn-block">发送信息</a>
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