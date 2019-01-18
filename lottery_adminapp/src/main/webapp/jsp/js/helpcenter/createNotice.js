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
			attrs.push("title");
			attrs.push("type");
			attrs.push("detailType");
			attrs.push("content");
			attrs.push("status");
			var vals = new Array();
			vals.push($("#title").val());
			vals.push("A1");
			vals.push($("#detailType").val());
			vals.push(ue.getContent());
			vals.push($("input[type=radio]:checked").val());
			
			var action ="saveHelpCenter.do";
			formPost.submitForm(action,attrs,vals);
		}
	};
}();