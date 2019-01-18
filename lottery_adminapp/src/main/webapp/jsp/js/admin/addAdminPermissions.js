var addAdminPermissions = function(){
	var packageData = function(attrs,values){
		var falg = true;
		var id=$("#id").val();
		var permissionsName = $("#permissionsName").val();
		var permissions = $("#permissions").val();
		var sequence = $("#sequence").val();
		var levels = $("#levels").val();
//		var amPwd = $("#amPwd").val();
//		var picCode = $("#picCode").val();
		var status = $("#status").val();
		attrs.push("id");
		attrs.push("permissionsName");
		attrs.push("permissions");
		attrs.push("sequence");
		attrs.push("levels");
//		attrs.push("amPwd");
//		attrs.push("picCode");
		attrs.push("status");
		if(permissionsName==""){
			formPost.showErrorMessage("权限名不能空");
			falg = false;
			return falg;
		}
		if(permissions==""){
			formPost.showErrorMessage("权限URL不能空");
			falg = false;
			return falg;
		}
		if(sequence==""){
			formPost.showErrorMessage("排序序号不能为空");
			falg = false;
			return falg;
		}
		if(isNaN(sequence)){
			formPost.showErrorMessage("请填写数字");
			falg = false;
			return falg;
		}
		if(levels==""){
			formPost.showErrorMessage("请选择父级菜单");
			falg = false;
			return falg;
		}
		if(status==""){
			formPost.showErrorMessage("请选择权限类型");
			falg = false;
			return falg;
		}
//		if(amPwd==""){
//			formPost.showErrorMessage("操作不能空");
//			falg = false;
//			return falg;
//		}
//		if(picCode==""){
//			formPost.showErrorMessage("验证码不能空");
//			falg = false;
//			return falg;
//		}
		values.push(id);
		values.push(permissionsName);
		values.push(permissions);
		values.push(sequence);
		values.push(levels);
//		values.push(amPwd);
//		values.push(picCode);
		values.push(status);
		return falg;
	};
	return{
		addAdminPermissionsfuc:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitForm("insertAdminPer.do",attrs,values);
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
		addAdminPermissions.addAdminPermissionsfuc();
	});
});