$(document).ready(function() {
	var ue = UE.getEditor('editor');
	ue.addListener("ready", function () {
        ue.setContent($("#content").val());
	});
	$("#luck_menu").addClass("active");
	
	$("._addLevel").on('click',function(){
		luckActivity.addLevel(this);
	});
	$("._delLevel").on('click',function(){
		luckActivity.delLevel(this);
	});
	
	$("._addAwardArea").on('click',function(){
		luckActivity.addAwardArea($(this));
	});
	
	$("._deleteAwardArea").on('click',function(){
		luckActivity.deleteAwardArea($(this));
	});
	
	$("#createActivity").on('click',function(){
		luckActivity.createActivity(this);
	});
	
	$("#preTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#closeTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#startTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	$("#endTime").bind("click",function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	});
	 $.checkFrom.initBind(".checkedAttr", true, true,true);
	 
	 $("#noMaxLimit").on('click',function(){
		 if($(this).prop("checked")){
			 $("#maxAwardAmount").hide();
			 $("#maxAwardAmount").val(99999999);
		 }else{
			 $("#maxAwardAmount").show();
			 $("#maxAwardAmount").val("");
		 }
	 });
	 
	 $("#awardDiv").find("._awardRank").each(function(index){
			$(this).val(index+1);
		});
	 
	 $("._awardLevel").each(function(index){
			$(this).val(index+1);
		}); 
});

var luckActivity = function(){
	
	return {
		addAwardArea:function(obj){
			var $awardArea = $(obj).closest("._awrdArea").clone(true);
			$awardArea.find("input").val("");
			$("#awardDiv").append($awardArea);
			$("#awardDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
			$("#awardDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
			$("#awardDiv").find("._awardRank").each(function(index){
				$(this).val(index+1);
			});
			$awardArea.find("._awardLevel").each(function(index){
				$(this).val(index+1);
			}); 
		},
		deleteAwardArea:function(obj){
			if($("#awardDiv").find("._mytb").size()==1){
				return;
			}
			$(obj).closest("._awrdArea").remove();
			$("#awardDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
			$("#awardDiv").find("._awardRank").each(function(index){
				$(this).val(index+1);
			});
		},
		addLevel:function(obj){
			var $tr = $(obj).closest("tr").clone(true);
			$tr.find("input").each(function(){
				$(this).val("");
			});
			$tr.find("._addLevel").html("加");
			$(obj).closest("._mytb").append($tr.clone(true));
			$(obj).closest("._mytb").find("._li").each(function(index){
				$(this).text(index+1);
			});
			$(obj).closest("._mytb").find("._awardLevel").each(function(index){
				$(this).val(index+1);
			});
		},
		delLevel:function(obj){
			if($(obj).closest("tr").find("._li").text()==1){
				return;
			}
			$(obj).closest("tr").remove();
			$("#awardTb").find("._li").each(function(index){
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
			$("#cretaActivity").find("._name").each(function(){
				var name = $(this).attr("name");
				var val = $(this).val();
				attrs.push(name);
				vals.push(val);
			});
			
			attrs.push("type");
			attrs.push("model");
			attrs.push("userType");
			var type = $("#actType").val();
			var model = $("#awardType").val();
			var userType = $("#userType").val();
			vals.push(type);
			vals.push(model);
			vals.push(userType);
			
			//奖区配置区域
			$("#awardDiv").find("._awrdArea").each(function(index){
				var cname = "luckTempls["+index+"].";
				$(this).find("._amountConfig").find("input[type='text']").each(function(){
					attrs.push(cname+$(this).attr("name"));
					vals.push($(this).val());
				});
				$(this).find("._mytb").find("._cds").each(function(){
					attrs.push(cname+$(this).attr("name"));
					vals.push($(this).val());
				});
				$(this).find("._amountConfig").find("input[type='hidden']").each(function(){
					attrs.push(cname+$(this).attr("name"));
					vals.push($(this).val());
				});
				$(this).find("#awardTb").find("tr").each(function(index2){
					$(this).find("input[type='text']").each(function(){
						attrs.push(cname+"levelTemps["+index2+"]."+$(this).attr("name"));
						vals.push($(this).val());
					});
					$(this).find("input[type='hidden']").each(function(){
						attrs.push(cname+"levelTemps["+index2+"]."+$(this).attr("name"));
						vals.push($(this).val());
					});
				});
			});
			var ue = UE.getEditor('editor');
			var content = ue.getContent();
			attrs.push("summary");
			vals.push(content);
			
			attrs.push("sourceType");
			var sourceType = $("#sourceType").val();
			vals.push(sourceType);
			
			console.info(attrs);
			console.info(vals);
			formPost.submitAjaxForm("activity/createLuckTempl.do",attrs,vals,this.saveSuccess);
		},
		saveSuccess:function(data){
			var info= data.success;
			formPost.showErrorMessage(info);
		}
	};
}();