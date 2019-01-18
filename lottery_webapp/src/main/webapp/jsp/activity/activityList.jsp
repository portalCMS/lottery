<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动大厅</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script type="text/javascript"
		src="${contextPath }/jsp/js/activity/activity_list.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>

	<!-- content -->
	<div class="k990">
		<p class="pagePo mt25">
			<a href="${contextPath }/index.html">首页</a>活动
		</p>
		<div class="clearfix">
			<c:forEach items="${actList }" var="act" varStatus="status">
				<div class="w740 fl">
				<div class="activityBox clearfix">
					<input type="hidden" value="${act.id }" class="_actId"/>
					<div class="clearfix">
						<div class="fl relative cursor-p">
							<img src="${act.picurl }" width="450" height="200" class="_actPicurl" title="查看活动详情"/><span
								class="activityHY">进行中</span>
						</div>
						<ul class="fl activityText">
							<li class="f18 _actTitle cursor-p color_blue">${act.title }</li>
							<li class="mt15 f14">活动时间
								<p class="f12 color_red1 mt5">${act.starttime }</p>
								至<p class="f12 color_red1 mt5">${act.endtime }</p>
							</li>
							<li class="mt25"><a href="javascript:;" class="mediumBtn f16 _getAward">
							<span style="padding-left: 40px;padding-right: 40px;">领取奖励</span></a></li>
						</ul>
					</div>
					<p class="boxShadow"></p>
				</div>
			</div>
			<c:if test="${status.index==0 }">
				<div class="w240 fr">
				<div class="myActivity mt15">
					<p class="myActivTitle">我的活动领奖记录</p>
					<ul class="activityRUl">
					</ul>
					<li class="clearfix cursor-p _hidLi"><img
							src="" width="60" height="45"
							class="fl _myactPic mt5" />
							<input type="hidden" class="_myActId"/>
							<div class="myActivText">
								<p class="title">
									<a href="javascript:;" class="color_blue _myactTitle"></a>
									<input type="hidden"  class="_myActId"/>
									<br/>
									时间:<span class="color_5b ml5 _getTime"></span>
									<br/>
									金额:<span class="color_red ml5 _award"></span>
									<br/>
									状态:<span class="color_5b ml5 _status"></span>
								</p>
							</div>
					</li>
					<p class="activityPin">
						<a href="javascript:;" id="pre" class="color_blue">上一页</a>
						<span id="pageNum" class="ml5"></span>/<span id="totalPage"></span>
						<a href="javascript:;" class="ml5 color_blue" id="next">下一页</a>
					</p>
					<p class="boxShadow"></p>
				</div>
				<!-- 
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>新手帮助</span>
				</p>
				<div class="i3Box2 relative">
					<ul class="noticeDRList">
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=37">如何绑定银行卡</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=35">账户充值</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=69">如何提款</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=58">如何注册</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=79">什么是追号投注</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=78">什么是恶意投注</a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=76">彩票投注后可以撤单吗</a></a></li>
						<li class="clearfix"><a href="${contextPath }/showHelps.html?id=75">什么叫彩票代购</a></li>
					</ul>
					<p class="boxShadow"></p>
				</div>
				-->
			</div>
			</c:if>
			</c:forEach>
<!-- 			如果没有数据也要显示右边的帮助信息 -->
			<c:if test="${haveAct==0 }">
				<div class="w240 fr">
				<div class="myActivity mt15">
					<p class="myActivTitle">我的活动领奖记录</p>
					<ul class="activityRUl">
					</ul>
					<li class="clearfix cursor-p _hidLi"><img
							src="" width="60" height="45"
							class="fl _myactPic" />
							<input type="hidden" class="_myActId"/>
							<div class="myActivText">
								<p class="title">
									<a href="javascript:;" class="color_blue _myactTitle"></a>
									<input type="hidden"  class="_myActId"/>
									<br/>
									时间:<span class="color_5b ml5 _getTime"></span>
									<br/>
									金额:<span class="color_red ml5 _award"></span>
								</p>
							</div>
					</li>
					<p class="activityPin">
						<a href="javascript:;" id="pre" class="color_blue">上一页</a>
						<span id="pageNum" class="ml5"></span>/<span id="totalPage"></span>
						<a href="javascript:;" class="ml5 color_blue" id="next">下一页</a>
					</p>
					<p class="boxShadow"></p>
				</div>
				<p class="i3Line mt15"></p>
				<p class="i3BoxTitle">
					<span>活动帮助</span>
				</p>
				<div class="i3Box2 relative">
					<ul class="noticeDRList">
						<li class="clearfix"><a href="#"></a></li>
					</ul>
					<p class="boxShadow"></p>
				</div>
			</div>
			</c:if>
		</div>
	</div>
	<!-- content End -->

	<%@ include file="../include/footer.jsp"%>
</body>
</html>