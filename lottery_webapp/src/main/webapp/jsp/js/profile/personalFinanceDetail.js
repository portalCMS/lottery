var psFinance =function(){
	return{
		checkForm:function(){
			var selectVal = $("#timeList").find("option:selected").val();
			if(selectVal == "1"){
				if($("#sdate").val() == '' || $("#edate").val() == ''){
					formPost.showErrorMessage("请选择时间区间之后进行查询");
					return false;
				}
			}
			return true;
		},
		initResultMsg:function(text){
			$("#ykdata").html("");
			$("#ykdata").html("<tr><td  height='39' align='center' colspan='7' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
		},
		ajaxSubmit:function(){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			var selectVal = $("#timeList").find("option:selected").val();
			
			attrs.push("sdate");
			attrs.push("edate");
			attrs.push("queryType");
			
			values.push($("#sdate").val());
			values.push($("#edate").val());
			values.push(selectVal);
			
			formPost.setTbodyObj($("#ykdata"),$("._ykclone"));
			formPost.submitAjaxForm("finance/queryPersonalFinancialList.shtml",attrs,values,psFinance.showPersonOrders);
		},
		showPersonOrders:function(data){
			formPost.setTbodyObj(null,null);
			$("#ykdata").html("");
			//如果没有数据则页面不显示分页信息。
			var result =data.orders;
			if(result==undefined || result=='' || result.length==0){
				psFinance.initResultMsg("暂无记录");
				return;
			}
			var trObj = $("._ykclone").clone(true);
			var calcObj = $("._calcSta").clone(true);
			$(trObj).show();
			var tdObjs = $(trObj).find("td");
			var calObjs = $(calcObj).find("td");
			var order='';
			var totalTetAmount=0,rebateAmount=0,saleAmount=0,winAmount=0,ykAmount=0;
			for(var i=0;i<result.length;i++){
				order = result[i];
				tdObjs.eq(0).html(order.createUser);
				tdObjs.eq(1).html(order.createTime.substring(0,10));
				tdObjs.eq(2).html(order.totalTetAmount);
				tdObjs.eq(3).html(order.rebateAmount);
				tdObjs.eq(4).html(order.saleAmount);
				tdObjs.eq(5).html(order.winAmount);
				tdObjs.eq(6).html(order.ykAmount);
				
				totalTetAmount +=parseFloat(order.totalTetAmount);
				rebateAmount +=parseFloat(order.rebateAmount);
				saleAmount +=parseFloat(order.saleAmount);
				winAmount +=parseFloat(order.winAmount);
				ykAmount +=parseFloat(order.ykAmount);
				
				$("#ykdata").append($(trObj).clone(true).show());
			}
			calObjs.eq(1).html(totalTetAmount);
			calObjs.eq(2).html(rebateAmount);
			calObjs.eq(3).html(saleAmount);
			calObjs.eq(4).html(winAmount);
			calObjs.eq(5).html(ykAmount);
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			if($("#timeList").find("option:selected").val() == '1'){
				$("._showDayGrapBtn").hide();
				$("._showDaysGrapBtn").show();
				$("#ykdata").append($(calcObj).clone(true).show());
			}
			else{
				$("._showDayGrapBtn").show();
				$("._showDaysGrapBtn").hide();
			}
			$("._amount").formatCurrency();
		},
		ajaxGrapChart:function(){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			var selectVal = $("#timeList").find("option:selected").val();
			
			attrs.push("sdate");
			attrs.push("edate");
			attrs.push("queryType");
			
			values.push($("#sdate").val());
			values.push($("#edate").val());
			values.push(selectVal);	
			
			formPost.submitAjaxForm("finance/queryPersonalFinancialList.shtml",attrs,values,psFinance.showChartData);
		},
		showChartData:function(data){
			$("#grapReport").html('');
			var order = data.orders[0];
			var date =new Date();
	        var chart = anychart.columnChart([
	            ['投注总额', NumberFormat.formatNumber(order.totalTetAmount,2,0)],
	            ['返点总额', NumberFormat.formatNumber(order.rebateAmount,2,0)],
	            ['实际销售总额', NumberFormat.formatNumber(order.saleAmount,2,0)],
	            ['中奖总额', NumberFormat.formatNumber(order.winAmount,2,0)],
	            ['总盈亏',NumberFormat.formatNumber(order.ykAmount,2,0)]
	        ]);
	        chart.title(date.getFullYear()+'年'+(date.getMonth()+1)+"月"+date.getDate()+"日");
	        chart.yAxis().title().text('金额：（元）');
	        chart.yAxis().labels().textFormatter(function(){
	            return '$' + NumberFormat.formatNumber(this.value,2,0);
	        });
	        chart.xAxis().title().text('');
	        
	        chart.container('grapReport').draw();
	        $(".anychart-credits-text").hide();
	        $(".anychart-credits-logo").hide();
	        $("#grapReport").show();
		},
		ajaxHistoryGrapChart:function(){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			
			attrs.push("sdate");
			attrs.push("edate");
			attrs.push("queryType");
			
			values.push($("#sdate").val());
			values.push($("#edate").val());
			values.push("3");
			formPost.submitAjaxForm("finance/queryPersonalFinancialList.shtml",attrs,values,psFinance.historyChartData);
		},
		historyChartData:function(data){
			$("#grapReport").html('');
			var orders = data.orders;
			var oneArray = new Array();
			var twoArray = new Array();
			for(var i=0;i<orders.length;i++){
				twoArray = new Array();
				twoArray.push(orders[i].day.replace(/-/g, "月")+"日",NumberFormat.formatNumber(orders[i].ykAmount,2,0));
				oneArray.push(twoArray);
			}
			 var chart = anychart.lineChart(oneArray);
		     chart.title().text('游戏总盈亏统计');
		     chart.yAxis().title().text('金额：（元）');
			 chart.yAxis().labels().textFormatter(function(){
				 return '$' + NumberFormat.formatNumber(this.value,2,0);
			 });
			 chart.xAxis().title().text('');
			 chart.container('grapReport').draw();
			 $(".anychart-credits-text").hide();
			 $(".anychart-credits-logo").hide();
	        $("#grapReport").show();
		}
	}
}();
$(function(){
	$("#sdate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).val(getTime(0, 0, 4)).attr("disabled","disabled");
	$("#edate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).val(getTime(0, 1, 4)).attr("disabled","disabled");
	
	$("#timeList").change(function(){
		$("#grapReport").hide();
		$("#ykdata").empty();
		psFinance.initResultMsg("请选择查询条件进行查询");
		$("._showDayGrapBtn").hide();
		$("._showDaysGrapBtn").hide();
		var time = $(this).find("option:selected").val();
		if(time == '1'){
			$("#sdate").removeAttr("disabled");
			$("#sdate").val(getTime(0, -7, 4));
			$("#edate").val(getTime(0, 0, 4));
			$("#tip").show();
			
		}else{
			$("#sdate").attr("disabled","disabled").val(getTime(0, 0, 4));
			$("#edate").attr("disabled","disabled").val(getTime(0, 1, 4));
			$("#tip").hide();
		}
	});
	$("._ykBtn").click(function(){
		psFinance.ajaxSubmit();
	});
	$("._showDayGrapBtn").click(function(){
		psFinance.ajaxGrapChart();
	});
	$("._showDaysGrapBtn").click(function(){
		psFinance.ajaxHistoryGrapChart();
	});
	// 初始化列表文本信息。
	psFinance.initResultMsg("请选择查询条件进行查询");
	$("#personalFinancialMenu").addClass("current");
});