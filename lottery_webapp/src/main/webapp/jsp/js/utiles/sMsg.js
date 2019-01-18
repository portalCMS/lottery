(function($) {
	$.fn.sMsg = function(options) {
		var defaults = {
			title : "温馨提示",
			content : "内容在这里",
			actionEvent : "click",
			autoDisplay : false,
			btns : [ {
				text : "确&nbsp;&nbsp;&nbsp;&nbsp;定",
				style : "miniBtn",
				click : function(evn) {
					removeMsg();
				}
			} ]
		};
		var opts = $.extend({}, defaults, options);
		var o = $.meta ? $.extend({}, opts, $this.data()) : opts;

		if (o.autoDisplay) {
			building();
		}
		$(this).on(o.actionEvent, function() {
			building();
		});

		// 绑定关闭按钮事件
		$("body").on("click", ".msg-closeBtn", function() {
			removeMsg();
		});

		// 按下回车键和esc关闭遮罩弹出层
		$("body").on("keydown", function(e) {
			if(e.keyCode == 13 || e.keyCode == 27){
				removeMsg();
			}
		});

		// 关闭
		function removeMsg() {
			$(".bgIframe").remove();
			$(".msg").remove();
		}

		// 构建界面
		function building() {
			// 构建弹出层之前先删除
			removeMsg();
			var iframe = $("<iframe>").addClass("bgIframe").css({
				height : $(document).height() + "px"
			});
			var msg = $("<div>").addClass("msg");
			center(msg);
			var closeBtn = $("<span>").addClass("msg-title-close-btn").addClass("msg-closeBtn");
			//var poPupTop = $("<p>").addClass("poPupTop");
			var poPupBg = $("<div>").addClass("poPupBg");
			var poPupBot = $("<p>").addClass("poPupBot");

			var title = $("<h3>").addClass("poPupTitle").html(o.title);
			title.append(closeBtn);
			poPupBg.append(title);
			
			
			var btns = $("<p>").addClass("poPupBtn");

			//如果title为导入文件
			if(o.title=='导入文件'){
				var fileInput = "<input type='file' id='myfiles' name='myfiles'/>" ;
				var content = $("<div>").addClass("poPupBgUl").html(fileInput);
				
				poPupBg.append(content);
				
				$.each(o.btns, function(index, val) {
					var btn = $("<a id='uploadFile' href='javascript:;'>").addClass('smallBtn ml5').html(
							"<span style='width: 60px'>载入文件</span>");
					btns.append(btn);
				});
				
				$("#uploadFile").unbind();
				btns.find("#uploadFile").on("click", function() {
					ajaxFileUpload();
				});
			}else{
				var ct = o.content;
				var tab="<textarea cols='60' rows='12'>";
				var tae="</textarea>";
				if(o.content.length>600){
					ct = tab+o.content+tae;
				}
				var content = $("<div>").addClass("poPupBgUl").html(ct);
				
				poPupBg.append(content);
				
				$.each(o.btns, function(index, val) {
					var btn = $("<a>").attr("id","copyBtn").addClass(val.style).html(
							"<span>" + val.text + "</span>");
					btns.append(btn);
				});
				
				// 弹出层按钮绑定事件，并设置回调
				btns.find("a").on("click", function() {
					o.btns[$(this).index()].click();
				});
			}


			if (o.btns.length >= 2) {
				btns.find("a").css({
					"margin-right" : "10px"
				});
			}

			poPupBg.append(btns);

			$(window).scroll(function() {
				center(msg);
			});
			$(window).resize(function() {
				center(msg);
			});

			// register
			//msg.append(closeBtn);
			//msg.append(poPupTop);
			msg.append(poPupBg);
			//msg.append(poPupBot);
			$("body").append(msg);
			$("body").append(iframe);
		}

		function center(obj) {
			var windowWidth = $(window).width();
			var windowHeight = $(window).height();
			var popupHeight = $(obj).height();
			var popupWidth = $(obj).width();
			$(obj).css(
					{
						"top" : (windowHeight - popupHeight) / 2
								+ $(window).scrollTop() - 50
					});
		}
		
		function ajaxFileUpload() {
			//$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
		    //执行上传文件操作的函数
		    $.ajaxFileUpload({
		        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		        url:'fileUpload.shtml',
		        secureuri:false,                       //是否启用安全提交,默认为false
		        type : 'post',
		        fileElementId:'myfiles',           //文件选择框的id属性
		        dataType:'text',                       //服务器返回的格式,可以是json或xml等
		        success:function(data){        //服务器响应成功时的处理函数
		        	 data = data.substring(data.indexOf("{"),data.indexOf("}")+1);
	   	             //将String字符串转换成json
	   	             var dataset = $.parseJSON(data);
		        	if(dataset.status=='success'){
		        		$("#fileInfo").val(dataset.fileInfo);
		        		removeMsg();
		        		$("#fileInfo").trigger("keyup");
		        		var fileInfo = $("#fileInfo").val();
		        		if(fileInfo.lastIndexOf(",")==(fileInfo.length-1)){
		        			$("#fileInfo").val(fileInfo.substring(0,fileInfo.length-1));
		        		}
		        		$("#fileInfo").trigger("keyup");
		        	}else if(dataset.errorMsg){
		        		$(".poPupBgUl").html("<p class='color_red ml30 mb10'>"+dataset.errorMsg+"</p>" +
		        				"<input type='file' id='myfiles' name='myfiles'/>");
		        	}else{
		        		$(".poPupBgUl").html("<p class='color_red ml30 mb10'>亲,载入文件失败</p>" +
		        				"<input type='file' id='myfiles' name='myfiles'/>");
		        	}
		        	
		        },
		        error:function(data, status, e){ //服务器响应失败时的处理函数
		        	$(".poPupBgUl").html("<p class='color_red ml30 mb10'>亲,载入文件失败</p>" +
    				"<input type='file' id='myfiles' name='myfiles'/>");
		        }
		    });
	    }
	};
})(jQuery);
