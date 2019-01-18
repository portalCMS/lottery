<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人中心-站内信</title> 
	<%@ include file="../include/css_asset.jsp"%>	
	<%@ include file="../include/js_asset.jsp"%>
	<script src="${contextPath }/jsp/js/user/webMsg.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/profile_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1" id="listBox">
				<div class="bgGray p10 pl20 ">
					<h3 class="color_8d">站内信列表 <a href="javascript:;" id="sendBtn" class="color_blue" style="margin-left: 540px">发送站内信</a></h3>
				</div>
				<p class="line5"></p>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="accountTab">
					<thead>
						<tr>
							<th width="8%" height="39" align="center" valign="middle">序号</th>
							<th width="20%" height="39" align="center" valign="middle">发信人</th>
							<th width="30%" height="39" align="center" valign="middle">主题</th>
							<th width="22%" height="39" align="center" valign="middle">时间</th>
							<th width="20%" height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody id="_tbody">
					</tbody>
					<tr style="display: none;" class="_tr">
						<td height="30" align="center" valign="middle"></td>
						<td height="30" align="center" valign="middle"></td>
						<td height="30" align="center" valign="middle"></td>
						<td height="30" align="center" valign="middle"></td>
						<td height="30" align="center" valign="middle">
							<a href="javascript:void(0);" class="color_blue underline _msgInfo">查看</a>
							&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="javascript:void(0);" class="color_blue underline _delete">删除</a>
							<input type="hidden" class="_msgId"/>
							<input type="hidden" class="_msgContent"/>
							<input type="hidden" class="_msgTitle"/>
							<input type="hidden" class="_msgStatus"/>
							<input type="hidden" class="_suname"/>
						</td>
					</tr>
				</table>
				<p class="line5"></p>
				<xb:foot ajax="true" />
			</div>
			<div class="pageFrBox_1" id="infoBox" style="display: none;height: 371px;">
					<div class="bgGray p10 pl20 ">
						<h3 class="color_8d">站内信详情<a id="backList" href="javascript:;" class="color_yellow  fr">返回</a><span class="fr" style="margin-right: 10px">|</span>
						<a id="callback" href="javascript:;" class="color_blue fr" style="margin-right: 10px">回复</a></h3>
					</div>
					<p class="line5"></p>
					<div id="msgContent" class="ml20">
						<h3 style="text-align: center;" class="mt10 mb10" id="msgTitle"></h3>
						<textarea rows="17" class="ml20" cols="110" disabled="disabled" style="resize: none;border-color: white;background-color: white;"></textarea>
						<input type="hidden" id="buname" />
					</div>
			</div>
		<!-- send -->
			<div class="pageFrBox_2" id="sendBox" style="display: none;height: 350px;">
					<div class="bgGray p10 pl20 ">
						<h3>发送站内信 <a href="javascript:;" id="sbackList" class="color_yellow fr">返回</a></h3>
					</div>
					<p class="line5"></p>
					<div  class="pageFrBox lh24 f14">
						<ul class="mtLi15">
							<li>
								<span class="formListSpan">收信人:</span>
								<span class="smallInput">
									 <span>
		 								<input type="text" class="_param" id="toUserName" />
									</span>
								</span>
								<label class="ml10">
									<input type="checkbox" id="sendUp" />发送给上级
								</label>
								<span class="color_red ml20" id="toUserNameError"></span>
							</li>
							<li>
								<span class="formListSpan">&nbsp;&nbsp;&nbsp;&nbsp;标题:</span>
								<span class="smallInput">
									 <span>
		 								<input type="text" value="" class="_param" id="title" />
									</span>
								</span>
								<span class="color_red ml20" id="titleError"></span>
							</li>
							<li>
								<span class="formListSpan">&nbsp;&nbsp;&nbsp;&nbsp;内容:</span>
		 						<textarea rows="6"  cols="60" id="message" class="_param"  style="resize: none;background-color: white;"></textarea>
		 						<span class="color_red mb25 ml20"  id="messageError"></span>
							</li>
							<li>
								<a href="javascript:;" class="miniBtn" id="sendMsgBtn" style="margin-left: 65px"><span>发送</span></a>
								<span class="color_red fr f14">站内信只能发送给自己的上级以及直属下级</span>
							</li>
						</ul>
					</div>
			</div>
		<!-- send end -->
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>