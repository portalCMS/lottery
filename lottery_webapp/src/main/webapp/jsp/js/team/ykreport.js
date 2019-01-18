$(document).ready(function(){
	$("#ykReport").addClass("current");
	$("#sdate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd'
		});
	});
	$("#edate").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd'
		});
	});
	if ($("#sdate").val() == "") {
        $("#sdate").val(getNowDate(0, -1));
        $("#edate").val(getNowDate(0, 0));
    }
	$("#queryOrder").bind("click",function(){
		ykreport.showReport(1,"total","desc");
	});
	$("input[type='radio']").click(function(){
		if($(this).val() == '1'){
			$("#sdate").val(getNowDate(0, -1)).removeAttr("disabled");
		    $("#edate").val(getNowDate(0, 0)).removeAttr("disabled");
		    $("._createTime").show();
		    $("._type").hide();
		    $("._saleAmount").html("实际销售总额");
		    $("._ykAmount").html("游戏总盈亏");
		    ykreport.initResultMsg("请选择查询条件进行查询");
		}else{
			if($(this).val() == '2'){
				$("#sdate").val(getNowDate(0, 0)).attr("disabled","disabled");
				$("#edate").val(getNowDate(0, 1)).attr("disabled","disabled");
			}
			else{
				$("#sdate").val(getNowDate(0, 0)).removeAttr("disabled");
				$("#edate").val(getNowDate(0, 1)).attr("disabled","disabled");
			}
			
			$("._createTime").hide();
			$("._type").show();
			$("._saleAmount").html("实际销售总额"+"<a href='javascript:void(0)' sortType='desc' id='saleAmount"+$(this).val()+"'> ▼</a>");
			$("._ykAmount").html("游戏总盈亏"+"<a href='javascript:void(0)' sortType='desc' id='ykAmount"+$(this).val()+"'> ▼</a>");
			ykreport.initResultMsg("请选择查询条件进行查询");
		}
		
		var sortType="";
		var type = $(this).val();
		$("#saleAmount"+type).click(function(){
			sortType=$(this).attr("sortType");
			ykreport.showReport(1,"total",sortType);
			if(sortType =="desc"){
				$("._saleAmount").find("a").attr("sortType","asc").text(" ▲");
			}else{
				$("._saleAmount").find("a").attr("sortType","desc").text(" ▼");
			}
		});
		$("#ykAmount"+type).click(function(){
			sortType=$(this).attr("sortType");
			ykreport.showReport(1,"yk",sortType);
			if(sortType =="desc"){
				$("._ykAmount").find("a").attr("sortType","asc").text(" ▲")
			}else{
				$("._ykAmount").find("a").attr("sortType","desc").text(" ▼");
			}
		});
	});
	
	ykreport.initResultMsg("请选择查询条件进行查询");
});

var ykreport = function(){
	var type='';
	var bckfuc = {
		showBck : function(data){
			formPost.setTbodyObj(null,null);
			var $orderTbody = $("#orderTbody");
			$orderTbody.html("");
			var $tr = $("._hidTr").clone(true);
			var objs='';
			if(type=="1"){
			 objs = data.results;
			}else{
				objs = data.page.pagelist;
			}
			//如果没有数据则页面不显示分页信息。
			if(objs==undefined||objs==null||objs.length==0){
				ykreport.initResultMsg("暂无记录");
				return;
			}
			$tr.show();
			var totalTetAmount=0,rebateAmount=0,saleAmount=0,winAmount=0,ykAmount=0;
			if(type =="1"){
				var order='';
				for(var i=0;i<objs.length;i++){
					order = objs[i];
					$tr.find("td").eq(0).text(order.createUser);
					$tr.find("td").eq(1).text(order.createTime.substring(0,10));
					$tr.find("td").eq(2).text(order.rsvst1);
					$tr.find("td").eq(3).text(order.totalTetAmount);
					$tr.find("td").eq(4).text(order.rebateAmount);
					$tr.find("td").eq(5).text(order.saleAmount);
					$tr.find("td").eq(6).text(order.winAmount);
					$tr.find("td").eq(7).text(order.ykAmount);
					
					totalTetAmount +=parseFloat(order.totalTetAmount);
					rebateAmount +=parseFloat(order.rebateAmount);
					saleAmount +=parseFloat(order.saleAmount);
					winAmount +=parseFloat(order.winAmount);
					ykAmount +=parseFloat(order.ykAmount);
					$orderTbody.append($tr.clone(true));
				}
			}else{
				for(var i=0;i<objs.length;i++){
					var obj = objs[i];
					$tr.find("td").eq(0).text(obj[1]);
					$tr.find("td").eq(1).text('');
					if(obj[6] == '12001'){
						$tr.find("td").eq(2).text('代理');
					}else if(obj[6] == '12002'){
						$tr.find("td").eq(2).text('会员');
					}else{
						$tr.find("td").eq(2).text('测试');
					}
					$tr.find("td").eq(3).text(obj[2]);
					$tr.find("td").eq(4).text((obj[3]*10000000+obj[4]*10000000)/10000000);
					$tr.find("td").eq(5).text((obj[2]*10000000-(obj[3]*10000000+obj[4]*10000000))/10000000);
					$tr.find("td").eq(6).text(obj[5]);
					$tr.find("td").eq(7).text((obj[2]*10000000-(obj[3]*10000000+obj[4]*10000000+obj[5]*10000000))/10000000);
					$orderTbody.append($tr.clone(true));
				}
			}
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			$("._amount").formatCurrency();
			if(type =="2"){
				var tp = data.page.pageCount;
				var pn= data.page.pageNum;
				pageBarBuid.showPageBar("my_bet.showReport",pn,tp);
			}
		}
	};
	
	return{
		initResultMsg:function(text){
			$("#orderTbody").html("");
			$("#orderTbody").html("<tr><td  height='39' align='center' colspan='7' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
			$("#_myPageDiv").html('');
			return;
		},
		showReport:function(pageNum,field,sortType){
			if(contrastTime($("#sdate").val(),$("#edate").val())){
				formPost.showErrorMessage("开始时间不能大于结束时间");
				return false;
			}
			var attrs = new Array();
			var vals = new Array();
			type= $("input[type='radio']:checked").val();
			if(type != "1"){
				attrs.push("pageNum");
				if(undefined==pageNum){
					vals.push("1");
				}else{
					vals.push(pageNum);
				}
			}
			attrs.push("rsvst1");
			vals.push(type);
			
			attrs.push("field");
			vals.push(field);
			
			attrs.push("sortType");
			vals.push(sortType);
			
			attrs.push("startTime");
			attrs.push("endTime");
			attrs.push("uname");
		
			vals.push($("#sdate").val());
			vals.push($("#edate").val());
			vals.push($("#userName").val());
			
			
			formPost.setTbodyObj($("#orderTbody"),$("._hidTr"));
			formPost.submitAjaxForm("team/showYkReport.shtml",attrs,vals,bckfuc["showBck"]);
		}
	};
}();