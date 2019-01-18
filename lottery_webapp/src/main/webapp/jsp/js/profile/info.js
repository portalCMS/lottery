$(document).ready(function(){
	$("._aliaserror").html("");
	$("._saveBtn").click(function(){
		if(info.getFlag()){
			info.submitform();
		}else{
			$("._aliaserror").html("已经被占用");
		}
	});
	$("#customerAlias").blur(function(){
		info.checkalias();
	});
	$("#userDataMenu").addClass("current");
	
	$.checkFrom.initBind("._addparam",true,true,true);
});

var info = function(){
	var flag = true;
	return{
		checkalias:function(){
			var attrs = new Array();
			var values = new Array();
			attrs.push("customerAlias");
			values.push($("#customerAlias").val());
			formPost.submitAjaxForm1("user/checkAlias.shtml",attrs,values,info.checkaliasajaxdata,null);
		},
		checkaliasajaxdata:function(data){
			var status = data.flag;
			if(status=='false'){
				flag=false;
				$("._aliaserror").html("已经被占用");
			}else{
				info.checkAliasLength();
				$("._aliaserror").html("");
				flag=true;
			}
		},
		checkAliasLength:function(){
			//得到输入的字节数
			var alias = $("#customerAlias").val();
			var inputNum = alias.replace(/[^\x00-\xff]/g, "**").length; 
			if(inputNum>12||inputNum<4){
				formPost.showErrorMessage("亲，昵称必须在2到6个汉字，4到12个字母或数字之间哦！","erroralias");
				$("#customerAlias").select();
				return true;
			}
			return false;
		},
		submitform:function(){
			//做一次所有的表单格式验证
			var check = $.checkFrom.checkAllType("#customerAlias");
			if(!check){
				return;
			}
			if(info.checkAliasLength()){
				return;
			}
			
			var attrs = new Array();
			var values = new Array();
			info.infopackagedata(attrs, values);
			formPost.submitForm("updateUserData.html",attrs,values);
		},
		infopackagedata:function(attrs,values){
			$("._addparam").each(function(){
				attrs.push($(this).attr("name"));
				values.push($(this).val());
			});
		},
		setFlag : function(flag1){
			flag = flag1;
		},
		getFlag : function(){
			return flag;
		}
	};
}();