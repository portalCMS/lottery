var card = function(){
	
	var callBackFuc = {
		"userInfo":function(data){
			var $tbody = $("#tbody");
			$tbody.html();
			var $tr = $("#ctr").clone(true);
			$tr.show();
			var userInfos = data.userInfo.entitys;
			for(var i=0;i<userInfos.length;i++){
				var obj = userInfos[i];
				$tr.find("td").eq(0).text(i+1+(data.userInfo.pageNum==1?0:data.userInfo.pageNum)*10);
				$tr.find("td").eq(1).text(obj[1]);
				var porxy = "";
				if(obj[2]==0){
					porxy="总代";
				}else{
					porxy = obj[2]+"级代理";
				}
				$tr.find("td").eq(2).text(porxy);
				var prop = "继承绑定";
				if(obj[3]==15001)prop = "独立绑定";
				$tr.find("td").eq(3).text(prop);
				$tr.find("td").eq(4).find("label").text(obj[4]);
				var money = obj[5];
				if(money==null)money = 0;
				$tr.find("td").eq(5).text(money);
				var stauts = "正常";
				if(obj[6]==10001)stauts="停用";
				$tr.find("td").eq(6).text(stauts);
				$tr.find("td").eq(7).find("a").attr("href","user/showUserInfo/"+obj[0]+".do");
				$tbody.append($tr.clone(true));
			}
			pageInfo("_page",data.userInfo.pageNum,10,data.userInfo.totalCount);
		}
	};
	pageInfo=function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:Orders.queryOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页</li><li><a href='javaScript:Orders.queryOrders("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		getCardUserInfo:function(num){
			var pageNum = 1;
			if(num!=undefined)pageNum = num;
			var attrs = new Array();
			var vals = new Array();
			attrs.push("bankcardId");
			attrs.push("pageNum");
			vals.push($("#cardId").val());
			vals.push(pageNum);
			formPost.submitAjaxForm("getCardBindUserInfo.do",attrs,vals,callBackFuc["userInfo"]);
		}
	};
}();

$(document).ready(function(){
	card.getCardUserInfo(0);
});