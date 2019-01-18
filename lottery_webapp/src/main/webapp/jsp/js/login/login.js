function init() {
	$(".loginCode").attr("title", "点击刷新");
	$(".loginCode").attr("src", "createPic.html?" + Math.random());
	$(".loginCode").bind("click", function() {
		$(".loginCode").attr("src", "createPic.html?" + Math.random());
	});
	$(".errorHint").html("");
}
function submitForm() {
	var uname = $(".uname").val();
	var upwd = $(".pwd").val();
	var code = $(".code").val();
	if (uname == null || uname == "") {
		$(".errorHint").html("用户不能为空");
		return;
	}

	if (upwd == null || upwd == "") {
		$(".errorHint").html("密码不能为空");
		return;
	}
	if (code != undefined &&(code == null || code == "")) {
		$(".errorHint").html("验证码不能为空");
		return;
	}
	loginForm.submit();
}

var loginKeyManager = function(){
	this.bindEvent = function(){
		$(document).on("keydown",function(e){
			var keyCode = e.keyCode;
			if(keyCode == 13){
				$("._loginSend").trigger("click");
			}
		});
	};
	return{
		init:function(){
			bindEvent();
		}
	};
}();


$(document).ready(function(){
	init();
	$(".maxBtn").click(function(){
		submitForm();
	});
	loginKeyManager.init();
});