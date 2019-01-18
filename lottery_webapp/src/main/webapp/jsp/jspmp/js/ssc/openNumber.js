var opennumber = function(){
	return{
		ajaxSubmit:function(pageNum){
			msg.load();
			var lotteryCode=$("#lotteryCode").val();
			if(undefined==pageNum){
				pageNum="1";
			}
			$.ajax({
				type: "POST",
				url: "showrecord.shtml",
				dataType: "json",
				data:{lotteryCode: lotteryCode,pageNum:pageNum },
				success: function(data) {
					opennumber.showOrders(data);
				}
			})
		},
		showOrders:function(data){
			var vos = data.record.entitylist;
			var html="";
			if(vos.length==0){
				html="<tr><td colspan='9' align='center' valign='middle' " +
				"style='vertical-align: middle;'>无数据</td></tr>";
			}
			var j=0;
			for(var i=0;i<vos.length;i++){
				html += "<tr>";
				html += "<td>"+vos[i].startTime+"</td>";
				html += "<td>"+vos[i].issue+"</td>";
				 switch(vos[i].status)
				 {
				 case 10005:
				 case 10013:
					 html += "<td><font color='red'>"+vos[i].lotteryNumber+"</font></td>";
					break;
				 case 10006:
				 case 10012:
					 html += "<td><font color='red'>开奖失败</font></td>";
				   break;
				 case 10007:
				 case 10010:
				 case 10011:
					 html += "<td><font color='red'>正在开奖</font></td>";
				   break;
				 default:
					 html += "<td><font color='red'>等待开奖</font></td>";
				 }
				
				html += "</tr>";
			}
			
			$("._tbody").html(html);
			//显示分页选择显示栏
			opennumber.pageInfo("pager",data.pageNum,data.maxCount,data.pageCount);
		},
		pageInfo : function(divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<a href='javaScript:opennumber.ajaxSubmit("+(pageNum+1)+")' role='button' class='btn btn-disable'>下一页</a><a href='javaScript:opennumber.ajaxSubmit("+(pageNum-1)+")' role='button' class='btn btn-disable'>上一页</a>";
			$("."+divId).html(html);
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
	opennumber.ajaxSubmit();
});