$(document).ready(function() {
	$("._querybtn").click(function(){
		permissionsItems.queryData(1);
	});
});

var permissionsItems = function(){
	var who,dwho;
	return{
		queryData:function(pageNo){
			var attrs = new Array();
			var values = new Array();
			attrs.push("pageNum");
			attrs.push("permissionsName");
			if(pageNo==undefined)pageNo==1;
			values.push(pageNo);
			values.push($("#permissionsName").val());
			formPost.submitForm("initAdminPermissions.do",attrs,values);
		}
	};
}();