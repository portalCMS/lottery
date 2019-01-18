<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
</head>
<label id="helpLineLabel"> <input type="checkbox" id="helpLine"
	name="helpLine" value="1" />辅助折线
</label> <label> <input type="checkbox" id="missing" name="missing"
	value="1" class="ml10" />遗漏值
</label>
<label> <input type="checkbox" id="splitLine" name="splitLine"
	value="1" class="ml10" />分隔线
</label>
<a href="initNoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue" target="_blank">基本走势图</a> 
<a href="initZhNoTrend.html?lotteryCode=${lotteryCode }" class="fr f14 color_blue mr10" target="_blank">综合走势图</a>


