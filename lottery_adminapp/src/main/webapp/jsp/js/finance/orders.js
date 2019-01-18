$(document).ready(function(){
	orders.init();
});

var orders = function(){
	return {
		init:function(){
			$(".recharge_time").eq(0).val(getNowTime(0, -7));
			$(".recharge_time").eq(1).val(getNowTime(0, 0));
			$(".recharge_time").bind("click",function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});

			$(".drawing_time").eq(0).val(getNowTime(0, -7));
			$(".drawing_time").eq(1).val(getNowTime(0, 0));
			$(".drawing_time").bind("click",function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});
			orders.queryOrders('pageInfo','queryRechargeOrder.do',1);
			orders.queryOrders('pageInfo2','queryDrawingOrder.do',1);
			orders.countRechargeOrder();
			orders.countDrawingOrder();
		},
		queryOrders : function (divId,url,pageNo){
			var orderTimeBegin = $("#orderTimeBegin").val();
			var orderTimeEnd = $("#orderTimeEnd").val();
			var customerName = $("#customerName").val();
			var orderStatus = $("#orderStatus").val();
			var orderType=14001;
			var orderDetailType=18009;
			if(divId=="pageInfo2"){
				orderType=14002;
				orderDetailType=18007;
				orderTimeBegin = $("#orderTimeBegin2").val();
				orderTimeEnd = $("#orderTimeEnd2").val();
				customerName = $("#customerName2").val();
				orderStatus = $("#orderStatus2").val();
			}
			
			
			var params = "&pageNum="+pageNo+"&orderType="+orderType+"&orderDetailType="+orderDetailType;
			if(orderTimeBegin!=""){
				params += "&orderTimeBegin="+orderTimeBegin;
			}
			if(orderTimeEnd!=""){
				params += "&orderTimeEnd="+orderTimeEnd;
			}
			if(customerName!=""){
				params += "&customerName="+customerName;
			}
			if(orderStatus!=""){
				params += "&orderStatus="+orderStatus;
			}
			$.ajax({
				type : 'post',
				url : contextPath+"/"+url,
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.errorMsg){
						Messenger().post({
							message :decodeURI(data.errorMsg),
							type : 'error',
							showCloseButton : true
						});
						return;
					}
					var userOrderMaps = data.userOrderMaps;
					if(divId=="pageInfo"){
						orders.showRechargeList(userOrderMaps.entitylist);
						orders.showPageInfo(divId,data.pageNum,data.maxCount,data.pageCount);
					}else{
						orders.showDrawingList(userOrderMaps.entitylist);	
						orders.showPageInfo2(divId,data.pageNum,data.maxCount,data.pageCount);
					}
					
					
				},
				error : function(error) {
				}
			});
		},

		showPageInfo : function (divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<li><a href='javaScript:orders.queryRechargeOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
						+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:orders.queryRechargeOrders("+(pageNum+1)+")'>下一页</a></li>";
			$("#"+divId).html(html);
		},


		showPageInfo2 : function (divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<li><a href='javaScript:orders.queryDrawingOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
						+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:orders.queryDrawingOrders("+(pageNum+1)+")'>下一页</a></li>";
			$("#"+divId).html(html);
		},
		queryRechargeOrders:function (pageNum){
			orders.queryOrders('pageInfo','queryRechargeOrder.do',pageNum);
		},

		queryDrawingOrders:function (pageNum){
			orders.queryOrders('pageInfo2','queryDrawingOrder.do',pageNum);
		},

		/**
		 * 显示用户充值记录
		 * @param entitylist
		 * 
		 */
		showRechargeList:function (entitylist){
			var html = "";
			for(var i=0;i<entitylist.length;i++){
				var order = entitylist[i];
				html += "<tr rowId='"+order.id+"'>";
				html += orders.appendTableRow(order);
				html += "</tr>";
			}
			$("#rechargeOrdersTbody").html(html);
		},
		/**
		 * 添加一行数据到表格中。
		 */
		appendTableRow:function (order){
			var html = "";
			html += "<td>"+order.id+"</td>";
			html += "<td>"+order.orderTime+"</td>";
			html += "<td>"+order.card.bankName+"</td>";
			html += "<td>"+order.card.opencardName+"</td>";
			html += "<td>"+order.card.cardNo+"</td>";
			html += "<td>"+order.receiveAmount+"</td>";
			html += "<td>"+order.remark+"</td>";
			html += "<td>"+order.user.customerName+"</td>";
			if(order.orderStatus=='17001'){
				html += "<td><font color='deepskyblue'>处理中</font></td>";
			}else if(order.orderStatus=='17002'){
				html += "<td><font color='green'>成功</font></td>";
			}else if(order.orderStatus=='17003'){
				html += "<td><font color='red'>失败</font></td>";
			}else if(order.orderStatus=='17004'){
				html += "<td><font color='gray'>过期</font></td>";
			}else{
				html += "<td><font color='red'>未定义状态</font></td>";
			}
			
			var approveOrder = "javascript:orders.approveOrder("+order.id+")";
			var rejectOrder="javascript:orders.rejectOrder("+order.id+")";
			var showOrderInfo = contextPath +"/showRechargeOrderInfo.do?id="+order.id;
			html += "<td class='text-center'>" ;
			if(order.orderStatus=='17001'){
				html += "<a href='"+approveOrder+"' class='btn btn-link btn-sm'>通过审核</a>"; 
				html += "<a href='"+rejectOrder+"'class='btn btn-link btn-sm'>驳回审核</a> ";
			}
			html += "<a href='"+showOrderInfo+"' class='btn btn-link btn-sm'>查看详情</a>"; 
			html += "</td>";
			return html;
		},
		/**
		 * 通过审核
		 * @param orderId
		 */
		approveOrder:function (orderId){
			var params = "&id="+orderId;
			$.ajax({
				type : 'post',
				url : contextPath+"/approveRechargeOrder.do",
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.errorMsg){
						Messenger().post({
							message :decodeURI(data.errorMsg),
							type : 'error',
							showCloseButton : true
						});
						return;
					}
					var order = data.order;
					orders.countRechargeOrder();
					orders.countDrawingOrder();
					orders.changeOrderRow(order);
				},
				error : function(error) {
				}
			});
		},

		changeOrderRow:function (order){
			var rows = $("#rechargeOrdersTbody").find("tr");
			for(var i=0;i<rows.size();i++){
				var row = rows[i];
				if($(row).attr('rowId')==order.id){
					$(row).html("");
					var newHtml = orders.appendTableRow(order);
					$(row).html(newHtml);
				};
			};
		},

		/**
		 * 驳回申请
		 * @param orderId
		 */
		rejectOrder:function (orderId){
			var params = "&id="+orderId;
			$.ajax({
				type : 'post',
				url : contextPath+"/rejectRechargeOrder.do",
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.errorMsg){
						Messenger().post({
							message :decodeURI(data.errorMsg),
							type : 'error',
							showCloseButton : true
						});
						return;
					}
					var order = data.order;
					orders.countRechargeOrder();
					orders.countDrawingOrder();
					orders.changeOrderRow(order);
				},
				error : function(error) {
				}
			});
		},
		showDrawingList:function (entitylist){
			var html = "";
			for(var i=0;i<entitylist.length;i++){
				var order = entitylist[i];
				html += "<tr>";
				html += "<td>"+order.id+"</td>";
				html += "<td>"+order.orderTime+"</td>";
				html += "<td>"+order.receiveAmount+"</td>";
				html += "<td>"+order.user.customerName+"</td>";
				if(order.orderStatus=='17001'){
					html += "<td><font color='deepskyblue'>处理中</font></td>";
				}else if(order.orderStatus=='17002'){
					html += "<td><font color='green'>成功</font></td>";
				}else if(order.orderStatus=='17003'){
					html += "<td><font color='red'>失败</font></td>";
				}else if(order.orderStatus=='17004'){
					html += "<td><font color='gray'>过期</font></td>";
				}else{
					html += "<td><font color='red'>未定义状态</font></td>";
				}
				
				var showOrderInfo = contextPath +"/showDrawingOrderInfo.do?id="+order.id;
				html += "<td class='text-center'><a href='"+showOrderInfo+"' class='btn btn-link btn-sm'>查看详情</a></td>";
			}
			
			$("#rechargeOrdersTbody2").html(html);
		},
		countRechargeOrder:function (){
			var orderType=14001;
			var orderDetailType=18009;
			var params = "&orderType="+orderType+"&orderDetailType="+orderDetailType;
			$.ajax({
				type : 'post',
				url : contextPath+"/countDisposingOrder.do",
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.errorMsg){
						Messenger().post({
							message :decodeURI(data.errorMsg),
							type : 'error',
							showCloseButton : true
						});
						return;
					}
					$("._rechargeCount").html(data.count);
				},
				error : function(error) {
				},
			});
		},

		countDrawingOrder:function (){
			var orderType=14002;
			var orderDetailType=18007;
			var params = "&orderType="+orderType+"&orderDetailType="+orderDetailType;
			$.ajax({
				type : 'post',
				url : contextPath+"/countDisposingOrder.do",
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.errorMsg){
						Messenger().post({
							message :decodeURI(data.errorMsg),
							type : 'error',
							showCloseButton : true
						});
						return;
					}
					
					$("._drawingCount").html(data.count);
				},
				error : function(error) {
				},
			});
		}
	};
}(); 



