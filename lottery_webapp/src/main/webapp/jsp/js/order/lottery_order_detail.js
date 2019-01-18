var order_detail = function(){
	var callFucs = {
		"cancelCallBack":function(data){
			formPost.showErrorMessage(data.success);
//			var attrs = new Array();
//			var values = new Array();
//			attrs.push("id");
//			values.push($("._param").attr("id"));
//			attrs.push("urlName");
//			values.push($("._fromUrlName").text());
//			attrs.push("fromUrl");
//			values.push($("._fromUrlName").attr("href"));
//			formPost.submitForm("showbrdetail.html",attrs,values);
			$("._status").html("已撤单");
			$("._cancel").hide();
			formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
		}
	};
	return{
		cancelBetRecord:function(){
			if(!confirm("亲,你确定要放弃这次中大奖的机会吗?")){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push($("._param").attr("id"));
			formPost.submitAjaxForm1("cancelBR.shtml",attrs,values,callFucs["cancelCallBack"],null);
		}
	};
}();

$(document).ready(function(){
	if($("._cancel").html()!=undefined){
		$("._cancel").on("click",order_detail.cancelBetRecord);
	}
	$("#betRecordsMenu").addClass("current");
});