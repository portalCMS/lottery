<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${task.lotteryName }</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<link href="${contextPath }/jsp/lottery/css/lottery.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath }/jsp/lottery/css/11x5.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${contextPath }/jsp/js/ajaxfileupload.js"></script>
<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/lottery.js"></script>
	<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/11x5.js"></script>
<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/lock_number.js"></script>
	<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/betUtil.js"></script>
	<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/11x5KeyManager.js"></script>
	<script type="text/javascript"
		src="${contextPath }/jsp/lottery/js/textscroll.js"></script>
<script language="javascript">
	var spanTime = '${lottery.spanTime}';
	var totalMinutes = parseInt(spanTime / 60);
	var callTime;
	var openAward;
	$(document).ready(function(){
		callTime = setInterval("countdown.time_callback()", 1000);
		//如果是正在开奖的状态，30秒检查一次是否已经开奖。
		openAward = setInterval("betUtil.getOpenAward()", 10000);
		//最近中奖
		setInterval("betUtil.queryWingOrders()",300000);
	});
</script>
<body class="body-color">
	<%@ include file="../include/head.jsp"%>
	<div class="betMainBox">
		<div class="g-play-left-big-angle"></div>
		<span class="wing_info">&nbsp;&nbsp;最新中奖播报：</span>
		<span id="wingScroll" class="fl wing_order_div">
		  	<span id="noticeList" class="wing_scroll_info" >
		  	</span>
		  	<span class="ml30 _hidSpan" style="display:none;"></span>
		</span>
		<div class="iBox1 clearfix" style="display:none;">
			<div class="_titleInfo">
				<p>
					<input id="lotGroup" type="hidden" value="${task.lotteryGroup }" />
					<input id="lotCode" type="hidden" value="${task.lotteryCode }" />
					<input id="endBetTime" type="hidden" value="${task.endBetTime }" />
					<span id="totalMinutes" class="color_red">${lottery.spanTime/10 }</span>分钟一期，
					全天<span id="totalTimes" class="color_red">${lottery.totalTimes }</span>期。<br>
					首期开奖<span id="startTime" class="color_red">${lottery.startTime }</span>，末期开奖
					<span id="endTime" class="color_red">${lottery.endTime }</span>。
					<!-- 设置最近的开奖记录期号，方便判断是否需要刷新开奖记录的轮询。 -->
					<input type="hidden" id="lastAwardNo" value="${lastAwardNo}">
				</p>
			</div>
		</div>
		<!-- top lottery info end -->
		<div class="clearfix _syxwMainBg">
				<div class="cathectic-box" id="cathectic">
					<div class="cathectic-num ">
						<c:forEach items="${playList }" var="p" varStatus="status">
								<a href="javascript:;" name="${p.balCount }">${p.groupName }</a>
						</c:forEach>
					</div>
					<c:forEach items="${playList }" var="p">
						<div class="cathectic-type">
							<div class="cathectic-type-choose g-play-with">
							<div class="fl g-play-quick"> <ul>
								<c:forEach items="${p.lpmList }" var="lpm">
								<li><div class="h_tit">&nbsp;&nbsp;${lpm.playModel.modelName }</div>
<%-- 									<span> <strong>${lpm.playModel.modelName }</strong>  --%>
									<c:forEach items="${lpm.selectList }" var="select">
											<div class="tit">
												<input type="radio" id="CTC_${select.selectIndex }"
													data-type="${lpm.playModel.modelName }"
													data-code="${lpm.playModel.modelCode }"
													data-select="${select.selectCode }" name="ctype" 
													style="display:none;" />
												<label for="CTC_${select.selectIndex }">${select.selectName }</label>
											</div>
										</c:forEach>
<!-- 									</span> -->
								</li>
								</c:forEach>
								</ul>
							</div>
							<div class="fl g-play-limit" style="line-height: 43px;"></div><div style="clear:both;"></div>
							<c:forEach items="${p.lpmList }" var="lpm2">
								<c:forEach items="${lpm2.selectList }" var="select2">
									<div class="cathectic-tip d_${select2.selectIndex }"
										style="display: block">
										<b class="indexIco iPrompt"></b> <input class="_oriWinAmount"
											type="hidden" value="${lpm2.playModel.winAmount }" /> <input
											class="_oriPayoutRatio" type="hidden"
											value="${lpm2.bonus.payoutRatio }" />
										玩法介绍：${select2.selectDesc }<span class="_amount _winAmount">${lpm2.playModel.winAmount*lpm2.bonus.payoutRatio }</span>元。
									</div>
								</c:forEach>
							</c:forEach>
						</div>
						</div>
					</c:forEach>
					<div class="cathectic-container">
						<div class="cathectic-recent">
							<div class="cathectic-recent-btn">
								<!-- <span>选号助手</span> -->
							</div>
							<div class="cathectic-recent-list"></div>
						</div>
						<div class="cathectic-main">
							<div class="cathectic-main-choose">
								<div class="cathectic-selector-item"></div>
							</div>
							<div class="cathectic-main-choose-text bgYellow">
								<span>
									<a href="javascript:;" class="smallSubBtn ml5" id="deleteRepeat">
									<span style="width: 60px">删重复号</span></a>
									<a href="javascript:;" class="smallSubBtn ml5" id="loadFile">
									<span style="width: 60px">导入文件</span></a>
									<a href="javascript:;" class="smallSubBtn ml5" id="clearText">
									<span style="width: 60px">清空号码</span></a>
									<span class="ml10 color_8d">『提醒』每注号码以
									<span class="color_green">"逗号","空格","分号"</span>分离</span>
								</span>
								<textarea id="fileInfo"></textarea>
								<p><i id="textErrMsg" class="color_red f14"></i>
								</p>
							</div>
							<!-- Control Center -->
							<div class="g-play-list">
				                <div class="g-play-tabs fl">
				                    <table style="margin-top: 5px;">
				                        <thead>
				                        <tr><td>选注</td>
				                        <td>本金</td>
				                        <td>奖金</td>
				                        <td>盈利</td>
				                        </tr></thead>
				                        <tbody>
				                        <tr>
				                            <td> <span id="oneBetCount" class="color_red">0</span> 注 </td>
				                            <td> <span id="oneBetAmount" class="color_red">0.00</span> ￥</td>
				                            <td> <span id="oneAwardAmount">0.00</span> ￥</td>
				                            <td> <span id="oneWinAmount">0.00</span> ￥</td>
				                        </tr>
				                        </tbody>
				                    </table>
				                </div>
				                <div class="g-play-limit fl g-play-list-select">
				                       <select id="moneyModel" autocomplete="off">
											<option value="1">元模式</option>
											<option value="0.1">角模式</option>
											<option value="0.01">分模式</option>
										</select>
				                </div>
				                <ul>
				                    <li><a href="#" class="CSR_clear">清空全部</a></li>
				                    <li><input type="checkbox" id="missing" autocomplete="off"/>&nbsp;遗漏</li>
				                    <li><input type="checkbox" id="hotcold" autocomplete="off"/>&nbsp;冷热</li>
				                </ul>
				            </div>
					             <div class="g-play-readybtn">
<!-- 				               		<a href="javascript:;" class="CSR_btn" onclick="return false;">选好了</a><i></i> -->
				               		<button class="btn_xhl_op CSR_btn"></button>
				                	<div class="g-play-readybtn-angle"></div>
							    </div>
						</div>
						
							<div class="g-play-getlist">
				                <h1>号码列表</h1>
				                <div class="g-play-getlist-brush" id="clearResult">清空列表<i></i></div>
				                <div class="g-play-getlist-random" id="randomResult">随机<i></i></div>
				            </div>
						<!-- Result Box -->
							<div class="cathectic-resluts">
								<div class="cathectic-resluts-list"></div>
								<!-- Doublint Controller -->
								<p class="cathectic-resluts-doubling">
	<!-- 								<a href="#">清空列表</a>  -->
									单倍下注：<strong id="betCount">0</strong>，
									金额：<strong id="betAmount" class="_amount">0.00</strong>元，
									您选择了 <span><i>-</i> 
									<input type="text" id="doubling" value="1" /> 
									<i>+</i></span> 倍 <br /> 
									<em style="display: none;">注：倍率不大于<b>1000</b></em>
									
									<span style="float: right;margin-top: -28px;margin-right: 70px">
										<!-- 0返点的中奖理论金额必须加上用户对应的返点金额 -->
										<input id="userRebates" type="hidden" value=""/>
										<select id="userBonus" class="ml10 color_red" 
											style="line-height: 35px;vertical-align: middle;margin-left: 30px;">
											<option value="0" name="${rebates }">${rebates }&nbsp;返点</option>
											<option value="${rebates }" name="0">0&nbsp;返点</option>
										</select>
										<input id="noRebatesBonus" type="hidden" value=""/>
										<input id="bonusGroupId" type="hidden" value=""/>
										<!-- 火狐input域缓存清理 autocomplete="off" -->
										<input type="hidden" name="token" id="token" value="${token }" autocomplete="off">
									</span>
								</p>
							</div>
						</div>
						
						
				    <p style="margin-top: 54px;"></p>
					<div class="xuanHaoBtn g-play-getlist-btn-op">
							投注&nbsp;<span class="color_red" id="totalPeriods">1</span>&nbsp;期，金额为<span
								class="color_red _amount" id="totalAmount">0.00</span>元
            		</div>
            		<div class="g-play-readybtn _betbtn">
            			<c:if test="${task.taskStatus==10002 }"><button class="btn_xz fb f14 color_green">立即投注</button><input id="traceBet" type="checkbox"><span id="traceSpan">我要追号</span></c:if>
						<c:if test="${task.taskStatus!=10002 }"><button class="btn_xz_op fb f14 color_green">暂停销售</button><input id="traceBet" type="checkbox"><span id="traceSpan">我要追号</span></c:if>
				       <div class="g-play-readybtn-angle"></div>
				    </div>
				    
		  <div style="display: none;" id="trace">
			<div class="g-play-item" id="traceSelect">
                    <ul>
                        <li><i class="g-play-item-money"></i> 倍投追号<input type="hidden" value="Z"></li>
                        <li><i class="g-play-item-fig"></i> 利润追号<input type="hidden" value="L"></li>
                    </ul>
              </div>
		              
		     <div class="g-play-chase-control">
                <div class="g-play-chase-control-dbtn"><a href="javascript:;" id="doubleTimes">翻倍追号</a></div>

                <span class="fl s1">（倍）</span><span class="fl s2">×</span>
                <div class="g-play-getlist-double g-play-chase-control-double">
                    <ul>
                        <li><input id="double_time" type="text" value="2"></li>
                    </ul>
                </div>
                <span class="g-play-chase-control-pro">【点击翻倍追号，即可和成倍投方案(小于5000倍)】</span>
            </div>
            <div class="g-play-profit-control">
                <span class="f14">起始倍数：</span>
					<input type="text" id="start_mulite" value="1" />
				<span class="f14 ml30">最低收益率：</span>
                	<input id="profit" type="text" value="30">&nbsp;%
				<span class="g-play-profit-control-pro">【更改追期数，即可生成方案 (小于5000倍)】</span>
            </div>
            <div class="g-play-chase-control-quick">
                <div class="g-play-chase-control-quick-box">
                    <ul class="queickZQ">
                        <li class="current">追<span>5</span>期</li>
                        <li>追<span>10</span>期</li>
                        <li>追<span>20</span>期</li>
                        <li>追<span>50</span>期</li>
                        <li>追<span>100</span>期</li>
                    </ul>
                    <input type="checkbox" id="awardStop" name="awardStop" value="1" class="ml40"/>  
                    <span id="astopSpan" class="color_red cursor-p">中奖后停止</span>
                    <div style="clear: both;"></div>
                </div>
            </div>
            <div class="g-play-item-money-list">
                    <table>
                        <thead>
                            <tr><td style="width: 5%;">序号</td>
                            <td style="width: 20%;">
                                <span style="float: left;margin-right: 5px;margin-top: 8px;">追</span>
                                    <div class="g-play-getlist-double" style="margin-top: 3px;  overflow: hidden;float: left; height: 27px;width: 50px;padding: 0px; line-height: 0;">
                                        <ul>
                                            <li><input id="zqNum" type="text" value="1"></li>
                                        </ul>
                                    </div>
                                <span style="float: left; margin-left: 5px;margin-top: 8px;">期</span>
                            </td>
                            <td style="width: 15%;">
                                <span style="float: left;margin-left: 20px;margin-right: 5px;">&nbsp;</span>
                                <div class="g-play-getlist-double" style="margin-top: 3px;  overflow: hidden;float: left; height: 27px;width: 50px;padding: 0px; line-height: 0;">
                                    <ul>
                                        <li><input id="betmp" type="text" value="1"></li>
                                    </ul>
                                </div>
                                <span style="float: left; margin-left: 5px;margin-top: 8px;">倍</span>
                            </td>
                            <td width="15%" align="center" valign="middle">当期投入</td>
                            <td width="15%" align="center" valign="middle">累计投入</td>
                            <td width="15%" align="center" valign="middle">理论奖金</td>
                        </tr></thead>
                        <tbody id="zqtb">
                        </tbody>
						<tr class="_hidZqTr" style="display:none;">
							<td width="5%" height="35" align="center" valign="middle"></td>
							<td width="20%" height="35" align="left" valign="middle">
								<input type="checkBox" class="mtF3 zqcb">
								<span class="_issue_no"></span>
							</td>
							<td width="15%" height="35" align="center" valign="middle">
								&nbsp;&nbsp;
								<input type="text" value="1" id="betmp" class="inputMini zqbs" />
								&nbsp;倍
							</td>
							<td width="15%" height="35" align="center" valign="middle">
								<span class="curspen _amount">--</span>
							</td>
							<td width="15%" height="35" align="center" valign="middle">
								<span class="totspen _amount">--</span>
							</td>
							<td width="15%" height="35" align="center" valign="middle">
								<span class="award _amount">--</span>
							</td>
						</tr>
                    </table>
                </div>
                </div>
				<%@ include file="../include/recentBet.jsp"%>
			</div>
			<!-- 客服咨询 -->
			<div class="mod-kefu">
			    <ul>
					<li><a target="_blank" href="${contextPath }/opencs.html" class="a1 live800_online"></a></li>
					<li><a class="a2" href="${contextPath }/showfeedback.html" target="_blank"></a></li>
				</ul>
			</div>
			<div class="fl" id="splitDiv">&nbsp;</div>
		</div>
			
		<div class="g-play-left">
				<div id="issueInfo" class="g-play-left-clock _issueInfo">
<!-- 	                <div class="g-play-left-big-angle"></div> -->
	                <h1>${lottery.lotteryName }-<b id="currentIssno">${task.lotterySeries }</b>期</h1>
	                <div class="g-play-left-clock-number">
	                        <span id="end_time_hour" style="display: none;">${betHour }</span>
	                        <i style="display: none;">时</i>
	                        <span id="end_time_minute">${betMinutes }</span>
	                        <i>分</i>
	                        <span id="end_time_second">${betSeconds }</span>
	                        <i>秒</i>
	                        <input id="tempCode" value="${lotteryCode }" type="hidden"/>
	                </div>
	            </div>
	            
	            <div id ="openNumInfo" class="g-play-left-numbers">
                    <div class="g-play-left-nub-box" id="open_code_list">
                        <i class="g-play-left-nub">?</i>
                        <i class="g-play-left-nub">?</i>
                        <i class="g-play-left-nub">?</i>
                        <i class="g-play-left-nub">?</i>
                        <i class="g-play-left-nub">?</i>
                    </div>
                    <div style="clear: both;"></div>
                    <p class="openInfo">开奖公告:第 <span class="color_red _last_open_issue">?</span>期开奖</p>
            </div>
	            
	        <div class="g-play-left-trend">
                <ul>
                    <li>
                        <i></i>
                        <a href="initNoTrend.html?lotteryCode=${lottery.lotteryCode }"target="_blank">基本走势图</a> 
                        <a href="initZhNoTrend.html?lotteryCode=${lottery.lotteryCode }" target="_blank">综合走势图</a>
                    </li>
                </ul>
            </div>
				<div class="mt10">
					<div class="g-play-left-tables">
		               <span>开奖号表</span>
		                <a id="refreshLotNum" href="javascript:;">刷新</a>
		                <i></i>
		            </div>
		            <div class="g-play-left-tables-withe">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="20%" height="37" align="center" valign="middle">时间</th>
								<th width="30%" height="37" align="center" valign="middle">期号</th>
								<th width="30%" height="37" align="center" valign="middle">开奖号码</th>
							</tr>
						</thead>
						<tbody id="recordsTbody">
							<tr>
								<td height="37" align="center" valign="middle">${task.startBetTime }</td>
								<td height="37" align="center" valign="middle">${task.lotterySeries }</td>
								<td height="37" align="center" valign="middle">正在销售</td>
							</tr>
							<c:forEach items="${records }" var="t">
								<tr>
								<c:choose>
									<c:when test="${t.status==10007||t.status==10011}">
										<td height="37" align="center" valign="middle">${t.startTime }</td>
									</c:when>
									<c:when test="${t.status==10005&&t.status==10013 }">
										<td height="37" align="center" valign="middle">${t.openTime }</td>
									</c:when>
									<c:otherwise>
										<td height="37" align="center" valign="middle">${t.endTime }</td>
									</c:otherwise>
								</c:choose>
								
								
								<td height="37" align="center" valign="middle">${t.issue }</td>
								<c:choose>
									<c:when test="${t.lotteryNumber != null }">
										<td height="37" align="center" valign="middle" class="_lotNum">${t.lotteryNumber }</td>
									</c:when>
									<c:when test="${t.status==10006 }">
										<td height="37" align="center" valign="middle">开奖失败</td>
									</c:when>
									<c:when test="${t.status==10007||t.status==10010||t.status==10011 }">
										<td height="37" align="center" valign="middle">正在开奖</td>
										<input type="hidden" class="_openAward" value="${t.status}"/>
									</c:when>
									<c:otherwise>
										<td height="37" align="center" valign="middle">等待开奖</td>
									</c:otherwise>
								</c:choose>
							</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>