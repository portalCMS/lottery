var helpCenter = function(){
	var callBackFuc = {
			"initCallBack":function(data){
				classSorts = data.classSorts;
				amvos = data.amvos;
				var $dl = $("._dl");
				$dl.html("");
				var $dd = $("._dd").clone(true);
				for(var i=0;i<classSorts.length;i++){
					var classSort = classSorts[i];
					$dd.show();
					$dd.find("._setTitle").text(classSort.datailName);
					$dd.find("._ul").html("");
					for(var j=0;j<amvos.length;j++){
						var am = amvos[j];
						if(classSort.detailType!=am.detailType)continue;
						var $a = $("<a>");
						$a.attr("href","showHelps.html?id="+am.id);
						$a.attr("id",am.id);
						$a.click(function(){
							showHelp($(this).attr("id"),$(this));
						});
						$a.text(am.title);
						var $li = $("<li>").append($a);
						$dd.find("._ul").append($li);
					}
					if($dd.find("._ul").find("li").length>0){
						$dl.append($dd.clone(true));
					}
				}
			}
		};
	return{
		init:function(){
			var attrs = new Array();
			var vals = new Array();
			formPost.submitAjaxForm1("getHelps.shtml",attrs,vals,callBackFuc["initCallBack"],null);
		},
		showzh:function(){
			$("._setTitle").each(function(){
				if($(this).text().indexOf("账户")>-1){
					$(this).parent('dd').toggleClass('helpLDdOver').siblings('dd').removeClass('helpLDdOver');
				}
			});
		},
		showcz:function(){
			$("._setTitle").each(function(){
				if($(this).text().indexOf("彩种")>-1){
					$(this).parent('dd').toggleClass('helpLDdOver').siblings('dd').removeClass('helpLDdOver');
				}
			});
		},
		showcw:function(){
			$("._setTitle").each(function(){
				if($(this).text().indexOf("财务")>-1){
					$(this).parent('dd').toggleClass('helpLDdOver').siblings('dd').removeClass('helpLDdOver');
				}
			});
		}
	};
}();

$(document).ready(function(){
	helpCenter.init();
	$("._zh").click(function(){
		helpCenter.showzh();
	});
	$("._cz").click(function(){
		helpCenter.showcz();
	});
	$("._cw").click(function(){
		helpCenter.showcw();
	});
});