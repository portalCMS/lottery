<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人资料</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
</head>
<body>
<%@ include file="../include/top.jsp"%>
	<div class="container">
		<form class="form-horizontal" action="updateUserData.do" method="post">
			<input type="hidden" name="token" id="token" value="${token }">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<p>基本资料</p>
						<label class="control-label" for="input01">昵称</label>
						<div class="controls">
							<input type="text" name="customerAlias" id="customerAlias"  value="${customerkey.customerAlias }" placeholder="placeholder" class="input-xlarge">
						</div>
					</div>
					<p>联系信息</p>
					<label class="control-label" for="input01">QQ</label>
					<div class="controls">
						<input type="text" name="qq" id="qq" value="${customerkey.qq }" placeholder="placeholder" class="input-xlarge">
					</div>
					<label class="control-label" for="input01">email</label>
					<div class="controls">
						<input type="text" name="email" id="email" value="${customerkey.email }" placeholder="placeholder" class="input-xlarge">
					</div>
				</div>
			</div>
			<div class="control-group" style="margin-top: 80px;">
				<div class="controls">
					<button class="btn btn-success" type="submit">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>