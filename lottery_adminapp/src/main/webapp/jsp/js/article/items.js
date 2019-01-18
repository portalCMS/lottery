$(document).ready(function() {
	$("._queryBtn").on('click',function(){
		noticeItems.queryNotice();
	});
	$("._queryBtn").click();
	$("._updArt").on("click",function(){
		noticeItems.showUpdate($(this).attr("id"));
	});
	$("._hidArt").click(function() {
		noticeItems.updateStatus($(this).attr("id"), $(this).attr("name"), $(this));
	});
	$("._delArt").click(function(){
		var $self = $(this);
		$("._dtitle").text($self.closest("tr").find("td").eq(2).text());
		$("._didBtn").unbind();
		$("._didBtn").click(function(){
			noticeItems.deleteStatus($self);
		});
		$("#delete_modal").modal("show");
	});
});

var noticeItems = function(){
	var who,dwho;
	var callBackFunc = {
		"upStatusCallBakc":function(data){
			formPost.showErrorMessage(data.success);
			if($(who).text()=='隐藏'){
				$(who).text('显示');
				$(who).closest("tr").find("td").eq(5).find("span").eq(0).hide();
				$(who).closest("tr").find("td").eq(5).find("span").eq(1).show();
			}else{
				$(who).text('隐藏');
				$(who).closest("tr").find("td").eq(5).find("span").eq(0).show();
				$(who).closest("tr").find("td").eq(5).find("span").eq(1).hide();
			}
		},
		"deleteCallBack":function(data){
			$("#delete_modal").modal("hide");
			formPost.showErrorMessage(data.success);
			$(dwho).closest("tr").remove();
		}
	};
	var pageInfo = function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:noticeItems.queryNotice("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:noticeItems.queryNotice("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		showUpdate : function(id){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			formPost.submitForm("initUpdateNotice.do",attrs,values);
		},
		queryNotice : function(pageNum){
			var attrs = new Array();
			attrs.push("topMark");
			attrs.push("keyMark");
			attrs.push("detailType");
			attrs.push("pageNum");
			var vals = new Array();
			var top = $("#topMark").prop("checked");
			if(top){
				vals.push(10002);
			}else{
				vals.push(0);
			}
			
			var key = $("#keyMark").prop("checked");
			if(key){
				vals.push(10002);
			}else{
				vals.push(0);
			}
			
			vals.push($("#detailType").val());
			if(pageNum==undefined)pageNum=1;
			vals.push(pageNum);
			var action ="article/queryNotice.do";
			formPost.submitAjaxForm(action,attrs,vals,noticeItems.queryCallBack);
		},
		queryCallBack:function(data){
			pageInfo("_pageInfo",data.pageNum,data.maxCount,data.pageCount);
			$("#myTbody").html("");
			for(var i=0;i<data.page.entitylist.length;i++){
				var article = data.page.entitylist[i];
				var $hidTr = $("._hidTr:last").clone(true);
				$hidTr.show();
				$hidTr.find("._index").html(i+1);
				$hidTr.find("._createTime").html(article.createTime);
				$hidTr.find("._title").html(article.title);
				var detailName = $("._"+article.detailType).text();
				$hidTr.find("._detailName").html(detailName);
				if(article.topMark==10002){
					$hidTr.find("._attr span:first").show();
				}else{
					$hidTr.find("._attr span:first").hide();
				}
				if(article.keyMark==10002){
					$hidTr.find("._attr span:last").show();
				}else{
					$hidTr.find("._attr span:last").hide();
				}
				
				if(article.status==10002){
					$hidTr.find("._status span").eq(0).show();
				}else if(article.status==10001){
					$hidTr.find("._status span").eq(1).show();
				}
				$hidTr.find("._updArt").attr("id",article.id);
				$hidTr.find("._hidArt").attr("id",article.id);
				$hidTr.find("._hidArt").attr("name",article.status);
				if(article.status==10001){
					$hidTr.find("._hidArt").text("显示");
				}else{
					$hidTr.find("._hidArt").text("隐藏");
				}
				$hidTr.find("._delArt").attr("id",article.id);
				$("#myTbody").append($hidTr.clone(true));
			}
		},
		updateStatus:function(id,status,self){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			attrs.push("status");
			if(status==10001){
				values.push(10002);
			}else{
				values.push(10001);
			}
			who = self;
			formPost.submitAjaxForm("article/updateNoticeStatus.do",attrs,values,callBackFunc["upStatusCallBakc"]);
		},
		deleteStatus:function(dself){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push($(dself).attr("id"));
			dwho = dself;
			formPost.submitAjaxForm("article/deleteArticleMange.do",attrs,values,callBackFunc["deleteCallBack"]);
		}
	};
}();