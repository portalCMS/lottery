$(function() {
	//记得要修改这里的地址部署的时候
	var jobctx = "ws://localhost:8090/lottery_job";
	var jobws = new WebSocket(jobctx+"/message?user="+"job");
	jobws.onopen = function(){};
	jobws.onmessage = function(message){
		//开奖后将中奖用户列表发送回来，利用socket.io的client.js送前台webapp发送中奖人列表
		if(message.data.indexOf("userIdStrs")!=-1){
			console.info(message.data);
		}
	};
	 
});

