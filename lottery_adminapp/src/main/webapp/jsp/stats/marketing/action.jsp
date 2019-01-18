<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>

</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_marketing.jsp"%>
			<div class="col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="pull-left">总代盈亏列表&nbsp;<span class="">2014-11-15</span></div>
						<div class="pull-right">
							<!-- 来一个日期到日期的选择框 -->
						</div>
					</div>
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<td>用户名</td>
									<td>投注总额</td>
									<td>总返点</td>
									<td>中奖总额</td>
									<!--  -->
									<td>盈亏值</td>
									<!-- 增加一个分红订单，在后台充值那块 -->
									<td>分红</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><a href="#">李四</a></td>
									<td>12345789.0000</td>
									<td>123456.0000</td>
									<td>456789.0000</td>
									<td>+123456789.0000</td>
									<td>456789.0000</td>
								</tr>
								<tr>
									<td><a href="#">李四</a></td>
									<td>12345789.0000</td>
									<td>123456.0000</td>
									<td>456789.0000</td>
									<td>+123456789.0000</td>
									<td>456789.0000</td>
								</tr>
								<tr>
									<td><a href="#">李四</a></td>
									<td>12345789.0000</td>
									<td>123456.0000</td>
									<td>456789.0000</td>
									<td>+123456789.0000</td>
									<td>456789.0000</td>
								</tr>
								<tr>
									<td><a href="#">李四</a></td>
									<td>12345789.0000</td>
									<td>123456.0000</td>
									<td>456789.0000</td>
									<td>+123456789.0000</td>
									<td>456789.0000</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div id="saleAnalytics"></div>
							</div>
							<div class="col-sm-6">
								<div id="winAnalytics"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div id="shareWin"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<div id="shareWinLog"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$('#saleAnalytics').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: '总代投注总量占比'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '投注占比',
		            data: [
		                ['zhangsan',   1234.0000],
		                ['lisi',       11223],
		                ['wangwu',    255123],
		                ['zhaoliu',     89965],
		                ['wangermazi',   88466]
		            ]
		        }]
		    });
			$('#winAnalytics').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: '总代中奖总量占比'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '投注占比',
		            data: [
		                ['zhangsan',   1234.0000],
		                ['lisi',       11223],
		                ['wangwu',    255123],
		                ['zhaoliu',     89965],
		                ['wangermazi',   88466]
		            ]
		        }]
		    });
			 $('#shareWinLog').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: '盈亏及分红总量历史'
			        },
			        xAxis: {
			            categories: ['2014-12', '2014-12', '2014-12', '2014-12', '2014-12']
			        },
			        yAxis:{
			        	title:{
			        		text:"金额"
			        	}
			        },
			        tooltip: {
			    	    pointFormat: '{series.name}: <b>{point.y} 元</b>'
			        },
			        credits: {
			            enabled: false
			        },
			        series: [{
			        	type: "column",
			            name: '投注总数',
			            data: [5, 3, 4, 7, 2]
			        }, {
			            name: '分红总数',
			            type: "column",
			            data: [2, -2, -3, 2, 1]
			        }, {
			        	type: "spline",
			            name: '中奖总数',
			            data: [3, 4, 4, -2, 5]
			        }, {
			        	type: "column",
			            name: '返点总数s',
			            data: [3, 4, 4, -2, 5]
			        }]
			    });
		});
	</script>
</body>
</html>