$(document).ready(
		function() {
			$("#pcode").attr("src", "../../picode/ycode.do?key=updateUserkey");
			$("#pcode").attr("title", "点击刷新");
			$("#pcode").bind(
					"click",
					function() {
						$("#pcode").attr(
								"src",
								"../../picode/ycode.do?key=updateUserkey&&random="
										+ Math.random());
					});
		});

function getBankAndCard() {
	var userId = $("#userId").val();
	var params = "id=" + userId;
	var bankId = $("#banks").val();
	if(bankId!=null&&bankId!=""){
		params += "&bankId=" + bankId;
	}
	jQuery(function() {
		$.ajax({
			type : 'post',
			url : '../findBankCards.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if (data.errorMsg!=null && data.errorMsg != "") {
					Messenger().post({
						message : decodeURI(data.errorMsg),
						type : 'error',
						showCloseButton : true
					});
					return false;
				}
				var bankhtml = "";
				var banks = data.banks;
				for (var i = 0; i < banks.length; i++) {
					bankhtml += "<option value='" + banks[i].id + "'>"
							+ banks[i].name + "</option>";
				}
				$("#banks").html(bankhtml);
				var cardshtml = "";
				var bankCards = data.bankCards;
				for (var i = 0; i<bankCards.length; i++) {
					cardshtml += "<option value='" + bankCards[i].id + "'>"
							+ bankCards[i].cardNo + "</option>";
				}
				if(cardshtml=="")cardshtml += "<option value='0'>该银行未添加银行卡</option>";
				$("#bankCards").html(cardshtml);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.info(XMLHttpRequest);
				console.info(textStatus);
			}
		});
	});
}

function getBankCard() {
	var userId = $("#userId").val();
	var params = "id=" + userId;
	var bankId = $("#banks").val();
	if(bankId!=null&&bankId!=""){
		params += "&bankId=" + bankId;
	}
	jQuery(function() {
		$.ajax({
			type : 'post',
			url : '../findBankCards.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if (data.errorMsg!=null &&data.errorMsg != "") {
					Messenger().post({
						message : decodeURI(data.errorMsg),
						type : 'error',
						showCloseButton : true
					});
					return false;
				}
				var cardshtml = "";
				var bankCards = data.bankCards;
				for (var i = 0; i<bankCards.length; i++) {
					cardshtml += "<option value='" + bankCards[i].id + "'>"
							+ bankCards[i].cardNo + "</option>";
				}
				if(cardshtml=="")cardshtml += "<option value='0'>该银行未添加银行卡</option>";
				$("#bankCards").html(cardshtml);
			},
			error : function() {
			}
		});
	});
}


function addBindCard(){
	var cardId = $("#bankCards").val();
	if(cardId == 0){
		alert("请选择银行卡");
		return;
	}
	if(confirm("请问是否确定添加该银行卡?")){
		bindCardForm.submit();
	}
}
var bindCardIds = new Array();
var cardIds = new Array();
function checkSubmit(){
	var bindcards = "";
	for(var i=0;i<bindCardIds.length;i++){
		if(bindcards == ""){
			bindcards=$("input[name='cardstatus"+bindCardIds[i]+"']:checked").val();
		}else{
			bindcards+=":"+$("input[name='cardstatus"+bindCardIds[i]+"']:checked").val();
		}
	}
	$("#userbindcards").val(bindcards);
	var cards = "";
	for(var i=0;i<cardIds.length;i++){
		if(cards == ""){
			cards=$("input[name='userCard"+cardIds[i]+"']:checked").val();
		}else{
			cards+=":"+$("input[name='userCard"+cardIds[i]+"']:checked").val();
		}
	}
	$("#usercards").val(cards);
	var quotas = "";
	$("input[name='quota']").each(function(){
		if(quotas==""){
			quotas=$(this).attr("id").split(",")[1]+","+$(this).val();
		}else{
			quotas+=":"+$(this).attr("id").split(",")[1]+","+$(this).val();
		}
	});
	$("#customerStatus").val($("input[name='customerStatus1']:checked").val());
	$("#quotas").val(quotas);
	var rebates=$("#rebatesStr").val();
	if(rebates == ""){
		alert("用户返点不能为空");
		return;
	}
	if(rebates.indexOf("%")==-1){
		alert("用户返点格式错误(例如:12.5%)");
		return;
	}
	var strnum=rebates.split("%")[0];
	if(isNaN(strnum)){
		alert("用户返点格式必须是数字");
		return;
	}
	if((accDiv(strnum,100)) > 0.13){
		alert("用户返点格式不能大于0.13");
		return;
	}
	$("#rebates").val(strnum/100);
	
	var adminPwd = $("#adminPwd").val();
	if(adminPwd==''){
		alert("管理密码不能为空");
		return;
	}
	var code = $("#code").val();
	if(code==''){
		alert("验证码不能为空");
		return;
	}
	//添加最低返点数不能低于下级返点数验证	by joseph
	
	var userId = $("#userId").val();
	var params = "id=" + userId;
	//alert("params : "+params);
	jQuery(function() {
		$.ajax({
			type : 'post',
			url : '../findMaxChildRebates.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if (data.errorMsg!=null && data.errorMsg != "") {
					Messenger().post({
						message : decodeURI(data.errorMsg),
						type : 'error',
						showCloseButton : true
					});
					return false;
				}
				var jsResultMaxRebates = data.resultMaxRebates;
				/*alert("aa: "+jsResultMaxRebates);
				alert("bb: "+accDiv(strnum,100));
				alert("cc: "+((accDiv(strnum,100)) <= jsResultMaxRebates));*/
				if((accDiv(strnum,100)) <= jsResultMaxRebates){
					alert("总代返点率不能小于其下级的返点率,请确认！");
					return false;
				}else{
					updateUserInfoForm.submit();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.info(XMLHttpRequest);
				console.info(textStatus);
			}
		});
	});
	
	//updateUserInfoForm.submit();
}

// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}