<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>圈子管理-下级管理</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/team/lowermanage.js"></script>
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
					<c:if test="${customerkey.rsvst4 == 1}">
						<a href="javascript:;" class="current">&nbsp;&nbsp;下级充值&nbsp;&nbsp;</a> 
						<a href="javascript:;" >调整返点</a>
					</c:if>
					<c:if test="${customerkey.rsvst4 != 1}">
						<a href="javascript:;" class="current">&nbsp;&nbsp;调整返点&nbsp;&nbsp;</a>
					</c:if>
				</div>
				<c:if test="${customerkey.rsvst4 == 1}">
					<div class="userTabBox" id="d1">
						<p class="mt20">
							<label>目标用户:</label> 
							<span class="mediumInput ml10">
								<span>
									<input type="text" id="toUserName" name="toUserName" class="_param"/>
								</span>
							</span>
							<span id="error" class="color_red"></span>
						</p>
						<p class="mt20">
							<label>充值金额:</label>
							<span class="mediumInput ml10">
								<span>
									<input type="text" id="toMoney" name="toMoney" class="_param"/>
								</span>
							</span>
						</p>
						<p class="mt20">
							<label>资金密码:</label>
							<span class="mediumInput ml10">
								<span>
									<input type="password" id="moneryPwd" name="moneryPwd" class="_param"/>
								</span>
							</span>
						</p>
						<p class="line4 mt25"></p>
						<p class="mt25 ">
							&nbsp; <a href="javascript:;" class="miniBtn ml60 _mBtn"><span>确定充值</span></a>
						</p>
					</div>
				</c:if>
				<div class="userTabBox" <c:if test="${customerkey.rsvst4 == 1}"> style="display: none;"</c:if> id="d2">
					<p class="mt20">
						<label>目标用户:</label>
						<span class="mediumInput ml10">
							<span>
								<input type="text" id="toQUserName" name="toUserName" class="_param1"/>
							</span>
						</span>
						<span id="error1" class="color_red"></span>
					</p>
					<p class="mt20">
						<label>调整返点:</label>
						<select class="select2 _param1 ml10" style="width: 172px;height: 35px;" id="toQuota" name="toQuota" >
								<option value="">请输入目标用户</option>
						</select>
					</p>
					<p class="mt20">
						<label>资金密码:</label>
						<span class="mediumInput ml10">
							<span>
								<input type="password" id="moneryQPwd" name="moneryPwd" class="_param1"/>
							</span>
						</span>
					</p>
					<p class="line4 mt25"></p>
					<p class="mt25 ">
						&nbsp; <a href="javascript:;" class="miniBtn ml60 _qBtn"><span>确定调整</span></a>
					</p>
				</div>
				<p class="line5 mt20"></p>
				<p class="p20 cpb color_8d">
					Tip：下级充值与调整返点功能仅支持自身子级用户，次级用户的返点无法管理。
					且返点只能提升但不能高于或等于自己的返点。
				</p>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>