var noticelist = function(){
	
	var callBackFuc = {
		"initCallBack":function(data){
			var $ul = $("._ul");
			$ul.find("li").each(function(){
				$(this).remove();
			});
			var vos = data.page.pagelist;
			var $li = $("._li").clone(true);
			var tp = data.pageCount;
			var pn= data.pageNum;
			for(var i=0;i<vos.length;i++){
				var obj = vos[i];
				$li.find("div").eq(0).find("a").removeClass("color_red");
				$li.show();
				//shownotice.html
				$li.find("div").eq(0).find("span").find("span").eq(0).text("["+obj.detailTypeName+"]");
				$li.find("div").eq(0).find("span").find("span").eq(1).text(obj.createTime);
				$li.find("div").eq(0).find("a").attr("href","shownotice.html?id="+obj.id);
				$li.find("div").eq(0).find("a").text(obj.title);
				if(obj.keyMark == 10002){
					$li.find("div").eq(0).find("a").addClass("color_red");
				}
				var tmepStr = $li.find("div").eq(1).html();
				$li.find("div").eq(1).html(obj.content.substring(0,tmepStr.length));
				$ul.append($li.clone(true));
			}
			//显示分页选择显示栏
			pageBarBuid.showPageBar("noticelist.init",pn,tp);
		}
	};
	return{
		init:function(pageNo){
			var attrs = new Array();
			var value = new Array();
			attrs.push("pageNum");
			if(pageNo==undefined||pageNo==null)pageNo=1;
			value.push(pageNo);
			formPost.submitAjaxForm1("getNotices.shtml",attrs,value,callBackFuc["initCallBack"],null);
		}
	};
}();

$(document).ready(function(){
	noticelist.init(1);
});