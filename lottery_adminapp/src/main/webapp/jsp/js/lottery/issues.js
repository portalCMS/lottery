$(document).ready(function() {
	
	$.checkFrom.initBind(".checkedAttr", true, true,true);
	
	//奖期星期勾选
	var weekConfig = $("#timeConfig").val();
	if(""!=weekConfig&&null!=weekConfig){
		var weekArr = weekConfig.split(",");
		var seriesArr = $("input[name='seriesTime']");
		for(var k=0;k<seriesArr.size();k++){
			var series = seriesArr.get(k);
			for(var l=0;l<weekArr.length;l++){
				if($(series).val()==weekArr[l]){
					$(series).attr('checked','checked');
					break;
				}
			}
		}
	}else{
		//非从奖金组返回的操作，则将数字型的字段空值显示
		$("input[type='text']").val('');
	}
	
	
	$(".close_btn").hide();
	
	$("#new_btn").click(function() {
		var clonePeriod = $("#clonePeriod>div:first-child").clone(true);
		clonePeriod.show();
		$("#series_panle").append(clonePeriod);
	});
	$(".close_btn2").on("click", function() {
		$(this).closest(".period-panel").remove();
	});

	$("#next_btn").click(function() {
		var checkBoxs = document.getElementsByName("seriesTime");
		var checkValue="";
		for(var i=0;i<checkBoxs.length;i++){
			if(checkBoxs[i].checked==true){
				if(checkValue==""){
					checkValue=checkBoxs[i].value;
				}else{
					checkValue=checkValue+","+checkBoxs[i].value;
				}
			}
		}
		if(checkValue==""){
			alert("开奖周期必须至少勾选一个时间！");
			return ;
		}
		
		var check = $.checkFrom.checkAllType("#lottery_series");
		if(!check){
			return;
		}
		
		$("#clonePeriod").html("");
		var startTimes = $(".startTime");
		for (var i = 0; i < startTimes.length; i++) {
			startTimes.eq(i).attr("name", "lotterys[" + i+"].startTime");
		}
		var endTimes = $(".endTime");
		for (var i = 0; i < endTimes.length; i++) {
			endTimes.eq(i).attr("name", "lotterys[" + i+"].endTime");
		}
		var spanTimes = $(".spanTime");
		for (var i = 0; i < spanTimes.length; i++) {
			spanTimes.eq(i).attr("name", "lotterys[" + i+"].spanTime");
		}
		var totalTimes = $(".totalTimes");
		for (var i = 0; i < totalTimes.length; i++) {
			totalTimes.eq(i).attr("name", "lotterys[" + i+"].totalTimes");
		}
		var catchTimes = $(".catchTimes");
		for (var i = 0; i < catchTimes.length; i++) {
			catchTimes.eq(i).attr("name", "lotterys[" + i+"].catchTimes");
		}
		var beforeLotTimes = $(".beforeLotTime");
		for (var i = 0; i < beforeLotTimes.length; i++) {
			beforeLotTimes.eq(i).attr("name", "lotterys[" + i+"].beforeLotTime");
		}
		var afterLotTimes = $(".afterLotTime");
		for (var i = 0; i < beforeLotTimes.length; i++) {
			afterLotTimes.eq(i).attr("name", "lotterys[" + i+"].afterLotTime");
			if(afterLotTimes.eq(i).val()==undefined){
				afterLotTimes.eq(i).val(0);
			}
		}
		
		$("#timeConfig").val(checkValue);
		
		lotteryForm.submit();
	});
	
});