$(document).ready(function() {
	head.init();
	$("._betPage").click(function(){
		var betName = $(this).text();
		$(".mainNavDiv").find("a").each(function(){
			if($(this).find("span").text()==betName){
				var str = $(this).attr("href");
				eval(str.split(":")[1]);
				return;
			}
		});
	});
	
	$("._myCashAmount").on('click',function(){
		formPost.submitAjaxForm1("getCashAmount.shtml", null, null,head.refreshCash, null);
	});
	head.getMsgCount();
	setInterval("head.getMsgCount()", 30000);
	$("._cashShow").on('click',function(){
		$(this).hide();
		$("._myCashAmount").show();
		$("._cashHide").show();
		$.cookie("cashKey",'true',{expires: 7,path:"/"});
	});
	$("._cashHide").on('click',function(){
		$(this).hide();
		$("._myCashAmount").hide();
		$("._cashShow").show();
		$.cookie("cashKey",'false',{expires: 7,path:"/"});
	});
	if($.cookie("cashKey")=='true'){
		$("._cashShow").trigger('click');
	}else{
		$("._cashHide").trigger('click');
	}
});

var head = function() {
	this.refreshLottery = function(data) {
		for (var i = 0; i < data.groupList.length; i++) {
			var group = data.groupList[i];
			var row = $("<div class='mainNav_row clearfix'>");
			var img = $("<img class='fl'>").attr(
					'src',
					ctx+'/jsp/img/img_' + group.lotteryGroup
							+ '.jpg');
			var ul = $("<ul class='fl clearfix'>");
			for (var j = 0; j < group.lotteryList.length; j++) {
				var lot = group.lotteryList[j];
				var lihtml = "<a href='javascript:head.switchLottery(\""
					+ lot.lotteryCode + "\",\""
					+ group.lotteryGroup + "\");\'> <span>"
					+ lot.lotteryName +"</span>";
				
				if(lot.rsvst2!=""&&lot.rsvst2!=undefined){
					var tipInfo = "";
					if(lot.rsvst2=="h"){
						tipInfo = "<label class='icon-fire ml5'>火</label>";
					}else if(lot.rsvst2=="r"){
						tipInfo = "<label class='icon-hot ml5'>热</label>";
					}else if(lot.rsvst2=="x"){
						tipInfo = "<label class='icon-new ml5'>新</label>";
					}
					lihtml += tipInfo;
				}
				var li = $("<li>").html(lihtml+ "</a>");
				ul.append(li);
			}
			row.append(img);
			row.append(ul);
			$(".mainNavDiv").append(row);
		}
		$(".mainNav_row:last").css("border", "none");
	};
	var msgCount = function(data){
		var count = data.msgCount;
		$("._cwximsg").text(count);
	};
	return {
		init : function() {
			this.showAllLotterys();
			formPost.submitAjaxForm1("getAllLottery.shtml", null, null,
					refreshLottery, null);
			formPost.submitAjaxForm1("getCashAmount.shtml", null, null,
					this.refreshCash, null);
		},
		switchLottery : function(code, group) {
			var attrs = new Array();
			attrs.push("lotteryCode");
			var values = new Array();
			values.push(code);

			var action = "";
			if (group == "SSC") {
				action = ctx+"/showssc.html";
			} else if (group == "SYXW") {
				action = ctx+"/show11x5.html";
			}else if(group == "3D"){
				action = ctx+"/show3d.html";
			}
			formPost.submitForm(action, attrs, values, true);
		},
		refreshCash : function(data) {
			$("._myCashAmount").each(function() {
				$(this).find("span").html(data.cashAmount);
			});
			var ca = $("#headCashAmount").text();
			ca = NumberFormat.formatNumber(ca,4,1);
			$("#headCashAmount").text(ca);
		},
		getAwardUsers:function(){
			formPost.submitAjaxForm("getAwardUsers.shtml", null, null,this.refreshAwardUsers);
		},
		refreshAwardUsers:function(data){
//			console.info(data);
		},
		getMsgCount:function(){
			formPost.submitAjaxForm1("getmsgcount.shtml", null, null,msgCount,null);
		},
		showAllLotterys:function(){
			//取得菜种菜单导航的父级绝对left，并设置给导航
			var manNavLeft = $(".mainNavDiv").parent().offset().left;
			var manNavTop = $(".mainNavDiv").parent().offset().top+45;
			$(".mainNavDiv").css({
				left:manNavLeft,
				top:manNavTop
			});
		}
	};
}();
