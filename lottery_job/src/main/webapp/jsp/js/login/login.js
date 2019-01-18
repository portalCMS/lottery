function init() {
	$("#error_msg").hide();
	$("#code").attr("src", "picode/createPic.do" );
	$("#code").attr("title", "点击刷新");
	$("#code").bind("click", function() {
		$("#code").attr("src", "picode/createPic.do?" + Math.random());
	});
}

function submitForm() {
	var adminName = $("#adminName").val();
	var adminPwd = $("#adminPwd").val();
	var picCode = $("#picCode").val();
	if (adminName == null || adminName == "") {
		$("#error_msg_val").html("用户不能为空");
		$("#error_msg").show();
		return;
	}

	if (adminPwd == null || adminPwd == "") {
		$("#error_msg_val").html("密码不能为空");
		$("#error_msg").show();
		return;
	}

	if (picCode == null || picCode == "") {
		$("#error_msg_val").html("验证码不能为空");
		$("#error_msg").show();
		return;
	}
//	var params = "adminName=" + adminName + "&adminPwd="+adminPwd + "&picCode=" + picCode;
//	jQuery(function(){
//		$.ajax({
//			type : 'post',
//			url : 'login.do',
//			data : params,
//			dataType : 'json',
//			success : function(data) {
//				if(data.errorMsg!=null&&data.errorMsg!=""){
//					$("#error_msg_val").html(data.errorMsg);
//					$("#error_msg").show();
//					$("#code").attr("src", "img/createPic.img?" + Math.random());
//				}
//			},
//			error : function() {
//			}
//		});
//	});
	loginform.submit();

}