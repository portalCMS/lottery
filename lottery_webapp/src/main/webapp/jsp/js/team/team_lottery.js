var betRecord = function(){
	var titleName = "圈子投注记录";
	var _lotteryCode ="";
	var playModelsCallBack = function(data){
		var playModels = data.lotteryType.playModelVOs;
		var html = "<option value=\"\">全部玩法</option>";
		for(var i=0;i<playModels.length;i++){
			html+="<option value=\""+playModels[i].modelCode+"\">"+playModels[i].modelName+"</option>";
		}
		$("._playmodel").html(html);
		
		var lotterySeries = data.lotteryType.currentIssueNo;
		html ="<option value=''>全部奖期</option>";
		if(lotterySeries ==''){
			$("._issueNo").html(html);
			return;
		}
		if(_lotteryCode == "50001" || _lotteryCode =="50002"){
			countSeries = parseInt(lotterySeries.substring(2));
		}else{
			countSeries = parseInt(lotterySeries.substring(6));
		}
		
		var total = 0;
		for(var i = countSeries-1;i>0;i--){
			 total++;
			 lotterySeries = parseInt(lotterySeries)-1;
			 html+="<option value='"+lotterySeries+"'>"+lotterySeries+"</option>";
			 if(total >120){
				 break;
			 }
		}
		$("._issueNo").html(html);
	};
	
	var initDataCallBack = function(data){
		formPost.setTbodyObj(null,null);
		$("#brdata").html("");
		//如果没有数据则页面不显示分页信息。
		if( data.page==undefined|| data.page==null||data.page.pagelist.length==0){
			$("#brdata").html("<tr><td  height='39' align='center' " +" colspan='10' align='middle' class='f12 color_8d' >暂无记录<td></tr>");
			$("#_myPageDiv").html("");
			return;
		}
		var tp = data.page.pageCount;
		var pn= data.page.pageNum;
		var trObj = $("._trclone").clone(true);
		var vos = data.page.pagelist;
		
		for(var i=0;i<vos.length;i++){
			var betRecord = vos[i];
			$(trObj).find("td").eq(0).html(betRecord.createTime);
			$(trObj).find("td").eq(1).html(betRecord.lotteryCode);
			$(trObj).find("td").eq(2).html(betRecord.playCode+"<span class='color_blue'>("+betRecord.selectCodeName+")</span>");
			var text = betRecord.betNum;
			if(text.length > 12){
				text = text.substring(0,12)+"...";
			}
			$(trObj).find("td").eq(3).html("<a href=\"javascript:void(0);\" class=\"color_blue _betNum\">"+text+"</a>");
			$(trObj).find("td").eq(3).attr("title",betRecord.betNum);
			if(betRecord.betModel == 1){
				$(trObj).find("td").eq(4).html("元模式");
			}else if(betRecord.betModel == 1){
				$(trObj).find("td").eq(4).html("角模式");
			}else{
				$(trObj).find("td").eq(4).html("分模式");
			}
			$(trObj).find("td").eq(5).html(betRecord.multiple+"倍");
			$(trObj).find("td").eq(6).addClass("color_red _amount").html(betRecord.betMoney*betRecord.multiple);
			$(trObj).find("td").eq(7).html(betRecord.opernBetNumber);
			if(betRecord.betStatus==21001){
				$(trObj).find("td").eq(8).html("投注成功");
			}else if(betRecord.betStatus==21002){
				$(trObj).find("td").eq(8).html("<span class=\"correctHint\">已中奖</span>" +
						"<p class=\"color_red _amount\">"+betRecord.winMoney+"</p>");
			}else if(betRecord.betStatus==21003){
				$(trObj).find("td").eq(8).html("未中奖");
				$(trObj).find("td").eq(8).addClass("color_8d");
			}else if(betRecord.betStatus==21004){
				$(trObj).find("td").eq(8).html("<del class=\"color_8d\">已撤单</del>");
			}
			$(trObj).find("td").eq(9).find("input").val(betRecord.id);
			$("#brdata").append($(trObj).clone(true).show());
		}
		$("._betNum").unbind();
		$("._betNum").click(function(){
			formPost.showInfoTab('投注号码明细',$(this).parent().attr("title"));
		});
		// 单行表格加背景色
		$('.accountTab tbody tr:odd').find("td").css({
			'background-color' : '#FFF'
		});
		$('.accountTab tbody tr:even').find("td").css({
			'background-color' : '#f6f6f6'
		});
		
		//显示分页选择显示栏
		pageBarBuid.showPageBar("betRecord.initData",pn,tp);
		$("._amount").formatCurrency();
	};
	var packageSecParam = function(attrs,values){
		$("._params").each(function(){
			if($(this).attr("type")=="checkbox"){
				if($(this).prop("checked")==true){
					attrs.push($(this).attr("id"));
					values.push($(this).val());
				}else{
					attrs.push($(this).attr("id"));
					values.push(0);
				}
			}else{
				attrs.push($(this).attr("id"));
				values.push($(this).val());
			}
		});
	};
	return{
		checkForm:function(){
			if($("#sdate").val() == '' || $("#edate").val() == ''){
				formPost.showErrorMessage("请选择时间区间之后进行查询");
				return false;
			}
			return true;
		},
		initPlayModel : function(lotteryCode){
			if(lotteryCode==""){
				var html = "<option value=\"\">全部玩法</option>";
				$("._playmodel").html(html);
			}else{
				var attrs = new Array();
				var values = new Array();
				attrs.push("lotteryCode");
				values.push(lotteryCode);
				_lotteryCode = lotteryCode;
				formPost.submitAjaxForm1("finance/getPlayModelsAndIssueNo.shtml",attrs,values,playModelsCallBack,null);
			}
		},
		initData : function(pageNum){
			if(!this.checkForm()){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("pageNum");
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			packageSecParam(attrs,values);
			formPost.setTbodyObj($("#brdata"),$("._trclone"));
			formPost.submitAjaxForm1("team/showbrdata.shtml",attrs,values,initDataCallBack,null);
		},
		detailInfo : function(id,urlName){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			attrs.push("urlName");
			values.push(titleName);
			attrs.push("fromType");
			attrs.push("betRecordsMenu");
			formPost.submitForm("showbrdetail.html",attrs,values);
		},
		initResultMsg : function(text){
			$("#brdata").html("<tr><td  height='39' align='center' " +" colspan='10' align='middle' class='f12 color_8d' >"+ text +"<td></tr>");
			$("#_myPageDiv").html("");
		}
	};
}();

$(document).ready(function(){
	$("#startTime").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).focus(function(){
		if(Math.abs(getDateDiff($("#endTime").val(),$("#startTime").val(),"day")) > 1){
			$("._issueNo").attr("disabled","disabled");
			$("._issueNo")[0].options[0].selected =true;
		}else{
			$("._issueNo").removeAttr("disabled");
		}
	});
	$("#endTime").bind("click", function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	}).focus(function(){
		if(Math.abs(getDateDiff($("#endTime").val(),$("#startTime").val(),"day")) > 1){
			$("._issueNo").attr("disabled","disabled");
			$("._issueNo")[0].options[0].selected =true;
		}else{
			$("._issueNo").removeAttr("disabled");
		}
	});
    $("#startTime").val(getTime(0, 0, 4));
    $("#endTime").val(getTime(0, 1, 4));
	$("._initPlayMode").click(function(){
		betRecord.initPlayModel($(this).val());
	});
	$("._secBtn").click(function(){
		betRecord.initData(1);
	});
	$("._detailInfo").click(function(){
		betRecord.detailInfo($(this).next().val());
	});
	$("#betRecordsMenu").addClass("current");
	//betRecord.initData(1);
	betRecord.initResultMsg("请选择查询条件进行查询");
});