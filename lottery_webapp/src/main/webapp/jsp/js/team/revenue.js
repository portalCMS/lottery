var revenue = function(){
	return{
		checkForm:function(){
			if($("#sdate").val() == '' || $("#edate").val() == ''){
				formPost.showErrorMessage("请选择时间区间之后进行查询");
				return false;
			}
			return true;
		},
		ajaxSubmit:function(pageNum){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			attrs.push("orderType");
			attrs.push("orderDetailType");
			attrs.push("sdate");
			attrs.push("edate");
			attrs.push("rsvst3");
			attrs.push("rsvst4");
			attrs.push("pageNum");
			
			var values = new Array();
			values.push(14001);
			values.push(18015);
			values.push($("#startTime").val());
			values.push($("#endTime").val());
			values.push($("#userName").val());
			if($("#checkLower").is(":checked")){
				values.push("true");
			}else{
				values.push("false");
			}
			
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			formPost.setTbodyObj($("#orderTbody"),$("._hidTr"));
			formPost.submitAjaxForm("finance/queryRevenueLower.shtml",attrs,values,revenue.showOrders);
		},
		showOrders:function(data){
			formPost.setTbodyObj(null,null);
			$("#orderTbody").html("");
			$("#totalAmount").text("0.0000");
			//如果没有数据则页面不显示分页信息。
			if(data.orders==undefined||data.orders==null||data.orders.entitylist ==null||data.orders.entitylist.length==0){
				$("#orderTbody").html("<tr><td  height='39' align='center' " +
							" colspan='5' align='middle' class='f12 color_8d' >暂无记录<td></tr>");
				$("#_myPageDiv").html("");
				return;
			}
			var tp = data.orders.pageCount;
			var tr= data.orders.totalCount;
			var pn= data.orders.pageNum;
			
			//显示总金额
			$("#totalAmount").text(data.orders.rsvdc1);
			
			var trObj = $("._hidTr").clone(true);
			$(trObj).show();
			
			var tdObjs = $(trObj).find("td");
			for(var i=0;i<data.orders.entitylist.length;i++){
				var order = data.orders.entitylist[i];
				$(tdObjs).eq(0).html(order[1]);
				$(tdObjs).eq(1).find("._user").html(order[8]);
				$(tdObjs).eq(2).html("代理");
				$(tdObjs).eq(3).html(order[4]);
				$(tdObjs).eq(4).addClass("color_red");
				$(tdObjs).eq(4).html(NumberFormat.format_number1(order[2]));
				$("#orderTbody").append($(trObj).clone(true));
			}
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			
			//显示分页选择显示栏
			pageBarBuid.showPageBar("revenue.ajaxSubmit",pn,tp);
			
			//显示总金额
			$("#totalAmount").html(data.orders.rsvdc1);
			
			//$("._amount").formatCurrency();
		}
		
	};
}();

$(document).ready(function(){
	$("#startTime").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
	$("#endTime").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
    $("#startTime").val(getTime(0, 0, 4));
    $("#endTime").val(getTime(0, 1, 4));
    
	$("#queryOrder").click(function(){
		revenue.ajaxSubmit();
	});
	
	$("#revenueMenu").addClass("current");
	
	$(".paging").hide();
	//初始化后查询一次
	revenue.ajaxSubmit(1);
});