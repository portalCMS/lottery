var xyyKeyManager = function() {

	this.moveScroll = function(obj, offset) {
		var top = obj.offset().top;
		if (navigator.userAgent.indexOf("Chrome") > -1) {
			$("body").stop().animate({
				scrollTop : top - offset
			}, 500);
		} else {
			document.documentElement.scrollTop = top - offset;
		}
		return false;
		
		//var b = browser.browserMatch();
		//console.info(b);
		//document.documentElement.scrollTop = top;
		
		return false;
	};
	this.bindTab = function() {
		var items = $(".cathectic-num a");
		$(document).on("keydown", function(e) {
			var keyCode = e.keyCode;
			//ALT键+数字1-8 切换玩法Tab
			if(e.altKey && keyCode == 49 ){
				moveScroll(items.eq(1), 100);
				items.eq(0).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 50 ){
				moveScroll(items.eq(1), 100);
				items.eq(1).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 51 ){
				moveScroll(items.eq(1), 100);
				items.eq(2).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 52 ){
				moveScroll(items.eq(1), 100);
				items.eq(3).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 53 ){
				moveScroll(items.eq(1), 100);
				items.eq(4).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 54 ){
				moveScroll(items.eq(1), 100);
				items.eq(5).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 55 ){
				moveScroll(items.eq(1), 100);
				items.eq(6).trigger("click");
				return false;
			}
			if(e.altKey && keyCode == 56 ){
				moveScroll(items.eq(1), 100);
				items.eq(7).trigger("click");
				return false;
			}
		});
	};

	this.bindSubTab = function() {
		var index = 1, count = 0 ,tabIndex = 0;
		$(document).on("keydown",function(e) {
			var keyCode = e.keyCode;
			// 顺序切换单选按钮，q 【tab键有问题】
			if (keyCode == 81) {
				var tabs = $(".cathectic-num a");
				tabs.each(function(){
					var cla = $(this).attr("class");
					if (cla == "cathectic-num_on") {
						tabIndex = parseInt($(this).attr("name"));
					};
				});
				var radios = $(".cathectic-type-choose span input[type=radio]");
				var tempRadios = new Array();
				radios.each(function() {
					var id = $(this).attr("id").split("_")[1];
					if(id == tabIndex){
						tempRadios.push(this);
					}
				});
				count = tempRadios.length;
				if (index < count) {
					index += 1;
				} else {
					index = 1;
				}
				$(tempRadios[index - 1]).trigger("click");
				if($(".cathectic-main-choose-text").css("display") == "block"){
					$(".cathectic-main-choose-text textarea").focus();
					$(".cathectic-main-choose-text textarea").val("");
				}
			}
		});
	};

	this.bindEvent = function() {
		$(document).on("keydown", function(e) {
			var keyCode = e.keyCode;
			switch (keyCode) {
			case 74:
				// 随机一注 j
				$(".cathectic-selector-item select").trigger("click");
				moveScroll($(".cathectic-num a").eq(1), 100);
				break;
			case 75:
				// 添加选号内容至号码列表 k
				$(".CSR_btn").trigger("click");
				moveScroll($(".CSR_btn"), 100);
				break;
			case 73:
				// 清除选号 i
				$(".CSR_clear").trigger("click");
				moveScroll($(".cathectic-num a").eq(1), 100);
				break;
			case 76:
				// 提交投注 l
				$("._betbtn").trigger("click");
				moveScroll($("._betbtn"), 100);
				break;
			case 79:
				// 清除选号列表 o
				$(".cathectic-resluts-doubling a").trigger("click");
				moveScroll($(".CSR_btn"), 100);
				break;
			}
		});
	};
	this.bindDoubling = function() {
		$(document).on("keydown", function(e) {
			var keyCode = e.keyCode;
			var btns = $(".cathectic-resluts-doubling span i");
			// 减一个倍数 】
			if (keyCode == 221) {
				moveScroll(btns.eq(0), 200);
				btns.eq(1).trigger("click");
			}
			// 增加一个倍数【
			if (keyCode == 219) {
				moveScroll(btns.eq(0), 200);
				btns.eq(0).trigger("click");
			}
		});
	};
	return {
		init : function() {
			bindTab();
			bindSubTab();
			bindEvent();
			bindDoubling();
		}
	};
}();

$(document).ready(function() {
	xyyKeyManager.init();
});