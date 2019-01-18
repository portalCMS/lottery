var record = function(){
	//var self = this;
//	var initTypesAndPlayModel = function(data){
//		var typeHtml = "";
//		typeHtml+="<option value='0'>所有彩种</option>";
//		var types = data.types;
//		for(var i=0;i<types.length;i++){
//			typeHtml += "<option value='"+types[i].lotteryCode+"'>"+types[i].lotteryName+"</option>"; 
//		}
//		$("._typs").html(typeHtml);
//		var playModelHtml = "";
//		playModelHtml+="<option value='0'>所有玩法</option>";
//		var playModels = data.playModels;
//		for(var i=0;i<playModels.length;i++){
//			playModelHtml += "<option value='"+playModels[i].modelCode+"'>"+playModels[i].modelName+"</option>";
//		}
//		$("._playModels").html(playModelHtml);
//	};
	var initData = function(data){
		var vos = data.betRecordVO.pagelist;
		var summoney = 0;
		var bet = 0;
		var cancle = 0;
		var win = 0;
		var rebates = 0;
		var cells = data.betRecordVO.pagelist;
		for(var i=0;i<cells.length;i++){
			if(cells[i][0]==21001){
				bet += cells[i][1]*10000;
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
		var html = "";
		if(vos.length==0){
			html="<tr><td colspan='9' align='center' valign='middle' " +
			"style='vertical-align: middle;'>无数据</td></tr>";
		}
		for(var i=0;i<vos.length;i++){
			html += "<tr>";
			html += "<td>"+vos[i].createTime+"</td>";
			html += "<td>"+vos[i].lotteryCode+"<b>"+vos[i].selectCodeName+"</b></td>";
			html += "<td class='_fcash'><font color='red'>"+NumberFormat.format_number1(vos[i].betMoney*vos[i].multiple)+"</font></td>";
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
			html += "</tr>";
		}
		$("._tbody").html(html);
		pageInfo("pager",data.pageNum,data.maxCount,data.pageCount);
	};
	var pageInfo = function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<a href='javaScript:record.getBetRecord("+(pageNum+1)+")' role='button' class='btn btn-disable'>下一页</a><a href='javaScript:record.getBetRecord("+(pageNum-1)+")' role='button' class='btn btn-disable'>上一页</a>";
		$("."+divId).html(html);
	};
	return{
		getBetRecord:function(pageNo){
			msg.load();
			if(undefined==pageNo){
				pageNo=1;
			}else{
				pageNo=pageNo;
			}
			$.ajax({
				type: "POST",
				url: "showRecordData.shtml",//showRecordData.shtml
				dataType: "json",
				data:{pageNum:pageNo },
				success: function(data) {
					initData(data);
				},
				error: function(){
				}
			})
		}
	};
}();

$(document).ready(function(){
	record.getBetRecord();
});