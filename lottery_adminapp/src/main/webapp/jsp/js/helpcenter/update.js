$(document).ready(function() {
	var ue = UE.getEditor('editor');
	ue.addListener("ready", function () {
        ue.setContent($("#content").val());
	});
	
	$("._saveBtn").on('click',function(){
		notice.saveNotice(ue);
	});
});

var notice = function(){
	return{
		saveNotice : function(ue){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("title");
			attrs.push("detailType");
			attrs.push("content");
			attrs.push("status");
			var vals = new Array();
			vals.push($("#id").val());
			vals.push($("#title").val());
			vals.push($("#detailType").val());
			vals.push(ue.getContent());
			vals.push($("input[type=radio]:checked").val());
			var action ="updateHelpCenter.do";
			formPost.submitForm(action,attrs,vals);
		}
	};
}();