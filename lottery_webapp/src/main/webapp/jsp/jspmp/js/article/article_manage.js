var noticelist = function(){
	var callBackFuc = {
		"initCallBack":function(data){
			var vos = data.page.pagelist;
			var tempstr= $("#pidstr").html();
			var html=""
				for(var i=0;i<vos.length;i++){
					html+="<a href='showDetail.html?id="+vos[i].id+"'><h3><span>["+vos[i].detailTypeName+"] </span>"+vos[i].title+"</h3></a>" +
						  "<p id='pidstr'>"+vos[i].content.substring(0,40)+"</p>" +
						  "<time>"+vos[i].createTime+"</time>";
				}
			$(".board-item").html(html);
		}
	};
	return{
		init:function(pageNum){
			msg.load();
		    $.ajax({
		        type: "POST",
		        url: "queryNotices.shtml",
		        data: {pageNum:pageNum},
		        dataType: "json",
		        success: function (data) {
		        	callBackFuc.initCallBack(data);
					//显示分页选择显示栏
				    noticelist.pageInfo("pager",data.pageNum,data.maxCount,data.pageCount);
		        }
		    });

		},
		pageInfo : function(divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<a href='javaScript:noticelist.init("+(pageNum+1)+")' role='button' class='btn btn-disable'>下一页</a><a href='javaScript:noticelist.init("+(pageNum-1)+")' role='button' class='btn btn-disable'>上一页</a>";
			$("."+divId).html(html);
		}
	};
}();

$(document).ready(function(){
	noticelist.init(1);
});