//页面初始化处理
$(document).ready(function() {
	// 检查银行卡数量是否超过限制
	var cards = 0;
	$(".bind-item").each(function() {
		cards++;
	});
	if (cards >= 4) {
		$(".fs20").html("<font color='red'>亲,银行卡绑定已满</font>");
		$(".btn").removeAttr("href");
	}else{
		var ss = 4-cards;
		var str="您还可以绑定"+ss+"张银行卡"
		$(".fs20").html(str);
	}

	//$.checkFrom.initBind(".checkedAttr", true, true, true);
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

});
function cancelBind() {
	$("#bind_panle input[type='text']").val('');
	$("#bind_panle input[type='password']").val('');
	$("#bind_panle_btn").click();
}
function submitBindForm() {
	// 做一次所有的表单格式验证
	if($("#cardNo").val()=="" || isNaN($("#cardNo").val())){
		msg.alertMessage("请填写正确的卡号","alert"); 
		return;
	}else if($("#openCardName").val()==""){
		msg.alertMessage("开户姓名不能为空","alert"); 
		return;
	}else if($("#branchBankName").val()==""){
		msg.alertMessage("请填写支行信息","alert"); 
		return;
	}else if($("#moneyPwd").val()==""){
		msg.alertMessage("资金密码不能为空","alert");
		return;
	}
		
	var province = $("#province option").not(function(){ return !this.selected }).val();
	var city = $("#city option").not(function(){ return !this.selected }).val();
	$("#address").val(province + city);
	var selectBankId = $("#selectBank option").not(function(){ return !this.selected }).val();;
	$("#bankId").val(selectBankId);
//	saveUserCardForm.submit();
    $.ajax({
        type: "POST",
        url: "saveBindsCard.shtml",
        data: $('#saveUserCardForm').serialize(),
        success: function (data) {
        	if(data.success=="success"){
        		window.location="showBindCards.shtml";
        	}else if(data.success=="zjerror"){
        		msg.alertMessage("资金密码不正确","alert");
        	}else{
        		msg.alertMessage("系统错误,请联系管理员","alert");
        	}
        }
    });
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
