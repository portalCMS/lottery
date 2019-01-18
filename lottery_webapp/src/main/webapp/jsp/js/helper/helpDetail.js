var helpDetail = function(){
	var classSorts,amvos;
	var callBackFuc = {
		"initCallBack":function(data){
			classSorts = data.classSorts;
			amvos = data.amvos;
			var $dl = $("._dl");
			$dl.html("");
			var $dd = $("._dd").clone(true);
			var isShow = false;
			for(var i=0;i<classSorts.length;i++){
				var classSort = classSorts[i];
				$dd.show();
				$dd.find("._setTitle").text(classSort.datailName);
				$dd.find("._ul").html("");
				for(var j=0;j<amvos.length;j++){
					var am = amvos[j];
					if(classSort.detailType!=am.detailType)continue;
					if($("#hid").val()==am.id){
						$dd.toggleClass('helpLDdOver');
						$("._titledd").text(classSort.datailName);
						showconTent(am,j);
						isShow = true;
					}
					var $a = $("<a>");
					$a.attr("href","javaScript:;");
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
				if(isShow)$dd.removeClass('helpLDdOver');
			}
			if(!isShow){
				$dl.find("dd").eq(0).toggleClass('helpLDdOver');
				$a = $dl.find("dd").eq(0).find("li").eq(0).find("a");
				showHelp($a.attr("id"),$a);
			}
		},
		"updateHelpArticle":function(data){
			formPost.showErrorMessage("谢谢亲对我们的支持!");
		}
	};
	var showHelp = function(id,self){
		var am = null;
		var index = "";
		for(var j=0;j<amvos.length;j++){
			if(amvos[j].id==id){
				am = amvos[j];
				index = j;
				break;
			}
		}
		showconTent(am,index);
		//$("._bread").find("i").text($(self).closest('dd').find("a").eq(0).text());
	};
	var showconTent = function(am,index){
		var $show = $("._showam");
		$show.find("input").each(function(){
			$(this).remove();
		});
		$show.find(".helpDTitle").text(am.title);
		$show.find(".helpDContent").html(am.content);
		var $input = $("<input>");
		$input.attr("type","hidden");
		$input.attr("id","showId");
		$input.attr("value",am.id);
		$show.append($input);
		var $input1 = $("<input>");
		$input1.attr("type","hidden");
		$input1.attr("id","hindex");
		$input1.attr("value",index);
		$show.append($input1);
		$show.find(".helpDBtn").find("i").eq(0).text("("+am.applaud+")");
		$show.find(".helpDBtn").find("i").eq(1).text("("+am.oppose+")");
	};
	var updateHelpArticle = function(id,str){
		var attrs = new Array();
		var vals = new Array();
		attrs.push("id");
		vals.push(id);
		attrs.push(str);
		vals.push("true");
		formPost.submitAjaxForm1("updateHelpArticle.shtml",attrs,vals,callBackFuc["updateHelpArticle"],null);
	};
	return{
		init:function(){
			var attrs = new Array();
			var vals = new Array();
			formPost.submitAjaxForm1("getHelps.shtml",attrs,vals,callBackFuc["initCallBack"],null);
		},
		applaud:function(){
			var $show = $("._showam");
			var index = $("#hindex").val();
			var hid = $("#showId").val();
			if($.cookie("isApplaud"+hid)==null){
				amvos[index].applaud = amvos[index].applaud+1;
				$show.find(".helpDBtn").find("i").eq(0).text("("+amvos[index].applaud+")");
				$.cookie("isApplaud"+hid,true);
				updateHelpArticle(hid,"isApplaud");
			}else{
				formPost.showErrorMessage("亲,请不要重复点击哦");
			}
		},
		oppose:function(){
			var $show = $("._showam");
			var index = $("#hindex").val();
			var hid = $("#showId").val();
			if($.cookie("isOppose"+hid)==null){
				amvos[index].oppose = amvos[index].oppose+1;
				$show.find(".helpDBtn").find("i").eq(1).text("("+amvos[index].oppose+")");
				$.cookie("isOppose"+hid,true);
				updateHelpArticle(hid,"isOppose");
			}else{
				formPost.showErrorMessage("亲,请不要重复点击哦");
			}
		}
	};
}();

$(document).ready(function(){
	helpDetail.init();
	$("._isApplaud").click(function(){
		helpDetail.applaud();
	});
	$("._isOppose").click(function(){
		helpDetail.oppose();
	});
});