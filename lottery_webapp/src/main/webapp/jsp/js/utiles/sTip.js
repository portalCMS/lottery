(function($) {
	$.fn.sTip = function(options) {
		var defaults = {
			display : false,
			tipInfo : "默认显示的提示文案。在这里",
			UIClass : {
				cover : "tipsCover",
				info : "tipsInfo",
				top : "tipsTopP",
				bot : "tipsBotP",
				bg : "tipsBg"
			},
			offsetTop : 0,
			offsetLeft : 0
		};
		var opts = $.extend({}, defaults, options);
		var o = $.meta ? $.extend({}, opts, $this.data()) : opts;

		var offsetTop = this.offset().top, offsetLeft = this.offset().left;
		$(this).addClass(o.UIClass.cover);
		var dom = DOMBuilder();
		$("body").append(dom);
		dom.css({
			top : (offsetTop - dom.height()) + o.offsetTop,
			left : offsetLeft - o.offsetLeft
		});
		if (!o.display) {
			$(this).hover(function() {
				$("." + o.UIClass.info).show();
			}, function() {
				$("." + o.UIClass.info).hide();
			});
		}
		
		function DOMBuilder() {
			$(".tipsInfo").remove();
			var cover = $("<div>"), info = $("<div>"), top = $("<div>"), bot = $("<p>");
			cover.addClass(o.UIClass.info);
			if (!o.display) {
				cover.hide();
			}

			top.addClass(o.UIClass.top);
			bot.addClass(o.UIClass.bot);
			info.addClass(o.UIClass.bg);
			info.text(o.tipInfo);
			cover.append(top);
			cover.append(info);
			cover.append(bot);

			return cover;
		}

	};
})(jQuery);
