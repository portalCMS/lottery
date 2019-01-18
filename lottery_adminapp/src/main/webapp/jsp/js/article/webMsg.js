$(document).ready(function(){
	$("#toUserName").on("blur",function(){
		if($(this).val() == ""){
			$("._userNameError").text("对象用户不能为空");
			$("._userNameError").show();
		}else{
			$("._userNameError").text("");
			$("._userNameError").hide();
			userMsg.checkUserName($(this).val());
		}
	});
	$("#sendBtn").on("click",function(){
		userMsg.sendMsg();
	});
});

var userMsg = function(){
	var flag = false;
	var isHave = false;
	var callBack = {
		checkBack : function(data){
			var checkMsg = data.checkMsg;
			if(checkMsg != undefined && checkMsg != ""){
				$("._userNameError").text(checkMsg);
				$("._userNameError").show();
				flag = false;
			}else{
				$("._userNameError").text("");
				$("._userNameError").hide();
				flag = true;
				isHave = true;
			}
		},
		sendBack : function(data){
			formPost.showErrorMessage(data.success);
			clearFrom();
		}
	};
	var clearFrom = function(){
		$("#toUserName").val("");
		$("#title").val("");
		$("#message").val("");
	};
	var packageData = function(attrs,values){
		checked();
		$("._param").each(function(){
			attrs.push($(this).attr("id"));
			values.push($(this).val());
		});
	};
	var checked = function(){
		var title = $("#title").val();
		var message = $("#message").val();
		var toUserName = $("#toUserName").val();
		if(toUserName == ""){
			$("._userNameError").text("对象用户不能为空");
			$("._userNameError").show();
			flag = false;
			return;
		}else{
			$("._userNameError").text("");
			$("._userNameError").hide();
		}
		if(title == "" || title.length>500){
			$("._titleError").text("信息标题不能为空且长度不能大于250个字符");
			$("._titleError").show();
			flag = false;
			return;
		}else{
			$("._titleError").text("");
			$("._titleError").hide();
		}
		if(message == ""){
			$("._messageError").text("信息内容不能为空");
			$("._messageError").show();
			flag = false;
			return;
		}else{
			$("._messageError").text("");
			$("._messageError").hide();
		}
		if(!isHave){
			$("._userNameError").text("该用户不存在");
			$("._userNameError").show();
			flag = false;
			return;
		}else{
			$("._userNameError").text("");
			$("._userNameError").hide();
		}
		flag = true;
	};
	return{
		checkUserName : function(userName){
			var attrs = new Array();
			var values = new Array();
			attrs.push("toUserName");
			values.push(userName);
			formPost.submitAjaxForm("article/checkMsgUserName.do",attrs,values,callBack["checkBack"]);
		},
		sendMsg : function(){
			var attrs = new Array();
			var values = new Array();
			packageData(attrs,values);
			if(flag && isHave){
				formPost.submitAjaxForm("article/sendMsg.do",attrs,values,callBack["sendBack"]);
			}
		}
	};
}();