var setImg = function(){
	var callBack = {
		"init":function(data){
			var imgs = data.imgs;
			var panel = $("._img");
			var img = $("._imgDiv").clone(true);
			for(var i=0;i<imgs.length;i++){
				var obj = imgs[i];
				img.show();
				img.find("img").attr("src", obj.url);
				img.find("#url").val(obj.url);
				img.find("#alt").val(obj.alt);
				img.find("input[name='openType']").each(function(){
					if($(this).val==obj.openType)$(this).prop("checked",true);
				});
				img.on("blur","#url",function(){
					var size = $("#size").val();
					var width = size.split("*")[0];
					var height = size.split("*")[1];
					var tmepImg = new Image();
					tmepImg.src = $(this).val();
					if(tmepImg.width>width||tmepImg.height>height){
						formPost.showErrorMessage("图片宽高比超出限制");
						$(this).val("");
						return;
					}
					img.find("img").attr("src", $(this).val());
				});
				img.on("click","img",function(){
					if(confirm("是否确定删除该广告位图片?")){
						delSelf(img);
					}
				});
				panel.prepend(img);
			}
		}
	};
	var delSelf = function(img){
		$(img).remove();
	};
	var packageDate = function(attrs,values){
		//advers
		var flag = 0;
		if($("._img > .img_display").length<=0){
			formPost.showErrorMessage("请上传图片");
			return false;
		}
		$("._img > .img_display").each(function(index,val){
			attrs.push("advers["+index+"].regionCode");
			attrs.push("advers["+index+"].index");
			attrs.push("advers["+index+"].url");
			attrs.push("advers["+index+"].alt");
			attrs.push("advers["+index+"].openType");
			values.push($("#regionCode").val());
			values.push(index+1);
			var url = $(this).find("#url").val();
			if(url==""){
				formPost.showErrorMessage("url不能为空");
				flag++;
				return;
			}
			values.push(url);
			var alt = $(this).find("#alt").val();
			values.push(alt);
			var openType = $(this).find("input[name='openType']:checked").val();
			values.push(openType);
		});
		if(flag==0)return true;
		return false;
	};
	return{
		initImg:function(){
			var regionCode = $("#regionCode").val();
			var attrs = new Array();
			var values = new Array();
			attrs.push("regionCode");
			values.push(regionCode);
			formPost.submitAjaxForm("advert/getAdvertisingList.do",attrs,values,callBack["init"]);
		},
		addImg:function(){
			if($("._img > .img_display").length+1>$("#count").val()){
				formPost.showErrorMessage("超过图片限制");
				return;
			}
			var panel = $("._img");
			var img = $("._imgDiv").clone(true);
			img.show();
			img.find("img").attr("src", "http://zcimg.zcool.com.cn/zcimg/694c53e7b38832f8758920697cc9");
			img.on("blur","#url",function(){
				var size = $("#size").val();
				var width = size.split("*")[0];
				var height = size.split("*")[1];
				var tmepImg = new Image();
				tmepImg.src = $(this).val();
				if(tmepImg.width>width||tmepImg.height>height){
					formPost.showErrorMessage("图片宽高比超出限制");
					$(this).val("");
					return;
				}
				img.find("img").attr("src", $(this).val());
			});
			img.on("click","img",function(){
				if(confirm("是否确定删除该广告位图片?")){
					delSelf(img);
				}
			});
			panel.prepend(img);
		},
		saveImg:function(){
			var attrs = new Array();
			var values = new Array();
			attrs.push("regionCode");
			values.push($("#regionCode").val());
			var flag = packageDate(attrs,values);
			if(flag)formPost.submitForm("saveAdvertisingList.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	setImg.initImg();
	$("#_upload_img_btn").click(function() {
		setImg.addImg();
	});
	$("._saveBtn").click(function(){
		setImg.saveImg();
	});
});