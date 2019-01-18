$(document).ready(function() {
	var ue = UE.getEditor('editor');
	ue.addListener("ready", function () {
        ue.setContent($("#content").val());
	});
	$("#total_recharge_menu").addClass("active");
	
	$("._addConfig").on('click',function(){
		trcActivity.addAmountConfig(this);
	});
	$("._deleteConfig").on('click',function(){
		trcActivity.deleteAmountConfig(this);
	});
});

var trcActivity = function(){
	
	return {
		addAmountConfig:function(obj){
			var $config = $(obj).closest("._amountConfig").clone(true);
			$config.find("input").each(function(){
				$(this).val("");
			});
			$config.find("._deleteConfig").show();
			$("#configDiv").append($config.clone(true));
			$("#configDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
		},
		deleteAmountConfig:function(obj){
			$(obj).closest("._amountConfig").remove();
			$("#configDiv").find("._cfIndex").each(function(index){
				$(this).text(index+1);
			});
		}
	};
}();