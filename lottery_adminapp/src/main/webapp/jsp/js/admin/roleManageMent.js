$(document).ready(function() {
	$("._hidArt").click(function() {
		noticeItems.updateStatus($(this).attr("id"), $(this).attr("name"), $(this));
	});

	$("._showUpdate").click(function(){
		noticeItems.showUpdate($(this).attr("id"));
	});
	$("._delArt").click(function(){
		var $self = $(this);
		$("._title").text($self.closest("tr").find("td").eq(3).text());
		$("._didBtn").click(function(){
			noticeItems.deleteStatus($self);
		});
		$("#delete_modal").modal("show");
	});
	$("._querybtn").click(function(){
		roleItems.queryData(1);
	});
});

var roleItems = function(){
	var who,dwho;
	var callBackFunc = {
		"upStatusCallBakc":function(data){
			formPost.showErrorMessage(data.success);
			var temp = $(who).text();
			$(who).text($(who).closest("tr").find("td").eq(6).find("span").text());
			$(who).closest("tr").find("td").eq(6).find("span").text(temp);
		},
		"deleteCallBack":function(data){
			$("#delete_modal").modal("hide");
			formPost.showErrorMessage(data.success);
			$(dwho).closest("tr").remove();
		}
	};
	return{
		showUpdate:function(id){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push(id);
			formPost.submitForm("initUpdateHelpCenter.do",attrs,values);
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
			formPost.submitAjaxForm("article/updateHelpCenterStatus.do",attrs,values,callBackFunc["upStatusCallBakc"]);
		},
		deleteStatus:function(dself){
			var attrs = new Array();
			var values = new Array();
			attrs.push("id");
			values.push($(dself).attr("id"));
			dwho = dself;
			formPost.submitAjaxForm("article/deleteArticleMange.do",attrs,values,callBackFunc["deleteCallBack"]);
		},
		queryData:function(pageNo){
			var attrs = new Array();
			var values = new Array();
			attrs.push("pageNum");
			attrs.push("roleName");
			attrs.push("roleEnName");
			if(pageNo==undefined)pageNo==1;
			values.push(pageNo);
			values.push($("#roleName").val());
			values.push($("#roleEnName").val());
			formPost.submitForm("initAdminRole.do",attrs,values);
		}
	};
}();