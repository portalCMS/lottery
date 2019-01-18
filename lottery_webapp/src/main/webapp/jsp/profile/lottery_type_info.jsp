<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>个人中心-彩种信息</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/profile/lottery_type_info.js"></script>
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<c:forEach var="group" items="${lotteryGroups}">
					<tr>
						<td width="2%" height="39" align="left" ></td>
						<td width="10%" height="39" align="left">${group.lotteryGroupName}类：</td>
						<td width="90%" height="39" align="left" class="_lotteryTypeList">
							<c:forEach var="type" items="${group.lotteryList}">
								<a  href="javascript:void(0);" id="${type.lotteryCode}" class="label">
									${type.lotteryName}
								</a>
							</c:forEach>
						</td>
					</tr>
					</c:forEach>
					<p class="ml15 mt10 color_red" style="padding-bottom: 10px;">请点击彩种查看数据 </p>
					<p class="line5"></p>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th width="12%" height="39" align="center" valign="middle">玩法群</th>
							<th width="16%" height="39" align="left" valign="middle">玩法组</th>
							<th width="25%" height="39" align="left" valign="middle">玩法</th>
							<th width="25%" height="39" align="left" valign="middle">高奖金</th>
							<th width="25%" height="39" align="left" valign="middle">高返点</th>
						</tr>
					</thead>
					<tbody id="typeData"></tbody>
					<tr class="_trclone" style="display:none;">
						<td height="39" align="center" valign="middle">玩法群</td>
						<td height="39" align="left" valign="middle">玩法组</td>
						<td height="39" align="left" valign="middle">玩法</td>
						<td height="39" align="left" valign="middle">高奖金</td>
						<td height="39" align="left" valign="middle">高返点</td>
					</tr>
				</table>
				<xb:foot ajax="true"></xb:foot>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>