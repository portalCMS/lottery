<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>方案详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
</head>
<body>
	<%@ include file="../include/head.jsp"%>
	<div class="k990 mt10 clearfix">
		<div class="pageFl fl">
			<c:if test="${menu=='team' }">
				<%@ include file="../include/team_menu.jsp"%>
			</c:if>
			<c:if test="${menu=='profile' }">
				<%@ include file="../include/profile_menu.jsp"%>
			</c:if>
		</div>
		<div class="pageFr fr">
			<div class="pageFrBox_1">
				<div class="clearfix p10 ">
					<ul class="fl clearfix">
						<li class="fl mr10"><a href="javascript:void(0);"
							class="color_blue underline">追号记录</a></li>
						<li class="fl mr10">></li>
						<li class="fl color_8d">${orderNo }</li>
					</ul>
					<div class="fr">
						<a href="javascript:void(0);" class="color_yellow underline">返回</a>
						<input type="hidden" value="${orderNo}">
					</div>
				</div>
				<p class="line5"></p>
				<div class="p10 order_detail">
					<h3>方案信息</h3>
					<p class="line4 mt5"></p>
					<ul class="p20">
						<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;彩种： ${lotteryName }</li>
						<li class="clearfix">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期号： ${issueNo }期</li>
						<li>开奖号码： ${openNumber }</li>
						<li class="clearfix">
							<div class="fl">投注内容：</div>
							<div class="fl">
								<ul>
									<c:forEach var="br" items="${brs }"> 
										<c:if test="${br.betStatus == 21001 }">
											<li>
												<span class="color_yellow">[${br.playCode }]</span>
												<span class="correctHint">投注成功</span> 
												<span>${br.multiple}倍</span> 
												<span>
													<c:if test="${empty br.bileNum}">
														${br.betNum }
													</c:if>
													<c:if test="${not empty br.bileNum}">
														胆码${br.bileNum }：托码：${br.betNum }
													</c:if>
												</span>
											</li>
										</c:if>
										<c:if test="${br.betStatus == 21002 }">
											<li>
												<span class="color_yellow">[${br.playCode }]</span> 
												<span class="color_red">已中奖(奖金:<fmt:formatNumber value="${br.multiple*br.baseMoney }" pattern="#,#00.00"/>)</span>
												<span>${br.multiple}倍</span> 
												<span class="color_red">
													<c:if test="${empty br.bileNum}">
														${br.betNum }
													</c:if>
													<c:if test="${not empty br.bileNum}">
														胆码${br.bileNum }：托码：${br.betNum }
													</c:if>
												</span> 
										</c:if>
										<c:if test="${br.betStatus == 21003 }">
											<li>
												<span class="color_yellow">[${br.playCode }]</span>
												<span class="correctHint">未中奖</span>
												<span>${br.multiple}倍</span>  
												<span>
													<c:if test="${empty br.bileNum}">
														${br.betNum }
													</c:if>
													<c:if test="${not empty br.bileNum}">
														胆码${br.bileNum }：托码：${br.betNum }
													</c:if>
												</span> 
											</li>
										</c:if>
										<c:if test="${br.betStatus == 21004 }">
											<li>
												<span class="color_yellow">[${br.playCode }]</span> 
												<span class="color_8d">
													<del>
														已撤单
													</del>
												</span>
												<span>${br.multiple}倍</span>
												<span>
													<c:if test="${empty br.bileNum}">
														${br.betNum }
													</c:if>
													<c:if test="${not empty br.bileNum}">
														胆码${br.bileNum }：托码：${br.betNum }
													</c:if>
												</span> 
											</li>
										</c:if>
										
									</c:forEach>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<p class="boxBotBg"></p>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>

</body>
<script>
	$(document).ready(function(){
		$("#financeMenu").addClass("current");
		$("#ordersMenu").addClass("current");
	});
</script>
</html>