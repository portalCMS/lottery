$(document).ready(function() {
	var myDate = new Date();
	
    var year = myDate.getFullYear();       //年
    var month = myDate.getMonth() + 1;     //月
    var day = myDate.getDate();            //日
	
	$("._endTime").val(year+"-"+month+"-"+day);     //获取当前日期
	var days = day-10;
	if(days<=0){
		$("._startTime").val(year+"-"+(month-1)+"-"+(30+days));
	}else{
		$("._startTime").val(year+"-"+month+"-"+(day-10));
	}
	
	if(month<10){
		if(day<10){
			$("#et1").val(year+"-0"+month+"-0"+day+" 04:00:00");     
		}else{
			$("#et1").val(year+"-0"+month+"-"+day+" 04:00:00");     
		}
		
	}else{
		if(day<10){
			$("#et1").val(year+"-0"+month+"-0"+day+" 04:00:00");     
		}else{
			$("#et1").val(year+"-"+month+"-"+day+" 04:00:00");    
		}
	}
	
	var days = day-1;
	if(days<=0){
		month = month - 1;
		if(month<10){
			month = "0"+month;
		}
		$("#st1").val(year+"-"+(month-1)+"-"+(30+days)+" 04:00:00");
	}else{
		if(month<10){
			month = "0"+month;
		}
		if(days<10){
			days = "0"+days;
		}
		$("#st1").val(year+"-"+month+"-"+ days+" 04:00:00");
	}
	
	marketquery.queryMarketStasData(1);
	
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});
	});
	$("._startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});
	});
	
	$("#st1").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'});
	});
	$("#et1").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'});
	});
	
	$("#userExcelDownload").on('click',function(){
		normalquery.downloadUserExcel();
	});
	$("#statsMenu li").removeClass("active");
	$("#normal").addClass("active");
	
	$("#prev").on('click',function(){
		marketquery.prevQuery();
	});
	$("#next").on('click',function(){
		marketquery.nextQuery();
	});
	$("#queryMarket").on('click',function(){
		marketquery.queryMarketStasData(1);
	});
	$("#downloadExcel").on('click',function(){
		marketquery.downloadMarketData();
	});
	
	marketquery.queryAvgPerStasData();
	
	marketquery.queryAmountStasData();
	
	$("#queryAmount").on('click',function(){
		marketquery.queryAmountStasData();
	});
	
	$("#downloadAmountExcel").on('click',function(){
		marketquery.downloadAmountExcel();
	});
	
});

var marketquery = function(){
	
	return{
		downloadAmountExcel:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			vals.push($("#st2").val());
			vals.push($("#et2").val());
			formPost.submitForm("downloadAmountExcel.do",attrs,vals);
		},
		queryAmountStasData:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			vals.push($("#st2").val());
			vals.push($("#et2").val());
			formPost.submitAjaxForm("statistic/queryAmountStasData.do",attrs,vals,this.queryAmountCallback);
		},
		queryAmountCallback:function(data){
			data.amountVos.sort(
					function(a,b){
						return parseInt(a.key)-parseInt(b.key);
					}
				);
			
			var dateArr = new Array();
			var betArr = new Array();
			var rcArr = new Array();
			var drArr = new Array();
			var awArr = new Array();
			var rbArr = new Array();
			var cashArr = new Array();
			for(var i=0;i<data.amountVos.length;i++){
				var map = data.amountVos[i];
				var date = map.key;
				var datas = map.vos;
				dateArr.push(date.substring(date.length-2));
				var betAmount =parseFloat(NumberFormat._currencyFormatted(datas[0].value));
				var awAmount = parseFloat(NumberFormat._currencyFormatted(datas[1].value));
				var rcAmount = parseFloat(NumberFormat._currencyFormatted(datas[2].value));
				var drAmount= parseFloat(NumberFormat._currencyFormatted(datas[3].value));
				var rbAmount = parseFloat(NumberFormat._currencyFormatted(datas[4].value));
				var cashAmount = parseFloat(NumberFormat._currencyFormatted(datas[5].value));
				betArr.push(betAmount);
				awArr.push(awAmount);
				rcArr.push(rcAmount);
				drArr.push(drAmount);
				rbArr.push(rbAmount);
				cashArr.push(cashAmount);
			}
			
			//moneyAnalytics
			$('#moneyAnalytics').highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : '网站余额'
				},
				subtitle : {
					text : '充值总额 -  投注总额  +  返点总额  + 返奖总额  -  提款总额  =  网站余额'
				},
				xAxis : {
					categories : dateArr
				},
				yAxis : {
					min : 0,
					title : {
						text : '金额'
					}
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.y} 元 </b><br>',
					shared : true
				},
				plotOptions : {
					column : {
						pointPadding : 0.2,
						borderWidth : 0
					}
				},
				series : [{
					name : '充值总额',
					data : rcArr

				}, {
					name : '投注总额',
					data : betArr

				}, {
					name : '返点总额',
					data : rbArr
				}, {
					name : '返奖总额',
					data : awArr
				}, {
					name : '提款总额',
					data : drArr 

				}, {
					name : '网站余额',
					type : 'line',
					data : cashArr
				}]
			});
		},
		queryAvgPerStasData:function(){
			formPost.submitAjaxForm("statistic/queryAvgPerStasData.do",null,null,this.queryAvgCallback);
		},
		queryAvgCallback:function(data){
			//按日期排序
			data.mapVos.sort(
				function(a,b){
					return parseInt(a.key)-parseInt(b.key);
				}
			);
			
			data.ykVos.sort(
					function(a,b){
						return parseInt(a.key)-parseInt(b.key);
					}
			);
			
			var dateArr = new Array();
			var betArr = new Array();
			var rcArr = new Array();
			var drArr = new Array();
			var awArr = new Array();
			var cashArr = new Array();
			for(var i=0;i<data.mapVos.length;i++){
				var map = data.mapVos[i];
				var date = map.key;
				var datas = map.vos;
				dateArr.push(date.substring(date.length-2));
				var betAmount =parseFloat(NumberFormat._currencyFormatted(datas[0].value));
				var rcAmount = parseFloat(NumberFormat._currencyFormatted(datas[2].value));
				var drAmount = parseFloat(NumberFormat._currencyFormatted(datas[3].value));
				var awAmount = parseFloat(NumberFormat._currencyFormatted(datas[1].value));
				var cashAmount = parseFloat(NumberFormat._currencyFormatted(datas[4].value));
				betArr.push(betAmount);
				awArr.push(awAmount);
				rcArr.push(rcAmount);
				drArr.push(drAmount);
				cashArr.push(cashAmount);
			}
			//renjunAnalytics
			$('#renjunAnalytics').highcharts({
				chart : {
					type : 'line'
				},
				title : {
					text : '人均行为统计'
				},
				subtitle : {
					text : '总金额  /  参与人数  =  人均值'
				},
				xAxis : {
					categories : dateArr
				},
				yAxis : {
					title : {
						text : '金额'
					}
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.y} 元 </b><br>',
					shared : true
				},
				series : [{
					name : '投注人均值',
					data : betArr
				}, {
					name : '充值人均值',
					data : rcArr
				}, {
					name : '提款人均值',
					data : drArr
				}, {
					name : '返奖人均值',
					data : awArr
				}, {
					name : '账户余额人均值',
					data : cashArr
				}]
			});
			
			//盈亏
			var ykArr = new Array();
			for(var i=0;i<data.ykVos.length;i++){
				var ykvo = data.ykVos[i];
				var amount = parseFloat(NumberFormat._currencyFormatted(ykvo.value));
				ykArr.push(amount);
			}
			
			var totalBet = parseFloat(NumberFormat._currencyFormatted(data.totalBet));
			var totalAward = parseFloat(NumberFormat._currencyFormatted(data.totalAward));
			var totalRebates = parseFloat(NumberFormat._currencyFormatted(data.totalRebates));
			var totalRevenus = parseFloat(NumberFormat._currencyFormatted(data.totalRevenus));
			totalRebates = parseFloat(NumberFormat._currencyFormatted(totalRebates+totalRevenus));
			$('#marketingAnalytics').highcharts({
				chart : {
				},
				title : {
					text : '网站盈亏  / 月'
				},
				subtitle : {
					text : "投注总金额   - 返奖总金额  -  用户返点总额  =  盈亏值"
				},
				xAxis : {
					categories : dateArr
				},
				yAxis : {
					title : {
						text : "金额"
					}
				},
				credits : {
					enabled : false
				},
				plotOptions : {
					line : {
						dataLabels : {
							enabled : true
						},
						enableMouseTracking : false
					}
				},
				series : [{
					type : 'line',
					name : '盈亏值',
					data : ykArr 
				}, {
					type : 'pie',
					name : '总额',
					data : [{
						name : '投注',
						y : totalBet,
					}, {
						name : '返奖',
						y : totalAward,
					}, {
						name : '返点',
						y : totalRebates,
					}],
					center : [650, -20],
					size : 80,
					showInLegend : false,
					dataLabels : {
						enabled : false
					}
				}]
			});
		},
		queryMarketStasData:function(pageNum){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			attrs.push("pageNum");
			attrs.push("rsvst1");
			var vals = new Array();
			vals.push($("#st1").val());
			vals.push($("#et1").val());
			vals.push(pageNum);
			vals.push("true");
			formPost.submitAjaxForm("statistic/queryMarketStasData.do",attrs,vals,this.mqCallback);
		},
		mqCallback:function(data){
			//$("#queryDate").text($("._endTime").val());
			$("#register").text(parseInt(data.register));
			$("#recharge").text(parseInt(data.recharge));
			$("#firstRc").text(parseInt(data.firstRc));
			$("#drawingCount").text(parseInt(data.drawingCount));
			$("#drawingTimes").text(parseInt(data.drawingTimes));
			$("#hot").text(parseInt(data.hot));
			$("#rcAmount").text(data.rcAmount);
			$("#dwAmount").text(data.dwAmount);
			$("#betCount").text(parseInt(data.betCount));
			$("#awardCount").text(parseInt(data.awardCount));
			$("#rebatesAmount").text(data.rebatesAmount);
			$("#betAmount").text(data.betAmount);
			$("#awardAmount").text(data.awardAmount);
			$("#revenusAmount").text(data.revenusAmount);
			$("#cashAmount").text(data.cashAmount);
			
			var ba = parseFloat(data.betAmount);
			var aa = parseFloat(data.awardAmount);
			var ra = parseFloat(data.revenusAmount);
			var rb = parseFloat(data.rebatesAmount);
			var ca = parseFloat(data.cancelAmount);
			var wpa = ba - aa - ra - rb - ca;
			$("#webProfitAmount").html(wpa);
			$("#rechargeTb").empty();
			if(data.rcList==undefined){
				$("#pageNo").html(0);
				$("#totalPage").html(0);
				return;
			}
			var tp = data.pageCount;
			var pn= data.pageNum;
			$("#pageNo").html(pn);
			$("#totalPage").html(tp);
			var $hidTr = $("._hidTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<data.rcList.length;i++){
				var rc = data.rcList[i];
				$hidTr.find("._time").html(rc.createTime);
				if(rc.rsvst3=="frc"){
					$hidTr.find("._times").html("首充");
				}else{
					$hidTr.find("._times").html(rc.rsvdc2);
				}
				$hidTr.find("._cn").html(rc.rsvst1);
				$hidTr.find("._rebeats").html(rc.rsvdc4);
				$hidTr.find("._fn").html(rc.rsvst2);
				$hidTr.find("._ct").html(rc.rsvst4);
				$hidTr.find("._oa").html(rc.orderAmount);
				$hidTr.find("._cash").html(rc.rsvdc5);
				$hidTr.find("._oid").val(rc.id);
				var rcType = "第三方充值";
				if(rc.orderDetailType==18009){
					rcType = "银行卡转账";
				}else if(rc.rsvst5.indexOf("hx:")!=-1){
					rcType +="(环讯)";
				}else if(rc.rsvst5.indexOf("yb:")!=-1){
					rcType +="(易宝)";
				}
				$hidTr.find("._rct").val(rcType);
				
				$("#rechargeTb").append($hidTr.clone(true));
			}
			$("._amount").formatCurrency();
			
			$("._queryDetail").on('click',function(){
				var action =ctx+"/showRechargeOrderInfo.do?id="+$(this).next().val();
				window.open(action);
			});
		},
		prevQuery:function(){
			var pageNo = parseInt($("#pageNo").html());
			if(pageNo<=1){
				formPost.showErrorMessage("当前页已是第一页！");
				return;
			}else{
				this.queryMarketStasData(pageNo-1);
			}
		},
		nextQuery:function(){
			var totalPage = parseInt($("#totalPage").html());
			var pageNo = parseInt($("#pageNo").html());
			if(pageNo>=totalPage){
				formPost.showErrorMessage("当前页已是最后一页！");
				return;
			}else{
				this.queryMarketStasData(pageNo+1);
			}
		},
		downloadMarketData:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			vals.push($("#st1").val());
			vals.push($("#et1").val());
			formPost.submitForm("downloadMarketData.do",attrs,vals);
		}
	};
}();
