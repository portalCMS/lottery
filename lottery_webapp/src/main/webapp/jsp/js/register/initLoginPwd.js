//页面初始化处理
$(document).ready(function() {
	$.checkFrom.initBind(".checkedAttr", true, true,true);
	
	$("._commit").on("click",function(){
		saveNewPwd();
	});
	//检查密码是否两次输入不一致
	$(".confirmPwd").on("blur",function(){
		confirmPwd(this);
	});
});
function confirmPwd(obj){
	var newPwd = $(".newPwd").val();
	var confirmPwd = $(obj).val();
	if(newPwd!=confirmPwd){
		var div = $(obj).attr("loc");
		var msg = "两次密码输入不一致！";
		$("#"+div).html(msg);
	}
}
function saveNewPwd(){
	var attrs = new Array();
	attrs.push('customerPwd');
	attrs.push('newPwd');
	attrs.push('confirmPwd');
	attrs.push('token');
	
	var values = new Array();
	values.push($(".customerPwd").val());
	values.push($(".newPwd").val());
	values.push($(".confirmPwd").val());
	values.push($("#token").val());
	formPost.submitForm1("saveLoginPwd.html",attrs,values);
}