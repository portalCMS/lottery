<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>圈子管理-开设下级</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/jquery.zclip.js"></script>
<script src="${contextPath }/jsp/js/team/create_account.js" ></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/team_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="iTab" id="userTab">
					<a href="javascript:;" class="current">代理</a><a href="javascript:;">会员</a><a href="javascript:;">链接开户</a>
				</div>
				<div class="userTabBox" id="proxy">
					<p class="mt20">
						&nbsp;&nbsp;&nbsp;<label>账户名：</label> <span
							class="mediumInput ml10"> <span> 
							<input type="text" class="proxyadd" id="customerName" name="customerName" checkType="isNotEmpty" alt="账户名不能为空" loc="userError">
							<input type="hidden" class="proxyadd" id="customerType" name="customerType" value="12001"/>
							<input type="hidden" class="proxyadd" id="token" name="token" value="${token }"/>
						</span>
						</span>
						<i id="userError"></i>
					</p>
					<!--  
					<p class="mt20">
						<label>用户昵称：</label> <span class="mediumInput ml10"> <span>
							<input type="text" class="proxyadd" id="customerAlias" name="customerAlias" checkType="isNotEmpty" alt="用户昵称不能为空" loc="aliasError">
							<div id="aliasError"></div>
						</span>
						</span>
					</p>
					-->
					<p class="mt20">
						<label>设置返点：</label> 
						<select class="select2 ml10 proxyadd _showProxyInput" style="width: 172px;height: 35px;"  id="quotaId" name="quotaId">
							<c:forEach items="${quotalist}" var="quota">
								<option value="${quota.id }"><fmt:formatNumber value="${quota.proportion }" pattern="0.0%" /> --剩余${quota.quota_count }</option>
							</c:forEach>
							<c:forEach items="${qtQuota }" var="qt">
							  <option value="${qt/1000 }"><fmt:formatNumber value="${qt/1000 }" pattern="#.#%"/></option>
							</c:forEach>
							<!-- <option value="0">其他返点</option>-->
						</select> 
						<span class="smallInput ml10 _pinput" style="display: none;">
						 <span> 
							<input type="text" id="proxyInput" name="minRebates" style="width:52px"/>
						 </span>
						</span>
					</p>
					<p class="line4 mt25"></p>
					<p class="mt25 ">
						&nbsp;<a href="javascript:;" class="miniBtn ml70 proxyBtn"><span>确定开户</span></a>
						<span class="ml10 color_8d">开户成功后，请复制生成的随机密码</span>
					</p>
				</div>

				<div class="userTabBox" id="member" style="display: none;">
					<p class="mt20">
						&nbsp;&nbsp;&nbsp;<label>账户名：</label> <span
							class="mediumInput ml10"> <span> 
							<input type="text" class="memberadd" id="customerName" name="customerName" checkType="isNotEmpty" alt="用户名不能为空" loc="userError1">
							<input type="hidden" class="memberadd" id="customerType" name="customerType" value="12002"/>
							<input type="hidden" class="memberadd" id="token" name="token" value="${token }"/>
						</span>
						</span>
						<i id="userError1"></i>
					</p>
					<!--  
					<p class="mt20">
						<label>用户昵称：</label> 
						<span class="mediumInput ml10"> <span>
							<input type="text" class="memberadd" id="customerAlias" name="customerAlias" checkType="isNotEmpty" alt="用户昵称不能为空" loc="aliasError1">
							<div id="aliasError1"></div>
						</span>
						</span>
					</p>
					-->
					
					<p class="mt20">
						<label>设置返点：</label> 
						<select class="select2 ml10 memberadd _showMemberInput" style="width: 172px;height: 35px;" id="quotaId" name="quotaId">
							<c:forEach items="${quotalist}" var="quota">
								<c:if test="${quota.proportion<=0.125 }">
									<option value="${quota.id }"><fmt:formatNumber value="${quota.proportion }" pattern="0.0%" />--剩余${quota.quota_count }</option>
								</c:if>
							</c:forEach>
							<c:forEach items="${qtQuota }" var="qt">
							  <option value="${qt/1000 }"><fmt:formatNumber value="${qt/1000 }" pattern="#.#%"/></option>
							</c:forEach>
							<!-- <option value="0">其他返点</option>-->
						</select>
						<span class="smallInput ml10 _minput" style="display: none;">
						 <span> 
							<input type="text" id="memberInput" name="minRebates" style="width:52px"/>
						 </span>
						</span>
					</p>

					<p class="line4 mt25"></p>
					<p class="mt25 ">
						&nbsp;<a href="javascript:;" class="miniBtn ml70 memberBtn"><span>确定开户</span></a>
						<span class="ml10 color_8d">开户成功后，请复制生成的随机密码</span>
					</p>
				</div>
				
				<div class="userTabBox" id="link" style="display: none;">
					<p class="mt20">
						<label>开户类型：</label> 
						<select class="select2 ml10 memberType" style="width: 172px;height: 35px;" id="memberType" name="memberType">
							<option value="12001">代理</option>
							<option value="12002">会员</option>
						</select>
					</p>
					
					<p class="mt10">
						<label>设置返点：</label> 
						<input id="userId" value="${customerkey.id }" type="hidden"/>
						<select class="select2 ml10 memberadd _showlink" style="width: 172px;height: 35px;" id="quotaId" name="quotaId">
<%-- 							<c:forEach items="${quotalist}" var="quota"> --%>
<%-- 								<c:if test="${quota.proportion<=0.075 and quota.quota_count>0 }"> --%>
<%-- 									<option value="${quota.id }"><fmt:formatNumber value="${quota.proportion }" pattern="0.0%" />--剩余${quota.quota_count }</option> --%>
<%-- 								</c:if> --%>
<%-- 							</c:forEach> --%>
							<c:forEach items="${qtQuota }" var="qt">
							  <option value="${qt/1000 }"><fmt:formatNumber value="${qt/1000 }" pattern="#.#%"/></option>
							</c:forEach>
							<!-- <option value="0">其他返点</option>-->
						</select>
						<span class="smallInput ml10 _linput" style="display: none;">
						 <span> 
							<input type="text" id="linkInput" name="minRebates" style="width:52px"/> %
						 </span>
						</span>
					</p>
					<p class="mt10 mb10">
						<label>链接地址：</label> 
						<span class="_links "><a href="javascript:;" class="color_blue"></a></span> 
					</p>
					<p class="line4 _linkLine4"></p>
					<p class="mt25 ">
						<a href="javascript:;" class="miniBtn ml70 _linkBtn"><span>确定开户</span></a>
					</p>
				</div>
			</div>
			<p class="boxBotBg"></p>
			
			<div class="pageFrBox lh24 mt10">
				<h3>开设下级</h3>
				<li class="line1"></li>
				<ul class="pointUL mt10">
					<li>
						输入用户名及返点，完成开户后，您将获得该账户的    <span class="color_yellow">随机密码</span>，使用此密码第一次登陆时将会进入注册流程，以完善账户信息。     该账户完成了注册流程后，您可以在【<a href="${contextPath }/team/show.html" class="color_blue underline">用户列表</a>】中查看此账户的状态。
					</li>
					<li>
						开设下级用户需要“配额”的支持，不同返点的配额开设不同的账户。如果您还未拥有配额，请与您的上级询问索要。
					</li>
					<li>
						【代理】账户可以继续开设下级，您可以在【<a href="${contextPath }/team/showqm.html" class="color_blue underline">配额管理</a>】中为他分配各返点的配额。
					</li>
					<li>
						【会员】账户无法开设下级，并且无法看见与<span class="color_yellow">圈子管理</span>相关的内容。
					</li>
				</ul>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>