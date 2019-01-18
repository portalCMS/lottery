var sscdata = function(){
	var sscObj;
	var spanTime;
	var callTime;
	var initCallBackFuc = function(data){
		if($("._titleInfo").find("h1").text().indexOf("排列五")>-1){
			window.ConfigParam["stars"] = 5;
		}
		initRebates(data);
		spanTime = data.lottery.spanTime;
		betUtil.init();
		var ConfigDOM = initConfigDOM(data);
		var ssc = new SSC($("#ssc"),ConfigDOM);
		sscObj = ssc;
		//初始化倒计时，开奖结果
		callTime = setInterval("countdown.time_callback()",1000);
		setInterval("betUtil.getOpenAward()",10000);
		//最近中奖
		setInterval("betUtil.queryWingOrders()",300000);
		
		//冷热号查询显示
		betUtil.hotMissNumCount();
		$("._amount").formatCurrency();
		//默认选中后三玩法
		$(".ssc-num a").each(function(){
			if($(this).text()=="任选"){
				$(this).click();
			}
		});
		
	};
	var initConfigDOM = function(data){
		var ConfigDom = new Object();
		var playList = data.playList;
		for(var i =0;i<playList.length;i++){
			var lpmList = playList[i].lpmList;
			var tempArray = new Array();
			for(var j=0;j<lpmList.length;j++){
				var obj = new Object();
				obj.group = lpmList[j].playModel.modelName;
				//获取玩法
				var selectList = lpmList[j].selectList;
				//多奖级中奖金额
				var levelList = lpmList[j].levelList;
				var oriWinAmountStr = "";
				for(var k=0;k<levelList.length;k++){
					if(oriWinAmountStr==""){
						oriWinAmountStr = levelList[k].winAmount;
					}else{
						oriWinAmountStr += ","+levelList[k].winAmount;
					}
				}
				var temp = new Array();
				for(var x=0;x<selectList.length;x++){
					var selectObj = new Object();
					selectObj.m = selectList[x].selectName;
					var desc = selectList[x].selectDesc;
					var descArr = desc.split("<span></span>");
					var tempDesc="";
					var spanStr="";
					var spanStr2="";
					for(var t=0;t<descArr.length-1;t++){
						if(selectList[x].selectCode=="40028"&&t==0){
							tempDesc = tempDesc + descArr[t];
						}else{
							spanStr = '<span class=\'_amount\' >';
							spanStr2 = '</span>元';
							tempDesc = tempDesc + descArr[t]+spanStr
										+levelList[t].winAmount*lpmList[j].bonus.payoutRatio
											+spanStr2;
						}
						
					}
					selectObj.intro = tempDesc;
					selectObj.type = selectList[x].selectName;
					var str = selectList[x].selectIndex.split("_");
					selectObj.pos = [str[0],str[1]];
					selectObj.dataCode = lpmList[j].playModel.modelCode;
					selectObj.dataSelect = selectList[x].selectCode;
					selectObj.sumAmount = lpmList[j].playModel.winAmount*lpmList[j].bonus.payoutRatio;
					selectObj.winAmount = oriWinAmountStr;
					selectObj.payoutRatio = lpmList[j].bonus.payoutRatio;
					temp.push(selectObj);
				}
				obj.data = temp;
				tempArray.push(obj);
			}
			ConfigDom[playList[i].groupName]=tempArray;
		}
		return ConfigDom;
	};
	var initRebates = function(data){
		$("#userRebates").val(data.rebates);
		$("#noRebatesBonus").val(data.bonus);
		$("#bonusGroupId").val(data.bgId);
		//防止返点为undefined，导致总代尝试投注后因为rebates为bigdecimal导致无法提交后台，也就无法提示总代不能投注。
		if(data.rebates==undefined){
			data.rebates = "";
		}
		var html ="<option value='"+data.rebates+"' name='0'>"+"0&nbsp;返点</option>";
		html += "<option value='0' name='"+data.rebates+"'>"+data.rebates+"&nbsp;返点</option>";
		$("#userBonus").html(html);
	};
	return{
		initSsc : function(){
			var attrs = new Array();
			var values = new Array();
			if($("#tempCode").val()!=""){
				attrs.push("lotteryCode");
				values.push($("#tempCode").val());
			}
			formPost.submitAjaxForm1("init3d.shtml",attrs,values,initCallBackFuc);
		},
		getCallTime:function(){
			return callTime;
		},
		getSpanTime:function(){
			return spanTime;
		},
		getSSCobj:function(){
			return sscObj;
		}
	};
}();

$(document).ready(function(){
	sscdata.initSsc();
});