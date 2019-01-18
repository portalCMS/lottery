$(document).ready(function() {
	$("._tops li").removeClass("active");
	$("#taskPage").addClass("active");
	$("#createIssuePanle").hide();
	$("._updatePanel").hide();
	$("._logPanel").hide();
	
	$("._cancel_log").on('click',function(){
		$("._logPanel").hide(200);
		$("._taskTable").show();
	});
	
	$("._cancel_update").on('click',function(){
		$("._updatePanel").hide(200);
		$("._updatePanel").find("input").val("");
		$("._taskTable").show();
	});
	
	$("#all_delete").click(function() {
		$("#del_all_model").modal("show");
	});
	$("#_create_issue_btn").click(function() {
		$("#createIssueDesc").toggle(200);
		$("#createIssuePanle").toggle(200);
	});
	$("._cancel").on('click',function(){
		$("#createIssuePanle").find("input").val("");
		$("#_create_issue_btn").click();
	});
	$("._names a").on('click',function(){
		tasks.changeLotGroup(this);
	});
	
	$("._lots li").on('click',function(){
		$("._updatePanel").hide();
		tasks.changeLot(this);
		$("._taskTable").show();
	});
	
	$("._names a:first").addClass("active");
	var gn = $("._names").find("a.active").text();
	$("#groupName").text(gn);
	
	$("._lots li:first").addClass("active");
	$("._lots li:first").click();
	
	$("._upd_commit").on('click',function(){
		tasks.updateTask();
	});
	
	$("#createIssuePanle ._commit").on('click',function(){
		tasks.createTask(this);
	});
	
	$("._refreshTasks").on('click',function(){
		tasks.refreshTasks(this);
	});
	
	$("._del_all_btn").on('click',function(){
		tasks.batchDeleteTask(this);
	});
	
	$("#del_model ._del_btn").on('click',function(){
		tasks.deleteTask(this);	
	});
	
	$("#checkAll").on('click',function(){
		if($(this).prop("checked")){
			$("._cb").prop("checked",true);
		}else{
			$("._cb").prop("checked",false);
		}
		
	});
	
	$("._removeTasks").on('click',function(){
		tasks.batchRemoveTask();
	});
});

var tasks = function(){
	var $task;
	return{
		refreshTasks:function(obj){
			var attrs = new Array();
			attrs.push("lotteryCode");
			attrs.push("lotteryGroup");
			var vals = new Array();
			var lotCode = $("._lots .active").find("._lotCode").val();
			var lotGroup = $("._lots .active").find("._lotGroup").val();
			vals.push(lotCode);
			vals.push(lotGroup);
			formPost.submitAjaxForm("refreshTasks.do",attrs,vals,this.callbackRefresh);
		},
		callbackRefresh:function(data){
			if(data.errorMsg!=""&&data.errorMsg!=null){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}else{
				formPost.showInfoMessage(data.success);
				$("._lots li.active").click();
			}
		},
		changeLotGroup:function(obj){
			var attrs = new Array();
			attrs.push("lotteryGroup");
			var vals = new Array();
			vals.push($(obj).find("input").val());
			
			formPost.submitAjaxForm("changeLotGroup.do",attrs,vals,this.showLots);
			$("._names a").removeClass("active");
			$(obj).addClass("active");
			
			var gn = $("._names").find("a.active").text();
			$("#groupName").text(gn);
		},
		showLots:function(data){
			$("._lots").html("");
			var $lotLi = $("._hidLi:last").clone(true);
			$lotLi.show();
			for(var i=0;i<data.lotteryList.length;i++){
				var lot = data.lotteryList[i];
				$lotLi.find("._lotName").text(lot.lotteryName);
				if(lot.lotteryStatus==10003){
					$lotLi.find("._lotName").text(lot.lotteryName+"(暂停)");
				}
				$lotLi.find("._lotCode").val(lot.lotteryCode);
				$lotLi.find("._lotGroup").val(lot.lotteryGroup);
				$lotLi.find("._seriesRule").val(lot.seriesRule);
				$lotLi.find("._totalTimes").val(lot.totalTimes);
				
				$("._lots").append($lotLi.clone(true));
			}
			
			$("._lots li").on('click',function(){
				tasks.changeLot(this);
			});
			
			$("._lots li:first").addClass("active");
			$("._lots li:first").click();
		},
		changeLot:function(obj){
			var attrs = new Array();
			attrs.push("lotteryGroup");
			attrs.push("lotteryCode");
			attrs.push("repeatTimes");
			var vals = new Array();
			vals.push($(obj).find("._lotGroup").val());
			vals.push($(obj).find("._lotCode").val());
			vals.push($(obj).find("._totalTimes").val());
			
			formPost.submitAjaxForm("showLotTasks.do",attrs,vals,this.showTasks);
			$("._lots li").removeClass("active");
			$(obj).addClass("active");
		},
		showTasks:function(data){
			$("#tasksTbody").html("");
			var $lotTr = $("._hidTr:last").clone(true);
			$lotTr.show();
			for(var i=0;i<data.taskList.length;i++){
				var task = data.taskList[i];
				$lotTr.find("._id").val(task.id);
				$lotTr.find("._lotName").text(task.lotteryName);
				$lotTr.find("._issueNo").text(task.lotterySeries);
				$lotTr.find("._startTime").text(task.startBetTime);
				$lotTr.find("._endTime").text(task.endBetTime);
				$lotTr.find("._lotTime").text(task.lotTime);
				$lotTr.find("._catchTimes").text(task.catchTimes);
				if(task.running==true){
					$lotTr.find("._running").removeClass("label-warning");
					$lotTr.find("._running").addClass("label-success");
					$lotTr.find("._running").text("已启动");
				}else{
					$lotTr.find("._running").addClass("label-warning");
					$lotTr.find("._running").removeClass("label-success");
					$lotTr.find("._running").text("未启动");
				}
				$("#tasksTbody").append($lotTr.clone(true));
			}
			
			// 单行表格加背景色
			$('#tasksTbody  tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#tasksTbody  tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			
			$("#tasksTbody ._upd_btn").on('click',function(){
				tasks.showUpdateTask(this);
			});
			
			$("#tasksTbody ._del_btn").on('click',function(){
				var id = $(this).closest("tr").find("._id").val();
				$("#del_model ._curId").val(id);
				$("#del_model").modal("show");
			});
			$("#tasksTbody ._log_btn").on('click',function(){
				tasks.viewLog(this);
			});
		},
		showUpdateTask:function(obj){
			var $tr = $(obj).closest("tr");
			$task = $tr;
			var id = $tr.find("._id").val();
			var issueNo = $tr.find("._issueNo").text();
			var startTime = $tr.find("._startTime").text();
			var endTime = $tr.find("._endTime").text();
			var lotTime = $tr.find("._lotTime").text();
			var catchTimes = $tr.find("._catchTimes").text();
			$("._updatePanel").find("._upd_id").val(id);
			$("._updatePanel").find("._upd_issueNo").val(issueNo);
			$("._updatePanel").find("._upd_startTime").val(startTime);
			$("._updatePanel").find("._upd_endTime").val(endTime);
			$("._updatePanel").find("._upd_lotTime").val(lotTime);
			$("._updatePanel").find("._upd_catchTimes").val(catchTimes);
		
			$("._taskTable").hide();
			$("._updatePanel").show();
			$("._updatePanel").find("input[type='text']").eq(0).select();
			$("._updatePanel").find("input[type='text']").eq(0).focus();
		},
		updateTask:function(){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("startBetTime");
			attrs.push("endBetTime");
			attrs.push("lotTime");
			attrs.push("catchTimes");
			attrs.push("lotterySeries");
			var vals = new Array();
			var id = $("._updatePanel ._upd_id").val();
			var startTime = $("._updatePanel ._upd_startTime").val();
			var endTime = $("._updatePanel ._upd_endTime").val();
			var lotTime = $("._updatePanel ._upd_lotTime").val();
			var catchTimes = $("._updatePanel ._upd_catchTimes").val();
			var issueNo = $("._updatePanel ._upd_issueNo").val();
			vals.push(id);
			vals.push(startTime);
			vals.push(endTime);
			vals.push(lotTime);
			vals.push(catchTimes);
			vals.push(issueNo);
			formPost.submitAjaxForm("updateTask.do",attrs,vals,this.refreshTask); 
		},
		refreshTask:function(data){
			if(data.errorMsg!=""&&data.errorMsg!=null){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}else{
				formPost.showInfoMessage(data.success);
			}
			$("._updatePanel").hide();
			var task = data.task;
			$task.find("._issueNo").text(task.lotterySeries);
			$task.find("._startTime").text(task.startBetTime);
			$task.find("._endTime").text(task.endBetTime);
			$task.find("._lotTime").text(task.lotTime);
			$task.find("._catchTimes").text(task.catchTimes);
			if(task.running==true){
				$task.find("._running").removeClass("label-warning");
				$task.find("._running").addClass("label-success");
				$task.find("._running").text("已启动");
			}else{
				$task.find("._running").addClass("label-warning");
				$task.find("._running").removeClass("label-success");
				$task.find("._running").text("未启动");
			}
			$("._taskTable").show();
		},
		createTask:function(obj){
			var attrs = new Array();
			attrs.push("lotteryCode");
			attrs.push("lotteryGroup");
			attrs.push("lotteryName");
			attrs.push("spanTime");
			attrs.push("startBetTime");
			attrs.push("endBetTime");
			attrs.push("lotTime");
			attrs.push("catchTimes");
			attrs.push("repeatTimes");
			attrs.push("seriesRule");
			attrs.push("newIssueType");
			var vals = new Array();
			var lotteryCode = $("._lots .active").find("._lotCode").val();
			var lotteryGroup = $("._lots .active").find("._lotGroup").val();
			var lotteryName = $("._lots .active").find("._lotName").text();
			var seriesRule = $("._lots .active").find("._seriesRule").val();
			var spanTime = $("#createIssuePanle ._spanTime").val();
			var startTime = $("#createIssuePanle  ._startTime").val();
			var endTime = $("#createIssuePanle  ._endTime").val();
			var lotTime = $("#createIssuePanle  ._lotTime").val();
			var catchTimes = $("#createIssuePanle  ._catchTimes").val();
			var repeatTimes = $("#createIssuePanle  ._totalTimes").val();
			var newIssueType = $("#periodType").val();
			vals.push(lotteryCode);
			vals.push(lotteryGroup);
			vals.push(lotteryName);
			vals.push(spanTime);
			vals.push(startTime);
			vals.push(endTime);
			vals.push(lotTime);
			vals.push(catchTimes);
			vals.push(repeatTimes);
			vals.push(seriesRule);
			vals.push(newIssueType);
			formPost.submitAjaxForm("createTask.do",attrs,vals,this.refreshLot); 
		},
		refreshLot:function(data){
			if(data.errorMsg!=""&&data.errorMsg!=null){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}else{
				formPost.showInfoMessage(data.success);
			}
			$("#createIssuePanle").hide();
			$("#createIssueDesc").show();
			$("._lots .active").click();
		},
		viewLog:function (obj){
			var attrs = new Array();
			attrs.push("id");
			//标识日志类型是J，还是S.J标识奖期生成任务的日志，S标识奖期任务的日志。
			attrs.push("lotteryJobName");
			var vals = new Array();
			vals.push($(obj).closest("tr").find("._id").val());
			vals.push("S");
			formPost.submitAjaxForm("viewJobLog.do",attrs,vals,this.showLogList); 
		},
		showLogList : function (data){
			$("#logListTbody").html("");
			var html = "";
			for(var i=0;i<data.logList.length;i++){
				var log = data.logList[i];
				html += "<tr class='text-center'>";
				html += "<td>"+log.taskName+"</td>";
				html += "<td>"+log.processStartTime+"</td>";
				html += "<td>"+log.processEndTime+"</td>";
				if(log.runStatus==10006){
					html += "<td><font color='red'>失败</font></td>";
					html += "<td><font color='red'>"+log.errorMessage+"</font></td>";
				}else if(log.runStatus==10005){
					html += "<td><font color='green'>成功</font></td>";
					html += "<td></td>";
				}else {
					html += "<td><font color='red'>未定义状态</font></td>";
				}
				
				html += "</tr>";
			}
			$("#logListTbody").html(html);
			$("._taskTable").hide();
			$("._logPanel").show(200);
		},
		deleteTask:function(obj){
			$("#del_model").modal("hide");
			var attrs = new Array();
			attrs.push("idList");
			var vals = new Array();
			vals.push($(obj).find("input").val());
			formPost.submitAjaxForm("batchDeleteTask.do",attrs,vals,this.refreshLot); 
		},
		batchDeleteTask:function(obj){
			$("#del_all_model").modal("hide");
			var attrs = new Array();
			attrs.push("idList");
			var vals = new Array();
			var idList ="";
			$("#tasksTbody ._cb").each(function(){
				if($(this).prop("checked")){
					if(idList==""){
						idList=$(this).next().val();
					}else{
						idList +=","+$(this).next().val();
					}
				}
			});
			if(idList==""){
				formPost.showErrorMessage("请至少勾选一个奖期任务！");
				return;
			}
			vals.push(idList);
			formPost.submitAjaxForm("batchDeleteTask.do",attrs,vals,this.refreshLot); 
		},
		batchRemoveTask:function(obj){
			if(!confirm("是否确认停止所选奖期任务？")){
				return;
			}
			var attrs = new Array();
			attrs.push("idList");
			var vals = new Array();
			var idList ="";
			$("#tasksTbody ._cb").each(function(){
				if($(this).prop("checked")){
					if(idList==""){
						idList=$(this).next().val();
					}else{
						idList +=","+$(this).next().val();
					}
				}
			});
			if(idList==""){
				formPost.showErrorMessage("请至少勾选一个奖期任务！");
				return;
			}
			vals.push(idList);
			formPost.submitAjaxForm("removeTasks.do",attrs,vals,this.refreshRemoveTask); 
		},
		refreshRemoveTask:function(data){
			$("._lots li.active").click();
			if(data.errorMsg!=""&&data.errorMsg!=null){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}else{
				formPost.showInfoMessage(data.success);
			}
			return;
		}
	};
}();