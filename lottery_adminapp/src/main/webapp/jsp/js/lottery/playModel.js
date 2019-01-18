var playModel = function() {
	var config = {
		"btn" : "_addPoutLeave",
		"group" : "_levelGroup",
		"del" : "_del_level_btn",
		"hid" : "_hidLevel"	
	};
	var level;
	function getElement() {
		$("._hidLevel ."+ config["del"]).hide();
		$("." + config["btn"]).on("click", function() {
			level = $("."+config["hid"]).eq(0).clone(true).show();
			$(level).find("." + config["del"]).show();
			$(this).closest("td").find("."+config["group"]).append(level);
			checkLeave();
		});
		$("." + config["del"]).on("click", function() {
			$(this).closest("."+config["hid"]).remove();
			checkLeave();
		});
		
		//判断是否已经有数据的，有的话表明是modify的，不能初始化第一行。
		$("td").each(function() {
			var hids = $(this).find("."+config["hid"]);
			if(hids.size()<1){
				$(this).find("." + config["btn"]).click();
				$(this).find("." + config["del"]).hide();
			}else{
				//已有数据的第一行的删除按钮要隐藏掉。
				$(hids).find("." + config["del"]).show();
				$(hids).eq(0).find("." + config["del"]).hide();
			}
		});
	}

	function checkLeave() {
		$("td").each(function() {
			var c = $(this).find("."+config["hid"]);
			c.each(function(index, value) {
				$(this).find("span").eq(0).text(index + 1);
			});
		});
	}

	return {
		init : function() {
			getElement();
			$("."+config["hid"]).eq(0).hide();
		}
	};
}();

$(document).ready(function() {
	playModel.init();
	$("#allSelect").on('click',function(){
		var isChecked = $(this).prop("checked");
		$("._mid").each(function(){
			$(this).prop("checked",isChecked);
		});
	});
});

var playForm = function(){
	return{
		packageData:function(attrs,values){
			attrs.push("lotteryGroup");
			values.push($("#lotteryGroup").val());
			
			var $tr = $("._mtr");
			var isOk = playForm.checkInputVal();
			if(!isOk){
				return false;
			}
			
			var i=0;
			$tr.each(function(){
				var self = $(this);
				if(!$(this).find("input[type='checkbox']").prop("checked")){
					return;
				}
				attrs.push("playList["+i+"].id");
				attrs.push("playList["+i+"].modelCode");
				values.push($(self).find("._mid").val());
				values.push($(self).find("._mc").text());
				
				var $level = $(self).find("._hidLevel"); 
				
				var j=0;
				$level.each(function(){
					var levelSelf=$(this);
					attrs.push("playList["+i+"].levelList["+j+"].winingRate");
					attrs.push("playList["+i+"].levelList["+j+"].winAmount");
					attrs.push("playList["+i+"].levelList["+j+"].awardLevel");
					attrs.push("playList["+i+"].levelList["+j+"].levelName");
					values.push($(levelSelf).find("input[type='text']").eq(0).val());
					values.push($(levelSelf).find("input[type='text']").eq(1).val());
					values.push($(levelSelf).find("span").eq(0).text());
					values.push($(levelSelf).find("span").eq(0).text()+$(levelSelf).find("span").eq(1).text());
					j++;
				});
				
				i++;
			});
		},
		checkInputVal:function(){
			var $tr = $("._mtr");
			var isOk = true;
			for(var i=0 ; i<$tr.size();i++){
				var self = $tr.get(i);
				if($(self).find("input[type='checkbox']").prop("checked")){
					var $level = $(self).find("._hidLevel"); 
					for(var j=0;j<$level.size();j++){
						var levelSelf=$level.get(j);
						var wr= $(levelSelf).find("input[type='text']").eq(0).val();
						var wa= $(levelSelf).find("input[type='text']").eq(1).val();
						//trim方法ie7不支持
						wr.replace(/(^s*)|(s*$)/g, "");
						if(wr==undefined||wr==""){
							isOk = false;
							break;
						}
						
						wa.replace(/(^s*)|(s*$)/g, "");
						if(wa==undefined||wa==""){
							isOk = false;
							break;
						}
					}
					
					if(!isOk){
						break;
					}
				}
			}
			return isOk;
		},
		submitForm:function(){
			var attrs = new Array();
			var values = new Array();
			var isOk = playForm.packageData(attrs,values);
			if(attrs.length<1){
				formPost.showErrorMessage("亲,别忘记选择你的玩法哦!");
				return;
			}
			if(isOk==false){
				formPost.showErrorMessage("亲,别忘记给已选择的玩法，配置中奖概率和中奖金额哦!");
				return;
			}
			
			formPost.submitForm("savePlayModel.do",attrs,values);
		},
		submitForm2:function(){
			var attrs = new Array();
			var values = new Array();
			var isOk = playForm.packageData(attrs,values);
			if(attrs.length<1){
				formPost.showErrorMessage("亲,别忘记选择你的玩法哦!");
				return;
			}
			if(isOk==false){
				formPost.showErrorMessage("亲,别忘记给已选择的玩法，配置中奖概率和中奖金额哦!");
				return;
			}
			
			formPost.submitForm("saveModifyPlayModel.do",attrs,values);
		}
		
	};
}();

