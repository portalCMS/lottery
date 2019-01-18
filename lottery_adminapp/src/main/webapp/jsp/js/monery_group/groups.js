var groups = function(){
	
	return{
		showUpdateGroup:function(id){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			formPost.submitForm("showBonusGroup.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	$("._edit_btn").click(function(){
		var id = $(this).attr("id");
		groups.showUpdateGroup(id);
	});
});