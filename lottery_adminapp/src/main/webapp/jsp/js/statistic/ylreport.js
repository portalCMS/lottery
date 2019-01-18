$(document).ready(function() {
	$("#ylreport").addClass("active");
	$("#st1").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
	$("#et1").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});
	if ($("#st1").val() == "") {
        $("#st1").val(getNowTime(0, -1));
        $("#et1").val(getNowTime(0, 0));
    }
	$("#queryReport").bind("click",function(){
		ykreport.showReport(1);
	});
	ykreport.showReport(1);
});

var ykreport = function(){
	var uid = 0;
	var bckfuc = {
		showBck : function(data){
			var $orderTbody = $("#orderTbody");
			$orderTbody.html("");
			var $tr = $("._hidTr").clone(true);
			var objs = data.page.pagelist;
			$tr.show();
			$tr.find("td").addClass("_cash");
			for(var i=0;i<objs.length;i++){
				var obj = objs[i];
				$tr.find("td").eq(0).text(obj[1]);
				$tr.find("td").eq(0).removeClass("_cash");
				$tr.find("td").eq(1).text(obj[2]);
				$tr.find("td").eq(2).text((obj[3]*10000000+obj[4]*10000000)/10000000);
				$tr.find("td").eq(3).text((obj[2]*10000000-(obj[3]*10000000+obj[4]*10000000))/10000000);
				$tr.find("td").eq(4).text(obj[5]);
				$tr.find("td").eq(5).text((obj[2]*10000000-(obj[3]*10000000+obj[4]*10000000+obj[5]*10000000))/10000000);
				//$tr.find("td").eq(6).text(obj[6]);
				//$tr.find("td").eq(7).text(obj[7]);
				//$tr.find("td").eq(8).text($tr.find("td").eq(5).text()-obj[6]-obj[7]);
				var $a = $("<a>");
				$a.click(function(){
					uid = $(this).attr("id");
					ykreport.showLowerReport(1,uid);
				});
				$tr.find("td").eq(6).html($a.attr({"href":"javascript:void(0);","id":obj[0]}).text("查看详情"));
				$orderTbody.append($tr.clone(true));
			}
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			//$("._cash").formatCurrency();
			if(uid != 0){
				pageInfo1("_pageInfo",data.page.pageNum,data.page.maxCount,data.page.pageCount,uid);
			}else{
				pageInfo("_pageInfo",data.page.pageNum,data.page.maxCount,data.page.pageCount);
			}
		}
	};
	var pageInfo = function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:ykreport.showReport("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:ykreport.showReport("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	var pageInfo1 = function(divId,pageNum,maxCount,pageCount,uid){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:ykreport.showLowerReport("+(pageNum-1)+","+uid+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:ykreport.showLowerReport("+(pageNum+1)+","+uid+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		showReport:function(pageNum){
			uid = 0;
			var attrs = new Array();
			var vals = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			attrs.push("uname");
			attrs.push("pageNum");
			vals.push($("#st1").val());
			vals.push($("#et1").val());
			vals.push($("#uname").val());
			vals.push(pageNum);
			formPost.submitAjaxForm("statistic/queryykReport.do",attrs,vals,bckfuc["showBck"]);
		},
		showLowerReport:function(pageNum,id){
			var attrs = new Array();
			var vals = new Array();
			attrs.push("startTime");
			attrs.push("endTime");
			attrs.push("uname");
			attrs.push("pageNum");
			attrs.push("uid");
			vals.push($("#st1").val());
			vals.push($("#et1").val());
			vals.push($("#uname").val());
			vals.push(pageNum);
			vals.push(id);
			formPost.submitAjaxForm("statistic/queryykReport.do",attrs,vals,bckfuc["showBck"]);
		}
	};
}();