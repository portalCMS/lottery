var defaultIndex = function(){
	var callback = {
			"init11x5":function(data){
				if(data.success!=null&&data.success!=undefined){
					formPost.showErrorMessage(data.success);
				}else{
					formPost.showErrorMessage(data.failed);
				}
				$("._syxw .xuanHaoUl > li").removeClass("current");
				$("._syxw ._mult").val(1);
				$("._syxw ._betCount").text(0);
				$("._syxw ._totalAmount").text(0.00);
				$("#SYXW_BET").click(function(){
					defaultIndex.submitForm("_syxw");
				});
				$("#SYXW_BET span").text("立即投注");
				$("#token").val(data.token);
				formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
			},
			"initSSC":function(data){
				if(data.success!=null&&data.success!=undefined){
					formPost.showErrorMessage(data.success);
				}else{
					formPost.showErrorMessage(data.failed);
				}
				$("._ssc select").val(0).removeClass("color_red current");
				$("._ssc ._mult").val(1);
				$("._ssc ._totalAmount").text(0.00);
				$("#token").val(data.token);
				$("#SSC_BET span").text("立即投注");
				$("#SSC_BET").click(function(){
					defaultIndex.submitForm("_ssc");
				});
				formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
			},
			"bet11x5ErrorFunc":function(){
				$("#SYXW_BET").addClass("Bet_btn_OK");
				$("#SYXW_BET").click(function(){
					defaultIndex.submitForm("_syxw");
				});
				$("#SYXW_BET span").text("立即投注");
			},
			"betSscErrorFunc":function(){
				$("#SSC_BET").addClass("Bet_btn_OK");
				$("#SSC_BET").click(function(){
					defaultIndex.submitForm("_ssc");
				});
				$("#SSC_BET span").text("立即投注");
			}
		};
	return{
		showNitice:function(id){
			var attrs = new Array();
			var vals = new Array();
			attrs.push("id");
			vals.push(id);
			formPost.submitForm1("shownotice.html",attrs,vals);
		},
		//跳转到其它的彩种投注页面
		switchLottery:function(code,group){
			var attrs = new Array();
			attrs.push("lotteryCode");
			var values = new Array();
			values.push(code);
			
			var action = "";
			if(group=="SSC"){
				 action = ctx+"/showssc.html";
			}else if(group=="SYXW"){
				 action = ctx+"/show11x5.html";
			}
			 formPost.submitForm(action,attrs,values,true);
		},
		initBet11x5:function(){
			var attrs = new Array();
			attrs.push("lotteryCode");
			var values = new Array();
			values.push(90001);
			var action = "hotLotteryTask.shtml";
			
			formPost.submitAjaxForm(action,attrs,values,defaultIndex.showHot11x5);
		},
		showHot11x5:function(data){
			defaultIndex.showHotBetInfo(data, "_syxw");
		},
		showHotBetInfo:function(data,betType){
			$("."+betType+" ._lotName").text(data.lottery.lotteryName);
			$("."+betType+" ._lastAward").text(data.record.issue);
			$("."+betType+" ._lastLotNum").text(data.record.lotteryNumber);
			$("."+betType+" ._totalTimes").text(data.lottery.totalTimes);
			$("."+betType+" ._currIssue").text(data.task.lotterySeries);
			
			$("."+betType+" ._lotCode").val(data.lottery.lotteryCode);
			$("."+betType+" ._rebates").val(data.bonusMap.rebates);
			
			var pc = $("."+betType+" ._playCode").val();
			var awardAmount ;
			for(var i=0;i<data.plays.length;i++){
				var play = data.plays[i];
				if(play.modelCode==pc){
					awardAmount = play.winAmount*data.bonusMap.payRatio;
					break;
				}
			}
			if(data.bonusMap.payRatio!=undefined){
				$("."+betType+" ._am").addClass("color_red").text(awardAmount);
				$("."+betType+" ._am").formatCurrency();
			}
			
		},
		initBetSSC:function(){
			var attrs = new Array();
			attrs.push("lotteryCode");
			var values = new Array();
			values.push(91002);
			var action = "hotLotteryTask.shtml";
			
			formPost.submitAjaxForm(action,attrs,values,defaultIndex.showHotSSC);
		},
		showHotSSC:function(data){
			defaultIndex.showHotBetInfo(data, "_ssc");
		},
		packageData:function(attrs,values,betType,betNums){
				attrs.push("issueNos");
				var mutiple = $("."+betType+" ._mult").val();
				values.push($("."+betType+" ._currIssue").text()+":"+mutiple);
				attrs.push("volist[0].lotteryCode");
				attrs.push("volist[0].playCode");
				attrs.push("volist[0].betModel");
				attrs.push("volist[0].betMoney");
				attrs.push("volist[0].betType");
				attrs.push("volist[0].bileNum");
				attrs.push("volist[0].betNum");
				attrs.push("volist[0].rebates");
				attrs.push("volist[0].selectCode");
				attrs.push("volist[0].betCount");
				values.push($("."+betType+" ._lotCode").val());
				values.push($("."+betType+" ._playCode").val());
				values.push(1);
				var betCount = $("."+betType+" ._betCount").text();
				values.push(2*betCount);
				values.push(20000);
				
				values.push("");
				values.push(betNums);
				values.push($("."+betType+" ._rebates").val());
				values.push($("."+betType+" ._selectCode").val());
				values.push($("."+betType+" ._betCount").text());
		},
		submitForm:function(betType){
			var betNums="";
			if(betType=="_syxw"){
				var $nums = $("._syxw .xuanHaoUl .current");
				if($nums.size()<5){
					formPost.showErrorMessage("亲，请至少选择5个号码进行投注哦！");
					return;
				}
				$nums.each(function(){
					var str = $(this).find("p").html().trim();
					if(betNums ==""){
						betNums = str;
					}else{
						betNums +=","+str ;
					}
				});
			}else{
				var size = $("._ssc .current").size();
				if(size==3){
					betNums = "*|*|"+$("._i1s").val()+"|"+$("._i2s").val()+"|"+$("._i3s").val();
				}
			}
			
			if(betNums==""){
				formPost.showErrorMessage("亲,别忘记选择你的投注号码哦!");
				return;
			}
			if(betType=="_ssc"){
				$("#SSC_BET").removeClass("Bet_btn_OK");
				$("#SSC_BET").unbind();
				$("#SSC_BET span").text("投注中，请稍候..");
			}else if(betType=="_syxw"){
				$("#SYXW_BET").removeClass("Bet_btn_OK");
				$("#SYXW_BET").unbind();
				$("#SYXW_BET span").text("投注中，请稍候..");
			}
			
			
			var attrs = new Array();
			var values = new Array();
			attrs.push("orderAmount");
			var mutiple = $("."+betType+" ._mult").val();
			var betCount = $("."+betType+" ._betCount").text();
			values.push(2*mutiple*betCount);
			
			attrs.push("token");
			values.push($("#token").val());
			
			attrs.push("awardStop");
			values.push("0");
			
			attrs.push("isTrack");
			values.push("P");
			this.packageData(attrs,values,betType,betNums);
			if(betType=="_syxw"){
				formPost.submitAjaxForm1("bet11x5.shtml",attrs,values,callback["init11x5"],null);
				formPost.setErrorFunc(callback["bet11x5ErrorFunc"]);
				defaultIndex.initBet11x5();
			}else{
				formPost.submitAjaxForm1("betSSC.shtml",attrs,values,callback["initSSC"],null);
				formPost.setErrorFunc(callback["betSscErrorFunc"]);
				defaultIndex.initBetSSC();
			}
			
		},
		navSwitch:function(){
			//导航mainNavGP
			$('.mainNav3Ul > li').hover(
					function() {
						if ($(this).hasClass('mainNavGpJs')) {
							$(this).addClass('current mainNavGP').siblings()
									.removeClass('current');
						} else {
							$(this).addClass('current').siblings().removeClass(
									'current mainNavGP');
						}
						$(this).find('.mainNavDiv').show();
					}, function() {
						$(this).removeClass('current mainNavGP');
						$('.mainNavDiv').hide();
					});
			$('.mainNavDiv > dl:last').css({
				'border-bottom' : 'none',
				'padding-bottom' : 0
			});
			//可关闭banner
			$('.iCloseBan > span').click(function() {
				$(this).parent().remove();
			});
			//banner切换
			var speed = 5000;
			var focusTime;
			var focusIco = $(".i3BanIco > a");
			var len = $(".i3BanIco > a").length;
			

			$(focusIco).mouseover(function() {
				index = $(focusIco).index(this);
				defaultIndex.showImg(index);
			}).eq(0).mouseover();

			$(".i3Ban").hover(function() {
				clearInterval(focusTime);
			}, function() {
				focusTime = setInterval(function() {
					defaultIndex.showImg(index);
					index++;
					if (index == len) {
						index = 0;
					}
				}, speed);
			}).trigger("mouseout");

			//今日幸运小提示
			$('.luckyText > img').hover(function() {
				$('.luckyTips').show();
			}, function() {
				$('.luckyTips').hide();
			});
			//快速链接去border
			$('.serviceUl > li:last').css('border-bottom', 'none');
			//站内聊天收缩
			$('.serviceDt').click(function() {
				$(this).toggleClass('serviceDtOver');
				$('.serviceDd').toggle();
			});
		},
		showImg : function (index) {
			var focusIco = $(".i3BanIco > a");
			var yhSpeed = 500;
			var i3BanUl = $('.i3BanUl');
			var i3BanW = $('.i3Ban').width();
			if (!$(i3BanUl).is(":animated")) {
				$(i3BanUl).animate({'left' : -(index * i3BanW)}, yhSpeed);
			}
			
			$(focusIco).removeClass("current").eq(index)
					.addClass("current");
		},
		//从一个给定的数组arr中,随机返回num个不重复项 
		getArrayItems : function(arr, num){
			//新建一个数组,将传入的数组复制过来,用于运算,而不要直接操作传入的数组; 
			var temp_array = new Array(); 
			for (var index in arr) { 
				temp_array.push(arr[index]); 
			} 
			//取出的数值项,保存在此数组 
			var return_array = new Array(); 
			for (var i = 0; i<num; i++) { 
				//判断如果数组还有可以取出的元素,以防下标越界 
				if (temp_array.length>0) { 
					//在数组中产生一个随机索引 
					var arrIndex = Math.floor(Math.random()*temp_array.length); 
					//将此随机索引的对应的数组元素值复制出来 
					return_array[i] = temp_array[arrIndex]; 
					//然后删掉此索引的数组元素,这时候temp_array变为新的数组 
					temp_array.splice(arrIndex, 1); 
				} else { 
					//数组中数据项取完后,退出循环,比如数组本来只有10项,但要求取出20项. 
					break; 
				} 
			} 
			return return_array; 
		},
		checkSSCIsBetOk:function(){
			var size = $(".i3TabDl").find(".current").size();
			if(size==3){
				$("#SSC_BET").addClass("Bet_btn_OK");
			}else{
				$("#SSC_BET").removeClass("Bet_btn_OK");
			}
		}
	};
}();

var luckyNumber = function(){
	
	var key = function(){
		var key = getNowDate(0,0)+":"+"luckayNumber";
		return key;
	};
	var removeLuckyNumber = function(){
		var oldkey = getNowDate(0,-1)+":"+"luckayNumber";
		$.cookie(oldkey,null);
	};
	var generateLuckyNumber = function(){
		removeLuckyNumber();
		var luckyNumber = "<span>"+GetRandomNum(0,11)+"</span>"+"<span>"+GetRandomNum(0,11)+"</span>"+"<span>"+GetRandomNum(0,11)+"</span>";
		$.cookie(key(),luckyNumber,{expires: 1,path:"/",maxAge:2});
		return luckyNumber;
	};
	var GetRandomNum = function (Min,Max){   
		var Range = Max - Min;   
		var Rand = Math.random();   
		return(Min + Math.round(Rand * Range));   
	};
	var showFirstNotice = function(data){
		$("body").sMsg({
			title : "最新公告："+data.entity.title,
			content : data.entity.content,
			autoDisplay : true,
			isNotice : true,
			actionEvent : ""
		});
	};
	var showkey = "show";
	return{
		getLuckyNumber:function(){
			if($.cookie(key())==undefined||$.cookie(key())==null){
				return generateLuckyNumber();
			}else{
				var luckyNumber = $.cookie(key());
				removeLuckyNumber();
				return luckyNumber;
			}
			
		},
		showFirstNotice:function(){
			if($.cookie(showkey)==undefined||$.cookie(showkey)==null){
				var attr = new Array();
				var vals = new Array();
				attr.push("id");
				vals.push($("._showNotice").eq(0).attr("id"));
				formPost.submitAjaxForm("showFirstNotice.shtml",attr,vals,showFirstNotice,null);
			}
		}
	};
}();


$(document).ready(function(){
	$("._showNotice").click(function(){
		defaultIndex.showNitice($(this).attr("id"));
	});
	$("._setLucky").html(luckyNumber.getLuckyNumber());
	
	//luckyNumber.showFirstNotice();
	
	defaultIndex.initBet11x5();
	
	defaultIndex.initBetSSC();
	
	defaultIndex.navSwitch();
	
	$("#suijiBtn1").click(function(){
		$(".xuanHaoUl > li").removeClass('current');
		var temp_arr = defaultIndex.getArrayItems([0,1,2,3,4,5,6,7,8,9,10],5);
		for(var i=0;i<5;i++) {
			var index = temp_arr[i];
			$($(".xuanHaoUl > li").get(index)).addClass("current");
		}
		$("._syxw ._betCount").text(1);
		var bc = parseInt($("._syxw ._betCount").text());
		var mutiple = $("._syxw  ._mult").val();
		$("._syxw ._totalAmount").text(2*bc*mutiple);
		$("._syxw ._totalAmount").formatCurrency();
		$("#SYXW_BET").addClass("Bet_btn_OK");
	});
	
	$(".xuanHaoUl li a").on('click',function(){
		$(this).closest("li").toggleClass("current");
		var bc = $(".xuanHaoUl .current").size();
		if(bc>=5){
			var fmv = 1;
			for(var j=bc;j>5;j--){
				fmv = fmv*j;
			}
			
			var fzv=1;
			for(var k=1;k<=bc-5;k++){
				fzv = fzv*k;
			}
			
			$("._syxw ._betCount").text(fmv/fzv);
			var bc = parseInt($("._syxw ._betCount").text());
			var mutiple = $("._syxw  ._mult").val();
			$("._syxw ._totalAmount").text(2*bc*mutiple);
			$("._syxw ._totalAmount").formatCurrency();
			if($("._syxw").find(".Bet_btn_OK").html()==undefined){
				$("#SYXW_BET").addClass("Bet_btn_OK");
			}
		}else{
			$("._syxw ._betCount").text(0);
			$("._syxw ._totalAmount").text(0.00);
			$("#SYXW_BET").removeClass("Bet_btn_OK");
		}
	});
	
	$("#ssc_random").click(function(){
		var temp_arr1 = defaultIndex.getArrayItems([0,1,2,3,4,5,6,7,8,9],1);
		var temp_arr2 = defaultIndex.getArrayItems([0,1,2,3,4,5,6,7,8,9],1);
		var temp_arr3 = defaultIndex.getArrayItems([0,1,2,3,4,5,6,7,8,9],1);
		$("._i1s").addClass("color_red current").val(temp_arr1[0]);
		$("._i2s").addClass("color_red current").val(temp_arr2[0]);
		$("._i3s").addClass("color_red current").val(temp_arr3[0]);
		
		var bc = parseInt($("._ssc ._betCount").text());
		var mutiple = $("._ssc  ._mult").val();
		$("._ssc ._totalAmount").text(2*bc*mutiple);
		$("._ssc ._totalAmount").formatCurrency();
		$("#SSC_BET").addClass("Bet_btn_OK");
	});
	
	$("._i1s").on('click',function(){
		$(this).addClass("color_red current");
		defaultIndex.checkSSCIsBetOk();
	});
	
	$("._i2s").on('click',function(){
		$(this).addClass("color_red current");
		defaultIndex.checkSSCIsBetOk();
	});
	
	$("._i3s").on('click',function(){
		$(this).addClass("color_red current");
		defaultIndex.checkSSCIsBetOk();
	});
	
	$("#SYXW_BET").on('click',function(){
		defaultIndex.submitForm("_syxw");
	});
	
	$("#SSC_BET").on('click',function(){
		defaultIndex.submitForm("_ssc");
	});
	
	$("._subBtn").on('click',function(){
		var $val = $(this).next();
		var val = Number($val.val());
		$val.val(val - 1 > 0 ? val - 1 : 1);
		$val.trigger("change");
	});
	
	$("._addBtn").on('click',function(){
		var $val = $(this).prev();
		var val = Number($val.val());
		$val.val(val + 1);
		$val.trigger("change");
	});
	
	$("._syxw ._mult").on("change keyup",function(){
		var bc = parseInt($("._syxw ._betCount").text());
		var mutiple = $(this).asNumber();
		if(mutiple<1){
			mutiple = 1;
			$(this).val(1);
		}
		$("._syxw ._totalAmount").text(2*bc*mutiple);
		$("._syxw ._totalAmount").formatCurrency();
	});
	
	$("._ssc ._mult").on("change keyup",function(){
		var bc = parseInt($("._ssc ._betCount").text());
		var mutiple =$(this).asNumber();
		if(mutiple<1){
			mutiple = 1;
			$(this).val(1);
		}
		$("._ssc ._totalAmount").text(2*bc*mutiple);
		$("._ssc ._totalAmount").formatCurrency();
	});
	
	$("._todyIntegral").click(function(){
		everyDay.registration($(this));
	});
	
	(new CenterImgPlay()).Start();
	
	$("#sliderBar_close").click(function(){
		$("#sliderBar").hide();
	});
});

var everyDay = function(){
	var $btn;
	var callBackFuc = {
		"registration":function(data){
			var obj = data.integral;
			$btn.removeClass("smallBtn");
			$btn.removeClass("_todyIntegral");
			$btn.addClass("smallDisable");
			$btn.find("span").text("今日已签到");
			$("._integral").text(obj.integral);
			var $star = $("._level").find("span").clone(true);
			$("._level").html("");
			for(var i=1;i<=obj.level;i++){
				$("._level").append($star.clone(true));
			}
			$("._continuou").find("span").text(obj.continuou);
		}
	};
	return{
		registration:function(btn){
			$btn = $(btn);
			var attr = new Array();
			var vals = new Array();
			formPost.submitAjaxForm("registration.shtml",attr,vals,callBackFuc["registration"],null);
		}
	};
}();

function CenterImgPlay() {
    this.list = $(".playDiv").children(":first").children();
    this.indexs = [];
    this.length = this.list.length;
    //图片显示时间
    this.timer = 3500;
    this.showTitle = $(".play_title");

    var index = 0, self = this, pre = 0, handid, isPlay = false, isPagerClick = false;

    this.Start = function () {
        this.Init();
        //计时器，用于定时轮播图片
        handid = setInterval(self.Play, this.timer);
    };
    //初始化
    this.Init = function () {
        var o = $(".pager ul li");

        for (var i = o.length - 1, n = 0; i >= 0; i--, n++) {
            this.indexs[n] = o.eq(i).click(self.PagerClick);
        }
    };
    this.Play = function () {
        isPlay = true;
        index++;
        if (index == self.length) {
            index = 0;
        }
        //先淡出，在回调函数中执行下一张淡入
        self.list.eq(pre).fadeOut(300, "linear", function () {
            var info = self.list.eq(index).fadeIn(500, "linear", function () {
                isPlay = false;
                if (isPagerClick) { handid = setInterval(self.Play, self.timer); isPagerClick = false; }
            }).attr("title");
            //显示标题
            self.showTitle.text(info);
            //图片序号背景更换
            self.indexs[index].css("background-color", "#FF70Ad");
            self.indexs[pre].css("background-color", "#6f4f67");

            pre = index;
        });
    };
    //图片序号点击
    this.PagerClick = function () {
        if (isPlay) { return; }
        isPagerClick = true;

        clearInterval(handid);

        var oPager = $(this), i = parseInt(oPager.text()) - 1;

        if (i != pre) {
            index = i - 1;
            self.Play();
        }
    };
};
