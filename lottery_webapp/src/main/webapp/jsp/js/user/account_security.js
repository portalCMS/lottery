//页面初始化处理
$(document).ready(function() {
	
	//修改登录密码弹出框
	$("#loginDiv").dialog({
		autoOpen : false,
		width : 426,
		buttons : [ {
			text : "修 改",
			click : function() {
				saveLoginPwd();
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

	// Link to open the dialog
	$("#loginDiv-link").click(function(event) {
		$("#loginDiv").dialog("open");
		event.preventDefault();

		var div_h = $('.ui-dialog').height();
		var scrollTop = $(window).scrollTop();
		$('.ui-dialog').css({
			'position' : 'absolute',
			'top' : scrollTop + (div_h - 25) / 2
		});
		$('.ui-dialog-buttonset button').eq(1).addClass('sub ml20');
	});

	//修改资金密码弹出框
	$("#moneyDiv").dialog({
		autoOpen : false,
		width : 426,
		buttons : [ {
			text : "保 存",
			click : function() {
				saveMoneyPwd();
			}
		}, {
			text : "返 回",
			click : function() {
				$("#errorDiv2").html("");
				$("#moneyDiv input").val("");
				$(this).dialog("close");
			}
		} ]
	});
	// Link to open the dialog
	$("#moneyDiv-link").click(function(event) {
		$("#moneyDiv").dialog("open");
		event.preventDefault();

		var div_h = $('.ui-dialog').height();
		var scrollTop = $(window).scrollTop();
		$('.ui-dialog').css({
			'position' : 'absolute',
			'top' : scrollTop + (div_h - 25) / 2
		});
		$('.ui-dialog-buttonset button').eq(3).addClass('sub ml20');
		showModifyMoneyPwd();
	});
	
	//修改安全问答的弹出框
	$("#answerDiv").dialog({
		autoOpen : false,
		width : 426,
		buttons : [ {
			text : "保 存",
			click : function() {
				saveAnswer();
			}
		}, {
			text : "返 回",
			click : function() {
				$("#errorDiv3").html("");
				$("#answerDiv input").val("");
				$(this).dialog("close");
			}
		} ]
	});
	// Link to open the dialog
	$("#answerDiv-link").click(function(event) {
		$("#answerDiv").dialog("open");
		event.preventDefault();

		var div_h = $('.ui-dialog').height();
		var scrollTop = $(window).scrollTop();
		$('.ui-dialog').css({
			'position' : 'absolute',
			'top' : scrollTop + (div_h - 25) / 2
		});
		$('.ui-dialog-buttonset button').eq(5).addClass('sub ml20');
	});
	
	$.checkFrom.initBind("#loginDiv .checkedAttr", true, true,true);
	
	$.checkFrom.initBind("#moneyDiv .checkedAttr", true, true,true);
	
	$.checkFrom.initBind("#answerDiv .checkedAttr", true, true,true);
	
	//检查密码是否两次输入不一致
	$(".confirmPwd").on("blur",function(){
		confirmPwd(this);
	});
	$("#accountSafeMenu").addClass("current");
	iplog.initIpLog(1);
});

function confirmPwd(obj){
	var newPwd = $(obj).closest(".poPupBg").find(".newPwd").val();
	var confirmPwd = $(obj).val();
	if(newPwd!=confirmPwd){
		var div = $(obj).attr("loc");
		var msg = "两次密码输入不一致！";
		$("#"+div).html(msg);
	}
}

function saveLoginPwd(){
	var attrs = new Array();
	attrs.push('customerPwd');
	attrs.push('newPwd');
	attrs.push('confirmPwd');
	attrs.push('token');
	
	var values = new Array();
	values.push($("#loginDiv .customerPwd").val());
	values.push($("#loginDiv .newPwd").val());
	values.push($("#loginDiv .confirmPwd").val());
	values.push($("#token").val());
	formPost.submitAjaxForm1("user/modifyLoginPwd.shtml",attrs,values,saveLpwdSuccess,"#loginDiv");
}

function saveLpwdSuccess(data){
	$("#token").val(data.token);
	$("#loginDiv").dialog("close");
	$("#loginDiv input").val("");
	$("body").sMsg({
		content : data.success,
		autoDisplay : true,
		actionEvent : ""
	});
}

/**
 * 显示资金密码修改
 * @returns
 */
function showModifyMoneyPwd(){
	var attrs = new Array();
	var values = new Array();
	formPost.submitAjaxForm1("user/showModifyMoneyPwd.shtml",attrs,values,showSuccess,null);
}
function showSuccess(data){
	$("#question").html(data.question);
	$("#question").next().val(data.question);
	$("#question").next().next().val(data.questionId);
}

function saveMoneyPwd(){
	var attrs = new Array();
	attrs.push('moneyPwd');
	attrs.push('newPwd');
	attrs.push('confirmPwd');
	attrs.push('question');
	attrs.push('questionId');
	attrs.push('answer');
	attrs.push('token');
	
	var values = new Array();
	values.push($("#moneyDiv .moneyPwd").val());
	values.push($("#moneyDiv .newPwd").val());
	values.push($("#moneyDiv .confirmPwd").val());
	values.push($("#moneyDiv .question").val());
	values.push($("#moneyDiv .questionId").val());
	values.push($("#moneyDiv .answer").val());
	values.push($("#token").val());
	formPost.submitAjaxForm1("user/modifyMoneyPwd.shtml",attrs,values,saveMpwdSuccess,"#moneyDiv");
}
function saveMpwdSuccess(data){
	$("#token").val(data.token);
	$("#moneyDiv").dialog("close");
	$("#moneyDiv input").val("");
	$("#errorDiv2").html("");
	$("body").sMsg({
		content : data.success,
		autoDisplay : true,
		actionEvent : ""
	});
}

function saveAnswer(){
	var attrs = new Array();
	attrs.push('question');
	attrs.push('answer');
	attrs.push('token');
	
	var values = new Array();
	values.push($("#answerDiv .question").val());
	values.push($("#answerDiv .answer").val());
	values.push($("#token").val());
	formPost.submitAjaxForm1("user/saveQuestion.shtml",attrs,values,saveAnswerSuccess,"#answerDiv");
}

function saveAnswerSuccess(data){
	$("#token").val(data.token);
	$("#answerDiv").dialog("close");
	$("#answerDiv input").val("");
	$("#errorDiv3").html("");
	$("body").sMsg({
		content : data.success,
		autoDisplay : true,
		actionEvent : ""
	});
}

var iplog = function(){
	var callBacks = {
		"initIpLog":function(data){
			var $tbody = $("#_tbody");
			$tbody.html("");
			var vos = data.page.entitylist;
			var $tr = $("._tr").clone(true);
			var tp = data.pageCount;
			var pn= data.pageNum;
			for(var i=0;i<vos.length;i++){
				var obj = vos[i];
				$tr.show();
				$tr.find("td").eq(0).text(obj.id);
				$tr.find("td").eq(1).text(obj.createTime);
				$tr.find("td").eq(2).text(obj.type);
				$tr.find("td").eq(3).text(obj.ip);
				$tbody.append($tr.clone(true));
			}
			// 单行表格加背景色
			$('#_tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#_tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			//显示分页选择显示栏
			pageBarBuid.showPageBar("iplog.initIpLog",pn,tp);
		}
	};
	return{
		initIpLog:function(pageNo){
			var pageNum = 1;
			var attrs = new Array();
			var vals = new Array();
			attrs.push("pageNum");
			if(pageNo!=undefined&&pageNo!=null){
				pageNum = pageNo;
			}
			vals.push(pageNum);
			formPost.submitAjaxForm1("user/getIpLogs.shtml",attrs,vals,callBacks["initIpLog"],null);
		}
	};
}();