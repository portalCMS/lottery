$(document).ready(function() {
	$("._tops li").removeClass("active");
	$("#jobPage").addClass("active");
	$("._logPanel").hide();
	
	$("._cancel_log").on('click',function(){
		$("._logPanel").hide(200);
	});
	
	$("._groups li").on('click',function(){
		jobs.changeLotGroup(this);
	});
	
	$("._groups li:first").click();
	
	$("._all_cb").on('click',function(){
		jobs.selectAll(this);
	});
	
	$("#sta_sel_btn").on('click',function(){
		jobs.confirmModal(this,"start");
	});
	
	$("#stop_sel_btn").on('click',function(){
		jobs.confirmModal(this,"stop");
	});
	
	$("#upd_time_sel_btn").on('click',function(){
		jobs.confirmModal(this,"update");
	});
	
	
	
});

var jobs = function(){
	var $job;
	return{
		confirmModal:function(obj,type){
			$("#del_model ._confirm_btn").unbind();
			if(type=="stop"){
				$("#del_model ._title").html("停止奖期生成任务");
				$("#del_model ._confirmInfo").html("确定停止所选奖期生成任务？");
				$("#del_model ._confirm_btn").on('click',function(){
					$("#del_model").modal("hide");	
					jobs.stopJobs(this);
				});
			}else if(type=="start"){
				$("#del_model ._title").html("启动奖期生成任务");
				$("#del_model ._confirmInfo").html("确定启动所选奖期生成任务？");
				$("#del_model ._confirm_btn").on('click',function(){
					$("#del_model").modal("hide");	
					jobs.startJobs(this);
				});
			}else if(type=="update"){
				$("#del_model ._title").html("修改奖期生成任务执行时间");
				$("#del_model ._confirmInfo").html("确定修改所选奖期生成任务的执行时间？");
				$("#del_model ._confirm_btn").on('click',function(){
					$("#del_model").modal("hide");	
					jobs.modifyJobCron(this);
				});
			}
			
			$("#del_model").modal("show");	
		},
		selectAll : function (obj){
			var checked = $(obj).prop('checked');
			$("#jobsTbody ._cb").each(
				function (){
					$(this).prop('checked',checked);
				}
			);
		},
		getIdList:function(attr){
			var list ="";
			$("#jobsTbody ._cb").each(function(){
				if($(this).prop("checked")){
					if(list==""){
						list=$(this).parent().find(attr).val();
					}else{
						list +=","+$(this).parent().find(attr).val();
					}
				}
			});
			return list;
		},
		startJobs:function(obj){
			var attrs = new Array();
			attrs.push("lotteryCodes");
			attrs.push("rsvbl");
			var vals = new Array();
			var codes = this.getIdList("._code");
			if(codes==""){
				formPost.showErrorMessage("请至少勾选一个彩种！");
				return;
			}
			vals.push(codes);
			vals.push($("#createToday").prop("checked"));
			formPost.submitAjaxForm("startJobs.do",attrs,vals,this.refreshLots);
		},
		stopJobs:function(obj){
			var attrs = new Array();
			attrs.push("lotteryGroup");
			attrs.push("lotteryCodes");
			var vals = new Array();
			var codes = this.getIdList("._code");
			if(codes==""){
				formPost.showErrorMessage("请至少勾选一个彩种！");
				return;
			}
			vals.push($("._groups .active").find("input").val());
			vals.push(codes);
			
			formPost.submitAjaxForm("stopJobs.do",attrs,vals,this.refreshLots);
		},
		modifyJobCron : function(obj){
			var idList = this.getIdList("._id");
			if(idList==""){
				formPost.showErrorMessage("请至少勾选一个彩种！");
				return;
			}
			var attrs = new Array();
			var vals = new Array();
			var i=0;
			$("#jobsTbody tr").each(function(){
				if($(this).find("._cb").prop("checked")){
					attrs.push("lotterys["+i+"].id");
					attrs.push("lotterys["+i+"].taskCornExpression");
					vals.push($(this).find("._id").val());
					vals.push($(this).find("._lotTimeCorn").val());
					i++;
				}
			});
			
			
			formPost.submitAjaxForm("modifyJobCron.do",attrs,vals,this.refreshLots);
		},
		refreshLots:function(data){
			if(data.errorMsg!=""&&data.errorMsg!=null){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}else{
				formPost.showInfoMessage(data.success);
			}
			$("._groups .active").click();
		},
		changeLotGroup:function(obj){
			$("._logPanel").hide();
			var attrs = new Array();
			attrs.push("lotteryGroup");
			var vals = new Array();
			vals.push($(obj).find("input").val());
			
			formPost.submitAjaxForm("changeLotGroup.do",attrs,vals,this.showLots);
			$("._groups li").removeClass("active");
			$("input[type='checkbox']").prop("checked",false);
			$(obj).addClass("active");
		},
		showLots:function(data){
			$("#jobsTbody").html("");
			var $lotTr = $("._hidTr:last").clone(true);
			$lotTr.show();
			for(var i=0;i<data.lotteryList.length;i++){
				var lot = data.lotteryList[i];
				$lotTr.find("._id").val(lot.id);
				$lotTr.find("._code").val(lot.lotteryCode);
				$lotTr.find("._lotName").text(lot.lotteryName);
				$lotTr.find("._startTime").text(lot.startTime);
				$lotTr.find("._endTime").text(lot.endTime);
				$lotTr.find("._lotTimeCorn").val(lot.taskCornExpression);
				$lotTr.find("._spanTime").text(lot.spanTime);
				$lotTr.find("._catchTimes").text(lot.catchTimes);
				$lotTr.find("._totalTimes").text(lot.totalTimes);
				
				var statusStr="";
				var statusClass="";
				if(lot.taskRunStatus==10001){
					statusStr = "停止";
					statusClass= "label label-warning  _status";
				}else if(lot.taskRunStatus==10004){
					statusStr = "就绪";
					statusClass= "label label-primary  _status";
				}else if(lot.taskRunStatus==10005){
					statusStr = "成功";
					statusClass= "label label-success  _status";
				}else if(lot.taskRunStatus==10006){
					statusStr = "失败";
					statusClass= "label label-danger  _status";
				}
				$lotTr.find("._status").attr('class',statusClass).text(statusStr);
				
				$lotTr.find("._befLot").text(lot.beforeLotTime);
				if(null==lot.afterLotTime||""==lot.afterLotTime){
					$lotTr.find("._aftLot").text(0);
				}else{
					$lotTr.find("._aftLot").text(lot.afterLotTime);
				}
				
				
				$("#jobsTbody").append($lotTr.clone(true));
			}
			
			// 单行表格加背景色
			$('#jobsTbody  tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#jobsTbody  tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			
			$("#jobsTbody ._log_btn").on('click',function(){
				jobs.viewLog(this);
			});
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
		},
		viewLog:function (obj){
			var attrs = new Array();
			attrs.push("id");
			//标识日志类型是J，还是S.J标识奖期生成任务的日志，S标识奖期任务的日志。
			attrs.push("lotteryJobName");
			var vals = new Array();
			vals.push($(obj).closest("tr").find("._id").val());
			vals.push("J");
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
		}
	};
}();