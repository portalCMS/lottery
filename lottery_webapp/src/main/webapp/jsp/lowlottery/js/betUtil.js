/***
 * 倒计时js
 */
var countdown = function() {
	var timeOutTimes=0;
	return {
		clearTimer:function(){
			$(".bgIframe").remove();
			$(".msg").remove();
		},
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
					formPost.submitForm("home.html",null,null);
					return;
				 }
				 betUtil.getCurrTask();
				  if(bh==0&&bm==0&&bs==1){
					  var msg = "您好,"+$("#currentIssno").html()+"期已截止,投注时请确认您的期号。";
					  $("body").sMsg({
							content : msg ,
							autoDisplay : true,
							actionEvent : ""
						});
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
		 }
	};
}();


var betUtil = function(){
	return{
		removeSame : function(str){
			var list = str.split(",");
			var newmap = new Map();
			for(var i=0;i<list.length;i++){
			   newmap.put(list[i],"1");
			}
			 var keys = newmap.keySet();
			 var newstr = "";
			 for(var i=0;i<keys.length;i++){
				if(newstr==""){
					newstr+=keys[i];
				}else{
					newstr+=","+keys[i];
				}
			 }
			 return newstr;
		},
		getCurrTask:function(){
			var attrs = new Array();
			 attrs.push("lotteryCode");
			 attrs.push("spanTime");
			 attrs.push("lastOpenIssue");
			 var vals = new Array();
			 vals.push($("#lotCode").val());
			 if($("#lotGroup").val()=="SSC"){
				 vals.push(sscdata.getSpanTime());
			 }else if($("#lotGroup").val()=="SYXW"){
				 vals.push(spanTime);
			 }
			 
			 var newNum = $("#recordsTbody").find("._newNum").html();
			 //如果开奖n次失败，最近10期无开奖号码则设置最近开奖期号为0。
			 if(newNum==undefined){
				 vals.push("0");
			 }else{
				 var issueNo =  $("#recordsTbody").find("._newNum").closest("tr").find("td").eq(1).html();
				 vals.push(issueNo);
			 }
			 
			 var action = "newCurrTask.shtml";
			 formPost.submitAjaxForm(action,attrs,vals,this.taskSuccess);
		 },
		 taskSuccess:function(data){
			 //刷新新一期的倒计时
			 $("#endBetTime").val(data.task.endBetTime);
			 $("#end_time_minute").text(data.betMinutes);
			 $("#end_time_second").text(data.betSeconds);
			 $("#currentIssno").html(data.task.lotterySeries);
			//如果当前期值变化则重新显示追期
			 betUtil.updatePeriod();
			 //更新显示开奖记录表
			 betUtil.refreshRecords(data);
		 },
		 /**
		  * 更新显示开奖记录表
		  */
		 refreshRecords : function(data) {
			 //服务器返回为true才更新表格。
			 if(data.refresh=="true"){
				 var html ="";
				 $("#recordsTbody").hide();
				 var lastNum = $("#recordsTbody").find("._newNum").html();
				 var newNum ;
				 $("#recordsTbody").html("");
				 var td ="<td height='37' align='center' valign='middle'>";
				 var redtd ="<td height='37' align='center' valign='middle' class=\"_newNum color_red\">";
				 html +="<tr>"+td+data.task.startBetTime+"</td>"+td+data.task.lotterySeries+"</td>"
				 		 +td+"正在销售</td></tr>";
				 var j=0;
				 for(var i=0;i<data.records.length;i++){
					 var record = data.records[i];
					 var succHtml = "<tr>"+td+record.openTime+"</td>"+td+record.issue+"</td>"
					 					+td+record.lotteryNumber+"</td></tr>";
					 var failedHtml = "<tr>"+td+record.endTime+"</td>"+td+record.issue+"</td>"
										+td+"开奖失败</td></tr>";
					 var runHtml = "<tr>"+td+record.startTime+"</td>"+td+record.issue+"</td>"
										+td+"正在开奖</td></tr>";
					 var waitHtml = "<tr>"+td+record.startTime+"</td>"+td+record.issue+"</td>"
										+td+"等待开奖</td></tr>";
					 switch(record.status)
					 {
					 case 10005:
					 case 10013:
						 j++;
						 if(j==1){
							 newNum = record.lotteryNumber;
							 html += "<tr>"+td+record.openTime+"</td>"+td+record.issue+"</td>"
			 					+redtd+record.lotteryNumber+"</td></tr>"; 
						 }else{
							 html +=succHtml;
						 }
						
						break;
					 case 10006:
					 case 10012:
						 html +=failedHtml;
					   break;
					 case 10007:
					 case 10010:
					 case 10011:
						 html +=runHtml;
					   break;
					 default:
						 html +=waitHtml;
					 }
				 }
				 $("#recordsTbody").html(html);
				 $("#recordsTbody").show(10);
				 
				//如果有新的开奖结果产生，则更新‘冷热遗漏’信息。
				 if(lastNum!=newNum){
					 betUtil.hotMissNumCount();
				 }
				 if($("._newNum")!=undefined){
					 $("#lastAwardNo").val($("._newNum").closest("tr").find("td").eq(1).html().trim());
				 }
			 }
			 $("#currentIssno").html(data.task.lotterySeries);
			 
			 betUtil.refreshOpenNumInfo();
		 },
		 /**
		  * 显示最新开奖号码
		  */
		 refreshOpenNumInfo:function(){
			 var newNum = $("#recordsTbody").find("._newNum").html();
			 //如果开奖n次失败，最近10期五开奖号码则返回。
			 if(newNum==undefined){
				 return;
			 }
			 var issueNo =  $("#recordsTbody").find("._newNum").closest("tr").find("td").eq(1).html();
			 $("#openNumInfo").find("._last_open_issue").text(issueNo);
			 
			 var nums = newNum.split(",");
			 for(var i=0;i<nums.length;i++){
				 $("#open_code_list").find("i").eq(i).html(nums[i]);
			 }
//			 if(nums.length<5){
//				 for(var i=nums.length;i<5;i++){
//					 $("#open_code_list").find("i").eq(i).hide();
//				 }
//			 }
			 var totalIssue = $("#totalTimes").text();
			 if(parseInt(totalIssue)==1){
				 $(".openIssueInfo").hide();
			 }else{
				 var openedIssue = issueNo.substring(issueNo.length-totalIssue.length);
				 var notOpenIssue = parseInt(totalIssue)-parseInt(openedIssue);
				 $("#openedIssue").text(parseInt(openedIssue));
				 $("#notOpenIssue").text(notOpenIssue);
			 }
		 },
		 /**
		  * 如果是正在开奖的状态，30秒检查一次是否已经开奖。
		  */
		 getOpenAward:function(){
			 if($("#lastAwardNo").val().trim()!=""&&$("#currentIssno").text()!=undefined){
				 var lastAwardNo = parseInt($("#lastAwardNo").val());
				 var currentTaskNo = parseInt($("#currentIssno").text().trim());
				 //判断是否需要刷新开奖记录的轮询。
				 if(lastAwardNo>=(currentTaskNo-1)){
					 return;
				 }
			 }
			 
			 var attrs = new Array();
			 attrs.push("lotteryCode");
			 attrs.push("spanTime");
			 attrs.push("lastOpenIssue");
			 var vals = new Array();
			 vals.push($("#lotCode").val());
			 if($("#lotGroup").val()=="SSC"){
				 vals.push(sscdata.getSpanTime());
			 }else if($("#lotGroup").val()=="SYXW"){
				 vals.push(spanTime);
			 }
			 
			 var newNum = $("#recordsTbody").find("._newNum").html();
			 //如果开奖n次失败，最近10期无开奖号码则设置最近开奖期号为0。
			 if(newNum==undefined){
				 vals.push("0");
			 }else{
				 var issueNo =  $("#recordsTbody").find("._newNum").closest("tr").find("td").eq(1).html();
				 vals.push(issueNo);
			 }
			 
			 var action = "newCurrTask.shtml";
			 formPost.submitAjaxForm(action,attrs,vals,betUtil.ajaxSuccess);
		 },
		 ajaxSuccess:function(data){
			//更新显示开奖记录表
			 betUtil.refreshRecords(data);
		 },
		 /**
		  * ajax查询当前期及之后的当天奖期
		  */
		 showZhuiQi:function(){
			 $("#zhuiQi_table_content").show();
			 var attrs = new Array();
			 attrs.push("lotteryCode");
			 var vals = new Array();
			 vals.push($("#lotCode").val());
			 var action = "getTodayTask.shtml";
			 formPost.setTbodyObj($("#zqtb"),$("._hidZqTr"));
			 formPost.submitAjaxForm(action,attrs,vals,betUtil.getZuiQiSuccess);
		 },
		 /**
		  * 由ajax查询的返回结果，构造追期表格。
		  */
		 getZuiQiSuccess:function(data){
			 formPost.setTbodyObj(null,null);
			 $("#zqtb").empty();
			 var $hidTr = $("._hidZqTr:last").clone(true);
			 $hidTr.show();
			 //限制最多显示300期。
			 var index =0;
			 for(var i=0;i<data.tasks.length&&index<=300;i++){
				 var task = data.tasks[i];
				 var nowIssueNo = $("#currentIssno").text();
				 if(parseInt(task.lotterySeries)<parseInt(nowIssueNo)){
					 continue;
				 }
				index++;
				var $tds =$hidTr.find("td");
				$tds.eq(0).html(index);
				$tds.eq(1).find("input.zqcb").val(task.lotterySeries);
				 if(parseInt(task.lotterySeries)==parseInt(nowIssueNo)){
					 $tds.eq(1).find("._issue_no").addClass("color_red").text(task.lotterySeries+"期[当前期]");
				 }else{
					 $tds.eq(1).find("._issue_no").removeClass("color_red").text(task.lotterySeries+"期");
				 }
				 $("#zqtb").append($hidTr.clone(true));
			 }
			 
			 $("#zqtb .zqcb").on("click",function(){
				 if( $(this).attr("name")=="true"){
					 $(this).prop('checked',false);
					 $(this).attr("name", "false"); 
				 }else{
					 $(this).prop('checked',true);
					 $(this).attr("name", "true"); 
				 }
				 betUtil.chasePeriodHelper(0);
			 });
			 
			 $("#zqtb .zqbs").on("keyup",function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
				betUtil.chasePeriodHelper(0,false,$(this).val());
			 });
			 
			 var num = $(".queickZQ .current").find("span").eq(0).text();
			 betUtil.chasePeriod(num);
		 },
		 /**
		  * 勾选追n期
		  */
		 chasePeriod:function(num,b){
			// 取消全部     
	        $("#zqtb input[type='checkbox']").each( function() {     
	            $(this).prop("checked", false);     
	            $(this).attr("name", "false");    
	        }); 
	        //根据追期勾选奖期
	        $("#zqNum").val(num);
			$("#zqtb input[type='checkbox']").filter(
				 function( index ) {
					 return index <num;
				}
			).prop("checked", true);
			betUtil.chasePeriodHelper(num,b);
		 },
		 /**
		  * 翻倍追期勾选
		  */
		 doubleChasePeriod:function(){
			 	var num = $("#zqNum").val();
				// 取消全部     
		        $("#zqtb input[type='checkbox']").each( function() {     
		            $(this).prop("checked", false);     
		            $(this).attr("name", "false");    
		        }); 
		        //根据追期勾选奖期
		        $("#zqNum").val(num);
				$("#zqtb input[type='checkbox']").filter(
					 function( index ) {
						 return index <num;
					}
				).prop("checked", true);
				betUtil.chasePeriodHelper(num,false,false,$("#double_time").val());
			 },
		/**
		  * 计算追n期对应的钱数。
		  */
		chasePeriodHelper:function(num,b,bv,dt){
			//清空所有旧数据
			if(!bv){
				$("#zqtb input[type='text']").val("");
			}
			 
			$("#zqtb span.curspen").html("-");
			$("#zqtb span.totspen").html("-");
			$("#zqtb span.award").html("-");
			 
			//判断是否能够计算理论中奖金额，如果多中玩法则无法计算理论中奖金额，因为无法确定会中哪一种玩法。
			var canAward=true;
			var map = new Map();
			var map2 = new Map();
			var $reultsDiv;
			if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				$reultsDiv = $(".ssc-resluts-list div");
			}else if($("#lotGroup").val()=="SYXW"){
				$reultsDiv = $(".cathectic-resluts-list div");
			}
			
			$reultsDiv.each(function(){
				var mc = $(this).find("input").eq(0).val();
				//元角分模式，显示理论中奖金额依据。
				var temp= $(this).find("input").eq(2).val();
				if(undefined==map2.get(temp)){
					if(temp==0.01){
						map2.put(temp, "f");
					}else if(temp==0.1){
						map2.put(temp, "j");
					}else{
						map2.put(temp, "y");	
					}
				}
				
				if(undefined==map.get(mc)){
					map.put(mc, mc);
				}
			});
			if(map.size()>1){
				canAward = false;
			}
			
			var yjfstr="";
			for(var i=0,keys = map2.keySet(), len = keys.length; i < len;i++){
				yjfstr= yjfstr+map2.container[keys[i]];
			}
			
			
			var totalAmount =0;
			var zqNum = 0;
			var $zqcb = $("#zqtb input[type='checkbox']");
			
			var chaseType = $("#traceSelect").find(".ok").find("input").val();
			if(chaseType==""||chaseType==undefined){
				chaseType="P";
			}
			
			//利润率追号时获取玩法的中奖金额
			var tempAw = $(".ssc-resluts-list div").eq(0).find("input._betAward").asNumber();
			var sm = $("#start_mulite").val();
			var prof = $("#profit").asNumber()/100;
			//注单金额
			var ba =$("#betAmount").asNumber();
			var moneyModel = $("#moneyModel").val();
			tempAw = tempAw*moneyModel;
			if((tempAw/ba)<(prof+1)&&chaseType=="L"){
				formPost.showErrorMessage("亲，收益率数值过大，无法生成倍投计划！");
				$zqcb.each(function(){
					$(this).prop("checked",false);
				});
				return;
			}
			
			var limit =false;
			var tdt = dt*1;
			for(var i=0;i<$zqcb.size();i++){
				var $row = $($zqcb.get(i));
				if(limit){
					$row.prop("checked",false);
					continue;
				}
				 if($row.prop("checked")){
					 zqNum++;
					//填充倍数
					var t = $("#doubling").val();
					$("#betmp").val(t);
					//如果不是修改倍数触发的追期,则都用统一设置的倍数。
					if(!bv){
						//翻倍追号
						if(dt!=undefined&&dt>0){
							if(i==0){
								tdt = 1;
								t =tdt;
							}else{
								tdt = tdt*dt;
								t =tdt;
							}
						}
						if(t>5000){
							$row.prop("checked",false);
							limit=true;
						}else{
							$row.closest("tr").find("input[type='text']").val(t);
						}
						
					}
					t = $row.closest("tr").find("input[type='text']").val();
					
					//利润率投注起始倍数，利润率
					if(chaseType=="L"&&tempAw>0){
						for(var j=sm;1==1;j++){
							if(j>5000){
								limit =true;
								$row.prop("checked",false);
								break;
							}
							//假设的当前投入后的累计投入
							var dqtr = totalAmount + ba*j;
							//假设的中奖奖金
							var temp = j*tempAw;
							if(temp/dqtr>=(prof+1)){
								t = j;
								break;
							}
						}
						$row.closest("tr").find("input[type='text']").val(t);
					}
					if(limit){
						$row.closest("tr").find("input[type='text']").val("");
						continue;
					}
					
					var amount = ba*t;
					$row.closest("tr").find("span.curspen").html(amount);
					//累计投入
					totalAmount += amount;
					$row.closest("tr").find("span.totspen")
						.html(totalAmount);
					//理论奖金
					if(canAward){
						var aw;
						if($("#lotGroup").val()=="SSC"){
							//不要用：visible这种jquery伪函数，严重影响循环速度。
							aw = $("#sscType").find("i span").eq(0).asNumber();
						}else if($("#lotGroup").val()=="SYXW"){
							aw = $("#awardAmount").asNumber();
						}
						//如果当前期未投入金额，即奖金也应为0.
						if(ba>0){
							var temp="";
							if(yjfstr.indexOf("y")!=-1){
								temp = NumberFormat.formatNumber(aw*t,2,1);
							}
							if(yjfstr.indexOf("j")!=-1){
								if(temp!=""){
									temp = temp+"/";
								}
								temp = temp+NumberFormat.formatNumber(aw*t*0.1,2,1);
							}
							if(yjfstr.indexOf("f")!=-1){
								if(temp!=""){
									temp = temp+"/";
								}
								temp = temp+NumberFormat.formatNumber(aw*t*0.01,2,1);
							}
							$row.closest("tr").find("span.award").html(temp);
						}else{
							$row.closest("tr").find("span.award").html(0.00);
						}
					}
					
					
					$row.closest("tr").find("input[type='text']")
				 	.attr("readonly", false);
					$row.closest("tr").find("input[type='text']")
				 		.css("background-color", "#FFF");
				 }else{
					 $row.closest("tr").find("input[type='text']")
					 	.attr("readonly", true);
					 $row.closest("tr").find("input[type='text']")
					 	.css("background-color", "#EDEDED");
				 }
				
			}
			 //设置最后投注的总金额
			 $("#totalAmount").html(totalAmount);
			 $("#zqNum").val(zqNum);
			 $("#totalPeriods").html(zqNum);
			$("._amount").formatCurrency();
			//不能计算理论中奖金额的，显示"-"就好。
			if(!canAward){
				$("#zqtb span.award").html("-");
			}
		 },
		 updatePeriod:function(){
			 var isZq = $("#traceSelect").find("li.ok").find("input").val();
			 if(isZq=="Z"||isZq=="L"){
				if(isZq=="L"){
					$(".g-play-chase-control").hide();
					$(".g-play-profit-control").show();
				}else{
					$(".g-play-chase-control").show();
					$(".g-play-profit-control").hide();
				}
				//显示追期表信息
				betUtil.showZhuiQi();
			}
		 },
		 winAmountChange:function(){
			 //返点奖金显示框要用到的参数值
			 var wa;
			 var pyr;
			 
			 var selectRebates =$("#userBonus").val();
			 $("._winAmount").each(function (index){
				if($(this).parent().is(":visible")){
					var winAmount =  $(this).parent().find("._oriWinAmount").val();
					var payoutRatio = $(this).parent().find("._oriPayoutRatio").val();
					var py = parseFloat(payoutRatio)+parseFloat(selectRebates);
					winAmount = parseFloat(winAmount)*py;
					$(this).text(winAmount);
					$(this).formatCurrency();
					wa = $(this).parent().find("._oriWinAmount").val();
					pyr = payoutRatio;
				}
				
			});
			//返点奖金显示框只显示一等奖金额
			$("#userBonus").find("option").each(function(){
				var rb = $(this).val();
				
				var py = parseFloat(pyr)+parseFloat(rb);
				var awardAmount = parseFloat(wa)*py;
				
				var showRb = $(this).attr("name");
				var rbPercent = NumberFormat.formatNumber(showRb*100,1,1);
				awardAmount = NumberFormat.formatNumber(awardAmount,2,1);
				$(this).html("奖金"+awardAmount+"&nbsp;&nbsp;&nbsp;&nbsp;返点"+rbPercent+"%");
			});
				
			 Cathectic._setMoney();
		 },
		 /**
		  * 中奖金额显示
		  */
		 winAmountChange2:function(){
			 //返点奖金显示框要用到的参数值
			 var wa;
			 var pyr;
			 
			 var selectRebates =$("#userBonus").val();
			 $("i").each(function (){
			    $(this).find("span").each(function(index){
			    	var winAmounts =  $(".ssc-type-choose:visible")
			    						.find("input[type='radio']:checked").attr("data-ora")+"";
			    	
					var amountArr = winAmounts.split(",");
					var winAmount = amountArr[index];
					var payoutRatio = $(this).parent().parent().find("._oriPayoutRatio").val();
					var py = parseFloat(payoutRatio)+parseFloat(selectRebates);
					winAmount = parseFloat(winAmount)*py;
					$(this).text(winAmount);
					$(this).formatCurrency();
					if(index==0){
						wa = amountArr[0];
						pyr = payoutRatio;
					}
			    });
			});
			 
			//返点奖金显示框只显示一等奖金额
			$("#userBonus").find("option").each(function(){
				var rb = $(this).val();
				
				var py = parseFloat(pyr)+parseFloat(rb);
				var awardAmount = parseFloat(wa)*py;
				
				var showRb = $(this).attr("name");
				var rbPercent = NumberFormat.formatNumber(showRb*100,1,1);
				awardAmount = NumberFormat.formatNumber(awardAmount,2,1);
				$(this).html("奖金"+awardAmount+"&nbsp;&nbsp;&nbsp;&nbsp;返点"+rbPercent+"%");
			});
		 },
		 checkboxMiss:function(){
			 var itemClass ="cathectic";
			 if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				 itemClass = "ssc";
			 }
			 if(!$("#missing").prop('checked')){
					$("."+itemClass+"-selector-item").each(function(index){
						$(this).find("em._mNum").hide();
						$(this).find("em._mTitle").hide();
					});
				}else{
					$("."+itemClass+"-selector-item").each(function(index){
						$(this).find("em._mNum").show();
						$(this).find("em._mTitle").show();
					});
				}
		 },
		 checkboxHot:function(){
			 var itemClass ="cathectic";
			 if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				 itemClass = "ssc";
			 }
			 if(!$("#hotcold").prop('checked')){
					$("."+itemClass+"-selector-item").each(function(index){
						$(this).find("em._hNum").hide();
						$(this).find("em._hTitle").hide();
					});
				}else{
					$("."+itemClass+"-selector-item").each(function(index){
						$(this).find("em._hNum").show();
						$(this).find("em._hTitle").show();
					});
				}
		 },
		 hotMissNumCount:function(){
			 this.checkboxMiss();
			 this.checkboxHot();
			 if(!$("#missing").prop('checked') && !$("#missing").prop('checked')){
				 if($("#text_input").css("display")!="none"){
					 $("#hotMissDiv").hide();
				 }else{
					 $("#hotMissDiv").show();
				 }
				return; 
			 }
			 var attrs = new Array();
			 attrs.push("lotGroup");
			 attrs.push("lotteryCode");
			 attrs.push("modelCode");
			 attrs.push("selectCode");
			 attrs.push("type");
			 var vals = new Array();
			 vals.push($("#lotGroup").val());
			 vals.push($("#lotCode").val());
			 
			 var playCode = "";
			 var selectCode ="";
			 var curType = "";
			 var curId ="";
			
			 if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				 playCode = $("#sscType").find("input[type='radio']:checked").attr("data-code");
				 selectCode =$("#sscType").find("input[type='radio']:checked").attr("data-select");
				 curId= $("#sscType").find("input[type='radio']:checked").attr("id");
				 curType = betUtil.getCurrType(curId);
				 //手工输入不用查询冷热遗漏
				 if(curType=="text"){
					 $("#hotMissDiv").hide();
					 return;
				 }else{
					 $("#hotMissDiv").show();
				 }
				 vals.push(playCode);
				 vals.push(selectCode);
				 vals.push(curType);
				 
				 var action = "hotMissNumCount.shtml";
				 formPost.submitAjaxForm(action,attrs,vals,betUtil.refreshSSCHMCount);
			 }else{
				 playCode = $(".cathectic-type:visible").find("input[type='radio']:checked").attr("data-code");
				 selectCode =$(".cathectic-type:visible").find("input[type='radio']:checked").attr("data-select");
				 curId= $(".cathectic-type:visible").find("input[type='radio']:checked").attr("id");
				 curType = betUtil.get11x5CurrType(curId);
				//手工输入不用查询冷热遗漏
				 if(curType=="text"){
					 $("#hotMissDiv").hide();
					 return;
				 }else{
					 $("#hotMissDiv").show();
				 }
				 vals.push(playCode);
				 vals.push(selectCode);
				 vals.push(curType);
				 
				 var action = "hotMissNumCount.shtml";
				 formPost.submitAjaxForm(action,attrs,vals,betUtil.refresh11x5HMCount);
			 }
			 
			 
		 },
		 getCurrType:function(curId){
			  if(/(1|2|3|4|5|6)_1|6_3|(8|9)_1/.test(curId)){
		            // 标准选号, 6_3为通选
		            curType = "zx";
		        }else if("SSC_2_6"==curId||"SSC_2_4"==curId){
		            // 后二星和值
		            curType = "h2zs";
		        }else if("SSC_18_5"==curId||"SSC_18_2"==curId){
		            // 前二星和值
		            curType = "q2zs";
		        }else if("SSC_3_2"==curId){
		            // 后三星直选和值
		            curType = "h3zs";
		        }else if("SSC_4_3"==curId){
		            // 后三星组三和值
		            curType = "h3z3s";
		        }else if("SSC_4_6"==curId){
		            // 后三星组六和值
		            curType = "h3z6s";
		        }else if("SSC_2_3"==curId||"SSC_2_8"==curId){
		            // 后二星包号
		            curType = "h2bh";
		        }else if("SSC_18_4"==curId||"SSC_18_7"==curId){
		            // 前二星包号
		            curType = "q2bh";
		        }else if(/4_(2|5)/.test(curId)){
		            // 三星包号
		            curType = "3bh";
		        }else if("SSC_2_5"==curId){
		            // 后二星胆拖
		            curType = "h2dt";
		        }else if("SSC_18_6"==curId){
		            // 前二星胆拖
		            curType = "q2dt";
		        }else if(/3_3|4_7/.test(curId)){
		            // 三星胆拖
		            curType = "3dt";
		        }else if(/3_4/.test(curId)){
		            // 跨度
		            curType = "3kd";
		        }else if(/2_(2|6)|3_5|4_(4|8)|6_(2|4)/.test(curId)){
		            // 手工录入
		            curType = "text";
		        }else if(/7_1/.test(curId)){
		            // 大小单双
		            curType = "dxds";
		        }else if(/10_7/.test(curId)){
		            // 前三不定位一码
		            curType = "3bd1";
		        }else if(/10_8/.test(curId)){
		            // 中三不定位一码
		            curType = "3bd2";
		        }else{
		        	curType = "rx";
		        }
		        return curType;
		 },
		 get11x5CurrType:function(curId){
			    if(/(1|2|3)_1/.test(curId)){
		            // 标准选号, 6_3为通选
		            curType = "zx";
		        }else if(/2_3|2_5|3_4|3_6|(4|5|6|7|8)_2/.test(curId)){
		            //胆拖
		            curType = "dt";
		        }else if(/3_2|5_3/.test(curId)){
		            // 手工录入
		            curType = "text";
		        }else{
		        	curType = "rx";
		        }
		        
		        return curType;
		 },
		 refreshSSCHMCount:function(data){
			 $("._mTitle").sTip({
					tipInfo : "冷热：当天总期数内的开奖号码出现次数统计。   遗漏：最近一期开奖号码包含该号码的间隔期数。 ",
					display:false,
					offsetLeft:30
			 });
			 $("._hTitle").sTip({
					tipInfo : "冷热：当天总期数内的开奖号码出现次数统计。   遗漏：最近一期开奖号码包含该号码的间隔期数。 ",
					display:false,
					offsetLeft:30
			 });
			 var mList = data.mList;
			 var $item=$(".ssc-main-choose").find(".ssc-selector-item");
			 var items = $item.size();
			 var p = 1;
			 //显示遗漏
			 var numLen = 4;
			 var newNum = $("#recordsTbody").find("._newNum").html();
			 //如果开奖n次失败，最近10期五开奖号码则返回。
			 if(newNum!=undefined){
				 var nums = newNum.split(",");
				 numLen = nums.length - 1;
			 }
			 
			 var curId ="";
			 if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				 curId= $("#sscType").find("input[type='radio']:checked").attr("id");
			 }else{
				 curId= $(".cathectic-type:visible").find("input[type='radio']:checked").attr("id");
			 }
			 
			 if(/11_1|13_1|18_1|17_1/.test(curId)){
	         		//前n的显示遗漏
					 for(var i=0;i<3;i++){
						 for(var j=0;j<15;j++){
							 var key = i+"_"+$item.eq(i).find("span").eq(j+1).find("strong").html();
							 for(var k=0;k<mList.length;k++){
								 if(key==mList[k].key){
									 $item.eq(i).find("span").eq(j+1).find("em:first").html(mList[k].value);
									 break;
								 }
							 }
						 }
					 }
					//显示总期数中的号码冷热
					 var hList = data.hList;
					 for(var i=0;i<3;i++){
						 for(var j=0;j<15;j++){
							 var key = i+":"+$item.eq(i).find("span").eq(j+1).find("strong").html();
							 for(var k=0;k<hList.length;k++){
								 if(key==hList[k].key){
									 $item.eq(i).find("span").eq(j+1).find("em:last").html(hList[k].value);
									 break;
								 }
							 }
						 }
					 }
	         	}else{
	         		//后n的显示遗漏
	         		for(var i=numLen;i>numLen-items;i--){
		   				 for(var j=0;j<15;j++){
		   					 var key = i+"_"+$item.eq(items-p).find("span").eq(j+1).find("strong").html();
		   					 for(var k=0;k<mList.length;k++){
		   						 if(key==mList[k].key){
		   							 $item.eq(items-p).find("span").eq(j+1).find("em:first").html(mList[k].value);
		   							 break;
		   						 }
		   					 }
		   				 }
	   				 	p++;
		   			 }
		   			//显示总期数中的号码冷热
		   			 var hList = data.hList;
		   			 p=1;
		   			 for(var i=numLen;i>numLen-items;i--){
		   				 for(var j=0;j<15;j++){
		   					 var key = i+":"+$item.eq(items-p).find("span").eq(j+1).find("strong").html();
		   					 for(var k=0;k<hList.length;k++){
		   						 if(key==hList[k].key){
		   							 $item.eq(items-p).find("span").eq(j+1).find("em:last").html(hList[k].value);
		   							 break;
		   						 }
		   					 }
		   				 }
		   				 p++;
		   			 }
	         	}
			 
			 betUtil.setHMNumColor($item,"em._hNum");
			 betUtil.setHMNumColor($item,"em._mNum");
		 },
		 refresh11x5HMCount:function(data){
			 $(".tipsInfo").remove();
			 $("._mTitle").sTip({
					tipInfo : "冷热：当天总期数内的开奖号码出现次数统计。   遗漏：最近一期开奖号码包含该号码的间隔期数。 ",
					display:false,
					offsetLeft:30
			 });
			 $("._hTitle").sTip({
					tipInfo : "冷热：当天总期数内的开奖号码出现次数统计。   遗漏：最近一期开奖号码包含该号码的间隔期数。 ",
					display:false,
					offsetLeft:30
			 });
			 var mList = data.mList;
			 var $item =$(".cathectic-main-choose").find(".cathectic-selector-item");
			 var items = $item.size();
			 var p = 1;
			 //显示遗漏
			 for(var i=0;i<items;i++){
				 for(var j=1;j<12;j++){
					 var key = "";
					 if(j<10){
						 key = i+"_0"+j;
					 }else{
						 key = i+"_"+j;
					 }
					 for(var k=0;k<mList.length;k++){
						 if(key==mList[k].key){
							 $item.eq(i).find("span").eq(j).find("em:first").html(mList[k].value);
							 break;
						 }
					 }
				 }
				 p++;
			 }
			//显示总期数中的号码冷热
			 var hList = data.hList;
			 p=1;
			 for(var i=0;i<items;i++){
				 for(var j=1;j<12;j++){
					 var key = "";
					 if(j<10){
						 key = i+":0"+j;
					 }else{
						 key = i+":"+j;
					 }
					 for(var k=0;k<hList.length;k++){
						 if(key==hList[k].key){
							 $item.eq(i).find("span").eq(j).find("em:last").html(hList[k].value);
							 break;
						 }
					 }
				 }
				 p++;
			 }
			 betUtil.setHMNumColor($item,"em._hNum");
			 betUtil.setHMNumColor($item,"em._mNum");
		 },
		 setHMNumColor:function(item,objStr){
			 var $item =$(item);
			 for(var i=0;i<$item.size();i++){
				 var maxNum = -1;
				 var minNum = 99999;
				 $item.eq(i).find(objStr).each(function(index){
					 var val = parseInt($(this).html());
					 if(val>maxNum){
						 maxNum = val;
					 }
					 if(val<=minNum){
						 minNum = val;
					 }
				 });
				 $item.eq(i).find(objStr).each(function(index){
					 $(this).removeClass("color_red");
					 $(this).removeClass("color_blue");
					 var val = parseInt($(this).html());
					 if(val==maxNum){
						 $(this).addClass("color_red");
					 }
					 if(val==minNum){
						 $(this).addClass("color_blue");
					 }
				 });
			 }
		 },
		 showAwardExample:function(obj){
			 var info =$(obj).next().text();
			 $(obj).sTip({
					tipInfo : "冷热：当天总期数内的开奖号码出现次数统计。   遗漏：最近一期开奖号码包含该号码的间隔期数。 ",
					display:false,
					offsetLeft:30
			 });
		 },
		 //查询最新中奖
		 queryWingOrders:function(){
			 var attrs =[];
			 var vals = [];
			 attrs.push("orderAmount");
			 vals.push(500);
			 formPost.submitAjaxForm("queryNewWinningOrder.shtml",attrs,vals,this.wingOrderBack);
		 },
		 wingOrderBack:function(data){
			 $("#noticeList").html("");
			 var orders = data.newWingOrders;
			 var $hidSpan = $("._hidSpan:last").clone(true);
			 $hidSpan.show();
			 for(var i=0;i<orders.length;i++){
				 var worder = orders[i];
				 var amount = NumberFormat.formatNumber(worder.orderAmount,2,"1");
				 var wingInfo = worder.customerName+"   "+worder.lotteryTypeName+"   "+amount;
				 $hidSpan.text(wingInfo);
				 
				 $("#noticeList").append($hidSpan.clone(true));
			 }
			 
			 $("#wingScroll").textScroll();
		 },
		 init:function(){
			//头部锁定js
			 $(window).scroll(function() {
				var left = $("#splitDiv").offset().left+12;
				var top = $(".rNav").offset().top;
				if($(window).scrollTop()>=50){
					$(".nav").addClass("fixedNav");
					if(top<500 && $(window).scrollTop()<360){
						$(".rNav").addClass("fixedFrNav");
						$(".fixedFrNav").css({
							left:left
						});
					}else{
						$(".rNav").removeClass("fixedFrNav");
					}
					
				}else{
					$(".nav").removeClass("fixedNav");
					$(".rNav").removeClass("fixedFrNav");
				} 
			});
			 
			$("#recordsTbody").find("._lotNum:first").addClass("color_red");
			$("#end_time_hour").hide();
			$("#hour").hide();
			$("#end_time_hour").next().hide();
			$("#zhuiQi_table_content").hide();
			$("input[name=lunch_type]").click(function() {
				if ($(this).attr("id") == "lunch_t1") {
					$("#zhuiQi_table_content").hide();
					var betAmount = $("#betAmount").text().replace(",","");
					var doubling = $("#doubling").val();
					var totalAmount = betAmount*doubling;
					$("#totalAmount").html(totalAmount);
					$("#totalPeriods").html(1);
					$("._amount").formatCurrency();
				} else {
					if($(this).val()=="L"){
						$(".profitTrace").show();
						$("#doubleChase").hide();
					}else{
						$(".profitTrace").hide();
						$("#doubleChase").show();
					}
					//显示追期表信息
					betUtil.showZhuiQi();
				}
			});
			
			$(".queickZQ").find("li").on("click",function() {
				$(".queickZQ").find("li").removeClass("current");
				$(this).addClass("current");
				var zNum = $(this).find("span").eq(0).text();
				betUtil.chasePeriod(zNum);
			});
			
			$("#doubling").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
				if($("#betmp").val()!=$(this).val()){
					$("#betmp").val($(this).val());
				}
				betUtil.chasePeriodHelper(0,true);
			});
			$("#betmp").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
				if($("#doubling").val()!=$(this).val()){
					$("#doubling").val($(this).val());
				}
				betUtil.chasePeriodHelper(0,true);
			});
			$("#zqNum").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
				betUtil.chasePeriod($(this).val());
			});
			
			
			$("._amount").formatCurrency();
			
			
			if($("#lotGroup").val()=="SSC"||$("#lotGroup").val()=="3D"){
				$("#moneyModel").on('change',function(){
						var ssc1 = sscdata.getSSCobj();
						ssc1.refreshBetModel();
				});
				$("#userBonus").on('change',function(){
					betUtil.winAmountChange2();
					var ssc1 = sscdata.getSSCobj();
					ssc1.refreshBetModel();
				});
				if($("._titleInfo").find("h1").html()=="重庆时时彩"){
					$("#oldAwardInfo").show();
					$("#newAwardInfo").hide();
				}else{
					$("#oldAwardInfo").hide();
					$("#newAwardInfo").show();
				}
			}else if($("#lotGroup").val()=="SYXW"){
				$("#moneyModel").on('change',function(){
					Cathectic._setMoney();
				});
				$("#totalMinutes").html(totalMinutes);
				//页面初始化后更新一次页面的时间和奖期信息
				betUtil.winAmountChange();
				$("#userBonus").on('change',function(){
					betUtil.winAmountChange();
				});
			}
			//开奖结果的最近开奖号码设置为红色。
			$("#recordsTbody").find("._lotNum").eq(0).addClass("_newNum");
			$("#recordsTbody").find("._lotNum").eq(0).removeClass("color_red");
			$("#recordsTbody").find("._lotNum").eq(0).addClass("color_red");
			
			//显示最新开奖结果
			betUtil.refreshOpenNumInfo();
			
			var endHour = parseInt($("#end_time_hour").text());
			if(endHour>0){
				$("#end_time_hour").show();
				$("#hour").show();
				$("#end_time_hour").next().show();
			}
			
			$("._betbtn button").click(function(){
				betForm.submitForm();
			});
			
			$("#refreshLotNum").on('click',function(){
				betUtil.getOpenAward();
			});
			
			$("#missing").on('click',function(){
				betUtil.checkboxMiss();
				betUtil.hotMissNumCount();
			});
			$("#hotcold").on('click',function(){
				betUtil.checkboxHot();
				betUtil.hotMissNumCount();
			});
			
			$("#loadFile").click(function() {
				$("body").sMsg({
					title:'导入文件',
					content : '',
					autoDisplay : true,
					actionEvent : ""
				});
			});
			
			$("#clearText").click(function() {
				$(".CSR_clear").trigger("click");
			});
			$("#deleteRepeat").click(function(){
				var str = $("#fileInfo").val();
				str = betUtil.removeSame(str);
				$("#fileInfo").val(str);
				$("#fileInfo").trigger("keyup");
				formPost.showErrorMessage("重复号码删除成功");
			});
			
			$("#double_time").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
			});
			$("#profit").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
			});
			$("#start_mulite").on('keyup',function(){
				var integer = "^[1-9]\\d*$";
				var re =new RegExp(integer);
				if(!re.test($(this).val())){
					$(this).val(1);
				}
			});
			
			$("#doubleTimes").on('click',function(){
				betUtil.doubleChasePeriod();
			});
//			$("#showBet").trigger("click");

			$("#traceBet").on('click',function(){
				if($(this).prop("checked")){
					$("#trace").show();
					$("#traceSelect").find("li").eq(0).addClass("ok");
					$(".g-play-chase-control").show();
					$(".g-play-profit-control").hide();
					//显示追期表信息
					betUtil.showZhuiQi();
				}else{
					$("#traceSelect").find("li").removeClass("ok");
					$("#trace").hide();
					var betAmount = $("#betAmount").text().replace(",","");
					var doubling = $("#doubling").val();
					var totalAmount = betAmount*doubling;
					$("#totalAmount").html(totalAmount);
					$("#totalPeriods").html(1);
					$("._amount").formatCurrency();
				}
			});
			
			
			$("#traceSelect li").on('click',function(){
				$("#traceSelect li").removeClass("ok");
				$(this).addClass("ok");
				if($(this).find("input").val()=="Z"){
					$(".g-play-chase-control").show();
					$(".g-play-profit-control").hide();
				}else{
					$(".g-play-chase-control").hide();
					$(".g-play-profit-control").show();
				}
			});
			
			$("#traceSpan").on('click',function(){
				$("#traceBet").click();
			});
			$("#astopSpan").on('click',function(){
				$("#awardStop").click();
			});
			//最近中奖
			betUtil.queryWingOrders();
		 }
	};
}();

