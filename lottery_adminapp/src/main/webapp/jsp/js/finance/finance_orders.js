$(document).ready(function(){
	orders.init();
	$("#activity_detail_modal").modal("hide");
});
var orders = function(){
	var tempType;
	return {
		init:function(){
			$("._startTime").val(getNowTime(0, -1));
			$("._startTime").bind("click",function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});

			$("._endTime").bind("click",function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});
			
			orders.countRechargeOrder();
			orders.countDrawingOrder();
			orders.countActivityOrder();
			
			$("._queryOrder").on('click',function(){
				var $div = $("#typeUl li.active");
				orders.queryOrders($div,1);
			});
			
			$("#typeUl li").on('click',function(){
				orders.queryOrders(this,1);
			});
			$("#typeUl li.active").click();
			
			$("._prev").on('click',function(){
				orders.prev(this);
			});
			$("._next").on('click',function(){
				orders.next(this);
			});
		},
		prev:function(obj){
			var pageNum = $(obj).closest(".pager").find(".pageNum").text();
			pageNum = parseInt(pageNum);
			if(pageNum<1){
				return;
			}else{
				var $div = $("#typeUl li.active");
				orders.queryOrders($div,pageNum-1);
			}
		},
		next:function(obj){
			var pageNum = $(obj).closest(".pager").find(".pageNum").text();
			var totalPage = $(obj).closest(".pager").find(".totalPage").text();
			pageNum = parseInt(pageNum);
			totalPage = parseInt(totalPage);
			if(pageNum>=totalPage){
				return;
			}else{
				var $div = $("#typeUl li.active");
				orders.queryOrders($div,pageNum+1);
			}
		},
		queryOrders:function (obj,pageNum){
			var type=$(obj).find("._typeVal").val();
			var orderType;
			var orderDetailType;
			var action;
			var divId;
			if(type=="drawing"){
				orderType = 14002;
				orderDetailType = 18007;
				action="queryDrawingOrder.do";
				divId="drawingDiv";
			}else if(type=="recharge"){
				orderType = 14001;
				orderDetailType = 18009;
				action="queryRechargeOrder.do";
				divId="rechargeDiv";
			}else{
				orderType = 14001;
				orderDetailType = 18012;
				action="queryActivityOrder.do";
				divId="activityDiv";
			}
			
			tempType = orderDetailType;
			
			var attrs = new Array();
			attrs.push("pageNum");
			attrs.push("orderType");
			attrs.push("orderDetailType");
			attrs.push("orderTimeBegin");
			attrs.push("orderTimeEnd");
			attrs.push("customerName");
			attrs.push("orderStatus");
			attrs.push("maxCount");
			
			var orderTimeBegin = $("#"+divId).find("._startTime").val();
			var orderTimeEnd =  $("#"+divId).find("._endTime").val();
			var customerName =  $("#"+divId).find("._userName").val();
			var orderStatus = $("#"+divId).find("._orderStatus").val();
			
			var vals=new Array();
			vals.push(pageNum);
			vals.push(orderType);
			vals.push(orderDetailType);
			vals.push(orderTimeBegin);
			vals.push(orderTimeEnd);
			vals.push(customerName);
			vals.push(orderStatus);
			vals.push(10);
			
			formPost.submitAjaxForm(action,attrs,vals,this.querySuccess);
		},

		querySuccess : function(data){
			if(data.errorMsg){
				formPost.showErrorMessage(data.errorMsg);
				return;
			}
			var userOrderMaps = data.userOrderMaps;
			var pageCount = data.pageCount;
			var pageNum = data.pageNum;
			var odt = tempType ;
			if(odt==18009){
				orders.showRechargeList(userOrderMaps.entitylist);
				$("#rechargePage").find(".pageNum").text(pageNum);
				$("#rechargePage").find(".totalPage").text(pageCount);
			}else if(odt==18007){
				orders.showDrawingList(userOrderMaps.entitylist);
				$("#drawingPage").find(".pageNum").text(pageNum);
				$("#drawingPage").find(".totalPage").text(pageCount);
			}else{
				orders.showActivityList(userOrderMaps.entitylist);
				$("#activityPage").find(".pageNum").text(pageNum);
				$("#activityPage").find(".totalPage").text(pageCount);
			}
			orders.countRechargeOrder();
			orders.countDrawingOrder();
			orders.countActivityOrder();
		},
		/**
		 * 显示用户充值记录
		 * @param entitylist
		 * 
		 */
		showRechargeList:function (entitylist){
			$("#rechargeTb").empty();
			var $hidTr = $("._hidRcTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<entitylist.length;i++){
				var order = entitylist[i];
				$hidTr.find("._index").html(i+1);
				$hidTr.find("._time").html(order.orderTime);
				$hidTr.find("._bankName").html(order.card.bankName);
				$hidTr.find("._openName").html(order.card.opencardName);
				$hidTr.find("._carNo").html(order.card.cardNo);
				$hidTr.find("._rechargAmount").html(order.receiveAmount);
				$hidTr.find("._ps").html(order.remark);
				$hidTr.find("._rcUser").html(order.user.customerName);
				
				$hidTr.find("._approve").hide();
				$hidTr.find("._reject").hide();
				if(order.orderStatus=='17001'){
					$hidTr.find("._status").html("<font color='deepskyblue'>处理中</font>");
					$hidTr.find("._approve").show();
					$hidTr.find("._reject").show();
				}else if(order.orderStatus=='17002'){
					$hidTr.find("._status").html("<font color='green'>成功</font>");
				}else if(order.orderStatus=='17003'){
					$hidTr.find("._status").html("<font color='red'>失败</font>");
				}else if(order.orderStatus=='17004'){
					$hidTr.find("._status").html("<font color='gray'>过期</font>");
				}else{
					$hidTr.find("._status").html("<font color='red'>未定义状态</font>");
				}
				
				$hidTr.find("._ordId").val(order.id);
				$("#rechargeTb").append($hidTr.clone(true));
			}
			$("._approve").unbind();
			$("._approve").on('click',function(){
				if(!confirm("亲,是否确认通过审核?")){
					return;
				}
				var orderId = $(this).next().val();
				orders.approveOrder(orderId);
			});
			$("._reject").unbind();
			$("._reject").on('click',function(){
				if(!confirm("亲,是否确认驳回审核?")){
					return;
				}
				var orderId = $(this).prev().val();
				orders.rejectOrder(orderId);
			});
			$("#rechargeTb").find("._query").on('click',function(){
				var orderId = $(this).closest("td").find("._ordId").val();
				var action = "showRechargeOrderInfo.do";
				var attrs = new Array();
				attrs.push("id");
				var vals = new Array();
				vals.push(orderId);
				formPost.submitForm(action,attrs,vals);
			});
			
		},
		/**
		 * 显示活动领取记录
		 * @param entitylist
		 * 
		 */
		showActivityList:function (entitylist){
			$("#activityTb").empty();
			var $hidTr = $("._hidAcTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<entitylist.length;i++){
				var order = entitylist[i];
				$hidTr.find("._actId").val(order.sourceId);
				$hidTr.find("._ordId").val(order.id);
				$hidTr.find("._index").html(i+1);
				$hidTr.find("._time").html(order.orderTime);
				$hidTr.find("._type").html(order.rsvst3);
				$hidTr.find("._title").html(order.remark);
				$hidTr.find("._awardAmount").html(order.receiveAmount);
				$hidTr.find("._awaUser").html(order.user.customerName);
				
				$hidTr.find("._actApprove").hide();
				$hidTr.find("._actReject").hide();
				
				if(order.orderStatus=='17001'){
					$hidTr.find("._status").html("<font color='deepskyblue'>处理中</font>");
					$hidTr.find("._actApprove").show();
					$hidTr.find("._actReject").show();
				}else if(order.orderStatus=='17002'){
					$hidTr.find("._status").html("<font color='green'>成功</font>");
				}else if(order.orderStatus=='17003'){
					$hidTr.find("._status").html("<font color='red'>失败</font>");
				}else if(order.orderStatus=='17004'){
					$hidTr.find("._status").html("<font color='gray'>过期</font>");
				}else{
					$hidTr.find("._status").html("<font color='red'>未定义状态</font>");
				}
				$("#activityTb").append($hidTr.clone(true));
			}
			$("#activityTb").find("._actApprove").on('click',function(){
				if(!confirm("亲,是否确认通过审核?")){
					return;
				}
				var orderId = $(this).closest("td").find("._ordId").val();
				orders.approveActivityOrder(orderId);
			});
			$("#activityTb").find("._actReject").on('click',function(){
				$("#reject_activity_model").modal("show");
				
				$("#rejectConfrim").unbind();
				var $self = $(this);
				$("#rejectConfrim").on('click',function(){
					if($("#rjaReason").val().trim()==""){
						formPost.showErrorMessage("驳回申请，请填写驳回原因!");
						return;
					}
					var orderId = $self.closest("td").find("._ordId").val();
					orders.rejectActivityOrder(orderId);
					$("#reject_activity_model").modal("hide");
					$("#rjaReason").val("");
				});
				
				$("#rejectCancel").unbind();
				$("#rejectCancel").on('click',function(){
					$("#rjaReason").val("");
					$("#reject_activity_model").modal("hide");
				});
			});
			$("#activityTb").find("._actQuery").on('click',function(){
				orders.showActivityOrder(this);
			});

		},
		/**
		 * 通过审核
		 * @param orderId
		 */
		approveOrder:function (orderId){
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(orderId);
			formPost.submitAjaxForm("approveRechargeOrder.do",attrs,vals,this.JudgeSuccess);
		},
		/**
		 * 驳回申请
		 * @param orderId
		 */
		rejectOrder:function (orderId){
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(orderId);
			formPost.submitAjaxForm("rejectRechargeOrder.do",attrs,vals,this.JudgeSuccess);
		},
		JudgeSuccess:function(data){
			orders.countRechargeOrder();
			orders.countDrawingOrder();
			orders.countActivityOrder();
			var $div = $("#typeUl li.active");
			var pageNum = $("#rechargeDiv").find(".pageNum").text();
			orders.queryOrders($div,pageNum);
			
			var attrs = [];
			var vals = [];
			attrs.push("uname");
			attrs.push("ipAddress");
			
			vals.push("admin");
			vals.push(webctx);
			
			formPost.submitAjaxForm("refreshResult.do",attrs,vals);
		},
		showDrawingList:function (entitylist){
			$("#drawingTb").empty();
			var $hidTr = $("._hidDwTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<entitylist.length;i++){
				var order = entitylist[i];
				$hidTr.find("._ordId").val(order.id);
				$hidTr.find("._index").html(i+1);
				$hidTr.find("._time").html(order.orderTime);
				$hidTr.find("._drawingAmount").html(order.receiveAmount);
				$hidTr.find("._dwUser").html(order.user.customerName);
				
				$hidTr.find("._approve").hide();
				$hidTr.find("._reject").hide();
				if(order.orderStatus=='17001'){
					$hidTr.find("._status").html("<font color='deepskyblue'>处理中</font>");
				}else if(order.orderStatus=='17002'){
					$hidTr.find("._status").html("<font color='green'>成功</font>");
				}else if(order.orderStatus=='17003'){
					$hidTr.find("._status").html("<font color='red'>失败</font>");
				}else if(order.orderStatus=='17004'){
					$hidTr.find("._status").html("<font color='gray'>过期</font>");
				}else{
					$hidTr.find("._status").html("<font color='red'>未定义状态</font>");
				}
				$("#drawingTb").append($hidTr.clone(true));
			}
			$("#drawingTb").find("._query").on('click',function(){
				var orderId = $(this).closest("td").find("._ordId").val();
				var action = "showDrawingOrderInfo.do";
				var attrs = new Array();
				attrs.push("id");
				var vals = new Array();
				vals.push(orderId);
				formPost.submitForm(action,attrs,vals);
			});
		},
		countRechargeOrder:function (){
			var attrs = new Array();
			attrs.push("orderType");
			attrs.push("orderDetailType");
			
			var orderType=14001;
			var orderDetailType=18009;
			
			var vals = new Array();
			vals.push(orderType);
			vals.push(orderDetailType);
			
			formPost.submitAjaxForm("countDisposingOrder.do",attrs,vals,this.countRcSuccess);
		},
		countRcSuccess:function(data){
			$("._rechargeCount").html(data.count);
		},
		countDrawingOrder:function (){
			var orderType=14002;
			var orderDetailType=18007;
			var attrs = new Array();
			attrs.push("orderType");
			attrs.push("orderDetailType");
			
			var vals = new Array();
			vals.push(orderType);
			vals.push(orderDetailType);
			
			formPost.submitAjaxForm("countDisposingOrder.do",attrs,vals,this.countDwSuccess);
		},
		countActivityOrder:function (){
			var orderType=14001;
			var orderDetailType=18012;
			var attrs = new Array();
			attrs.push("orderType");
			attrs.push("orderDetailType");
			
			var vals = new Array();
			vals.push(orderType);
			vals.push(orderDetailType);
			
			formPost.submitAjaxForm("countDisposingOrder.do",attrs,vals,this.countAcSuccess);
		},
		countDwSuccess:function(data){
			$("._drawingCount").html(data.count);
		},
		countAcSuccess:function(data){
			$("._activityCount").html(data.count);
		},
		/**
		 * 通过活动审核
		 * @param orderId
		 */
		approveActivityOrder:function (orderId){
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(orderId);
			formPost.submitAjaxForm("approveActivityOrder.do",attrs,vals,this.JudgeSuccess);
		},
		/**
		 * 驳回活动审核
		 * @param orderId
		 */
		rejectActivityOrder:function (orderId){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("rsvst5");
			var vals = new Array();
			vals.push(orderId);
			vals.push($("#rjaReason").val());
			formPost.submitAjaxForm("rejectActivityOrder.do",attrs,vals,this.JudgeSuccess);
		},
		/**
		 * 显示活动内容
		 * @param orderId
		 */
		showActivityOrder:function (obj){
			var orderId = $(obj).closest("td").find("._actId").val();
			var tempOrderId = $(obj).closest("td").find("._ordId").val();
			var attrs = new Array();
			attrs.push("id");
			attrs.push("customerId");
			var vals = new Array();
			vals.push(orderId);
			vals.push(tempOrderId);
			formPost.submitForm("showActivityOrder.do",attrs,vals);
		}
	};
}(); 



