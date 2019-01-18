<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>圈子管理-配额管理</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/user/quotamanage.js"></script>
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
					<a href="javascript:;" class="current" id="giveA">&nbsp;&nbsp;分配配额&nbsp;&nbsp;</a><a
						href="javascript:;" id="lossA">回收配额</a>
				</div>
				<div class="userTabBox" id="d1">
					<p class="mt20">
						<label>目标用户:</label> 
						<span class="mediumInput ml10"><span>
							<input type="text" id="userName"  style="width: 153px" />
						</span></span>
						<select class="select2 ml10" id="cuId" name="cuId" style="width: 153px;height: 35px;">
							<c:forEach items="${lowerlist }" var="cu">
								<option value="${cu.id }" name="${cu.rebates }">${cu.customerName }</option>
							</c:forEach>
							<c:if test="${lowerlist.size()==0 }"><option value="">暂无数据</option></c:if>
						</select>
					</p>
					
					<p class="p20 cpb color_8d">分配及回收配额功能仅支持自身子级用户，次级用户的返点无法管理</p>
					<p class="mt20 f14 ml20">
						<label>用户名称:</label>
						<span id="customerUserName" class="ml10"></span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<label class="ml30">返点:</label>
						<span id="userRebates" class="ml10"></span>
					</p>
					
					<p class="line1 mt10"></p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="accountTab" id="giveQuotaTb">
						<thead>
							<tr>
								<th width="20%" height="35" align="center" valign="middle"><span>分配</span>返点值</th>
								<th width="25%" height="35" align="center" valign="middle">用户已有配额</th>
								<th width="25%" height="35" align="center" valign="middle">我的剩余配额</th>
								<th width="30%" height="35" align="center" valign="middle"><span>分配</span>数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${quotalist }" var="quota">
									<tr>
										<td height="35" align="center" valign="middle">
											<fmt:formatNumber value="${quota.proportion }" pattern="0.0%" />
											<input type="hidden" value="${quota.proportion }" class="_qrb"/>
										</td>
										<td height="35" align="center" valign="middle" class="_uqc">0</td>
										<td height="35" align="center" valign="middle"class="_mqc">${quota.quota_count }</td>
										<td height="35" align="center" valign="middle">
										<span class="smallInput"> 
											<span> 
												<input type="text" style="width: 100px" class="_gqc"/>
												<input type="hidden" value="${quota.id }" class="_qid"/>
												<input type="hidden" class="_lqid"/>
											</span>
										</span>
										</td>
									</tr>
							</c:forEach>
						</tbody>
					</table>
					<p class="mt25 ">
						&nbsp; <a href="javascript:;" class="miniBtn fr allocationBtn"><span>确定分配</span></a>
						<a href="javascript:;" class="miniBtn fr recycleBtn"><span>确定回收</span></a>
						<input type="hidden" id="token" name="token" value="${token }" />
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>