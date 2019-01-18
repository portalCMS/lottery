var bonusGroup = function(){
	
	return{
		initData:function(){
			
		},
		addForm:function(action){
			var params = new Array();
			var values = new Array();
			var flag = formPost.packageData("addParam", params, values);
			formPost.setFlag(flag);
			formPost.submitForm(action,params,values);
		},
		showbonusGroupInfo:function(id){
			var attrs = new Array();
			attrs.push("id");
			var values = new Array();
			values.push(id);
			formPost.submitAjaxForm("showBonusGroup.do",attrs,values,bonusGroup.showData);
		},
		showData:function(data){
			$(".name").val(data.bg.name);
			$(".rebates").val(data.bg.rebates);
			$(".payoutRatio").val(data.bg.payoutRatio);
			$(".margin").val(data.bg.margin);
			$(".remark").val(data.bg.remark);
			$(".id").val(data.bg.id);
			$("#edit_modal").modal("show");
		},
		updateForm:function(action){
			if(action==undefined)action = "updateBonusGroups.do";
			var params = new Array();
			var values = new Array();
			formPost.packageData("updateParam", params, values);
			formPost.submitForm(action,params,values);
		},
		checkTheoryMoney:function(obj,objform){
			if(objform == "amoney"){
				switch ($(obj).attr("id")) {
				case "rebates":
					$(".money").html($("#theoryBonus").val()*$(obj).val());
					break;
				case "payoutRatio":
					$(".money1").html($("#theoryBonus").val()*$(obj).val());
					break;
				default:
					$(".money2").html($("#theoryBonus").val()*$(obj).val());
					break;
				}
			}else{
				switch ($(obj).attr("id")) {
				case "rebates":
					$(".umoney").html($("#utheory").val()*$(obj).val());
					break;
				case "payoutRatio":
					$(".umoney2").html($("#utheory").val()*$(obj).val());
					break;
				default:
					$(".umoney3").html($("#utheory").val()*$(obj).val());
					break;
				}
			}
		}
	};
}();

$(document).ready(function() {
	$("#add_money_locak_panel").hide();
	$(".dis_add_money_group_btn").click(function() {
		$("#add_money_locak_panel").toggle(500);
	});
	$(".edit_btn").click(function() {
		bonusGroup.showbonusGroupInfo($(this).attr("id"));
	});
	$(".addbtn").click(function(){
		bonusGroup.addForm("nextNoPoint.do");
	});
	$(".btn-update").click(function() {
		messenger.cofirmMsg("是否确认修改该奖金组?",bonusGroup.updateForm);
	});
	$(".amoney").on("blur",function(){
		bonusGroup.checkTheoryMoney($(this),"amoney");
	});
	$(".umoney").on("blur",function(){
		bonusGroup.checkTheoryMoney($(this),"umoney");
	});
	$.checkFrom.initBind('addParam', true, true);
	$.checkFrom.initBind('updateParam', true, true);
	
});