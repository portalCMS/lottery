<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
</head>
<label id="helpLineLabel"><input type="checkbox" id="helpLine" name="helpLine" value="1" />辅助折线
</label> 
<label> <input type="checkbox" id="missing" name="missing"
	value="1" class="ml10" />遗漏值
</label>
<label> <input type="checkbox" id="splitLine"
	name="splitLine" value="1" class="ml10"/>分隔线
</label>
<c:if test="${lotteryCode<70000 || lotteryCode==91003}">
<a href="initSSC3NoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue" target="_blank">三星基本走势图</a>
<a href="initSSC2NoTrend_q.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">前二基本走势图</a>
<a href="initSSC2NoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">后二基本走势图</a>
</c:if>
<c:if test="${lotteryCode>70000 && lotteryCode<91003}">
<a href="initSSCNoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue"  target="_blank">五星基本走势图</a>
<a href="initSSCzh5NoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">五星综合走势图</a>
<a href="initSSC3NoTrend_q.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">前三基本走势图</a>
<a href="initSSC3NoTrend_z.html?lotteryCode=${lotteryCode }" class=" fr f14 color_blue mr10" target="_blank">中三基本走势图</a>
<a href="initSSC3NoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">后三基本走势图</a>
<a href="initSSC2NoTrend_q.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">前二基本走势图</a>
<a href="initSSC2NoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">后二基本走势图</a>
</c:if>

