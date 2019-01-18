$(document).ready(function() {
	$("._hidLi:last").hide();
	$("._actTitle").on('click',function(){
		activity.showActDetail(this);
	});
	
	$("._actPicurl").on('click',function(){
		activity.showActDetail(this);
	});
	
	$("._myactPic").on('click',function(){
		activity.showMyActDetail(this);
	});
	
	$("._myactTitle").on('click',function(){
		activity.showMyActDetail(this);
	});
	
	$("._getAward").on('click',function(){
		activity.getActivityAward(this);
	});
	
	activity.querMyActivityRecord(1);
	
	$("#pre").on('click',function(){
		activity.prePage(this);
	});
	
	$("#next").on('click',function(){
		activity.nextPage(this);
	});

});

var activity = function() {
	var callBackFuc = {
			"resumeAward":function(){
				//领奖返回后马上恢复获取领奖按钮
				var obj = $(".mediumDisable");
				$(obj).addClass("mediumBtn");
				$(obj).removeClass("mediumDisable");
				$("._getAward").find("span").text("领取奖励");
			}
		};
	return {
		resumeAward:function(){
			//领奖返回后马上恢复获取领奖按钮
			var obj = $(".mediumDisable");
			$(obj).addClass("mediumBtn");
			$(obj).removeClass("mediumDisable");
			$("._getAward").find("span").text("领取奖励");
		},
		prePage:function(){
			var pageNum = parseInt($("#pageNum").text());
			if(pageNum>1){
				this.querMyActivityRecord(pageNum-1);
			}else{
				formPost.showErrorMessage("当前已是第一页！");
			}
		},
		nextPage:function(){
			var pageNum = parseInt($("#pageNum").text());
			var totalPage =  parseInt($("#totalPage").text());
			if(totalPage>pageNum){
				this.querMyActivityRecord(pageNum+1);
			}else{
				formPost.showErrorMessage("当前已是尾页！");
			}
		},
		querMyActivityRecord:function(pageNum){
			var attrs = new Array();
			attrs.push("pageNum");
			attrs.push("maxCount");
			var vals = new Array();
			vals.push(pageNum);
			vals.push(5);
			formPost.submitAjaxForm("activity/querMyActivityRecord.shtml",attrs,vals,activity.myactSuccess);
		},
		myactSuccess:function(data){
			$(".activityRUl").empty();
			var $hidLi = $("._hidLi:last").clone(true);
			$hidLi.show();
			var myList = data.page.pagelist;
			var pageNum = data.page.pageNum;
			var totalPage = data.page.pageCount;
			$("#pageNum").html(pageNum);
			$("#totalPage").html(totalPage);
			for(var i=0;i<myList.length;i++){
				var myact = myList[i];
				$hidLi.find("._myactPic").attr("src",myact.picurl);
				$hidLi.find("._myActId").val(myact.id);
				$hidLi.find("._myactTitle").html(myact.title);
				$hidLi.find("._award").html(myact.rsvdc1);
				if(myact.status==17002){
					$hidLi.find("._status").html("成功");
				}else{
					$hidLi.find("._status").html("处理中");
				}
				var getTime = myact.endtime;
				$hidLi.find("._getTime").html(getTime.substring(5,getTime.length-2));
				$(".activityRUl").append($hidLi.clone(true));
			}
			$("._award").formatCurrency();
			
		},
		showActDetail : function(obj) {
			var actId = $(obj).closest(".activityBox").find("._actId").val();
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(actId);
			formPost.submitForm("activityDetail.html",attrs,vals);
		},
		showMyActDetail : function(obj) {
			var actId = $(obj).next().val();
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(actId);
			formPost.submitForm("activityDetail.html",attrs,vals);
		},
		getActivityAward : function(obj){
			//领奖申请中马上取消获取奖金的绑定
			if($(obj).hasClass("mediumDisable")){
				return;
			}
			$(obj).removeClass("mediumBtn");
			$(obj).addClass("mediumDisable");
			$(obj).find("span").text("领取中...");
			
			var actId = $(obj).closest(".activityBox").find("._actId").val();
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(actId);
			
			//通过actId来辨别到底要恢复哪个button的启用
			formPost.setErrorFunc(callBackFuc["resumeAward"]);
			formPost.submitAjaxForm1("activity/getActivityAward.shtml",attrs,vals,this.getAwardSuccess,null);
		},
		getAwardSuccess:function(data){
			//领奖返回后马上恢复获取领奖按钮
			activity.resumeAward();
			
			var result = data.returnMap;
			if(result.showInfo){
				formPost.showErrorMessage(result.showInfo);
				//刷新用户余额
				$("._myCashAmount").click();
			}
			
		}
	};
}();
