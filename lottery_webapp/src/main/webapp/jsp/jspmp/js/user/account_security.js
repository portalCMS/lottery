var opennumber = function(){
	return{
		ajaxSubmit:function(pageNum){
			if(undefined==pageNum){
				pageNum="1"
			}else{
				pageNum=pageNum;
			}
			//formPost.submitAjaxForm("user/getIpLogs.shtml",attrs,values,opennumber.showOrders);
		    $.ajax({
		        type: "POST",
		        url: "user/getIpLogs.shtml",
		        data: {pageNum:pageNum},
		        success: function (data) {
		        	msg.load();
		        	opennumber.showOrders(data);
		        }
		    });
		},
		showOrders:function(data){
			var vos = data.page.entitylist;
			var html="";
			for(var i=0;i<vos.length;i++){
				html += "<tr>";
				html += "<td>"+vos[i].id+"</td>";
				html += "<td>"+vos[i].createTime+"</td>";
				html += "<td>"+vos[i].type+"</td>";
				html += "<td><font color='red'>"+vos[i].ip+"</font></td>";
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
		}
	};
}();

$(document).ready(function(){
	opennumber.ajaxSubmit();
});