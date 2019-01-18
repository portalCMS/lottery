var record = function(){
	//var self = this;
	var initTypesAndPlayModel = function(data){
		var typeHtml = "";
		typeHtml+="<option value='0'>所有彩种</option>";
		var types = data.types;
		for(var i=0;i<types.length;i++){
			typeHtml += "<option value='"+types[i].lotteryCode+"'>"+types[i].lotteryName+"</option>"; 
		}
		$("._typs").html(typeHtml);
		var playModelHtml = "";
		playModelHtml+="<option value='0'>所有玩法</option>";
		var playModels = data.playModels;
		for(var i=0;i<playModels.length;i++){
			playModelHtml += "<option value='"+playModels[i].modelCode+"'>"+playModels[i].modelName+"</option>";
		}
		$("._playModels").html(playModelHtml);
	};
	var initData = function(data){
		var vos = data.betRecordVO.pagelist;
		var summoney = 0;
		var bet = 0;
		var cancle = 0;
		var win = 0;
		var rebates = 0;
		var cells = data.betRecordVO.cellList;
		for(var i=0;i<cells.length;i++){
			if(cells[i][0]==21001){
				bet += cells[i][1]*10000;
				//rebates += cells[i][2]*10000;
			}
			if(cells[i][0]==21002){
				bet += cells[i][1]*10000;	
				rebates += cells[i][2]*10000;
				win += cells[i][3]*10000;
			}
			if(cells[i][0]==21003){
				bet += cells[i][1]*10000;
				rebates += cells[i][2]*10000;
			}
			if(cells[i][0]==21004){
				bet += cells[i][1]*10000;
				cancle+=cells[i][1]*10000;
			}
		}
		summoney = bet - rebates - win - cancle;
		$("._sumcount").text(summoney/10000);
		$("._bet").text(bet/10000);
		$("._cancle").text(cancle/10000);
		$("._win").text(win/10000);
		$("._rebates").text(rebates/10000);
		var html = "";
		for(var i=0;i<vos.length;i++){
			html += "<tr>";
			html += "<td>"+vos[i].id+"</td>";
			html += "<td>"+vos[i].createTime+"</td>";
			html += "<td>"+vos[i].lotteryCode+"</td>";
			html += "<td>"+vos[i].issueNo+"</td>";
			html += "<td>"+vos[i].playCode+"</td>";
			html += "<td class='_fcash'>"+vos[i].betMoney*vos[i].multiple+"</td>";
			switch (vos[i].betStatus) {
			case 21001:
				html += "<td><label class=\"label label-default\">投注成功</label></td>";
				break;
			case 21002:
				html += "<td><label class=\"label label-danger\">已中奖"+vos[i].winMoney+"</label></td>";
				break;
			case 21003:
				html += "<td><label class=\"label label-default\">未中奖</label></td>";
				break;
			default:
				html += "<td><label class=\"label label-default\">已撤单</label></td>";
				break;
			}
			html += "<td><a href=\"user/showUserInfo/"+vos[i].customerId+".do\">"+vos[i].uName+"</a></td>";
			html += "<td align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"records_detail_btn\">简要信息</a>&nbsp;&nbsp;";
			html += "<a href=\"javascript:void(0);\" class=\"_details\">方案详情</a> ";
			html += "&nbsp;&nbsp;<a href=\"javascript:void(0);\" class=\"_cancel\">撤销此单</a></td>";
			html += "</tr>";
		}
		$("._tbody").html(html);
		$(".records_detail_btn").click(function() {
			getInfo($(this).closest("tr").find("td").eq(0).text());
		});
		$("._details").click(function(){
			detailsInfo($(this).closest("tr").find("td").eq(0).text());
		});
		$("._cancel").click(function(){
			record.cancelBetById($(this).closest("tr").find("td").eq(0).text());
		});
		pageInfo("_pageInfo",data.pageNum,data.maxCount,data.pageCount);
		$("._fcash").formatCurrency();
	};
	var detailsInfo = function(id){
		var attrs = new Array();
		var values = new Array();
		attrs.push("id");
		values.push(id);
		formPost.submitForm("detailsInfo.do",attrs,values);
	};
	var callBackInfo = function(data){
		$("._uName").html(data.vo.uName);
		$("._lotteryTypeAndPlayCode").html(data.vo.lotteryCode+"<span class=\"text-primary\">("+data.vo.playCode + "&nbsp;&nbsp;"+data.vo.selectCodeName+")</span>");
		$("._issueNo").html(data.vo.issueNo);
		var betMoneyHtml = "<span class=\"text-danger _amount\">"+data.vo.betMoney*data.vo.multiple+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
		switch (data.vo.betModel) {
		case 1:
			betMoneyHtml+="<label class=\"label label-primary\">元模式 </label>&nbsp;&nbsp;&nbsp;&nbsp;";
			betMoneyHtml+=" <label class=\"label label-success\">倍数："+data.vo.multiple+"</label>";
			break;
		case 0.1:
			betMoneyHtml+="<label class=\"label label-primary\">角模式 </label>&nbsp;&nbsp;&nbsp;&nbsp;";
			betMoneyHtml+=" <label class=\"label label-success\">倍数："+data.vo.multiple+"</label>";
			break;
		default:
			betMoneyHtml+="<label class=\"label label-primary\">分模式 </label>&nbsp;&nbsp;&nbsp;&nbsp;";
			betMoneyHtml+=" <label class=\"label label-success\">倍数："+data.vo.multiple+"</label>";
			break;
		}
		var betStatusHtml = "";
		switch (data.vo.betStatus) {
		case 21001:
			betStatusHtml += "<label class=\"label label-default\">投注成功</label>&nbsp;&nbsp;<span class=\"text-default\"></span>";
			break;
		case 21002:
			betStatusHtml += "<label class=\"label label-danger\">已中奖&nbsp;&nbsp;中奖金额：" +
					"<span class='_amount'>"+data.vo.winMoney+"</span>&nbsp;&nbsp;返奖率："
					+data.vo.payoutRatio+"</label>";
			break;
		case 21003:
			betStatusHtml += "<label class=\"label label-default\">未中奖</label>&nbsp;&nbsp;<span class=\"text-default\"></span>";
			break;
		default:
			betStatusHtml += "<label class=\"label label-default\">已撤单</label>&nbsp;&nbsp;<span class=\"text-default\"></span>";
			break;
		}
		$("._betStatus").html(betStatusHtml);
		$("._betMoney").html(betMoneyHtml);
		$("._time").html(data.vo.createTime);
		var number = "";
		if(data.vo.bileNum==""){
			number = data.vo.betNum;
		}else{
			number = "胆码:"+data.vo.bileNum+" 拖码:"+data.vo.betNum;
		}
		$("._openNum").html(data.vo.opernBetNumber);
		$("._number").html(number);
		$("#records_detail_modal").modal("show");
		
		$("._amount").formatCurrency();
	};
	var getInfo = function(id){
		var attrs = new Array();
		var values = new Array();
		attrs.push("id");
		values.push(id);
		formPost.submitAjaxForm("getBetRecordInfo.do",attrs,values,callBackInfo);
	};
	var pageInfo = function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:record.getBetRecord("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:record.getBetRecord("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		cancelBetById : function(id){
			if(!confirm("亲,是否确认撤销该投注单?")){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			formPost.submitAjaxForm("cancelBetByAdmin.do",attrs,values,this.cancelSuccess);
		},
		cancelBetByIssue : function(){
			if(!confirm("亲,是否确认撤销整期投注单?")){
				return;
			}
			
			var attrs = new Array();
			var values = new Array();
			attrs.push("issueNo");
			attrs.push("lotteryCode");
			
			var issueNo = $("#issueNo").val();
			var lotCode = $("#lotteryCode").val();
			if(lotCode=="0"||lotCode==""||lotCode==undefined||issueNo==""||issueNo==undefined){
				formPost.showErrorMessage("亲，请选中一个彩种并填写奖期号，再进行撤单操作！");
				return;
			}
			values.push(issueNo);
			values.push(lotCode);
			
			formPost.submitAjaxForm("cancelBetByAdmin.do",attrs,values,this.cancelSuccess);
		},
		cancelSuccess:function(data){
			if(data.info=="success"){
				formPost.showErrorMessage("亲，撤单操作成功！");
				record.getBetRecord(1);
			}else{
				formPost.showErrorMessage("亲，撤单操作失败！");
			}
		},
		getTypeAndModelPlay:function(){
			var groupCode = $("._groups").val();
			var attrs = new Array();
			var values = new Array();
			attrs.push("groupCode");
			values.push(groupCode);
			formPost.submitAjaxForm("getTypeAndModelPlay.do",attrs,values,initTypesAndPlayModel);
		},
		getBetRecord:function(pageNo){
			$("._tbody").html("<tr><td colspan='9' align='center' valign='middle' " +
					"style='vertical-align: middle;'>查询中，请稍候......</td></tr>");
			
			var attrs = new Array();
			var values = new Array();
			this.buildAjaxParam(attrs,values);
			if(pageNo != undefined && pageNo!=""){
				attrs.push("pageNum");
				values.push(pageNo);
			}
			formPost.submitAjaxForm("getBetRecordData.do",attrs,values,initData);
		},
		buildAjaxParam:function(attr,values){
			$("._ajaxParam").each(function(){
				attr.push($(this).attr("id"));
				values.push($(this).val());
			});
		}
	};
}();

$(document).ready(function(){
	$("._groups").on("change",function(){
		record.getTypeAndModelPlay();
	});
	
	$("#_cancelBtn").on("click",function(){
		record.cancelBetByIssue();
	});

	if($("._startTime").val()==""){
		var now = new Date();
		now = now.addDay(0);
		var nowTime = now.format("yyyy-MM-dd");
		$("._startTime").val(nowTime+" 04:00:00");
	}
	
	$("._startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("._endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("._secBtn").on("click",function(){
		record.getBetRecord();
	});
	record.getBetRecord();
});