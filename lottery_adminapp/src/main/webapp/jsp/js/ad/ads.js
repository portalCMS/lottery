var ads = function(){
	
	return{
		showAdverInfo:function(code,id){
			var attrs = new Array();
			var values = new Array();
			attrs.push("regionCode");
			attrs.push("id");
			values.push(code);
			values.push(id);
			formPost.submitForm("showAdverInfo.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	$("._setPicBtn").click(function(){
		var code = $(this).attr("id");
		var id = $(this).attr("name");
		ads.showAdverInfo(code,id);
	});
});