<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/default.js"></script>
</head>
<body>
	<div class="slider-bar" id="sliderBar">
		<div class="hd"><a href="javascript:void(0);" id="sliderBar_close" class="close"></a>联系上级</div>
		<div class="bd">
			<!--  -->
			<a href="${contextPath }/showWebMsg.html" target="_blank" class="letters"></a>
		</div>
	</div>
	<%@ include file="include/head.jsp"%>
	<div class="k990">
		<div class="clearfix mt10">
			<div class="i3Box1 fl relative">
				<p class="i3BoxTitle ">
					<span>每日签到拿大奖</span>
				</p>
				<div class="i3Box1Div pb25">
					<ul class="lh24 pt10">
						<li>
							我的积分：<span class="_integral color_yellow">${integral.integral }</span>
						</li>
						<li>
							我的等级：<span class="rT3 _level"> <c:forEach begin="1" end="${integral.level }" var="i">
										<span><img src="jsp/images/xing.png" width="14" height="13" />&nbsp;</span>
									 </c:forEach></span>
						</li>
						<li class="_continuou">已连续签到<span class="ml5 mr5 color_yellow">${integral.continuou }</span>天</li>
					</ul>
					<p class="tc mt15">
						<c:if test="${integral.registration == true }">
							<a href="javascript:;" class="smallDisable" ><span style="width: 160px">今日已签到</span></a>
						</c:if>
						<c:if test="${integral.registration != true}">
							<a href="javascript:;" class="smallBtn _todyIntegral" ><span style="width: 160px">今日签到【${integral.todyIntegral }积分】</span></a>
						</c:if>
					</p>
				</div>
				<p class=" boxShadow"></p>
				
				<p class="i3BoxTitle">
					<span>网站公告</span>
				</p>
				<div class="i3Box1Div">
					<ul class="i3Box1List pt10 pb10">
						<c:forEach items="${ams }" var="am">
							<li><a href="javaScript:;" id="${am.id }" class="_showNotice <c:if test="${am.keyMark==10002 }">color_red</c:if>">${am.title }</a></li>
						</c:forEach>
					</ul>
				</div>
				<p class="boxShadow"></p>
			</div>
			<div class="playDiv fr">
				 <!--图片列表，除第一张显示外，其余隐藏-->
			        <ul> 
			        	<li style="display: block;" title="注册就送8.8元"><a href="${contextPath }/activity/showActivityList.html">
			                <img src="${contextPath }/jsp/images/activity/zc.jpg" /></a></li>
			            <li  title="首充10以上送18">
			            	<a href="${contextPath }/activity/showActivityList.html">
			                <img src="${contextPath }/jsp/images/activity/cz.jpg" /></a></li>
			            <li title="佣金推荐有奖"><a href="${contextPath }/activity/showActivityList.html">
			                <img src="${contextPath }/jsp/images/activity/yj.jpg" /></a></li>  
			        </ul>
			        <div class="title_bg common"><!--图片标题背景-->
			        </div>
			        <!--图片显示标题-->
			        <div class="play_title common">注册就送8.8元</div>
			        <!--图片序号-->
			        <div class="pager common">
			            <ul>
			                <li style="background: #6f4f67;">3</li>
			                <li style="background: #6f4f67;">2</li>
			                <li style="background: #FF70Ad;">1</li>
			            </ul>
			        </div>
			</div>
		</div>
	
		<div class="clearfix mt10">
			<div class="w740 fl">
				<p class="i3Line"></p>
				<p class="i3BoxTitle">
					<span class="color_red">热门彩种</span> <input type="hidden" id="token"
						value="${token }">
				</p>
				<div class="i3Box2 relative">
				<div class="clearfix pt20 hidden _ssc">
						<div class="fl relative ml20">
							<img src="${contextPath }/jsp/img/img_14.jpg" width="76"
								height="76" /><span class="czImgBg"></span>
						</div>
						<div class="fl ml15 mt5">
							<p class="color_000">
								<a href="javascript:;">
									<span class="f24 _lotName _betPage">夺金高速彩</span>
								</a> <input type="hidden" class="_lotCode" /> <input type="hidden"
									class="_rebates" /> <span class="ml10 _lastAward">140922001</span>期开奖
								<span class="ml10 color_b30 _lastLotNum">1,2,3,4,5</span>
							</p>
							<p class="mt10 color_blue">
								高奖金3分钟一期 每天<span>380</span>期
							</p>
						</div>
						<ul class="i3Box2Ul fr">
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">重庆时时彩</span></a></li>
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">江西时时彩</span></a></li>
						</ul>
					</div>
					<p class="clearfix i3WanFa mt10 _ssc">
						<a href="#" class="fr i3Box1More mr15"></a> 玩法-三星直选：<input
							type="hidden" class="_playCode" value="7004" /> <input
							type="hidden" class="_selectCode" value="40007" />
						每行各选1个号码。按位置与开奖号后3位相同即可中奖，奖金<span class="_am">1980.00</span>元。
					</p>
					<dl class="i3TabDl clearfix _ssc">
						<dt class="fl">
							<p class="iBg xuanH">
								<span class="_currIssue color_blue">14092201</span><span
									class="color_blue">期</span>
							</p>
						</dt>
						<dd class="fl ml10">
							<span class="i3BtnBg"> 百位&nbsp; <select
								class="i3Select _i1s">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
							</select>
							</span> <span class="i3BtnBg"> 十位&nbsp; <select
								class="i3Select _i2s">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
							</select>
							</span> <span class="i3BtnBg"> 个位&nbsp; <select
								class="i3Select _i3s">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
							</select>
							</span>
						</dd>
						<dt class="fl ml30 mt5 rT3">
							<a href="javascript:;" class="i3GreyBtn" id="ssc_random"><span
								style="min-width: 70px;">随机一注</span></a>
						</dt>
					</dl>
					<div class="tc mt15 clearfix ml110 _ssc">
						<div class="color_000 iTableDtDiv fl mt5">
							注数：<span class="_betCount color_red">1</span>&nbsp;&nbsp; 模式：<span
								class="color_red">元&nbsp;&nbsp;</span> 金额：<span
								class="_totalAmount color_red">0.00</span>&nbsp;&nbsp;元&nbsp;&nbsp;
							<span class="jianOrJia"><a href="javascript:;"
								class="iBg jianBtn _subBtn"></a><input type="text" value="1"
								name="" class="inputMini color_red _mult" /><a
								href="javascript:;" class="iBg jiaBtn _addBtn"></a></span>&nbsp;&nbsp;倍
						</div>
						<a href="javascript:;" class="Bet_btn  fl ml30" id="SSC_BET"><span
							style="min-width: 140px;">立即投注</span></a>
					</div>
					<p class="line1 mt35"></p>
					<div class="clearfix pt15 hidden _syxw">
						<div class="fl relative ml20">
							<img src="${contextPath }/jsp/img/img_13.jpg" width="76"
								height="76" /><span class="czImgBg"></span>
						</div>
						<div class="fl ml15 mt5">
							<p class="color_000">
								<a href="javascript:;">
									<span class="f24 _lotName _betPage">广东11选5</span>
								</a> <input type="hidden" class="_lotCode" /> <input type="hidden"
									class="_rebates" /> <span class="ml20 _lastAward">14092201</span>期开奖
								<span class="ml10 color_b30 _lastLotNum">01,02,03,04,05</span>
							</p>
							<p class="mt10 color_blue">
								易中奖 1分钟1期 每天<span class="_totalTimes">84</span>期
							</p>
						</div>
						<ul class="i3Box2Ul fr">
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">广东11选5</span></a></li>
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">江西11选5</span></a></li>
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">重庆11选5</span></a></li>
							<li><a
								href="javascript:;"
								class="i3GreyBtn"> <span class="_betPage">上海11选5</span></a></li>
						</ul>
					</div>
					<p class="clearfix i3WanFa mt10 _syxw">
						<a href="#" class="fr i3Box1More mr15"></a> 玩法-任选五：<input
							type="hidden" class="_playCode" value="6009" /> <input
							type="hidden" class="_selectCode" value="30015" />
						任选5个号，所选号码与5个开奖号相同即可中奖，奖金<span class="_am">830.00</span>元。
					</p>
					<dl class="i3TabDl clearfix _syxw">
						<dt class="fl">
							<p class="iBg xuanH">
								<span class="_currIssue color_blue">14092201</span><span
									class="color_blue">期</span>
							</p>
						</dt>
						<dd class="fl">
							<ul class="xuanHaoUl">
								<li><a href="javascript:;"><p class="iBg">01</p></a></li>
								<li><a href="javascript:;"><p class="iBg">02</p></a></li>
								<li><a href="javascript:;"><p class="iBg">03</p></a></li>
								<li><a href="javascript:;"><p class="iBg">04</p></a></li>
								<li><a href="javascript:;"><p class="iBg">05</p></a></li>
								<li><a href="javascript:;"><p class="iBg">06</p></a></li>
								<li><a href="javascript:;"><p class="iBg">07</p></a></li>
								<li><a href="javascript:;"><p class="iBg">08</p></a></li>
								<li><a href="javascript:;"><p class="iBg">09</p></a></li>
								<li><a href="javascript:;"><p class="iBg">10</p></a></li>
								<li><a href="javascript:;"><p class="iBg">11</p></a></li>
							</ul>
						</dd>
						<dt class="fr mt5 rT3">
							<a href="javascript:;" class="i3GreyBtn" id="suijiBtn1"><span
								style="min-width: 70px;">随机一注</span></a>
						</dt>
					</dl>
					<div class="tc mt15 clearfix ml110 pb32 _syxw">
						<div class="color_000 iTableDtDiv fl mt5">
							注数：<span class="_betCount color_red">0</span>&nbsp;&nbsp; 模式：<span
								class="color_red">元&nbsp;&nbsp;</span> 金额：<span
								class="_totalAmount color_red">0.00</span>&nbsp;&nbsp;元&nbsp;&nbsp;
							<span class="jianOrJia"><a href="javascript:;"
								class="iBg jianBtn _subBtn"></a><input type="text" value="1"
								class="inputMini color_red _mult" /><a href="javascript:;"
								class="iBg jiaBtn _addBtn"></a></span>&nbsp;&nbsp;倍
						</div>
						<a href="javascript:;" id="SYXW_BET" class="Bet_btn fl ml30"><span
							style="min-width: 140px;">立即投注</span></a>
					</div>
					<p class="boxShadow"></p>
				</div>
			</div>
			<div class="w240 fr">
				<div class="i3Box3 relative">
					<div class="luckyBg">
						<div class="luckyText">
							今日幸运数字&nbsp;&nbsp;<img src="jsp/images/luckyIco.jpg" width="15"
								height="15" class="rT3" />
							<div class="luckyTips">
								<p class="luckyTipsTopP"></p>
								<div class="luckyTipsBg">每天网站将为您产生3个0至11的幸运号码，绝对随机，供您参考。</div>
								<p class="luckyTipsBotP"></p>
							</div>
						</div>
					</div>
					<p class="luckyNumber ml10 _setLucky">
						<span>20</span><span>02</span><span>12</span>
					</p>
					<p class="boxShadow"></p>
				</div>
				<p class="tc mt15">		
					<c:forEach items="${adverts }" var="advert">
						<c:if test="${advert.regionCode=='I_A2'}">
							<img src="${advert.url}" width="240" />
						</c:if>
					</c:forEach>
					<!--  
					<img src="${contextPath }/jsp/images/guanGao.jpg" width="240" />
					-->
				</p>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>最新中奖</span>
				</p>
				<div class="i3Box2 relative"
					style="height: 250px; overflow: hidden;">
					<div id="i3Box4ListGd">
						<div id="noticeGdBox">
							<ul class="i3Box4List" id="noticeGd_1">
								<c:forEach items="${ ordervos}" var="order">
									<li class="clearfix">
										<span class="span1">
											${order.customerName }
										</span>
										<span class="span2 color_blue underline"> 
											<a href="javascript:;" class="_betPage">${order.lotteryTypeName }</a>
										</span> 
										<span class="color_b30 span3">
											<fmt:formatNumber value="${order.orderAmount }" pattern="#,##0.00" />元</span>
									</li>
								</c:forEach>
							</ul>
							<ul class="i3Box4List" id="noticeGd_2"></ul>
						</div>
					</div>
					<p class="boxShadow"></p>
				</div>
			</div>
		</div>
		<p class="tc mt15">
			<!--  
					<c:forEach items="${adverts }" var="advert">
						<c:if test="${advert.regionCode=='I_A3'}">
							<img src="${advert.url}" width="990" />
						</c:if>
					</c:forEach>
					-->
<%-- 			<img src="${contextPath }/jsp/images/i_b.jpg" width="990" /> --%>
		</p>
		<div class="clearfix mt20">
			<div class="w740 fl">
				<p class="i3Line"></p>
				<div class="i3Box3 relative clearfix">
					<div class="clearfix">
						<dl class="i3Box4Dl">
							<dt class="ico1">新手帮助</dt>
							<dd>
								<a href="${contextPath }/showHelps.html?id=37">如何绑定银行卡</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=35">账户充值</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=69">如何提款</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=58">如何注册</a>
							</dd>
						</dl>
						<dl class="i3Box4Dl">
							<dt class="ico2">彩种介绍</dt>
							<dd>
								<a href="${contextPath }/showHelps.html?id=79">什么是追号投注</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=78">什么是恶意投注</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=76">彩票投注后可以撤单吗</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=75">什么叫彩票代购</a>
							</dd>
						</dl>
						<dl class="i3Box4Dl i3Box4Dl_no">
							<dt class="ico3">安全隐私</dt>
							<dd>
								<a href="${contextPath }/showHelps.html?id=38">如何找回密码</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=43">什么是安全问答</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=55">我的个人信息是否安全</a>
							</dd>
							<dd>
								<a href="${contextPath }/showHelps.html?id=60">关于验证码</a>
							</dd>
						</dl>
					</div>
					<br/>
					<br/>
					<p class="boxShadow"></p>
				</div>
			</div>
			<div class="w240 fr">
				<p class="i3Line"></p>
				<p class="i3BoxTitle">
					<span>关于我们</span>
				</p>
				<div class="i3Box2 relative mb20">
					<div class="i3About">夺金，打造最好的中文在线购彩平台。夺金品牌位于菲律宾首都，集团实力雄厚，涉足房地产、星级酒店、娱乐场贵宾厅、线上娱乐等产业。从2013年筹划，历时1年多，打造彩王、夺金两大品牌，已获菲律宾政府牌照；大额无忧；诚信、安全无污点！会员账号余额可到澳门、菲律宾多家赌场贵宾厅现场出码！</div>
					<p class="boxShadow"></p>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="include/footer.jsp"%>
	<script>
		//无缝滚动
		var speed = 60;
		var tab = document.getElementById("i3Box4ListGd");
		var tab1 = document.getElementById("noticeGd_1");
		var tab2 = document.getElementById("noticeGd_2");
		tab2.innerHTML = tab1.innerHTML;
		function Marquee() {
			if (tab2.offsetHeight - tab.scrollTop <= 0)
				tab.scrollTop -= tab1.offsetHeight;
			else {
				tab.scrollTop++;
			}
		}
		var MyMar = setInterval(Marquee, speed);
		tab.onmouseover = function() {
			clearInterval(MyMar);
		};
		tab.onmouseout = function() {
			MyMar = setInterval(Marquee, speed);
		};
	</script>
</body>