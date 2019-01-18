/***
 * 百分百原创 by carlos 
 */
var lock_number = function() {
	var config = {
		"address" : "g-play-limit",
		"name" : "限号列表"
	};
	var a,id = "_lock_number", main = "<div>";
	function createUI(index,callBackFuc) {
		$("." + id).remove();
		a = $("<a>").attr("class", id).html(config["name"]);
		$("." + config['address']).eq(index).append(a);
		a.css({
			"margin-right":"0px",
			"float":"right",
			"position" : "relative",
			"color" : "#ececec",
			"font-size":"12px"
		});
		var d = $(main).css({
			"position" : "absolute",
			"top" : 27,
			"left" : 0,
			"background" : "#ececec",
			"width" : "88px",
			"border" : "1px solid #ececec",
			"color" : "#000"
		}).attr("class", "_lock_number_list");
		d.hide();
		a.append(d);
		a.hover(function() {
			if(!!callBackFuc)callBackFuc(lock_number.buildList);
			d.show();
		}, function() {
			d.hide();
		});
	}
	function buildInfo(type) {
		if (!!a) {
			var type = parseInt(type);
			if(type==0)type=1;
			switch (type) {
				case 1:
					var title = '21:00:00';
					var array = [ "1,4,2,1,56", "1,2,4,5,7,8,1" ];
					buildList(title, array);
					break;
				case 2:
					var title = '21:00:00';
					var array = ["1,4,2,1,56", "1,2,4,5,7,8,1" ];
					buildList(title, array);
					break;
				default:
					var title = '21:00:00';
					var array = [ "1,4,2,1,56", "1,2,4,5,7,8,1" ];
					buildList(title, array);
					break;
			}
		}
	}
	return {
		init : function(type,address,index,callBackFuc) {
			if(address!=undefined)
			config["address"] = address;
			if(!!!index)var index=0;
			createUI(index,callBackFuc);
			//buildInfo(type);
		},
		buildList:function(title, array) {
			$("." + "_lock_number_list").html("");
			var title = $("<div class='lock_title'> ").html(title + "更新").css({
				"border-bottom" : "1px solid #ccc"
			});
			var ul = $("<ul class='lock_ul'>").css({
				"height" : "100px",
				"overflow-y" : "scroll"
			});
			if (array==undefined||array.length == 0) {
				var li = $("<li>").html("暂无限号").css({
					"padding" : "2px 5px"
				});
				ul.append(li);
			} else {

				for (var i = 0; i <= array.length; i++) {
					var li = $("<li>").css({
						"padding" : "2px 5px"
					});
					li.html(array[i]);
					ul.append(li);

				}
			}
			$("." + "_lock_number_list").append(title).append(ul);
		}
	};
}();
