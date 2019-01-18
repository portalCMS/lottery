var bindCard = function(){
	return{
		checkCard : function(){
				var cashAmount = 0;
				var maxmoney = 0;
				cashAmount = $("#cashAmount").val();
				maxmoney = $(".amount").text();
				//maxmoney = maxmoney.replace(/(^\s*)|(\s*$)/g, "");
				//cashAmount =cashAmount.replace(/(^\s*)|(\s*$)/g, "");
				if(cashAmount=="" || isNaN(cashAmount)){
					msg.alertMessage("提款金额不正确","alert");
		    		return;
				}
				var adminPwd=$("#adminPwd").val();
				if($("#adminPwd").val()==""){
					msg.alertMessage("亲,资金密码不能为空!","alert");
		    		return;
				}
				var referenceId = $("#referenceId").val();
			    $.ajax({
			        type: "POST",
			        url: "saveDrawing.shtml",
			        data: {cashAmount:cashAmount,adminPwd:adminPwd,referenceId:referenceId},
			        success: function (data) {
			        	msg.alertMessage(data.msg,"alert");
			        }
			    });
		} 
	};
}();