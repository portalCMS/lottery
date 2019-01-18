var finance = function(){
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
			var curId = $("#userTab .current").attr("name");
			var attrs = new Array();
			var values = new Array();
			attrs.push("rsvst1");
			//放入当前显示的选项卡的id，方便后台判断不同选项卡对应的所有订单子类。
			values.push(curId);
			
			attrs.push("setTime");
			values.push($("#timeList").find("option:selected").val());
			
			attrs.push("sdate");
			values.push($("#sdate").val());
			
			attrs.push("edate");
			values.push($("#edate").val());
			
			attrs.push("lotteryTypeName");
			values.push($("#lotteryType").find("option:selected").val()==undefined?"":$("#lotteryType").find("option:selected").val());
			
			attrs.push("orderType");
			values.push($("#lotdt").val());
			
			attrs.push("orderDetailType");
			values.push($("#"+curId).val());
			
			attrs.push("orderStatus");
			values.push($("#orderStatus").val());
			
			
			attrs.push("pageNum");
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			var curId = $("#userTab .current").attr("name");
			formPost.setTbodyObj($("."+curId),$(".hidTr"));
			formPost.submitAjaxForm("finance/queryOrder.shtml",attrs,values,finance.showOrders);
		},
		showOrders:function(data){
			formPost.setTbodyObj(null,null);
			var curId = $("#userTab .current").attr("name");
			$("."+curId).html("");
			var resultList = data.orders.pagelist;
			//如果没有数据则页面不显示分页信息。
			if(data.orders==undefined||data.orders==null||resultList.length==0){
				finance.initResultMsg(curId,9,"暂无记录");
				return;
			}
			var tp = data.orders.pageCount;
			var pn= data.orders.pageNum;
			
			var inAmount = data.orders.rsvdc1;
			var outAmount = data.orders.rsvdc2;
			var proAmount = parseFloat(inAmount)-parseFloat(outAmount);
			
			$("#inAmount").text(inAmount);
			$("#outAmount").text(outAmount);
			$("#profitAmount").text(proAmount);
			
			$("#inAmount").formatCurrency();
			$("#outAmount").formatCurrency();
			$("#profitAmount").formatCurrency();
			
			var trObj = $(".hidTr").last().clone(true);
			$(trObj).show();
			
			var curId = $("#userTab .current").attr("name");
			var odtMap = new Map();
			$("#"+curId).find("option").each(
				function (){
					odtMap.put($(this).val(),$(this).text());
				}
			);
			
			var dtMap = new Map();
			$("#lotdt").find("option").each(
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
			
			var tdObjs = $(trObj).find("td");
			for(var i=0;i<resultList.length;i++){
				var order = resultList[i];
				$(tdObjs).eq(0).html(order.orderTime);
				$(tdObjs).eq(1).html(order.orderNumber);
				$(tdObjs).eq(2).html(odtMap.get(order.orderDetailType));
				$(trObj).find("._odt").val(order.orderDetailType);
				$(tdObjs).eq(3).html(order.rsvst2);
				$(tdObjs).eq(4).html(order.rsvst3);
				$(tdObjs).eq(5).html(dtMap.get(order.orderType));
				$(tdObjs).eq(6).html(order.receiveAmount);
				$(tdObjs).eq(7).html(order.accountBalance==undefined?0:order.accountBalance);
				if(osMap.get(order.orderStatus)){
					if(order.orderStatus>17002){
						$(tdObjs).eq(8).attr("class","color_red");
					}else{
						$(tdObjs).eq(8).attr("class","correctHint");
					}
					$(tdObjs).eq(8).html(osMap.get(order.orderStatus));
				}else{
					$(tdObjs).eq(8).html("异常状态");
					$(tdObjs).eq(8).attr("class","color_red");
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
			
			//显示分页选择显示栏
			pageBarBuid.showPageBar("finance.ajaxSubmit",pn,tp);
			$("._amount").formatCurrency();
		},
		showOrderInfo:function(obj,ordId){
			var odt = $(obj).closest("tr").find("._odt").val();
			var action ="";
			var type="financeMenu";
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
			
			finance.showOrderdetail(ordId,action,type);
		},
		showOrderdetail:function(ordId,action,type){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("fromType");
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
		initDataList:function(flag){
			if(flag == 1){
				$("._orderNo").hide();
				$("._lotteryType").show();
				$("._playModel").show();
				$("._issueNO").show();
			}else{
				$("._orderNo").show();
				$("._lotteryType").hide();
				$("._playModel").hide();
				$("._issueNO").hide();
			}
		},
		initResultMsg:function(curId,cols,text){
			$("."+curId).html("");
			$("."+curId).html("<tr><td  height='39' align='center' " +
					"colspan='"+ cols +"' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
			$("#_myPageDiv").empty();
			$("#inAmount").text("0.00");
			$("#outAmount").text("0.00");
			$("#profitAmount").text("0.00");
		}
	};
}();

$(document).ready(function(){
	$("#sdate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
	$("#edate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});

	$("#timeList").change(function(){
		var time = $(this).find("option:selected").val();
		if(time == '1'){
			$("._setTime").show();
			$("#sdate").val(getTime(0, -1, 4));
		    $("#edate").val(getTime(0, 0, 4));
		}else{
			$("._setTime").hide();
		}
	});
	$("#queryOrder").click(function(){
		if(!finance.checkForm()){
			return;
		}
		finance.ajaxSubmit();
	});
	
	$("._detailInfo").click(function(){
		finance.showOrderInfo($(this),$(this).next().val());
	});
	
	$("#financeMenu").addClass("current");
	$("#lotdt").on('change',function(){
		finance.changeDdt($(this).val());
	});
	// 选项卡
	$('#userTab > a').click(function() {
		$("#lotdt").trigger('change');
		var curId = $(this).attr('name');
		var i = $(this).attr("index");
		$("._odtList").hide();
		$("._tbody").hide();
		if(i == 1){
			$("._selectTime").show();
			$("._selectText").hide();
			if($("#timeList").find("option:selected").val() == 0){
				$("._setTime").hide();
			}else{
				$("._setTime").show();
				$("#sdate").val(getTime(0, -1, 4));
			    $("#edate").val(getTime(0, 0, 4));
			}
			$("._lottType").show();
			$("._lottType").next().attr("width","25%");
		}else{
			$("._selectTime").hide();
			$("._selectText").show();
			$("._setTime").show();
			$("._lottType").hide();
			$("._lottType").next().attr("width","19%");
		    $("#sdate").val(getTime(0, 0, 4));
		    $("#edate").val(getTime(0, 1, 4));
		}
		$("."+curId).show();
		$("#"+curId).show();
		//切换卡片重置表单默认值
		$("#lotteryType").val("0");
		$("#lotdt").val("0");
		$("#orderStatus").val("0");
		$("#"+curId).val("0");
		
		finance.initDataList(i);
		// 初始化列表文本信息。
		finance.initResultMsg(curId,8,"请选择查询条件进行查询");
	});
	// 初始化后查询一次
	//$('#userTab  a:visible').eq(0).click();
	$("#lotdt").trigger('change');
	var curId =$("#userTab .current").attr("name");
	// 初始化列表文本信息。
	finance.initResultMsg(curId,9,"请选择查询条件进行查询");
	// 初始化列表头部
	finance.initDataList(1);
});