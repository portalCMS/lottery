
var quotaManage = function(){
	var allLowerUserArray = [];
	return{
		getUserQuota:function(){
			if($("#cuId")==undefined||$("#cuId").val()==""){
				return;
			}
			var attrs = new Array();
			attrs.push("cuId");
			
			var vals = new Array();
			vals.push($("#cuId").val());
			
			formPost.submitAjaxForm1("team/getUserQuotaCount.shtml", attrs, vals,this.userQuotaSuccess,null);
		},
		userQuotaSuccess:function(data) {
			$("#giveQuotaTb").find("tr").each(function(){
				$(this).find("._uqc").text(0);
				for(var i=0;i<data.cq.length;i++){
					var lqid = data.cq[i].id;
					var rb = data.cq[i].proportion;
					var rbc = data.cq[i].quota_count;
					if($(this).find("._qrb").val()==rb){
						$(this).find("._uqc").text(rbc);
						$(this).find("._lqid").val(lqid);
					}
				}
			});
		},
		changeUser:function(){
			$("#customerUserName").text($("#cuId").find("option:selected").text());
			var rb =$("#cuId").find("option:selected").attr("name");
			var orb = rb;
			rb = NumberFormat.formatNumber(rb*100,1,0)+"";
			rb = rb.substring(0,4)+"%";
			$("#userRebates").text(rb);
			$("#giveQuotaTb").find("tr").each(function(){
				var qrb = $(this).find("._qrb").val();
				if(qrb>orb){
					$(this).find("._gqc").val("");
					$(this).hide();
				}else{
					$(this).show();
				}
			});
			this.getUserQuota();
		},
		allocationSumit:function(type){
			if($("#cuId")==undefined || $("#cuId").val()==""){
				formPost.showErrorMessage("亲，请至少选择一个目标用户进行操作");
				return;
			}
			var attrs = new Array();
			var vals= new Array();
//			quotaManage.allocationpackgedata(attrs,value);
			attrs.push("cuId");
			attrs.push("changeQuotaType");
			attrs.push("qids");
			attrs.push("changeCounts");
			attrs.push("token");
			
			vals.push($("#cuId").val());
			vals.push(type);
			
			//循环所有已经填写了配额数量的返点
			var gqcs="";
			var qids="";
			
			var isStop = false;
			$("#giveQuotaTb").find("tr").each(function(){
				var mqc = $(this).find("._mqc").text();
				var gqc = $(this).find("._gqc").val();
				var qid = $(this).find("._qid").val();
				if(type=="recycle"){
					qid = $(this).find("._lqid").val();
					mqc = $(this).find("._uqc").text();
				}
				
				if(gqc!=""&&parseInt(mqc)<parseInt(gqc)){
					isStop = true;
					return;
				}
				if(gqc!=""&&gqc>0){
					if(gqcs==""){
						gqcs +=gqc;
						qids +=qid;
					}else{
						gqcs +=","+gqc;
						qids +=","+qid;
					}
				}
			});
			if(isStop){
				formPost.showErrorMessage("亲，配额数量必须为正数，并且不能大于本身的剩余配额");
				return;
			}
			if(qids==""){
				formPost.showErrorMessage("亲，请至少填写一个配额数量");
				return;
			}
			vals.push(qids);
			vals.push(gqcs);
			vals.push($("#token").val());
			
			formPost.submitForm("changeUserQuota.html",attrs,vals,true);
		},
		allocationpackgedata:function(attrs,value){
			$(".allocation").each(function(){
				attrs.push($(this).attr("name"));
				value.push($(this).val());
			});
		},
		recycleSubmit:function(){
			var attrs = new Array();
			var value = new Array();
			quotaManage.recyclepackgedata(attrs,value);
			formPost.submitForm1("changeUserQuota.html",attrs,value,"#d2");
		},
		recyclepackgedata:function(attrs,value){
			$(".recycle").each(function(){
				attrs.push($(this).attr("name"));
				value.push($(this).val());
			});
		},
		setAllLowerUserArray:function(userArray){
			allLowerUserArray =  userArray;
		},
		getAllLowerUserArray:function(){
			return allLowerUserArray;
		},
		//恢复所有的下拉选项
		resumeAllUser:function(){
			$("#cuId").find("option").each(function(){
				$(this).remove();
			});
			
			var userAllArray = quotaManage.getAllLowerUserArray();
			for(var j=0;j<userAllArray.length;j++){
				var userInfo = userAllArray[j];
				var $opt = $("<option>").attr("value",userInfo.split(",")[0]).attr("name",userInfo.split(",")[2])
				.text(userInfo.split(",")[1]);
				$("#cuId").append($opt);
			}
			$("#cuId").find("option[index='0']").attr("selected",'selected');
		}
	};
}();
$(document).ready(function() {
	$(".allocationBtn").click(function(){
		quotaManage.allocationSumit("distribution");
	});
	$.checkFrom.initBind("#changeCount", true, true);
	$(".recycleBtn").click(function(){
		quotaManage.allocationSumit("recycle");
	});
	$("#quotaManageMenu").addClass("current");
	$.checkFrom.initBind("#changeCount1", true, true);
	
	$("#userName").val($("#cuId").find("option:selected").text());
	$("#cuId").on('change',function(){
		$("#userName").val($("#cuId").find("option:selected").text());
		quotaManage.changeUser();
	});
	
	var userArray = [];
	$("#cuId option").each(function(){
		var text = $(this).text();
		var val = $(this).val();
		var name = $(this).attr("name");
		userArray.push(val+","+text+","+name);
	});
	quotaManage.setAllLowerUserArray(userArray);
	
	$(".recycleBtn").hide();
	$("#cuId").trigger("change");
	
	$("#userTab a").on('click',function(){
		var aid = $(this).attr("id");
		$("#giveQuotaTb").find("th").each(function(){
			if(aid=="giveA"){
				$(this).find("span").text("分配");
				$(".allocationBtn").show();
				$(".recycleBtn").hide();
			}else{
				$(this).find("span").text("回收");
				$(".recycleBtn").show();
				$(".allocationBtn").hide();
			}
		});
	});
	
	$("#userName").on('change keyup',function(){
		//先恢复所有下拉用户
		quotaManage.resumeAllUser();
		//先查询是否有完全相同的用户名，如果有则不用模糊查询
		var isHave = false;
		$("#cuId").find("option").each(function(){
			if($(this).text()==$("#userName").val()){
				$("#cuId").val($(this).val());
				quotaManage.changeUser();
				isHave = true;
				var curUserId = $(this).val();
				$("#cuId").find("option").each(function(){
					if($(this).val()!=curUserId){
						$(this).remove();
					}
				});
				return;
			}
		});
		if(!isHave){
			//模糊查询匹配
			var getUser = false;
			$("#cuId").find("option").each(function(){
				if($(this).text().indexOf($("#userName").val())!=-1){
					if(!getUser){
						getUser = true;
						$("#cuId").val($(this).val());
						quotaManage.changeUser();
					}
				}else{
					$(this).remove();
				}
			});
			//如果查询不到任何用户，则恢复所有选项
			if(!getUser){
				quotaManage.resumeAllUser();
			}
		}
		
	});
});