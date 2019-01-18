//页面初始化处理
$(document).ready(function() {

	// 检查银行卡数量是否超过限制
	var cards = 0;
	$("._cards").each(function() {
		cards++;
	});
	if (cards >= 4) {
		$(".bindingNewCardBtn").addClass("color_8d color_8d_hover").text("银行卡绑定已满");
	}

	$.checkFrom.initBind(".checkedAttr", true, true, true);
	$("#cancelBtn").on("click", function() {
		cancelBind();
	});
	$($(".bindCard").get(0)).click();

	$("#bind_panle").hide();
	$(".bindingNewCardBtn").on("click", function() {
		if (cards < 4) {
			$("#checkBindDiv").find("input").val("");
			if(cards>=1){
				$("#checkBindDiv").dialog("open");
				event.preventDefault();

				var div_h = $('.ui-dialog').height();
				var scrollTop = $(window).scrollTop();
				$('.ui-dialog').css({
					'position' : 'absolute',
					'top' : scrollTop + (div_h - 25) / 2
				});
				$('.ui-dialog-buttonset button').eq(1).addClass('sub ml20');
			}else{
				$("#bind_panle").slideDown(500);
				$(this).slideUp(500);
			}
		}
	});
	$("#cancelBtn").click(function() {
		$("#bind_panle").slideUp(500);
		$(".bindingNewCardBtn").slideDown(500);
		
	});
	$("#bindCardMenu").addClass("current");
	
	//修改登录密码弹出框
	$("#checkBindDiv").dialog({
		autoOpen : false,
		width : 426,
		buttons : [ {
			text : "提交",
			click : function() {
				bindCard.checkCard();
			}
		}, {
			text : "返 回",
			click : function() {
				$("#errorDiv").html("");
				$("#loginDiv input").val("");
				$(this).dialog("close");
			}
		} ]
	});

});
function cancelBind() {
	$("#bind_panle input[type='text']").val('');
	$("#bind_panle input[type='password']").val('');
	$("#bind_panle_btn").click();
}
function submitBindForm() {
	// 做一次所有的表单格式验证
	var check = $.checkFrom.checkAllType("#bind_panle");
	if (!check) {
		return;
	}
	var province = $("#province option:selected").val();
	var city = $("#city option:selected").val();
	$("#address").val(province + city);
	var selectBankId = $("#selectBank option:selected").val();
	$("#bankId").val(selectBankId);
	saveUserCardForm.submit();
}

function showBindCardDiv() {
	$("#bindCardDiv").toggle();
}

var bindCard = function(){
	
	var callBaks = {
		checkCardBck : function(data){
			if(data.isOk!=undefined &&data.isOk != ""){
				$("#checkBindDiv").dialog("close");
				$("#bind_panle").slideDown(500);
				$(this).slideUp(500);
			}else{
				formPost.showErrorMessage("验证不通过,信息有误");
			}
		}
	};
	var packageData = function(atrs,vals){
		var flag = true;
		$("._cparam").each(function(){
			var val = $(this).val();
			if(val==""){
				formPost.showErrorMessage($(this).attr("alt")+"不能为空");
				flag = false;
				return flag;
			}else{
				atrs.push($(this).attr("id"));
				vals.push(val);
			}
		});
		return flag;
	};
	return{
		checkCard : function(){
			var atrs = new Array();
			var vals = new Array();
			var flag = packageData(atrs,vals);
			if(flag){
				formPost.submitAjaxForm1("user/checkBindCard.shtml",atrs,vals,callBaks["checkCardBck"]);
			}
		}
	};
}();
