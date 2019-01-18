$(document).ready(function() {
	$("#trendSelect li").on('click',function(){
		$("#noTb").empty();
		$("#mylineDiv").empty();
		$("#trendSelect li").removeClass("current");
		$(this).addClass("current");
		noSscTrend.queryRecords();
	});
	$("#missing").prop("checked",true);
	$("#helpLine").prop("checked",true);
	$("#splitLine").prop("checked",true);
	
	$("#splitLine").on('click',function(){
		if($(this).prop("checked")){
			noSscTrend.drawSplitLine();
		}else{
			noSscTrend.dropSplitLine();
		}
	});
	$("#helpLine").on('click',function(){
		if($(this).prop("checked")){
			noSscTrend.drawHelpLine();
		}else{
			noSscTrend.dropAllLine();
		}
	});
	
	$("#missing").on('click',function(){
		if($(this).prop("checked")){
			noSscTrend.drawMissing();
		}else{
			noSscTrend.dropMissing();
		}
	});
	
	$("._analog").on('click',function(){
		var c = $(this).attr("class");
		if(c=="_analog"){
			$(this).attr("class","_current");
		}else{
			$(this).attr("class","_analog");
		}
	});
	noSscTrend.queryRecords();
	
});
var noSscTrend = function(){
	var myInteval;
	var startTr=0;
	var endTr=0;
	var totalTr=0;
	var cancasIsOk=false;
	try {
		document.createElement("canvas").getContext("2d");
		cancasIsOk = true;
	} catch (e) {
		
	} 
	return{
		html5CanvasLine:function(startX,startY,endX,endY,index){
			 var beginY=0;
			 var stopY=endY-startY;
		     if(endX<startX){
		    	var tempX = startX;  
	            startX = endX;  
	            endX = tempX; 
	            beginY = endY-startY;
	            stopY =0;
		     }
		     
			 var $myCanvas = $("<canvas>");  
			 $myCanvas.attr("id","myCv"+index);
			 $myCanvas.css("position","absolute");
			 $myCanvas.css("left",startX);
			 $myCanvas.css("top",startY);
			 if(endX==startX){
				 $myCanvas.attr("width",1);
			 }else{
				 $myCanvas.attr("width",endX-startX);
			 }
			 
			 $myCanvas.attr("height",endY-startY);
		     $("#mylineDiv").append($myCanvas);
		     var myCv = document.getElementById("myCv"+index);
		     var context = myCv.getContext("2d"); 
		     context.beginPath();  
		     context.strokeStyle = "blue";  
		     context.lineWidth=1;
		     context.moveTo(0,beginY);  
		     context.lineTo(endX-startX,stopY);  
		     context.stroke();
		},
		queryRecords:function(){
			clearInterval(myInteval);
			startTr=0;
			endTr=0;
			totalTr=0;
			var $hidLoading = $("._lodingTr:last").clone(true);
			$hidLoading.show();
			$("#noTb").append($hidLoading.clone(true));
			
			var attrs = new Array();
			var values = new Array();
			attrs.push("lotteryCode");
			attrs.push("totalTimes");
			values.push($("#lotCode").val());
			values.push($("#trendSelect .current").attr('name'));
			
			formPost.submitAjaxForm("queryRecords.shtml",attrs,values,this.showNoTrend);
		},
		drawLine : function(lc,startX, startY, endX, endY) {  
		    if (startX == endX) {  
		        if (startY > endY) {  
		            var tempY = startY;  
		            startY = endY;  
		            endY = tempY;  
		        }  
		        for (var k = startY; k < endY; k++) {  
		            this.createPoint(lc,startX, k);  
		        }  
		    }else{
			    var a = (startY - endY) / (startX - endX);  
			    var b = startY - ((startY - endY) / (startX - endX)) * startX;  
			    if (Math.abs(startX - endX) > Math.abs(startY - endY)) {  
			    	//横向大于纵向
			        if (startX > endX) {  
			            var tempX = endX;  
			            endX = startX;  
			            startX = tempX;  
			        }  
			        for (var i = startX; i <= endX; i++) {  
			        	this.createPoint(lc,i, a * i + b);  
			        }  
			    } else {  
			        if (startY > endY) {  
			            var tempY = startY;  
			            startY = endY;  
			            endY = tempY;  
			        }  
			        for (var j = startY; j <= endY; j++) {  
			        	this.createPoint(lc,(j - b) / a, j);  
			        }  
			    }  
		    }  
		   
		},  
		createPoint : function (lc,x, y) {  
		    var node =$("<div>");  
		    $(node).addClass(lc);  
		    $(node).css("top",y);  
		    $(node).css("left",x); 
		    $(node).css("position","absolute"); 
		    $("#mylineDiv").append($(node));  
		},
		drawAllLine:function(){
			endTr = startTr + 30;
			if(endTr>=totalTr){
				endTr = totalTr;
			}
			var $rows = $("#noTb").find("tr._hidTr");
			for(var p=startTr;p<endTr;p++){
				var $selfRow = $rows.eq(p);
				var $selfClos = $selfRow.find("td._open_num");
				for(var j=0;j<3;j++){
					var lc = "_noline1";
					var startX=0;
					var startY=0;
					var endX=0;
					var endY=0;
					var $myNum =$selfClos.eq(j);
					startY =$myNum.offset().top;
					startX =$myNum.offset().left;
					$myNum =$rows.eq((p+1)).find("td._open_num").eq(j);
					endY =$myNum.offset().top;
					endX =$myNum.offset().left;
					var myWidth = parseInt($myNum.css('width'));
					var myHeight = parseInt($myNum.css('height'));
					if(endX>0&&endY>0){
						if(!cancasIsOk){
							noSscTrend.drawLine(lc,startX+myWidth/2, startY+myHeight/2, endX+myWidth/2, endY+myHeight/2);
						}else{
							noSscTrend.html5CanvasLine(startX+myWidth/2, startY+myHeight/2, endX+myWidth/2, endY+myHeight/2,p+"_"+j);
						}
					}
				}
				//画组选形态的折线
				var startX=0;
				var startY=0;
				var endX=0;
				var endY=0;
				var $zxst = $selfRow.find("td._zxst_num").eq(0);
				startY =$zxst.offset().top;
				startX =$zxst.offset().left;
				$zxst =$rows.eq((p+1)).find("td._zxst_num").eq(0);
				endY =$zxst.offset().top;
				endX =$zxst.offset().left;
				var myWidth = parseInt($zxst.css('width'));
				var myHeight = parseInt($zxst.css('height'));
				if(endX>0&&endY>0){
					if(!cancasIsOk){
						noSscTrend.drawLine("_noline1",startX+myWidth/2, startY+myHeight/2, endX+myWidth/2, endY+myHeight/2);
					}else{
						noSscTrend.html5CanvasLine(startX+myWidth/2, startY+myHeight/2, endX+myWidth/2, endY+myHeight/2,p+"_"+j);
					}
				}
			}
			if(endTr==totalTr){
				clearInterval(myInteval);
			}else{
				startTr = endTr-1;
			}
		},
		dropAllLine:function(){
			$("#mylineDiv").empty();
			clearInterval(myInteval);
			startTr=0;
			endTr=0;
			totalTr=0;
		},
		drawHelpLine:function(){
				clearInterval(myInteval);
				startTr=0;
				endTr=0;
				totalTr = $("#noTb").find("tr._hidTr").size();
				noSscTrend.drawAllLine();
				if(totalTr>30){
					myInteval = setInterval("noSscTrend.drawAllLine()", 500);
				}
		},
		dropMissing:function(){
			var $rows = $("#noTb").find("tr._hidTr");
			var rowsize = $rows.size();
			var colsize =$rows.eq(0).find("td").size();
			for(var j=2;j<colsize-5;j++){
				for(var i=0;i<rowsize;i++){
					var $row = $rows.eq(i);
					var $tds = $row.find("td");
					var $td =  $tds.eq(j);
					var c =$td.attr("class");
					if(c.indexOf("_open_num")==-1){
						$td.removeClass("_whiteWord");
						$td.html(" ");
					}
				}
			}
		},
		drawMissing:function(){
			var $rows = $("#noTb").find("tr._hidTr");
			var rowsize = $rows.size();
			var colsize =$rows.eq(0).find("td").size();
			for(var j=2;j<colsize-5;j++){
				var maxMiss=0;
				var maxSame=0;
				var sameCount=0;
				var missingIndex=1;
				var totalOpen=0;
				for(var i=0;i<rowsize;i++){
					var $row = $rows.eq(i);
					var $tds = $row.find("td");
					var $td =  $tds.eq(j);
					var c =$td.attr("class");
					if(c.indexOf("_open_num")==-1&&c.indexOf("_zxst_num")==-1){
						$td.addClass("_whiteWord");
						$td.html(missingIndex);
						missingIndex++;
						sameCount=0;
					}else{
						$td.removeClass("_whiteWord");
						if(maxMiss<missingIndex){
							maxMiss = missingIndex-1;
						}
						missingIndex=1;
						totalOpen++;
						sameCount++;
						if(maxSame<sameCount){
							maxSame = sameCount;
						}
					}
				}
				//总出现次数统计
				$("#noTb").find("tr._hidCountTr").eq(0).find("td").eq(j-1).html(totalOpen);
				//平均遗漏次数统计
				var avgMissing =rowsize;
				if(totalOpen>0){
					avgMissing = Math.floor(rowsize/totalOpen);
				}
				$("#noTb").find("tr._hidCountTr").eq(1).find("td").eq(j-1).html(avgMissing);
				//最大遗漏次数统计
				if(maxMiss==0){
					maxMiss = rowsize;
				}
				$("#noTb").find("tr._hidCountTr").eq(2).find("td").eq(j-1).html(maxMiss);
				//最大连出次数统计
				$("#noTb").find("tr._hidCountTr").eq(3).find("td").eq(j-1).html(maxSame);
			}
		},
		showNoTrend:function(data){
			$("#noTb").empty();
			var noType = $("#noType").val();
			if(noType=="q3"){
				$("._lotInfo").html(data.lotName+" 前三基本走势图  ");
			}else if(noType=="z3"){
				$("._lotInfo").html(data.lotName+" 中三基本走势图  ");
			}else{
				$("._lotInfo").html(data.lotName+" 后三基本走势图  ");
			}
			
			//限制最多显示150条数据
			var len = data.records.length;
			if(len>150){
				len = 150;
			}
			for(var i=0;i<len;i++){
				var record = data.records[i];
				var $hidTr = $("._hidTr:last").clone(true);
				$hidTr.show();
				$hidTr.find("td._issue").html("&nbsp;"+record.issue+"&nbsp;");
				$hidTr.find("td._lotNum").html("&nbsp;"+record.lotteryNumber+"&nbsp;");
				
				var nums = record.lotteryNumber.split(",");
				var noType = $("#noType").val();
				/**
				 * ks 循环开始值
				 * ke 循环结束值
				 * ns1 取号码的顺序1
				 * ns2 取号码的顺序2
				 * ns3 取号码的顺序3
				 */
				var ks,ke,ns1,ns2,ns3;
				//前三星走势图，或者3D走势图
				if(nums.length == 3||noType=="q3"){
					ks = 0;ke = 3;
					ns1 = 0;ns2 = 1; ns3 = 2;
				}else if(noType=="z3"){
					//中三走势图
					ks = 1;ke = 4;
					ns1 = 1;ns2 = 2; ns3 = 3;
				}else {
					//后三走势图
					ks = 2;ke = 5;
					ns1 = 2;ns2 = 3; ns3 = 4;
				}
				for(var k=ks+1;k<ke+1;k++){
					var $num = $("._num"+k+"_group:last").clone(true);
					if(nums.length == 3||noType=="q3")$num = $("._num"+(k+2)+"_group:last").clone(true);
					if(noType=="z3" )$num = $("._num"+(k+1)+"_group:last").clone(true);
					$num.show();
					for(var j=0;j<10;j++){
						if(j==parseInt(nums[k-1])){
							$num.html(nums[k-1]);
							$num.addClass("_open_num");
						}else{
							$num.removeClass("_open_num");
							$num.html(" ");
						}
						$hidTr.append($num.clone(true));
					}
				}
				
				var sameNumCount=0;
				var same2Num=-1;
				var same3Num=-1;
				var num1 =parseInt(nums[ns1]);
				var num2 =parseInt(nums[ns2]);
				var num3 =parseInt(nums[ns3]);
				if(num1==num2){
					sameNumCount++;
					same2Num=num1;
				}
				if(num1==num3){
					sameNumCount++;
					if(sameNumCount==2){
						same2Num=-1;
						same3Num=num1;
					}else{
						same2Num=num1;
					}
					
				}
				if(num2==num3){
					sameNumCount++;
					if(sameNumCount>2){
						same2Num=-1;
						same3Num=num2;
					}else{
						same2Num=num2;
					}
				}
					
				for(var j=0;j<10;j++){
					var $allnum = $("._allnum_group:last").clone(true);
					$allnum.show();
					for(var k=ks;k<ke;k++){
						if(j==parseInt(nums[k])){
							$allnum.html(nums[k]);
							if(j==same2Num){
								$allnum.addClass("_open_num2");
							}else if(j==same3Num){
								$allnum.addClass("_open_num3");
							}else{
								$allnum.addClass("_open_num");
							}
							break;
						}else{
							$allnum.html(" ");
							$allnum.removeClass("_open_num");
							$allnum.removeClass("_open_num2");
							$allnum.removeClass("_open_num3");
						}
					}
					$hidTr.append($allnum.clone(true));
				}
				
				//祖选特征
				var $zxst = $("._zxst:last").clone(true);
				$zxst.show();
				$zxst.html(" ");
				$zxst.removeClass("_zxst_num");
				for(var j=0;j<3;j++){
					$hidTr.append($zxst.clone(true));
				}
				
				if(same2Num!=-1){
					$hidTr.find("._zxst").eq(1).addClass("_zxst_num");
				}else if(same3Num!=-1){
					$hidTr.find("._zxst").eq(0).addClass("_zxst_num");
				}else{
					$hidTr.find("._zxst").eq(2).addClass("_zxst_num");
				}
				
				var dx="";
				var ds="";
				var zh="";
				var sum=0;
				var max=0;
				var min=100;
				for(var j=ks;j<ke;j++){
					var num = parseInt(nums[j]);
					sum +=num;
					
					if(num>max){
						max = num;
					}
					
					if(num<min){
						min = num;
					}
					
					if(num>4){
						dx += "大";
					}else{
						dx += "小";
					}
					
					if(num%2==0){
						ds += "双";
					}else{
						ds += "单";
					}
					
					//判断是否是合数
					if(num>3){
						var ish =false;
						for(var p=2;p<num;p++){
							if(num%p==0){
								zh +="合";
								ish=true;
								break;
							}
						}
						if(!ish){
							zh +="质";
						}
					}else if(num==0){
						zh +="合";
					}else{
						zh +="质";
					}
				}
				
				var $dx = $("._dx:last").clone(true);
				var $ds = $("._ds:last").clone(true);
				var $zh = $("._zh:last").clone(true);
				var $kd = $("._kd:last").clone(true);
				var $sum = $("._sum:last").clone(true);
				
				$dx.show().html(dx);
				$ds.show().html(ds);
				$zh.show().html(zh);
				$kd.show().html((max-min));
				$sum.show().html(sum);
				
				$hidTr.append($dx.clone(true));
				$hidTr.append($ds.clone(true));
//				$hidTr.append($zh.clone(true));
				$hidTr.append($kd.clone(true));
				$hidTr.append($sum.clone(true));
				
				$("#noTb").append($hidTr.clone(true));
			}
			//统计数据行插入
			noSscTrend.drawNoCount();
			//填充遗漏期数，计算统计数据
			if($("#missing").prop("checked")){
				noSscTrend.drawMissing();
			}
			//画分隔线
			noSscTrend.drawSplitLine();
			//如果需要辅助线，则画辅助线
			if($("#helpLine").prop("checked")){
				noSscTrend.drawHelpLine();
			}
		},
		drawSplitLine:function(){
			var $htrs = $("#noTb").find("._hidTr");
			var htrLen =  $("#noTb").find("._hidTr").size();
			for(var i=0;i<htrLen;i++){
				if((i+1)%5==0){
					$htrs.eq(i).after($("._hidLineTr:last").clone(true).show());
				}
			}
		},
		dropSplitLine:function(){
			$("#noTb").find("._hidLineTr").remove();
		},
		drawNoCount:function(){
			for(var i=0;i<4;i++){
				var info ="";
				if(i==0){
					info = "出现总次数";
					this.dncTrHelper(info);
				}else if(i==1){
					info = "平均遗漏值";
					this.dncTrHelper(info);					
				}else if(i==2){
					info = "最大遗漏值";
					this.dncTrHelper(info);
				}else if(i==3){
					info = "最大连号值";
					this.dncTrHelper(info);
				}
			}
		},
		dncTrHelper:function(info){
			var $hidTr = $("tr._hidCountTr:last").clone(true);
			$hidTr.show();
			$hidTr.find("._count_info").html(info);
			for(var j=3;j<6;j++){
				var $num = $("._num"+j+"_group:last").clone(true);
				$num.show();
				for(var k=0;k<10;k++){
					$hidTr.append($num.clone(true));
				}
			}
			
			var $allnum = $("._allnum_group:last").clone(true);
			$allnum.show();
			for(var k=0;k<10;k++){
				$hidTr.append($allnum.clone(true));
			}
			
			var $zxstnum = $("._zxst:last").clone(true);
			$zxstnum.show();
			for(var k=0;k<3;k++){
				$hidTr.append($zxstnum.clone(true));
			}
			
			$("#noTb").append($hidTr.clone(true));
		}
	};
}();