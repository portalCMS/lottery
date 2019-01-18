$(document).ready(function() {
	var ue = UE.getEditor('editor');
	ue.addListener("ready", function () {
        ue.setContent($("#content").val());
	});
	$("#reg_menu").addClass("active");
	
	$("#startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});
	});
	$("#endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});
	});
	$("#regStartTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#regEndTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	 $.checkFrom.initBind(".checkedAttr", true, true,true);
	 
	 $("#createActivity").on('click',function(){
		 regActivity.createActivity(this);
	});
});

var regActivity = function(){
	
	return {
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
				if(name=="regStartTime"||name=="regEndTime"||name=="ativityMoney"){
					attrs.push("registerTempl."+name);
					vals.push(val);
				}else if(name!="summary"){
					attrs.push(name);
					vals.push(val);
				}
				
			});
			
			attrs.push("model");
			var model = $("#awardType").val();
			vals.push(model);
			
			attrs.push("sourceType");
			var sourceType = $("#sourceType").val();
			vals.push(sourceType);
			
			attrs.push("type");
			vals.push("REG");
			
			var ue = UE.getEditor('editor');
			var content = ue.getContent();
			attrs.push("summary");
			vals.push(content);
			
			console.info(attrs);
			console.info(vals);
			formPost.submitAjaxForm("activity/createRegTempl.do",attrs,vals,this.saveSuccess);
		},
		saveSuccess:function(data){
			var info= data.success;
			formPost.showErrorMessage(info);
		}
	};
}();