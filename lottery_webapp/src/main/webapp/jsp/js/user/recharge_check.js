function submitForm(){
	var attrs = new Array();
	attrs.push('cardNo');
	attrs.push('bankName');
	attrs.push('sourceId');
	attrs.push('remark');
	attrs.push('referenceId');
	attrs.push('cashAmount');
	attrs.push('token');
	
	var values = new Array();
	values.push($("#cardNo").val());
	values.push($("#bankName").val());
	values.push($("#sourceId").val());
	values.push($("#remark").val());
	values.push($("#referenceId").val());
	values.push($("#cashAmount").val());
	values.push($("#token").val());
	formPost.submitAjaxForm("user/saveUserRecharge.shtml",attrs,values,saveSuccess);
}
function previous(){
	 location.href="showUserRecharge.html";
}
function saveSuccess(data){
	$(".confirmBtn").hide();
	formPost.showErrorMessage(data.success);
	
}