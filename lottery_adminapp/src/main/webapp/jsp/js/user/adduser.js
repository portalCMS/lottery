$(document).ready(function() {
	var params = "";
	$.ajax({
		type : 'post',
		url : '../getBankCards.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			var entitylist = data.bankCards;
			addBankRows(entitylist);
		},
		error : function() {
		}
	});
	$("#startQuota").val($("#point").val().split(",")[1]*100);
	$("#startQuota").bind("change",function(){
		if(isNaN($(this).val())){
			alert("起始返点只能是数字");
			return;
		}
		if($(this).val()>$("#point").val().split(",")[1]*100){
			$("#startQuota").val($("#point").val().split(",")[1]*100);
		}
		if($(this).val()<0){
			alert("起始返点不能小于0");
			return;
		}
		if($(this).val()<$("#minPoint").val()){
			$("#startQuota").val($("#point").val().split(",")[1]*100);
			alert("起始返点必须大于最小返点");
			return;
		}
		 initQuota();
	});
	initQuota();
	$("#point").bind("change",function(){
		 initQuota();
	});
	$("#sPoint").bind("change",function(){
		if($("#sPoint").val()<$("#ysPoint").val()){
			alert("不能小于配置最小点差");
			$("#sPoint").val($("#ysPoint").val());
			return;
		}
		if($("#sPoint").val()>=1){
			alert("最小点差不能大于1");
			$("#sPoint").val($("#ysPoint").val());
			return;
		}
		$("#stepPoint").val($("#sPoint").val());
		 initQuota();
	});
	$("#openUser").bind("click",function(){
		if(initData()==true){
			addMainUser();
		}
	});
	$("#openRegUser").bind("click",function(){
		if(initData()==true){
			addRegUser();
		}
	});
	$("#openTestUser").bind("click",function(){
		if(initData()==true){
			openTestUser();
		}
	});
});

/**
 * 刷新银行卡
 * @param entitylist
 */
function addBankRows(entitylist){
	var html = "";
	for(var i=0;i<entitylist.length;i++){
		var entity = entitylist[i];
		var flag = true;  //判断银行卡是否已被选中
		for(var j=0;j<bankCards.length;j++){
			if(entity.id==bankCards[j].split(":")[0]){
				flag = false;
			}
		}
		if($("#czcs").val()!=""&&$("#czcs").val()<entity.cardCount){
			continue;
		}
		if($("#fprs").val()!=""&&$("#fprs").val()<entity.bindCount){
			continue;
		}
		if($("#ljje").val()!=""&&$("#ljje").val()<entity.cardMoney){
			continue;
		}
		if(flag){
			html += "<tr>";
			html += "<td>"+entity.id+"</td>";
			html += "<td>"+entity.updateTime+"</td>";
			html += "<td>"+entity.bankName+"</td>";
			html += "<td>"+entity.opencardName+"</td>";
			html += "<td><a href=\"javascript:void(0);\" class=\"btn btn-link btn-sm\" title='查看详情'>"+entity.cardNo+"</a></td>";
			html += "<td>"+entity.cardCount+"</td>";
			html += "<td class=\"text-danger\">"+entity.cardMoney+"</td>";
			html += "<td>"+entity.cardLevel+"</td>";
			html += "<td>"+entity.cardInventoryName+"</td>";
			html += "<td>"+entity.bindCount+"</td>";
			html += "<td ><label class='checkbox-inline'><input type='checkBox' name='bankId' onchange='addBankCard(this)' id='bankId"+entity.id+"' value='"+entity.id+"'>分配此卡</label></td>";
			html += "<td class=\"text-center\"><label class=\"radio-inline\">"
					+"<input type=\"radio\" value=\"15001\"  onclick='checkBankIdStatus(this,"+entity.id+")' name=\"bankcard"+entity.id+"\" id=\"bankcard"+entity.id+"\">"
					+"独立绑定"
					+"</label><label class=\"radio-inline\"> <input type=\"radio\" checked='true' onclick='checkBankIdStatus(this,"+entity.id+")' value=\"15002\" name=\"bankcard"+entity.id+"\" id=\"bankcard"+entity.id+"\"> 继承绑定";
					+"</label></td>";
			html += "</tr>";
		}
	}
	$("#banksBody").html(html);
}


/**
 * 初始化配额信息
 */
function initQuota(){
	var stepPoint = $("#stepPoint").val()*10;
	var minPoint =  $("#minPoint").val()*10;
	var startQuota = $("#startQuota").val();
	var point =  0;
	if(startQuota!=undefined && startQuota != "" && startQuota*10<=$("#point").val().split(",")[1]*1000){
		point = startQuota*10;
	}else{
		point = $("#point").val().split(",")[1]*1000;
	}
	var html = "";
	for(var i=point-stepPoint;;){
		if(!(i>=minPoint)){
			break;
		}
		html += "<div class=\" col-sm-3 \">";
		html += "<label class=\"control-label \">"+i/10+"%</label> <input type=\"number\" value=\"1\" name='quota' id='quota,"+i/1000+"' class=\"form-control\">";
		html += "</div>";
		i=i-stepPoint;
	}
	$("#quota").html(html);
}

/**
 * ajax查询银行卡
 */
function findBanks(){
	jQuery(function(){
		var bankId = $("#bank").val();
		var params = "bankId="+bankId;
		$.ajax({
			type : 'post',
			url : '../getBankCards.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				var entitylist = data.bankCards;
				addBankRows(entitylist);
			},
			error : function() {
			}
		});
	});
}

/**
 * 全选和反选
 * @param obj
 */
function checkStatus(obj){
	var checkBoxs = document.getElementsByName("bankId");
	for(var i=0;i<checkBoxs.length;i++){
		if(obj.checked==true){
			checkBoxs[i].checked = true;
			addBankCard(checkBoxs[i]);
		}else{
			checkBoxs[i].checked = false;
			addBankCard(checkBoxs[i]);
		}
	}
}

/**
 * bankCards全局银行变量
 */
var bankCards = new Array();
/**
 * 勾选分配银行卡出发事件
 * @param obj
 */
function addBankCard(obj){
	if(obj.checked==false){
		for(var i=0;i<bankCards.length;i++){
			if(bankCards[i].split(":")[0]==obj.value){
				bankCards.splice(i,1);
				changeCardLevelStatus2($(obj).closest("tr").find("td").eq(7).text());
			}
		}
	}else{
		var cardId = obj.value;
		var extendsStatus =  $("input[name='bankcard"+cardId+"']:checked").val();
		var temp = 0;
		for(var i=0;i<bankCards.length;i++){
			if(bankCards[i].split(":")[0]==cardId){
				temp++;
			}
		}
		if(temp==0){
			bankCards.push(obj.value+":"+extendsStatus);
			changeCardLevelStatus($(obj).closest("tr").find("td").eq(7).text());
		}
	}
	$("#cbs").html(bankCards.length);
}

/**
 * 已选择中的银行卡
 */
function showBankCard(){
	var params = "";
	$.ajax({
		type : 'post',
		url : '../getBankCards.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			var entitylist = data.bankCards;
			var html = "";
			for(var i=0;i<entitylist.length;i++){
				var entity = entitylist[i];
				for(var j = 0;j<bankCards.length;j++){
					if(bankCards[j].split(":")[0]==entity.id){
						html += "<tr>";
						html += "<td>"+entity.id+"</td>";
						html += "<td>"+entity.updateTime+"</td>";
						html += "<td>"+entity.bankName+"</td>";
						html += "<td>"+entity.opencardName+"</td>";
						html += "<td><a href=\"javascript:void(0);\" class=\"btn btn-link btn-sm\" title='查看详情'>"+entity.cardNo+"</a></td>";
						html += "<td>"+entity.cardCount+"</td>";
						html += "<td class=\"text-danger\">"+entity.cardMoney+"</td>";
						html += "<td>"+entity.cardLevel+"</td>";
						html += "<td>"+entity.cardInventoryName+"</td>";
						html += "<td>"+entity.bindCount+"</td>";
						html += "<td ><label class='checkbox-inline'><input type='checkBox' name='bankId' checked='checked' onchange='addBankCard(this)' id='bankId"+entity.id+"' value='"+entity.id+"'>分配此卡</label></td>";
						html += "<td class=\"text-center\"><label class=\"radio-inline\">";
						if(bankCards[j].split(":")[1]==15001){
							html +=	"<input type=\"radio\" value=\"15001\" checked='true' onclick='checkBankIdStatus(this,"+entity.id+")' name=\"bankcard"+entity.id+"\">";
						}else{
							html +=	"<input type=\"radio\" value=\"15001\" onclick='checkBankIdStatus(this,"+entity.id+")' name=\"bankcard"+entity.id+"\">";
						}
						html += "独立绑定"
								+"</label><label class=\"radio-inline\"> ";
						if(bankCards[j].split(":")[1]==15002){
							html +=	"<input type=\"radio\" value=\"15002\" checked='true' onclick='checkBankIdStatus(this,"+entity.id+")' name=\"bankcard"+entity.id+"\"> 继承绑定";
						}else{
							html +=	"<input type=\"radio\" value=\"15002\" onclick='checkBankIdStatus(this,"+entity.id+")' name=\"bankcard"+entity.id+"\"> 继承绑定";
						}
						html +="</label></td>";
						html += "</tr>";
					}
				}
			}
			$("#banksBody").html(html);
			
		},
		error : function() {
		}
	});
}

var checkCardLevel = false;
var level1 = 0;
var level2 = 0;
var level3 = 0;
var level4 = 0;
var level5 = 0;
function changeCardLevelStatus(level){
	switch (parseInt(level)) {
	case 1:
		level1++;
		break;
	case 2:
		level2++;
		break;
	case 3:
		level3++;
		break;
	case 4:
		level4++;
		break;
	default:
		level5++;
		break;
	}
	if(level1>=1&&level2>=1&&level3>=1&&level4>=1&&level5>=1){
		checkCardLevel = true;
	}else{
		checkCardLevel = false;
	}
}
function changeCardLevelStatus2(level){
	switch (parseInt(level)) {
	case 1:
		level1--;
		break;
	case 2:
		level2--;
		break;
	case 3:
		level3--;
		break;
	case 4:
		level4--;
		break;
	default:
		level5--;
		break;
	}
	if(level1>=1&&level2>=1&&level3>=1&&level4>=1&&level5>=1){
		checkCardLevel = true;
	}else{
		checkCardLevel = false;
	}
}
/**
 * 如果银行卡被选择 那么则替换全局变量中的bankCards的节点
 * @param obj
 * @param id
 */
function checkBankIdStatus(obj,id){
	var checkBoxs = document.getElementsByName("bankId");
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			for(var j=0;j<bankCards.length;j++){
				if(bankCards[j].split(":")[0]==id){
					bankCards.splice(j,1,id+":"+obj.value);
				}
			}
		}
	}
}

/**
 * 提交时数据拼装
 * @returns {Boolean}
 */
function initData(){
	var quotas = "";
	$("input[name='quota']").each(function(){
		quotas+=$(this).attr("id").split(",")[1]+":"+$(this).val()+";";
	});
	$("#quotas").val(quotas);
	if(bankCards.length==0){
		alert("请至少分配一张银行卡");
		return false;
	}
	if(bankCards.length<5||!checkCardLevel){
		alert("请至少每个等级分配一张银行卡");
		return false;
	}
	var cards = "";
	for(var i=0;i<bankCards.length;i++){
		cards += bankCards[i]+";";
	}
	$("#cards").val(cards);
	var urls = "";
	$("input[name='curl']:checked").each(function(){
		urls+=$(this).val()+";";
	});
	if(urls==""){
		alert("请至少分配一个域名");
		return false;
	}
	$("#urls").val(urls);
	return true;
}

/**
 * 提交
 */
function addMainUser(){
	var customerName = $("#customerName").val();
	if(customerName==""){
		alert("用户名不能为空");
		return;
	}
	var adminpwd = $("#adminPwd").val();
	if(adminpwd == ""){
		alert("授权密码不能为空");
		return;
	}
	var code = $("#code").val();
	if(code==""){
		alert("验证码不能为空");
		return;
	}
	addUserForm.submit();
}

/**
 * 提交
 */
function addRegUser(){
	var customerName = $("#customerName").val();
	if(customerName==""){
		alert("用户名不能为空");
		return;
	}
	var adminpwd = $("#adminPwd").val();
	if(adminpwd == ""){
		alert("授权密码不能为空");
		return;
	}
	var code = $("#code").val();
	if(code==""){
		alert("验证码不能为空");
		return;
	}
	addUserForm.action = "addRegUser.do";
	addUserForm.submit();
}
function openTestUser(){
	var customerName = $("#customerName").val();
	if(customerName==""){
		alert("用户名不能为空");
		return;
	}
	var adminpwd = $("#adminPwd").val();
	if(adminpwd == ""){
		alert("授权密码不能为空");
		return;
	}
	var code = $("#code").val();
	if(code==""){
		alert("验证码不能为空");
		return;
	}
	addUserForm.action = "addTestUser.do";
	addUserForm.submit();
}