$(document).ready(function(){
	$(".loginCode").attr("title", "点击刷新");
	$(".loginCode").attr("src", ctx+"/ycode.html?key=cu&" + Math.random());
	$(".loginCode").bind("click", function() {
		$(".loginCode").attr("src", ctx+"/ycode.html?key=cu&" + Math.random());
	});
	$("._checkBtn").click(function(){
		check.checkUrl();
	});
});

var check = function(){
	
	var callback = {
		checkBck : function(data){
			formPost.showErrorMessage(data.success);
			$("#url").val("");
			$("#code").val("");
		}
	};
	
	var packageData = function(attrs,vals){
		var url = $("#url").val();
		var code = $("#code").val();
		if(url == ""){
			formPost.showErrorMessage("url地址不能为空");
			return false;
		}
		if(code == ""){
			formPost.showErrorMessage("验证码不能为空");
			return false;
		}
		attrs.push("url");
		vals.push(url);
		attrs.push("code");
		vals.push(code);
		return true;
	};
	
	
	return{
		
		checkUrl : function(){
			var attrs = new Array();
			var vals = new Array();
			var flag = false;
			flag = packageData(attrs,vals);
			if(flag){
				formPost.submitAjaxForm("check/checkUrl.shtml",attrs,vals,callback["checkBck"]);
			}
		}
	};
}();
