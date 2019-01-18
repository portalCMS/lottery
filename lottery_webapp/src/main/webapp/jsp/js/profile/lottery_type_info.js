var lotterType = function(){
	return{
		initType:function(lotteryCode){
			var attrs = new Array();
			var values = new Array();
			
			attrs.push("lotteryCode");
			values.push(lotteryCode);
			formPost.submitAjaxForm1("initSsc.shtml",attrs,values,this.queryTypeInfo);
		},
		queryTypeInfo:function(data){
			formPost.setTbodyObj(null,null);
			$("#typeData").html("");
			var playList = data.playList;
			//如果没有数据则页面不显示分页信息。
			if(playList==undefined||playList==null||playList.length==0){
				$("#typeData").html("");
				$("#typeData").html("<tr><td  height='39' align='center' " +" colspan='5' align='middle' class='f12 color_8d' >暂无记录<td></tr>");
				return;
			}
			var trObj = $("._trclone").clone(true);
			var lpmList ="",
				selectList ="",
				levelList ="",
				bonus ="";
			var trObjs = $(trObj).find("td");
			for(var a =0;a<playList.length;a++){
				lpmList = playList[a].lpmList;
				for(var b=0;b<lpmList.length;b++){
					selectList = lpmList[b].selectList;
					for(var c=0;c<selectList.length;c++){
						var td3 ="";
						var	td4 ="";
						var name="";
						var	groupName=playList[a].groupName;
						var	modelName=lpmList[b].playModel.modelName;
						var	selectName=selectList[c].selectName;
						if((modelName =="通选(官方)" || modelName == "直选" || modelName =="后三直选" 
								|| modelName =="后三组六" || modelName =="后三组三" || modelName=="后二直选" || modelName=="后二组选"
									|| modelName == "一码不定位" || modelName == "二码不定位" || modelName =="四星通选" || modelName=="四星直选"
								|| modelName == "中三组六" || modelName =="中三组三" || modelName == "中三直选" || modelName=="前三组三"
								|| modelName =="五星直选" || modelName=="五星通选" || modelName =="四星通选" || modelName =="前三直选"
								|| modelName =="前三组六"|| modelName =="前二组选" || modelName =="前二直选" || modelName =="大小单双")
								&& selectList.length>=1){
							for(var x=0;x<selectList.length;x++){
								name += selectList[x].selectName+"/";
							}
							$(trObjs).eq(0).html(groupName);
							$(trObjs).eq(1).html(modelName);
							$(trObjs).eq(2).html(name.substring(0, name.length-1));
							bonus = lpmList[b].bonus;
							levelList = lpmList[b].levelList;
							if(levelList.length>1){
								for(var d=0;d<levelList.length;d++){
									td3+=levelList[d].levelName+NumberFormat.formatNumber(parseInt(levelList[d].winAmount)*(parseFloat(bonus.payoutRatio)
											+parseFloat(bonus.rebates)),2,0)+"元+0.00%<br/>";
									td4+=levelList[d].levelName+NumberFormat.formatNumber(parseInt(levelList[d].winAmount)*parseFloat(bonus.payoutRatio),2,0)+"元+<span class='_amount'>"
										+parseFloat(bonus.rebates)*100+"</span>%<br/>";
								}
								$(trObjs).eq(3).html(td3);
								$(trObjs).eq(4).html(td4);
							}else{
								$(trObjs).eq(3).html(NumberFormat.formatNumber(parseInt(bonus.bonusAmount)*(parseFloat(bonus.payoutRatio)+
										parseFloat(bonus.rebates)),2,0)+"元+"+"0.00%");
								$(trObjs).eq(4).html(NumberFormat.formatNumber(parseInt(bonus.bonusAmount)*parseFloat(bonus.payoutRatio),2,0)+"元+<span class='_amount'>"+parseFloat(bonus.rebates)*100+"</span>%");
							}
							$("#typeData").append($(trObj).clone(true).show());
							break;
						}else{
							$(trObjs).eq(0).html(groupName);
							$(trObjs).eq(1).html(modelName);
							
							$(trObjs).eq(2).html(selectList[c].selectName);
							bonus = lpmList[b].bonus;
							levelList = lpmList[b].levelList;
							if(levelList.length>1){
								for(var d=0;d<levelList.length;d++){
									td3+=levelList[d].levelName+NumberFormat.formatNumber(parseInt(levelList[d].winAmount)*(parseFloat(bonus.payoutRatio)
											+parseFloat(bonus.rebates)),2,0)+"元+0.00%<br/>";
									td4+=levelList[d].levelName+NumberFormat.formatNumber(parseInt(levelList[d].winAmount)*parseFloat(bonus.payoutRatio),2,0)+"元+<span class='_amount'>"
										+NumberFormat.formatNumber(parseFloat(bonus.rebates)*100,2,0)+"</span>%<br/>";
								}
								$(trObjs).eq(3).html(td3);
								$(trObjs).eq(4).html(td4);
							}else{
								$(trObjs).eq(3).html(NumberFormat.formatNumber(parseInt(bonus.bonusAmount)*(parseFloat(bonus.payoutRatio)+
										parseFloat(bonus.rebates)),2,0)+"元+"+"0.00%");
								$(trObjs).eq(4).html(NumberFormat.formatNumber(parseInt(bonus.bonusAmount)*parseFloat(bonus.payoutRatio),2,0)+"元+<span class='_amount'>"+NumberFormat.formatNumber(parseFloat(bonus.rebates)*100,2,0)+"</span>%");
							}
							$("#typeData").append($(trObj).clone(true).show());
						}
					}
				}
			}
			$("._amount").formatCurrency();
		}
	}
}();

$(document).ready(function(){
	var firstType = $("._lotteryTypeList").eq(0);
	firstType.find("a:first").css("background","rgb(62, 147, 226)");
	lotterType.initType(firstType.find("a:first").attr("id"));
	$("._lotteryTypeList >a").click(function(){
		lotterType.initType($(this).attr("id"));
		$("._lotteryTypeList >a").css("background","#a4a4a4");
		$(this).css("background","rgb(62, 147, 226)");
	});
	firstType.addClass("current");
	$("#lotteryTypeInfo").addClass("current");
});