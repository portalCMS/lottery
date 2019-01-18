//$(document).ready(function(){
//	$(".errorHint").html("");
//	$(".maxBtn").click(function(){
//		submitForm();
//	});
//});
;(function($){
	$(document).on('click','.btn', function(){
		submitForm();
	});
	
})(Zepto);
function submitForm() {
	var uname = $("#customerName").val();
	var upwd = $("#customerPwd").val();
	if (uname == null || uname == "") {
		$(".errorHint").html("用户不能为空");
		return;
	}

	if (upwd == null || upwd == "") {
		$(".errorHint").html("密码不能为空");
		return;
	}
	$("#loginForm").submit();
	
//	$.ajax({
//		type: "GET",
//		url: "loginmp.html",
//		dataType: "html",
//		data:{customerName: uname,customerPwd:upwd },
//		success: function(data) {
//			//$("aside").html(ret);
//			alert(data.pwdError);
//			window.location="indexmp.html";
//		},
//		error: function(){
//		}
//	})
	
}


