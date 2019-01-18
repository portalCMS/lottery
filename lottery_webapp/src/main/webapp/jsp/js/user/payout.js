//页面初始化处理
$(document).ready(function() {
	$.checkFrom.initBind(".checkedAttr", true, true, true);
	$("input[type='radio']").on('click', function() {
		$(this).closest("._cards").find("._cardInfo").removeClass("color_red");
		$(this).next().addClass("color_red");
	});
	$("input[type='radio']").eq(0).click();
	$("#payoutMenu").addClass("current");
	
	formatCardInfo.init($(".cardInfoBox"));
});

function submitDrawingForm() {
	// 做一次所有的表单格式验证
	var check = $.checkFrom.checkAllType("#payout_panle");
	if (!check) {
		return;
	}
	$("#referenceId").val($("input[name='cardId']:radio:checked").val());
	userDrawingForm.submit();
}

var formatCardInfo = function() {

	this.formatCard = function(obj) {
		$(obj).each(function(index, value) {
			
		});
	};

	return {
		init : function(obj) {
			formatCard(obj);
		}
	};
}();
