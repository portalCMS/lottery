var helpcenter = function(){
	var who,dwho;
	var callbackFuc = {
		"saveCallBack":function(data){
			formPost.showErrorMessage(data.success);
			$("#datailName").val("");
			var obj = data.obj;
			var $trObj = $("._clone").clone(true);
			$trObj.show();
			$trObj.find("td").eq(0).text(obj.id);
			$trObj.find("td").eq(1).text(obj.createTime);
			$trObj.find("td").eq(2).text(obj.datailName);
			$trObj.find("td").eq(3).find("span").text("显示");
			$trObj.find("td").eq(4).text(0);
			$trObj.find("td").eq(4).find("a").eq(0).attr("id",obj.id);
			$trObj.find("td").eq(4).find("a").eq(1).attr("id",obj.id);
			$("#tbody").prepend($trObj.clone(true));
		},
		"updateCallBack":function(data){
			formPost.showErrorMessage(data.success);
			var $trObj = $(who);
			var obj = data.obj;
			$trObj.find("td").eq(2).text(obj.datailName);
			if(obj.status==10002){
				$trObj.find("td").eq(3).find("span").text("显示");
			}else{
				$trObj.find("td").eq(3).find("span").text("隐藏");
			}
			$("#edit_modal").modal("hide");
		},
		"getClassSort":function(data){
			var objs = data.objs;
			var html = "";
			for(var i=0;i<objs.length;i++){
				html+="<option value='"+objs[i].detailType+"'>"+objs[i].datailName+"</option>";
			}
			$("#dtype").html(html);
		},
		"deleteCallBack":function(data){
			formPost.showErrorMessage(data.success);
			$(dwho).remove();
			$("#del_modal").modal("hide");
		}
	};
	
	var packageData = function(attrs,values){
		attrs.push("type");
		attrs.push("datailName");
		values.push($("#type").val());
		if($("#datailName").val()==""){formPost.showErrorMessage("分类名称不能为空");return false;}
		values.push($("#datailName").val());
		return true;
	};
	var updatePackageData = function(attrs,values){
		attrs.push("id");
		attrs.push("datailName");
		attrs.push("status");
		values.push($("#id").val());
		if($("#updateDatailName").val()==""){formPost.showErrorMessage("分类名称不能为空");return false;}
		values.push($("#updateDatailName").val());
		values.push($("input[name='status']:checked").val());
		return true;
	};
	return{
		saveSort:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = packageData(attrs,values);
			if(flag)formPost.submitAjaxForm("article/saveHelpSort.do",attrs,values,callbackFuc["saveCallBack"]);
		},
		showSortUpdate:function(trObj){
			var $obj = $(trObj);
			$("#updateDatailName").val($obj.find("td").eq(2).text());
			$("#id").val($obj.find("td").eq(0).text());
			if($obj.find("td").eq(3).find("span").text()=="显示"){
				$("input[name='status']").eq(0).prop("checked",true);
			}else{
				$("input[name='status']").eq(1).prop("checked",true);
			}
			who = $obj;
			$("#edit_modal").modal("show");
		},
		updateSort:function(){
			var attrs = new Array();
			var values = new Array();
			var flag = updatePackageData(attrs,values);
			if(flag)formPost.submitAjaxForm("article/updateHelpSort.do",attrs,values,callbackFuc["updateCallBack"]);
		},
		showDelteSort:function(trObj){
			var $obj = $(trObj);
			var attrs = new Array();
			var values = new Array();
			attrs.push("type");
			attrs.push("id");
			values.push($("#type").val());
			values.push($obj.find("td").eq(0).text());
			formPost.submitAjaxForm("article/getClassSort.do",attrs,values,callbackFuc["getClassSort"]);
			dwho = $obj;
			$("#did").val($obj.find("td").eq(0).text());
			$("#del_modal").modal("show");
		},
		delteSort:function(trObj){
			var $obj = $(trObj);
			var attrs = new Array();
			var values = new Array();
			attrs.push("detailType");
			attrs.push("id");
			values.push($("#dtype").val());
			values.push($("#did").val());
			formPost.submitAjaxForm("article/deleteHelpSort.do",attrs,values,callbackFuc["deleteCallBack"]);
			$("#del_modal").modal("show");
		}
	};
}();

$(document).ready(function(){
	$("._add").click(function(){
		helpcenter.saveSort();
	});
	$("._edit_btn").click(function() {
		helpcenter.showSortUpdate($(this).closest("tr"));
	});
	$("._del_btn").click(function() {
		helpcenter.showDelteSort($(this).closest("tr"));
	});
	$("._update").click(function(){
		helpcenter.updateSort();
	});
	$("._delete").click(function(){
		helpcenter.delteSort();
	});
});