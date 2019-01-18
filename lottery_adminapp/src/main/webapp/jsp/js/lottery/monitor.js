$(document).ready(function() {
	$("._stopDiv a").on('click',function(){
		monitor.confirmOrder(this,"S");
	});
	
	$("._handAward").click(function() {
		monitor.confirmOrder(this,"H");
	});
	
	$("._reAward").click(function() {
		monitor.reAward(this);
	});
	
	$("._back").click(function() {
		monitor.back(this);
	});
	
	
	$("._cancelOrder").click(function() {
		monitor.confirmOrder(this,"C");
	});
	
	$("._back").on('click',function(){
		$("#back_order_modal").modal("hide");
	});
	
	$("._btn-query").on('click',function(){
		monitor.queryOneRecord(1);
	});
	
	$("._refreshPage").on('click',function(){
		formPost.submitForm("sourceMonitor.do",null,null)
	});
	
	monitor.queryFailedTask(1);
});

var monitor = function(){
	var queryType="all";
	return{
		queryOneRecord:function(pageNum){
			if($("#openIssueNo").val()==""||$("#openIssueNo").val()==undefined){
				formPost.showErrorMessage("查询具体某一期开奖结果，必须输入开奖期号!");
				$("#openIssueNo").focus();
				return;
			}
			var attrs = new Array();
			attrs.push("pageNum");
			attrs.push("lotteryCode");
			attrs.push("issue");
			var vals = new Array();
			vals.push(pageNum);
			vals.push($("#lotteryType").val());
			vals.push($("#openIssueNo").val());
			queryType = "one";
			formPost.submitAjaxForm("lottery/queryOneRecord.do",attrs,vals,monitor.refreshTbody);
		},
		queryFailedTask:function(pageNum){
			var attrs = new Array();
			attrs.push("pageNum");
			var vals = new Array();
			vals.push(pageNum);
			
			formPost.submitAjaxForm("lottery/queryFailedTask.do",attrs,vals,monitor.refreshTbody);
		},
		refreshTbody:function(data){
			$("#myTbody").html("");
			monitor.buildPageBar(data);
			
			var $hidTr = $("._hidTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<data.page.entitylist.length;i++){
				var record = data.page.entitylist[i];
				var $tds = $hidTr.find("td");
				$tds.eq(0).html(i+1);
				$tds.eq(1).html(record.createTime);
				$tds.eq(2).html(record.lotteryName);
				$tds.eq(3).html(record.issue);
				if(record.status==10007){
					$tds.eq(4).find("label").attr('class','label label-default');
					$tds.eq(4).find("label").html("正在自动开奖");
					$tds.eq(5).find("._failedDiv").hide();
					$tds.eq(5).find("._succDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
					$tds.eq(5).find("._stopDiv").show();
				}else if(record.status==10006){
					$tds.eq(4).find("label").attr('class','label label-danger');
					$tds.eq(4).find("label").html("自动开奖失败");
					$tds.eq(5).find("._failedDiv").show();
					$tds.eq(5).find("._succDiv").hide();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
				}else if(record.status==10013){
					$tds.eq(4).find("label").attr('class','label label-success');
					$tds.eq(4).find("label").html("手工开奖成功");
					$tds.eq(5).find("._failedDiv").hide();
					$tds.eq(5).find("._succDiv").show();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
					$tds.eq(5).find("span:first").html(record.lotteryNumber);
					$tds.eq(5).find("span:last").html(" [ "+record.openTime+" ] 录入  ");
				}else if(record.status==10005){
					$tds.eq(4).find("label").attr('class','label label-success');
					$tds.eq(4).find("label").html("自动开奖成功");
					$tds.eq(5).find("._failedDiv").hide();
					$tds.eq(5).find("._succDiv").show();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
					$tds.eq(5).find("span:first").html(record.lotteryNumber);
					$tds.eq(5).find("span:last").html(" [ "+record.openTime+" ] 录入  ");
				}else if(record.status==10012){
					$tds.eq(4).find("label").attr('class','label label-danger');
					$tds.eq(4).find("label").html("手动开奖失败");
					$tds.eq(5).find("._failedDiv").show();
					$tds.eq(5).find("._succDiv").hide();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
				}else if(record.status==10010){
					$tds.eq(4).find("label").attr('class','label label-default');
					$tds.eq(4).find("label").html("手动停止开奖");
					$tds.eq(5).find("._failedDiv").show();
					$tds.eq(5).find("._succDiv").hide();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
				}else if(record.status==10011){
					$tds.eq(4).find("label").attr('class','label label-default');
					$tds.eq(4).find("label").html("正在手动开奖");
					$tds.eq(5).find("._failedDiv").show();
					$tds.eq(5).find("._succDiv").hide();
					$tds.eq(5).find("._stopDiv").hide();
					$tds.eq(5).find("._reAwardDiv").hide();
				}
				$hidTr.find("._recordId").val(record.id);
				$hidTr.find("._lotCode").val(record.lotteryCode);
				$hidTr.find("._lotGroup").val(record.lotteryGroup);
				$("#myTbody").append($hidTr.clone(true));
			}
			
			// 单行表格加背景色
			$('#myTbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#myTbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			
		},
		buildPageBar:function(data){
			$("._tc span").html(data.page.totalCount);
			var tp = data.page.pageCount;
			var pn= data.page.pageNum;
			$("._pn").text(pn);
			$("._tp").text(tp);
			var pre = 1;
			if(pn>1){
				pre=pn-1;
			}
			$("._prePage").attr('href','javascript:monitor.queryFailedTask('+pre+');');
			
			var next = 1;
			if(pn>=1&&pn<tp){
				next = pn+1;
			}else if(pn>=tp){
				next = tp;
			}
			$("._nextPage").attr('href','javascript:monitor.queryFailedTask('+next+');');
		},
		stopAutoTask:function(obj){
			var recordId = $(obj).closest("td").find("._recordId").val();
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(recordId);
			
			formPost.submitAjaxForm("lottery/stopAutoTask.do",attrs,vals,monitor.refreshTask);
			
			$("#back_order_modal").modal("hide");
		},
		refreshTask:function(data){
			Messenger().post({
				message :"操作成功！",
				type : 'info',
				showCloseButton : true
			});
			//刷新当前页
			if(queryType=="one"){
				monitor.queryOneRecord(1);
			}else{
				monitor.queryFailedTask($("._pn").text());
			}
			
		},
		confirmOrder:function(obj,t){
			var lotName = $(obj).closest("tr").find("._lotteryName").html();
			var issue = $(obj).closest("tr").find("._issue").html();
			//删除所有绑定事件。
			$("#back_order_modal ._confirm").unbind();
			if(t=="H"){
				$("#back_order_modal").find(".modal-title").html("手动开奖确认");
				$("#back_order_modal").find("._info").html("手动开奖");
				$("#back_order_modal ._confirm").on('click',function(){
					monitor.handAward(obj);
				});
			}else if(t=="C"){
				$("#back_order_modal").find(".modal-title").html("撤单确认");
				$("#back_order_modal").find("._info").html("撤单");
				$("#back_order_modal ._confirm").on('click',function(){
					monitor.cancelAward(obj);
				});
			}else{
				$("#back_order_modal").find(".modal-title").html("停止开奖确认");
				$("#back_order_modal").find("._info").html("停止开奖");
				$("#back_order_modal ._confirm").on('click',function(){
					monitor.stopAutoTask(obj);
				});
			}
			$("#back_order_modal").find("._lotName").html(lotName);
			$("#back_order_modal").find("._lotIssue").html(issue);
			$("#back_order_modal").modal("show");
		},
		handAward:function(obj){
			$("#back_order_modal").modal("hide");
			var lotteryNumber = $(obj).closest("div").find("input[type='text']").val();
			var recordId = $(obj).closest("td").find("._recordId").val();
			var lotCode = $(obj).closest("td").find("._lotCode").val();
			var lotGroup = $(obj).closest("td").find("._lotGroup").val();
			if(!monitor.checkLotNum(lotteryNumber, lotCode,lotGroup)){
				return;
			}
			var attrs = new Array();
			attrs.push("id");
			attrs.push("lotteryNumber");
			var vals = new Array();
			vals.push(recordId);
			vals.push(lotteryNumber);
			
			formPost.submitAjaxForm("lottery/handAward.do",attrs,vals,monitor.refreshTask);
		},
		checkLotNum:function(lotteryNumber,lotCode,lotGroup){
			var numArr = lotteryNumber.split(",");
			if(lotteryNumber==""||lotteryNumber==undefined||(numArr.length!=5&&numArr.length!=3)){
				Messenger().post({
					message :"开奖号码必须输入5个或3个号码！",
					type : 'error',
					showCloseButton : true
				});
				return false;
			}else{
				var maxNum =0;
				var numLength=0;
				if(lotGroup=="SYXW"){
					 var nary=numArr.sort();
					 for(var i=0;i<numArr.length;i++){
						 if (nary[i]==nary[i+1]){
							 Messenger().post({
									message :"开奖号码不能有重复号码！",
									type : 'error',
									showCloseButton : true
								});
								return false;
						 }
					 }
					numLength=2;
					maxNum = 11;
				}else if(lotGroup=="SSC"||lotGroup=="3D"){
					numLength=1;
					maxNum = 9;
				}
				for(var i=0;i<numArr.length;i++){
					var num = numArr[i];
					if(num.length!=numLength||parseInt(num)>maxNum){
						var message = "开奖号码格式错误,号码位数必须是"+numLength+"位，号码最大值不能大于"+maxNum+"!";
						Messenger().post({
							message : message,
							type : 'error',
							showCloseButton : true
						});
						return false;
					}
					
					if(lotGroup=="SYXW"&&parseInt(num)<1){
						Messenger().post({
							message : "开奖号码必须大于1！",
							type : 'error',
							showCloseButton : true
						});
						return false;
					}else if((lotGroup=="SSC"||lotGroup=="3D")&&parseInt(num)<0){
						Messenger().post({
							message : "开奖号码必须大于等于0！",
							type : 'error',
							showCloseButton : true
						});
						return false;
					}
				}
			}
			return true;
		},
		cancelAward:function(obj){
			var recordId = $(obj).closest("td").find("._recordId").val();
			var lotCode = $(obj).closest("td").find("._lotCode").val();
			var issue = $(obj).closest("tr").find("._issue").html();
			var attrs = new Array();
			attrs.push("id");
			attrs.push("lotteryCode");
			attrs.push("issue");
			var vals = new Array();
			vals.push(recordId);
			vals.push(lotCode);
			vals.push(issue);
			formPost.submitAjaxForm("lottery/cancelAward.do",attrs,vals,monitor.refreshTask);
			
			$("#back_order_modal").modal("hide");
		},
		reAward:function(obj){
			$(obj).closest("td").find("._failedDiv").hide();
			$(obj).closest("td").find("._succDiv").hide();
			$(obj).closest("td").find("._stopDiv").hide();
			$(obj).closest("td").find("._reAwardDiv").show();
		},
		back:function(obj){
			$(obj).closest("td").find("._failedDiv").hide();
			$(obj).closest("td").find("._succDiv").show();
			$(obj).closest("td").find("._stopDiv").hide();
			$(obj).closest("td").find("._reAwardDiv").hide();
		}
	};
}();