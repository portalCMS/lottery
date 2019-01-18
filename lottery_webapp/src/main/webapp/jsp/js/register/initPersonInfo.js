$(document).ready(function() {
	$.checkFrom.initBind("._checkedAttr", true, true,true);
	
	$("._customerAlias").on("blur",function(){
			checkalias();
	});
	
	$("._commit").on("click",function(){
		saveInfo();
	});
});

function saveInfo(){
	//做一次所有的表单格式验证
	var check = $.checkFrom.checkAllType("._checkedAttr");
	if(!check){
		return;
	}
	//得到输入的字节数
	var alias = $("#alias").val();
	var inputNum = alias.replace(/[^\x00-\xff]/g, "**").length; 
	if(inputNum>12||inputNum<4){
		formPost.showErrorMessage("亲，昵称必须在2到6个汉字，4到12个字母或数字之间哦！","errorDiv");
		return ;
	}
	var attrs = new Array();
	attrs.push('customerAlias');
	attrs.push('qq');
	attrs.push('email');
	attrs.push('token');
	
	var values = new Array();
	values.push($("input[name='customerAlias']").val());
	values.push($("input[name='qq']").val());
	values.push($("input[name='email']").val());
	values.push($("#token").val());
	formPost.submitForm1("savePersonInfo.html",attrs,values);
}

function checkalias(){
	if($("input[name='customerAlias']").val().trim()==""){
		$("#errorDiv").addClass("ml15 color_red");
		$("#errorDiv").html("昵称不能为空！");
		return;
	}
//	if($("#alias").val().length<4||$("#alias").val().length>8){
//		$("#errorDiv").addClass("ml15 color_red");
//		$("#errorDiv").html("昵称必须在4~8个字符内!");
//		return;
//	}
	//得到输入的字节数
	var alias = $("#alias").val();
	var inputNum = alias.replace(/[^\x00-\xff]/g, "**").length; 
	if(inputNum>12||inputNum<4){
		formPost.showErrorMessage("亲，昵称必须在2到6个汉字，4到12个字母或数字之间哦！","errorDiv");
		return ;
	}
	
	var attrs = new Array();
	var values = new Array();
	attrs.push("customerAlias");
	values.push($("input[name='customerAlias']").val());
	formPost.submitAjaxForm1("user/checkAlias.shtml",attrs,values,aliasajaxdata,null);
}
function aliasajaxdata(data){
	var status = data.flag;
	if(status=='false'){
		flag=false;
		$("#errorDiv").addClass("ml15 color_red");
		$("#errorDiv").html("昵称已经被占用");
	}else{
		flag=true;
	}
}
