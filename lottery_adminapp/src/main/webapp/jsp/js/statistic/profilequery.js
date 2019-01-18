var profileQuery = function(){
	return{
		saveBigUserConfig:function(){
			var attrs = new Array();
			attrs.push("name");
			attrs.push("key1");
			attrs.push("key2");
			attrs.push("key3");
			var vals = new Array();
			vals.push("bigUserConfig");
			vals.push($("#limitDays").val());
			vals.push($("#limitAmount").val());
			vals.push($("#reportDay").val());
			formPost.submitAjaxForm("statistic/saveBigUserConfig.do",attrs,vals,this.saveSuccess);
		},
		saveSuccess:function(data){
			messenger.cofirmMsg(data.successMsg+"是否重新查询？",profileQuery.reQuery);
		},
		reQuery:function(){
			formPost.submitForm("profilequery.do",null,null);
		},
		queryAmpuntPage:function(pageNo){
			var attrs = new Array();
			attrs.push("pageNum");
			var vals = new Array();
			vals.push(pageNo);
			formPost.submitAjaxForm("statistic/queryAmpuntPage.do",attrs,vals,this.pageCallback);
		},
		pageCallback:function(data){
			$("#pageUl").empty();
			var $hidLi = $("._hidLi:last").clone(true);
			$hidLi.show();
			for(var i=0;i<data.nameList.length;i++){
				var name =data.nameList[i];
				var uid = data.userIds[i];
				var rebates =data.rebatesList[i];
				rebates = NumberFormat.formatNumber(rebates,1,1);
				var realBetAmount =data.realbaList[i];
				$hidLi.find("._index").html(i+1);
				$hidLi.find("._name").html(name);
				$hidLi.find("._rebates").html(rebates+"%");
				$hidLi.find("._betAmount").html(realBetAmount);
				$hidLi.find("._uid").val(uid);
				$("#pageUl").append($hidLi.clone(true));
			}
			$("._hidLi .amount").formatCurrency();
			
			$("#pageNo").html(data.pageNo);
			$("#totalPage").html(data.totalPage);
			
			$("._name").unbind();
			$("._name").on('click',function(){
				$("#userName").val($(this).html().trim());
				$("#userId").val($(this).next().val());
				$("#queryProfile").click();
			});
		},
		queryProfileAmount:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			vals.push($("#startTime1").val());
			vals.push($("#endTime1").val());
			formPost.submitAjaxForm("statistic/queryProfileAmount.do",attrs,vals,this.prfAmountSuccess);
		},
		prfAmountSuccess:function(data){
			var voList = data.voList;
			var len = $("#orderList").find("td._amount").size();
			$("#orderList").find("td._amount").html("0.00");
			for(var j=0;j<voList.length;j++){
				var vo = voList[j];
				var isNewOdt = true;
				for(var i=0;i<len;i++){
					var $td = $("#orderList").find("td._amount").eq(i);
					var orderType = $td.attr("name").trim();
					if(vo.value == orderType){
						$td.html(vo.value2);
						isNewOdt =false;
						break;
					}
				}
				//如果用心的订单类型，则添加td.
				
				if(isNewOdt){
					var tdCount = $("#orderList").find("tr:last").find("td").size();
					if(tdCount>4){
						var $tr = $("<tr>");
						var $td1 = $("<td>");
						$td1.html(vo.value);
						$td1.addClass("_orderType");
						var $td2 = $("<td>");
						$td2.html(vo.value2);
						$td2.addClass("_amount");
						$td2.addClass("text-danger");
						$tr.append($td1);
						$tr.append($td2);
						$("#orderList").append($tr.clone(true));
					}else{
						var $td1 = $("<td>");
						$td1.html(vo.value);
						$td1.addClass("_orderType");
						var $td2 = $("<td>");
						$td2.html(vo.value2);
						$td2.addClass("_amount");
						$td2.addClass("text-danger");
						$("#orderList").find("tr:last").append($td1.clone(true));
						$("#orderList").find("tr:last").append($td2.clone(true));
					}
					
				}
			}
			
			$("#orderList ._amount").formatCurrency();
		},
		/**
		 * 查询个人数据
		 */
		queryProfileData:function(){
			var attrs = new Array();
			attrs.push("rsvst1");
			attrs.push("rsvdc1");
			attrs.push("rsvdc3");
			var vals = new Array();
			vals.push($("#userName").val());
			vals.push($("#reportDay").val());
			vals.push($("#userId").val());
			formPost.submitAjaxForm("statistic/queryProfileData.do",attrs,vals,this.profileDataBack); 
		},
		profileDataBack:function(data){
			var rb = NumberFormat.formatNumber(data.rebates*100,1,1);
			$("#rebates").html(rb+"%");
			$("#fName").html(data.fName);
			$("#userType").html(data.userType);
			$("#totalAmount").html(data.betAmount);
			$("#reportDays").html("统计"+$("#reportDay").val()+"天");
			
			$("#queryUserDetail").unbind();
			$("#queryUserDetail").on('click',function(){
				var action = ctx + "/user/showUserInfo/"+data.mid+".do";
				window.open(action); 
			});
			$("#fName").unbind();
			$("#fName").on('click',function(){
				var action = ctx + "/user/showUserInfo/"+data.fid+".do";
				window.open(action); 
			});
			$("#totalAmount").formatCurrency();
			
			var dateArr = new Array();
			var cashArr = new Array();
			var profitArr = new Array();
			var betArr = new Array();
			var awardArr = new Array();
			if(data.profitList == undefined) return;
			for(var i=data.profitList.length-1;i>=0;i--){
				var p = data.profitList[i];
				dateArr.push(p.key.substring(4));
				cashArr.push(parseFloat(p.value));
				profitArr.push(parseFloat(p.value2));
			}
			//投注和中奖
			for(var i=data.baList.length-1;i>=0;i--){
				var a = data.baList[i];
				betArr.push(parseFloat(a.value));
				awardArr.push(parseFloat(a.value2));
			}
			
			//充值和提款
			var rechargeArr = new Array();
			var drawingArr = new Array();
			for(var i=data.rdList.length-1;i>=0;i--){
				var a = data.rdList[i];
				rechargeArr.push(parseFloat(a.value));
				drawingArr.push(parseFloat(a.value2));
			}
			
			//返点和下级返点
			var rebatesArr = new Array();
			var revenusArr = new Array();
			for(var i=data.brList.length-1;i>=0;i--){
				var a = data.brList[i];
				rebatesArr.push(parseFloat(a.value));
				revenusArr.push(parseFloat(a.value2));
			}
			
			//其他收入和支出
			var otherInArr = new Array();
			var otherOutArr = new Array();
			for(var i=data.otherList.length-1;i>=0;i--){
				var a = data.otherList[i];
				otherInArr.push(parseFloat(a.value));
				otherOutArr.push(parseFloat(a.value2));
			}
			
			$('#yk_analytic').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '个人盈亏概况'
		        },
		        xAxis: {
		            categories: dateArr
		        },
		        yAxis:{
		        	title:{
		        		text:"金额"	
		        	}
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		            name: '盈亏值',
		            data: profitArr
		        },{
		            name: '账户余额',
		            type : 'spline',
		            data: cashArr
		        }]
		    });
			
			$('#roiCount').highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : '账户资金概览 / 日'
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
					pointFormat : '<span style="color:{series.color}">{series.name}</span>: {point.y:.1f} <br>',
					shared : true,
					useHTML : false
				},
				plotOptions : {
					column : {
						pointPadding : 0.2,
						borderWidth : 0
					}
				},
				series : [{
					name : '余额',
					type : 'spline',
					data : cashArr
				}, {
					name : '投注',
					data : betArr
				}, {
					name : '中奖',
					data : awardArr
				}, {
					name : '充值',
					data : rechargeArr
				}, {
					name : '提款',
					data : drawingArr
				}, {
					name : '返点',
					data : rebatesArr
				}, {
					name : '下返',
					data : revenusArr
				}, {
					name : '其他收入',
					data : otherInArr
				}, {
					name : '其他支出',
					data : otherOutArr
				}]
			});
			
			
			//betAanlytics
			var betPlayList = data.betPlayList;
			var betPlayArr = new Array();
			var bpMap = new Map();
			for(var j=0;j<betPlayList.length;j++){
				var bp = betPlayList[j];
				//判断彩种是否已经有map了，没有的话彩新建obj。
				if(bpMap.get(bp.key)==undefined){
					var obj = new Object();
					obj.name = bp.key; 
					bpMap.put(bp.key, obj);
					
					var dArr = new Array();
					
					//玩法和金额
					var pmArr = new Array();
					pmArr.push(parseInt(bp.value));
					pmArr.push(parseFloat(bp.value2));
					pmArr.push(parseInt(bp.value3));
					
					dArr.push(pmArr);
					obj.data = dArr;
				}else{
					var obj = bpMap.get(bp.key);
					var dArr = obj.data ;
					//玩法和金额
					var pmArr = new Array();
					pmArr.push(parseInt(bp.value));
					pmArr.push(parseFloat(bp.value2));
					pmArr.push(parseInt(bp.value3));
					
					dArr.push(pmArr);
					obj.data = dArr;
				}
			}
			
			for(var j=0;j<bpMap.size();j++){
				betPlayArr.push(bpMap.get(bpMap.keySet()[j]));
			}
			var playCountList = data.playCountList;
			//玩法-金额对应的次数map
			var pcMap = new Map();
			//玩法对应的玩法名称map
			var pnMap = new Map();
			for(var j=0;j<playCountList.length;j++){
				var pc = playCountList[j];
				if(pcMap.get(pc.key)==undefined){
					pcMap.put(pc.key, pc.value);
				}
				
				if(pnMap.get(pc.key)==undefined){
					pnMap.put(pc.key, pc.value2);
				}
			}
			
			$('#betAanlytics').highcharts({
				chart : {
					type : 'bubble',
					zoomType : 'xy'
				},
				title : {
					text : '投注玩法分布图'
				},
				yAxis : {
					title : {
						text : '投注金额'
					}
				},
				tooltip : {
					formatter : function() {
						var ba = this.y+"";
						if(ba.indexOf(".")==-1){
							ba = this.y+".0000";
						}else if(ba.length-ba.indexOf(".")<4){
							var len = ba.length-ba.indexOf(".");
							for(var k=0;k<=len;k++){
								ba+="0";
							}
						}
						var key = this.x+"-"+ba;
						var str = '<b>' + this.series.name + '</b><br/>';
						str += pnMap.get(key);
						str += "<br>投注金额:"+this.y;
						str += "<br>投注次数:"+pcMap.get(key);
						return str;
					}
				},series :betPlayArr 
			});
		},
		prevQuery:function(){
			var pageNo = parseInt($("#pageNo").html());
			if(pageNo<=1){
				formPost.showErrorMessage("当前页已是第一页！");
				return;
			}else{
				this.queryAmpuntPage(pageNo-1);
			}
		},
		nextQuery:function(){
			var totalPage = parseInt($("#totalPage").html());
			var pageNo = parseInt($("#pageNo").html());
			if(pageNo>=totalPage){
				formPost.showErrorMessage("当前页已是最后一页！");
				return;
			}else{
				this.queryAmpuntPage(pageNo+1);
			}
		},
		ipPrevQuery:function(){
			var pageNo = parseInt($("#ipPageNo").html());
			if(pageNo<=1){
				formPost.showErrorMessage("当前页已是第一页！");
				return;
			}else{
				this.queryIpLog(pageNo-1);
			}
		},
		ipNextQuery:function(){
			var totalPage = parseInt($("#ipTotalPage").html());
			var pageNo = parseInt($("#ipPageNo").html());
			if(pageNo>=totalPage){
				formPost.showErrorMessage("当前页已是最后一页！");
				return;
			}else{
				this.queryIpLog(pageNo+1);
			}
		},
		downloadExcel:function(){
			var attrs = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			var vals = new Array();
			vals.push($("#startTime1").val());
			vals.push($("#endTime1").val());
			formPost.submitForm("downloadBigUserData.do",attrs,vals);
		},
		queryIpLog:function(pageNo){
			var pageNum = 1;
			var attrs = new Array();
			var vals = new Array();
			attrs.push("pageNum");
			attrs.push("id");
			if(pageNo!=undefined&&pageNo!=null){
				pageNum = pageNo;
			}
			vals.push(pageNum);
			if($("#userId").val()=="")return;
			vals.push($("#userId").val());
			formPost.submitAjaxForm("statistic/getIpLogs.do",attrs,vals,this.showIpLog);
		},
		showIpLog:function(data){
			var $tbody = $("#ipTb");
			$tbody.empty();
			var vos = data.page.entitylist;
			var $tr = $("._hidTr").clone(true);
			var tp = data.pageCount;
			var pn= data.pageNum;
			$("#ipPageNo").html(pn);
			$("#ipTotalPage").html(tp);
			for(var i=0;i<vos.length;i++){
				var obj = vos[i];
				$tr.show();
				$tr.find("td").eq(0).text(obj.id);
				$tr.find("td").eq(1).text(obj.createTime);
				$tr.find("td").eq(2).text(obj.ip);
				$tr.find("td").eq(3).text(obj.ipAddress);
				$tr.find("td").eq(4).text(obj.type);
				$tbody.append($tr.clone(true));
			}
			// 单行表格加背景色
			$('#_tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('#_tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
//			//显示分页选择显示栏
//			pageBarBuid.showPageBar("iplog.initIpLog",pn,tp);
		}
	};
}();

$(document).ready(function(){
	$("#statsMenu li").removeClass("active");
	$("#profile").addClass("active");
	$("._amount").each(function(index){
		if(index!=0){
			$(this).formatCurrency();
		}
	});
	$(".settingModalBtn").click(function(){
		$("#settingModal").modal("show");
	});
	$("#saveConfig").on('click',function(){
		profileQuery.saveBigUserConfig();
		$("#settingModal").modal("hide");
	});
	profileQuery.queryAmpuntPage(1);
	$("#prev").on('click',function(){
		profileQuery.prevQuery();
	});
	$("#next").on('click',function(){
		profileQuery.nextQuery();
	});
	$("#ipPrev").on('click',function(){
		profileQuery.ipPrevQuery();
	});
	$("#ipNext").on('click',function(){
		profileQuery.ipNextQuery();
	});
	$("#queryProfile").on('click',function(){
		profileQuery.queryProfileData();
		//
		profileQuery.queryIpLog(1);
	});
	$("#downloadExcel").on('click',function(){
		profileQuery.downloadExcel();
	});
	$("._startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-#{%d-1}'});
	});
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'});
	});
	
	$("#queryPrfAmount").on('click',function(){
		profileQuery.queryProfileAmount();
	});
});