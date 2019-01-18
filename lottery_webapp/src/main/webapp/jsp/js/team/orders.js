var orders = function(){
	return{
		checkForm : function(){
			var selectVal = $("#timeList").find("option:selected").val();
			if(selectVal == "1"){
				if($("#sdate").val() == '' || $("#edate").val() == ''){
					formPost.showErrorMessage("请选择时间区间之后进行查询");
					return false;
				}
			}
			return true;
		},
		ajaxSubmit:function(pageNum){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			
			var sid = $("#userTab").find(".current").attr("name");
			attrs.push("setTime");
			values.push($("#setTime").find("option:selected").val());
			
			attrs.push("sdate");
			values.push($("#sdate").val());
			
			attrs.push("edate");
			values.push($("#edate").val());
			
			attrs.push("lotteryTypeName");
			values.push($("#lotteryType").find("option:selected").val()==undefined?"":$("#lotteryType").find("option:selected").val());
			
			attrs.push("orderStatus");
			values.push($("#orderStatus").val());
			
			attrs.push("orderType");
			attrs.push("orderDetailType");
			attrs.push("rsvst1");
			attrs.push("rsvst3");
			//是否勾选查询下级
			attrs.push("rsvst4");
			attrs.push("lotteryType");
			attrs.push("pageNum");
			values.push($("#orderType").val());
			values.push($("#"+sid).val());
			values.push(sid);
			values.push($("#userName").val());
			//是否勾选查询下级
			if($("#checkLower").prop("checked")==true){
				values.push("true");
			}else{
				values.push("false");
			}
			values.push($("#lotteryType").val());
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			formPost.setTbodyObj($("#orderTbody"),$(".hidTr"));
			formPost.submitAjaxForm("order/queryOrder.shtml",attrs,values,orders.showOrders);
		},
		showOrders:function(data){
			var curId = $("#userTab .current").attr("name");
			var odtMap = new Map();
			$("#"+curId).find("option").each(
				function (){
					odtMap.put($(this).val(),$(this).text());
				}
			);
			
			var dtMap = new Map();
			$("#orderType").find("option").each(
				function (){
					dtMap.put($(this).val(),$(this).text());
				}
			);
			var osMap = new Map();
			$("#orderStatus").find("option").each(
				function (){
					osMap.put($(this).val(),$(this).text());
				}
			);
			
			formPost.setTbodyObj(null,null);
			$("."+curId).html("");
			//如果没有数据则页面不显示分页信息。
			if(data.orders==undefined||data.orders==null||data.orders.entitylist.length==0){
				orders.initResultMsg(curId,9,"暂无记录");
				return;
			}
			var tp = data.orders.pageCount;
			var pn= data.orders.pageNum;
			var trObj = $(".hidTr").clone(true);
			$(trObj).show();
			
			var tdObjs = $(trObj).find("td");
			for(var i=0;i<data.orders.entitylist.length;i++){
				var order = data.orders.entitylist[i];
				$(tdObjs).eq(0).html(order.user.customerName);
				$(tdObjs).eq(1).html(order.orderNumber);
				$(tdObjs).eq(2).html(order.orderTime);
				$(tdObjs).eq(3).html(dtMap.get(order.orderType));
				$(tdObjs).eq(4).html(odtMap.get(order.orderDetailType));
				$(tdObjs).eq(5).html(order.rsvst2);
				$(tdObjs).eq(6).html(order.rsvst3);
				$(trObj).find("._odt").val(order.orderDetailType);
				$(tdObjs).eq(7).addClass("color_red _amount");
				$(tdObjs).eq(7).html(NumberFormat.format_number1(order.receiveAmount));
				$(tdObjs).eq(8).addClass("color_red _amount");
				$(tdObjs).eq(8).html(NumberFormat.format_number1(order.accountBalance));
				if(osMap.get(order.orderStatus)){
					if(order.orderStatus>17002){
						$(tdObjs).eq(9).attr("class","color_red");
					}else{
						$(tdObjs).eq(9).attr("class","correctHint");
					}
					$(tdObjs).eq(9).html(osMap.get(order.orderStatus));
				}else{
					$(tdObjs).eq(9).html("异常状态");
					$(tdObjs).eq(9).attr("class","color_red");
				}
				$(trObj).find("._detailInfo").show();
				$(trObj).find("._detailInfo").next().val(order.id);
				$("."+curId).append($(trObj).clone(true));
			}
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			var inAmount = data.orders.rsvdc1;
			var outAmount = data.orders.rsvdc2;
			var proAmount = parseFloat(inAmount)-parseFloat(outAmount);
			
			$("#inAmount").text(inAmount);
			$("#outAmount").text(outAmount);
			$("#profitAmount").text(proAmount);
			
			$("#inAmount").formatCurrency();
			$("#outAmount").formatCurrency();
			$("#profitAmount").formatCurrency();
			
			//显示分页选择显示栏
			pageBarBuid.showPageBar("orders.ajaxSubmit",pn,tp);
			$("._amount").formatCurrency();
		},
		showOrderInfo:function(obj,ordId){
			var odt = $(obj).closest("tr").find("._odt").val();
			var action ="";
			var type="ordersMenu";
			if(odt==18003){
				action = ctx+"/order/winningRebateDetail.html";
			}else if(odt==18015){
				action = ctx+"/order/betRebateDetail.html";
			}else if(odt==18005){
				action = ctx+"/order/myBetRebateDetail.html";
			}else if(odt==18007){
				action = ctx+"/order/cashDrawingDetail.html";
			}else if(odt==18001){ // 普通投注
				action = ctx+"/order/betOrderDetail.html";
			}else if(odt==18004){
				action = ctx+"/finance/showAwardStopDetail.html";
			}else if(odt==18009||odt==18019||odt==18012||odt==18016){
				action = ctx+"/finance/rcOrderDetail.html";
			}else if(odt==18010){
				action = ctx+"/finance/myFeeRefundDetail.html";
			}else if(odt==18018||odt==18006){
				action = ctx+"/order/cancelOrder.html";
			}else if(odt==18013){
				action = ctx+"/finance/zjOrderDetail.html";
				//type ="zjzc";
			}else if(odt==18014){
				action = ctx+"/finance/zjOrderDetail.html";
				//type ="zjzr";
			}else if(odt==18017){ // 追号投注
				action = ctx+"/finance/queryTraceInfo.html";
				//type ="cwmx";
			}
			this.showOrderdetail(ordId,action,type);
		},
		showOrderdetail:function(ordId,action,type){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("rsvst1");
			var values = new Array();
			values.push(ordId);
			values.push(type);
			formPost.submitForm(action,attrs,values);
		},
		changeDdt:function(val){
			var sid = $("#userTab").find(".current").attr("name");
			$("#"+sid).html("");
			$("#"+sid).html("<option value=\"0\" class=\"_alldt\">全部类型</option>");
			if(val==0){
				$("#hidOpt").find("option").each(function(){
					if($(this).attr('name')=="_"+sid){
						$("#"+sid).html($("#"+sid).html()+"<option value='"+$(this).val()+"'>"+$(this).attr("alt")+"</option>");
					}
				});
			}else{
				$("#hidOpt").find("option").each(function(){
					if($(this).attr('name')=="_"+sid){
						if($(this).attr('class').indexOf(val)!=-1){
							$("#"+sid).html($("#"+sid).html()+"<option value='"+$(this).val()+"'>"+$(this).attr("alt")+"</option>");
						}
					}
				});
			}
			$("#"+sid).val(0);
		},
		initDataList:function(curId){
			$("._orderDetailType").show();
			$("._userName").show();
			$("._orderSelectType").show();
			if(curId == 'lotOdt'){
				$("._orderStatus").hide();
				$("._orderNo").hide();
				$("._orderType").hide();
				$("._lotteryType").show();
				$("._issueNO").show();
				$("._queryOrder1").show();
				$("._queryOrder2").hide();
				$("._selectTime").show();
				$("._setTime").hide();
				$("._selectText").hide();
				$("._userName").attr("width","12%");
				$("._checkLower").show();
				$("._lottType").show();
				$("._orderStatus").hide();
				$("#tip").show();
				 $("._orderSelectType").attr("colspan","2");
			}else if(curId == 'finOdt'){
				$("._orderStatuss").show();
				$("._orderNo").show();
				$("._orderType").show();
				$("._lotteryType").hide();
				$("._issueNO").hide();
				$("._queryOrder1").show();
				$("._queryOrder2").hide();
				$("._selectTime").hide();
				$("._setTime").show();
				$("._selectText").show();
				$("#sdate").val(getTime(0, 0, 4));
			    $("#edate").val(getTime(0, 1, 4));
			    $("._orderSelectType").find("span").css("margin-left","30px");
			    $("._userName").attr("width","12%");
			    $("._orderSelectType").attr("colspan","3");
			    $("._checkLower").show();
			    $("._lottType").hide();
			    $("._orderStatus").show();
			    $("#tip").hide();
			}else{
				if(curId == 'userOdt'){
					$("._queryOrder1").show();
					$("._queryOrder2").hide();
					$("._checkLower").show();
					$("._orderStatuss").hide();
					$("._orderSelectType").find("span").css("margin-left","30px");
					$("._userName").attr("width","10%");
					$("._orderSelectType").attr("colspan","3");
				}else{
					$("._userName").hide();
					$("._orderSelectType").hide();
					$("._queryOrder1").hide();
					$("._queryOrder2").show();
					$("._checkLower").hide();
					$("._orderStatus").show();
					$("._setTime").attr("width","20%");
				}
				$("._orderNo").show();
				$("._orderType").show();
				$("._orderStatus").hide();
				$("._lotteryType").hide();
				$("._issueNO").hide();
				$("._selectTime").hide();
				$("._setTime").show();
				$("._selectText").show();
				$("#sdate").val(getTime(0, 0, 4));
			    $("#edate").val(getTime(0, 1, 4));
			    $("._lottType").hide();
			    $("#tip").hide();
			}
		},
		initResultMsg:function(curId,cols,text){
			$("."+curId).html("");
			$("."+curId).html("<tr><td  height='39' align='center' " +
					"colspan='"+ cols +"' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
			$("#_myPageDiv").html("");
			$("#inAmount").text("0.00");
			$("#outAmount").text("0.00");
			$("#profitAmount").text("0.00");
		}
	};
}();

$(document).ready(function(){
	var curId ="";
	$("#sdate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).val(getTime(0, -1, 4));
	$("#edate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).val(getTime(0, 0, 4));
	
	$("#setTime").change(function(){
		var time = $(this).find("option:selected").val();
		if(time == '1'){
			$("._setTime").show();
			$("#sdate").val(getTime(0, -1, 4));
		    $("#edate").val(getTime(0, 0, 4));
		}else{
			$("._setTime").hide();
		}
	});
	
	$("#queryOrder1").click(function(){
		orders.ajaxSubmit(1);
	});
	
	$("._queryOrder2").click(function(){
		orders.ajaxSubmit(1);
	});
	
	$("._detailInfo").click(function(){
		orders.showOrderInfo($(this),$(this).next().val());
	});
	$("#orderType").on('change',function(){
		orders.changeDdt($(this).val());
	});
	$("#userTab >a").click(function(){
		$("#orderType").trigger('change');
		$("._odtList").hide();
		$("._tbody").hide();
		curId = $(this).attr("name");
		$("#orderDetailType").val("0");
		$("#orderType").val("0");
		$("#"+curId).val("0");
		$("#userName").val("");
		$("#checkLower").removeAttr("checked");
		$("#orderStatus").val("0");
		$("."+curId).show();
		$("#"+curId).show();
		orders.initDataList(curId);
		
		orders.initResultMsg(curId,9,"请选择查询条件进行查询");
	});
	curId =$("#userTab .current").attr("name");
	orders.initResultMsg(curId,9,"请选择查询条件进行查询");
	$("#ordersMenu").addClass("current");
	$("#orderType").trigger('change');
	// 初始化列表头部
	orders.initDataList(curId);
});