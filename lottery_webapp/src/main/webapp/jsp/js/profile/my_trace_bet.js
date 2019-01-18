var packageParams = function(attrs,values){
	$("._params").each(function(){
		attrs.push($(this).attr("id"));
		values.push($(this).val());
	});
};
var traceOrders  = function(){
	var _lotteryCode ="";
	var callbackfun ={
		"initPlayModel":function(data){
			var lotterySeries = data.lotteryType.currentIssueNo;
			html ="<option value=''>全部奖期</option>";
			if(lotterySeries ==''){
				$("._issueNo").html(html);
				return;
			}
			if(_lotteryCode == "50001" || _lotteryCode =="50002"){
				countSeries = parseInt(lotterySeries.substring(2));
			}else{
				countSeries = parseInt(lotterySeries.substring(6));
			}
			
			var total = 0;
			for(var i = countSeries-1;i>0;i--){
				 total++;
				 lotterySeries = parseInt(lotterySeries)-1;
				 html+="<option value='"+lotterySeries+"'>"+lotterySeries+"</option>";
				 if(total >120){
					 break;
				 }
			}
			$("._issueNo").html(html);
		}
	};
	return{
		checkForm:function(){
			if($("#sdate").val() == '' || $("#edate").val() == ''){
				formPost.showErrorMessage("请选择时间区间之后进行查询");
				return false;
			}
			return true;
		},
		initPlayModel:function(lotteryCode){
			_lotteryCode=lotteryCode;
			if(lotteryCode == ""){
				$("._playmodel").html("<option value=''>全部玩法</option>");
			}else{
				var attrs = new Array();
				var values = new Array();
				attrs.push("lotteryCode");
				values.push(lotteryCode);
				formPost.submitAjaxForm1("finance/getPlayModelsAndIssueNo.shtml",attrs,values,callbackfun["initPlayModel"],null);
			}
		},
		ajaxSubmit:function(pageNum){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			packageParams(attrs,values);
			attrs.push("pageNum");
			
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			formPost.setTbodyObj($("#traceOrders"),$("._hidtr"));
			formPost.submitAjaxForm("finance/queryTraceOrders.shtml",attrs,values,traceOrders.showOrders);
		},
		showOrders:function(data){
			formPost.setTbodyObj(null,null);
			$("#traceOrders").html("");
			//如果没有数据则页面不显示分页信息。
			if(data.orders==undefined||data.orders==null||data.orders.entitylist.length==0){
				traceOrders.initResultMsg("暂无记录");
				$("#inAmount").text(0);
				$("#outAmount").text(0);
				return;
			}
			var tp = data.orders.pageCount;
			var pn= data.orders.pageNum;
			var trObj = $("._hidtr").clone(true);
			$(trObj).show();
			
			var tdObjs = $(trObj).find("td");
			for(var i=0;i<data.orders.entitylist.length;i++){
				var order = data.orders.entitylist[i];
				$(tdObjs).eq(0).html(order.createTime);
				$(tdObjs).eq(1).html(order.rsvst1);
				$(tdObjs).eq(2).html(order.rsvst2);
				$(tdObjs).eq(3).html(order.orderAmount);
				$(tdObjs).eq(4).html(order.finishAmount);
				
				$(tdObjs).eq(5).html(order.rsvdc2+"/"+order.rsvdc1+"期");
				$(tdObjs).eq(6).html(order.returnAmount);
				if(order.rsvdc4==0){
					$(tdObjs).eq(7).find("span").html("未中奖");
					$(tdObjs).eq(7).find("span").removeClass("correctHint");
					$(tdObjs).eq(7).find("p").html("");
				}else{
					$(tdObjs).eq(7).find("span").html("中奖");
					$(tdObjs).eq(7).find("span").addClass("correctHint");
					$(tdObjs).eq(7).find("p").html(order.rsvdc4);
				}
				if(order.awardStop==1){
					$(tdObjs).eq(8).find("span").html("是");
					$(tdObjs).eq(8).find("span").addClass("correctHint");
				}else{
					$(tdObjs).eq(8).find("span").html("否");
					$(tdObjs).eq(8).find("span").removeClass("correctHint");
				}
				$(trObj).find("._detailInfo").show();
				$(trObj).find("._detailInfo").next().val(order.id);
								
				var inAmount = data.orders.rsvdc1;
				var outAmount = data.orders.rsvdc2;
				
				$("#inAmount").text(inAmount);
				$("#outAmount").text(outAmount);
				
				$("#inAmount").formatCurrency();
				$("#outAmount").formatCurrency();
				
				$("#traceOrders").append($(trObj).clone(true));
			}
			// 单行表格加背景色
			$('#traceOrders tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#traceOrders tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			$("._betNum").unbind();
			$("._betNum").click(function(){
				formPost.showInfoTab('投注号码明细',$(this).parent().attr("title"));
			});
			//显示分页选择显示栏
			pageBarBuid.showPageBar("traceOrders.ajaxSubmit",pn,tp);
			$("._amount").formatCurrency();
		},
		showOrderdetail:function(ordId){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("fromType");
			var values = new Array();
			values.push(ordId);
			values.push("myTraceBetMenu");
			var action="queryTraceInfo.html";
			formPost.submitForm(action,attrs,values);
		},
		initResultMsg : function(text){
			$("#traceOrders").html("<tr><td height='39' align='center' " +" colspan='11' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
			$("#_myPageDiv").html("");
		}
	};
}();

$(document).ready(function(){
	$("#sdate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).focus(function(){
		if(Math.abs(getDateDiff($("#edate").val(),$("#sdate").val(),"day")) > 1){
			$("._issueNo").attr("disabled","disabled");
			$("._issueNo")[0].options[0].selected =true;
		}else{
			$("._issueNo").removeAttr("disabled");
		}
	});
	$("#edate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).focus(function(){
		if(Math.abs(getDateDiff($("#edate").val(),$("#sdate").val(),"day")) > 1){
			$("._issueNo").attr("disabled","disabled");
			$("._issueNo")[0].options[0].selected =true;
		}else{
			$("._issueNo").removeAttr("disabled");
		}
	});
    $("#sdate").val(getTime(0, 0, 4));
    $("#edate").val(getTime(0, 1, 4));
	$("#queryOrder").click(function(){
		traceOrders.ajaxSubmit();
	});
	$("._detailInfo").click(function(){
		traceOrders.showOrderdetail($(this).next().val());
	});
	$("._lotteryCode").on("change",function(){
		if($(this).val() == ""){
			$("._issueNo").attr("disabled","disabled");
			$("._playmodel").attr("disabled","disabled");
			$("._issueNo")[0].options[0].selected =true;
			$("._playmodel")[0].options[0].selected =true;
		}else{
			$("._issueNo").removeAttr("disabled");
			$("._playmodel").removeAttr("disabled");
		}
		traceOrders.initPlayModel($(this).val());
	});
	$("#myTraceBetMenu").addClass("current");
	traceOrders.initResultMsg("请选择查询条件进行查询");
	//初始化后查询一次
	//traceOrders.ajaxSubmit(1);
});