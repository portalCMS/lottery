<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问题反馈</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/feedback.js"></script>
</head>
<body>
	<%@ include file="include/head.jsp"%>
	<!-- content -->
	<div class="k990">
		<!-- <p class="pagePo mt25">
			<a href="index.html">首页</a>意见反馈
		</p> -->
		<div class="clearfix mt15">
			<div class="helpL fl">
				<div class="p10 lh22 helpBorder">
					<h3 class="f18">:) 非常感谢您</h3>
					<p class='line1 mt5'></p>
					<div class="mt5 color_8d">
						我们非常重视您所反馈的问题，反馈受理后我们将第一时间修复完善网站。 <br>
						提出的问题，我们会根据重要程度定期给予反馈者一定数额金额奖励。<br>
					</div>
					<p class="line1 mt5"></p>
					<span class="color_yellow">优化建议：</span>
					告诉我们您期望的新功能或彩种，我们一起将他实现。
					<br>
					<span class="color_yellow">缺陷反馈：</span>
					请告诉我们您所发现网站中现有功能存在的缺陷，我们非常重视此类问题，核实后将在第一时间修复。
				</div>
				<p class="boxShadow"></p>
			</div>
			<div class="w740 fr">
				<p class="i3Line"></p>
				<p class="i3BoxTitle">
					<span>问题反馈</span>
				</p>
				<div class="i3Box2 relative">
					<div class="fdTable">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="17%" height="50" align="right" valign="middle">反馈类型：</td>
								<td width="2%" height="50" valign="middle">&nbsp;</td>
								<td width="81%" height="50" align="left" valign="middle"><select
									class="fdSelect _addParam" id="typeName">
										<option value="优化建议"
											<c:if test="${vo.typeName=='优化建议' }">selected</c:if>>优化建议</option>
										<option value="缺陷反馈"
											<c:if test="${vo.typeName=='缺陷反馈' }">selected</c:if>>缺陷反馈</option>
										<option value="投诉建议"
											<c:if test="${vo.typeName=='投诉建议' }">selected</c:if>>投诉建议</option>
										<option value="其他"
											<c:if test="${vo.typeName=='其他建议' }">selected</c:if>>其他</option>
								</select></td>
							</tr>
							<tr>
								<td height="50" align="right" valign="middle">页面名称：</td>
								<td height="50" valign="middle">&nbsp;</td>
								<td height="50" align="left" valign="middle"><input
									type="text" value="${vo.pageName }" id="pageName"
									class="fdInput _addParam" /></td>
							</tr>
							<tr>
								<td height="10" align="right" valign="middle"></td>
								<td height="10" valign="middle"></td>
								<td height="10" align="left" valign="middle"></td>
							</tr>
							<tr>
								<td height="50" align="right" valign="top">描述：</td>
								<td height="50" valign="middle">&nbsp;</td>
								<td height="50" align="left" valign="top"><textarea role=""
										id="dsce" rows="" class="fdTextarea _addParam">${vo.dsce }</textarea></td>
							</tr>
							<tr>
								<td height="50" align="right" valign="middle">&nbsp;</td>
								<td height="50" valign="middle">&nbsp;</td>
								<td height="50" align="left" valign="middle"
									class="f12 color_8d">请尽量描述清楚该问题发生的情况及环境</td>
							</tr>
							<tr>
								<td height="50" align="right" valign="middle">验证码：</td>
								<td height="50" valign="middle">&nbsp;</td>
								<td height="50" align="left" valign="middle"><input
									type="text" value="" id="picCode" class="fdInput _addParam"
									style="width: 100px;" /> <a href="javascript:;" class="ml10"><img
										src="createPic.html" class="_loginCode" width="98" height="33" /></a></td>
							</tr>
							<tr>
								<td height="50" align="right" valign="middle">&nbsp;</td>
								<td height="50" valign="middle">&nbsp;</td>
								<td height="60" valign="bottom"><a href="javascript:;"
									class="btn3 small _addBtn"><span>提交反馈</span></a></td>
							</tr>
						</table>
					</div>
					<p class="boxShadow"></p>
				</div>
			</div>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="include/footer.jsp"%>

</body>
</html>