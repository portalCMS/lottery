$(document).ready(function(){
	$("#lowerManage").addClass("current");
	$("._mBtn").click(function(){
		lowerManager.submitForm();
	});
	$("._qBtn").click(function(){
		lowerManager.submitForm1();
	});
	
	$("#toUserName").blur(function(){
		lowerManager.checkUser();
	});
	$("#toQUserName").blur(function(){
		lowerManager.checkUser1();
	});
});

var lowerManager = function(){
	
	var userFlag = false;
	var userFlag1 = false;
	
	var checkForm = function(){
		var toUserName = $("#toUserName").val();
		var toMoney = $("#toMoney").val();
		var moneryPwd = $("#moneryPwd").val();
		if(toUserName == ""){
			formPost.showErrorMessage("目标用户不能为空");
			return false;
		}
		if(toMoney == ""){
			formPost.showErrorMessage("充值金额不能为空");
			return false;
		}
		if(isNaN(toMoney)){
			formPost.showErrorMessage("充值金额不是数字");
			return false;
		}
		if(moneryPwd == ""){
			formPost.showErrorMessage("资金密码不能为空");
			return false;
		}
		return true;
	};
	
	var checkForm1 = function(){
		var toQUserName = $("#toQUserName").val();
		var toQuota = $("#toQuota").val();
		var moneryQPwd = $("#moneryQPwd").val();
		if(toQUserName == ""){
			formPost.showErrorMessage("目标用户不能为空");
			return false;
		}
		if(toQuota == ""){
			formPost.showErrorMessage("调整返点不能为空");
			return false;
		}
		if(isNaN(toQuota)){
			formPost.showErrorMessage("调整返点不是数字");
			return false;
		}
		if(moneryQPwd == ""){
			formPost.showErrorMessage("资金密码不能为空");
			return false;
		}
		return true;
	};
	
	var packageData = function(atrs,vals){
		$("._param").each(function(){
			atrs.push($(this).attr("name"));
			vals.push($(this).val());
		});
	};
	
	var packageData1 = function(atrs,vals){
		$("._param1").each(function(){
			atrs.push($(this).attr("name"));
			vals.push($(this).val());
		});
	};
	
	var callBck = {
		checkUserBck : function(data){
			if(data.emsg){
				$("#error").text(data.emsg);
			}else{
				$("#error").text("");
				userFlag = true;
			}
		},
		checkUser1Bck : function(data){
			if(data.emsg){
				$("#error1").text(data.emsg);
			}else{
				$("#error1").text("");
				userFlag1 = true;
				var superuser = data.superuser.rebates;
				var sunuser = data.sunuser.rebates;
				var cstepPoint = data.cstepPoint;
				var cminPoint = data.cminPoint;
				var quotalist = data.quotalist;
				superuser = superuser * 1000;
				sunuser = sunuser * 1000;
				cminPoint = cminPoint * 10;
				cstepPoint = cstepPoint*10;
				var $toQuota = $("#toQuota");
				$toQuota.html("");
				$("#error1").text(data.sunuser.customerName+"的返点"+data.sunuser.rebates*1000/10+"%");
				if(quotalist.length>0){
					quotalist.sort(function(a,b){return a.proportion<b.proportion?1:-1;});
					for(var i = 0; i < quotalist.length; i++){
						if(quotalist[i].proportion*1000 == sunuser){
							break;
						}
						$toQuota.append($("<option>").val(quotalist[i].proportion).text(quotalist[i].proportion*1000/10+"%---"+quotalist[i].quota_count));
					}
					if(cminPoint-cstepPoint > sunuser){
						for(var i=cminPoint-cstepPoint;i>sunuser;){
							$toQuota.append($("<option>").val(i/1000).text(i/10+"%"));
							i=i-cstepPoint;
						}
					}
				}else{
					if(cminPoint-cstepPoint > sunuser){
						for(var i=cminPoint-cstepPoint;i>sunuser;){
							$toQuota.append($("<option>").val(i/1000).text(i/10+"%"));
							i=i-cstepPoint;
						}
					}
				}
				
			}
		},
		formBck : function(data){
			if(data.success!=""){
				formPost.showErrorMessage(data.success);
				$("._param").each(function(){
					$(this).val("");
				});
			}
		},
		form1Bck : function(data){
			if(data.success!=""){
				formPost.showErrorMessage(data.success);
				$("#error1").text("");
				$("._param1").each(function(){
					$(this).val("");
				});
				$toQuota.append($("<option>").val("").text("请输入目标用户"));
			}
		}
	};
	return{
		submitForm : function(){
			var flag = checkForm();
			if(flag&&userFlag){
				var atrs = new Array();
				var vals = new Array();
				packageData(atrs,vals);
				formPost.submitAjaxForm("team/getlowerm.shtml",atrs,vals,callBck["formBck"]);
			}
		},
		submitForm1 : function(){
			var flag = checkForm1();
			if(flag&&userFlag1){
				var atrs = new Array();
				var vals = new Array();
				packageData1(atrs,vals);
				formPost.submitAjaxForm("team/changelowerq.shtml",atrs,vals,callBck["form1Bck"]);
			}
		},
		checkUser : function(){
			if($("#toUserName").val()==""){
				$("#error").text("目标用户不能为空");
				return;
			}else{
				$("#error").text("");
			}
			var atrs = new Array();
			var vals = new Array();
			atrs.push("userName");
			vals.push($("#toUserName").val());
			formPost.submitAjaxForm("team/checklowern.shtml",atrs,vals,callBck["checkUserBck"]);
		},
		checkUser1 : function(){
			if($("#toQUserName").val()==""){
				$("#error1").text("目标用户不能为空");
				return;
			}else{
				$("#error1").text("");
			}
			var atrs = new Array();
			var vals = new Array();
			atrs.push("userName");
			vals.push($("#toQUserName").val());
			formPost.submitAjaxForm("team/checklowern.shtml",atrs,vals,callBck["checkUser1Bck"]);
		}
	};
}();