<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<script src="${contextPath }/jsp/js/recent_bet.js"></script>
</head>
<div class="mt20 orderListAtLottery">
	<div class="title clearfix">
		<h3 class="fl"><a href="${contextPath }/finance/showMyBetRecord.html" class="color_blue ">投注记录</a></h3>
		<a href="javascript:;" class="fl ml10  color_blue" id="refreshBet" style="display:none;margin-top:2px;">刷新</a>
		<a href="javascript:;" class="fr  color_blue" id="showBet">展开</a>
	</div>
	
	<div class="g-play-tables-list">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" 
			class="accountTab" id="betTable" style="display:none;">
                    <thead>
                        <tr style="background-color: #ccc;">
                            <td>彩种</td>
                            <td>玩法</td>
                            <td>期号</td>
                            <td>投注内容</td>
                            <td>投注金额</td>
                            <td>开奖号码</td>
                            <td>状态</td>
                            <td>投注类型</td>
                        </tr>
                    </thead>
                    <tbody id="brdata">
					</tbody>
					<tr  class="_trclone" style="display:none;">
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle" class="_rbn color_blue"></td>
						<td height="39" align="center" valign="middle"><span
							class=""></span></td>
						<td height="39" align="center" valign="middle"><span
							class="correctHint">中奖</span>
							<p class="color_red">10000000.00</p></td>
						<td height="39" align="center" valign="middle"></td>
						<td height="39" align="center" valign="middle">
						</td>
					</tr>
                </table>
            </div>
</div>



