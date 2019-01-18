<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置图片</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/ad/setImg.js"></script>
<style>
.img_display {
	background: #ECECEC;
	padding-bottom: 5px;
	margin-bottom: 10px;
	border-radius: 0px 0px 3px 3px;
}

.img_display img {
	cursor: pointer;
}
</style>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="showAdvers.do">广告管理</a></li>
			<li class="active ">设置广告位</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">广告位信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-1">所在页面</label>
						<div class="col-sm-5">
							<div class="form-control-static">${entity.pageName }</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">区域编码</label>
						<div class="col-sm-5">
							<div class="form-control-static">${entity.regionCode }</div>
							<input type="hidden" id="regionCode" value="${entity.regionCode }">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">尺寸限制</label>
						<div class="col-sm-5">
							<div class="form-control-static">${entity.size }</div>
							<input type="hidden" id="size" value="${entity.size }">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">图片数量</label>
						<div class="col-sm-5">
							<div class="form-control-static">${entity.count }张</div>
							<input type="hidden" id="count" value="${entity.count }">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1">描述介绍</label>
						<div class="col-sm-5">
							<div class="form-control-static">${entity.dsce }</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				图片设置<span class="text-muted">点击图片删除</span>
			</div>
			<div class="panel-body _img">
				
				<div class="split"></div>
				<a href="javascript:;" class="btn btn-block btn-primary btn-sm"
					id="_upload_img_btn"> 添加图片 </a>
			</div>
			<div class="img_display _imgDiv" style="display: none;">
					<img
						src="http://zcimg.zcool.com.cn/zcimg/e1b053e3ad0732f87520e6f25235"
						width="100%" />
					<div class="split"></div>
					<div class="img_info">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-1">链接：</label>
								<div class="col-sm-6">
									<input type="text" id="url" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-1">ALT：</label>
								<div class="col-sm-6">
									<input type="text" id="alt" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-1">打开：</label>
								<div class="col-sm-6">
									<label class="radio-inline"> <input type="radio" name="openType" checked="checked" value="2"/>
										当前页面跳转
									</label> <label class="radio-inline"> <input type="radio" name="openType" value="1"/>
										新窗口打开
									</label>
								</div>
							</div>
						</div>
					</div>
				</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<a href="javaScript:;" class="btn btn-primary _saveBtn">提交保存</a>
			</div>
		</div>
	</div>
</body>
</html>