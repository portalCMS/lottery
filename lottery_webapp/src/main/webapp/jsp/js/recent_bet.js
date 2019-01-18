var recent_bet = function(){
	var callbackfuc = {
		"initDataCallBack":function(data){
			formPost.setTbodyObj(null,null);
			$("#brdata").html("");
			var vos = data.page.pagelist;
			//如果没有数据则页面不显示分页信息。
			if(vos==undefined||vos==null||vos.length==0){
				$("#brdata").html("<tr><td  height='39' align='center' " +
							" colspan='7' align='middle' class='f12 color_8d' >暂无记录<td></tr>");
				$("#_myPageDiv").html("");
				return;
			}
			var trObj = $("._trclone").clone(true);
			for(var i=0;i<vos.length;i++){
				var betRecord = vos[i];
				$(trObj).find("td").eq(0).html(betRecord.lotteryCode);
				$(trObj).find("td").eq(1).html(betRecord.playCode);
				$(trObj).find("td").eq(2).html(betRecord.issueNo);
				var bn = betRecord.betNum;
				if(betRecord.betNum.length>12){
					bn = bn.substring(0,12)+"......";
				}
				$(trObj).find("td").eq(3).html(bn+"<input type='hidden' value='"+betRecord.betNum+"'/>");
				$(trObj).find("td").eq(4).addClass("color_red _amount");
				$(trObj).find("td").eq(4).html(betRecord.betMoney*betRecord.multiple);
				$(trObj).find("td").eq(5).addClass("color_red").html(betRecord.opernBetNumber);
				if(betRecord.betStatus==21001){
					$(trObj).find("td").eq(6).html("<a href='javascript:;' class='underline color_blue _cancel_bet'>可撤单" +
							"<input type='hidden' value='"+betRecord.id+"'/></a>");
				}else if(betRecord.betStatus==21002){
					$(trObj).find("td").eq(6).html("<span class=\"correctHint\">已中奖</span>" +
							"<p class=\"color_red _amount\">"+betRecord.winMoney+"</p>");
				}else if(betRecord.betStatus==21003){
					$(trObj).find("td").eq(6).html("未中奖");
					$(trObj).find("td").eq(6).addClass("color_8d");
				}else if(betRecord.betStatus==21004){
					$(trObj).find("td").eq(6).html("<del class=\"color_8d\">已撤单</del>");
				}
				if(betRecord.betType=='20001'){
					$(trObj).find("td").eq(7).html("追期投注");
				}else{
					$(trObj).find("td").eq(7).html("普通投注");
				}
				
				$("#brdata").append($(trObj).clone(true).show());
			}
			// 单行表格加背景色
//			$('.accountTab tbody tr:odd').find("td").css({
//				'background-color' : '#FFF'
//			});
//			$('.accountTab tbody tr:even').find("td").css({
//				'background-color' : '#f6f6f6'
//			});
			$("._rbn").unbind();
			$("._rbn").on('click',function(){
				var betNum = $(this).find("input").val();
				formPost.showInfoTab('投注号码明细',betNum);
			});
			
			$("._cancel_bet").unbind();
			$("._cancel_bet").on('click',function(){
				recent_bet.cancelBetById($(this));
			});
			
			$("._amount").formatCurrency();
		}
	};
	return{
		initData : function(pageNum){
			var attrs = new Array();
			var values = new Array();
			attrs.push("pageNum");
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			formPost.setTbodyObj($("#brdata"),$("._trclone"));
			formPost.submitAjaxForm1("finance/showrcbrdata.shtml",attrs,values,callbackfuc["initDataCallBack"],null);
		},
		cancelBetById : function(obj){
			if(!confirm("亲,是否确认撤销该投注单?")){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push($(obj).find("input").val());
			
			var betDesc = $(obj).closest("tr").find("td").eq(7).text();
			if(betDesc=="普通投注"){
				formPost.submitAjaxForm("cancelBR.shtml",attrs,values,this.cancelSuccess);
			}else{
				formPost.submitAjaxForm1("finance/cancelPlan.shtml",attrs,values,this.cancelSuccess,null);
			}
			
		},
		cancelSuccess:function(data){
			formPost.showErrorMessage(data.success);
			formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
			$("._cancel_bet").each(function(){
				if($(this).find("input").val()==data.betId){
					$(this).closest("td").html("<del class=\"color_8d\">已撤单</del>");
				}
			});
		}
	};
}();

$(document).ready(function(){
	$("#refreshBet").click(function(){
		if($("#betTable").is(":visible")){
			recent_bet.initData(1);
		}
	});
	
	$("#showBet").click(function(){
		if($("#betTable").is(":hidden")){
			$("#showBet").html("隐藏");
			$("#refreshBet").show();
			recent_bet.initData(1);
		}else{
			$("#showBet").html("展开");
			$("#refreshBet").hide();
		}
		$("#betTable").toggle(500);
	});
	$("#showBet").click();
});