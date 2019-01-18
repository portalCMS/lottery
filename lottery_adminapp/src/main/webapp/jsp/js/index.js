$(document).ready(function() {
	$("#addTaskBtn").click(function() {
		$("#task_modal").modal("show");
	});
	$(".task_detail_btn").click(function() {
		$("#task_detail_modal").modal("show");
	});

	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});
	adminIndex.querySalesData();
	adminIndex.queryNotices(1);
	adminIndex.queryFeedbacks(1);
	adminIndex.queryOnlineUsers();
});

var adminIndex = function(){
	var colors = Highcharts.getOptions().colors, 
	name = '彩种投注数据';
	return{
		querySalesData:function(){
			formPost.submitAjaxForm("querySalesData.do",null,null,this.showDatas);
		},
		showDatas:function(data){
			groupCategories = new Array();
			data0 = new Array();
			
			for(var i=0;i<data.groupMaps.length;i++){
				groupCategories.push(data.groupMaps[i].key);
			}
			
			for(var i=0;i<data.groupMaps.length;i++){
				var obj = new Object();
				obj.color = colors[i];
				obj.name = name;
				//设置对应的彩种组的总金额y
				for(var j=0;j<data.lotGroupMaps.length;j++){
					if(data.lotGroupMaps[j].key == data.groupMaps[i].key){
						obj.y =Number(data.lotGroupMaps[j].value);
						var subdrilldown = new Object();
						
						subdrilldown.name = data.groupMaps[i].key;
						subdrilldown.categories = data.groupMaps[i].value.split(",");
						subdrilldown.color = colors[i];
						var data1 = new Array();
						for(var k =0 ;k<subdrilldown.categories.length;k++){
							for(var l=0;l<data.lotMaps.length;l++){
								if(subdrilldown.categories[k] ==data.lotMaps[l].key){
									data1.push(Number(data.lotMaps[l].value));
									break;
								}
							}
						}
						subdrilldown.data = data1;
						
						obj.drilldown =subdrilldown; 
						break;
					}
				}
				
				data0.push(obj);
			}

			var chart = $('#lottery_log').highcharts(
					{
						chart : {
							type : 'column'
						},
						title : {
							text : '彩种月销售数据【元】'
						},
						xAxis : {
							categories : groupCategories
						},
						yAxis : {
							title : {
								text : '销售金额'
							}
						},
						plotOptions : {
							column : {
								cursor : 'pointer',
								point : {
									events : {
										click : function() {
											var drilldown = this.drilldown;
											if (drilldown) {// drill
												// down
												setChart(
														drilldown.name,
														drilldown.categories,
														drilldown.data,
														drilldown.color);
											} else {// restore
												setChart(
														name,
														groupCategories,
														data0);
											}
										}
									}
								},
								dataLabels : {
									enabled : true,
									color : colors[0],
									style : {
										fontWeight : 'bold'
									},
									formatter : function() {
										return this.y;
									}
								}
							}
						},
						tooltip : {
							formatter : function() {
								var point = this.point, s = this.x
										+ ':<b>'
										+ this.y
										+ '</b><br>';
								if (point.drilldown) {
									s += '点击查看'
											+ point.category
											+ '具体彩种详情';
								} else {
									s += '返回彩种组数据';
								}
								return s;
							}
						},
						series : [ {
							name : name,
							data : data0,
							color : 'white'
						} ],
						exporting : {
							enabled : false
						}
					}).highcharts();
			
			function setChart (name, categories, data, color) {
				chart.xAxis[0].setCategories(categories, false);
				chart.series[0].remove(false);
				chart.addSeries({
					name : name,
					data : data,
					color : color || 'white'
				}, false);
				chart.redraw();
			}
		},
		queryNotices : function(pageNum){
			var attrs = new Array();
			attrs.push("pageNum");
			var vals = new Array();
			vals.push(pageNum);
			formPost.submitAjaxForm("article/queryNotice.do",attrs,vals,this.showNotics);
		},
		showNotics : function(data){
			$("._notices").html("");
			var $hidLi = $("._hidLi:last").clone(true);
			$hidLi.show();
			for(var i=0;i<data.page.entitylist.length;i++){
				var notice = data.page.entitylist[i];
				$hidLi.find("._title").html(notice.title+"<input type='hidden' value='"+notice.id+"'/>");
				$hidLi.find("._time").html(notice.createTime);
				$("._notices").append($hidLi.clone(true));
			}
			$hidLi.find("._title").unbind();
			$("._notices ._title").on('click',function(){
				adminIndex.showNoticeDetail(this);
			});
			
			adminIndex.buildPageBar(data,"_noticePage");
		},
		showNoticeDetail:function(obj){
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push($(obj).find("input").val());
			formPost.submitForm("article/initUpdateNotice.do",attrs,vals);
		},
		buildPageBar:function(data,area){
			$("."+area+" ._tc span").html(data.page.totalCount);
			var tp = data.page.pageCount;
			var pn= data.page.pageNum;
			$("."+area+" ._pn").text(pn);
			$("."+area+" ._tp").text(tp);
			var pre = 1;
			if(pn>1){
				pre=pn-1;
			}
			var next = 1;
			if(pn>=1&&pn<tp){
				next = pn+1;
			}else if(pn>=tp){
				next = tp;
			}
			
			if(area=="_feedbackPage"){
				$("."+area+" ._prePage").attr('href','javascript:adminIndex.queryFeedbacks('+pre+');');
				$("."+area+" ._nextPage").attr('href','javascript:adminIndex.queryFeedbacks('+next+');');
			}else{
				$("."+area+" ._prePage").attr('href','javascript:adminIndex.queryNotices('+pre+');');
				$("."+area+" ._nextPage").attr('href','javascript:adminIndex.queryNotices('+next+');');
			}
		},
		queryOnlineUsers:function(){
			$('#user_stat').highcharts(
					{
						chart : {
							type : 'spline',
							animation : Highcharts.svg, 
							marginRight : 10,
							events : {
								load : function() {
									var series = this.series[0];
									setInterval(function() {
										var x = (new Date()).getTime(), // current  time
										y = onlinePeoPle.getCustomerCount();
										series.addPoint([ x, y ], true,true);
										$("#onLineUserNames").val(onlinePeoPle.getOnLineUserNames());
									}, 15000);
								}
							}
						},
						title : {
							text : '用户在线数'
						},
						xAxis : {
							type : 'datetime',
							tickPixelInterval : 150
						},
						yAxis : {
							title : {
								text : '人数'
							},
							plotLines : [ {
								value : 0,
								width : 2,
								color : '#808080'
							} ]
						},
						tooltip : {
							formatter : function() {
								return '<b>'
										+ this.series.name
										+ '</b><br>'
										+ Highcharts.dateFormat(
												'%Y-%m-%d %H:%M:%S',
												this.x)
										+ '<br>'
										+ Highcharts.numberFormat(
												this.y, 2);
							}
						},
						legend : {
							enabled : false
						},
						exporting : {
							enabled : false
						},
						series : [ {
							name : '在线人数',
							data : (function() {
								// generate an array of
								// random data
								var data = [], time = (new Date())
										.getTime(), i;
								for (i = -19; i <= 0; i++) {
									data.push({
										x : time + i * 1000,
										y :onlinePeoPle.getCustomerCount()
									});
								}
								return data;
							})()
						} ]
					});
		},
		queryFeedbacks:function(pageNum){
			var attrs = new Array();
			attrs.push("pageNum");
			var vals = new Array();
			vals.push(pageNum);
			//formPost.submitAjaxForm("article/queryFeedbacks.do",attrs,vals,this.showFeedbacks);
		},
		showFeedbacks:function(data){
			$("#fbTbody").html("");
			var $hidTr = $("._hidTr:last").clone(true);
			$hidTr.show();
			for(var i=0;i<data.page.entitylist.length;i++){
				var fb = data.page.entitylist[i];
				$hidTr.find("._index").html(i+1);
				$hidTr.find("._time").html(fb.createTime);
				$hidTr.find("._typeName").html(fb.typeName);
				$hidTr.find("._user").html(fb.customerName);
				$hidTr.find("._url").html(fb.pageName);
				$hidTr.find("._content").html(fb.dsce);
				$("#fbTbody").append($hidTr.clone(true));
			}
			
			adminIndex.buildPageBar(data,"_feedbackPage");
			$("._count").html(data.page.totalCount);
		}
	};
}();
