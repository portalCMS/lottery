$(document).ready(function() {
	$("#trendSelect li").on('click',function(){
		$("#noTb").empty();
		$("#mylineDiv").empty();
		$("#trendSelect li").removeClass("current");
		$(this).addClass("current");
		nozhTrend.queryRecords();
	});
	$("#missing").prop("checked",true);
	$("#helpLineLabel").hide();
	
	$("#splitLine").prop("checked",true);
	
	$("#splitLine").on('click',function(){
		if($(this).prop("checked")){
			nozhTrend.drawSplitLine();
		}else{
			nozhTrend.dropSplitLine();
		}
	});
	$("#missing").on('click',function(){
		if($(this).prop("checked")){
			nozhTrend.drawMissing();
		}else{
			nozhTrend.dropMissing();
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
	nozhTrend.queryRecords();
	
});
var nozhTrend = function(){
	return{
		queryRecords:function(){
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
		dropMissing:function(){
			var $rows = $("#noTb").find("tr._hidTr");
			var rowsize = $rows.size();
			for(var j=2;j<23;j++){
				for(var i=0;i<rowsize;i++){
					var $row = $rows.eq(i);
					var $tds = $row.find("td");
					var $td =  $tds.eq(j);
					var c =$td.attr("class");
					if(c.indexOf("_open_num")==-1&&c.indexOf("_opendx")==-1){
						$td.removeClass("_whiteWord");
						$td.html(" ");
					}
				}
			}
		},
		drawMissing:function(){
			var $rows = $("#noTb").find("tr._hidTr");
			var rowsize = $rows.size();
			for(var j=2;j<23;j++){
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
					if(c.indexOf("_open_num")==-1&&c.indexOf("_opendx")==-1){
						$td.addClass("_whiteWord");
						$td.html(missingIndex);
						missingIndex++;
						sameCount=0;
					}else if(c.indexOf("_opendx")!=-1){
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
			$("._lotInfo").html(data.lotName+"综合走势图  ");
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
				
				for(var j=1;j<12;j++){
					var $allnum = $("._allnum_group:last").clone(true);
					$allnum.show();
					for(var k=0;k<5;k++){
						if(j==parseInt(nums[k])){
							$allnum.html(nums[k]);
							$allnum.addClass("_open_num");
							break;
						}else{
							$allnum.html(" ");
							$allnum.removeClass("_open_num");
						}
					}
					$hidTr.append($allnum.clone(true));
				}
				
				var $dx = $("._dx:last").clone(true);
				$dx.show();
				for(var k=0;k<nums.length;k++){
					var num = parseInt(nums[k]);
					if(num>5){
						$dx.removeClass("_opendx");
						$dx.addClass("_opendx");
						$dx.html("大");
						$hidTr.append($dx.clone(true));
						$dx.removeClass("_opendx");
						$dx.html(" ");
						$hidTr.append($dx.clone(true));
					}else{
						$dx.removeClass("_opendx");
						$dx.html(" ");
						$hidTr.append($dx.clone(true));
						$dx.addClass("_opendx");
						$dx.html("小");
						$hidTr.append($dx.clone(true));
					}
				}
				
				var sum=0;
				var max=0;
				var min=12;
				var bc=0;
				var dxpw="";
				var hc=0;
				for(var k=0;k<nums.length;k++){
					var num = parseInt(nums[k]);
					sum += num;
					if(max<num){
						max = num;
					}
					if(min>num){
						min=num;
					}
					if(num>5){
						dxpw+="大";
					}else{
						dxpw+="小";
					}
					
					if(num%2==0){
						bc++;
					}
					//判断是否是合数，是的话数量加1.
					if(num>3){
						for(var p=2;p<num;p++){
							if(num%p==0){
								hc++;
								break;
							}
						}
					}
					
				}
				var $sum = $("td._sum:last").clone(true);
				var $betwen = $("td._betwen:last").clone(true);
				var $dsb = $("td._dsb:last").clone(true);
				var $zhb = $("td._zhb:last").clone(true);
				var $dxpw = $("td._dxpw:last").clone(true);
				$sum.show().html("&nbsp;&nbsp;&nbsp;"+sum+"&nbsp;&nbsp;&nbsp;");
				$betwen.show().html("&nbsp;&nbsp;&nbsp;"+(max-min)+"&nbsp;&nbsp;&nbsp;");
				$dsb.show().html("&nbsp;&nbsp;"+bc+":"+(5-bc)+"&nbsp;&nbsp;");
				$zhb.show().html("&nbsp;&nbsp;"+(5-hc)+":"+hc+"&nbsp;&nbsp;");
				$dxpw.show().html("&nbsp;&nbsp;"+dxpw+"&nbsp;&nbsp;");
				$hidTr.append($sum.clone(true));
				$hidTr.append($betwen.clone(true));
				$hidTr.append($dsb.clone(true));
				$hidTr.append($zhb.clone(true));
				$hidTr.append($dxpw.clone(true));
				$("#noTb").append($hidTr.clone(true));
			}
			//统计数据行插入
			nozhTrend.drawNoCount();
			//填充遗漏期数，计算统计数据
			if($("#missing").prop("checked")){
				nozhTrend.drawMissing();
			}
			//每5行画的辅助线
			if($("#splitLine").prop("checked")){
				nozhTrend.drawSplitLine();
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
			var $allnum = $("._allnum_group:last").clone(true);
			$allnum.show();
			for(var k=0;k<11;k++){
				$hidTr.append($allnum.clone(true));
			}
			
			var $dx = $("._dx:last").clone(true);
			$dx.show();
			for(var k=0;k<10;k++){
				$hidTr.append($dx.clone(true));
			}
			
			$("#noTb").append($hidTr.clone(true));
		}
	};
}();