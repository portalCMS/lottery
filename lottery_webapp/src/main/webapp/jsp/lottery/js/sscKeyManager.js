var sscKeys = {
	action : true,
	createNew : function() {
		var keys = {};
		keys.bindEvent = function() {
			$(document).on("keydown", function(e) {
				var keyCode = e.keyCode;
				switch (keyCode) {
				case 74:
					// 随机一注 j
					$(".ssc-selector-item .btn").trigger("click");
					moveScroll($(".ssc-num a").eq(1), 100);
					break;
				case 75:
					// 添加选号内容至号码列表 k
					$(".CSR_btn").trigger("click");
					moveScroll($(".CSR_btn"), 100);
					break;
				case 73:
					// 清除选号 i
					$(".CSR_clear").trigger("click");
					moveScroll($(".ssc-num a").eq(1), 100);
					break;
				case 76:
					// 提交投注 l
					$("._betbtn").trigger("click");
					moveScroll($("._betbtn"), 100);
					break;
				case 79:
					// 清除选号列表 o
					$(".ssc-resluts-doubling a").trigger("click");
					moveScroll($(".CSR_btn"), 100);
					break;
				}
			});
		};
		keys.bindTab = function() {
			$(document).on("keydown", function(e) {
				var items = $(".ssc-num a");
				var keyCode = e.keyCode;

				if (e.altKey && keyCode == 49) {
					moveScroll(items.eq(1), 100);
					items.eq(0).trigger("click");
				}
				if (e.altKey && keyCode == 50) {
					moveScroll(items.eq(1), 100);
					items.eq(1).trigger("click");
				}
				if (e.altKey && keyCode == 51) {
					moveScroll(items.eq(1), 100);
					items.eq(2).trigger("click");
				}
				if (e.altKey && keyCode == 52) {
					moveScroll(items.eq(1), 100);
					items.eq(3).trigger("click");
				}
				if (e.altKey && keyCode == 53) {
					moveScroll(items.eq(1), 100);
					items.eq(4).trigger("click");
				}
				if (e.altKey && keyCode == 54) {
					moveScroll(items.eq(1), 100);
					items.eq(5).trigger("click");
				}
			});
		};
		keys.bindSubTab = function() {
			var index = 1, count = 0;
			$(document).on("keydown",
				function(e) {
					var keyCode = e.keyCode;
					// 顺序切换单选按钮，q 【tab键有问题】
					if (keyCode == 81) {
						var radios = $(".ssc-type-choose input[type=radio]");
						count = radios.length;
						if (index < count) {
							index += 1;
						} else {
							index = 1;
						}
						radios.eq(index - 1).trigger("click");
						radios.each(function() {
							if ($(this).prop("checked") == true) {
								id = $(this).attr("id");
								// 切换时没执行，但是模拟随机一注却执行，不懂
								switch (id) {
								case "SSC_2_3":
									$(".ssc-selector-item select").change(
									function() {
										$(this).find("option").eq(1).prop("selected",true);
									});
									break;
								case "SSC_4_2":
									$(".ssc-selector-item select").change(
										function() {
											$(this).find("option").eq(1).prop("selected",true);
										});
									break;
								case "SSC_4_5":
									$(".ssc-selector-item select").change(
										function() {
											$(this).find("option").eq(2).prop("selected",true);
										});
									break;
								default:
									$(".ssc-selector-item select").change(
										function() {
											$(this).find("option").get(0).selected = true;
										});
									break;
								}
							}
						});
						if($(".ssc-main-choose-text").css("display") == "block"){
							$(".ssc-main-choose-text textarea").focus();
							$(".ssc-main-choose-text textarea").val('');
						}
					}
				});
		};
		keys.bindDoubling = function() {
			$(document).on("keydown", function(e) {
				var keyCode = e.keyCode;
				var btns = $(".ssc-resluts-doubling span i");
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
		moveScroll = function(obj, offset) {
			var top = obj.offset().top;
			if (navigator.userAgent.indexOf("Chrome") > -1) {
				$("body").stop().animate({
					scrollTop : top - offset
				}, 500);
			} else {
				document.documentElement.scrollTop = top - offset;
			}
			return false;
		};
		return keys;
	}
};
$(document).ready(function() {
	var k = sscKeys.createNew();
	k.bindEvent();
	k.bindTab();
	k.bindSubTab();
	k.bindDoubling();
});