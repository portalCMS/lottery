var Orders = function(){
	var orderDetailTypes = new Array();
	var contextPath = ctx;
	var orderBody = "orderBody";
	var pageDivClass = "pager";
	var orderBigTypes = function(bigType){
		$("#orderDetailTypes").html("");
		for(var i=0;i<orderDetailTypes.length;i++){
			var type = orderDetailTypes[i].split(":");
			switch (parseInt(type[0])) {
				case 18001:
				case 18002:
				case 18003:
				case 18004:
				case 18005:
				case 18006:
				case 18017:
				case 18018:
					if(bigType==1||bigType==0){
						$("#orderDetailTypes").append($("<option>").val(type[0]).text(type[1]).attr("selected","selected"));
					}
					break;
				case 18007:
				case 18008:
				case 18009:
				case 18010:
				case 18011:
				case 18019:
					if(bigType==2||bigType==0){
						$("#orderDetailTypes").append($("<option>").val(type[0]).text(type[1]).attr("selected","selected"));
					}
					break;
				case 18013:
				case 18014:
				case 18015:
				case 18016:
					if(bigType==3||bigType==0){
						$("#orderDetailTypes").append($("<option>").val(type[0]).text(type[1]).attr("selected","selected"));
					}
					break;
				case 18012:
					if(bigType==4||bigType==0){
						$("#orderDetailTypes").append($("<option>").val(type[0]).text(type[1]).attr("selected","selected"));
					}
					break;
				default:
					if(bigType==0){
						$("#orderDetailTypes").append($("<option>").val(type[0]).text(type[1]).attr("selected","selected"));
					}
					break;
			}
		}
	};
	return{
		init:function(){
			$("#orderDetailTypes").find("option").each(function(){
				orderDetailTypes.push($(this).val()+":"+$(this).text());
			});
			$("#orderBigTypes").on("change",function(){
				orderBigTypes($(this).val());
			});
			this.queryOrders(0);
		},
		queryOrders:function(pageNo){
			$(".orderBody").html("<tr><td colspan='9' align='center' valign='middle' " +
			"style='vertical-align: middle;'>查询中，请稍候......</td></tr>");
			var params = "";
			var orderTimeBegin = $("#orderTimeBegin").val();
			var orderTimeEnd = $("#orderTimeEnd").val();
			var customerName = $("#customerName").val();
			var orderNumber = $("#orderNumber").val();
			var orderStatus = $("#orderStatus").val();
			var orderDetailTypes=$("#orderDetailTypes").val();
			var customerType = $("#customerType").val();
			params = "pageNum="+pageNo+"&orderDetailTypes="+orderDetailTypes+"&customerType="+customerType;
			if(pageNo>-1&&orderTimeBegin!=""){
				params += "&orderTimeBegin="+orderTimeBegin;
			}
			if(pageNo>-1&&orderTimeEnd!=""){
				params += "&orderTimeEnd="+orderTimeEnd;
			}
			if(customerName!=""){
				params += "&customerName="+customerName;
			}
			if(orderStatus!=""){
				params += "&orderStatus="+orderStatus;
			}
			if(orderNumber!=""){
				params += "&orderNumber="+orderNumber;
			}
			var url = "queryOrders.do";
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
					Orders.innerHtmlToTable(data);
					$("._fcash").formatCurrency();
				},
				error : function(error) {
					
				}
			});
		},
		innerHtmlToTable: function(data){
			var tableDate = data.userOrderMaps.pagelist;
			var cells = data.userOrderMaps.cellList;
			var sumcount = 0;
			var revenue = 0;
			var expenses = 0;
			for(var i=0;i<cells.length;i++){
				if(cells[i][0]==14001){
					revenue = cells[i][1];
					sumcount += cells[i][1]*10000;
				}
				if(cells[i][0]==14002){
					expenses = cells[i][1];
					sumcount -= cells[i][1]*10000;
				}
			}
			$("._revenue").text(revenue);
			$("._expenses").text(expenses);
			$("._sumcount").text(sumcount/10000);
			var html = "";
			for(var i = 0; i < tableDate.length; i++){
				html += "<tr>";
				html += "<td>"+tableDate[i].orderNumber+"</td>";
				if(tableDate[i].orderDetailType==18012){
					html += "<td>"+tableDate[i].orderDetailTypeName+"("+tableDate[i].remark+")</td>";
				}else{
					html += "<td>"+tableDate[i].orderDetailTypeName+"</td>";
				}
				
				html += "<td>"+tableDate[i].orderTime+"</td>";
				html += "<td><a href=\"user/showUserInfo/"+tableDate[i].customerId+".do"+"\">"+tableDate[i].customerName+"</a></td>";
				if(tableDate[i].orderTypeName == '收入'){
					html += "<td><span class=\"text-success\">"+tableDate[i].orderTypeName+"</span></td>";
				}else{
					html += "<td><span class=\"text-danger\">"+tableDate[i].orderTypeName+"</span></td>";
				}
				html += "<td><span class=\"text-danger _fcash\">"+NumberFormat.formatNumber(tableDate[i].orderAmount,2,1)+"</span></td>";
				if(tableDate[i].orderStatusName == '成功'){
					html += "<td><label class=\"label label-success\">"+tableDate[i].orderStatusName+"</label></td>";
				}else{
					html += "<td><label class=\"label label-default\">"+tableDate[i].orderStatusName+"</label></td>";
				}
				if(tableDate[i].accountBalance==null||tableDate[i].accountBalance==undefined
						||tableDate[i].accountBalance==""){
					html += "<td><span class='text-danger'>未记录</span></td>";
				}else{
					html += "<td><span class='text-danger'>"+tableDate[i].accountBalance+"</span></td>";
				}
				html += "<td><span class='text-danger'>"+tableDate[i].remark+"</span></td>";
				html += this.searchOrderDetail(tableDate[i].orderDetailType,tableDate[i].orderNumber);
				html += "</tr>";
			}
			$("."+orderBody).html(html);
			this.pageInfo(pageDivClass, data.pageNum, data.maxCount, data.pageCount);
		},
		searchOrderDetail: function(orderDetailType,order_number){
			var _detailType = parseInt(orderDetailType);
			var html = "";
			switch(_detailType){
				case 18001: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18002: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18003: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfozjfk.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18004: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfocd.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18005: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotzfd.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18006: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfocd.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18007: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotk.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18008: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfohtkk.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18009: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfocz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18019: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfocz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18010: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfoczfk.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18011: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfohtcz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18012: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfohdpf.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18013: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfozjzc.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18014: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfozjzr.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18015: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotzyl.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18016: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfoxtfh.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18017: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfotz.do\",\""+order_number+"\")'>查看详情</a></td>";break;
				case 18018: html = "<td><a href='javascript:void(0)' onclick='Orders.queryDetail(\"showQueryInfocd.do\",\""+order_number+"\")'>查看详情</a></td>";break;
			}
			return html;
		},
		pageInfo:function(divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<li><a href='javaScript:Orders.queryOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
						+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:Orders.queryOrders("+(pageNum+1)+")'>下一页</a></li>";
			$("."+divId).html(html);
		},
		queryDetail: function(action,order_number){
			var attrs = new Array();
			attrs.push("orderNumber");
			var values = new Array();
			values.push(order_number);
			formPost.submitForm(action,attrs,values);
		}
	};
}();

$(document).ready(function(){
	if ($(".recharge_time").eq(0).val() == "") {
		var now = new Date();
		now = now.addDay(0);
		var nowTime = now.format("yyyy-MM-dd");
		$(".recharge_time").eq(0).val(nowTime+" 04:00:00");
		//$(".recharge_time").eq(1).val(getNowTime(0, 0));
	}
	$(".recharge_time").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
	Orders.init();
	$(".btn-query").bind("click",function(){Orders.queryOrders(0);});
	//$(".queryDetail").bind("click",function(){alert(1);});
});