<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邀请注册-完善账户资料</title> <%@ include file="../include/css_asset.jsp"%>
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/register/initPersonInfo.js"></script>
</head>
<body>
	<%@ include file="../include/simple-head.jsp"%>
	<input type="hidden" id="token" name="token" value="${token}">
	<!-- content -->
	<div class="k990 mt20">
		<div class="registTitle registTitle3">
			<span>修改登录密码</span><span>设置账户安全</span><span class="color_fff">完善资料</span><span>完成</span>
		</div>
		<div class="findPwBox">
			<div class="clearfix hidden">
				<div class="fl pl20">
					<span class="f18 color_000">基本资料：</span>
					<p class="line4 mt5"></p>
					<span id="errorDiv" class="f10"></span>
					<div class="clearfix f14 lh40 mt25">
						<span class="formListSpan">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span>
						<div class="formListDiv">
							<span class="maxInput"><span> 
							<input type="text" name="customerAlias" id="alias"  
							        class="_customerAlias  _checkedAttr"
									checkType="isNotEmpty,ecdInput" alt="亲，昵称不能为空哦！,亲，昵称只能输入数字及英文字母或汉字哦！"
									 loc="errorDiv" style="width: 225px;" />
							</span></span>
						</div>
					</div>
					<p class="mt30"></p>
					<span class="f18 color_000 mt25">联系信息：</span><span id="errorDiv2" class="f14"></span>
					<p class="line4 mt5"></p>
					<ul class="mt25">
						<li class="clearfix f14 lh40"><span class="formListSpan tr">QQ：</span>
							<div class="formListDiv">
								<span class="maxInput"><span> <input type="text" name="qq" 
									 loc="errorDiv2" style="width: 225px;" />
								</span></span>
							</div></li>
						<li class="clearfix f14 lh40 mt10"><span
							class="formListSpan tr">Email：</span>
							<div class="formListDiv">
								<span class="maxInput"><span> <input type="text" name="email" 
									 loc="errorDiv2" style="width: 225px;" />
								</span></span>
								<p class="mt25">
									<a href="javascript:;" class="maxBtn _commit"><span
										style="width: 241px;">确 定</span></a>
								</p>
							</div></li>
					</ul>
				</div>
				<div class="findPwText fr">
					<p>
						为了更方便的为您提供服务，我们希望能够与您保持联系。
					</p>
					<p class="mt10">
						我们尊重您的个人隐私，在本站您所有的数据都将经过本站数据中心加密存储，我们一直努力通过技术的力量让您在体验优质的服务同时，信息更加的安全。
					</p>
					<p class="mt10">
						您提交的昵称及联系方式在完成注册后，可以随时进行修改。
					</p>
				</div>
			</div>
		</div>
		<p class="boxBotBg"></p>
	</div>
	<!-- content End -->
	<%@ include file="../include/simple-footer.jsp"%>
</body>
</html>