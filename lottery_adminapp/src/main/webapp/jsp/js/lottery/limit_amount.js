var limitAmount = function(){
	
	return{
		showLimitAmount:function(obj){
			$(".playclass").attr('class','playclass');
			$(obj).parent().attr('class','active playclass');
			var showId = $(obj).attr('name');
			$(".nav_content").hide();
			$("#"+showId).show();
			$('.amount_class').formatCurrency();
		},
		avgLimitAmount:function(){
			var totalAmount = $("#totalLimitAmount").asNumber();
			var playSize = $("#playSize").val();
			var limitAmount = parseFloat(totalAmount)/parseFloat(playSize);
			$(".limitAmount").text(limitAmount);
			for(var i=0;i<playSize;i++){
				var obj = $(".nav_content").eq(i);
				$("#avgAmount_"+i).text(parseFloat(limitAmount));
				
				var avgAwardAmount =parseFloat($("#avgAmount_"+i).text());
				var awardAmount = parseFloat($(obj).find("._winAmount").val());
				
				var payoutRatio = parseFloat($("#payoutRatio").val());
				//因为一注的奖金是2乘以返奖率的，所以这里需要乘回去。
				var betAmount = avgAwardAmount/(awardAmount*payoutRatio/100)*2;
				$(obj).find("input._limitAmount").val(betAmount);
				$(obj).find("span._limitAmount").text(betAmount);
				$(obj).find("span._payoutRatio").text(payoutRatio);
				$("#la_percent_"+i).val(100/parseFloat(playSize));
			}
			$('.amount_class').formatCurrency();
		},
		changeLimitPercent : function(obj){
			var totalAmount = $("#totalLimitAmount").asNumber();
			var percent = $(obj).asNumber()/100;
			var pId = $(obj).attr('id');
			var index = pId.substring(pId.length-1,pId.length);
			var limitAmount = parseFloat(totalAmount)*percent;
			$("#limitAmount_"+index).text(limitAmount);
			$("#avgAmount_"+index).text(parseFloat(limitAmount));
			var avgAwardAmount =parseFloat($("#avgAmount_"+index).text());
			var awardAmount = parseFloat($(obj).closest(".nav_content").find("._winAmount").val());
			var payoutRatio = parseFloat($("#payoutRatio").val());
			//因为一注的奖金是2乘以返奖率的，所以这里需要乘回去。
			var betAmount = avgAwardAmount/(awardAmount*payoutRatio/100)*2;
			$(obj).closest(".nav_content").find("input._limitAmount").val(betAmount);
			$(obj).closest(".nav_content").find("span._limitAmount").text(betAmount);
			$(obj).closest(".nav_content").find("span._payoutRatio").text(payoutRatio);
			$('.amount_class').formatCurrency();
		},
		submitForm:function(){
			var check = $.checkFrom.checkAllType("#lottery_series");
			if(!check){
				return;
			}
			var isLimt = false;
			$("span._limitAmount").each(function(){
				if($(this).asNumber()<=0){
					isLimt = true;
					return;
				}
			});
			if(isLimt){
				formPost.showErrorMessage("亲，必须设置每个玩法限额金额哦！");
				return;
			}
			
			$('.amount_class').toNumber();
			lotteryForm.submit();
		}
	};
}();

$(document).ready(function() {
	$("#payoutRatio").change(function() {
		var payoutRatio = $("#payoutRatio").val().trim();
		var totalBets = $("#totalBets").val().trim();
		var bonusAmount =  $("#bonusAmount").asNumber();
		if(payoutRatio!=""&&totalBets!=""&&bonusAmount!=""){
			var amount = (parseFloat(payoutRatio)*parseInt(totalBets)*parseFloat(bonusAmount))/100;
			if(amount>999999999999){
				formPost.showErrorMessage("总限金额过大，必须小于一千亿！");
				$("#payoutRatio").focus();
				$("#payoutRatio").select();
				$("#payoutRatio").val(0);
				return;
			}
			$("#totalLimitAmount").text(amount);
			$("#totalLimitAmount2").val(amount);
			// 如将页面所有表格的金额单元格格式化显示
			$('.amount_class').formatCurrency();
		}
	});
	$("#totalBets").change(function() {
		var payoutRatio = $("#payoutRatio").val().trim();
		var totalBets = $("#totalBets").val().trim();
		var bonusAmount =  $("#bonusAmount").asNumber();
		if(payoutRatio!=""&&totalBets!=""&&bonusAmount!=""){
			var amount = (parseFloat(payoutRatio)*parseInt(totalBets)*parseFloat(bonusAmount))/100;
			if(amount>999999999999){
				formPost.showErrorMessage("总限金额过大，必须小于一千亿！");
				$("#totalBets").focus();
				$("#totalBets").select();
				$("#totalBets").val(0);
				return;
			}
			$("#totalLimitAmount").text(amount);
			$("#totalLimitAmount2").val(amount);
			// 如将页面所有表格的金额单元格格式化显示
			$('.amount_class').formatCurrency();
		}
	});
	$("#bonusAmount").change(function() {
		var payoutRatio = $("#payoutRatio").val().trim();
		var totalBets = $("#totalBets").val().trim();
		var bonusAmount =  $("#bonusAmount").asNumber();
		if(payoutRatio!=""&&totalBets!=""&&bonusAmount!=""){
			var amount = (parseFloat(payoutRatio)*parseInt(totalBets)*parseFloat(bonusAmount))/100;
			if(amount>999999999999){
				formPost.showErrorMessage("总限金额过大，必须小于一千亿！");
				$("#bonusAmount").focus();
				$("#bonusAmount").select();
				$("#bonusAmount").val(0);
				return;
			}
			$("#totalLimitAmount").text(amount);
			$("#totalLimitAmount2").val(amount);
			// 如将页面所有表格的金额单元格格式化显示
			$('.amount_class').formatCurrency();
		}
	});
	$(".la_percent").change(function() {
		limitAmount.changeLimitPercent(this);
	});
	
	$.checkFrom.initBind(".checkedAttr",true, true,true);
	
	$("li.playclass").eq(0).show();
	$(".nav_content").eq(0).show();
	
	$("._play_a").on('click',function(){
		limitAmount.showLimitAmount($(this));
	});
	$("#playModelTabs").find("._play_a").eq(0).click();
});