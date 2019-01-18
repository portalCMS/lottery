var addAdminRole = function(){
	
	var packageData = function(attrs,values){
		var falg = true;
		var id = $("#id").val();
		var roleName = $("#roleName").val();
		var roleEnName = $("#roleEnName").val();
		var roleLevel = $("#roleLevel").val();
		var amPwd = $("#amPwd").val();
		var picCode = $("#picCode").val();
		attrs.push("id");
		attrs.push("roleName");
		attrs.push("roleEnName");
		attrs.push("roleLevel");
		attrs.push("amPwd");
		attrs.push("picCode");
		if(roleName==""){
			formPost.showErrorMessage("角色名不能空");
			falg = false;
			return falg;
		}
		if(roleEnName==""){
			formPost.showErrorMessage("角色英文名不能空");
			falg = false;
			return falg;
		}
		if(roleLevel==""){
			formPost.showErrorMessage("请填写角色级别");
			falg = false;
			return falg;
		}
		if(isNaN(roleLevel)){
			formPost.showErrorMessage("角色级别请填写数字");
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
		values.push(roleName);
		values.push(roleEnName);
		values.push(roleLevel);
		values.push(amPwd);
		values.push(picCode);
		return falg;
	};
	return{
		addAdminRolefuc:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitForm("insertAdminRole.do",attrs,values);
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
		addAdminRole.addAdminRolefuc();
	});
});