/***
 * 倒计时js
 */
var countdown = function() {
	var timeOutTimes=0;
	return {
		time_callback : function(){  
			//截至投注倒计时初始化
			 var bh = parseInt($("#end_time_hour").text());
			 var bm = parseInt($("#end_time_minute").text());
			 var bs = parseInt($("#end_time_second").text());
			 if(bh==0){
				 $("#end_time_hour").hide(); 
				 $("#hour").hide(); 
				 $("#end_time_hour").next().hide(); 
			 }
			 
			 if((bh==0&&bm==0&&bs==1)||bm<0||bs<0
					  ||isNaN(bm)||isNaN(bs)){
				 timeOutTimes++;
				 if(timeOutTimes>20){
					 //20次请求不到后台信息，则跳转到登录页面。
					return;
				 }
				 countdown.getCurrTask();
				  if(bh==0&&bm==0&&bs==1){
					  var msgstr = "您好,"+$(".series").eq(0).html()+"期已截止,投注时请确认您的期号。";
					  msg.alertMessage(msgstr,"alert");
					  //msg.alertMsgBox(msgstr);
					  //window.location.reload();
				  }
			  }else if(bh>0&&bm==0&&bs==0){
				 bh = bh-1;
				 bm = 59;
				 bs = 59;
				 if(bh<10){
					 $("#end_time_hour").text("0"+bh);
					 $("#hour").text("0"+bh); 
				 }else{
					 $("#end_time_hour").text(bh);
					 $("#hour").text(bh); 
				 }
				
				 $("#end_time_hour").show();
				 $("#hour").show();
				 this.setMinSec(bm,bs);
			  }else if(bs==0){
				  bs = 59;
				  bm = bm -1;
				  this.setMinSec(bm,bs);
			  }else{
				  bs = bs -1;
				  this.setMinSec(bm,bs);
			  }
		 },
		 setMinSec : function(bm,bs){
			 if(0<=bs&&bs<10){
				  var temp1 = '0'+bs;
				  $("#end_time_second").text(temp1);
				  $("#second").html(temp1+"秒");
			  }else{
				  $("#end_time_second").text(bs);
				  $("#second").html(bs+"秒");
			  }
			  if(0<=bm&&bm<10){
				  var temp2 = '0'+bm;
				  $("#end_time_minute").text(temp2);
				  $("#minute").html(temp2+"分");
			  }else if(bm>=10){
				  $("#end_time_minute").text(bm);
				  $("#minute").html(bm+"分");
			  }
		 },
		 getCurrTask:function(){
				var attrs = new Array();
				 attrs.push("lotteryCode");
				 attrs.push("spanTime");
				 attrs.push("lastOpenIssue");
				 var vals = new Array();
				 vals.push($("#lotCode").val());
				 var lotCode = $("#lotCode").val();
				 var spanTime = "";
				 if($("#lotGroup").val()=="SSC"){
					 vals.push(sscdata.getSpanTime());
					 spanTime = sscdata.getSpanTime();
				 }else if($("#lotGroup").val()=="SYXW"){
					 vals.push(spanTime);
					 spanTime=spanTime;
				 }
				 
				 var newNum = $("#recordsTbody").find("._newNum").html();
				 //如果开奖n次失败，最近10期无开奖号码则设置最近开奖期号为0。
				 var lastOpenIssue="";
				 if(newNum==undefined){
					 vals.push("0");
					 lastOpenIssue="0";
					 
				 }else{
					 var issueNo =  $("#recordsTbody").find("._newNum").closest("tr").find("td").eq(1).html();
					 vals.push(issueNo);
					 lastOpenIssue=issueNo;
				 }
				
				 var action = "newCurrTask.shtml";
					$.ajax({
					type: "POST",
					url: "newCurrTask.shtml",
					dataType: "json",
					data:{lotteryCode:lotCode,spanTime:spanTime,lastOpenIssue:lastOpenIssue },
					success: function(data) {
						countdown.taskSuccess(data);
					},
					error: function(){
					}
				})
				 //formPost.submitAjaxForm(action,attrs,vals,this.taskSuccess);
			 },
			 taskSuccess:function(data){
				 //刷新新一期的倒计时
				 $("#endBetTime").val(data.task.endBetTime);
				 $("#end_time_minute").text(data.betMinutes);
				 $("#end_time_second").text(data.betSeconds);
				 $(".series").html(data.task.lotterySeries);
				//如果当前期值变化则重新显示追期
				// betUtil.updatePeriod();
				 //更新显示开奖记录表
				 //betUtil.refreshRecords(data);
			 }
	};
}();


