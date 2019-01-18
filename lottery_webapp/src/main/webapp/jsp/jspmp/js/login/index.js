$(document).ready(function(){
	$("#xiangxi").click(function(){
		submitToForm();
	});
});

function submitToForm(){
	var attrs = new Array();
	var vals = new Array();
	//attrs.push("id");
	//vals.push(id);
	formPost.submitForm("showJiangxiCai.html",attrs,vals);
}