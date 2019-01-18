$(document).ready(function() {
	$("._strTime").bind("click",function(){ 
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("._qbtn").click(function(){
		activiList.queryActivity();
	});
});

var activiList = function(){
	var callFuc = {
		query : function(data){
			var $tbody = $("#_tbody");
			$tbody.html("");
			var $tr = $("#_tr").clone(true);
			$tr.show();
			var pagelist = data.page.pagelist;
			for(var i=0;i<pagelist.length;i++){
				var obj = pagelist[i];
				var index = i+1;
				if(data.page.pageNum>1){
					index = data.page.pageNum*10+(i+1);
				}
				$tr.find("td").eq(0).html(index);
				var mode = obj.mode;
				if(mode=="HAND")mode="手动";
				if(mode=="AUTO")mode="自动";
				if(mode=="HAUTO")mode="半自动";
				$tr.find("td").eq(1).html(mode);
				var type = obj.type;
				$tr.find("td").eq(2).html(actityType(type)[0]);
				$tr.find("td").eq(3).html(obj.starttime);
				$tr.find("td").eq(4).html(obj.endtime);
				$tr.find("td").eq(5).html(obj.title);
				$tr.find("td").eq(6).html(obj.customerCount);
				if(type=="FRC"){
					$tr.find("td").eq(7).html("<a href='updatefrecTempl.do?id="+obj.id+"&type=0'>修改活动</a>&nbsp;&nbsp;<a href='javascript:;'>活动详情</a>");
				}else if(type=="REG"){
					$tr.find("td").eq(7).html("<a href='updateRegTempl.do?id="+obj.id+"&type=0'>修改活动</a>&nbsp;&nbsp;<a href='javascript:;'>活动详情</a>");
				}else if(type=="TRC" || type=="BET" || type=="AWA"){
					$tr.find("td").eq(7).html("<a href='updateBetTempl.do?id="+obj.id+"&type=0'>修改活动</a>&nbsp;&nbsp;<a href='javascript:;'>活动详情</a>");
				}
				$tbody.append($tr.clone(true));
			}
			showPageInfo("pageInfo",data.page.pageNum,data.page.maxCount,data.page.pageCount);
		}
	};
	var actityType = function(typeCode){
		var typeName = "";
		typeName = $("#type option").map(function(){
			if($(this).val()==typeCode){
				return $(this).text();
			}
		});
		return typeName;
	};
	var showPageInfo = function (divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:orders.queryRechargeOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:orders.queryRechargeOrders("+(pageNum+1)+")'>下一页</a></li>";
		$("#"+divId).html(html);
	};
	var packageData = function(attrs,values){
		$("._param").each(function(){
			attrs.push($(this).attr("id"));
			values.push($(this).val());
		});
	};
	return {
		queryActivity:function(page){
			if(page == undefined) page=1;
			var attrs = new Array();
			var values = new Array();
			packageData(attrs,values);
			attrs.push("pageNum");
			values.push(page);
			formPost.submitAjaxForm("activity/queryActivity.do",attrs,values,callFuc["query"]);
		}
	};
}();