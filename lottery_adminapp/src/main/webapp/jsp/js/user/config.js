$(document).ready(function(){
	$("#pcode").attr("title", "点击刷新");
	$("#pcode").bind("click", function() {
		$("#pcode").attr("src", ctx+"/picode/ycode.do?key=configkey&&"+Math.random());
	});
	var items = $("._userLeaveSetting .form-group");
	items.each(function(index,value){
		$(this).find("input").on("blur",function(){
			$(items).eq(index+1).find("span._mina").eq(0).text($(this).val());
		});
	});
	
	$.checkFrom.initBind(".checkedAttr", true, true,true);
	
	userConfig.queryLevels();
});
	
var userConfig = function() {
	return {
		queryLevels:function(){
			formPost.submitAjaxForm("user/queryCardLevelConfig.do",null,null,this.updateLevels);
		},
		updateLevels:function(data){
			var $levelDiv = $("._levelDiv");
			$levelDiv.each(function(index){
				var level = $(this).find("._ul").html();
				for(var i=0;i<data.levels.length;i++){
					var config = data.levels[i];
					if(level == config.activeLevel){
						$(this).find("._mina").text(config.minAmount);
						$(this).find("._maxa").val(config.maxAmount);
						$(this).find("._in").val(config.cardInventoryId);
						$(this).find("._ls").val(config.cardLevel);
						break;
					}
				}
			});
		},
		submitForm : function () {
			//做一次所有的表单格式验证
			var check = $.checkFrom.checkAllType("._config");
			if(!check){
				return;
			}
			
			var attrs = new Array();
			
			attrs.push("rsvdc1");
			attrs.push("rsvdc2");
			attrs.push("rsvst1");
			attrs.push("rsvst2");
			
			var $levelDiv = $("._levelDiv");
			$levelDiv.each(function(index){
				attrs.push("voList["+index+"].minAmount");
				attrs.push("voList["+index+"].maxAmount");
				attrs.push("voList["+index+"].activeLevel");
				attrs.push("voList["+index+"].cardInventoryId");
				attrs.push("voList["+index+"].cardInventoryName");
				attrs.push("voList["+index+"].cardLevel");
			});
			attrs.push("webws");
			attrs.push("jobws");
			
			var vals = new Array();
			vals.push($("#minPoint").val());
			vals.push($("#stepPoint").val());
			vals.push($("#rolePwd").val());
			vals.push($("#picCode").val());
			var $levelDiv = $("._levelDiv");
			$levelDiv.each(function(index){
				var minAmount =$(this).find("._mina").text();
				var maxAmount =$(this).find("._maxa").val();
				var activeLevel =$(this).find("._ul").text();
				var cardInventoryId =$(this).find("._in").val();
				var cardInventoryName =$(this).find("._in").find("option:selected").text().trim();
				var cardLevel =$(this).find("._ls").val();
				vals.push(minAmount);
				vals.push(maxAmount);
				vals.push(activeLevel);
				vals.push(cardInventoryId);
				vals.push(cardInventoryName);
				vals.push(cardLevel);
			});
			vals.push($("#webws").val());
			vals.push($("#jobws").val());
			formPost.submitForm("saveUserBaseConfig.do",attrs,vals);
		}
	};
}();

