$(document).ready(function() {
	var ue = UE.getEditor('editor');
	ue.addListener("ready", function () {
        ue.setContent($("#content").val());
	});
	$("#bet_menu").addClass("active");
	
	$("._addConfig").on('click',function(){
		betActivity.addAmountConfig(this);
	});
	$("._deleteConfig").on('click',function(){
		betActivity.deleteAmountConfig(this);
	});
	$("#createActivity").on('click',function(){
		betActivity.createActivity(this);
	});
	
	$("#startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});
	});
	$("#endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});
	});
	$("#countStartTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#countEndTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	 $.checkFrom.initBind(".checkedAttr", true, true,true);
});

var betActivity = function(){
	
	return {
		addAmountConfig:function(obj){
			var $config = $(obj).closest("._amountConfig").clone(true);
			$config.find("input").each(function(){
				$(this).val("");
			});
			$config.find("._deleteConfig").show();
			$("#configDiv").append($config.clone(true));
			$("#configDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
		},
		deleteAmountConfig:function(obj){
			$(obj).closest("._amountConfig").remove();
			$("#configDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
		},
		createActivity:function(obj){
			var check = $.checkFrom.checkAllType("#cretaActivity");
			if(!check){
				return;
			}
			
			var attrs = new Array();
			var vals = new Array();
			$("#cretaActivity").find("input[type='text']").each(function(){
				var name = $(this).attr("name");
				var val = $(this).val();
				if(name=="minGameAmount"||name=="maxAwardAmount"||name=="cycleDays"||name=="limitDays"||name=="limitCount"){
					attrs.push("betTempl."+name);
					vals.push(val);
				}else if(name!="minAmount" && name!="maxAmount" && name!="awardAmount"&&name!="summary"){
					attrs.push(name);
					vals.push(val);
				}
				
			});
			
			attrs.push("type");
			attrs.push("betTempl.cycleType");
			attrs.push("model");
			attrs.push("betTempl.isHistory");
			attrs.push("betTempl.countStartTime");
			attrs.push("betTempl.countEndTime");
			var type = $("#actType").val();
			var model = $("#awardType").val();
			var cycleType = $("#cycleType").val();
			var ish = $("#isHistory").val();
			var cstime = $("#countStartTime").val();
			var cetime = $("#countEndTime").val();
			vals.push(type);
			vals.push(cycleType);
			vals.push(model);
			vals.push(ish);
			vals.push(cstime);
			vals.push(cetime);
			
			
			//金额配置区域
			$("#configDiv").find("._amountConfig").each(function(index){
				var cname = "betTempl.amountConfig["+index+"].";
				$(this).find("input[type='text']").each(function(){
					attrs.push(cname+$(this).attr("name"));
					vals.push($(this).val());
				});
			});
			var ue = UE.getEditor('editor');
			var content = ue.getContent();
			attrs.push("summary");
			vals.push(content);
			
			attrs.push("sourceType");
			var sourceType = $("#sourceType").val();
			vals.push(sourceType);
			
			formPost.submitAjaxForm("activity/createBetTempl.do",attrs,vals,this.saveSuccess);
		},
		saveSuccess:function(data){
			var info= data.success;
			formPost.showErrorMessage(info);
		}
	};
}();