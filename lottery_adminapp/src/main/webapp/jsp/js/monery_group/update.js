var update = function(){
	return{
		nextUpdateNoPoint:function(){
			var params = new Array();
			var values = new Array();
			var flag = formPost.packageData("addParam", params, values);
			formPost.setFlag(flag);
			formPost.submitForm("nextUpdateNoPoint.do",params,values);
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

$(document).ready(function(){
	$.checkFrom.initBind('addParam', true, true);
	$("._updatebtn").click(function(){
		update.nextUpdateNoPoint();
	});
	$(".amoney").on("blur",function(){
		update.checkTheoryMoney($(this),"amoney");
	});
});