<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="xb" uri="lotterytaglib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
<script src="${contextPath }/jsp/js/head.js"></script>
<!--  IM
<link href="${contextPath}/jsp/layim/css/layim.css" type="text/css" rel="stylesheet"/>
<script src="${contextPath}/jsp/layim/lay/layer/layer.min.js"></script>
<script src="${contextPath}/jsp/layim/lay/layim.js"></script>
-->
</head>
<!-- head -->
<div class="headTop3 nav">
	<div class="k990 clearfix" id="headJs">
		<ul class="headTopFl fl">
			<li><a href="${contextPath }/showhcenter.html">帮助中心</a></li>
			<li><a target="_blank" href="${contextPath }/opencs.html" class="color_blue">在线客服</a></li>
		</ul>
		<ul class="headTopFr fr">
			<li class="hRBorder">账户余额  
					<a href="javascript:;" class="_myCashAmount" style="display:none;"> 
					￥<span id="headCashAmount" class="color_red" title="点击刷新余额">0.00</span></a>
					<a href="javascript:;" class="_cashShow btn" title="点击显示余额" > 
					<span id="headCashAmount" class="color_blue f12">展开</span></a>
					<a href="javascript:;" class="_cashHide" style="display:none;"> 
					<span id="headCashAmount" class="color_blue ml10"  title="点击隐藏余额" >隐藏</span></a>
				</li>
			<li class="hRBorder"><a
				href="${contextPath }/user/showUserRecharge.html" data-step="4"
				data-intro="可以使用银行卡及第三方充值平台充值" data-position='bottom'>账户充值</a></li>
			<li class="hRBorder"><a
				href="${contextPath }/user/showDrawing.html" data-step="3"
				data-intro="在这里您可以发起提款申请。" data-position='bottom'>提款申请</a></li>
			<li class="hRBorder"><a
				href="${contextPath }/user/showBindCard.html" data-step="3"
				data-intro="在这里您可以绑定银行卡。" data-position='bottom'>绑定银行卡<c:if
						test="${sessioncards == 1 }">
						<label class="tip_icon">&nbsp;&nbsp;&nbsp;</label>
					</c:if></a></li>
			<li class="hRBorder"><a href="${contextPath }/showWebMsg.html" data-step="3"
				data-intro="站内通知" data-position='bottom'>站内信(<i class="color_red _cwximsg">0</i>)</a></li>
			<c:if test="${customerkey.customerType != 12002 }">
				<li class="hRBorder"><a href="${contextPath }/team/show.html"
					data-step="2" data-intro="与您下级相关的信息将在这里出现。" data-position='bottom'>圈子管理</a></li>
			</c:if>
				<li class="hRBorder"><a href="${contextPath }/finance/showFinanceDetail.html"
					data-step="2" data-intro="与您下级相关的信息将在这里出现。" data-position='bottom'>个人中心</a></li>
			<li class="hRBorder"><a
				href="${contextPath }/finance/showFinanceDetail.html" class="btn"
				data-step="1" data-intro="点击这里，查看您的账号信息" data-position='bottom'>欢迎您，
					<c:if test="${customerkey.customerAlias!=null }">${customerkey.customerAlias }</c:if>
					<c:if test="${customerkey.customerAlias==null }">${customerkey.customerName }</c:if>
			</a>
				<ul class="headTopPoPup3">
					<li><a href="${contextPath }/finance/showFinanceDetail.html"
						class="btn">欢迎您， <c:if
								test="${customerkey.customerAlias!=null }">${customerkey.customerAlias }</c:if>
							<c:if test="${customerkey.customerAlias==null }">${customerkey.customerName }</c:if>
					</a></li>
					<c:if test="${customerkey.customerLevel != 0 }">
						<li><a href="${contextPath }/finance/showMyBetRecord.html">投注记录</a></li>
						<li><a
							href="${contextPath }/finance/showMyTraceBetRecord.html">追号记录</a></li>
					</c:if>
					<li><a href="${contextPath }/loginout.html">退出登录</a></li>
				</ul></li>
		</ul>
	</div>
</div>
<c:if test="${task == null}">
<p class="line1"></p>
<div class="headC3">
	<div class="k990 clearfix">
		<h1 class="fl">
			<a href="${contextPath }/index.html"><img
				src="${contextPath }/jsp/images/1.png" alt="夺金" height="60" /></a>
		</h1>
		<img class="fl ml10" src="${contextPath }/jsp/images/slg1.png"
			height="60" />
			
			
		<div class="fr mr25">
			<a href="${contextPath }/showfeedback.html" >  
			<img src="${contextPath }/jsp/images/feedback_icon.jpg" class="mt5"/>
			</a>
		</div>
		
		<div class="fr mr25 cursor-p">
		<dl class="mt20 color_blue" id="KFZX">
	            <dt><a target="_blank" href="${contextPath }/opencs.html"><span class="icon icon-phone"></span><span style="font-size:16px;">在线客服</span></a></dt>
	        </dl>
        </div>
        
        <div class="fr mr25 cursor-p">
			<dl class="mt15 color_blue" id="CLDW">
	            <dt><a href="${contextPath }/downClient.html"   class="clientDown">
						<span class="ml30">&nbsp;&nbsp;客户端下载</span></a></dt>
	        </dl>
        </div>
		
	</div>
</div>
</c:if>

<div class="mainNav3">
	<div class="k990">
		<ul class="mainNav3Ul clearfix">
			<li><a href="${contextPath }/index.html">首页</a></li>
			<li class="mainNavGpJs" >
				<a href="${contextPath }/showssc.html"><c:if test="${task == null}">购彩大厅</c:if><c:if test="${task != null}"><i class="color_golden">${task.lotteryName }</i></c:if><span></span></a>
				<div class="mainNavDiv" style="display: none;">
					<p id="mainNavBgLine"></p>
				</div></li>
			<li><a href="${contextPath }/showNotices.html">网站公告</a></li>
			<li><a href="${contextPath }/activity/showActivityList.html">活动大厅</a></li>
		</ul>
	</div>
</div>
<!-- head End -->



