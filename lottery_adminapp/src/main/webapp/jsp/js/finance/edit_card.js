
function init(){
	$("#error_msg").hide();
	$("#cardInventory").val($("#cardInventoryId").val());
	$("#levelSelect").val($("#cardLevel").val());
}

function checkBankCard(){
	var branceBankName = $("#branceBankName").val();
	if(branceBankName == ""){
		$("#error_msg_val").html("支行名称不能为空");
		$("#error_msg").show();
		$("#branceBankName").focus();
		return false;
	}
	var opencardName = $("#opencardName").val();
	if(opencardName == ""){
		$("#error_msg_val").html("开户名不能为空");
		$("#error_msg").show();
		$("#opencardName").focus();
		return false;
	}
	var cardNo = $("#cardNo").val();
	if(cardNo == ""){
		$("#error_msg_val").html("银行账户不能为空");
		$("#error_msg").show();
		$("#cardNo").focus();
		return false;
	}
	
	var rolePwd = $("#rolePwd").val();
	if(rolePwd == ""){
		$("#error_msg_val").html("财务密码不能为空");
		$("#error_msg").show();
		$("#rolePwd").focus();
		return false;
	}

	var picCode = $("#picCode").val();
	if(picCode == ""){
		$("#error_msg_val").html("验证码不能为空");
		$("#error_msg").show();
		$("#picCode").focus();
		return false;
	}
	return true;
}

function submitForm(){
	if(!checkBankCard()){
		return;
	}
	var selectBankId = $("#selectBank").val();
	$("#bankId").val(selectBankId);
	var cardInventoryId = $("#cardInventory").val();
	$("#cardInventoryId").val(cardInventoryId);
	var levelSelect = $("#levelSelect").val();
	$("#cardLevel").val(levelSelect);
	var bankName = $("#selectBank").find("option:selected").text().trim();
	$("#bankName").val(bankName);
	bankCardForm.submit();
}