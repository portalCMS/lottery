var superQuery = function(){
	var callFuc = {
		initData:function(data){
			var $tbody = $("._tbody");
			$tbody.html("");
			var $ctr = $("#ctr").clone(true);
			$ctr.show();
			var objs = data.data.entitylist;
			for(var i=0;i<objs.length;i++){
				var newTr = objs[i];
				$ctr.find("td").eq(0).html(i+1);
				$ctr.find("td").eq(1).html(newTr[15]);
				$ctr.find("td").eq(2).html(newTr[2]);
				$ctr.find("td").eq(3).html(newTr[3]);
				$ctr.find("td").eq(4).find("a").html(newTr[5]).attr("href",ctx+"/user/showUserInfo/"+newTr[4]+".do");
				if(newTr[16] == 12002){
					$ctr.find("td").eq(5).html("会员["+newTr[17]*1000/10+"%]");
				}else{
					$("#types").find("option").each(function(){
						if($(this).val()==newTr[6]){
							$ctr.find("td").eq(5).html($(this).text()+"["+newTr[17]*1000/10+"%]");
							return;
						}
					});
				}
				$ctr.find("td").eq(6).html(newTr[7]+"人/"+newTr[8]+"个");
				var stastr = "";
				if(newTr[9]>0)stastr = "<span class=\"text-success\">绑卡</span>";
				if(newTr[10]>0)stastr+= "&nbsp;<span class=\"text-success\">分配卡</span>";
				$ctr.find("td").eq(7).html(stastr);
				$ctr.find("td").eq(8).html(NumberFormat.format_number1(newTr[11],4));
				$ctr.find("td").eq(9).html(NumberFormat.format_number1(newTr[12],4));
				$ctr.find("td").eq(10).html(NumberFormat.format_number1(newTr[13],4));
				$ctr.find("td").eq(11).html(NumberFormat.format_number1(newTr[14],4));
				$ctr.find("td").eq(12).find("a").eq(0).attr("href",ctx+"/user/showUserInfo/"+newTr[0]+".do");
				$ctr.find("td").eq(12).find("a").eq(1).attr("href",ctx+"/showRecords.do?uName="+newTr[3]);
				$ctr.find("td").eq(12).find("a").eq(2).attr("href",ctx+"/showOrders.do?customerName="+newTr[3]);
				$tbody.append($ctr.clone(true));
			}
			pageInfo("pager", data.data.pageNum, data.data.maxCount, data.data.pageCount);
			var total = data.data.cells;
			for(var i = 0;i<total.length;i++){
				if(total[0]==0)break;
				var obj = $("._total").eq(i);
				if(obj.attr("class").indexOf("_money")>-1){
					obj.html(NumberFormat.format_number1(total[i],4));
				}else{
					obj.html(total[i]);
				}
			}
		}
	};
	var packageParam = function(attrs,values){
		$("._param").each(function(){
			if($(this).attr("type")=="checkbox"&&$(this).prop("checked")==true){
				attrs.push($(this).attr("name"));
				values.push($(this).val());
			}else if($(this).attr("type")!="checkbox"){
				attrs.push($(this).attr("name"));
				values.push($(this).val());
			}
		});
	};
	var pageInfo=function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:superQuery.getData("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:superQuery.getData("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		getData:function(page){
			var pageNum = 1;
			if(page!=undefined&&page!="")pageNum = page;
			var attrs = new Array();
			var values = new Array();
			packageParam(attrs,values);
			attrs.push("pageNum");
			values.push(pageNum);
			formPost.submitAjaxForm("statistic/getSQData.do",attrs,values,callFuc["initData"]);
		},
		getDownLoad:function(){
			var attrs = new Array();
			var values = new Array();
			packageParam(attrs,values);
			formPost.submitForm("downloadSuperQueryExcel.do",attrs,values);
			this.getData(1);
		}
	};
}();

$(document).ready(function(){
//	if($("._startTime").val()==""){
//		$("._startTime").attr("readonly",true);
//		$("._startTime").val(getNowTime(0, -30));
//		$("._endTime").attr("readonly",true);
//		$("._endTime").val(getNowTime(0, 0));
//	}
	$("._startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#secBtn").click(function(){
		superQuery.getData(1);
	});
	$("#downLoadBtn").click(function(){
		superQuery.getDownLoad();
	});
	superQuery.getData(1);
	$("#statsMenu li").removeClass("active");
	$("#super").addClass("active");
});