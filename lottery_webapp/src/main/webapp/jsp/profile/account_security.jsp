<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人中心-账户安全</title> 
	<%@ include file="../include/css_asset.jsp"%>	
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/user/account_security.js"></script>
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
				<ul class="accountUl clearfix">
					<li class="pageBg">登录密码
						<p class="mt10">
							<a href="javascript:;" class="underline color_blue "
								id="loginDiv-link">修改登录密码&nbsp;&gt;&gt;</a>
						</p>
						<input type="hidden" id="token" name="token" value="${token}">
						<div id="loginDiv">
							<p class="poPupTop"></p>
							<div class="poPupBg">
								<p class="poPupTitle">修改登录密码</p>
								<ul class="poPupBgUl">
									<li class="clearfix lh28">
										<div id="errorDiv" class="poPupBgDiv f14 errorHint"></div>
									</li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">原始密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
												<input checkType="isNotEmpty" alt="原始密码不能为空！" loc="errorDiv"
													type="password"  name="customerPwd"
													  class="checkedAttr customerPwd" style="width: 230px;" /></span></span>
										</div></li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">新密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
												<input checkType="isNotEmpty,length1" alt="新密码不能为空！,密码长度必须在6到26位之间！" loc="errorDiv"
													type="password"  name="newPwd" 
													 class="checkedAttr newPwd" style="width: 230px;" /></span></span>
											<p class="color_8d">最小6位字符</p>
										</div></li>
									<li class="clearfix mt5 lh28"><span class="poPupBgSpan">重复新密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
												<input checkType="isNotEmpty,length1" alt="新密码不能为空！,密码长度必须在6到26位之间！" loc="errorDiv"
													type="password"  name="confirmPwd"
													 class="checkedAttr confirmPwd" style="width: 230px;" /></span></span>
										</div></li>
								</ul>
								<p class="poPupBtn"></p>
							</div>
							<p class="poPupBot"></p>
						</div>
					</li>
					<li class="pageBg">资金密码
						<p class="mt10">
							<a href="javascript:;" class="underline color_blue"
								id="moneyDiv-link">修改资金密码&nbsp;&gt;&gt;</a>
						</p>
						<div id="moneyDiv">
							<p class="poPupTop"></p>
							<div class="poPupBg">
								<p class="poPupTitle">修改资金密码</p>
								<ul class="poPupBgUl">
									<li class="clearfix lh28">
										<div id="errorDiv2" class="poPupBgDiv f14 errorHint"></div>
									</li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">问题：</span>
										<div class="poPupBgDiv" id="question"></div>
										<input type="hidden" class="question" name="question"/>
										<input type="hidden" class="questionId" name="questionId"/>
									</li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">您的答案：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
											<input checkType="isNotEmpty" alt="答案不能为空！" loc="errorDiv2" name="answer"
													type="text" class="checkedAttr answer" style="width: 230px;" /></span></span>
										</div></li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">旧资金密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
											<input  checkType="isNotEmpty" alt="旧资金密码不能为空！" loc="errorDiv2" name="moneyPwd"
													type="password" class="checkedAttr moneyPwd" style="width: 230px;" /></span></span>
										</div></li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">新资金密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
											<input checkType="isNotEmpty" alt="新资金密码不能为空！" loc="errorDiv2" name="newPwd"
													type="password" class="checkedAttr newPwd" style="width: 230px;" /></span></span>
										</div></li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">重复密码：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
											<input checkType="isNotEmpty" alt="重复密码不能为空！" loc="errorDiv2" name="confirmPwd" 
													type="password" class="checkedAttr confirmPwd" style="width: 230px;" /></span></span>
										</div></li>
								</ul>
								<p class="poPupBtn"></p>
							</div>
							<p class="poPupBot"></p>
						</div>
					</li>
					<li class="pageBg">
						<c:if test="${havaQue==true }">
							安全问答
							<p class="mt10 color_8d">
								安全问答已设定
							</p>
						</c:if>
						<c:if test="${havaQue==false }">
							安全问答未设定
							<p class="mt10 color_red">
								<a href="javascript:;" class="underline color_red"
								id="answerDiv-link">安全问答设定&nbsp;&gt;&gt;</a>
							</p>
						</c:if>
						<div id="answerDiv">
							<p class="poPupTop"></p>
							<div class="poPupBg">
								<p class="poPupTitle">安全问答设定</p>
								<ul class="poPupBgUl">
									<li class="clearfix lh28">
										<div id="errorDiv3" class="poPupBgDiv f14 errorHint"></div>
									</li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">安全问题：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
												<input checkType="isNotEmpty" alt="安全问题不能为空！" loc="errorDiv3"
													type="text"  name="question"
													  class="checkedAttr question" style="width: 230px;" /></span></span>
										</div></li>
									<li class="clearfix mt10 lh28"><span class="poPupBgSpan">问题答案：</span>
										<div class="poPupBgDiv">
											<span class="smallInput"><span>
												<input checkType="isNotEmpty" alt="问题答案不能为空！" loc="errorDiv3"
													type="text"  name="answer" 
													 class="checkedAttr answer" style="width: 230px;" /></span></span>
										</div></li>
								</ul>
								<p class="poPupBtn"></p>
							</div>
							<p class="poPupBot"></p>
						</div>
					</li>
				</ul>
				<p class="line5 mt25"></p>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="accountTab">
					<thead>
						<tr>
							<th width="18%" height="39" align="center" valign="middle">#</th>
							<th width="36%" height="39" align="center" valign="middle">时间</th>
							<th width="21%" height="39" align="center" valign="middle">操作</th>
							<th width="25%" height="39" align="center" valign="middle">IP</th>
						</tr>
					</thead>
					<tbody id="_tbody">
						
					</tbody>
					<tr style="display: none;" class="_tr">
						<td height="39" align="center" valign="middle">21</td>
						<td height="39" align="center" valign="middle">2014-6-16
								15:46:48</td>
						<td height="39" align="center" valign="middle">登录</td>
						<td height="39" align="center" valign="middle">192.168.1.1</td>
					</tr>
				</table>
				<xb:foot ajax="true" />
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>