
function submitForm() {
	var rex1 =  /^\d+(\.{0,1}\d+){0,1}$/ ;//非负数（正浮点数   +   0）   
	var rex2 =  /^[1-9]d*|0$/ ;//非负整数   
	var rex3 =  /^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$/ ;//时分输入验证 
	
	var thirdPartyMinAmount = $("#thirdPartyMinAmount").val();
	var thirdPartyMaxAmount = $("#thirdPartyMaxAmount").val();
	var thirdPartyTimes = $("#thirdPartyTimes").val();
	var rechargeMinAmount = $("#rechargeMinAmount").val();
	var rechargeMaxAmount = $("#rechargeMaxAmount").val();
	var rechargeTimes = $("#rechargeTimes").val();
	var rechargeStartTime = $("#rechargeStartTime").val();
	var rechargeEndTime = $("#rechargeEndTime").val();
	var drawingMinAmount = $("#drawingMinAmount").val();
	var drawingMaxAmount = $("#drawingMaxAmount").val();
	var drawingTimes = $("#drawingTimes").val();
	var drawingStartTime = $("#drawingStartTime").val();
	var drawingEndTime = $("#drawingEndTime").val();
	//充值通道1
	var payAccount = $("#payAccount").val();
	var payUrl = $("#payUrl").val();
	var ybAccount = $("#ybAccount").val();
	var ybUrl = $("#ybUrl").val();
	//充值通道2
	var payAccountGroupTwo = $("#payAccountGroupTwo").val();
	var payUrlGroupTwo = $("#payUrlGroupTwo").val();
	var ybAccountGroupTwo = $("#ybAccountGroupTwo").val();
	var ybUrlGroupTwo = $("#ybUrlGroupTwo").val();
	
	var defaultRecharge = $("#defaultRecharge").val();
	var rechargeCount = $("#rechargeCount").val();
	var rechargeMoney = $("#rechargeMoney").val();
	
	if(defaultRecharge == ""){
		formPost.showErrorMessage("充值方式开放条件-默认充值方式不能为空!");
		return false;
	}
	if(rechargeCount == ""){
		formPost.showErrorMessage("充值方式开放条件-充值次数不能为空!");
		return false;
	}
	if(rechargeMoney == ""){
		formPost.showErrorMessage("充值方式开放条件-充值金额不能为空!");
		return false;
	}
	
	if(payAccount == ""){
		formPost.showErrorMessage("充值通道1环讯充值账号不能为空!");
		return false;
	}
	if(payUrl == ""){
		formPost.showErrorMessage("充值通道1环讯跳转地址不能为空!");
		return false;
	}
	if(ybAccount == ""){
		formPost.showErrorMessage("充值通道1易宝充值账号不能为空!");
		return false;
	}
	if(ybUrl == ""){
		formPost.showErrorMessage("充值通道1易宝跳转地址不能为空!");
		return false;
	}
	
	if(payAccountGroupTwo == ""){
		formPost.showErrorMessage("充值通道2环讯充值账号不能为空!");
		return false;
	}
	if(payUrlGroupTwo == ""){
		formPost.showErrorMessage("充值通道2环讯跳转地址不能为空!");
		return false;
	}
	if(ybAccountGroupTwo == ""){
		formPost.showErrorMessage("充值通道2易宝充值账号不能为空!");
		return false;
	}
	if(ybUrlGroupTwo == ""){
		formPost.showErrorMessage("充值通道2易宝跳转地址不能为空!");
		return false;
	}
	if(!checkAmount(thirdPartyMinAmount, thirdPartyMaxAmount,rex1, "第三方支付"))return false;
	if(!checkTimes(thirdPartyTimes,rex2,"第三方支付充值"))return false;
	if(!checkAmount(rechargeMinAmount, rechargeMaxAmount,rex1, "银行卡充值"))return false;
	if(!checkTimes(rechargeTimes,rex2,"银行卡充值"))return false;
	if(!checkStartEndTime(rechargeStartTime,rechargeEndTime,rex3,"银行卡充值"))return false;
	if(!checkAmount(drawingMinAmount, drawingMaxAmount,rex1, "银行卡提款"))return false;
	if(!checkTimes(drawingTimes,rex2,"银行卡提款"))return false;
	if(!checkStartEndTime(drawingStartTime,drawingEndTime,rex3,"银行卡提款"))return false;
	
	var picCode = $("#picCode").val();
	var rolePwd = $("#rolePwd").val();
	if (rolePwd == null || rolePwd == "") {
		formPost.showErrorMessage("财务密码不能为空!");
		return false;
	}
	
	if (picCode == null || picCode == "") {
		formPost.showErrorMessage("验证码不能为空!");
		return false;
	}

	financeConfigForm.submit();
}

/**
 * 检查金额
 * @param key1
 * @param key2
 * @param rex
 * @param info
 * @returns {Boolean}
 */
function checkAmount(key1,key2,rex,info){
	if (key1 == null || key1 == "") {
		formPost.showErrorMessage("最小金额不能为空!");
		return false;
	}else{
		if(!rex.test(key1)){
			formPost.showErrorMessage("最小金额必须为非负数！");
			return false;
		};
	}

	if (key2 == null || key2 == "") {
		formPost.showErrorMessage("最大金额不能为空！");
		return false;
	}else{
		if(!rex.test(key2)){
			formPost.showErrorMessage("最大金额必须为非负数！");
			return false;
		}else if(parseFloat(key1)>parseFloat(key2)){
			formPost.showErrorMessage("最大金额必须不小于最小金额！");
			return false;
		}
	}
	
	return true;
}

/**
 * 检查充提次数
 * @param key
 * @param rex
 * @param info
 * @returns {Boolean}
 */
function checkTimes(key,rex,info){
	if (key == null || key == "") {
		formPost.showErrorMessage("次数不能为空！");
		return false;
	}else{
		if(!rex.test(key)){
			formPost.showErrorMessage("次数必须为非负整数！");
			return false;
		};
		
	}
	return true;
}
/**
 * 检查开始结束时间
 * @param key
 * @param rex
 * @param info
 * @returns {Boolean}
 */
function checkStartEndTime(key1,key2,rex,info){
	if (key1 == null || key1 == "") {
		formPost.showErrorMessage("开始时间不能为空！");
		return false;
	}else{
		if(!rex.test(key1)){
			formPost.showErrorMessage("开始时间必须为  hh:mm 格式！");
			return false;
		};
	}
	
	if (key2 == null || key2 == "") {
		formPost.showErrorMessage("结束时间不能为空！");
		return false;
	}else{
		if(!rex.test(key2)){
			formPost.showErrorMessage("结束时间必须为  hh:mm 格式！");
			return false;
		};
	}
	
	return true;
}