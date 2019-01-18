function checkAddBank(){
	var name = $("#name").val();
	if(name == ""){
		$("#error_msg_val").html("银行名称不能为空");
		$("#error_msg").show();
		return;
	}
	var url = $("#url").val();
	if(url == ""){
		$("#error_msg_val").html("URL不能为空");
		$("#error_msg").show();
		return;
	}
	var moneyMax = $("#moneyMax").val();
	if(moneyMax == ""){
		$("#error_msg_val").html("最大金额不能为空");
		$("#error_msg").show();
		return;
	}
	if(isNaN(moneyMax)){
		$("#error_msg_val").html("最大金额必须是数字");
		$("#error_msg").show();
		return;
	}
	var moneyMin = $("#moneyMin").val();
	if(moneyMin == ""){
		$("#error_msg_val").html("最小金额不能为空");
		$("#error_msg").show();
		return;
	}
	if(isNaN(moneyMin)){
		$("#error_msg_val").html("最小金额必须是数字");
		$("#error_msg").show();
		return;
	}
	var cancelTime = $("#cancelTime").val();
	if(cancelTime == ""){
		$("#error_msg_val").html("作废时间不能为空");
		$("#error_msg").show();
		return;
	}
	if(isNaN(cancelTime)){
		$("#error_msg_val").html("作废时间必须是数字");
		$("#error_msg").show();
		return;
	}
	var psStatus = $("input[name='psStatus']").val();
	if(psStatus==1){
		var psNum = $("#psNum").val();
		if(psNum == ""){
			$("#error_msg_val").html("附言位数不能为空");
			$("#error_msg").show();
			return;
		}
		if(isNaN(psNum)){
			$("#error_msg_val").html("附言位数必须是数字");
			$("#error_msg").show();
			return;
		}
	}
	
	var pwd = $("#pwd").val();
	if(pwd == ""){
		$("#error_msg_val").html("授权密码不能为空");
		$("#error_msg").show();
		return;
	}
	var code = $("#code").val();
	if(code == ""){
		$("#error_msg_val").html("验证码不能为空");
		$("#error_msg").show();
		return;
	}
	bankFrom.submit();
}


