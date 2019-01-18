//页面初始化处理
$(document).ready(function() {
	//$.checkFrom.initBind(".checkedAttr", true, true);
	$(".card").mouseover(function() {
		$(this).css('cursor','pointer');
	});
	$(".card").on('click',function() {
		$(".card").attr('class','card');
		$(this).attr('class','current card');
		$(".card").find("p").removeClass("color_red");
		$(this).addClass("color_red");
		//选中银行卡改变对应的隐藏域值。
		var cardId = $(".current .cardId").get(0);
		$("#id").val($(cardId).val());
		var bankName = $(".current .bankName").get(0);
		$("#branchBankName").val($(bankName).val());
		var cardNo = $(".current .cardNo").get(0);
		$("#cardNo").val($(cardNo).val());
	});
	//页面加载时，默认选中第一张银行卡
	var firstCard = $(".card").get(0);
	$(firstCard).click();
	$(".amount").formatCurrency();
	$("#rechargeMenu").addClass("current");
	
	$("._checkOnLine").mouseover(function() {
		$(this).css('cursor','pointer');
	});
	$("._checkOnLine").on("click",function(){
		$("._checkOnLine").removeClass("current");
		$(this).addClass("current");
	});
	$("._onlineBtn").on("click",function(){
		recharge.submitOnLineForm();
	});
	$(".img-bank").on('click',function(){
		$(".img-bank").removeClass("cur_bank");
		$(this).addClass("cur_bank");
	});
	$("._newOtherPayBtn").on('click',function(){
		recharge.submitOtherPayForm();
	});
});

function submitForm(){
	//提交前检查所有表单验证。
	var check = recharge.checkInput("cashAmount");
	if(!check){
		return;
	}
	if($("#id").val()==""||$("#cardNo").val()==""||$("#branchBankName").val()==""){
		formPost.showErrorMessage("请选择正确的银行卡");
		return;
	}
	saveUserRechargeForm.submit();
}

function getBankDescripe(){
	var bankId = $("#selectCard").find("option:selected").attr('id');
	$("#bankId").val(bankId);
	var params = "bankId="+bankId;
	$.ajax({
		type : 'post',
		url : "getBankDescripe.do",
		data : params,
		dataType : 'json',
		success : function(data) {
			var description = data.description;
			$("#helpInfo").text(description);
		},
		error : function() {
		}
	});
	
}

var recharge = function(){
	
	return{
		submitOtherPayForm:function(){
			//提交前检查所有表单验证。
			var check = recharge.checkInput("newOnlineAmount");
			if(!check){
				return;
			}
			if($("input[name='bank']:checked").val()==undefined){
				formPost.showErrorMessage("请选择一家银行");
				return null;
			}
			var attrs = new Array();
			attrs.push("onlineAmount");
			attrs.push("bankCode");
			attrs.push("group");
			var values = new Array();
			values.push($("#newOnlineAmount").val());
			values.push($("input[name='bank']:checked").val());
			values.push($("#group").val());
			formPost.submitForm("newOtherPay.html",attrs,values,true);
		},
		submitOnLineForm:function(){
			//提交前检查所有表单验证。
			var check = recharge.checkInput("onlineAmount");
			if(!check){
				return;
			}
			var attrs = new Array();
			attrs.push("onlineAmount");
			attrs.push("payCode");
			var values = new Array();
			values.push($("#onlineAmount").val());
			$("._checkOnLine").each(function(){
				if($(this).hasClass("current")){
					values.push($(this).attr("alt"));
					return;
				};
			});
			formPost.submitForm("otherPay.html",attrs,values,true);
		},
		checkInput:function(id){
			var amountVal = $("#"+id).val();
			if(amountVal==""){
				formPost.showErrorMessage("充值金额不能为空");
				return false;
			}
			if(isNaN(amountVal)){
				formPost.showErrorMessage("充值金额必须为数字");
				return false;
			}
			return true;
		}
	};
}();