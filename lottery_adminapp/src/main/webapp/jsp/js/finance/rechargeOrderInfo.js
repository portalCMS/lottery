function approveOrder(){
	var receiveAmount = $("#receiveAmount").val();
	var orderAmount = $("#orderAmount").asNumber();
	if(receiveAmount>orderAmount){
		formPost.showErrorMessage("实际到账金额必须小于或等于充值金额！");
		$("#receiveAmount").focus();
		return;
	}
	if(!$.checkFrom.checkAllType("#rechargeDiv")){
		return;
	}
	$(".btn-danger").removeAttr("onclick");
	
	var attrs = new Array();
	attrs.push("id");
	attrs.push("orderNumber");
	attrs.push("receiveAmount");
	attrs.push("transferAmount");
	attrs.push("rsvst4");
	var vals = new Array();
	vals.push($("#id").val());
	vals.push($("#orderNumber").val());
	vals.push($("#receiveAmount").val());
	vals.push($("#transferAmount").val());
	vals.push($("#remarkInfo").val());
	formPost.submitForm("approveRechargeOrder2.do",attrs,vals);
}
function rejectOrder(){
	var orderId = $("#id").val();
	var attrs = new Array();
	attrs.push("id");
	attrs.push("rsvst4");
	var vals = new Array();
	vals.push(orderId);
	vals.push($("#remarkInfo").val());
	$(".btn-default").removeAttr("onclick");
	formPost.submitForm("rejectRechargeOrder2.do",attrs,vals);
}
function restartOrder(){
	//财务密码必须不为空且正确
	var adminPwd = $("#adminPwd").val();
	if(adminPwd==null||adminPwd.trim()==""){
		formPost.showErrorMessage("财务密码不能为空!");
		return;
	}
	
	restartOrderForm.submit();
}

//整个函数启动的入口。
$(function() {
	$("._amount").formatCurrency();
	$.checkFrom.initBind(".checkedAttr", true, true,true);
});