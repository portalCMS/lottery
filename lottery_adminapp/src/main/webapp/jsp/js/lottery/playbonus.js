$(document).ready(function() {
	$.checkFrom.initBind(".checkedAttr", true, true);
	$(".backBtn").on("click", function() {
		bonusForm.modifyPalyModel();
	});
	$("._amount").formatCurrency();
	$(".nav_content").eq(0).show();
	$(".bonusclass").eq(0).addClass("active");
	
	// 单行表格加背景色
	$('.table tbody tr:odd').find("td").css({
		'background-color' : '#FFF'
	});
	$('.table tbody tr:even').find("td").css({
		'background-color' : '#f6f6f6'
	});
});

var bonusForm = function(){
	return{
		packageData:function(attrs,values){
			var $tr = $("._lpbRow");
			var i=0;
			$tr.each(function(){
				var self = $(this);
				attrs.push("playBonusList["+i+"].modelCode");
				attrs.push("playBonusList["+i+"].bonusGroupId");
				attrs.push("playBonusList["+i+"].modelName");
				attrs.push("playBonusList["+i+"].winningRate");
				attrs.push("playBonusList["+i+"].bonusAmount");
				attrs.push("playBonusList["+i+"].payoutRatio");
				attrs.push("playBonusList["+i+"].rebates");
				attrs.push("playBonusList["+i+"].margin");
				
				values.push($(self).find("._mc").val());
				values.push($(self).find("._bid").val());
				values.push($(self).find("._mn").val());
				values.push($(self).find("._wr").eq(0).text());
				values.push($(self).find("._wa").eq(0).asNumber());
				values.push($(self).find("._pr").val());
				values.push($(self).find("._rb").val());
				values.push($(self).find("._mg").val());
				
				i++;
			});
		},
		submitForm:function(){
			var validate = true;
			for(var i=0;i<$("._lpbRow").size();i++){
				var lpb = $("._lpbRow").get(i);
				var attrs = $(lpb).find(".checkedAttr");
				var totalVal=0;
				for(var j=0;j<attrs.size();j++){
					var attr = attrs.get(j);
					totalVal += parseFloat($(attr).val()); 
				}
				//防止js小数相加时，产生0.99999999的数值
				totalVal = NumberFormat.formatNumber(totalVal,0,0);
				if(totalVal!=1){
					validate =false;
					break;
				}
			}
			if(!validate){
				Messenger().post({
					message :"返奖率，返点率，利润率之和必须为100%！",
					type : 'error',
					showCloseButton : true
				});
				return;
			}
			
			var attrs = new Array();
			var values = new Array();
			bonusForm.packageData(attrs,values);
			formPost.submitForm("savePlayBonusGroup.do",attrs,values);
		},
		submitForm2:function(){
			var validate = true;
			for(var i=0;i<$("._lpbRow").size();i++){
				var lpb = $("._lpbRow").get(i);
				var attrs = $(lpb).find(".checkedAttr");
				var totalVal=0;
				for(var j=0;j<attrs.size();j++){
					var attr = attrs.get(j);
					totalVal += parseFloat($(attr).val()); 
				}
				//防止js小数相加时，产生0.99999999的数值
				totalVal = NumberFormat.formatNumber(totalVal,0,0);
				if(totalVal!=1){
					validate =false;
					break;
				}
			}
			if(!validate){
				Messenger().post({
					message :"返奖率，返点率，利润率之和必须为100%！",
					type : 'error',
					showCloseButton : true
				});
				return;
			}
			
			var attrs = new Array();
			var values = new Array();
			bonusForm.packageData(attrs,values);
			formPost.submitForm("saveModifylpb.do",attrs,values);
		},
		/**
		 * 修改玩法相关的所有配置
		 */
		modifyPalyModel:function (){
			var attrs = new Array();
			attrs.push("lotteryCode");
			attrs.push("lotteryGroup");
			var values = new Array();
			values.push($("#lotteryCode").val());
			values.push($("#lotteryGroup").val());
			formPost.submitForm("showModifyModel.do",attrs,values);
		},
		showBonusGroup:function (obj){
			$(".bonusclass").removeClass('active');
			$(obj).parent().addClass('active');
			var showId = $(obj).attr('name');
			$(".nav_content").hide();
			$("#"+showId).show();
		}
	};
}();