<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-个人资料</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/profile/info.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="p20 cpt cpb">
					<ul class="mtLi15">
						<li>
							<h3>基本信息</h3>
							<p class="line4 mt5"></p>
						</li>
						<li>
							<span>&nbsp;&nbsp;用户：</span>
							<span class="mediumDisableInput">
								<span> 
									<input type="text" readonly="readonly" id="userName" value="${user.customerName }" style="width: 225px;" />
								</span>
							</span>
						</li>
						<li>
							<span>&nbsp;&nbsp;昵称：</span>
							<span class="mediumInput">
								<span>
									<input type="text" class="_addparam" value="${user.customerAlias }" name="customerAlias" id="customerAlias"
									  checkType="isNotEmpty,ecdInput" alt="亲，昵称不能为空哦！,亲，昵称只能输入数字及英文字母或汉字哦！" loc="erroralias"
									  style="width: 225px;" />
									<input class="_addparam" type="hidden" id="token" name="token" value="${token }"/>
								</span>
							</span>
							<span id="erroralias"></span>
							<span class="ml5 color_red _aliaserror">已经被占用</span></li>
						<li>
							<h3 class="mt30">
								联系方式<span class="f12 color_8d fw ml10">填写联系方式方便客服人员为您服务，数据已经过加密处理，请放心。</span>
							</h3>
							<p class="line4 mt5"></p>
						</li>
						<li>
							<span>&nbsp;&nbsp;&nbsp;QQ：</span>
							<span class="mediumInput">
								<span> 
									<input type="text" class="_addparam" name="qq" id="qq" value="${user.qq }" style="width: 225px;" />
								</span>
							</span>
						</li>
						<li>
							<span>Email：</span>
							<span class="mediumInput">
								<span>
									<input type="text" class="_addparam" name="email" id="email" value="${user.email }" style="width: 225px;" />
								</span>
							</span>
						</li>
						<li>
							<p class="line5"></p> &nbsp;<a class="mediumBtn ml40 mt15 _saveBtn">
								<span>&nbsp;&nbsp;提交保存&nbsp;&nbsp;</span>
						</a>
						</li>
					</ul>
				</div>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>