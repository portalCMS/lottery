<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<style type="text/css">
#box{border: 1px solid #2e2e2e;width:250px;height: 150px;
            border-radius:5px;  
            display: none; 
        }
  #hd{font-size: 12px;padding: 1px;}
  #cnt{padding: 1px;}
  #cnt div{margin: 5px;}
   #cnt div span{margin-right: 10px;}
  </style>
<title>圈子管理-用户列表</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/team/team_list.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<!-- content -->
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<%@ include file="../include/team_menu.jsp"%>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1 cpb">
				<div class="clearfix p10">
					<h3 class="color_red fl _teamName">
						
					</h3>
				</div>
				<p class="line5 "></p>
				<div class="mt5 p10 ">
					返点：<span class="ml10 color_red mr20 _userRebates"> </span> 
					直接下级人数：<span class="ml10 color_red mr20 _directSub"></span>
					全部成员人数：<span class="ml10 color_red mr20 _allTeam"> </span>
				</div>
				<p class="line5 mt5"></p>
				<ul class="breadList clearfix bgGray _bread">
					<li><a href="javaScript:void(0);" class="color_blue underline">总销量</a> <span
						class="color_8d"></span></li>
					<li><label>&gt;</label></li>
					<li><a href="javaScript:void(0);" class="color_blue underline"></a> <span
						class="color_8d"></span></li>
					<li><label>&gt;</label></li>
					<li><span></span></li>
				</ul>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="queryTable" class="line5">
					<tr>
						<td width="15%" height="50" align="left" valign="middle" colspan="2">&nbsp;用户名：
							<span class="smallInput"> 
								<span><input type="text" id="customerName" style="width: 100px" /></span>
							</span>
							<span class="color_red">&nbsp;&nbsp;用户名检索将覆盖全体成员，其他设置条件只针对直属下级</span>
						</td>
					</tr>
					<tr>
						<td width="8%" height="50" align="left" valign="middle"><span style="margin-left: 10px;">&nbsp;</span>返点：
							<span class="smallInput"> <span> 
							<input type="text" id="rebates" style="width: 100px" />
							</span>
						</span>
						</td>
						<td width="20%" height="50" align="left" valign="middle">用户余额：
							<span class="smallInput"> 
								<span> 
									<input type="text" id="userCash1" style="width: 100px" />
								</span>
							</span>
							至 <span class="smallInput"> 
								<span> 
									<input type="text" id="userCash2" style="width: 100px" />
								</span>
							</span>
						</td>
					</tr>
					<tr>
						<td width="8%" height="50" align="left" valign="middle"><span style="margin-left: 10px;">&nbsp;</span>状态：
							<select class="select2 _params" style="width: 80px;" id="customerOnlineStatus">
								<option value="">全部</option>
								<option value="11002">在线</option>
								<option value="11001">离线</option>
						</td>
						<td width="15%" height="50" align="left" valign="middle"><span style="margin-left: 22px;">&nbsp;</span>排序：
							<select class="select2 _params" style="width: 120px;" id="sortKey">
								<option value="create_time">注册时间</option>
								<option value="rebates">返点级别</option>
								<option value="cash">账户余额</option>
							</select>
							<span style="margin-left: 25px;">&nbsp;</span>
							<a href="javascript:void(0);" class="miniBtn _secBtn" id="queryTeamUserByKey">
								<span style="min-width: 80px;">查 询</span></a>
						</td>
					</tr>
				</table>
			</div>
			<div class="boxBotBg"></div>

			<div class="pageFrBox_1 mt10">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="line5">
					<thead>
						<tr>
							<th height="39" align="center" valign="middle">账户名</th>
							<th height="39" align="center" valign="middle">类型</th>
							<th height="39" align="center" valign="middle">团队人数</th>
							<th height="39" align="center" valign="middle">返点[%]</th>
							<th height="39" align="center" valign="middle">开户时间</th>
							<th height="39" align="center" valign="middle">余额</th>
							<th height="39" align="center" valign="middle">状态</th>
							<th height="39" align="center" valign="middle">操作</th>
						</tr>
					</thead>
					<tbody id="data">
						
					</tbody>
					<tr style="display: none;" class="_tr">
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle"></td>
							<td height="39" align="center" valign="middle">
								<a href="javascript:void(0);" class="color_blue underline _detailInfo">团队</a>
								<input type="hidden" value="" />
							</td>
					</tr>
				</table>
				<xb:foot ajax="true" />
			</div>
			<div id="box">
		        <div id="hd">
		            <a id="close" style="float: right;padding-right: 4px;cursor: pointer;" herf="javascript:void(0);">关闭</a><h3 id="title">这里是标题</h3>
		        </div>
		        <p class="line5 "></p>
		        <div id="cnt">
	            	<div id="yesterday">昨日新增：</div>
	            	<div id="week">本周新增：</div>
	            	<div id="month">本月新增：</div>	
		        </div>
		    </div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>