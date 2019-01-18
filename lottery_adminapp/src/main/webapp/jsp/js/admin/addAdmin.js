var addAdmin = function(){
	
	var packageData = function(attrs,values){
		var falg = true;
		var adminName = $("#adminName").val();
		var userGroup = $("#userGroup").val();
		var adminPwd = $("#adminPwd").val();
		var adminRolePwd = $("#adminRolePwd").val();
		var amPwd = $("#amPwd").val();
		var picCode = $("#picCode").val();
		attrs.push("adminName");
		attrs.push("userGroup");
		attrs.push("adminPwd");
		attrs.push("adminRolePwd");
		attrs.push("amPwd");
		attrs.push("picCode");
		if(adminName==""){
			formPost.showErrorMessage("用户名不能空");
			falg = false;
			return falg;
		}
		if(adminName.length<6){
			formPost.showErrorMessage("用户名长度必须大于等于6位");
			falg = false;
			return falg;
		}
		if(adminPwd==""){
			formPost.showErrorMessage("登录密码不能空");
			falg = false;
			return falg;
		}
		if(adminPwd.length<6){
			formPost.showErrorMessage("登录密码长度必须大于等于6位");
			falg = false;
			return falg;
		}
		if(adminRolePwd==""){
			formPost.showErrorMessage("管理密码不能空");
			falg = false;
			return falg;
		}
		if(adminRolePwd.length<6){
			formPost.showErrorMessage("管理密码长度必须大于等于6位");
			falg = false;
			return falg;
		}
		if(amPwd==""){
			formPost.showErrorMessage("操作不能空");
			falg = false;
			return falg;
		}
		if(picCode==""){
			formPost.showErrorMessage("验证码不能空");
			falg = false;
			return falg;
		}
		values.push(adminName);
		values.push(userGroup);
		values.push(adminPwd);
		values.push(adminRolePwd);
		values.push(amPwd);
		values.push(picCode);
		return falg;
	};
	return{
		addAdminfuc:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitForm("addAdminUser.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	$("#code").attr("src", ctx+"/picode/createPic.do" );
	$("#code").attr("title", "点击刷新");
	$("#code").bind("click", function() {
		$("#code").attr("src", ctx+"/picode/createPic.do?" + Math.random());
	});
	$("._addBtn").click(function(){
		addAdmin.addAdminfuc();
	});
});