$(document).ready(function() {
	$("#trendSelect li").on('click',function(){
		$("#noTb").empty();
		$("#mylineDiv").empty();
		$("#trendSelect li").removeClass("current");
		$(this).addClass("current");
		nozhTrend.queryRecords();
	});
	$("#helpLineLabel").hide();
	$("#missing").prop("checked",true);
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
			var colsize = $rows.eq(0).find("td").size();
			var rowsize = $rows.size();
			for(var j=2;j<colsize-1;j++){
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
			var colsize = $rows.eq(0).find("td").size();
			var rowsize = $rows.size();
			for(var j=2;j<colsize-1;j++){
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
			$("._lotInfo").html(data.lotName+"五星综合走势图  ");
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
				var maxNum=0;
				var minNum=9;
				for(var k=0;k<nums.length;k++){
					var num = parseInt(nums[k]);
					if(minNum>num){
						minNum =num;
					}
					if(maxNum<num){
						maxNum =num;
					}
				}
				
				var numMap = new Map();
				for(var j=0;j<nums.length;j++){
					var sameNumCount=1;
					var num1 = nums[j];
					for(var k=0;k<nums.length&&j!=k;k++){
						var num2 = nums[k];
						if(num1==num2&&numMap.get(num1)==undefined){
							sameNumCount++;
							numMap.put(num1,sameNumCount);
							break;
						}else if(num1==num2&&numMap.get(num1)!=undefined){
							var samNum = numMap.get(num1);
							numMap.remove(num1);
							samNum++;
							numMap.put(num1,samNum);
							break;
						}
					}
				}
				//开奖号码分布
				var $allnum = $("._allnum_group:last").clone(true);
				$allnum.show();
				for(var j=0;j<10;j++){
					for(var k=0;k<5;k++){
						$allnum.removeClass("_open_num");
						$allnum.removeClass("_open_num2");
						$allnum.removeClass("_open_num3");
						$allnum.removeClass("_open_num4");
						$allnum.removeClass("_open_num5");
						if(j==parseInt(nums[k])){
							$allnum.html(nums[k]);
							if(numMap.get(nums[k])==undefined){
								$allnum.addClass("_open_num");
							}else if(numMap.get(nums[k])==2){
								$allnum.addClass("_open_num2");
							}else if(numMap.get(nums[k])==3){
								$allnum.addClass("_open_num3");
							}else if(numMap.get(nums[k])==4){
								$allnum.addClass("_open_num4");
							}else if(numMap.get(nums[k])==5){
								$allnum.addClass("_open_num5");
							}
							break;
						}else{
							$allnum.html(" ");
						}
					}
					$hidTr.append($allnum.clone(true));
				}
				//开奖号码跨度值
				var $kd = $("._kd:last").clone(true);
				$kd.show();
				for(var j=0;j<10;j++){
					if(j==maxNum-minNum){
						$kd.html(j);
						$kd.addClass("_open_num");
					}else{
						$kd.html(" ");
						$kd.removeClass("_open_num");
					}
					$hidTr.append($kd.clone(true));
				}
				
				var sum=0;
				var bc=0;
				var dc=0;
				var hc=0;
				for(var k=0;k<nums.length;k++){
					var num = parseInt(nums[k]);
					sum += num;
					if(num>4){
						dc++;
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
					}else if(num==0){
						hc++;
					}
					
				}
				
				var $dsb = $("td._dsb:last").clone(true);
				var $zhb = $("td._zhb:last").clone(true);
				var $dxb = $("td._dxb:last").clone(true);
				$dsb.show();
				$zhb.show();
				$dxb.show();
				//填充显示大小比
				for(var k=0;k<6;k++){
					if(k==(5-dc)){
						$dxb.addClass("_opendx");
						$dxb.html("&nbsp;"+dc+":"+(5-dc)+"&nbsp;");
					}else{
						$dxb.removeClass("_opendx");
						$dxb.html(" ");
					}
					$hidTr.append($dxb.clone(true));
				}
				//填充显示单双比
				for(var k=0;k<6;k++){
					if(k==bc){
						$dsb.addClass("_opendx");
						$dsb.html("&nbsp;"+(5-bc)+":"+bc+"&nbsp;");
					}else{
						$dsb.removeClass("_opendx");
						$dsb.html(" ");
					}		
					$hidTr.append($dsb.clone(true));
				}
				//填充显示质和比
				for(var k=0;k<6;k++){
					if(k==hc){
						$zhb.addClass("_opendx");
						$zhb.html("&nbsp;"+(5-hc)+":"+hc+"&nbsp;");
					}else{
						$zhb.removeClass("_opendx");
						$zhb.html(" ");
					}	
					$hidTr.append($zhb.clone(true));
				}
				
				var $sum = $("td._sum:last").clone(true);
				$sum.show().html("&nbsp;&nbsp;&nbsp;"+sum+"&nbsp;&nbsp;&nbsp;");
				$hidTr.append($sum.clone(true));
				
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
			var colsize = $("#noTb").find("tr._hidTr").eq(0).find("td").size();
			var $hidTr = $("tr._hidCountTr:last").clone(true);
			$hidTr.show();
			$hidTr.find("._count_info").html(info);
			var $allnum = $("._allnum_group:last").clone(true);
			$allnum.show();
			for(var k=0;k<10;k++){
				$hidTr.append($allnum.clone(true));
			}
			
			var $kd = $("._kd:last").clone(true);
			$kd.show();
			for(var k=0;k<10;k++){
				$hidTr.append($kd.clone(true));
			}
			
			var $dxb = $("._dxb:last").clone(true);
			$dxb.show();
			for(var k=0;k<6;k++){
				$hidTr.append($dxb.clone(true));
			}
			
			var $dsb = $("._dsb:last").clone(true);
			$dsb.show();
			for(var k=0;k<6;k++){
				$hidTr.append($dsb.clone(true));
			}
			
			var $zhb = $("._zhb:last").clone(true);
			$zhb.show();
			for(var k=0;k<6;k++){
				$hidTr.append($zhb.clone(true));
			}
			$("#noTb").append($hidTr.clone(true));
		}
	};
}();