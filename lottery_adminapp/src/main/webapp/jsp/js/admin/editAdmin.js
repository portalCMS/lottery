var editAdmin = function(){
	
	var packageData = function(attrs,values){
		var falg = true;
		var id = $("#id").val();
		var adminName = $("#adminName").val();
		var userGroup = $("#userGroup").val();
		var adminPwd = $("#adminPwd").val();
		var adminRolePwd = $("#adminRolePwd").val();
		var amPwd = $("#amPwd").val();
		var picCode = $("#picCode").val();
		attrs.push("id");
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
		values.push(id);
		values.push(adminName);
		values.push(userGroup);
		values.push(adminPwd);
		values.push(adminRolePwd);
		values.push(amPwd);
		values.push(picCode);
		return falg;
	};
	return{
		editAdminfuc:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitForm("editadminuser.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	$("#code").attr("src", ctx+"/picode/createPic.do" );
	$("#code").attr("title", "点击刷新");
	$("#code").bind("click", function() {
		$("#code").attr("src", ctx+"/picode/createPic.do?" + Math.random());
	});
	$("._editBtn").click(function(){
		editAdmin.editAdminfuc();
	});
	
});