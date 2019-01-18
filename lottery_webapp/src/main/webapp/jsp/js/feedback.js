var feedback = function(){
	
	var packageData = function(attrs,values){
		attrs.push("typeName");
		attrs.push("pageName");
		attrs.push("dsce");
		attrs.push("picCode");
		var typeName = $("#typeName").val();
		values.push(typeName);
		var pageName = $("#pageName").val();
		if(pageName==""){
			formPost.showErrorMessage("页面名称不能空");
			return false;
		}
		values.push(pageName);
		var dsce = $("#dsce").val();
		if(dsce==""){
			formPost.showErrorMessage("描述不能为空");
			return false;
		}
		if(dsce.length>7000){
			formPost.showErrorMessage("描述超过最大长度限制");
			return false;
		}
		values.push(dsce);
		var picCode = $("#picCode").val();
		if(picCode==""){
			formPost.showErrorMessage("验证码不能空");
			return false;
		}
		values.push(picCode);
		return true;
	};
	return{
		save:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitForm("saveFeedBack.html",attrs,values,true);
		}
	};
}();

$(document).ready(function(){
	$("._loginCode").attr("title", "点击刷新");
	$("._loginCode").bind("click", function() {
		$("._loginCode").attr("src", "createPic.html?" + Math.random());
	});
	$("._addBtn").click(function(){
		feedback.save();
	});
});