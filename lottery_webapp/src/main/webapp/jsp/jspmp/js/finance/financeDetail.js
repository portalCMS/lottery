var finance = function(){
	return{
		ajaxSubmit:function(pageNum){
			msg.load();
			if(undefined==pageNum){
				pageNum=1;
			}else{
				
				pageNum=pageNum;
			}
			$.ajax({
				type: "POST",
				url: "queryOrder.shtml",
				dataType: "json",
				data:{orderType:'14002',orderDetailType:'0',pageNum:pageNum,rsvst2:'lotOdt' },
				success: function(data) {
					finance.showOrders(data);
				},
				error: function(){
				}
			})
		},
		showOrders:function(data){
			var vos = data.orders.entitylist;
			var odtMap = new Map();
			$("#lotOdt").find("option").each(
				function (){
					odtMap.put($(this).val(),$(this).attr("alt"));
				}
			);
			var html="";
			if(vos.length==0){
				html="<tr><td colspan='9' align='center' valign='middle' " +
				"style='vertical-align: middle;'>无数据</td></tr>";
				
			}
			for(var i=0;i<vos.length;i++){
				html += "<tr>";
				html += "<td>"+vos[i].orderTime+"</td>";
				html += "<td>"+odtMap.get(vos[i].orderDetailType)+"</td>";
				html += "<td><font color='red'>"+NumberFormat.format_number1(vos[i].orderAmount)+"</font></td>";
				html += "</tr>";
			}
			
			$("._tbody").html(html);
			//显示分页选择显示栏
			finance.pageInfo("_pageInfo",data.pageNum,data.maxCount,data.pageCount);
		},
		pageInfo : function(divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<a href='javaScript:finance.ajaxSubmit("+(pageNum+1)+")' role='button' class='btn btn-disable'>下一页</a><a href='javaScript:finance.ajaxSubmit("+(pageNum-1)+")' role='button' class='btn btn-disable'>上一页</a>";
			$("#pagers").html(html);
		},
		showOrderdetail:function(ordId,action,type){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("rsvst1");
			var values = new Array();
			values.push(ordId);
			values.push(type);
			formPost.submitForm(action,attrs,values);
		}
	};
}();

$(document).ready(function(){
		finance.ajaxSubmit();
});