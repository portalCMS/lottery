//页面初始化处理
$(document).ready(function() {
	webMsg.queryWebMsg(1);
	$("#webMsgMenu").addClass("current");
	$("#backList").on('click',function(){
		$(".pageFrBox_1").hide();
		$("#listBox").fadeIn("slow");
	});
	$("#sbackList").on('click',function(){
		$(".pageFrBox_2").hide();
		$("#listBox").fadeIn("slow");
	});
	$("#sendBtn").click(function(){
		$("#listBox").hide();
		$("#sendBox").fadeIn("slow");
	});
	$("#sendMsgBtn").click(function(){
		webMsg.sendMsg();
	});
	$("#toUserName").on("blur",function(){
		webMsg.checkForm($(this).val(),"toUserName");
	});
	$("#title").on("blur",function(){
		webMsg.checkForm($(this).val(),"title");
	});
	$("#message").on("blur",function(){
		webMsg.checkForm($(this).val(),"message");
	});
	
	$("#sendUp").on('click',function(){
		if($(this).prop("checked")){
			$("#toUserName").closest(".smallInput").hide();
		}else{
			$("#toUserName").closest(".smallInput").show();
		}
	});
	$("#callback").on('click',function(){
		webMsg.backMsg($("#buname").val());
	});
});

var webMsg = function(){
	var delObj;
	var flag = false;
	var delback = function(data){
		formPost.showErrorMessage(data.success);
		$(delObj).closest("tr").remove();
		delObj = "";
		// 单行表格加背景色
		$('#_tbody tr:odd').find("td").css({
			'background-color' : '#FFF'
		});
		$('#_tbody tr:even').find("td").css({
			'background-color' : '#f6f6f6'
		});
		
	};
	var sendBack = function(data){
		formPost.showErrorMessage("发送成功");
		$("._param").each(function(){
			$(this).val("");
		});
	};
	var packageData = function(atr,val){
		$("._param").each(function(){
			atr.push($(this).attr("id"));
			val.push($(this).val());
		});
		if($("#sendUp").prop("checked")){
			val[0]="toMyUp";
		}
		
	};
	return{
		sendMsg : function(){
			if(!$("#sendUp").prop("checked")){
				this.checkForm($("#toUserName").val(), "toUserName");
			}
			
			this.checkForm($("#title").val(), "title");
			this.checkForm($("#message").val(), "message");
			var atr = new Array();
			var val = new Array();
			packageData(atr,val);
			if(flag){
				formPost.submitAjaxForm("sendMsg.shtml",atr,val,sendBack);
			}
		},
		queryWebMsg:function(pageNo){
			var pageNum = 1;
			var attrs = new Array();
			var vals = new Array();
			attrs.push("pageNum");
			attrs.push("maxCount");
			if(pageNo!=undefined&&pageNo!=null){
				pageNum = pageNo;
			}
			vals.push(pageNum);
			vals.push(10);
			formPost.submitAjaxForm("queryMsgPage.shtml",attrs,vals,this.msgCallback);
		},
		msgCallback:function(data){
			var $tbody = $("#_tbody");
			$tbody.html("");
			var vos = data.page.entitylist;
			var $tr = $("._tr").clone(true);
			var tp = data.pageCount;
			var pn= data.pageNum;
			var sIndex = (parseInt(pn)-1)*10;
			for(var i=0;i<vos.length;i++){
				var msg = vos[i];
				$tr.show();
				$tr.find("td").eq(0).text(sIndex+i+1);
				$tr.find("td").eq(1).text(msg.refUserName);
				if(msg.status==0){
					$tr.find("td").eq(2).html(msg.title+"<span class='color_red'>(未读)</span>");
				}else{
					$tr.find("td").eq(2).html(msg.title);
				}
				$tr.find("td").eq(3).text(msg.createTime);
				$tr.find("td").eq(4).find("._msgId").val(msg.id);
				$tr.find("td").eq(4).find("._msgContent").val(msg.message);
				$tr.find("td").eq(4).find("._msgTitle").val(msg.title);
				$tr.find("td").eq(4).find("._msgStatus").val(msg.status);
				$tr.find("td").eq(4).find("._suname").val(msg.refUserName);
				$tbody.append($tr.clone(true));
			}
			$("._msgInfo").unbind();
			$("._msgInfo").on('click',function(){
				webMsg.showMsgInfo(this);
			});
			$("._delete").on('click',function(){
				webMsg.delMsg(this);
			});
			// 单行表格加背景色
			$('#_tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#_tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			//显示分页选择显示栏
			pageBarBuid.showPageBar("webMsg.queryWebMsg",pn,tp);
		},
		showMsgInfo:function(obj){
			$("#listBox").hide();
			$("#infoBox").fadeIn("slow");
			var msg = $(obj).closest("td").find("._msgContent").val();
			var title = $(obj).closest("td").find("._msgTitle").val();
			$("#msgContent").find("#msgTitle").html(title);
			$("#msgContent").find("textarea").html(msg);
			$("#buname").val($(obj).closest("td").find("._suname").val());
			if($(obj).closest("td").find("._msgStatus").val()==0){
				var attrs = new Array();
				var vals = new Array();
				attrs.push("id");
				vals.push($(obj).closest("td").find("._msgId").val());
				formPost.submitAjaxForm("readMsg.shtml",attrs,vals);
				$(obj).closest("tr").find("td").eq(2).find("span").remove();
			}
		},
		delMsg:function(obj){
			if(confirm("亲,是否要删除标题为["+$(obj).closest("td").find("._msgTitle").val()+"]的站内信")){
				var attrs = new Array();
				var vals = new Array();
				attrs.push("id");
				vals.push($(obj).closest("td").find("._msgId").val());
				formPost.submitAjaxForm("delMsg.shtml",attrs,vals,delback);
				delObj = obj;
			}
		},
		checkForm : function(val,tid){
			if(tid=="toUserName"){
				if(val==""){
					$("#toUserNameError").text("收信人不能为空");
					return;
				}
			}
			$("#toUserNameError").text("");
			if(tid=="title"){
				if(val==""){
					$("#titleError").text("标题不能为空");
					return;
				}
				if(val.length>250){
					$("#titleError").text("标题不能超过250个字符");
					return;
				}
			}
			$("#titleError").text("");
			if(tid=="message"){
				if(val==""){
					$("#messageError").text("内容不能为空");
					return;
				}
			}
			$("#messageError").text("");
			flag = true;
		},
		backMsg : function(buname){
			if(buname == '系统消息'){
				formPost.showErrorMessage("系统消息不能回复");
				return;
			}
			$("#infoBox").hide();
			$("#sendBox").fadeIn("slow");
			$("#toUserName").val(buname);
		}
	};
}();