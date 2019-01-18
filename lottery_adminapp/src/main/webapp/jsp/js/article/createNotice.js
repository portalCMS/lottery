$(document).ready(function() {
	var ue = UE.getEditor('editor');
	$("._saveBtn").on('click',function(){
		notice.saveNotice(ue);
	});
	$("input[type=radio]").eq(0).click();
});

var notice = function(){
	return{
		saveNotice : function(ue){
			var attrs = new Array();
			attrs.push("topMark");
			attrs.push("keyMark");
			attrs.push("title");
			attrs.push("type");
			attrs.push("detailType");
			attrs.push("content");
			attrs.push("status");
			
			var vals = new Array();
			var top = $("#topMark").prop("checked");
			if(top){
				vals.push(10002);
			}else{
				vals.push(10001);
			}
			
			var key = $("#keyMark").prop("checked");
			if(key){
				vals.push(10002);
			}else{
				vals.push(10001);
			}
			
			vals.push($("#title").val());
			vals.push("A2");
			vals.push($("#detailType").val());
			vals.push(ue.getContent());
			vals.push($("input[type=radio]:checked").val());
			
			var action ="saveNoticeArticle.do";
			formPost.submitForm(action,attrs,vals);
		}
	};
}();