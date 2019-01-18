$(document).ready(function(){
	$(".loginCode").attr("title", "点击刷新");
	$(".loginCode").attr("src", ctx+"/ycode.html?key=regkey&&" + Math.random());
	$(".loginCode").bind("click", function() {
		$(".loginCode").attr("src", ctx+"/ycode.html?key=regkey&&" + Math.random());
	});
	$("._commit").click(function(){
		regUser.saveNewUser();
	});
});

var regUser = function(){
	var checkForm = function(){
		var customerName = $("#customerName").val();
		var customerPwd = $("#customerPwd").val();
		var confirmPwd = $("#confirmPwd").val();
		var code = $("#code").val();
		if(customerName == ""){
			$("#errorDiv").html("用户名不能为空");
			return false;
		}
		if(customerPwd == ""){
			$("#errorDiv").html("密码不能为空");
			return false;
		}
		if(confirmPwd == ""){
			$("#errorDiv").html("重复密码不能为空");
			return false;
		}
		if(code == ""){
			$("#errorDiv").html("验证码不能为空");
			return false;
		}
		if(customerName.length < 6){
			$("#errorDiv").html("用户名必须大于等于6位字母、数字、中文");
			return false;
		}
		if(customerPwd.lenth < 6 && customerPwd.lenth > 26){
			$("#errorDiv").html("密码必须大于等于6位且小于等于26位字母、数字");
			return false;
		}
		if(customerPwd != confirmPwd){
			$("#errorDiv").html("密码和重复密码不一致");
			return false;
		}
		return true;
	};
	var packageDate = function(attrs,values){
		var flag = false;
		flag = checkForm();
		$("._formvalue").each(function(){
			attrs.push($(this).attr("id"));
			values.push($(this).val());
		});
		return flag;
	};
	return{
		saveNewUser:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageDate(attrs,values);
			if(!flag){
				return;
			}
			formPost.submitForm("linkOpenAccount.html",attrs,values);
		}
	};
}();