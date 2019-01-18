var lotteryEngine = function(){
	
	return{
		initData:function(){
			$("._lgInfo").eq(0).addClass("active");
			$("._lotInfo").eq(0).show();
			setInterval("lotteryEngine.time_callback()",1000);
		},
		showLotteryGroup:function(obj){
			$(".groupclass").attr('class','groupclass');
			$(obj).parent().attr('class','active groupclass');
			var showId = $(obj).attr('name');
			$(".nav_content").hide();
			$("#"+showId).show();
		},
		initCreateLottery:function(action,lotteryGroup){
			var attrs = new Array();
			attrs.push("lotteryGroup");
			var values = new Array();
			values.push(lotteryGroup);
			formPost.submitForm(action,attrs,values);
		},
		showLotteryDetail:function(action,lotteryCode,lotteryGroup){
			var attrs = new Array();
			attrs.push("lotteryCode");
			attrs.push("lotteryGroup");
			var values = new Array();
			values.push(lotteryCode);
			values.push(lotteryGroup);
			formPost.submitForm(action,attrs,values);
		},
		time_callback:function(){
		  var intHours = parseInt($("#hour").val());
		  var intMinutes = parseInt($("#minutes").val());
		  var intSeconds = parseInt($("#seconds").val());
		  
		  intSeconds = intSeconds +1 ;
		  if(intSeconds >59){
			  intSeconds = 0;
			  intMinutes = intMinutes +1 ;
			  if(intMinutes>59){
				  intMinutes = 0;
				  intHours = intHours +1;
				  if(intHours>23){
					  intHours = 0;
				  }
			  }
		  }
		  
		  $("#hour").val(intHours);
		  $("#minutes").val(intMinutes);
		  $("#seconds").val(intSeconds);
		  
		  if (intHours < 10) {
			  intHours = "0"+intHours+" : ";
		  } else {
			  intHours = intHours+" : ";
		  }
		  if (intMinutes < 10) {
			  minutes = "0"+intMinutes+" : ";
		  } else {
			  minutes = intMinutes+" : ";
		  }
		  if (intSeconds < 10) {
			  seconds = "0"+intSeconds+" ";
		  } else {
			  seconds = intSeconds+" ";
		  }
	      $("#time_view").html("  " + intHours+minutes+seconds);
		}
	};
}();
$(function() {
	lotteryEngine.initData();
});
