var traceInfo = function(){
	var temp;
	var index = 0;
	var callFucs = {
		"cancelCallFuc":function(data){
			formPost.showErrorMessage(data.success);
			if(data.success==undefined){
				return;
			}
			var obj; 
			$("._showDetail").each(function(){
				if($(this).find("._issueNo").val()==temp){
					obj = $(this);
					return;
				}
			});
			
			$(obj).find(".ward_info  ._myInfo span:last").attr('class','color_8d').html("<del>已撤单</del>");
			$(obj).find(".ward_info  ._cancel").html("");
			traceInfo.showDetail(obj,true);
			formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
		}
	};
	return{
		queryPlanInfo:function(orderNo,issueNo){
			var attrs = new Array();
			var values = new Array();
			attrs.push("orderNo");
			attrs.push("issueNo");
			values.push(orderNo);
			values.push(issueNo);
			formPost.submitForm("queryPlanInfo.html",attrs,values,true);
		},
		cancelPlan:function(self){
			if(confirm("亲,机不可失失不再来哦!你确定要这么做吗?")){
				var attrs = new Array();
				var values = new Array();
				attrs.push("orderNo");
				attrs.push("issueNo");
				values.push($(self).closest("._showDetail").find("._orderNo").val());
				values.push($(self).closest("._showDetail").find("._issueNo").val());
				temp = $(self).closest("._showDetail").find("._issueNo").val();
				formPost.submitAjaxForm1("finance/cancelPlan.shtml",attrs,values,callFucs["cancelCallFuc"],null);
			}
		},
		setIndex : function(idx){
			index = idx;
		},
		getIndex : function(){
			return index;
		},
		showDetail:function(obj,isCancel){
//			if($(obj).find("._bets ._hidInfo").size()>0&&isCancel==undefined){
//				return;
//			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("orderNo");
			attrs.push("issueNo");
			
			var orderNo = $(obj).find("._orderNo").val();
			var issueNo = $(obj).find("._issueNo").val();
			values.push(orderNo);
			values.push(issueNo);
			
			formPost.submitAjaxForm("finance/queryIssueBets.shtml",attrs,values,this.showInfo);
		},
		showInfo:function(data){
			var obj; 
			$("._showDetail").each(function(){
				if($(this).find("._issueNo").val()==data.issueNo){
					obj = $(this);
					return;
				}
			});
			$(obj).find("._betInfo").html("");
			$(obj).find("._bets").html("");
			
			var $betdtl = $("._hidInfo:last").clone(true);
			$betdtl.show();
			
			var isWin=false;
			var isLost=false;
			var bets = data.brs;
			var twAmount = 0.00;
			for(var i=0;i<bets.length;i++){
				var bet = bets[i];
				$betdtl.find("._name").html("["+bet.playCode+"("+bet.selectCode+")]");
				$betdtl.find("._betnum").html(bet.betNum);
				$betdtl.find("._bilenum").html(bet.bileNum);
				if(bet.betStatus==21001){
					$betdtl.find("._status").html("投注成功");
					$betdtl.find("._status").attr("class","_status color_8d");
					$betdtl.find("._award").html("");
				}else if(bet.betStatus==21002){
					$betdtl.find("._status").html("中奖");
					$betdtl.find("._status").attr("class","_status color_red");
					$betdtl.find("._award").html(bet.winMoney);
					twAmount = twAmount + bet.winMoney;
					isWin=true;
				}else if(bet.betStatus==21003){
					$betdtl.find("._status").html("未中奖");
					$betdtl.find("._status").attr("class","_status color_8d");
					$betdtl.find("._award").html("");
					isLost=true;
				}else if(bet.betStatus==21004){
					$betdtl.find("._status").html("已撤销");
					$betdtl.find("._status").attr("class","_status color_8d");
					$betdtl.find("._award").html("");
				}
				
				var betModel="元模式";
				if(bet.betModel==0.01){
					betModel = "分模式";
				}else if(bet.betModel==0.1){
					betModel = "角模式";
				}
				$betdtl.find("._betModel").html(betModel);
				$betdtl.find("._betMoney").html(bet.betMoney*bet.multiple);
				$(obj).find("._bets").append($betdtl.clone(true));
			}
			if(isWin||isLost){
				var $awardInfo = $("._awardInfo:last").clone(true);
				$awardInfo.show().html("[开奖号码]");
				var $openNum = $("._openNum:last").clone(true);
				$openNum.show().html(data.openNumber);
				
				$(obj).find("._betInfo").append($awardInfo.clone(true));
				$(obj).find("._betInfo").append($openNum.clone(true));
				if(isWin){
					var $twAmount = $("._twAmount:last").clone(true);
					$twAmount.show().html(twAmount);
					$(obj).find("._betInfo").append($twAmount.clone(true));
					
					$("._twAmount").formatCurrency();
					$("._twAmount").each(function(){
						$(this).html("总奖金："+$(this).html());
					});
				}
			}
			$("._betMoney").formatCurrency();
			$("._award").formatCurrency();
			
		},
		queryCurIssue:function(){
			var attrs = new Array();
			var values = new Array();
			attrs.push("lotteryCode");
			values.push($("#lotCode").val());
			formPost.submitAjaxForm("finance/queryCurIssue.shtml",attrs,values,this.showCurBet);
		},
		showCurBet:function(data){
			var issueNo = data.issueNo;
			$("._issueNo").each(function(){
				if($(this).val()==issueNo){
					$(this).closest("._showDetail").click();
					$(this).closest("._showDetail").find("._issueInfo").text("(当前期)");
					return;
				}
			});
		}
	};
	
}();

var panel = function() {
	var config = {
		className : {
			items : "panel_items",
			item : "panel_item",
			info : "panel_info",
			ward : "ward_info"
		},
		display : true
	};
	var $ward;
	var class_items = config.className.items, class_info = config.className.info, 
	class_item = config.className.item, class_ward = config.className.ward;
	function DOMManager() {
		var d = $("." + class_ward);
		d.each(function(index, value) {
			var ward_no = parseInt($(value).find("span").first().text());
			if (ward_no != $ward) {
				$(value).parent().find("." + class_info).hide();
			}
		});
	}
	function eventManager() {
		$("." + class_item + " ." + class_ward).on("click", function(e) {
			$("." + class_info).hide();
			$(this).parent().find("." + class_info).toggle(200);
			return;
		});
		
//		$("." + class_item + " ." + class_ward).on("dblclick", function(e) {
//			$("._showDetail .panel_info").hide(200);
//			return;
//		});

	}
	return ( {
		init : function(ward) {
			$ward = ward;
			DOMManager();
			eventManager();
		}
	});
}();


$(document).ready(function(){
	panel.init(123171);
	$("#panel_tip").sTip({
		tipInfo : "单击期号信息行，展开或隐藏查看该期方案详情；点击展开标签，展开未展开的10期方案详情。 ",
		offsetLeft:30
	});
	if($("._cancel").html()!=undefined){
		$("._cancel").on("click",function(){
			traceInfo.setIndex($(this));
			traceInfo.cancelPlan($(this));
		});
	}
	$("._showDetail").on('click',function(){
		$(this).find(".panel_info").show(200);
		traceInfo.showDetail(this);
	});
	
	$("._show10Detail").on('click',function(){
		var i=0;
		$("._showDetail").each(function(index){
			if(i>10)return;
			if($(this).find(".panel_info").is(":hidden")){
				i++;
				$(this).find(".panel_info").show(200);
				traceInfo.showDetail(this);
			}
		});
		
	});
	var ft = $("#fromType").val();
	$("#"+ft).addClass("current");
	//显示当前期号对应的追号记录。
	traceInfo.queryCurIssue();
});