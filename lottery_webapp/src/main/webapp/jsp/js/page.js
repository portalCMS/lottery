// JavaScript Document

$(function() {
	// 顶部下拉
	$('#headJs > ul > li').hover(function() {
		$(this).children('ul').show();
	}, function() {
		$(this).children('ul').hide();
	});

	// // 消除input的默认值
	// $(':input').focus(function() {
	// var thisVal = $(this).val();
	// if (thisVal == this.defaultValue) {
	// $(this).val('');
	// }
	// }).blur(function() {
	// var thisVal = $(this).val();
	// if (thisVal == '') {
	// $(this).val(this.defaultValue);
	// }
	// });
	// 单行表格加背景色
	$('.accountTab tbody tr:even').find("td").css({
		'background-color' : '#f6f6f6'
	});

	// 选项卡
	$('#userTab > a').click(function() {
		$(this).addClass('current').siblings().removeClass('current');
		index = $(this).index('#userTab > a');
		$('.userTabBox').eq(index).show().siblings('.userTabBox').hide();
	});

	// 导航mainNavGP
	$('.mainNav3Ul > li').hover(
			function() {
				if ($(this).hasClass('mainNavGpJs')) {
					$(this).addClass('current mainNavGP').siblings()
							.removeClass('current');
				} else {
					$(this).addClass('current').siblings().removeClass(
							'current mainNavGP');
				}
				head.showAllLotterys();
				$(this).find('.mainNavDiv').show();
			}, function() {
				$(this).removeClass('current mainNavGP');
				$('.mainNavDiv').hide();
			});
	$('.mainNavDiv > dl:last').css({
		'border-bottom' : 'none',
		'padding-bottom' : 0
	});
	
	// 步骤引导
	// introJs().start();
});
