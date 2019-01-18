$(document).ready(function() {
	//彩种状态
	var statusRadios = $("input[name='lotteryStatus']");
	for(var i=0;i<statusRadios.size();i++){
		var radio = statusRadios.get(i);
		if($(radio).val()==$(radio).next().val()){
			$(radio).attr('checked',true);
		}
	}
	//号源状态
	var linkSize = $("#linkSize").val();
	linkSize = parseInt(linkSize);
	for(var j=0;j<linkSize;j++){
		statusRadios = $("input[name='linkStatus_"+j+"']");
		for(var i=0;i<statusRadios.size();i++){
			var radio = statusRadios.get(i);
			if($(radio).val()==$(radio).next().val()){
				$(radio).attr('checked',true);
			}
		}
	}
	//玩法状态
//	var lpmSize = $("#lpmSize").val();
//	lpmSize = parseInt(lpmSize);
//	for(var k=0;k<lpmSize;k++){
//		statusRadios = $("input[name='lpmStatus_"+k+"']");
//		for(var i=0;i<statusRadios.size();i++){
//			var radio = statusRadios.get(i);
//			if($(radio).val()==$(radio).next().val()){
//				$(radio).attr('checked',true);
//			}
//		}
//	}
	//奖期星期勾选
	var weekConfig = $("#seriesExpression").val();
	var index = weekConfig.lastIndexOf(" ");
	var week = weekConfig.substring(index+1,weekConfig.length);
	var weekArr = week.split(",");
	var seriesArr = $("input[name='series']");
	for(var k=0;k<seriesArr.size();k++){
		var series = seriesArr.get(k);
		for(var l=0;l<weekArr.length;l++){
			if($(series).val()==weekArr[l]){
				$(series).attr('checked','checked');
				break;
			}
		}
	}
	$(".amount_class").formatCurrency();
	
	$(".delete_btn").on("click", function() {
		deletePeriod(this);
	});
	$(".delete_btn").hide();
	
	
	$(".number_url_close_btn").on("click", function() {
		deleteSourceLink(this);
	});
	
	$(".close_btn").on("click", function() {
		deleteNewPeriod(this);
	});
	
	$(".modifyLpm").on("click", function() {
		modifyPalyModel();
	});
	
	$.checkFrom.initBind(".lottery", true, true);
	$.checkFrom.initBind(".period", true, true);
	$.checkFrom.initBind(".new_period", true, true);
	$.checkFrom.initBind(".link_names", true, true);
	$.checkFrom.initBind(".link_urls", true, true);
});

function showSaveBtn(area,modifyBtn,saveBtn){
	var info = $("."+area);
	for(var i=0;i<info.size();i++){
		var lt = info.get(i);
		$(lt).removeAttr('readonly');
	}
	$("#"+modifyBtn).hide();
	$("#"+saveBtn).show();
	
}
function showModifyBtn(area,modifyBtn,saveBtn){
	//更改显示的按钮
	var info = $("."+area);
	for(var i=0;i<info.size();i++){
		var lt = info.get(i);
		$(lt).attr('readonly','readonly');
	}
	$("#"+modifyBtn).show();
	$("#"+saveBtn).hide();
}
function saveLottery(id){
	messenger.cofirmMsg("是否确定保存更改！",
			function (){
				doSaveLottery(id);
			},
			function (){
				showModifyBtn('lottery','modifyBtn1','saveBtn1');
			});
}	

function doSaveLottery(id){
	var params = "";
	var lotteryCode = $("input[name='lotteryCode']").val();
	var seriesRule = $("._seriesRule").val();
	var lotteryStatus = $("input[name='lotteryStatus']:checked").val();
	var lotteryName=$("input[name='lotteryName']").val();
	var lotGroup = $("#lotteryGroup").val();
	
	params = "lotteryCode="+lotteryCode+"&lotteryStatus="+lotteryStatus
				+"&lotteryName="+lotteryName+"&id="+id;
	
	
	if(lotGroup=="3D"){
		var restdays = $("#restDays").val();
		if(restdays==undefined||restdays==""){
			restdays=0;
		}
		params = params+"&catchTimes="+restdays;
	}
	
	var url = "lottery/saveLotteryInfo.do";
	$.ajax({
		type : 'post',
		url : path+"/"+url,
		data : params,
		dataType : 'json',
		success : function(data) {
			if(data.errorMsg){
				Messenger().post({
					message :decodeURI(data.errorMsg),
					type : 'error',
					showCloseButton : true
				});
				return;
			}
			//更新最新值
			$("input[name='lotteryCode']").val(data.lottery.lotteryCode);
			$("input[name='lotteryStatus']:checked").val(data.lottery.lotteryStatus);
			$("input[name='lotteryName']").val(data.lottery.lotteryName);
			showModifyBtn('lottery','modifyBtn1','saveBtn1');
			Messenger().post({
				message :"保存更改成功！",
				type : 'info',
				showCloseButton : true
			});
		},
		error : function(error) {
			Messenger().post({
				message :error,
				type : 'error',
				showCloseButton : true
			});
		}
	});
}


/**
 * 修改号源配置
 * @param area
 * @param modifyBtn
 * @param saveBtn
 */
function showSourceModify(area,modifyBtn,saveBtn){
	showSaveBtn(area,modifyBtn,saveBtn);
	$("#newSourceBtn").show();
}
/**
 * 添加新号源
 */
function addNewSourceLink(){
	var newSourceLink = $("#sourceLink>div:first-child").clone(true);
	newSourceLink.css({display:'block'});
	$("#sourceLinks").append(newSourceLink);
}
/**
 * 删除新号源
 */
function deleteSourceLink(obj){
	$(obj).closest(".number_url_panle_class").remove();
}
/**
 * 是否确认保存号源设置
 */
function saveSource(){
	messenger.cofirmMsg("是否确定保存更改！",
	function (){
		doSaveSource();
	},
	function (){
		showSourceModifyBtn('source','modifyBtn2','saveBtn2');
	});
}

function showSourceModifyBtn(area,modifyBtn,saveBtn){
	showModifyBtn(area,modifyBtn,saveBtn);
	$("#newSourceBtn").hide();
}
/**
 * ajax 调用后台保存号源设置
 */
function doSaveSource(){	
	var linkSize = $("#linkSize").val();
	linkSize = parseInt(linkSize);
	var idListStr = "";
	var statusListStr="";
	for(var j=0;j<linkSize;j++){
		var link = $("input[name='linkStatus_"+j+"']:checked");
		var linkId = $(link).next().attr('name');
		linkId =linkId.substring(linkId.lastIndexOf("_")+1);
		if(idListStr==""){
			idListStr = linkId;
			statusListStr = $(link).val();
		}else{
			idListStr =idListStr+","+linkId;
			statusListStr =statusListStr+","+ $(link).val();
		}
	}
	
	//循环新添加的号源，拼接字符串
	var newSourceNames ="";
	var newSourceLinks ="";
	var sourceLevelStrs="";
	for(var i=0;i<$("#sourceLinks .link_names").size();i++){
		var name = $("#sourceLinks .link_names").eq(i).val();
		var link = $("#sourceLinks .link_urls").eq(i).val();
		var level = $("#sourceLinks .link_levels").eq(i).val();
		if(newSourceNames==""){
			newSourceNames = name;
			newSourceLinks = link;
			sourceLevelStrs = level;
		}else{
			newSourceNames = newSourceNames +","+name;
			newSourceLinks = newSourceLinks +","+link;
			sourceLevelStrs = sourceLevelStrs +","+level;
		}
		
	}
	var countSourceSize = $("#linkSize").val();
	var lotteryCode = $("#lotteryCode").val();
	var lotteryGroup = $("#lotteryGroup").val();
	
	var attrs = new Array();
	attrs.push('lotteryCode');
	attrs.push('lotteryGroup');
	attrs.push('idListStr');
	attrs.push('statusListStr');
	attrs.push('newSourceNames');
	attrs.push('newSourceLinks');
	attrs.push('countSourceSize');
	attrs.push('sourceLevelStrs');
	
	var values = new Array();
	values.push(lotteryCode);
	values.push(lotteryGroup);
	values.push(idListStr);
	values.push(statusListStr);
	values.push(newSourceNames);
	values.push(newSourceLinks);
	values.push(parseInt(countSourceSize));
	values.push(sourceLevelStrs);
	formPost.submitAjaxForm("lottery/saveSource.do",attrs,values,saveSourceSuccess);
}

function saveSourceSuccess(data){
	$("#links_tbody").html("");
	$("#sourceLinks").html("");
	var html="";
	for(var i=0;i<data.lsList.length;i++){
		var source = data.lsList[i];
		html += "<tr>";
		html += "<td>"+(i+1)+"</td>";
		html += "<td>"+source.link.sourceName+"</td>";
		html += "<td>"+source.link.sourceLink+"</td>";
		html += "<td>"+source.link.sourceLevel+"</td>";
		html += "<td><label class='radio-inline'> <input type='radio' name='linkStatus_"+i;
		html += "' value='1'> <input type='hidden' name='linkId_"+source.id+"' value='"+source.status+"'> 启用";
		html += "</label>  <label class='radio-inline'> <input type='radio' name='linkStatus_"+i;
		html += "' value='0'> <input type='hidden' name='linkId_"+source.id+"' value='"+source.status+"'> 停用 </label></td>";
		html += "</tr>";
	}
	$("#links_tbody").append(html);
	//号源状态
	$("#linkSize").val(data.lsList.length);
	var linkSize = $("#linkSize").val();
	linkSize = parseInt(linkSize);
	for(var j=0;j<linkSize;j++){
		statusRadios = $("input[name='linkStatus_"+j+"']");
		for(var i=0;i<statusRadios.size();i++){
			var radio = statusRadios.get(i);
			if($(radio).val()==$(radio).next().val()){
				$(radio).attr('checked',true);
			}
		}
	}
	
	showSourceModifyBtn('source','modifyBtn2','saveBtn2');
	
	Messenger().post({
		message :"号源更改保存成功！",
		type : 'info',
		showCloseButton : true
	});
}

function showPeriodModify(area,modifyBtn,saveBtn){
	showSaveBtn(area,modifyBtn,saveBtn);
	$("#newPeriodBtn").show();
	$("fieldset").removeAttr('disabled');
	$(".delete_btn").show();
}

function showPeriodModifyBtn(area,modifyBtn,saveBtn){
	showModifyBtn(area,modifyBtn,saveBtn);
	$("#newPeriodBtn").hide();
	$(".delete_btn").hide();
	$("fieldset").attr('disabled',true);
}
/**
 * 是否确认保存奖期设置
 */
function savePeriod(){
	messenger.cofirmMsg("是否确定保存更改！",
	function (){
		doSavePeriod();
	},
	function (){
		showPeriodModifyBtn('period','modifyBtn3','saveBtn3');
	});
}

/**
 * 保存奖期设置
 */
function doSavePeriod(){
	var checkValue=itemVal.getCheckBoxVal("series");
	var exps = $("input[name='seriesExpression']");
	for(var i=0;i<exps.size();i++){
		var exp = exps.get(i);
		$(exp).val(checkValue);
	}
	
	var srs = $("input[name='seriesRule']");
    var seriesRule = $("._seriesRule").val();
    for(var i=0;i<srs.size();i++){
		var sr = srs.get(i);
		$(sr).val(seriesRule);
	}
    
	var jsonMap = new Map();
	jsonMap.put("classNameList", ".periods");
	jsonMap.put("className", ".period");
	var jsonListStr = jsonFormat.buildAjaxJsonListStr(jsonMap);
	var jsonMap2 = new Map();
	jsonMap2.put("classNameList", "#lottery_series .period-panel");
	jsonMap2.put("className", ".new_period");
	var jsonListStr2 = jsonFormat.buildAjaxJsonListStr(jsonMap2);
	
	var attrs = new Array();
	var values = new Array();
	
	if(null!=jsonListStr){
		attrs.push('lotteryObjs');
		values.push(jsonListStr);
	}
	if(null!=jsonListStr2){
		attrs.push('newLotteryObjs');
		values.push(jsonListStr2);
	}
	if(null==jsonListStr&&null==jsonListStr2){
		Messenger().post({
			message :"奖期设置不能为空！",
			type : 'error',
			showCloseButton : true
		});
		return;
	}
	formPost.submitAjaxForm("lottery/savePeriod.do",attrs,values,savePeriodSuccess);
}

function savePeriodSuccess(data){
	//按钮重置
	showPeriodModifyBtn('period','modifyBtn3','saveBtn3');
	$("#lottery_series").html("");
	//更新星期的设置
	var ce = data.lsList[0].taskCornExpression;
	ce =ce.substring(ce.lastIndexOf(" ")+1);
	var ceArr = ce.split(",");
	for(var i=0;i<ceArr.length;i++){
		var cv = ceArr[i];
		var cbs = $("input[name='series']");
		for(var j=0;j<cbs.size();j++){
			var cb =cbs.get(i);
			if(cv==$(cb).val()){
				$(cb).attr('checked','checked');
				break;
			}
		}
	}
	//先拷贝旧的奖期对象
	var lotPeriod = $("#totalPeriods .lotPeriods").get(0);
	var lot =$(lotPeriod).clone(true);
	//确保拷贝的对象能显示出来不是隐藏的。
	$(lot).show();
	$("#totalPeriods .priodsInfo").html("");
	
	var id = $(lot).find("input[name='id']");
	var lotteryStatus = $(lot).find("input[name='lotteryStatus']");
	var startTime = $(lot).find("input[name='startTime']");
	var spanTime = $(lot).find("input[name='spanTime']");
	var beforeLotTime = $(lot).find("input[name='beforeLotTime']");
	var endTime = $(lot).find("input[name='endTime']");
	var totalTimes = $(lot).find("input[name='totalTimes']");
	var catchTimes = $(lot).find("input[name='catchTimes']");
	//根据返回的list重构显示，替换旧的奖期对象中的值为后台返回的新值，最后添加上去。
	for(var i=0;i<data.lsList.length;i++){
		var newLot =data.lsList[i];
		if(newLot.lotteryStatus!=10002){
			continue;
		}
		$(id).val(newLot.id);
		$(lotteryStatus).val(newLot.lotteryStatus);
		$(startTime).val(newLot.startTime);
		$(spanTime).val(newLot.spanTime);
		$(beforeLotTime).val(newLot.beforeLotTime);
		$(endTime).val(newLot.endTime);
		$(totalTimes).val(newLot.totalTimes);
		$(catchTimes).val(newLot.catchTimes);
		$("#totalPeriods .priodsInfo").append($(lot).clone(true));
	}
	//显示提示信息
	Messenger().post({
		message :"奖期更改保存成功！",
		type : 'info',
		showCloseButton : true
	});
	
}

function addNewPeriod(){
	var newSourceLink = $("#lottery_period>div:first-child").clone(true);
	newSourceLink.css({display:'block'});
	$("#lottery_series").append(newSourceLink);
}

/**
 * 删除新奖期
 */
function deleteNewPeriod(obj){
	$(obj).closest(".period-panel").remove();
}

/**
 * 删除原先已有奖期(隐藏并且修改status=10001)
 */
function deletePeriod(obj){
	$(obj).closest(".lotPeriods").find("input[name='lotteryStatus']").val(10001);
	$(obj).closest(".lotPeriods").hide();
}

/**
 * 修改玩法相关的所有配置
 */
function modifyPalyModel(){
	var attrs = new Array();
	attrs.push("lotteryCode");
	attrs.push("lotteryGroup");
	var values = new Array();
	values.push($("#lotteryCode").val());
	values.push($("#lotteryGroup").val());
	formPost.submitForm("showModifyModel.do",attrs,values);
}
