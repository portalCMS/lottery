$(function() {
	$("#closeAudio").on('click',function(){
		$(this).hide();
		$("#openAudio").show();
		$.cookie("audioKey",'true',{expires: 7,path:"/"});
	});
	$("#openAudio").on('click',function(){
		$(this).hide();
		$("#closeAudio").show();
		$.cookie("audioKey",'false',{expires: 7,path:"/"});
	});
	if($.cookie("audioKey")=='true'){
		$("#closeAudio").trigger('click');
	}else{
		$("#openAudio").trigger('click');
	}
	
	webscoket.getUserName();
	webscoket.chargeDrawCount();
	
	
});

var webscoket = function(){
	var customerCount=0;
	var onLineUserNames="";
	var timer;
	return{
		getUserName:function(){
			formPost.submitAjaxForm("getUserName.do",null,null,webscoket.initWS);
		},
		chargeDrawCount:function(){
			formPost.submitAjaxForm("chargeDrawCount.do",null,null,webscoket.loopCheck);
		},
		initWS:function(data){
			//记得要修改这里的地址部署的时候
			var jobws = new WebSocket(jobctx+"/message?user="+data.userName);
			jobws.onopen = function(){};
			jobws.onmessage = function(message){
				if(message.data.indexOf("userIdStrs")==-1){
					if(message.data.indexOf("工具类")==-1){
						webscoket.startAlarm("alarm");
					}else{
						//拿不到自主号源不提示
						return;
					}
					var msg = messenger.cofirmMsg(
						"奖期任务报警："+message.data,
						function (){
							webscoket.stopAlarm("alarm");
						},
						function (){
							webscoket.stopAlarm("alarm");
						});
					msg.update({hideAfter:10000000});
				}
			};
			
			//前台有充值或提单申请时后台响铃
			var webws = new WebSocket(webctx+"/message?user="+data.userName);
			webws.onopen = function(){};
			webws.onmessage = function(message){
				//如果传回来的是个整数即是统计在线人数的回馈。
				if(message.data.indexOf("onLineUser")!=-1){
					var onLineUserInfo = message.data;
					var userInfoArr = onLineUserInfo.split(";");
					var userCount = userInfoArr[0].substring(16);
					var userNames = userInfoArr[1].substring(16);
					customerCount = parseInt(userCount);
					onLineUserNames=userNames;
				}else{
					webscoket.chargeDrawCount();
					//如果当前管理员的页面在充提查询页面则重新刷新查询一次
					orders.init();
				}
				
			};
		},
		startAlarm:function(id){
			if($.cookie("audioKey")=='false'){
				var audio = document.getElementById(id);
				audio.play();
			}else{
				this.stopAlarm(id);
			}
		},
		stopAlarm:function(id){
			var audio = document.getElementById(id);
			audio.pause();
		},
		loopCheck:function(data){
			$("._rechargeCount").html(data.chargeCount);
			$("._drawingCount").html(data.drawCount);
			$("._activityCount").html(data.activityCount);
			clearInterval(timer);
			timer = setInterval("webscoket.checkChargeDrawCount()", 2000);
		},
		/**
		 * 检查充提单是否已经没有了，没有的话停止播放提示音。
		 */
		checkChargeDrawCount:function(){
			var chargeCount = parseInt($("._rechargeCount").html());
			var drawCount = parseInt($("._drawingCount").html());
			var actCount = parseInt($("._activityCount").html());
			if(actCount==0&&chargeCount==0&&drawCount==0){
				webscoket.stopAlarm("charge");
			}else{
				webscoket.startAlarm("charge");
			}
		},
		getCustomerCount:function(){
			return customerCount;
		},
		getOnLineUserNames:function(){
			return onLineUserNames;
		}
	};
}();

var onlinePeoPle = function(){
	var onLineUserNames="";
	var lockFunction = false;
	var init = function(data){
		onLineUserNames = data.peos;
		lockFunction=false;
	};
	var getUserNames = function(){
		if(lockFunction == true)return;
		lockFunction = true;
		formPost.submitAjaxForm("user/getpeos.do?webUrl="+webctx,null,null,init);
	};
	return{
		getCustomerCount:function(){
			getUserNames();
			if(onLineUserNames!=""){
				return onLineUserNames.split(",").length;
			}
			return 0;
		},
		getOnLineUserNames:function(){
			return onLineUserNames;
		}
	};
}();