$(document).ready(function() {
	normalquery.queryMonthAddUser();
	normalquery.queryMonthActiveUser();
	$("#userExcelDownload").on('click',function(){
		normalquery.downloadUserExcel();
	});
	$("#userAddExcelDownload").on('click',function(){
		normalquery.downloadUserAddExcel();
	});
	$("._startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-#{%d-1}'});
	});
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});
	});
	$("#statsMenu li").removeClass("active");
	$("#normal").addClass("active");
});

var normalquery = function(){
	
	return{
		downloadUserExcel:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			if($("#startTime1").val().trim()!=""&&$("#endTime1").val().trim()!=""){
				if($("#startTime1").val().trim()==$("#endTime1").val().trim()){
					formPost.showErrorMessage("结束日期必须大于开始日期！");
					return;
				}
				vals.push($("#startTime1").val());
				vals.push($("#endTime1").val());
				formPost.submitForm("downloadUserExcel.do",attrs,vals);
			}else{
				formPost.submitForm("downloadUserExcel.do",null,null);
			}
		},
		downloadUserAddExcel:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			if($("#startTime2").val().trim()!=""&&$("#endTime2").val().trim()!=""){
				if($("#startTime2").val().trim()==$("#endTime2").val().trim()){
					formPost.showErrorMessage("结束日期必须大于开始日期！");
					return;
				}
				vals.push($("#startTime2").val());
				vals.push($("#endTime2").val());
				formPost.submitForm("downloadAddUserExcel.do",attrs,vals);
			}else{
				formPost.submitForm("downloadAddUserExcel.do",null,null);
			}
		},
		queryMonthActiveUser:function(){
			formPost.submitAjaxForm("statistic/queryMonthActiveUser.do",null,null,this.hotUserCount);
		},
		hotUserCount:function(data){
			var dayArr = new Array();
			var datas = data.maps;
			var days = data.days;
			for(var i=1;i<days;i++){
				dayArr.push(i);
			}
			var dataArr = new Array();
			for(var i=0;i<datas.length;i++){
				var temp = datas[datas.length-i-1];
				dataArr.push(parseInt(temp.value));
			}
			$('#hotUserCount').highcharts({
				chart : {
					type : 'column',
					margin : [50, 50, 100, 80]
				},
				title : {
					text : '2014/11  活跃度统计/天 '
				},
				xAxis : {
					categories :dayArr 
				},
				yAxis : {
					min : 0,
					title : {
						text : '活跃点'
					}
				},
				tooltip : {
					pointFormat : '共 {point.y}人 投注',
				},
				series : [{
					name : '每天每用户初次投注则记为1个活跃点',
					data : dataArr
				}]
			});
			
			var tmc = parseInt(data.tmc);
			var tpc = parseInt(data.tpc);
			var tc = tmc+tpc;
			$("#totalHotCount").html("&nbsp;"+tc+"&nbsp;");
			$('#userCount').highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : '当月活跃度用户类型占比'
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							format : '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				series : [{
					type : 'pie',
					name : '占比',
					data : [['会员', tmc], ['代理', tpc]]
				}]
			});
		},
		queryMonthAddUser:function(){
			formPost.submitAjaxForm("statistic/queryMonthAddUser.do",null,null,this.addUserCount);
		},
		addUserCount:function(data){
			$("#proxyAdd").html("&nbsp;"+data.ptmc+"&nbsp;");
			$("#selfAdd").html("&nbsp;"+data.stmc+"&nbsp;");
			$("#linkAdd").html("&nbsp;"+data.ltsc+"&nbsp;");
			
			var daypArr = new Array();
			var daymArr = new Array();
			var pdatas = data.pMaps;
			var mdatas = data.mMaps;
			var days = data.days;
			var dayArr = new Array();
			for(var i=1;i<days;i++){
				dayArr.push(i);
			}
			for(var i=0;i<pdatas.length;i++){
				var temp = pdatas[pdatas.length-i-1];
				daypArr.push(parseInt(temp.value));
			}
			
			for(var i=0;i<mdatas.length;i++){
				var temp = mdatas[mdatas.length-i-1];
				daymArr.push(parseInt(temp.value));
			}
			$('#regMap').highcharts({
				title : {
					text : '当月 新增用户数/天',
				},
				xAxis : {
					categories : dayArr
				},
				yAxis : {
					title : {
						text : '人'
					},
					plotLines : [{
						value : 0,
						width : 1,
						color : '#808080'
					}]
				},
				tooltip : {
					valueSuffix : '人'
				},
				legend : {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 0
				},
				series : [{
					name : '代理',
					data : daypArr
				}, {
					name : '会员',
					data : daymArr
				}]
			});
		}
	};
}();
