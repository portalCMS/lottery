//页面初始化处理
$(document).ready(function() {
	$.checkFrom.initBind(".checkedAttr", true, true,true);
	
	$("._commit").on("click",function(){
		saveNewPwd();
	});
	//检查密码是否两次输入不一致
	$("._confirmPwd").on("blur",function(){
		confirmPwd(this);
	});
});
function confirmPwd(obj){
	var newPwd = $("._newPwd").val();
	var confirmPwd = $(obj).val();
	if(newPwd!=confirmPwd){
		var div = $(obj).attr("loc");
		var msg = "两次密码输入不一致！";
		$("#"+div).html(msg);
	}
}
function saveNewPwd(){
	var attrs = new Array();
	attrs.push('newPwd');
	attrs.push('confirmPwd');
	attrs.push('question');
	attrs.push('answer');
	attrs.push('token');
	
	var values = new Array();
	values.push($("._newPwd").val());
	values.push($("._confirmPwd").val());
	values.push($("._question").val());
	values.push($("._answer").val());
	values.push($("#token").val());
	formPost.submitForm1("saveMoneyPwd.html",attrs,values);
}